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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.resource.library.Header;
import org.cellocad.v2.webapp.resource.library.InputSensorFileDescriptor;
import org.cellocad.v2.webapp.resource.library.OutputDeviceFileDescriptor;
import org.cellocad.v2.webapp.resource.library.UserConstraintsFileDescriptor;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.cellocad.v2.webapp.user.UserUtils;
import org.springframework.web.multipart.MultipartFile;

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

  /**
   * Get the directory containing the user constraints files.
   *
   * @param user The user.
   * @return The directory containing the user constraints files.
   */
  public static String getUserConstraintsFileResourcesDirectory(final ApplicationUser user) {
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
   * @param multipartFile The user constraints file.
   * @throws IOException Unable to read or write file.
   * @throws JsonMappingException Unable to map file to JSON.
   * @throws JsonProcessingException Unable to deserialize file header.
   * @throws JsonGenerationException Unable to generate JSON metadata.
   * @throws FileAlreadyExistsException A file with the same name already exists.
   * @throws CelloWebException No header found in user constraints file.
   */
  // TODO: Too complicated.
  public static void addUserConstraintsFile(
      final ApplicationUser user, final MultipartFile multipartFile)
      throws JsonGenerationException, JsonMappingException, JsonProcessingException,
          FileAlreadyExistsException, IOException, CelloWebException {
    // Check if file exists
    final String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
    final Path path = Paths.get(getUserConstraintsFileResourcesDirectory(user), fileName);
    final Path appPath =
        Paths.get(ApplicationResourceUtils.getUserConstraintsFileResourcesDirectory(), fileName);
    if (Files.exists(path) || Files.exists(appPath)) {
      throw new FileAlreadyExistsException(
          "A user constraints file with that name already exists.");
    }
    // Read file
    final ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(multipartFile.getInputStream());
    // Read header
    Header header = null;
    // TODO maybe map to a UserConstraintsFile type
    for (JsonNode collection : node) {
      if (collection.get("collection").asText().equals("header")) {
        header = mapper.treeToValue(collection, Header.class);
      }
    }
    if (header == null) {
      throw new CelloWebException("No header found in user constraints file.");
    }
    final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    writer.writeValue(Files.newOutputStream(path), node);
    // Save metadata
    UserConstraintsFileDescriptor descriptor = new UserConstraintsFileDescriptor();
    descriptor.setHeader(header);
    descriptor.setFile(fileName);
    descriptor.setIsPrivate(true);
    Collection<UserConstraintsFileDescriptor> descriptors = getUserConstraintsFileDescriptors(user);
    descriptors.add(descriptor);
    writer.writeValue(new File(getUserConstraintsFileMetaDataFile(user)), descriptors);
  }

  /**
   * Get the named user constraints file as a byte array.
   *
   * @param user The user.
   * @param fileName The file name.
   * @return A byte array with the contents of the user constraints file.
   * @throws IOException Unable to read the user constraints file.
   */
  public static byte[] getUserConstraintsFile(final ApplicationUser user, final String fileName)
      throws IOException {
    byte[] rtn = null;
    Collection<UserConstraintsFileDescriptor> descriptors =
        getAllUserConstraintsFileDescriptors(user);
    UserConstraintsFileDescriptor descriptor = null;
    for (UserConstraintsFileDescriptor d : descriptors) {
      if (Paths.get(d.getFile()).getFileName().toString().equals(fileName)) {
        descriptor = d;
        break;
      }
    }
    if (descriptor != null) {
      String prefix = null;
      if (descriptor.getIsPrivate()) {
        prefix = getUserConstraintsFileResourcesDirectory(user);
      } else {
        prefix = ApplicationResourceUtils.getUserConstraintsFileResourcesDirectory();
      }
      Path path = Paths.get(prefix, descriptor.getFile());
      rtn = Files.readAllBytes(path);
    }
    return rtn;
  }

  /**
   * Delete user constraints file belonging to the given user.
   *
   * @param user The user.
   * @param fileName The file name.
   * @throws FileNotFoundException File not found.
   * @throws JsonMappingException Unable to map to JSON.
   * @throws JsonGenerationException Unable to generate JSON.
   * @throws IOException Unable to delete file.
   */
  public static void deleteUserConstraintsFile(final ApplicationUser user, final String fileName)
      throws FileNotFoundException, JsonGenerationException, JsonMappingException, IOException {
    final String dir = getUserConstraintsFileResourcesDirectory(user);
    Collection<UserConstraintsFileDescriptor> descriptors = getUserConstraintsFileDescriptors(user);
    UserConstraintsFileDescriptor descriptor = null;
    for (UserConstraintsFileDescriptor d : descriptors) {
      if (Paths.get(d.getFile()).getFileName().toString().equals(fileName)) {
        descriptor = d;
        break;
      }
    }
    if (descriptor != null) {
      descriptors.remove(descriptor);
      final ObjectMapper mapper = new ObjectMapper();
      final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      writer.writeValue(new File(getUserConstraintsFileMetaDataFile(user)), descriptors);
    }
    Path path = Paths.get(dir, fileName);
    if (!Files.exists(path)) {
      throw new FileNotFoundException("File not found.");
    }
    Files.delete(path);
  }

  /*
   * Input Sensor File
   */

  /**
   * Get the directory containing the input sensor files.
   *
   * @param user The user.
   * @return The directory containing the input sensor files.
   */
  public static String getInputSensorFileResourcesDirectory(final ApplicationUser user) {
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

  /**
   * Get the descriptors of the available input sensor files.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<InputSensorFileDescriptor> getInputSensorFileDescriptors(
      final ApplicationUser user) throws JsonProcessingException, IOException {
    Collection<InputSensorFileDescriptor> rtn = null;
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = getInputSensorFileMetaDataFile(user);
    rtn =
        mapper.readValue(
            new File(filepath), new TypeReference<List<InputSensorFileDescriptor>>() {});
    return rtn;
  }

  /**
   * Get the descriptors of all the available input sensor files, both the user's private files and
   * the public ones.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<InputSensorFileDescriptor> getAllInputSensorFileDescriptors(
      final ApplicationUser user) throws JsonProcessingException, IOException {
    Collection<InputSensorFileDescriptor> rtn = new ArrayList<>();
    Collection<InputSensorFileDescriptor> appFiles =
        ApplicationResourceUtils.getInputSensorFileDescriptors();
    Collection<InputSensorFileDescriptor> userFiles = getInputSensorFileDescriptors(user);
    rtn.addAll(appFiles);
    rtn.addAll(userFiles);
    return rtn;
  }

  /**
   * Add the given JSON input sensor file to the user's resources.
   *
   * @param user The user.
   * @param multipartFile The input sensor file.
   * @throws IOException Unable to read or write file.
   * @throws JsonMappingException Unable to map file to JSON.
   * @throws JsonGenerationException Unable to generate JSON metadata.
   * @throws FileAlreadyExistsException A file with the same name already exists.
   */
  public static void addInputSensorFile(
      final ApplicationUser user, final MultipartFile multipartFile)
      throws JsonGenerationException, JsonMappingException, FileAlreadyExistsException,
          IOException {
    // Check if file exists
    final String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
    final Path path = Paths.get(getInputSensorFileResourcesDirectory(user), fileName);
    final Path appPath =
        Paths.get(ApplicationResourceUtils.getInputSensorFileResourcesDirectory(), fileName);
    if (Files.exists(path) || Files.exists(appPath)) {
      throw new FileAlreadyExistsException("An output device file with that name already exists.");
    }
    // Read file
    final ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(multipartFile.getInputStream());
    final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    writer.writeValue(Files.newOutputStream(path), node);
    // Save metadata
    InputSensorFileDescriptor descriptor = new InputSensorFileDescriptor();
    descriptor.setFile(fileName);
    descriptor.setIsPrivate(true);
    Collection<InputSensorFileDescriptor> descriptors = getInputSensorFileDescriptors(user);
    descriptors.add(descriptor);
    writer.writeValue(new File(getInputSensorFileMetaDataFile(user)), descriptors);
  }

  /**
   * Get the named input sensor file as a byte array.
   *
   * @param user The user.
   * @param fileName The file name.
   * @return A byte array with the contents of the input sensor file.
   * @throws IOException Unable to read the input sensor file.
   */
  public static byte[] getInputSensorFile(final ApplicationUser user, final String fileName)
      throws IOException {
    byte[] rtn = null;
    Collection<InputSensorFileDescriptor> descriptors = getAllInputSensorFileDescriptors(user);
    InputSensorFileDescriptor descriptor = null;
    for (InputSensorFileDescriptor d : descriptors) {
      if (Paths.get(d.getFile()).getFileName().toString().equals(fileName)) {
        descriptor = d;
        break;
      }
    }
    if (descriptor != null) {
      String prefix = null;
      if (descriptor.getIsPrivate()) {
        prefix = getInputSensorFileResourcesDirectory(user);
      } else {
        prefix = ApplicationResourceUtils.getInputSensorFileResourcesDirectory();
      }
      Path path = Paths.get(prefix, descriptor.getFile());
      rtn = Files.readAllBytes(path);
    }
    return rtn;
  }

  /**
   * Delete input sensor file belonging to the given user.
   *
   * @param user The user.
   * @param fileName The file name.
   * @throws FileNotFoundException File not found.
   * @throws JsonMappingException Unable to map to JSON.
   * @throws JsonGenerationException Unable to generate JSON.
   * @throws IOException Unable to delete file.
   */
  public static void deleteInputSensorFile(final ApplicationUser user, final String fileName)
      throws FileNotFoundException, JsonGenerationException, JsonMappingException, IOException {
    final String dir = getInputSensorFileResourcesDirectory(user);
    Collection<InputSensorFileDescriptor> descriptors = getInputSensorFileDescriptors(user);
    InputSensorFileDescriptor descriptor = null;
    for (InputSensorFileDescriptor d : descriptors) {
      if (Paths.get(d.getFile()).getFileName().toString().equals(fileName)) {
        descriptor = d;
        break;
      }
    }
    if (descriptor != null) {
      descriptors.remove(descriptor);
      final ObjectMapper mapper = new ObjectMapper();
      final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      writer.writeValue(new File(getInputSensorFileMetaDataFile(user)), descriptors);
    }
    Path path = Paths.get(dir, fileName);
    if (!Files.exists(path)) {
      throw new FileNotFoundException("File not found.");
    }
    Files.delete(path);
  }

  /*
   * Output Device File
   */

  /**
   * Get the directory containing the output device files.
   *
   * @param user The user.
   * @return The directory containing the output device files.
   */
  public static String getOutputDeviceFileResourcesDirectory(final ApplicationUser user) {
    String rtn = "";
    rtn = getTargetDataResourcesDirectory(user) + Utils.getFileSeparator() + "output";
    return rtn;
  }

  /**
   * Get the filename of the metadata file for the output devices.
   *
   * @return The filename of the metadata file for the output devices.
   */
  private static String getOutputDeviceFileMetaDataFile(final ApplicationUser user) {
    String rtn = "";
    rtn = getOutputDeviceFileResourcesDirectory(user) + Utils.getFileSeparator() + "metadata.json";
    return rtn;
  }

  /**
   * Get the descriptors of the available output device files.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<OutputDeviceFileDescriptor> getOutputDeviceFileDescriptors(
      final ApplicationUser user) throws JsonProcessingException, IOException {
    Collection<OutputDeviceFileDescriptor> rtn = null;
    final ObjectMapper mapper = new ObjectMapper();
    final String filepath = getOutputDeviceFileMetaDataFile(user);
    rtn =
        mapper.readValue(
            new File(filepath), new TypeReference<List<OutputDeviceFileDescriptor>>() {});
    return rtn;
  }

  /**
   * Get the descriptors of all the available output device files, both the user's private files and
   * the public ones.
   *
   * @return A collection of descriptors.
   * @throws JsonProcessingException Unable to deserialize descriptors.
   * @throws IOException Unable to read metadata file.
   */
  public static Collection<OutputDeviceFileDescriptor> getAllOutputDeviceFileDescriptors(
      final ApplicationUser user) throws JsonProcessingException, IOException {
    Collection<OutputDeviceFileDescriptor> rtn = new ArrayList<>();
    Collection<OutputDeviceFileDescriptor> appFiles =
        ApplicationResourceUtils.getOutputDeviceFileDescriptors();
    Collection<OutputDeviceFileDescriptor> userFiles = getOutputDeviceFileDescriptors(user);
    rtn.addAll(appFiles);
    rtn.addAll(userFiles);
    return rtn;
  }

  /**
   * Add the given JSON output device file to the user's resources.
   *
   * @param user The user.
   * @param multipartFile The output device file.
   * @throws IOException Unable to read or write file.
   * @throws JsonMappingException Unable to map file to JSON.
   * @throws JsonGenerationException Unable to generate JSON metadata.
   * @throws FileAlreadyExistsException A file with the same name already exists.
   */
  public static void addOutputDeviceFile(
      final ApplicationUser user, final MultipartFile multipartFile)
      throws JsonGenerationException, JsonMappingException, FileAlreadyExistsException,
          IOException {
    // Check if file exists
    final String fileName = Paths.get(multipartFile.getOriginalFilename()).getFileName().toString();
    final Path path = Paths.get(getOutputDeviceFileResourcesDirectory(user), fileName);
    final Path appPath =
        Paths.get(ApplicationResourceUtils.getOutputDeviceFileResourcesDirectory(), fileName);
    if (Files.exists(path) || Files.exists(appPath)) {
      throw new FileAlreadyExistsException("An output device file with that name already exists.");
    }
    // Read file
    final ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(multipartFile.getInputStream());
    final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    writer.writeValue(Files.newOutputStream(path), node);
    // Save metadata
    OutputDeviceFileDescriptor descriptor = new OutputDeviceFileDescriptor();
    descriptor.setFile(fileName);
    descriptor.setIsPrivate(true);
    Collection<OutputDeviceFileDescriptor> descriptors = getOutputDeviceFileDescriptors(user);
    descriptors.add(descriptor);
    writer.writeValue(new File(getOutputDeviceFileMetaDataFile(user)), descriptors);
  }

  /**
   * Get the named output device file as a byte array.
   *
   * @param user The user.
   * @param fileName The file name.
   * @return A byte array with the contents of the output device file.
   * @throws IOException Unable to read the output device file.
   */
  public static byte[] getOutputDeviceFile(final ApplicationUser user, final String fileName)
      throws IOException {
    byte[] rtn = null;
    Collection<OutputDeviceFileDescriptor> descriptors = getAllOutputDeviceFileDescriptors(user);
    OutputDeviceFileDescriptor descriptor = null;
    for (OutputDeviceFileDescriptor d : descriptors) {
      if (Paths.get(d.getFile()).getFileName().toString().equals(fileName)) {
        descriptor = d;
        break;
      }
    }
    if (descriptor != null) {
      String prefix = null;
      if (descriptor.getIsPrivate()) {
        prefix = getOutputDeviceFileResourcesDirectory(user);
      } else {
        prefix = ApplicationResourceUtils.getOutputDeviceFileResourcesDirectory();
      }
      Path path = Paths.get(prefix, descriptor.getFile());
      rtn = Files.readAllBytes(path);
    }
    return rtn;
  }

  /**
   * Delete output reporter file belonging to the given user.
   *
   * @param user The user.
   * @param fileName The file name.
   * @throws FileNotFoundException File not found.
   * @throws JsonMappingException Unable to map to JSON.
   * @throws JsonGenerationException Unable to generate JSON.
   * @throws IOException Unable to delete file.
   */
  public static void deleteOutputDeviceFile(final ApplicationUser user, final String fileName)
      throws FileNotFoundException, JsonGenerationException, JsonMappingException, IOException {
    final String dir = getOutputDeviceFileResourcesDirectory(user);
    Collection<OutputDeviceFileDescriptor> descriptors = getOutputDeviceFileDescriptors(user);
    OutputDeviceFileDescriptor descriptor = null;
    for (OutputDeviceFileDescriptor d : descriptors) {
      if (Paths.get(d.getFile()).getFileName().toString().equals(fileName)) {
        descriptor = d;
        break;
      }
    }
    if (descriptor != null) {
      descriptors.remove(descriptor);
      final ObjectMapper mapper = new ObjectMapper();
      final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      writer.writeValue(new File(getOutputDeviceFileMetaDataFile(user)), descriptors);
    }
    Path path = Paths.get(dir, fileName);
    if (!Files.exists(path)) {
      throw new FileNotFoundException("File not found.");
    }
    Files.delete(path);
  }
}
