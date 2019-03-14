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
package org.cellocad.cello2.webapp.project;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.ApplicationUtils;
import org.cellocad.cello2.webapp.CelloWebException;
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.schemas.User;
import org.cellocad.cello2.webapp.specification.Specification;
import org.cellocad.cello2.webapp.specification.SpecificationUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-02-16
 *
 */
public class ProjectUtils {

	public static final String S_APPLICATION = "application";
	public static final String S_JOBID = "job_id";
	public static final String S_NAME = "name";
	public static final String S_TIMESTAMP = "timestamp";

	/**
	 * @param userId
	 * @return
	 */
	public static String getUserDirectory(String userId) {
		String rtn = "";
		rtn += ApplicationUtils.getProjectsDirectory() + Utils.getFileSeparator() + userId;
		return rtn;
	}

	/**
	 * @param user
	 */
	public static void createUserDirectory(User user) {
		String path = ProjectUtils.getUserDirectory(user.getId().toString());
		Utils.makeDirectory(path);
		ApplicationUtils.appendToUsersFile(user.getId().toString(),user.getEmail());
	}

	/**
	 * @param userId
	 * @return
	 */
	public static JsonNode getProjects(String userId) {
        ArrayNode rtn = null;
        ObjectMapper mapper = new ObjectMapper();
        rtn = mapper.createArrayNode();
		String filepath = ProjectUtils.getUserDirectory(userId) + Utils.getFileSeparator();
		File root = new File(filepath);
		File[] list = root.listFiles();
		if (list != null) {
			for (File f : list) {
				String project = f.getAbsolutePath();
				if (!project.endsWith(Utils.getFileSeparator())) {
					project += Utils.getFileSeparator();
				}
				try {
					JsonNode obj = mapper.readTree(new File(project + "details.json"));
					rtn.add(obj);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rtn;
	}

	/**
	 * @param userId
	 * @param project
	 * @return
	 * @throws CelloWebException
	 */
	public static Project getProject(String userId, String project) throws CelloWebException {
		Project rtn = null;
		ProjectFactory projectFactory = new ProjectFactory();
		JsonNode projects = ProjectUtils.getProjects(userId);
		String name = null;
		String type = null;
		String jobId = null;
		String directory = null;
		for (JsonNode json : projects) {
			try {
				name = json.get("name").asText();
				type = json.get("application").asText();
				jobId = json.get("job_id").asText();
			} catch (NullPointerException e) {
				throw new CelloWebException("Error with project.");
			}
			if (name.equals(project)) {
				directory = ProjectUtils.getProjectDirectory(userId,project);
				rtn = projectFactory.getProject(type,userId,jobId,directory);
				break;
			}        	
		}
		if (rtn != null) {
			Specification specification = SpecificationUtils.getSpecification(type,userId,project);
			rtn.setSpecification(specification);
		}
		return rtn;
	}

	public static String getProjectApplication(String userId, String name) throws CelloWebException {
		String rtn = "";
		String filepath = ProjectUtils.getDetailsFile(userId,name);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = null;
		try {
			node = mapper.readTree(new File(filepath));
			rtn = node.get(S_APPLICATION).asText();
		} catch (Exception e) {
			throw new CelloWebException("Error with project.");
		}
		return rtn;
	}

	/**
	 * @return
	 */
	public static String newJobId() {
		String rtn = null;
		rtn = (new ObjectId()).toString();
		return rtn;
	}

	/**
	 * @param userId
	 * @param name
	 * @return
	 */
	public static String getProjectDirectory(String userId, String name) {
		String rtn = "";
		rtn += ProjectUtils.getUserDirectory(userId);
		rtn += Utils.getFileSeparator();
		rtn += name;
		return rtn;
	}

	/**
	 * @param userId
	 * @param name
	 * @return
	 */
	public static String createProjectDirectory(String userId, String name) {
		String rtn = ProjectUtils.getProjectDirectory(userId,name);
		Utils.makeDirectory(rtn);
		String details = getDetailsFile(userId,name);
		Utils.createFile(details);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = mapper.createObjectNode();
		Utils.writeToFile(obj.toString(),details);
		return rtn;
	}

	/**
	 * @param userId
	 * @param name
	 * @return
	 */
	public static String getDetailsFile(String userId, String name) {
		String rtn = "";
		rtn += ProjectUtils.getProjectDirectory(userId,name);
		rtn += Utils.getFileSeparator();
		rtn += "details.json";
		return rtn;
	}

	/**
	 * @param userId
	 * @param name
	 * @param application
	 * @param jobId
	 * @throws CelloWebException 
	 */
	public static void writeDetailsFile(String userId, String name, String application, String jobId) throws CelloWebException {
		String details = getDetailsFile(userId,name);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode obj = mapper.createObjectNode();
		obj.put(S_APPLICATION,application);
		obj.put(S_JOBID,jobId);
		obj.put(S_NAME,name);
		obj.put(S_TIMESTAMP, (new Date()).toString());
		String json;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			Utils.writeToFile(json,details);
		} catch (JsonProcessingException e) {
			throw new CelloWebException("Error writing project details.");
		}
	}

}
