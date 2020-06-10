/*
 * Copyright (C) 2020 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.cellocad.v2.webapp.resource;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.v2.DNACompiler.runtime.environment.DNACompilerRuntimeEnv;
import org.cellocad.v2.webapp.ApplicationUtils;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.resource.library.InputSensorFileDescriptor;
import org.cellocad.v2.webapp.resource.library.OutputDeviceFileDescriptor;
import org.cellocad.v2.webapp.resource.library.UserConstraintsFileDescriptor;
import org.cellocad.v2.webapp.specification.library.serialization.HeaderSerializationConstants;
import org.cellocad.v2.webapp.specification.library.serialization.LibrarySerializationConstants;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Utils for application resources.
 *
 * @author Timothy Jones
 * @date 2020-03-08
 */
public class ApplicationResourceUtils {

  private static Logger getLogger() {
    return LogManager.getLogger(ApplicationResourceUtils.class);
  }

  private static String getTargetDataResourcesDirectory() {
    String rtn = "";
    rtn = ApplicationUtils.getResourcesDirectory() + Utils.getFileSeparator() + "target_data";
    return rtn;
  }

  /**
   * Get the directory containing the user constraints files.
   *
   * @return The directory containing the user constraints files.
   */
  public static String getUserConstraintsFileResourcesDirectory() {
    String rtn = "";
    rtn =
        ApplicationResourceUtils.getTargetDataResourcesDirectory()
            + Utils.getFileSeparator()
            + "ucf";
    return rtn;
  }

  /**
   * Get the descriptors of the available user constraints files.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<UserConstraintsFileDescriptor> getUserConstraintsFileDescriptors()
      throws JsonProcessingException, IOException {
    Collection<UserConstraintsFileDescriptor> rtn = null;
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = getUserConstraintsFileMetaDataFile();
    rtn =
        mapper.readValue(
            new File(filepath), new TypeReference<List<UserConstraintsFileDescriptor>>() {});
    return rtn;
  }

  /**
   * Get the filename of the metadata file for the user constraints.
   *
   * @return The filename of the metadata file for the user constraints.
   */
  public static String getUserConstraintsFileMetaDataFile() {
    String rtn = "";
    rtn =
        ApplicationResourceUtils.getUserConstraintsFileResourcesDirectory()
            + Utils.getFileSeparator()
            + "metadata.json";
    return rtn;
  }

  /**
   * Get the directory containing the input sensor files.
   *
   * @return The directory containing the input sensor files.
   */
  public static String getInputSensorFileResourcesDirectory() {
    String rtn = "";
    rtn =
        ApplicationResourceUtils.getTargetDataResourcesDirectory()
            + Utils.getFileSeparator()
            + "input";
    return rtn;
  }

  /**
   * Get the descriptors of the available input sensor files.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<InputSensorFileDescriptor> getInputSensorFileDescriptors()
      throws JsonProcessingException, IOException {
    Collection<InputSensorFileDescriptor> rtn = null;
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = getInputSensorFileMetaDataFile();
    rtn =
        mapper.readValue(
            new File(filepath), new TypeReference<List<InputSensorFileDescriptor>>() {});
    return rtn;
  }

  /**
   * Get the filename of the metadata file for the input sensors.
   *
   * @return The filename of the metadata file for the input sensors.
   */
  public static String getInputSensorFileMetaDataFile() {
    String rtn = "";
    rtn =
        ApplicationResourceUtils.getInputSensorFileResourcesDirectory()
            + Utils.getFileSeparator()
            + "metadata.json";
    return rtn;
  }

  /**
   * Get the directory containing the output device files.
   *
   * @return The directory containing the output device files.
   */
  public static String getOutputDeviceFileResourcesDirectory() {
    String rtn = "";
    rtn =
        ApplicationResourceUtils.getTargetDataResourcesDirectory()
            + Utils.getFileSeparator()
            + "output";
    return rtn;
  }

