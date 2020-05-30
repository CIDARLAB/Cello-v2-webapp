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

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.bson.types.ObjectId;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.exception.ProjectException;
import org.cellocad.v2.webapp.results.Result;
import org.cellocad.v2.webapp.specification.Specification;
import org.cellocad.v2.webapp.specification.library.SynBioHubLibraryResource;
import org.cellocad.v2.webapp.specification.library.TargetDataLibraryResource;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
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
  private String userConstraintsFile;
  private String inputSensorFile;
  private String outputDeviceFile;

  private Collection<Result> results;

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
    userConstraintsFile = targetDataFile;
  }

  /**
   * Initialize a new project.
   *
   * @param user The user the project belongs to.
   * @param name The project name.
   * @param specification Thne project specification.
   * @throws ProjectException Unable to initialize project.
   */
  public Project(final ApplicationUser user, final String name, final Specification specification)
      throws ProjectException {
    this.name = name;
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

    // // user constraints file
    // String userConstraintsFilePath = filepath.toString() +
    // Utils.getFileSeparator() + name +
    // ".UCF.json";
    // Utils.createFile(userConstraintsFilePath);
    // try {
    // mapper.writerWithDefaultPrettyPrinter().writeValue(new
    // File(userConstraintsFilePath),
    // specification.getLibraryResource().getLibrary());
    // } catch (IOException | LibraryException e) {
    // throw new ProjectException(e);
    // }
    // this.userConstraintsFile = userConstraintsFilePath;
    if (specification.getLibraryResource() instanceof TargetDataLibraryResource) {
      final TargetDataLibraryResource library =
          (TargetDataLibraryResource) specification.getLibraryResource();
      // FIXME
      final String path = "/lib/files/v2/";
      final String ucfPath = path + "ucf/";
      final String inputPath = path + "input/";
      final String outputPath = path + "output/";
      File userConstraintsFile = new File(library.getUserConstraintsFile().toString());
      File inputSensorFile = new File(library.getInputSensorFile().toString());
      File outputDeviceFile = new File(library.getOutputDeviceFile().toString());
      final PathMatchingResourcePatternResolver resolver =
          new PathMatchingResourcePatternResolver();
      Resource[] r = null;
      // user constraints file
      try {
        r = resolver.getResources("classpath:" + ucfPath + "**/" + userConstraintsFile.toString());
        if (r.length < 1) {
          throw new ProjectException("Unable to locate user constraints file.");
        }
        userConstraintsFile = new File(filepath, userConstraintsFile.toString());
        FileUtils.copyInputStreamToFile(r[0].getInputStream(), userConstraintsFile);
      } catch (final IOException e) {
        throw new ProjectException("Unable to write user constraints file to project directory.");
      }
      // input sensor file
      try {
        r = resolver.getResources("classpath:" + inputPath + "**/" + inputSensorFile.toString());
        if (r.length < 1) {
          throw new ProjectException("Unable to locate input sensor file.");
        }
        inputSensorFile = new File(filepath, inputSensorFile.toString());
        FileUtils.copyInputStreamToFile(r[0].getInputStream(), inputSensorFile);
      } catch (final IOException e) {
        throw new ProjectException("Unable to write input sensor file to project directory.");
      }
      // output device file
      try {
        r = resolver.getResources("classpath:" + outputPath + "**/" + outputDeviceFile.toString());
        if (r.length < 1) {
          throw new ProjectException("Unable to locate output device file.");
        }
        outputDeviceFile = new File(filepath, outputDeviceFile.toString());
        FileUtils.copyInputStreamToFile(r[0].getInputStream(), outputDeviceFile);
      } catch (final IOException e) {
        throw new ProjectException("Unable to write output device file to project directory.");
      }
      this.userConstraintsFile = userConstraintsFile.toString();
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
   * @param id the value to set <i>id</i>
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
   * @param name the value to set <i>name</i>
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
   * @param filepath the value to set <i>filepath</i>
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
   * @param created the value to set <i>created</i>
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
   * @param verilogFile the value to set <i>verilogFile</i>
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
   * @param optionsFile the value to set <i>optionsFile</i>
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
   * @param netlistConstraintFile the value to set <i>netlistConstraintFile</i>
   */
  public void setNetlistConstraintFile(final String netlistConstraintFile) {
    this.netlistConstraintFile = netlistConstraintFile;
  }

  /**
   * Getter for {@code userConstraintsFile}.
   *
   * @return The value of {@code userConstraintsFile}.
   */
  public String getUserConstraintsFile() {
    return userConstraintsFile;
  }

  /**
   * Setter for {@code userConstraintsFile}.
   *
   * @param userConstraintsFile the value to set <i>userConstraintsFile</i>
   */
  public void setUserConstraintsFile(final String userConstraintsFile) {
    this.userConstraintsFile = userConstraintsFile;
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
   * @param inputSensorFile the value to set <i>inputSensorFile</i>
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
   * @param outputDeviceFile the value to set <i>outputDeviceFile</i>
   */
  public void setOutputDeviceFile(final String outputDeviceFile) {
    this.outputDeviceFile = outputDeviceFile;
  }

  /**
   * Getter for {@code results}.
   *
   * @return The value of {@code results}.
   */
  public Collection<Result> getResults() {
    final File resultsPath = new File(getFilepath());
    final Collection<Result> results = new ArrayList<>();
    for (final File file :
        FileUtils.listFiles(resultsPath, TrueFileFilter.TRUE, TrueFileFilter.TRUE)) {
      final Result result = new Result(file);
      results.add(result);
    }
    return results;
  }

  /**
   * Setter for {@code results}.
   *
   * @param results the value to set <i>results</i>
   */
  public void setResults(final Collection<Result> results) {
    this.results = results;
  }
}
