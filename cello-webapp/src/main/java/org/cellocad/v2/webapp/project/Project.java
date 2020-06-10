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

package org.cellocad.v2.webapp.project;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.cellocad.v2.results.common.Result;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.exception.ProjectException;
import org.cellocad.v2.webapp.specification.Specification;
import org.cellocad.v2.webapp.specification.library.SynBioHubLibraryResource;
import org.cellocad.v2.webapp.specification.library.TargetDataLibraryResource;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Base class for a project.
 *
 * @author Timothy Jones
 * @date Feb 16, 2019
 */
@Document(collection = "projects")
@TypeAlias("project")
public abstract class Project {

  @Id private ObjectId id;
  private String name;
  private String filepath;
  private Date created;

  private String verilogFile;
  private String optionsFile;
  private String netlistConstraintFile;
  private String targetDataFile;
  private String inputSensorFile;
  private String outputDeviceFile;

  public Project() {}

  /**
   * Initialize a new project.
   *
   * @param name The project name.
   * @param filepath The path to the project directory.
   * @param created The creation date of the project.
   * @param optionsFile The path to the options file for the project.
   * @param verilogFile The path to the verilog file for the project.
   * @param netlistConstraintFile The path to the netlist constraint file for the project.
   * @param targetDataFile The path to the target data file for the project.
   */
  public Project(
      final String name,
      final String filepath,
      final Date created,
      final String verilogFile,
      final String optionsFile,
      final String netlistConstraintFile,
      final String targetDataFile) {
    this.name = name;
    this.filepath = filepath;
    this.created = created;
    this.verilogFile = verilogFile;
    this.optionsFile = optionsFile;
    this.netlistConstraintFile = netlistConstraintFile;
    this.targetDataFile = targetDataFile;
  }

  /**
   * Initialize a new project.
   *
   * @param user The user the project belongs to.
   * @param specification Thne project specification.
   * @throws ProjectException Unable to initialize project.
   */
  public Project(final ApplicationUser user, final Specification specification)
      throws ProjectException {
    this.name = specification.getName();
    final ObjectMapper mapper = new ObjectMapper();
    ProjectUtils.initUserProjectDirectory(user, name);
    id = new ObjectId();
    filepath = new File(ProjectUtils.getUserProjectDirectory(user, name)).getAbsolutePath();
    created = new Date();
    // verilog
    final String verilogFilepath = filepath.toString() + Utils.getFileSeparator() + name + ".v";
    try {
      Utils.createFile(verilogFilepath);
    } catch (final IOException e) {
      throw new ProjectException(e);
    }
    Utils.writeToFile(specification.getVerilog(), verilogFilepath);
    verilogFile = verilogFilepath;
    // options
    final String optionsFilepath =
        filepath.toString() + Utils.getFileSeparator() + name + "_options.csv";
    try {
      Utils.createFile(optionsFilepath);
    } catch (final IOException e) {
      throw new ProjectException(e);
    }
    Utils.writeToFile(specification.getSettings().toCSV(), optionsFilepath);
    optionsFile = optionsFilepath;
    // netlist constraint
    final String netlistConstraintFilepath =
        filepath.toString() + Utils.getFileSeparator() + name + "_netlistconstraints.json";
    try {
      Utils.createFile(netlistConstraintFilepath);
    } catch (final IOException e) {
      throw new ProjectException(e);
    }
    try {
      mapper
          .writerWithDefaultPrettyPrinter()
          .writeValue(new File(netlistConstraintFilepath), specification.getConstraints());
    } catch (final IOException e) {
      throw new ProjectException(e);
    }
    netlistConstraintFile = netlistConstraintFilepath;

    if (specification.getLibraryResource() instanceof TargetDataLibraryResource) {
      final TargetDataLibraryResource library =
          (TargetDataLibraryResource) specification.getLibraryResource();

      File userConstraintsFile = new File(library.getUserConstraintsFile().toString());
      File inputSensorFile = new File(library.getInputSensorFile().toString());
      File outputDeviceFile = new File(library.getOutputDeviceFile().toString());
      // FileUtils.copyInputStreamToFile(r[0].getInputStream(), userConstraintsFile);
      // FileUtils.copyInputStreamToFile(r[0].getInputStream(), inputSensorFile);
      // FileUtils.copyInputStreamToFile(r[0].getInputStream(), outputDeviceFile);
      this.targetDataFile = userConstraintsFile.toString();
      this.inputSensorFile = inputSensorFile.toString();
      this.outputDeviceFile = outputDeviceFile.toString();
    }
    if (specification.getLibraryResource() instanceof SynBioHubLibraryResource) {
      specification.getLibraryResource();
    }
  }

