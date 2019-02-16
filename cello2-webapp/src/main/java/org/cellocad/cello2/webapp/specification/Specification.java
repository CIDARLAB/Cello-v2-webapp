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

import java.io.IOException;

import org.cellocad.cello2.webapp.CelloWebException;
import org.cellocad.cello2.webapp.adaptors.SynBioHubAdaptor;
import org.cellocad.cello2.webapp.common.CObject;
import org.cellocad.cello2.webapp.common.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sbolstandard.core2.SBOLValidationException;
import org.synbiohub.frontend.SynBioHubException;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public abstract class Specification extends CObject {

	private static final String S_SETTINGS = "settings";
	private static final String S_PARAMETERS = "parameters";
	private static final String S_VERILOG = "verilog";
	private static final String S_LIBRARY = "library";
	private static final String S_CONSTRAINTS = "constraints";
	private static final String S_INPUTCONSTRAINTS = "input_constraints";
	private static final String S_OUTPUTCONSTRAINTS = "output_constraints";
	
	private String netlistConstraintFile;
	private String targetDataFile;
	private String optionsFile;
	private String verilogFile;
	
	public Specification(String netlistConstraintFile, String targetDataFile, String optionsFile, String verilogFile) {
		this.setNetlistConstraintFile(netlistConstraintFile);
		this.setTargetDataFile(targetDataFile);
		this.setOptionsFile(optionsFile);
		this.setVerilogFile(verilogFile);
	}
	
	/**
	 * @param name
	 * @param directory
	 * @param jObj
	 */
	private void parseParameters(String name, String directory, JSONObject jObj) {
		JSONObject value = null;
		value = jObj.getJSONObject(S_SETTINGS).getJSONObject(S_PARAMETERS);
		String str = "";
		for (String key : value.keySet()) {
			str += key;
			str += ",";
			str += '"' + value.getString(key) + '"';
			str += Utils.getNewLine();
		}
		String file = "";
		file += directory;
		file += Utils.getFileSeparator();
		file += name;
		file += "_options.csv";
		Utils.createFile(file);
		Utils.writeToFile(str,file);
		this.setOptionsFile(file);
	}

	/**
	 * @param name
	 * @param directory
	 * @param jObj
	 * @throws SBOLValidationException 
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws SynBioHubException 
	 */
	private void parseLibrary(String name, String directory, JSONObject jObj) throws JSONException, IOException, SBOLValidationException, SynBioHubException {
		// TODO get UCF from SBOL
		JSONObject value = null;
		value = jObj.getJSONObject(S_LIBRARY);
		boolean useRegistry = value.getBoolean("use_registry");
		String str = "";
		if (useRegistry) {
			String registry = value.getString("registry");
			String collection = value.getString("collection");
			SynBioHubAdaptor adaptor = null;
			adaptor = new SynBioHubAdaptor(registry,collection);
			str = adaptor.getTargetData().toString(2);
		}
		String file = "";
		file += directory;
		file += Utils.getFileSeparator();
		file += name;
		file += "_targetdata.json";
		Utils.createFile(file);
		Utils.writeToFile(str,file);
		this.setTargetDataFile(file);
	}
	
	/**
	 * @param name
	 * @param directory
	 * @param jObj
	 */
	private void parseVerilog(String name, String directory, JSONObject jObj) {
		String str = "";
		str = jObj.getString(S_VERILOG);
		String file = "";
		file += directory;
		file += Utils.getFileSeparator();
		file += name;
		file += ".v";
		Utils.createFile(file);
		Utils.writeToFile(str,file);
		this.setVerilogFile(file);
	}
	
	/**
	 * @param name
	 * @param directory
	 * @param jObj
	 */
	private void parseConstraints(String name, String directory, JSONObject jObj) {
		JSONObject value = null;
		value = jObj.getJSONObject(S_CONSTRAINTS);
		JSONArray constraints = new JSONArray();
		JSONObject temp = null;
		// input
		JSONObject input = value.getJSONObject(S_INPUTCONSTRAINTS);
		temp = new JSONObject();
		temp.put("collection",S_INPUTCONSTRAINTS);
		temp.put("sensor_map",input);
		constraints.put(temp);
		// output
		JSONObject output = value.getJSONObject(S_OUTPUTCONSTRAINTS);
		temp = new JSONObject();
		temp.put("collection",S_OUTPUTCONSTRAINTS);
		temp.put("reporter_map",output);
		constraints.put(temp);
		// file
		String file = "";
		file += directory;
		file += Utils.getFileSeparator();
		file += name;
		file += "_netlistconstraints.json";
		Utils.createFile(file);
		Utils.writeToFile(constraints.toString(2),file);
		this.setNetlistConstraintFile(file);
	}

	private void parseSpecification(String name, String directory, JSONObject jObj) throws JSONException, IOException, SBOLValidationException, SynBioHubException {
		this.parseParameters(name,directory,jObj);
		this.parseLibrary(name,directory,jObj);
		this.parseVerilog(name,directory,jObj);
		this.parseConstraints(name,directory,jObj);
	}

	public Specification(String name, String directory, JSONObject jObj) throws CelloWebException {
		try {
			this.parseSpecification(name,directory,jObj);
		} catch (JSONException | IOException | SBOLValidationException | SynBioHubException e) {
			throw new CelloWebException(e);
		}
	}
	
	/**
	 * Getter for <i>netlistConstraint</i>
	 * @return value of <i>netlistConstraint</i>
	 */
	public String getNetlistConstraintFile() {
		return netlistConstraintFile;
	}

	/**
	 * Setter for <i>netlistConstraintFile</i>
	 * @param netlistConstraintFile the value to set <i>netlistConstraintFile</i>
	 */
	public void setNetlistConstraintFile(final String netlistConstraintFile) {
		this.netlistConstraintFile = netlistConstraintFile;
	}

	/**
	 * Getter for <i>targetData</i>
	 * @return value of <i>targetData</i>
	 */
	public String getTargetDataFile() {
		return targetDataFile;
	}

	/**
	 * Setter for <i>targetDataFile</i>
	 * @param targetDataFile the value to set <i>targetDataFile</i>
	 */
	private void setTargetDataFile(final String targetDataFile) {
		this.targetDataFile = targetDataFile;
	}

	/**
	 * Getter for <i>optionsFile</i>
	 * @return value of <i>optionsFile</i>
	 */
	public String getOptionsFile() {
		return optionsFile;
	}

	/**
	 * Setter for <i>optionsFile</i>
	 * @param optionsFile the value to set <i>optionsFile</i>
	 */
	private void setOptionsFile(final String optionsFile) {
		this.optionsFile = optionsFile;
	}

	/**
	 * Getter for <i>verilogFile</i>
	 * @return value of <i>verilogFile</i>
	 */
	public String getVerilogFile() {
		return verilogFile;
	}

	/**
	 * Setter for <i>verilogFile</i>
	 * @param verilogFile the value to set <i>verilogFile</i>
	 */
	private void setVerilogFile(final String verilogFile) {
		this.verilogFile = verilogFile;
	}

}
