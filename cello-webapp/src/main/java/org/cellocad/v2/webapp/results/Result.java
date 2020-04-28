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
package org.cellocad.v2.webapp.results;

import java.io.File;

import org.cellocad.v2.webapp.common.CObject;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-18
 *
 */
public class Result extends CObject {

	private File file;
	private String stage;
	private String tag;

	public Result(File file) {
		this.file = file;
		this.setName(file.getName());
		if (this.getName().endsWith("dpl.png")) {
			this.stage = "placing";
			this.tag = "dpl";
		}
		if (this.getName().startsWith("response_plot") && this.getName().endsWith(".png") ) {
			this.stage = "technologyMapping";
			this.tag = "response_plot";
		}
		if (this.getName().endsWith("_technologyMapping.png") ) {
			this.stage = "technologyMapping";
			this.tag = "topology";
		}
		if (this.getName().endsWith("_logicSynthesis.png") ) {
			this.stage = "logicSynthesis";
			this.tag = "topology";
		}
		if (this.getName().endsWith(".xml") ) {
			this.stage = "export";
			this.tag = "SBOL";
		}
		if (this.getName().endsWith("_logic.csv") ) {
			this.stage = "logicSynthesis";
			this.tag = "logic";
		}
		if (this.getName().endsWith("_activity.csv") ) {
			this.stage = "technologyMapping";
			this.tag = "activity";
		}
		if (this.getName().endsWith("_toxicity.csv") ) {
			this.stage = "technologyMapping";
			this.tag = "toxicity";
		}
		if (this.getName().endsWith("log.log") ) {
			this.stage = "application";
			this.tag = "log";
		}
	}

	/**
	 * Getter for <i>file</i>
	 * 
	 * @return value of <i>file</i>
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Getter for <i>stage</i>
	 * 
	 * @return value of <i>stage</i>
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * Getter for <i>tag</i>
	 * 
	 * @return value of <i>tag</i>
	 */
	public String getTag() {
		return tag;
	}

}
