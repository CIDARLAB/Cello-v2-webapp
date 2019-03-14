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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.database.Database;
import org.cellocad.cello2.webapp.project.Project;
import org.cellocad.cello2.webapp.project.ProjectFactory;
import org.cellocad.cello2.webapp.project.ProjectUtils;
import org.cellocad.cello2.webapp.results.ResultsUtils;
import org.cellocad.cello2.webapp.schemas.Session;
import org.cellocad.cello2.webapp.schemas.User;
import org.cellocad.cello2.webapp.specification.Specification;
import org.cellocad.cello2.webapp.specification.SpecificationFactory;
import org.cellocad.cello2.webapp.specification.SpecificationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * 
 * @author Timothy Jones
 * 
 * @date 2019-02-15
 *
 */
@CrossOrigin(origins="*")
@Controller
public class MainController {

	private static Logger getLogger() {
		return LogManager.getLogger(MainController.class);
	}

	@PostConstruct
	public void init() {
		String projects = ApplicationUtils.getProjectsDirectory();
		if (!Utils.isValidFilepath(projects)) {
			Utils.makeDirectory(projects);
		}
		String users = ApplicationUtils.getUsersFile();
		if(!Utils.isValidFilepath(users)){
			ApplicationUtils.createUsersFile();
		}
	}

