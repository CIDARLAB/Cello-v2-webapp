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
package org.cellocad.cello2.webapp.project;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.exception.CelloWebException;
import org.cellocad.cello2.webapp.exception.LibraryException;
import org.cellocad.cello2.webapp.exception.ProjectException;
import org.cellocad.cello2.webapp.results.Result;
import org.cellocad.cello2.webapp.specification.Specification;
import org.cellocad.cello2.webapp.user.ApplicationUser;
import org.cidarlab.eugene.util.FileUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
@Document(collection = "projects")
@TypeAlias("project")
public abstract class Project {
	
	@Id
    private ObjectId id;
	private String name;
    private String filepath;
    private Date created;

    private String verilogFile;
    private String optionsFile;
    private String netlistConstraintFile;
    private String userConstraintsFile;
	private String inputSensorFile;
	private String outputDeviceFile;

    @Transient
    private Collection<Result> results;
    
    public Project() {
    	
    }
    
    public Project(String name, String filepath, Date created, String verilogFile, String optionsFile, String netlistConstraintFile, String targetDataFile) {
    	this.name = name;
    	this.filepath = filepath;
    	this.created = created;
    	this.verilogFile = verilogFile;
    	this.optionsFile = optionsFile;
    	this.netlistConstraintFile = netlistConstraintFile;
    	this.userConstraintsFile = targetDataFile;
    }
    
    public Project(ApplicationUser user, String name, Specification specification) throws ProjectException {
    	this.name = name;
    	ObjectMapper mapper = new ObjectMapper();
    	ProjectUtils.createProjectDirectory(user,name);
    	this.id = new ObjectId();
    	this.filepath = (new File(ProjectUtils.getProjectDirectory(user,name))).getAbsolutePath();
    	// verilog
    	String verilogFilepath = filepath.toString() + Utils.getFileSeparator() + name + ".v";
    	Utils.createFile(verilogFilepath);
    	Utils.writeToFile(specification.getVerilog(), verilogFilepath);
    	this.verilogFile = verilogFilepath;
    	// options
    	String optionsFilepath = filepath.toString() + Utils.getFileSeparator() + name + "_options.csv";
    	Utils.createFile(optionsFilepath);
    	try {
			Utils.writeToFile(specification.getSettings().toCSV(), optionsFilepath);
		} catch (IOException e) {
			throw new ProjectException(e);
		}
    	this.optionsFile = optionsFilepath;
    	// netlist constraint
    	String netlistConstraintFilepath = filepath.toString() + Utils.getFileSeparator() + name + "_netlistconstraints.json";
    	Utils.createFile(netlistConstraintFilepath);
    	try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(netlistConstraintFilepath), specification.getConstraints());
		} catch (IOException e) {
			throw new ProjectException(e);
		}
    	this.netlistConstraintFile = netlistConstraintFilepath;
    	// target data
    	String targetDataFilepath = filepath.toString() + Utils.getFileSeparator() + name + "_targetdata.json";
    	Utils.createFile(targetDataFilepath);
    	try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(targetDataFilepath), specification.getLibraryResource().getLibrary());
		} catch (IOException | LibraryException e) {
			throw new ProjectException(e);
		}
    	this.userConstraintsFile = targetDataFilepath;
    }
    
	public abstract void execute() throws CelloWebException;
	
	public void delete() throws IOException {
		FileUtils.deleteDirectory(new File(this.getFilepath()));
	}
	
	/**
	 * Getter for <i>id</i>
	 * @return value of <i>id</i>
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Setter for <i>id</i>
	 * @param id the value to set <i>id</i>
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Getter for <i>name</i>
	 * @return value of <i>name</i>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for <i>name</i>
	 * @param name the value to set <i>name</i>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for <i>filepath</i>
	 * @return value of <i>filepath</i>
	 */
	public String getFilepath() {
		return filepath;
	}

	/**
	 * Setter for <i>filepath</i>
	 * @param filepath the value to set <i>filepath</i>
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	/**
	 * Getter for <i>created</i>
	 * @return value of <i>created</i>
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Setter for <i>created</i>
	 * @param created the value to set <i>created</i>
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Getter for <i>verilogFile</i>
	 * @return value of <i>verilogFile</i>
	 */
	public String getVerilogFile() {
		return verilogFile;
	}

	/**
	 * Setter for <i>verilogFile</i>
	 * @param verilogFile the value to set <i>verilogFile</i>
	 */
	public void setVerilogFile(String verilogFile) {
		this.verilogFile = verilogFile;
	}

	/**
	 * Getter for <i>optionsFile</i>
	 * @return value of <i>optionsFile</i>
	 */
	public String getOptionsFile() {
		return optionsFile;
	}

	/**
	 * Setter for <i>optionsFile</i>
	 * @param optionsFile the value to set <i>optionsFile</i>
	 */
	public void setOptionsFile(String optionsFile) {
		this.optionsFile = optionsFile;
	}

	/**
	 * Getter for <i>netlistConstraintFile</i>
	 * @return value of <i>netlistConstraintFile</i>
	 */
	public String getNetlistConstraintFile() {
		return netlistConstraintFile;
	}

	/**
	 * Setter for <i>netlistConstraintFile</i>
	 * @param netlistConstraintFile the value to set <i>netlistConstraintFile</i>
	 */
	public void setNetlistConstraintFile(String netlistConstraintFile) {
		this.netlistConstraintFile = netlistConstraintFile;
	}

	/**
	 * Getter for <i>userConstraintsFile</i>
	 * 
	 * @return value of <i>userConstraintsFile</i>
	 */
	public String getUserConstraintsFile() {
		return userConstraintsFile;
	}

	/**
	 * Setter for <i>userConstraintsFile</i>
	 * 
	 * @param userConstraintsFile the value to set <i>userConstraintsFile</i>
	 */
	public void setUserConstraintsFile(String userConstraintsFile) {
		this.userConstraintsFile = userConstraintsFile;
	}

	/**
	 * Getter for <i>inputSensorFile</i>
	 * @return value of <i>inputSensorFile</i>
	 */
	public String getInputSensorFile() {
		return inputSensorFile;
	}

	/**
	 * Setter for <i>inputSensorFile</i>
	 * @param inputSensorFile the value to set <i>inputSensorFile</i>
	 */
	public void setInputSensorFile(String inputSensorFile) {
		this.inputSensorFile = inputSensorFile;
	}

	/**
	 * Getter for <i>outputDeviceFile</i>
	 * @return value of <i>outputDeviceFile</i>
	 */
	public String getOutputDeviceFile() {
		return outputDeviceFile;
	}

	/**
	 * Setter for <i>outputDeviceFile</i>
	 * @param outputDeviceFile the value to set <i>outputDeviceFile</i>
	 */
	public void setOutputDeviceFile(String outputDeviceFile) {
		this.outputDeviceFile = outputDeviceFile;
	}

	/**
	 * Getter for <i>results</i>
	 * @return value of <i>results</i>
	 */
	public Collection<Result> getResults() {
		return results;
	}

	/**
	 * Setter for <i>results</i>
	 * @param results the value to set <i>results</i>
	 */
	public void setResults(Collection<Result> results) {
		this.results = results;
	}

}
