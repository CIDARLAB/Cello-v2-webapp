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
package org.cellocad.v2.webapp.specification.library;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.cellocad.v2.webapp.exception.LibraryException;
import org.cellocad.v2.webapp.specification.library.serialization.InputSensorSerializer;
import org.sbolstandard.core2.Component;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.Interaction;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLValidationException;
import org.sbolstandard.core2.SequenceOntology;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-20
 *
 */
@JsonSerialize(using = InputSensorSerializer.class)
public class InputSensor {
	
	private String name;
	private Collection<Part> parts;
	private Part promoter;
	private Double low;
	private Double high;
	private URI uri;
	
	/**
	 * @param name
	 * @param parts
	 * @param promoter
	 * @param low
	 * @param high
	 * @param uri
	 */
	public InputSensor(String name, Collection<Part> parts, Part promoter, Double low, Double high, URI uri) {
		this.name = name;
		this.parts = parts;
		this.promoter = promoter;
		this.low = low;
		this.high = high;
		this.uri = uri;
	}
	
	/**
	 * @param cd
	 * @param document
	 * @throws LibraryException
	 */
	public InputSensor(ComponentDefinition cd, SBOLDocument document) throws LibraryException {
		this.name = cd.getDisplayId();
		this.parts = new ArrayList<>();
		List<Component> components;
		try {
			components = cd.getSortedComponents();
		} catch (SBOLValidationException e) {
			throw new LibraryException("Error with ComponentDefinition.");
		}
		for (Component c : components) {
			parts.add(new Part(c.getDefinition()));
		}
		ComponentDefinition cds = null;
		for (Component c : cd.getComponents()) {
			ComponentDefinition def = c.getDefinition();
			if (def.getRoles().contains(SequenceOntology.CDS)) {
				cds = def;
				break;
			}
		}
		if (cds == null) {
			throw new LibraryException("Error with ComponentDefinition.");
		}
		Set<Interaction> regulations = SBOLUtils.getRegulations(document, cds);
		Interaction regulation = regulations.iterator().next();
		this.low = Double.valueOf(SBOLUtils.getCelloAnnotationString(regulation,"ymax"));
		this.high = Double.valueOf(SBOLUtils.getCelloAnnotationString(regulation,"ymin"));
		ComponentDefinition promoter = SBOLUtils.getRegulated(document,cds).iterator().next();
		this.promoter = new Part(promoter);
		this.uri = cd.getIdentity();
	}

	/**
	 * Getter for <i>name</i>
	 * @return value of <i>name</i>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for <i>parts</i>
	 * @return value of <i>parts</i>
	 */
	public Collection<Part> getParts() {
		return parts;
	}

	/**
	 * Getter for <i>promoter</i>
	 * @return value of <i>promoter</i>
	 */
	public Part getPromoter() {
		return promoter;
	}

	/**
	 * Getter for <i>low</i>
	 * @return value of <i>low</i>
	 */
	public Double getLow() {
		return low;
	}

	/**
	 * Getter for <i>high</i>
	 * @return value of <i>high</i>
	 */
	public Double getHigh() {
		return high;
	}

	/**
	 * Getter for <i>uri</i>
	 * @return value of <i>uri</i>
	 */
	public URI getUri() {
		return uri;
	}

}
