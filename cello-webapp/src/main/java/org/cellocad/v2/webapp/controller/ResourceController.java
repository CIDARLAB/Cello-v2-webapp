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
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.v2.webapp.resource.ResourceUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    ResourceController.getLogger().info("Initializing resources.");
    ResourceUtils.initResources();
  }

  /**
   * Get the user constraints files available.
   *
   * @param res The response.
   * @return The set of user constraints files.
   * @throws IOException Unable to read resource.
   */
  // TODO Don't return JsonNode
  @GetMapping(
      value = "/user_constraints_files",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public JsonNode userConstraintsFiles(final HttpServletResponse res) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = ResourceUtils.getUserConstraintsFileMetaDataFile();
    final JsonNode rtn = mapper.readTree(new File(filepath));
    return rtn;
  }

  /**
   * Get the input sensor files.
   *
   * @param res The response.
   * @return The set of input sensor files.
   * @throws IOException Unable to read file.
   */
  // TODO Don't return JsonNode
  @GetMapping(
      value = "/input_sensor_files",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public JsonNode inputSensorFiles(final HttpServletResponse res) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = ResourceUtils.getInputSensorFileMetaDataFile();
    final JsonNode rtn = mapper.readTree(new File(filepath));
    return rtn;
  }

  /**
   * Get the output device files.
   *
   * @param res The response.
   * @return The set of output device files.
   * @throws IOException Unable to read file.
   */
  @GetMapping(
      value = "/output_device_files",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  // TODO Don't return JsonNode
  public JsonNode outputDeviceFiles(final HttpServletResponse res) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = ResourceUtils.getOutputDeviceFileMetaDataFile();
    final JsonNode rtn = mapper.readTree(new File(filepath));
    return rtn;
  }

  /**
   * Get the available settings for all applications.
   *
   * @param res The {@link HttpServletResponse} object.
   * @return The settings.
   * @throws IOException The settings file could not be produced.
   */
  // TODO Don't return JsonNode, maybe.
  @GetMapping(
      value = "/settings",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public JsonNode settings(final HttpServletResponse res) throws IOException {
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = ResourceUtils.getSettingsFile();
    final JsonNode rtn = mapper.readTree(new File(filepath));
    return rtn;
  }
}