	private static void writeMessage(String message, HttpServletResponse response) {
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(message);
			writer.flush();
		} catch (IOException e) {
			getLogger().error(e);
		}
	}
	
	private static Session auth(JsonNode request) {
		String sessionId;
		String token;
		try {
			sessionId = request.get("session").asText("");
			token = request.get("token").asText("");
		} catch (NullPointerException e) { return null; }
		if (sessionId.isEmpty() || token.isEmpty())
			return null;
		Session session = Session.findByCredentials(sessionId, token);
		return session;
	}
	
	private static String userId(JsonNode request) {
		Session session = auth(request);
		if (session == null)
			return null;
		User user = Session.getUser(session);
		String rtn = user.getId().toString();
		return rtn;
	}
	
	private static void deauth(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(
			@RequestBody JsonNode request,
			HttpServletResponse response) {
		String email;
		String password;
		try {
			email = request.get("email").asText();
			password = request.get("password").asText();
		} catch (NullPointerException e) { return; }
		User user = User.findByCredentials(email, password);
		if (user == null) {
			deauth(response);
			return;
		}
		ObjectId key = new ObjectId();
		Session session = new Session(user, key);
		Database.getInstance().save(session);

		// response
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
		json.put("session", session.getId().toString());
		json.put("token", key.toString());
		response.setStatus(HttpServletResponse.SC_OK);
		MainController.writeMessage(json.toString(),response);
	}

	@ResponseBody
	@PostMapping(value = "/signup")
	public void signup(
			@RequestBody JsonNode request,
			HttpServletResponse response) {
		String name = "";
		String institution = "";
		String email;
		String password;
		try {
			try {
				institution = request.get("institution").asText();
			} catch (NullPointerException e) {}
			try {
				name = request.get("name").asText();
			} catch (NullPointerException e) {}
			email = request.get("email").asText();
			password = request.get("password").asText();
		} catch (NullPointerException e) {
			return;
		}

		if (User.userExists(email)) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
		} else {
			User user = new User(name,email,password,institution);
			Database.getInstance().save(user);
			ObjectId key = new ObjectId();
			Session session = new Session(user, key);
			Database.getInstance().save(session);

			ProjectUtils.createUserDirectory(user);
			
			// response
			ObjectMapper mapper = new ObjectMapper();
	        ObjectNode json = mapper.createObjectNode();
			json.put("token", key.toString());
			json.put("session", session.getId().toString());
			response.setStatus(HttpServletResponse.SC_OK);
			MainController.writeMessage(json.toString(),response);
		}
	}

	@ResponseBody
	@PostMapping(value = "/projects")
	public void projects(
			@RequestBody JsonNode request,
			HttpServletResponse response) throws IOException {
		// auth
		String userId = userId(request);
		if (userId == null) {
			deauth(response);
			return;
		}
		JsonNode projectList = ProjectUtils.getProjects(userId);
		response.setStatus(HttpServletResponse.SC_OK);
		writeMessage(projectList.toString(),response);
	}

	@ResponseBody
	@PostMapping(value = "/delete/{project}")
	public void delete(
			@PathVariable(value="project") String projectName,
			@RequestBody JsonNode request,
			HttpServletResponse response) {
		// auth
		Session session = auth(request);
		if (session == null) {
			deauth(response);
			return;
		}
		User user = Session.getUser(session);
		String userId = user.getId().toString();
	}

	@ResponseBody
	@PostMapping(value = "/execute/{project}")
	public void execute(
			@PathVariable(value="project") String projectName,
			@RequestBody JsonNode request,
			HttpServletResponse response) {
		// auth
		Session session = auth(request);
		if (session == null) {
			deauth(response);
			return;
		}
		User user = Session.getUser(session);
		String userId = user.getId().toString();

		Project project;
		try {
			project = ProjectUtils.getProject(userId, projectName);
		} catch (CelloWebException e) {
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			MainController.writeMessage(e.getMessage(),response);
			return;
		}   
		if (project == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			MainController.writeMessage("Project not found.",response);
			return;
		}
		try {
			getLogger().info("Executing job '" + projectName + "' for user '" + user.getEmail() + "'.");
			project.execute();
			getLogger().info("Completed job '" + projectName + "' for user '" + user.getEmail() + "'.");
			ResultsUtils.createResultsFile(userId,projectName);
		} catch (CelloWebException e) {
			getLogger().warn(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			MainController.writeMessage(e.getMessage(),response);
			return;
		}

		// response
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
		json.put("message", "Job completed.");
		response.setStatus(HttpServletResponse.SC_OK);
		MainController.writeMessage(json.toString(),response);
	}

	@ResponseBody
	@PostMapping(value = "/specify/{project}")
	public void specify(
			@PathVariable(value="project") String projectName,
			@RequestBody JsonNode request, 
			HttpServletResponse response) {
		// auth
		Session session = auth(request);
		if (session == null) {
			deauth(response);
			return;
		}
		User user = Session.getUser(session);
		String userId = user.getId().toString();

		// validation
		JsonNode specification = request.get("specification");
		if (specification == null) return;
		JsonNode settings = specification.get("settings");
		if (settings == null) return;
		JsonNode application = settings.get("application");
		if (application == null || application.asText("").equals("")) {
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			MainController.writeMessage("No application specified.",response);
			return;
		}

		// project
		Project project;
		try {
			project = ProjectUtils.getProject(userId,projectName);
		} catch (CelloWebException e) {
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			MainController.writeMessage(e.getMessage(),response);
			return;
		}
		if (project != null) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			MainController.writeMessage("A project with that name already exists.",response);
			return;
		}
		String directory = ProjectUtils.createProjectDirectory(userId,projectName);
		String jobId = ProjectUtils.newJobId();
		try {
			ProjectUtils.writeDetailsFile(userId,projectName,application.asText(),jobId);
		} catch (CelloWebException e) {
			getLogger().warn(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			MainController.writeMessage(e.getCause().getLocalizedMessage(),response);
			return;
		}
		ProjectFactory projFactory = new ProjectFactory();
		project = projFactory.getProject(application.asText(),userId,jobId,directory);

		// response preparation
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();

		// specification
		SpecificationFactory specFactory = new SpecificationFactory();
		Specification spec = null;
		try {
			getLogger().info("Building specification for project '" + projectName + "' for user '" + user.getEmail() + "'.");
			spec = specFactory.getSpecification(application.asText(),projectName,directory,specification);
			SpecificationUtils.writeSpecificationFile(userId,projectName,spec);
		} catch (CelloWebException e) {
			getLogger().warn(e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			MainController.writeMessage(e.getCause().getLocalizedMessage(),response);
			return;
		} 
		project.setSpecification(spec);

		// response
		json.put("job_id", project.getJobId());
		response.setStatus(HttpServletResponse.SC_OK);
		MainController.writeMessage(json.toString(),response);
	}
	
	@ResponseBody
	@PostMapping(value = "/specification/{project}/{specification}")
	public void specification(
			@PathVariable(value="project") String projectName,
			@PathVariable(value="specification") String specification, 
			@RequestBody JsonNode request, 
			HttpServletResponse response) throws UnsupportedEncodingException, URISyntaxException, IOException {
		Session session = auth(request);
		if (session == null) {
			deauth(response);
			return;
		}

		User user = Session.getUser(session);
		String userId = user.getId().toString();
		Project project;
		try {
			project = ProjectUtils.getProject(userId,projectName);
		} catch (CelloWebException e) {
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			MainController.writeMessage(e.getMessage(),response);
			return;
		}
		if (project == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		Specification spec = project.getSpecification();
		if (spec == null) {
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			return;
		}
		
		String filepath = "";
		String type = "";
		switch (specification) {
		case "library":
			filepath = spec.getTargetDataFile();
			type = "application/json";
			break;
		case "constraints":
			filepath = spec.getNetlistConstraintFile();
			type = "application/json";
			break;
		case "settings":
			filepath = spec.getOptionsFile();
			type = "application/json";
			break;
		case "verilog":
			filepath = spec.getVerilogFile();
			break;
		default:
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String str = Utils.getFileContentAsString(filepath);
	}

	@ResponseBody
	@PostMapping(value = "/results/{project}")
	public void results(
			@PathVariable(value="project") String projectName,
			@RequestBody JsonNode request,
			HttpServletResponse response) {
		Session session = auth(request);
		if (session == null) {
			deauth(response);
			return;
		}

		// response preparation
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();

		User user = Session.getUser(session);
		String userId = user.getId().toString();
		JsonNode results;
		try {
			results = ResultsUtils.getResults(userId,projectName);
		} catch (CelloWebException e) {
			getLogger().warn(e);
			json.put("message", e.getCause().getLocalizedMessage());
			MainController.writeMessage(e.getMessage(),response);
			return;
		}

		response.setStatus(HttpServletResponse.SC_OK);
		MainController.writeMessage(results.toString(),response);
	}

	@ResponseBody
	@PostMapping(value = "/results/{project}/{file}")
	public void result(
			@PathVariable(value="project") String projectName,
			@PathVariable(value="file") String fileName,
			@RequestBody JsonNode request,
			HttpServletResponse response) {
		Session session = auth(request);
		if (session == null) {
			deauth(response);
			return;
		}
		User user = Session.getUser(session);
		String userId = user.getId().toString();
		String filepath = ResultsUtils.getResultFilePath(userId,projectName,fileName);
		if (!Utils.isValidFilepath(filepath)) {
			response.setStatus(HttpServletResponse.SC_OK);
			writeMessage("Not found.",response);
			return;
		}
		String str = "";
		try {
			str = Utils.getFileContentAsString(filepath);
		} catch (IOException e) {
			getLogger().error(e);
			return;
		}
		String type = "";
		if (fileName.endsWith(".dot")) {
			type = "text/vnd.graphviz";
		}
		if (fileName.endsWith(".csv")) {
			type = "text/csv";
		}
		response.setContentType(type + ";charset=utf-8");
		response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
		response.setStatus(HttpServletResponse.SC_OK);
		writeMessage(str,response);
	}

}