  /**
   * Get the descriptors of the available output device files.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<OutputDeviceFileDescriptor> getOutputDeviceFileDescriptors()
      throws JsonProcessingException, IOException {
    Collection<OutputDeviceFileDescriptor> rtn = null;
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = getOutputDeviceFileMetaDataFile();
    rtn =
        mapper.readValue(
            new File(filepath), new TypeReference<List<OutputDeviceFileDescriptor>>() {});
    return rtn;
  }

  /**
   * Get the filename of the metadata file for the output devices.
   *
   * @return The filename of the metadata file for the output devices.
   */
  public static String getOutputDeviceFileMetaDataFile() {
    String rtn = "";
    rtn =
        ApplicationResourceUtils.getOutputDeviceFileResourcesDirectory()
            + Utils.getFileSeparator()
            + "metadata.json";
    return rtn;
  }

  /**
   * Get the directory name of the settings resources.
   *
   * @return The directory name of the settings resources.
   */
  public static String getSettingsResourcesDirectory() {
    String rtn = "";
    rtn = ApplicationUtils.getResourcesDirectory() + Utils.getFileSeparator() + "settings";
    return rtn;
  }

  /**
   * Get the settings description file.
   *
   * @return The settings file.
   */
  public static String getSettingsFile() {
    String rtn = "";
    rtn =
        ApplicationResourceUtils.getSettingsResourcesDirectory()
            + Utils.getFileSeparator()
            + "settings.json";
    return rtn;
  }

  private static void initMetaDataFile(final String filepath) throws IOException {
    Utils.createFile(filepath);
    // write empty array
    final ObjectMapper mapper = new ObjectMapper();
    final ArrayNode arr = mapper.createArrayNode();
    Utils.writeToFile(arr.toString(), filepath);
  }

  // TODO use a descriptor class
  private static void appendToMetaDataFile(
      final String filepath, final File resource, final JsonNode header)
      throws JsonGenerationException, JsonMappingException, IOException {
    final ObjectMapper mapper = new ObjectMapper();
    ArrayNode node = null;
    try {
      node = (ArrayNode) mapper.readTree(new File(filepath));
    } catch (final IOException e) {
      throw new RuntimeException("Error with file.");
    }
    final ObjectNode obj = mapper.createObjectNode();
    obj.put("file", resource.getName());
    obj.set("header", header);
    obj.put("isPrivate", false);
    node.add(obj);
    final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    writer.writeValue(new File(filepath), node);
    node = (ArrayNode) mapper.readTree(new File(filepath));
  }

  private static void initTargetDataResources(
      final String dir, final String filepath, final String pattern) throws IOException {
    Utils.makeDirectory(dir);
    ApplicationResourceUtils.initMetaDataFile(filepath);
    final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    final Resource[] resources = resolver.getResources(pattern);
    final ObjectMapper mapper = new ObjectMapper();
    for (final Resource r : resources) {
      final File f = new File(dir + Utils.getFileSeparator() + r.getFilename());
      FileUtils.copyInputStreamToFile(r.getInputStream(), f);
      final JsonNode node = mapper.readTree(f);
      JsonNode collection = null;
      for (final JsonNode n : node) {
        if (n.get(LibrarySerializationConstants.S_UCF_COLLECTION)
            .asText()
            .equals(HeaderSerializationConstants.S_UCF_COLLECTION)) {
          collection = n;
          break;
        }
      }
      ApplicationResourceUtils.appendToMetaDataFile(filepath, f, collection);
    }
  }

  private static void initTargetDataResources() throws IOException {
    // Create directory
    String baseDir = ApplicationResourceUtils.getTargetDataResourcesDirectory();
    getLogger().debug(String.format("Creating target data resources directory at %s.", baseDir));
    Utils.makeDirectory(baseDir);
    // Reused variables
    String dir = "";
    String metadata = "";
    // user constraints
    dir = ApplicationResourceUtils.getUserConstraintsFileResourcesDirectory();
    getLogger()
        .debug(String.format("Creating user constraints file resources directory at %s.", dir));
    metadata = ApplicationResourceUtils.getUserConstraintsFileMetaDataFile();
    getLogger()
        .debug(
            String.format(
                "Initializing user constraints file resources metadata at %s.", metadata));
    ApplicationResourceUtils.initTargetDataResources(
        dir, metadata, "classpath:/lib/files/v2/ucf/**/*.UCF.json");
    // input sensor
    dir = ApplicationResourceUtils.getInputSensorFileResourcesDirectory();
    getLogger().debug(String.format("Creating input sensor file resources directory at %s.", dir));
    metadata = ApplicationResourceUtils.getInputSensorFileMetaDataFile();
    getLogger()
        .debug(String.format("Initializing input sensor file resources metadata at %s.", metadata));
    ApplicationResourceUtils.initTargetDataResources(
        dir, metadata, "classpath:/lib/files/v2/input/**/*.input.json");
    // output device
    dir = ApplicationResourceUtils.getOutputDeviceFileResourcesDirectory();
    getLogger().debug(String.format("Creating output device file resources directory at %s.", dir));
    metadata = ApplicationResourceUtils.getOutputDeviceFileMetaDataFile();
    getLogger()
        .debug(
            String.format("Initializing output device file resources metadata at %s.", metadata));
    ApplicationResourceUtils.initTargetDataResources(
        dir, metadata, "classpath:/lib/files/v2/output/**/*.output.json");
  }

