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
package org.cellocad.cello2.webapp.specification;

import java.io.File;

import org.cellocad.cello2.webapp.common.CObject;
import org.json.JSONObject;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public abstract class ApplicationSpecification extends CObject {
	
	private File netlistConstraintFile;
	private File targetDataFile;
	private File optionsFile;
	private File verilogFile;
	
	public ApplicationSpecification(File netlistConstraintFile, File targetDataFile, File optionsFile, File verilogFile) {
		this.setNetlistConstraintFile(netlistConstraintFile);
		this.setTargetDataFile(targetDataFile);
		this.setVerilogFile(verilogFile);
	}
	
	private void parseSpecification(JSONObject jObj) {
	}

	public ApplicationSpecification(JSONObject jObj) {
		this.parseSpecification(jObj);
	}
	
	/**
	 * Getter for <i>netlistConstraint</i>
	 * @return value of <i>netlistConstraint</i>
	 */
	public File getNetlistConstraintFile() {
		return netlistConstraintFile;
	}

	/**
	 * Setter for <i>netlistConstraintFile</i>
	 * @param netlistConstraintFile the value to set <i>netlistConstraintFile</i>
	 */
	public void setNetlistConstraintFile(File netlistConstraintFile) {
		this.netlistConstraintFile = netlistConstraintFile;
	}

	/**
	 * Getter for <i>targetData</i>
	 * @return value of <i>targetData</i>
	 */
	public File getTargetDataFile() {
		return targetDataFile;
	}

	/**
	 * Setter for <i>targetDataFile</i>
	 * @param targetDataFile the value to set <i>targetDataFile</i>
	 */
	public void setTargetDataFile(File targetDataFile) {
		this.targetDataFile = targetDataFile;
	}

	/**
	 * Getter for <i>optionsFile</i>
	 * @return value of <i>optionsFile</i>
	 */
	public File getOptionsFile() {
		return optionsFile;
	}

	/**
	 * Setter for <i>optionsFile</i>
	 * @param optionsFile the value to set <i>optionsFile</i>
	 */
	public void setOptionsFile(File optionsFile) {
		this.optionsFile = optionsFile;
	}

	/**
	 * Getter for <i>verilogFile</i>
	 * @return value of <i>verilogFile</i>
	 */
	public File getVerilogFile() {
		return verilogFile;
	}

	/**
	 * Setter for <i>verilogFile</i>
	 * @param verilogFile the value to set <i>verilogFile</i>
	 */
	public void setVerilogFile(File verilogFile) {
		this.verilogFile = verilogFile;
	}

}
