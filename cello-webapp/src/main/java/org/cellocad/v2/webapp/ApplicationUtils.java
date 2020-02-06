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
package org.cellocad.v2.webapp;

import java.io.File;
import java.io.IOException;

import org.cellocad.v2.webapp.common.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode arr = mapper.createArrayNode();
		Utils.writeToFile(arr.toString(),filepath);
	}

	/**
	 * 
	 */
	public static void appendToUsersFile(String userId, String email) {
		String filepath = getUsersFile();
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode node = null;
		try {
			node = (ArrayNode) mapper.readTree(new File(filepath));
		} catch (IOException e) {
			throw new RuntimeException("Error with file.");
		}
		ObjectNode obj = mapper.createObjectNode();
		obj.put(email,userId);
		Utils.writeToFile(node.toString(),filepath);
	}

}
