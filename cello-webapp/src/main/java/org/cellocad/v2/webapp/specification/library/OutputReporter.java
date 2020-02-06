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

import org.cellocad.v2.webapp.exception.LibraryException;
import org.cellocad.v2.webapp.specification.library.serialization.OutputReporterSerializer;
import org.sbolstandard.core2.Component;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLValidationException;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-20
 *
 */
@JsonSerialize(using = OutputReporterSerializer.class)
public class OutputReporter {

	private String name;
	private Collection<Part> parts;
	private URI uri;
	
	/**
	 * @param name
	 * @param parts
	 */
	public OutputReporter(String name, Collection<Part> parts, URI uri) {
		this.name = name;
		this.parts = parts;
		this.uri = uri;
	}
	
	/**
	 * @param cd
	 * @param document
	 * @throws LibraryException
	 */
	public OutputReporter(ComponentDefinition cd, SBOLDocument document) throws LibraryException {
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
	 * Getter for <i>uri</i>
	 * @return value of <i>uri</i>
	 */
	public URI getUri() {
		return uri;
	}

}
