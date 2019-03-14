/**
 * Copyright (C) 2019 Boston University (BU)
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cellocad.cello2.webapp.adaptors;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.sbolstandard.core2.Annotation;
import org.sbolstandard.core2.Attachment;
import org.sbolstandard.core2.Component;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.Identified;
import org.sbolstandard.core2.Interaction;
import org.sbolstandard.core2.ModuleDefinition;
import org.sbolstandard.core2.Participation;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLValidationException;
import org.sbolstandard.core2.SequenceOntology;
import org.sbolstandard.core2.SystemsBiologyOntology;
import org.synbiohub.frontend.SynBioHubException;
import org.synbiohub.frontend.SynBioHubFrontend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 18, 2019
 *
 */
public class SynBioHubAdaptor {
	
	private SynBioHubFrontend sbh;
	private SBOLDocument sbol;
	
	public SynBioHubAdaptor(String registry, String collection) throws SynBioHubException {
		this.setSbh(new SynBioHubFrontend(registry));
        URI uri = URI.create(collection);
        SBOLDocument sbol = sbh.getSBOL(uri);
        String arr[] = collection.split("/");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length - 2; i++) {
        	list.add(arr[i]);
        }
        String prefix = String.join("/",list);
        sbol.setDefaultURIprefix(prefix);
        this.setSbol(sbol);
	}
	
    /**
     * @return the URL contents (uncompressed) as a String
     * @throws IOException 
     * @throws ClientProtocolException 
     */
	private String getURLContentsAsString(URL url) throws IOException {
		String rtn = null;
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();
		HttpGet httpGet = new HttpGet(url.toString());
		HttpResponse httpResponse = httpClient.execute(httpGet);
		rtn = EntityUtils.toString(httpResponse.getEntity());
		return rtn;
	}

	private JsonNode getTopLevelJson() throws IOException {
		ArrayNode rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		rtn = mapper.createArrayNode();
		Set<Attachment> attachments = this.getSbol().getAttachments();
		// TODO move
		final List<String> S_VALIDSECTIONS = Arrays.asList(
				"header.json",
				"measurement_std.json",
				"logic_constraints.json",
				"eugene_rules.json",
				"genetic_locations.json"
				// TODO motif library
		);
		for (Attachment a : attachments) {
			if (S_VALIDSECTIONS.contains(a.getName())) {
				URL source = null;
				source = a.getSource().toURL();
				String str = getURLContentsAsString(source);
				JsonNode obj = mapper.readTree(str);
				rtn.add(obj);
			}
		}
		return rtn;
	}

	public JsonNode getTargetData() throws IOException, SBOLValidationException {
		ArrayNode rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		rtn = mapper.createArrayNode();
		for (JsonNode obj : this.getTopLevelJson()) {
			rtn.add(obj);
		}
		for (JsonNode obj : this.getGatesJson()) {
			rtn.add(obj);
		}
		ComponentDefinition cd = this.getSbol().getComponentDefinition("backbone", "1");
		if (cd != null) {
			JsonNode backbone = this.getPartJson(cd);
			rtn.add(backbone);
		}
		return rtn;
	}
	
	private String getCelloAnnotationString(Identified i, String name) {
		String rtn = null;
		Annotation value = i.getAnnotation(new QName("http://cellocad.org/Terms/cello#",name));
		if (value != null) {
			rtn = value.getStringValue();
		}
		return rtn;
	}

	/**
	 * @return
	 * @throws SBOLValidationException 
	 * @throws IOException 
	 */
	private JsonNode getGatesJson() throws SBOLValidationException, IOException {
		ArrayNode rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		rtn = mapper.createArrayNode();
		Set<JsonNode> parts = new HashSet<>();
		Collection<JsonNode> gates = new ArrayList<>();
		for (ComponentDefinition cd : this.getSbol().getRootComponentDefinitions()) {
			if (this.getCelloAnnotationString(cd,"group_name") != null) {
				gates.addAll(this.getGateJson(cd));
				parts.addAll(this.getPartsJson(cd));
			}
			String type = this.getCelloAnnotationString(cd,"gateType");
			if (type != null && type.equals("input_sensor")) {
				gates.addAll(this.getSensorJson(cd));
				parts.addAll(this.getPartsJson(cd));		
			}
			if (type != null && type.equals("output_reporter")) {
				gates.add(this.getReporterJson(cd));
				parts.addAll(this.getPartsJson(cd));
			}
		}
		for (JsonNode obj : parts) {
			rtn.add(obj);
		}
		for (JsonNode obj : gates) {
			rtn.add(obj);
		}
		return rtn;
	}
	
	private Set<Interaction> getInteractionsByType(ComponentDefinition cd, URI type) {
		Set<Interaction> rtn = new HashSet<>();
		for (ModuleDefinition md : this.getSbol().getRootModuleDefinitions()) {
			for (Interaction i : md.getInteractions()) {
				if (i.getTypes().contains(type)) {
					for (Participation p : i.getParticipations()) {
						if (p.getParticipantDefinition().equals(cd)) {
							rtn.add(i);
						}
					}
				}
			}
		}
		return rtn;
	}
	
	private Set<ComponentDefinition> getParticipantsByRole(Interaction i, URI role) {
		Set<ComponentDefinition> rtn = new HashSet<>();
		for (Participation p : i.getParticipations()) {
			if (p.containsRole(role)) {
				rtn.add(p.getParticipantDefinition());
			}
		}
		return rtn;
	}
		
	private Set<ComponentDefinition> getRegulated(ComponentDefinition cd) {
		Set<ComponentDefinition> rtn = new HashSet<>();
		Set<Interaction> productions = getInteractionsByType(cd,SystemsBiologyOntology.GENETIC_PRODUCTION);
		Set<ComponentDefinition> products = new HashSet<>();
		for (Interaction production : productions) {
			products.addAll(getParticipantsByRole(production,SystemsBiologyOntology.PRODUCT));
		}
		for (ComponentDefinition product : products) {
			Set<Interaction> stimulations = null;
			Set<Interaction> inhibitions = null;
			// stimulation
			stimulations = getInteractionsByType(product,SystemsBiologyOntology.STIMULATION);
			for (Interaction stimulation : stimulations) {
				rtn.addAll(getParticipantsByRole(stimulation,SystemsBiologyOntology.STIMULATED));
			}
			// inhibition
			inhibitions = getInteractionsByType(product,SystemsBiologyOntology.INHIBITION);
			for (Interaction inhibition : inhibitions) {
				rtn.addAll(getParticipantsByRole(inhibition,SystemsBiologyOntology.INHIBITED));
			}
			// complex formation
			Set<Interaction> formations = getInteractionsByType(product,SystemsBiologyOntology.NON_COVALENT_BINDING);
			for (Interaction formation : formations) {
				Set<ComponentDefinition> complexes = getParticipantsByRole(formation,SystemsBiologyOntology.PRODUCT);
				for (ComponentDefinition complex : complexes) {
					stimulations = getInteractionsByType(complex,SystemsBiologyOntology.STIMULATION);
					for (Interaction stimulation : stimulations) {
						rtn.addAll(getParticipantsByRole(stimulation,SystemsBiologyOntology.STIMULATED));
					}
					inhibitions = getInteractionsByType(product,SystemsBiologyOntology.INHIBITION);
					for (Interaction inhibition : inhibitions) {
						rtn.addAll(getParticipantsByRole(inhibition,SystemsBiologyOntology.INHIBITED));
					}
				}
			}
		}
		return rtn;
	}
	
	private Set<Interaction> getRegulations(ComponentDefinition cd) {
		Set<Interaction> rtn = new HashSet<>();
		Set<Interaction> productions = getInteractionsByType(cd,SystemsBiologyOntology.GENETIC_PRODUCTION);
		Set<ComponentDefinition> products = new HashSet<>();
		for (Interaction production : productions) {
			products.addAll(getParticipantsByRole(production,SystemsBiologyOntology.PRODUCT));
		}
		for (ComponentDefinition product : products) {
			Set<Interaction> stimulations = null;
			Set<Interaction> inhibitions = null;
			// stimulation
			stimulations = getInteractionsByType(product,SystemsBiologyOntology.STIMULATION);
			rtn.addAll(stimulations);
			// inhibition
			inhibitions = getInteractionsByType(product,SystemsBiologyOntology.INHIBITION);
			rtn.addAll(inhibitions);
			// complex formation
			Set<Interaction> formations = getInteractionsByType(product,SystemsBiologyOntology.NON_COVALENT_BINDING);
			for (Interaction formation : formations) {
				Set<ComponentDefinition> complexes = getParticipantsByRole(formation,SystemsBiologyOntology.PRODUCT);
				for (ComponentDefinition complex : complexes) {
					stimulations = getInteractionsByType(complex,SystemsBiologyOntology.STIMULATION);
					rtn.addAll(stimulations);
					inhibitions = getInteractionsByType(product,SystemsBiologyOntology.INHIBITION);
					rtn.addAll(inhibitions);
				}
			}
		}
		return rtn;
	}

	/**
	 * @param cd
	 * @return
	 * @throws SBOLValidationException 
	 */
	private Collection<JsonNode> getSensorJson(ComponentDefinition cd) throws SBOLValidationException {
		Collection<JsonNode> rtn = new HashSet<>();
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode sensor = mapper.createObjectNode();
		ComponentDefinition cds = null;
		for (Component c : cd.getComponents()) {
			ComponentDefinition def = c.getDefinition();
			if (def.getRoles().contains(SequenceOntology.CDS)) {
				cds = def;
				break;
			}
		}
		sensor.put("collection","input_sensors");
		sensor.put("name",cd.getDisplayId());
		Set<Interaction> regulations = this.getRegulations(cds);
		Interaction regulation = regulations.iterator().next();
		sensor.put("signal_high",Double.valueOf(this.getCelloAnnotationString(regulation,"ymax")));
		sensor.put("signal_low",Double.valueOf(this.getCelloAnnotationString(regulation,"ymin")));
		ComponentDefinition promoter = this.getRegulated(cds).iterator().next();
		sensor.put("promoter",promoter.getDisplayId());
		ArrayNode parts = mapper.createArrayNode();
		List<Component> temp = cd.getSortedComponents();
		for (Component c : temp) {
			parts.add(c.getDefinition().getDisplayId());
		}
		sensor.set("parts",parts);
		sensor.put("uri",cd.getIdentity().toString());
		rtn.add(sensor);
		rtn.add(this.getPartJson(promoter));
		return rtn;
	}

	/**
	 * @param cd
	 * @return
	 * @throws SBOLValidationException 
	 */
	private JsonNode getReporterJson(ComponentDefinition cd) throws SBOLValidationException {
		ObjectNode rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		rtn = mapper.createObjectNode();
		rtn.put("collection","output_reporters");
		rtn.put("name",cd.getDisplayId());
		ArrayNode parts = mapper.createArrayNode();
		List<Component> temp = cd.getSortedComponents();
		for (Component c : temp) {
			parts.add(c.getDefinition().getDisplayId());
		}
		rtn.set("parts",parts);
		rtn.put("uri",cd.getIdentity().toString());
		return rtn;
	}

	private Collection<JsonNode> getPartsJson(ComponentDefinition cd) {
		Collection<JsonNode> rtn = new HashSet<>();
		for (Component c : cd.getComponents()) {
			JsonNode part = this.getPartJson(c.getDefinition());
            rtn.add(part);
		}
		return rtn;
	}
	
	private JsonNode getPartJson(ComponentDefinition cd) {
		ObjectNode rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		rtn = mapper.createObjectNode();
		rtn.put("collection","parts");
		URI role = cd.getRoles().iterator().next();
		String roleString = "";
		if (role.equals(SequenceOntology.PROMOTER))
            roleString = "promoter";
        if (role.equals(SequenceOntology.CDS))
            roleString = "cds";
        if (role.equals(SequenceOntology.RIBOSOME_ENTRY_SITE))
            roleString = "rbs";
        if (role.equals(SequenceOntology.TERMINATOR))
            roleString = "terminator";
        if (role.equals(URI.create("http://identifiers.org/so/SO:0000374")))
            roleString = "ribozyme";
        if (role.equals(URI.create("http://identifiers.org/so/SO:0001953")))
            roleString = "scar";
        if (cd.getDisplayId().equals("backbone"))
        	roleString = "backbone";
        rtn.put("type",roleString);
        rtn.put("name",cd.getDisplayId());
		rtn.put("dnasequence",cd.getSequences().iterator().next().getElements());
        rtn.put("uri",cd.getIdentity().toString());
        return rtn;
	}

	/**
	 * @return
	 * @throws SBOLValidationException 
	 * @throws IOException 
	 */
	private Collection<JsonNode> getGateJson(ComponentDefinition cd) throws SBOLValidationException, IOException {
		Collection<JsonNode> rtn = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		// gate
		ObjectNode gate = mapper.createObjectNode();
		gate.put("collection","gates");
		gate.put("regulator",this.getCelloAnnotationString(cd,"group_name"));
		gate.put("group_name",this.getCelloAnnotationString(cd,"group_name"));
		gate.put("gate_name",cd.getDisplayId());
		gate.put("gate_type",this.getCelloAnnotationString(cd,"gate_type"));
		gate.put("system",this.getCelloAnnotationString(cd,"family"));
		gate.put("color_hexcode",this.getCelloAnnotationString(cd,"color_hexcode"));
		gate.put("uri",cd.getIdentity().toString());
		rtn.add(gate);
		// attachment
		Set<Attachment> attachments = cd.getAttachments();
		for (Attachment a : attachments) {
			if (a.getName().endsWith(".json")) {
				URL url = null;
				try {
					url = a.getSource().toURL();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				String str = getURLContentsAsString(url);
				JsonNode obj = mapper.readTree(str);
				rtn.add(obj);
			}
		}
		// response function
		ObjectNode rf = mapper.createObjectNode();
		rf.put("collection","response_functions");
		rf.put("gate_name",cd.getDisplayId());
		rf.put("equation",this.getCelloAnnotationString(cd,"response_function"));
		ArrayNode variables = mapper.createArrayNode();
		ObjectNode x = mapper.createObjectNode();
		x.put("name","x");
		x.put("on_threshold",Double.valueOf(this.getCelloAnnotationString(cd,"x_on_threshold")));
		x.put("off_threshold",Double.valueOf(this.getCelloAnnotationString(cd,"x_off_threshold")));
		variables.add(x);
		rf.set("variables",variables);
		ArrayNode parameters = mapper.createArrayNode();
		for (String name : new String[]{"ymax","ymin","K","n"}) {
			ObjectNode parameter = mapper.createObjectNode();
			parameter.put("name",name);
			parameter.put("value",Double.valueOf(this.getCelloAnnotationString(cd,name)));
			parameters.add(parameter);
		}
		rf.set("parameters",parameters);
		rtn.add(rf);
		// gate parts
		ObjectNode gateParts = mapper.createObjectNode();
		gateParts.put("collection","gate_parts");
		gateParts.put("gate_name",cd.getDisplayId());
		ArrayNode cassettes = mapper.createArrayNode();
		ObjectNode cassette = mapper.createObjectNode();
		cassette.put("maps_to_variable","x");
		ArrayNode cassetteParts = mapper.createArrayNode();
		List<Component> parts = cd.getSortedComponents();
		for (Component c : parts) {
			cassetteParts.add(c.getDefinition().getDisplayId());
		}
		cassette.set("cassette_parts",cassetteParts);
		cassettes.add(cassette);
		gateParts.set("expression_cassettes",cassettes);
		ComponentDefinition cds = null;
		for (Component c : cd.getComponents()) {
			ComponentDefinition def = c.getDefinition();
			if (def.getRoles().contains(SequenceOntology.CDS)) {
				cds = def;
				break;
			}
		}
		ComponentDefinition promoter = this.getRegulated(cds).iterator().next();
		gateParts.put("promoter",promoter.getDisplayId());
		rtn.add(gateParts);
		rtn.add(this.getPartJson(promoter));
		return rtn;
	}

	/**
	 * Getter for <i>sbh</i>
	 * @return value of <i>sbh</i>
	 */
	public SynBioHubFrontend getSbh() {
		return sbh;
	}

	/**
	 * Setter for <i>sbh</i>
	 * @param sbh the value to set <i>sbh</i>
	 */
	private void setSbh(SynBioHubFrontend sbh) {
		this.sbh = sbh;
	}

	/**
	 * Getter for <i>sbol</i>
	 * @return value of <i>sbol</i>
	 */
	public SBOLDocument getSbol() {
		return sbol;
	}

	/**
	 * Setter for <i>sbol</i>
	 * @param sbol the value to set <i>sbol</i>
	 */
	private void setSbol(SBOLDocument sbol) {
		this.sbol = sbol;
	}
	
}