  public abstract void execute() throws CelloWebException;

  public void delete() throws IOException {
    FileUtils.deleteDirectory(new File(getFilepath()));
  }

  /**
   * Getter for {@code id}.
   *
   * @return The value of {@code id}.
   */
  public ObjectId getId() {
    return id;
  }

  /**
   * Setter for {@code id}.
   *
   * @param id The value to set {@code id}.
   */
  public void setId(final ObjectId id) {
    this.id = id;
  }

  /**
   * Getter for {@code name}.
   *
   * @return The value of {@code name}.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for {@code name}.
   *
   * @param name The value to set {@code name}
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Getter for {@code filepath}.
   *
   * @return The value of {@code filepath}.
   */
  public String getFilepath() {
    return filepath;
  }

  /**
   * Setter for {@code filepath}.
   *
   * @param filepath The value to set {@code filepath}.
   */
  public void setFilepath(final String filepath) {
    this.filepath = filepath;
  }

  /**
   * Getter for {@code created}.
   *
   * @return The value of {@code created}.
   */
  public Date getCreated() {
    return created;
  }

  /**
   * Setter for {@code created}.
   *
   * @param created The value to set {@code created}.
   */
  public void setCreated(final Date created) {
    this.created = created;
  }

  /**
   * Getter for {@code verilogFile}.
   *
   * @return The value of {@code verilogFile}.
   */
  public String getVerilogFile() {
    return verilogFile;
  }

  /**
   * Setter for {@code verilogFile}.
   *
   * @param verilogFile The value to set {@code verilogFile}.
   */
  public void setVerilogFile(final String verilogFile) {
    this.verilogFile = verilogFile;
  }

  /**
   * Getter for {@code optionsFile}.
   *
   * @return The value of {@code optionsFile}.
   */
  public String getOptionsFile() {
    return optionsFile;
  }

  /**
   * Setter for {@code optionsFile}.
   *
   * @param optionsFile the value to set {@code optionsFile}.
   */
  public void setOptionsFile(final String optionsFile) {
    this.optionsFile = optionsFile;
  }

  /**
   * Getter for {@code netlistConstraintFile}.
   *
   * @return The value of {@code netlistConstraintFile}.
   */
  public String getNetlistConstraintFile() {
    return netlistConstraintFile;
  }

  /**
   * Setter for {@code netlistConstraintFile}.
   *
   * @param netlistConstraintFile The value to set {@code netlistConstraintFile}.
   */
  public void setNetlistConstraintFile(final String netlistConstraintFile) {
    this.netlistConstraintFile = netlistConstraintFile;
  }

  /**
   * Getter for {@code targetDataFile}.
   *
   * @return The value of {@code targetDataFile}.
   */
  public String getTargetDataFile() {
    return targetDataFile;
  }

  /**
   * Setter for {@code targedDataFile}.
   *
   * @param targetDataFile The value to set {@code targetDataFile}.
   */
  public void setTargetDataFile(final String targetDataFile) {
    this.targetDataFile = targetDataFile;
  }

  /**
   * Getter for {@code inputSensorFile}.
   *
   * @return The value of {@code inputSensorFile}.
   */
  public String getInputSensorFile() {
    return inputSensorFile;
  }

  /**
   * Setter for {@code inputSensorFile}.
   *
   * @param inputSensorFile The value to set {@code inputSensorFile}.
   */
  public void setInputSensorFile(final String inputSensorFile) {
    this.inputSensorFile = inputSensorFile;
  }

  /**
   * Getter for {@code outputDeviceFile}.
   *
   * @return The value of {@code outputDeviceFile}.
   */
  public String getOutputDeviceFile() {
    return outputDeviceFile;
  }

  /**
   * Setter for {@code outputDeviceFile}.
   *
   * @param outputDeviceFile The value to set {@code outputDeviceFile}.
   */
  public void setOutputDeviceFile(final String outputDeviceFile) {
    this.outputDeviceFile = outputDeviceFile;
  }

  /**
   * Getter for {@code results}.
   *
   * @return The value of {@code results}.
   * @throws IOException Unable to load results metadata.
   * @throws JsonMappingException Unable to map to result class.
   * @throws JsonParseException Unable to parse results JSON.
   */
  public Collection<Result> getResults()
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    final File resultsPath = new File(getFilepath(), "results.json");
    final Collection<Result> results =
        mapper.readValue(resultsPath, new TypeReference<Collection<Result>>() {});
    // Results are serialized with a base filename only. Add the full path back in.
    for (final Result result : results) {
      result.setFile(new File(this.getFilepath(), result.getFile().getName()));
    }
    return results;
  }
}
