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
package org.cellocad.cello2.webapp.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.exception.CelloWebException;
import org.cellocad.cello2.webapp.exception.ProjectException;
import org.cellocad.cello2.webapp.exception.ResourceNotFoundException;
import org.cellocad.cello2.webapp.project.Project;
import org.cellocad.cello2.webapp.project.ProjectFactory;
import org.cellocad.cello2.webapp.project.ProjectRepository;
import org.cellocad.cello2.webapp.results.Result;
import org.cellocad.cello2.webapp.specification.Specification;
import org.cellocad.cello2.webapp.user.ApplicationUser;
import org.cellocad.cello2.webapp.user.ApplicationUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-18
 *
 */
@Controller
public class ProjectController {
	
	private ApplicationUserRepository applicationUserRepository;
	private ProjectRepository projectRepository;
	
    public ProjectController(ApplicationUserRepository applicationUserRepository, ProjectRepository projectRepository) {
    	this.applicationUserRepository = applicationUserRepository;
    	this.projectRepository = projectRepository;
    }
	
	private static Logger getLogger() {
		return LogManager.getLogger(ProjectController.class);
	}

	@ResponseBody
	@GetMapping("/projects")
	public Collection<Project> projects(ApplicationUser user) {
		return user.getProjects();
	}

	@ResponseBody
	@PostMapping("/project/{name}/specify")
	public void specify(ApplicationUser user, @PathVariable(value="name") String name, @RequestBody Specification specification) {
		Iterator<Project> it = user.getProjects().iterator();
		while (it.hasNext()) {
			Project p = it.next();
			if (p.getName().equals(name)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "A project with that name already exists.");
			}
		};
		// project
		ProjectFactory factory = new ProjectFactory();
		Project project;
		try {
			project = factory.getProject(user,name,specification);
		} catch (ProjectException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
		}
		projectRepository.save(project);
		user.getProjects().add(project);
		applicationUserRepository.save(user);
	}

	@ResponseBody
	@GetMapping("/project/{name}/execute")
	public void execute(ApplicationUser user, @PathVariable(value="name") String name) throws CelloWebException, ResourceNotFoundException {
		Project project = Utils.findCObjectByName(name,user.getProjects());
		getLogger().info(String.format("Executing job '%s' for user '%s'.", name, user.getUsername()));
		project.execute();
		getLogger().info(String.format("Completed job '%s' for user '%s'.", name, user.getUsername()));
	}

	@ResponseBody
	@GetMapping("/project/{name}/delete")
	public void delete(ApplicationUser user, @PathVariable(value="name") String name) throws IOException {
		Iterator<Project> it = user.getProjects().iterator();
		while (it.hasNext()) {
			Project p = it.next();
			if (p.getName().equals(name)) {
				p.delete();
				it.remove();
			}
		}
	}

	@ResponseBody
	@GetMapping("/project/{name}/results")
	public Collection<Result> results(ApplicationUser user, @PathVariable(value="name") String name) throws ResourceNotFoundException {
		Project project = Utils.findCObjectByName(name,user.getProjects());
		return project.getResults();
	}

	@ResponseBody
	@GetMapping("/project/{name}/result/{result}")
	public Result result(ApplicationUser user, @PathVariable(value="name") String name, @PathVariable(value="result") String result) throws ResourceNotFoundException {
		Result rtn = null;
		Project project = Utils.findCObjectByName(name,user.getProjects());
		rtn = Utils.findCObjectByName(result,project.getResults()); 
		return rtn;
	}

}
