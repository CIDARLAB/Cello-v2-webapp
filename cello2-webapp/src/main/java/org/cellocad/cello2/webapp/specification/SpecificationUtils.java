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
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.project.ProjectUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-02-23
 *
 */
public class SpecificationUtils {
	
	public static final String S_NETLISTCONSTRAINT = "netlist_constraint";
	public static final String S_TARGETDATA = "target_data";
	public static final String S_OPTIONS = "options";
	public static final String S_VERILOG = "verilog";

	/**
	 * Get the specification of the project defined by parameters {@code type}, {@code userId}, and {@code name}.
	 * 
	 * @param type
	 * @param userId
	 * @param name
	 * @return
	 * @throws CelloWebException
	 */
	public static Specification getSpecification(String type, String userId, String name) throws CelloWebException {
		Specification rtn = null;
		String specificationFile = getSpecificationFile(userId,name);
		if (Utils.isValidFilepath(specificationFile)) {
			String specificationString;
			try {
				specificationString = Utils.getFileContentAsString(specificationFile);
			} catch (IOException e) {
				throw new CelloWebException("Error with specification.");
			}
			JSONObject json = new JSONObject(specificationString);
			String netlistConstraintFile;
			String targetDataFile;
			String optionsFile;
			String verilogFile;
			try {
				netlistConstraintFile = json.getString(S_NETLISTCONSTRAINT);
				targetDataFile = json.getString(S_TARGETDATA);
				optionsFile = json.getString(S_OPTIONS);
				verilogFile = json.getString(S_VERILOG);
			} catch (JSONException e) {
				throw new CelloWebException("Error with specification.");
			}
			SpecificationFactory specificationFactory = new SpecificationFactory();
			rtn = specificationFactory.getSpecification(type,netlistConstraintFile,targetDataFile,optionsFile,verilogFile);
		}
		return rtn;
	}

	/**
	 * Get the specification file of the project defined by parameters {@code userId} and {@code name}.
	 * 
	 * @param userId the userId
	 * @param name the project name
	 * @return the project specification file
	 */
	public static String getSpecificationFile(String userId, String name) {
		String rtn = "";
		rtn += ProjectUtils.getProjectDirectory(userId,name);
		rtn += Utils.getFileSeparator();
		rtn += "specification.json";
		return rtn;
	}

	/**
	 * Write the specification file of the project defined by parameters {@code userId} and {@code name}.
	 * 
	 * @param userId the userId
	 * @param name the project name
	 * @param netlistConstraintFile the netlist constraint file
	 * @param targetDataFile the target data file
	 * @param optionsFile the options file
	 * @param verilogFile the verilog file
	 */
	public static void writeSpecificationFile(String userId, String name, String netlistConstraintFile, String targetDataFile, String optionsFile, String verilogFile) {
	    String specification = SpecificationUtils.getSpecificationFile(userId,name);
	    JSONObject obj = new JSONObject();
	    obj.put(S_NETLISTCONSTRAINT,netlistConstraintFile);
	    obj.put(S_TARGETDATA,targetDataFile);
	    obj.put(S_OPTIONS,optionsFile);
	    obj.put(S_VERILOG,verilogFile);
	    Utils.writeToFile(obj.toString(),specification);
	}

	/**
	 * Write the specification file of the project defined by parameters {@code userId} and {@code name}.
	 * 
	 * @param userId the userId
	 * @param name the project name
	 * @param specification the specification
	 */
	public static void writeSpecificationFile(String userId, String name, Specification specification) {
	    String specificationFile = SpecificationUtils.getSpecificationFile(userId,name);
	    JSONObject obj = new JSONObject();
	    obj.put(S_NETLISTCONSTRAINT,specification.getNetlistConstraintFile());
	    obj.put(S_TARGETDATA,specification.getTargetDataFile());
	    obj.put(S_OPTIONS,specification.getOptionsFile());
	    obj.put(S_VERILOG,specification.getVerilogFile());
	    Utils.writeToFile(obj.toString(),specificationFile);
	}

}
