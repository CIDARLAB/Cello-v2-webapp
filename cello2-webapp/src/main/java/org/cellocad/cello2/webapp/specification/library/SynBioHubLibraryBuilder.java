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
package org.cellocad.cello2.webapp.specification.library;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.exception.LibraryException;
import org.sbolstandard.core2.Attachment;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.SBOLDocument;
import org.synbiohub.frontend.SynBioHubException;
import org.synbiohub.frontend.SynBioHubFrontend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 18, 2019
 *
 */
public class SynBioHubLibraryBuilder {
	
	private SynBioHubFrontend sbh;
	private SBOLDocument sbol;
	
	private static final String S_INPUT_SENSOR = "input_sensor";
	private static final String S_OUTPUT_REPORTER = "output_reporter";
	
	/**
	 * @param registry
	 * @param collection
	 * @throws SynBioHubException
	 */
	public SynBioHubLibraryBuilder(URL registry, URI collection) throws SynBioHubException {
		this.setSbh(new SynBioHubFrontend(registry.toString()));
        SBOLDocument sbol = sbh.getSBOL(collection);
//        String arr[] = collection.toString().split("/");
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < arr.length - 2; i++) {
//        	list.add(arr[i]);
//        }
//        String prefix = String.join("/",list);
//        sbol.setDefaultURIprefix(prefix);
        this.setSbol(sbol);
	}

	/**
	 * @return
	 * @throws LibraryException 
	 * @throws IOException 
	 */
	public Library build() throws LibraryException, IOException {
		Library rtn = null;
		Collection<Gate> gates = new ArrayList<>();
		Collection<Part> parts = new HashSet<Part>();
		Collection<InputSensor> sensors = new ArrayList<>();
		Collection<OutputReporter> reporters = new ArrayList<>();
		Collection<JsonNode> objects = this.getTopLevelJson();
		for (ComponentDefinition cd : this.getSbol().getRootComponentDefinitions()) {
			if (SBOLUtils.getCelloAnnotationString(cd,"group_name") != null) {
				gates.add(new Gate(this.getSbol(),cd));
			}
			String type = SBOLUtils.getCelloAnnotationString(cd,"gateType");
			if (type != null && type.equals(S_INPUT_SENSOR)) {
				sensors.add(new InputSensor(cd,this.getSbol()));
			}
			if (type != null && type.equals(S_OUTPUT_REPORTER)) {
				reporters.add(new OutputReporter(cd,this.getSbol()));
			}
			if (cd.getDisplayId().equals("backbone")) {
				parts.add(new Part(cd));
			}
		}
		for (InputSensor sensor : sensors) {
			parts.add(sensor.getPromoter());
			parts.addAll(sensor.getParts());
		}
		for (OutputReporter reporter : reporters) {
			parts.addAll(reporter.getParts());
		}
		for (Gate gate : gates) {
			GateParts gateParts = gate.getGateParts();
			ResponseFunction rf = gate.getResponseFunction();
			for (ResponseFunctionVariable variable : rf.getVariables()) {
				parts.addAll(gateParts.getCassetteParts(variable.getName()).getParts());
			}
			parts.add(gateParts.getPromoter());
		}
		rtn = new Library(gates,sensors,reporters,parts,objects);
		return rtn;
		
	}

	private Collection<JsonNode> getTopLevelJson() throws IOException {
		Collection<JsonNode> rtn = new ArrayList<>();
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
		ObjectMapper mapper = new ObjectMapper();
		for (Attachment a : attachments) {
			if (S_VALIDSECTIONS.contains(a.getName())) {
				URL source = null;
				source = a.getSource().toURL();
				String str = Utils.getURLContentsAsString(source);
				JsonNode obj = mapper.readTree(str);
				rtn.add(obj);
			}
		}
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
