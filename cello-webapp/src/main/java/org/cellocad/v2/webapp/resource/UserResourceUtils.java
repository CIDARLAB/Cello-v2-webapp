/*
 * Copyright (C) 2020 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cellocad.v2.webapp.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.resource.library.Header;
import org.cellocad.v2.webapp.resource.library.UserConstraintsFileDescriptor;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.cellocad.v2.webapp.user.UserUtils;

/**
 * Utilities for user resources.
 *
 * @author Timothy Jones
 * @date 2020-05-30
 */
public class UserResourceUtils {

  /**
   * Get the resources directory of a user.
   *
   * @param user The user.
   * @return The resources directory of the user.
   */
  private static String getResourcesDirectory(final ApplicationUser user) {
    String rtn = "";
    rtn += UserUtils.getUserDirectory(user);
    rtn += Utils.getFileSeparator();
    rtn += "resources";
    return rtn;
  }

  private static void createResourcesDirectory(final ApplicationUser user) {
    final String path = getResourcesDirectory(user);
    Utils.makeDirectory(path);
  }

  private static void createTargetDataResourcesDirectory(final ApplicationUser user) {
    final String path = getTargetDataResourcesDirectory(user);
    Utils.makeDirectory(path);
  }

  private static void initTargetDataResources(final ApplicationUser user) throws IOException {
    createTargetDataResourcesDirectory(user);
    initUserConstraintsFileResources(user);
    initInputSesorFileResources(user);
    initOutputDeviceFileResources(user);
  }

  private static void initOutputDeviceFileResources(final ApplicationUser user) throws IOException {
    createOutputDeviceFileResourcesDirectory(user);
    ResourceUtils.initMetaDataFile(getOutputDeviceFileMetaDataFile(user));
  }

  private static void createOutputDeviceFileResourcesDirectory(ApplicationUser user) {
    final String path = getOutputDeviceFileResourcesDirectory(user);
    Utils.makeDirectory(path);
  }

  private static void initInputSesorFileResources(final ApplicationUser user) throws IOException {
    createInputSensorFileResourcesDirectory(user);
    ResourceUtils.initMetaDataFile(getInputSensorFileMetaDataFile(user));
  }

  private static void createInputSensorFileResourcesDirectory(ApplicationUser user) {
    final String path = getInputSensorFileResourcesDirectory(user);
    Utils.makeDirectory(path);
  }

  private static void initUserConstraintsFileResources(final ApplicationUser user)
      throws IOException {
    createUserConstraintsFileResourcesDirectory(user);
    ResourceUtils.initMetaDataFile(getUserConstraintsFileMetaDataFile(user));
  }

  private static void createUserConstraintsFileResourcesDirectory(ApplicationUser user) {
    final String path = getUserConstraintsFileResourcesDirectory(user);
    Utils.makeDirectory(path);
  }

  public static void initResources(final ApplicationUser user) throws IOException {
    createResourcesDirectory(user);
    initTargetDataResources(user);
  }

  private static String getTargetDataResourcesDirectory(final ApplicationUser user) {
    String rtn = "";
    rtn = getResourcesDirectory(user) + Utils.getFileSeparator() + "target_data";
    return rtn;
  }

  /*
   * User Constraints File
   */

  private static String getUserConstraintsFileResourcesDirectory(final ApplicationUser user) {
    String rtn = "";
    rtn = getTargetDataResourcesDirectory(user) + Utils.getFileSeparator() + "ucf";
    return rtn;
  }

  /**
   * Get the filename of the metadata file for the user constraints.
   *
   * @return The filename of the metadata file for the user constraints.
   */
  private static String getUserConstraintsFileMetaDataFile(final ApplicationUser user) {
    String rtn = "";
    rtn =
        getUserConstraintsFileResourcesDirectory(user) + Utils.getFileSeparator() + "metadata.json";
    return rtn;
  }

