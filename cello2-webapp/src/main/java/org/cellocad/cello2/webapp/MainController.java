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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
		JSONObject json;
		String email;
		String password;
		try {
			json = new JSONObject(request);
			email = json.getString("email");
			password = json.getString("password");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}
		User user = User.findByCredentials(email, password);

		if(user != null) {
			ObjectId key = new ObjectId();
			Session session = new Session(user, key);
			Database.getInstance().save(session);

			JSONObject res = new JSONObject();
			res.put("token", key.toString());
			res.put("id", session.getId().toString());
			res.put("user", new JSONObject(user));
			res.put("session", new JSONObject(session));

			response.setStatus(HttpServletResponse.SC_OK);
			MainController.writeMessage(res.toString(),response);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void signup(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String name = "";
		String institution = "";
		String email;
		String password;

		try {
			JSONObject json = new JSONObject(request);
			try {
				institution = json.getString("institution");
			} catch (JSONException e) {}
			try {
				name = json.getString("name");
			} catch (JSONException e) {}
			email = json.getString("email");
			password = json.getString("password");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
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

			JSONObject res = new JSONObject();
			res.put("session", new JSONObject(session));
			res.put("token", key.toString());
			res.put("id", session.getId().toString());
			res.put("user", new JSONObject(user));

			response.setStatus(HttpServletResponse.SC_OK);
			MainController.writeMessage(res.toString(),response); 
		}
	}

	@ResponseBody
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public void reset(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
	}

	@ResponseBody
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public void forgot(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		String email;
		try {
			JSONObject json = new JSONObject(request);
			email = json.getString("email");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		User user = User.getUserByEmail(email);
		if(user != null) {
			String key = new ObjectId().toString();
			user.setForgotPasswordKey(key);
			response.setStatus(HttpServletResponse.SC_OK);
			// System.out.println("Send this key via email:" + key);
			MainController.writeMessage("Contact administrator.",response);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			MainController.writeMessage("User not found.",response);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public void updateUser(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
		String sessionId;
		String token;
		boolean advUser;
		String emailOptions;
		String[] registires;
		try {
			JSONObject json = new JSONObject(request);
			sessionId = json.getString("id");
			token = json.getString("token");
			advUser = json.getBoolean("advUser");
			emailOptions = json.getString("emailOption");
			registires = json.getJSONArray("registries").toString().replace("},{", " ,").split(" ");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		Session session = Session.findByCredentials(sessionId, token);
		if (session != null) {
			User user = Session.getUser(session);
			user.setAdvancedUser(advUser);
			user.setEmailOptions(emailOptions);
			user.setRegistries(registires);
			user.save();
			MainController.writeMessage("User updated.",response);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			MainController.writeMessage("User not found.",response);
		}

	}

	@ResponseBody
	@RequestMapping(value = "/projects", method = RequestMethod.POST)
	public void projects(@RequestBody String request, HttpServletResponse response) throws IOException {
		String sessionId;
		String token;
		try {
			JSONObject json = new JSONObject(request);
			sessionId = json.getString("id");
			token = json.getString("token");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		Session session = Session.findByCredentials(sessionId,token);

		if (session != null) {
			User user = Session.getUser(session);
			JSONArray projectList = ProjectUtils.getProjects(user.getId().toString());
			response.setStatus(HttpServletResponse.SC_OK);
			MainController.writeMessage(projectList.toString(),response);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
		String sessionId;
		String token;
		String name;
		try {
			JSONObject json = new JSONObject(request);
			sessionId = json.getString("id");
			token = json.getString("token");
			name = json.getString("name");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		Session session = Session.findByCredentials(sessionId,token);

		if (session != null) {
			User user = Session.getUser(session);
			String userId = user.getId().toString();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public void execute(@RequestBody String request, HttpServletResponse response) throws IOException {
		String sessionId;
		String token;
		String name;
		try {
			JSONObject json = new JSONObject(request);
			sessionId = json.getString("id");
			token = json.getString("token");
			name = json.getString("name");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		Session session = Session.findByCredentials(sessionId,token);

		if (session != null) {
			User user = Session.getUser(session);
			String userId = user.getId().toString();

			Project project;
			try {
				project = ProjectUtils.getProject(userId, name);
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
				getLogger().info("Executing job '" + name + "' for user '" + user.getEmail() + "'.");
				project.execute();
				getLogger().info("Completed job '" + name + "' for user '" + user.getEmail() + "'.");
				ResultsUtils.createResultsFile(userId,name);
			} catch (CelloWebException e) {
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				MainController.writeMessage(e.getMessage(),response);
				e.printStackTrace();
				return;
			}
			response.setStatus(HttpServletResponse.SC_OK);
			JSONObject res = new JSONObject();
			res.append("message", "Job completed.");
			MainController.writeMessage(res.toString(),response);
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/specify", method = RequestMethod.POST)
	public void specify(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException, URISyntaxException, IOException {
		String sessionId;
		String token;
		String name;
		JSONObject specification;
		try {
			JSONObject json = new JSONObject(request);
			sessionId = json.getString("id");
			token = json.getString("token");
			name = json.getString("name");
			specification = json.getJSONObject("specification");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		Session session = Session.findByCredentials(sessionId, token);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		JSONObject settings = specification.getJSONObject("settings");
		String application = settings.getString("application");
		if (application.equals("")) {
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			MainController.writeMessage("No application specified.",response);
			return;
		}

		User user = Session.getUser(session);
		String userId = user.getId().toString();
		Project project;
		try {
			project = ProjectUtils.getProject(userId,name);
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

		String directory = ProjectUtils.createProjectDirectory(userId,name);
		String jobId = ProjectUtils.newJobId();
		ProjectUtils.writeDetailsFile(userId,name,application,jobId);
		ProjectFactory projFactory = new ProjectFactory();
		project = projFactory.getProject(application,userId,jobId,directory);

		SpecificationFactory specFactory = new SpecificationFactory();
		Specification spec = null;
		try {
			getLogger().info("Building specification for project '" + name + "' for user '" + user.getEmail() + "'.");
			spec = specFactory.getSpecification(application,name,directory,specification);
			SpecificationUtils.writeSpecificationFile(userId,name,spec);
		} catch (CelloWebException ce) {
			getLogger().warn(ce);
			MainController.writeMessage(ce.getMessage(),response);
			return;
		} 
		project.setSpecification(spec);

		JSONObject res = new JSONObject();
		res.append("job_id", project.getJobId());
		response.setStatus(HttpServletResponse.SC_OK);
		MainController.writeMessage(res.toString(),response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/specification/{name}/{specification}", method = RequestMethod.POST)
	public void specification(@PathVariable(value="name") String name, @PathVariable(value="specification") String specification, @RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException, URISyntaxException, IOException {
		String sessionId;
		String token;
		try {
			JSONObject json = new JSONObject(request);
			sessionId = json.getString("id");
			token = json.getString("token");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		Session session = Session.findByCredentials(sessionId, token);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		User user = Session.getUser(session);
		String userId = user.getId().toString();
		Project project;
		try {
			project = ProjectUtils.getProject(userId,name);
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
	@RequestMapping(value = "/results/{name}", method = RequestMethod.POST)
	public void results(@PathVariable(value="name") String name, @RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
		String sessionId;
		String token;
		try {
			JSONObject json = new JSONObject(request);
			sessionId = json.getString("id");
			token = json.getString("token");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		Session session = Session.findByCredentials(sessionId, token);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		User user = Session.getUser(session);
		String userId = user.getId().toString();
		JSONArray results;
		try {
			results = ResultsUtils.getResults(userId,name);
		} catch (CelloWebException ce) {
			getLogger().warn(ce);
			MainController.writeMessage(ce.getMessage(),response);
			return;
		}

		response.setStatus(HttpServletResponse.SC_OK);
		MainController.writeMessage(results.toString(),response);
	}

	@ResponseBody
	@RequestMapping(value = "/results/{name}/{id}", method = RequestMethod.POST)
	public void result(@PathVariable(value="name") String name, @PathVariable(value="id") String id, @RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
		String sessionId;
		String token;
		try {
			JSONObject json = new JSONObject(request);
			sessionId = json.getString("id");
			token = json.getString("token");
		} catch (JSONException e) {
			getLogger().info("Error parsing request.", e);
			return;
		}

		Session session = Session.findByCredentials(sessionId, token);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		User user = Session.getUser(session);
		String userId = user.getId().toString();
		JSONArray results;
		try {
			results = ResultsUtils.getResults(userId,name);
		} catch (CelloWebException ce) {
			getLogger().warn(ce);
			MainController.writeMessage(ce.getMessage(),response);
			return;
		}
		JSONObject result = null;
		for (int i = 0; i < results.length(); i++) {
			JSONObject obj = results.getJSONObject(i);
			String resultName = "";
			String resultId = "";
			try {
				resultName = obj.getString("name");
				resultId = obj.getString("id");
			} catch (JSONException e) {
				getLogger().info("Error parsing results.", e);
				return;
			}
			if (resultId.equals(id)) {
				result = obj;
				break;
			}
		}
		if (result == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String filepath = ResultsUtils.getResultFilePath(userId,name,result.getString("name"));
		String str = "";
		try {
			str = Utils.getFileContentAsString(filepath);
		} catch (IOException e) {
			getLogger().error(e);
			return;
		}
		String type = "";
		if (result.getString("name").endsWith(".dot")) {
			type = "text/vnd.graphviz";
		}
		if (result.getString("name").endsWith(".csv")) {
			type = "text/css";
		}
		response.setContentType(type + ";charset=utf-8");
		response.setHeader("Content-Disposition","attachment; filename=\"" + result.getString("name") + "\"");
		writeMessage(str,response);
		response.setStatus(HttpServletResponse.SC_OK);
	}

}

