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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.database.Database;
import org.cellocad.cello2.webapp.project.ApplicationProject;
import org.cellocad.cello2.webapp.project.ApplicationProjectFactory;
import org.cellocad.cello2.webapp.results.ResultsUtils;
import org.cellocad.cello2.webapp.schemas.Session;
import org.cellocad.cello2.webapp.schemas.User;
import org.cellocad.cello2.webapp.specification.ApplicationSpecification;
import org.cellocad.cello2.webapp.specification.ApplicationSpecificationFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
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
@Controller
public class MainController {

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject(request);

        String email = json.getString("email");
        String password = json.getString("password");
        
        User user = User.findByCredentials(email, password);
        
        try {
            if(user != null) {
                ObjectId key = new ObjectId();
                Session session = new Session(user, key);
                Database.getInstance().save(session);

                PrintWriter writer;
                JSONObject res = new JSONObject();
                res.put("token", key.toString());
                res.put("id", session.getId().toString());
                res.put("user", new JSONObject(user));
                res.put("session", new JSONObject(session));

                response.setStatus(HttpServletResponse.SC_OK);
                writer = response.getWriter();
                writer.print(res.toString());
                writer.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
	}

	@ResponseBody
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
	public void signup(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject(request);

        String institution = json.getString("institution");
        String password = json.getString("password");
        String email = json.getString("email");
        String name = json.getString("name");
        
        try {
            if(User.userExists(email)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else {
                User user = new User(name,email,password,institution);
                Database.getInstance().save(user);
                ObjectId key = new ObjectId();
                Session session = new Session(user, key);
                Database.getInstance().save(session);
                
                ResultsUtils.createUserResultsDirectory(user);
                
                PrintWriter writer;
                JSONObject res = new JSONObject();
                res.put("session", new JSONObject(session));
                res.put("token", key.toString());
                res.put("id", session.getId().toString());
                res.put("user", new JSONObject(user));
                
                response.setStatus(HttpServletResponse.SC_OK);
                writer = response.getWriter();
                writer.print(res.toString());
                writer.flush();
            }
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
	}

	@ResponseBody
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public void reset(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
    }

	@ResponseBody
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public void forgot(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject(request);

        String email = json.getString("email");
        
        try {
            User user = User.getUserByEmail(email);
            if(user != null) {
                String key = new ObjectId().toString();
                user.setForgotPasswordKey(key);
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter writer;
                writer = response.getWriter();
                System.out.println("Send this key via email:" + key);
                writer.print("A password reset link has been emailed to you.");
                writer.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter writer;
                writer = response.getWriter();
                writer.print("User not found.");
                writer.flush();
            }
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
	}
	
	@ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void updateUser(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException {
        JSONObject json = new JSONObject(request);

        String sessionId = json.getString("id");
        String token = json.getString("token");
        boolean advUser = json.getBoolean("advUser");
        String emailOptions = json.getString("emailOption");
        String[] registires = json.getJSONArray("registries").toString().replace("},{", " ,").split(" ");
        
        try {
            Session session = Session.findByCredentials(sessionId, token);
            if(session != null) {
                User user = Session.getUser(session);
                user.setAdvancedUser(advUser);
                user.setEmailOptions(emailOptions);
                user.setRegistries(registires);
                user.save();

                PrintWriter writer;
                writer = response.getWriter();
                writer.print("User updated.");
                writer.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter writer;
                writer = response.getWriter();
                writer.print("User not found.");
                writer.flush();
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
    @ResponseBody
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public void projects(@RequestBody String request, HttpServletResponse response){
        JSONObject json = new JSONObject(request);
        
        String sessionId = json.getString("id");
        String token = json.getString("token");
        
        Session session = Session.findByCredentials(sessionId,token);
        
        if (session != null) {
            User user = Session.getUser(session);
            JSONArray projectList = ResultsUtils.getJSONProjectList(user.getId().toString());
            PrintWriter writer;
            try {
                response.setStatus(HttpServletResponse.SC_OK);
                writer = response.getWriter();
                writer.print(projectList.toString());
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public void execute(@RequestBody String request, HttpServletResponse response){
        JSONObject json = new JSONObject(request);
        
        String sessionId = json.getString("id");
        String token = json.getString("token");
        String name = json.getString("name");
        
        Session session = Session.findByCredentials(sessionId,token);
        
        if (session != null) {
            User user = Session.getUser(session);
            
            ApplicationProject project = ResultsUtils.getProject(user.getId().toString(), name);
            
            PrintWriter writer;
            
            if (project == null) {
            	try {
            		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writer = response.getWriter();
                    writer.print("Project not found.");
                    writer.flush();
            	} catch (IOException e) {
            		Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
            	}
            } else {
	            project.execute();
	            try {
	                response.setStatus(HttpServletResponse.SC_OK);
	                writer = response.getWriter();
	                writer.flush();
	            } catch (IOException e) {
	                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
	            }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/specification", method = RequestMethod.POST)
    public void specification(@RequestBody String request, HttpServletResponse response) throws UnsupportedEncodingException, URISyntaxException {
    	JSONObject json = new JSONObject(request);
        PrintWriter writer;
        
        String sessionId = json.getString("id");
        String token = json.getString("token");
        String name = json.getString("name");
        String type = json.getString("application");
        JSONObject specification = json.getJSONObject("specification");
        
        try {
            Session session = Session.findByCredentials(sessionId, token);
            if(session != null) {
                User user = Session.getUser(session);
                String userId = user.getId().toString();
                ApplicationProject project = ResultsUtils.getProject(userId,name);
                if (project != null) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    writer = response.getWriter();
                    JSONObject res = new JSONObject();
                    res.append("message", "A project with that name already exists.");
                    writer.write(res.toString());
                    writer.flush();
                } else {
                    try {
                    	ApplicationProjectFactory projFactory = new ApplicationProjectFactory();
                    	String directory = ResultsUtils.getProjectDirectory(userId,name);
                    	String jobId = ResultsUtils.newJobId();
                        project = projFactory.getProject(type,userId,jobId,directory);

                        ApplicationSpecificationFactory specFactory = new ApplicationSpecificationFactory();
                        ApplicationSpecification spec = specFactory.getSpecification(type,specification); 
                        
                        project.setSpecification(spec);
                        
                        response.setStatus(HttpServletResponse.SC_OK);
                        JSONObject res = new JSONObject();
                        res.append("job_id", project.getJobId());
                        res.append("message", "Project created.");
                        writer = response.getWriter();
                        writer.write(res.toString());
                        writer.flush();
                    }
//                    catch (SBOLValidationException | SBOLConversionException |InterruptedException ex) {
//                        response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
//                        writer = response.getWriter();
//                        writer.write(ex.toString());
//                        writer.flush();
//                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                    catch (Exception e) {
                    	e.printStackTrace();
                    }
                }
            }
        } catch(IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