  /**
   * Get the descriptors of the available user constraints files.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<UserConstraintsFileDescriptor> getUserConstraintsFileDescriptors(
      final ApplicationUser user) throws JsonProcessingException, IOException {
    Collection<UserConstraintsFileDescriptor> rtn = null;
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = getUserConstraintsFileMetaDataFile(user);
    rtn =
        mapper.readValue(
            new File(filepath), new TypeReference<List<UserConstraintsFileDescriptor>>() {});
    return rtn;
  }

  /**
   * Get the descriptors of all the available user constraints files, both the user's private files
   * and the public ones.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<UserConstraintsFileDescriptor> getAllUserConstraintsFileDescriptors(
      final ApplicationUser user) throws JsonProcessingException, IOException {
    Collection<UserConstraintsFileDescriptor> rtn = new ArrayList<>();
    Collection<UserConstraintsFileDescriptor> appFiles =
        ApplicationResourceUtils.getUserConstraintsFileDescriptors();
    Collection<UserConstraintsFileDescriptor> userFiles = getUserConstraintsFileDescriptors(user);
    rtn.addAll(appFiles);
    rtn.addAll(userFiles);
    return rtn;
  }

  /**
   * Add the given JSON user constraints file to the user's resources.
   *
   * @param user The user.
   * @param node The JSON-encoded user constraints file.
   * @throws CelloWebException Error adding user constraints file.
   */
  // TODO: Too complicated.
  public static void addUserConstraintsFile(final ApplicationUser user, final JsonNode node)
      throws CelloWebException {
    final ObjectMapper mapper = new ObjectMapper();
    // Get metadata
    Header header = null;
    // TODO: don't do this
    for (JsonNode collection : node) {
      if (collection.get("collection").asText().equals("header")) {
        try {
          header = mapper.treeToValue(collection, Header.class);
        } catch (JsonProcessingException e) {
          throw new CelloWebException("Unable to parse user constraints file.");
        }
      }
    }
    if (header == null) {
      throw new CelloWebException("No header found in user constraints file.");
    }
    final String filename = header.getVersion() + ".UCF.json";
    final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    final String filepath =
        getUserConstraintsFileResourcesDirectory(user) + Utils.getFileSeparator() + filename;
    File file = new File(filepath);
    // TODO: Do a proper check.
    if (file.exists()) {
      throw new CelloWebException(
          "A user constraints file with this identifier (the version field in the header) already exists.");
    }
    try {
      writer.writeValue(file, node);
    } catch (IOException e) {
      throw new CelloWebException("Unable to write user constraints file.");
    }
    // Save metadata.
    Collection<UserConstraintsFileDescriptor> descriptors;
    try {
      descriptors = getUserConstraintsFileDescriptors(user);
    } catch (IOException e) {
      throw new CelloWebException("Unable to read available user constraints files for user.");
    }
    UserConstraintsFileDescriptor descriptor = new UserConstraintsFileDescriptor();
    descriptor.setHeader(header);
    descriptor.setFile(filename);
    descriptors.add(descriptor);
    try {
      writer.writeValue(new File(getUserConstraintsFileMetaDataFile(user)), descriptors);
    } catch (IOException e) {
      throw new CelloWebException("Unable to write metadata for user constraints file.");
    }
  }

  /*
   * Input Sensor File
   */

  private static String getInputSensorFileResourcesDirectory(final ApplicationUser user) {
    String rtn = "";
    rtn = getTargetDataResourcesDirectory(user) + Utils.getFileSeparator() + "input";
    return rtn;
  }

  /**
   * Get the filename of the metadata file for the input sensors.
   *
   * @return The filename of the metadata file for the input sensors.
   */
  private static String getInputSensorFileMetaDataFile(final ApplicationUser user) {
    String rtn = "";
    rtn = getInputSensorFileResourcesDirectory(user) + Utils.getFileSeparator() + "metadata.json";
    return rtn;
  }

  /*
   * Output Device File
   */

  private static String getOutputDeviceFileResourcesDirectory(final ApplicationUser user) {
    String rtn = "";
    rtn = getTargetDataResourcesDirectory(user) + Utils.getFileSeparator() + "output";
    return rtn;
  }

  /**
   * Get the filename of the metadata file for the output devices.
   *
   * @return The filename of the metadata file for the output devices.
   */
  public static String getOutputDeviceFileMetaDataFile(final ApplicationUser user) {
    String rtn = "";
    rtn = getOutputDeviceFileResourcesDirectory(user) + Utils.getFileSeparator() + "metadata.json";
    return rtn;
  }
}
