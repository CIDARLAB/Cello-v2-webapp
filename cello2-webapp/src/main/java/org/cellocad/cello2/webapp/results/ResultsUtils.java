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
package org.cellocad.cello2.webapp.results;

import java.io.File;

import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.project.ApplicationProject;
import org.cellocad.cello2.webapp.project.ApplicationProjectFactory;
import org.cellocad.cello2.webapp.schemas.User;
import org.cellocad.cello2.webapp.specification.ApplicationSpecification;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public class ResultsUtils {
	
    public static String getResultsFilepath() {
        String rtn = "";
        rtn = Utils.getFilepath() + "results";
        if (!Utils.isValidFilepath(rtn)) {
            Utils.makeDirectory(rtn);
        } 
        JSONObject obj = new JSONObject();
        String users = rtn + Utils.getFileSeparator() + "users.json";
        if(!Utils.isValidFilepath(users)){
            Utils.writeToFile(obj.toString(),users);
        }
        return rtn;
    }
    
    public static String getUserResultsDirectory(String userId) {
    	String rtn = "";
    	rtn += ResultsUtils.getResultsFilepath() + Utils.getFileSeparator() + userId;
    	return rtn;
    }
	
    public static JSONArray getJSONProjectList(String userId) {
        JSONArray rtn = null;
        rtn = new JSONArray();
        String filepath = ResultsUtils.getUserResultsDirectory(userId) + Utils.getFileSeparator();
        File root = new File(filepath);
        File[] list = root.listFiles();
        for (File f : list) {
        	String project = f.getAbsolutePath();
        	if (!project.endsWith("" + Utils.getFileSeparator())) {
        		project += Utils.getFileSeparator();
        	}
        	rtn.put(new JSONObject(Utils.getFileContentAsString(project + "details.json")));
        }
        return rtn;
    }
    
    public static ApplicationProject getProject(String userId, String name) {
    	ApplicationProject rtn = null;
    	ApplicationProjectFactory factory = new ApplicationProjectFactory();
        JSONArray projects = ResultsUtils.getJSONProjectList(userId);
        for (Object obj : projects) {
            JSONObject json = (JSONObject) obj;
            if (json.get("name").equals(name)) {
            	String type = json.getString("type");
            	String jobId = json.getString("job_id");
            	String directory = json.getString("directory");
            	rtn = factory.getProject(type,userId,jobId,directory);
            }
        }
        return rtn;
    }
    
    public static ApplicationSpecification getSpecification(ApplicationProject project) {
    	ApplicationSpecification rtn = null;
        return rtn;
    }
    
    public static void createUserResultsDirectory(User user) {
        String path = "";
        path += ResultsUtils.getResultsFilepath();
        path += Utils.getFileSeparator();
        path += user.getId().toString();
        Utils.makeDirectory(path);
        String userlist = "";
        userlist += ResultsUtils.getResultsFilepath();
        userlist += Utils.getFileSeparator();
        userlist += "users.json";
        JSONObject users = new JSONObject(Utils.getFileContentAsString(userlist));
        users.put(user.getEmail(),user.getId().toString());
        Utils.writeToFile(users.toString(),userlist);
    }

	/**
	 * @return
	 */
	public static String getProjectDirectory(String userId, String name) {
		String rtn = "";
		rtn += ResultsUtils.getUserResultsDirectory(userId);
		rtn += Utils.getFileSeparator();
		rtn += name;
		return rtn;
	}
	
	public static String newJobId() {
		String rtn = null;
		rtn = (new ObjectId()).toString();
		return rtn;
	}

}
