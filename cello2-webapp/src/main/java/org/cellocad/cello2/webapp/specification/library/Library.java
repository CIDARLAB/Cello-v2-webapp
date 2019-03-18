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

import java.util.Collection;

import org.cellocad.cello2.webapp.specification.library.serialization.LibrarySerializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-19
 *
 */
@JsonSerialize(using = LibrarySerializer.class)
public class Library {

	public Collection<Gate> gates;
	public Collection<InputSensor> sensors;
	public Collection<OutputReporter> reporters;
	public Collection<Part> parts;
	public Collection<JsonNode> objects;

	/**
	 * @param gates
	 * @param sensors
	 * @param reporters
	 * @param parts
	 * @param objects
	 */
	public Library(Collection<Gate> gates, Collection<InputSensor> sensors, Collection<OutputReporter> reporters,
			Collection<Part> parts, Collection<JsonNode> objects) {
		super();
		this.gates = gates;
		this.sensors = sensors;
		this.reporters = reporters;
		this.parts = parts;
		this.objects = objects;
	}
	
	/**
	 * Getter for <i>gates</i>
	 * @return value of <i>gates</i>
	 */
	public Collection<Gate> getGates() {
		return gates;
	}

	/**
	 * Getter for <i>sensors</i>
	 * @return value of <i>sensors</i>
	 */
	public Collection<InputSensor> getSensors() {
		return sensors;
	}

	/**
	 * Getter for <i>reporters</i>
	 * @return value of <i>reporters</i>
	 */
	public Collection<OutputReporter> getReporters() {
		return reporters;
	}

	/**
	 * Getter for <i>parts</i>
	 * @return value of <i>parts</i>
	 */
	public Collection<Part> getParts() {
		return parts;
	}

	/**
	 * Getter for <i>objects</i>
	 * @return value of <i>objects</i>
	 */
	public Collection<JsonNode> getObjects() {
		return objects;
	}

}
