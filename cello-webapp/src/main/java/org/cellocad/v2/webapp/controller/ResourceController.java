/*
 * Copyright (C) 2020 Boston University (BU) Permission is hereby granted, free
 * of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.resource.ApplicationResourceUtils;
import org.cellocad.v2.webapp.resource.UserResourceUtils;
import org.cellocad.v2.webapp.resource.library.UserConstraintsFileDescriptor;
import org.cellocad.v2.webapp.user.ApplicationUser;
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
 * A controller for resources.
 *
 * @author Timothy Jones
 * @date 2020-02-02
 */
@RestController
@RequestMapping("/resources")
public class ResourceController {

  private static Logger getLogger() {
    return LogManager.getLogger(ResourceController.class);
  }

  @PostConstruct
  private static void init() throws IOException {
    ApplicationResourceUtils.initApplicationResources();
    getLogger().debug("Resource initialization completed.");
  }

  /**
   * Get descriptions of the user constraints files.
   *
   * @return The set of user constraints files.
   * @throws IOException Unable to read resource.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/user-constraints-files",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public Collection<UserConstraintsFileDescriptor> getUserConstraintsFiles(
      final ApplicationUser user) throws IOException {
    Collection<UserConstraintsFileDescriptor> rtn = new ArrayList<>();
    rtn = UserResourceUtils.getAllUserConstraintsFileDescriptors(user);
    return rtn;
  }

  /**
   * Add a user constraints file.
   *
   * @param user The user the file belongs to.
   * @param node The content of the user constraints file.
   * @throws CelloWebException Unable to add user constraints file.
   */
  @RequestMapping(method = RequestMethod.POST, value = "/user-constraints-files")
  public void addUserConstraintsFile(final ApplicationUser user, @RequestBody JsonNode node)
      throws CelloWebException {
    UserResourceUtils.addUserConstraintsFile(user, node);
  }

  /**
   * Get a user constraints file.
   *
   * @param user The user the file belongs to.
   * @throws CelloWebException Unable to add user constraints file.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/user-constraints-files/{file-name}",
      produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
  public byte[] getUserConstraintsFile(
      final ApplicationUser user, @PathVariable(value = "file-name") final String fileName) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Delete a user constraints file.
   *
   * @param user The user the file belongs to.
   * @throws CelloWebException Unable to add user constraints file.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/user-constraints-files/{file-name}")
  public void deleteUserConstraintsFile(
      final ApplicationUser user, @PathVariable(value = "file-name") final String fileName) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Get descriptions of the input sensor files.
   *
   * @return The set of input sensor files.
   * @throws IOException Unable to read file.
   */
  // TODO Don't return JsonNode
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/input-sensor-files",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public JsonNode getInputSensorFiles() throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = ApplicationResourceUtils.getInputSensorFileMetaDataFile();
    final JsonNode rtn = mapper.readTree(new File(filepath));
    return rtn;
  }

  /**
   * Add an input sensor file.
   *
   * @param user The user the file belongs to.
   * @param node The content of the input sensor file.
   * @throws CelloWebException Unable to add input sensor file.
   */
  @RequestMapping(method = RequestMethod.POST, value = "/input-sensor-files")
  public void addInputSensorFile(final ApplicationUser user, @RequestBody JsonNode node) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Get an input sensor file.
   *
   * @param user The user the file belongs to.
   * @throws CelloWebException Unable to add user constraints file.
   */
  @ResponseBody
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/input-sensor-files/{file-name}",
      produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
  public byte[] getInputSensorFile(
      final ApplicationUser user, @PathVariable(value = "file-name") final String fileName) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Delete an input sensor file.
   *
   * @param user The user the file belongs to.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/input-sensor-files/{file-name}")
  public void deleteInputSensorFile(
      final ApplicationUser user, @PathVariable(value = "file-name") final String fileName) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Get descriptions of the output device files.
   *
   * @return The set of output device files.
   * @throws IOException Unable to read file.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/output-device-files",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  // TODO Don't return JsonNode
  public JsonNode getOutputDeviceFiles() throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = ApplicationResourceUtils.getOutputDeviceFileMetaDataFile();
    final JsonNode rtn = mapper.readTree(new File(filepath));
    return rtn;
  }

  /**
   * Add an output device file.
   *
   * @param user The user the file belongs to.
   * @param node The content of the input sensor file.
   * @throws CelloWebException Unable to add input sensor file.
   */
  @RequestMapping(method = RequestMethod.POST, value = "/output-device-files")
  public void addOutputDeviceFile(final ApplicationUser user, @RequestBody JsonNode node) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Get an output device file.
   *
   * @param user The user the file belongs to.
   * @throws CelloWebException Unable to add user constraints file.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/output-device-files/{file-name}",
      produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
  public byte[] getOutputDeviceFile(
      final ApplicationUser user, @PathVariable(value = "file-name") final String fileName) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Delete an output device file.
   *
   * @param user The user the file belongs to.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/output-device-files/{file-name}")
  public void deleteOutputDeviceFile(
      final ApplicationUser user, @PathVariable(value = "file-name") final String fileName) {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Get the available settings for all applications.
   *
   * @return The settings.
   * @throws IOException The settings file could not be produced.
   */
  // TODO Don't return JsonNode, map to a settings object, maybe.
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/settings",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public JsonNode getSettings() throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = ApplicationResourceUtils.getSettingsFile();
    final JsonNode rtn = mapper.readTree(new File(filepath));
    return rtn;
  }
}
