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

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.v2.common.file.zip.utils.ZipUtils;
import org.cellocad.v2.results.common.Result;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.exception.ProjectException;
import org.cellocad.v2.webapp.exception.ResourceNotFoundException;
import org.cellocad.v2.webapp.project.Project;
import org.cellocad.v2.webapp.project.ProjectFactory;
import org.cellocad.v2.webapp.project.ProjectRepository;
import org.cellocad.v2.webapp.resource.ApplicationResourceUtils;
import org.cellocad.v2.webapp.resource.UserResourceUtils;
import org.cellocad.v2.webapp.resource.library.InputSensorFileDescriptor;
import org.cellocad.v2.webapp.resource.library.OutputDeviceFileDescriptor;
import org.cellocad.v2.webapp.resource.library.UserConstraintsFileDescriptor;
import org.cellocad.v2.webapp.specification.Specification;
import org.cellocad.v2.webapp.specification.library.TargetDataLibraryResource;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.cellocad.v2.webapp.user.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/projects",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<Project> getProjects(final ApplicationUser user) {
    return user.getProjects();
  }

  /**
   * Specify a new project.
   *
   * @param user The user to whom the project belongs.
   * @param specification The project specification.
   * @throws IOException Unable to read target data metadata.
   * @throws JsonProcessingException Unable to parse target data metadata.
   */
  @RequestMapping(method = RequestMethod.POST, value = "/projects")
  public void createProject(
      final ApplicationUser user, @RequestBody final Specification specification)
      throws JsonProcessingException, IOException {
    final Iterator<Project> it = user.getProjects().iterator();
    while (it.hasNext()) {
      final Project p = it.next();
      if (p.getName().equals(specification.getName())) {
        throw new ResponseStatusException(
            HttpStatus.CONFLICT, "A project with that name already exists.");
      }
    }
    // This is too long.
    if (specification.getLibraryResource() instanceof TargetDataLibraryResource) {
      final TargetDataLibraryResource library =
          (TargetDataLibraryResource) specification.getLibraryResource();
      Collection<InputSensorFileDescriptor> isfDescriptors =
          UserResourceUtils.getAllInputSensorFileDescriptors(user);
      String isfPath = null;
      for (InputSensorFileDescriptor d : isfDescriptors) {
        if (d.getFile().equals(library.getInputSensorFile().getName())) {
          if (d.getIsPrivate()) {
            isfPath =
                Paths.get(UserResourceUtils.getInputSensorFileResourcesDirectory(user), d.getFile())
                    .toString();
            break;
          } else {
            isfPath =
                Paths.get(
                        ApplicationResourceUtils.getInputSensorFileResourcesDirectory(),
                        d.getFile())
                    .toString();
            break;
          }
        }
      }
      if (isfPath == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Input sensor file not found.");
      }
      File isfFile = new File(isfPath);
      library.setInputSensorFile(isfFile);
      Collection<OutputDeviceFileDescriptor> odfDescriptors =
          UserResourceUtils.getAllOutputDeviceFileDescriptors(user);
      String odfPath = null;
      for (OutputDeviceFileDescriptor d : odfDescriptors) {
        if (d.getFile().equals(library.getOutputDeviceFile().getName())) {
          if (d.getIsPrivate()) {
            odfPath =
                Paths.get(
                        UserResourceUtils.getOutputDeviceFileResourcesDirectory(user), d.getFile())
                    .toString();
            break;
          } else {
            odfPath =
                Paths.get(
                        ApplicationResourceUtils.getOutputDeviceFileResourcesDirectory(),
                        d.getFile())
                    .toString();
            break;
          }
        }
      }
      if (odfPath == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Output device file not found.");
      }
      File odfFile = new File(odfPath);
      library.setOutputDeviceFile(odfFile);
      Collection<UserConstraintsFileDescriptor> ucfDescriptors =
          UserResourceUtils.getAllUserConstraintsFileDescriptors(user);
      String ucfPath = null;
      for (UserConstraintsFileDescriptor d : ucfDescriptors) {
        if (d.getFile().equals(library.getUserConstraintsFile().getName())) {
          if (d.getIsPrivate()) {
            ucfPath =
                Paths.get(
                        UserResourceUtils.getUserConstraintsFileResourcesDirectory(user),
                        d.getFile())
                    .toString();
            break;
          } else {
            ucfPath =
                Paths.get(
                        ApplicationResourceUtils.getUserConstraintsFileResourcesDirectory(),
                        d.getFile())
                    .toString();
            break;
          }
        }
      }
      if (ucfPath == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User constraints file not found.");
      }
      File ucfFile = new File(ucfPath);
      library.setUserConstraintsFile(ucfFile);
    }
    // project
    final ProjectFactory factory = new ProjectFactory();
    Project project;
    try {
      project = factory.getProject(user, specification);
    } catch (final ProjectException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error creating project.", e);
    }
    projectRepository.insert(project);
    user.getProjects().add(project);
    applicationUserRepository.save(user);
    ProjectController.getLogger()
        .info(
            String.format(
                "Executing job '%s' for user '%s'.", project.getName(), user.getUsername()));
    try {
      project.execute();
    } catch (CelloWebException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to execute project.", e);
    }
    ProjectController.getLogger()
        .info(
            String.format(
                "Completed job '%s' for user '%s'.", project.getName(), user.getUsername()));
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

  /**
   * Downloads a zip archive containing all files associated with a project.
   *
   * @param user The user to whom the project belongs.
   * @param projectName The name of the project.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/projects/{project-name}",
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public byte[] getProjectArchive(
      final ApplicationUser user, @PathVariable(value = "project-name") final String projectName) {
    final Project project = getProject(projectName, user);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      ZipUtils.zipDirectory(baos, Paths.get(project.getFilepath()));
    } catch (IOException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Unable to zip project directory.", e);
    }
    return baos.toByteArray();
  }

  /**
   * Delete a project.
   *
   * @param user The user to whom the project belongs.
   * @param projectName The name of the project.
   * @throws IOException Unable to delete project.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/projects/{project-name}")
  public void deleteProject(
      final ApplicationUser user, @PathVariable(value = "project-name") final String projectName)
      throws IOException {
    final Iterator<Project> it = user.getProjects().iterator();
    while (it.hasNext()) {
      final Project p = it.next();
      if (p.getName().equals(projectName)) {
        p.delete();
        it.remove();
      }
    }
  }

  static Result getProjectResult(final String file, final Project project) {
    Result rtn = null;
    Collection<Result> results = getProjectResults(project);
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

  static Collection<Result> getProjectResults(final Project project) {
    Collection<Result> rtn = null;
    try {
      rtn = project.getResults();
    } catch (IOException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Unable to load project results.", e);
    }
    return rtn;
  }

  /**
   * Get all results in a project.
   *
   * @param user The user to whom the project belongs.
   * @param projectName The name of the project.
   * @return All the results in a project.
   * @throws ResourceNotFoundException Unable to find project or load results.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/projects/{project-name}/results",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<Result> getProjectResults(
      final ApplicationUser user, @PathVariable(value = "project-name") final String projectName)
      throws ResourceNotFoundException {
    Collection<Result> rtn = null;
    final Project project = getProject(projectName, user);
    rtn = getProjectResults(project);
    return rtn;
  }

  /**
   * Get a project result as binary data.
   *
   * @param user The user to whom the project belongs.
   * @param projectName The name of the project.
   * @param fileName The filename of the result.
   * @return A byte array of the result content.
   * @throws ResourceNotFoundException Could not find the result.
   * @throws IOException Could not read result.
   */
  @ResponseBody
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/projects/{project-name}/results/{file-name}",
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public byte[] getResult(
      final ApplicationUser user,
      @PathVariable(value = "project-name") final String projectName,
      @PathVariable(value = "file-name") final String fileName)
      throws ResourceNotFoundException, IOException {
    final Project project = getProject(projectName, user);
    final Result r = getProjectResult(fileName, project);
    final InputStream is = new FileInputStream(r.getFile());
    return IOUtils.toByteArray(is);
  }
}
