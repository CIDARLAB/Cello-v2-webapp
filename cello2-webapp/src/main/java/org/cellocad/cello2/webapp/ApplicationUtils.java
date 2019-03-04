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
package org.cellocad.cello2.webapp;

import java.io.IOException;

import org.cellocad.cello2.webapp.common.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-02-23
 *
 */
public class ApplicationUtils {

	/**
	 * @return
	 */
	public static String getProjectsDirectory() {
		String rtn = "";
		rtn = Utils.getFilepath() + "projects";
		return rtn;
	}

	/**
	 * 
	 */
	public static void createProjectsDirectory() {
		Utils.makeDirectory(getProjectsDirectory());
	}

	/**
	 * @return
	 */
	public static String getUsersFile() {
		String rtn = "";
		rtn = getProjectsDirectory() + Utils.getFileSeparator() + "users.json";
		return rtn;
	}

	/**
	 * 
	 */
	public static void createUsersFile() {
		String filepath = getUsersFile();
		Utils.createFile(filepath);
		JSONArray arr = new JSONArray();
		Utils.writeToFile(arr.toString(),filepath);
	}

	/**
	 * 
	 */
	public static void appendToUsersFile(String userId, String email) {
		String filepath = getUsersFile();
		String str;
		try {
			str = Utils.getFileContentAsString(filepath);
		} catch (IOException e) {
			throw new RuntimeException("Error with file.");
		}
		JSONArray arr = new JSONArray(str);
		JSONObject obj = new JSONObject();
		obj.put(email,userId);
		arr.put(obj);
		Utils.writeToFile(arr.toString(),filepath);
	}

}
