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
package org.cellocad.v2.webapp.specification.constraints;

import java.util.Map;

import org.cellocad.v2.webapp.specification.constraints.serialization.ConstraintsDeserializer;
import org.cellocad.v2.webapp.specification.constraints.serialization.ConstraintsSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-19
 *
 */
@JsonDeserialize(using = ConstraintsDeserializer.class)
@JsonSerialize(using = ConstraintsSerializer.class)
public class Constraints {

	Map<String,String> sensors;
	Map<String,String> reporters;

	public Constraints(Map<String,String> sensors, Map<String,String> reporters) {
		this.sensors = sensors;
		this.reporters = reporters;
	}

	/**
	 * Getter for <i>sensors</i>
	 * @return value of <i>sensors</i>
	 */
	public Map<String,String> getSensors() {
		return sensors;
	}

	/**
	 * Getter for <i>reporters</i>
	 * @return value of <i>reporters</i>
	 */
	public Map<String,String> getReporters() {
		return reporters;
	}

}