  private static ObjectNode getStageNode(final String name, final ArrayNode stages) {
    ObjectNode rtn = null;
    for (final JsonNode node : stages) {
      if (node.get("name").asText().equals(name)) {
        rtn = (ObjectNode) node;
        break;
      }
    }
    return rtn;
  }

  /**
   * Build the complete description of all available settings.
   *
   * @throws IOException Unable to read or write resource.
   */
  private static void initSettingsFile() throws IOException {
    final InputStream is =
        DNACompilerRuntimeEnv.class.getClassLoader().getResourceAsStream("Configuration.json");
    final ObjectMapper mapper = new ObjectMapper();
    final JsonNode configuration = mapper.readTree(is);
    final ObjectNode settings = mapper.createObjectNode();
    final ArrayNode applications = settings.putArray("applications");
    final ObjectNode dnaCompiler = applications.addObject();
    dnaCompiler.put("name", "DNACompiler");
    final ArrayNode stages = dnaCompiler.putArray("stages");
    for (final JsonNode node : configuration.get("stages")) {
      final ObjectNode stage = stages.addObject();
      stage.set("name", node.get("name"));
      stage.putArray("algorithms");
    }
    final PathMatchingResourcePatternResolver resolver =
        new PathMatchingResourcePatternResolver(DNACompilerRuntimeEnv.class.getClassLoader());
    final Resource[] resources = resolver.getResources("classpath:/algorithms/**/*.json");
    for (final Resource r : resources) {
      final InputStream i = r.getInputStream();
      final URI uri = r.getURI();
      final String[] segments = uri.toURL().getPath().split("/");
      final String stage = segments[segments.length - 3];
      final JsonNode refAlgoNode = mapper.readTree(i);
      final ObjectNode stageNode = ApplicationResourceUtils.getStageNode(stage, stages);
      final ArrayNode algorithms = (ArrayNode) stageNode.get("algorithms");
      final ObjectNode algoNode = algorithms.addObject();
      algoNode.set("name", refAlgoNode.get("name"));
      algoNode.set("parameters", refAlgoNode.get("parameters"));
    }
    for (final JsonNode node : configuration.get("stages")) {
      final ObjectNode stageNode =
          ApplicationResourceUtils.getStageNode(node.get("name").asText(), stages);
      stageNode.set("default", node.get("algorithm_name"));
    }
    settings.put("default", "DNACompiler");
    final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    writer.writeValue(new File(ApplicationResourceUtils.getSettingsFile()), settings);
  }

  private static void initSettingsResources() throws IOException {
    // Create directory
    String dir = ApplicationResourceUtils.getSettingsResourcesDirectory();
    getLogger().debug(String.format("Creating settings directory at %s.", dir));
    Utils.makeDirectory(dir);
    // Initialize settings file
    getLogger().debug(String.format("Creating settings file."));
    ApplicationResourceUtils.initSettingsFile();
  }

  /**
   * Initialize all resources.
   *
   * @throws IOException Unable to read or write resources.
   */
  public static void initApplicationResources() throws IOException {
    // Create directory
    String dir = ApplicationUtils.getResourcesDirectory();
    getLogger().debug(String.format("Creating resource directory at %s.", dir));
    Utils.makeDirectory(ApplicationUtils.getResourcesDirectory());
    // Initialize target data resources
    getLogger().debug("Initializing target data resources.");
    ApplicationResourceUtils.initTargetDataResources();
    // Initialize settings description for webapp
    getLogger().debug("Initializing settings description.");
    ApplicationResourceUtils.initSettingsResources();
  }
}
