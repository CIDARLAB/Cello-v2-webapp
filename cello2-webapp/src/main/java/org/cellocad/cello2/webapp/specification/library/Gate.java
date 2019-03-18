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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellocad.cello2.webapp.exception.LibraryException;
import org.cellocad.cello2.webapp.specification.library.serialization.GateSerializationConstants;
import org.cellocad.cello2.webapp.specification.library.serialization.GateSerializer;
import org.sbolstandard.core2.Component;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLValidationException;
import org.sbolstandard.core2.SequenceOntology;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-18
 *
 */
@JsonSerialize(using = GateSerializer.class)
public class Gate {
	
	private String regulator;	
	private String group;
	private String name;
	private String type;
	private String system;
	private String color;
	private URI uri;
	
	private GateParts gateParts;
	private ResponseFunction responseFunction;

	public Gate(SBOLDocument document, ComponentDefinition cd) throws LibraryException {
		this.regulator = SBOLUtils.getCelloAnnotationString(cd,GateSerializationConstants.S_SYNBIOHUB_REGULATOR);
		this.group = SBOLUtils.getCelloAnnotationString(cd,GateSerializationConstants.S_SYNBIOHUB_GROUP_NAME);
		this.name = cd.getDisplayId();
		this.type = SBOLUtils.getCelloAnnotationString(cd,GateSerializationConstants.S_SYNBIOHUB_GATE_TYPE);
		this.system = SBOLUtils.getCelloAnnotationString(cd,GateSerializationConstants.S_SYNBIOHUB_SYSTEM);
		this.color = SBOLUtils.getCelloAnnotationString(cd,GateSerializationConstants.S_SYNBIOHUB_COLOR_HEXCODE);
		this.uri = cd.getIdentity();
		this.responseFunction = new ResponseFunction(cd);
		Collection<Part> parts = new ArrayList<>();
		List<Component> components;
		try {
			components = cd.getSortedComponents();
		} catch (SBOLValidationException e) {
			throw new LibraryException("Error with ComponentDefinition.");
		}
		for (Component c : components) {
			parts.add(new Part(c.getDefinition()));
		}
		String variable = this.responseFunction.getVariables().iterator().next().getName();
		CassetteParts cassetteParts = new CassetteParts(parts);
		Map<String,CassetteParts> gateParts = new HashMap<>();
		gateParts.put(variable, cassetteParts);
		ComponentDefinition cds = null;
		for (Component c : cd.getComponents()) {
			ComponentDefinition def = c.getDefinition();
			if (def.getRoles().contains(SequenceOntology.CDS)) {
				cds = def;
				break;
			}
		}
		ComponentDefinition promoter = SBOLUtils.getRegulated(document,cds).iterator().next();
		this.gateParts = new GateParts(gateParts,new Part(promoter));
	}

	/**
	 * Getter for <i>regulator</i>
	 * @return value of <i>regulator</i>
	 */
	public String getRegulator() {
		return regulator;
	}

	/**
	 * Getter for <i>group</i>
	 * @return value of <i>group</i>
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * Getter for <i>name</i>
	 * @return value of <i>name</i>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for <i>type</i>
	 * @return value of <i>type</i>
	 */
	public String getType() {
		return type;
	}

	/**
	 * Getter for <i>system</i>
	 * @return value of <i>system</i>
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * Getter for <i>color</i>
	 * @return value of <i>color</i>
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Getter for <i>uri</i>
	 * @return value of <i>uri</i>
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * Getter for <i>gateParts</i>
	 * @return value of <i>gateParts</i>
	 */
	public GateParts getGateParts() {
		return gateParts;
	}

	/**
	 * Getter for <i>responseFunction</i>
	 * @return value of <i>responseFunction</i>
	 */
	public ResponseFunction getResponseFunction() {
		return responseFunction;
	}
	
}
