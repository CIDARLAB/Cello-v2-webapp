/*
 * Copyright (C) 2019 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cellocad.v2.webapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.v2.results.common.Result;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.exception.ProjectException;
import org.cellocad.v2.webapp.exception.ResourceNotFoundException;
import org.cellocad.v2.webapp.project.Project;
import org.cellocad.v2.webapp.project.ProjectFactory;
import org.cellocad.v2.webapp.project.ProjectRepository;
import org.cellocad.v2.webapp.specification.Specification;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.cellocad.v2.webapp.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for projects.
 *
 * @author Timothy Jones
 * @date 2019-03-18
 */
@RestController
public class ProjectController {

  @Autowired private ApplicationUserRepository applicationUserRepository;
  @Autowired private ProjectRepository projectRepository;

  public ProjectController(
      final ApplicationUserRepository applicationUserRepository,
      final ProjectRepository projectRepository) {
    this.applicationUserRepository = applicationUserRepository;
    this.projectRepository = projectRepository;
  }

  private static Logger getLogger() {
    return LogManager.getLogger(ProjectController.class);
  }

  static Collection<Result> getResults(final Project project) {
    Collection<Result> rtn = null;
    try {
      rtn = project.getResults();
    } catch (IOException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Unable to load project results.", e);
    }
    return rtn;
  }

  static Result getResult(final String file, final Project project) {
    Result rtn = null;
    Collection<Result> results = getResults(project);
    for (Result r : results) {
      if (r.getFile().getName().equals(file)) {
        rtn = r;
        break;
      }
    }
    if (rtn == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find result.");
    }
    return rtn;
  }

  static Project getProject(final String name, final ApplicationUser user) {
    Project rtn = null;
    final Iterator<Project> it = user.getProjects().iterator();
    while (it.hasNext()) {
      final Project p = it.next();
      if (p.getName().equals(name)) {
        rtn = p;
        break;
      }
    }
    if (rtn == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find project.");
    }
    return rtn;
  }

  @ResponseBody
  @GetMapping("/projects")
  public Collection<Project> projects(final ApplicationUser user) {
    return user.getProjects();
  }

  /**
   * Specify a new project.
   *
   * @param user The user to whom the project belongs.
   * @param name The name of the project.
   * @param specification The project specification.
   */
  @ResponseBody
  @PostMapping("/project/{name}/specify")
  public void specify(
      final ApplicationUser user,
      @PathVariable(value = "name") final String name,
      @RequestBody final Specification specification) {
    final Iterator<Project> it = user.getProjects().iterator();
    while (it.hasNext()) {
      final Project p = it.next();
      if (p.getName().equals(name)) {
        throw new ResponseStatusException(
            HttpStatus.CONFLICT, "A project with that name already exists.");
      }
    }
    // project
    final ProjectFactory factory = new ProjectFactory();
    Project project;
    try {
      project = factory.getProject(user, name, specification);
    } catch (final ProjectException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
    projectRepository.insert(project);
    user.getProjects().add(project);
    applicationUserRepository.save(user);
  }

  /**
   * Execute a project.
   *
   * @param user The user to whom the project belongs.
   * @param name The name of the project.
   */
  @ResponseBody
  @GetMapping("/project/{name}/execute")
  public void execute(final ApplicationUser user, @PathVariable(value = "name") final String name) {
    final Project project = getProject(name, user);
    ProjectController.getLogger()
        .info(String.format("Executing job '%s' for user '%s'.", name, user.getUsername()));
    try {
      project.execute();
    } catch (CelloWebException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to execute project.", e);
    }
    ProjectController.getLogger()
        .info(String.format("Completed job '%s' for user '%s'.", name, user.getUsername()));
  }

  /**
   * Delete a project.
   *
   * @param user The user to whom the project belongs.
   * @param name The name of the project.
   * @throws IOException Unable to delete project.
   */
  @ResponseBody
  @GetMapping("/project/{name}/delete")
  public void delete(final ApplicationUser user, @PathVariable(value = "name") final String name)
      throws IOException {
    final Iterator<Project> it = user.getProjects().iterator();
    while (it.hasNext()) {
      final Project p = it.next();
      if (p.getName().equals(name)) {
        p.delete();
        it.remove();
      }
    }
  }

  /**
   * Get all results in a project.
   *
   * @param user The user to whom the project belongs.
   * @param name The name of the project.
   * @return All the results in a project.
   * @throws ResourceNotFoundException Unable to find project or load results.
   */
  @ResponseBody
  @GetMapping("/project/{name}/results")
  public Collection<Result> results(
      final ApplicationUser user, @PathVariable(value = "name") final String name)
      throws ResourceNotFoundException {
    Collection<Result> rtn = null;
    final Project project = getProject(name, user);
    rtn = getResults(project);
    return rtn;
  }

  /**
   * Get result metadata.
   *
   * @param user The user to whom the project belongs.
   * @param name The name of the project.
   * @param file The filename of the result.
   * @return The result metadata.
   * @throws ResourceNotFoundException Could not find the result.
   */
  @ResponseBody
  @GetMapping("/project/{name}/result/{file}")
  public Result result(
      final ApplicationUser user,
      @PathVariable(value = "name") final String name,
      @PathVariable(value = "file") final String file)
      throws ResourceNotFoundException {
    Result rtn = null;
    final Project project = getProject(name, user);
    rtn = getResult(file, project);
    return rtn;
  }

  /**
   * Download a project result.
   *
   * @param user The user to whom the project belongs.
   * @param name The name of the project.
   * @param file The filename of the result.
   * @return A byte array of the result content.
   * @throws ResourceNotFoundException Could not find the result.
   * @throws IOException Could not read result.
   */
  @ResponseBody
  @GetMapping(
      value = "/project/{name}/result/{file}/download",
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public byte[] download(
      final ApplicationUser user,
      @PathVariable(value = "name") final String name,
      @PathVariable(value = "file") final String file)
      throws ResourceNotFoundException, IOException {
    final Project project = getProject(name, user);
    final Result r = getResult(file, project);
    final InputStream is = new FileInputStream(r.getFile());
    return IOUtils.toByteArray(is);
  }
}
