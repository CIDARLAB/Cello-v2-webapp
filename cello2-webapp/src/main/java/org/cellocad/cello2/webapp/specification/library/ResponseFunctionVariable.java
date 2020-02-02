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

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-19
 *
 */
public class ResponseFunctionVariable {
	
	private String name;
	private Double offThreshold;
	private Double onThreshold;
	
	public ResponseFunctionVariable(String name, Double offThreshold, Double onThreshold) {
		this.name = name;
		this.offThreshold = offThreshold;
		this.onThreshold = onThreshold;
	}

	/**
	 * Getter for <i>name</i>
	 * @return value of <i>name</i>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for <i>offThreshold</i>
	 * @return value of <i>offThreshold</i>
	 */
	public Double getOffThreshold() {
		return offThreshold;
	}

	/**
	 * Setter for <i>offThreshold</i>
	 * @param offThreshold the value to set <i>offThreshold</i>
	 */
	public void setOffThreshold(Double offThreshold) {
		this.offThreshold = offThreshold;
	}

	/**
	 * Getter for <i>onThreshold</i>
	 * @return value of <i>onThreshold</i>
	 */
	public Double getOnThreshold() {
		return onThreshold;
	}

	/**
	 * Setter for <i>onThreshold</i>
	 * @param onThreshold the value to set <i>onThreshold</i>
	 */
	public void setOnThreshold(Double onThreshold) {
		this.onThreshold = onThreshold;
	}

}
