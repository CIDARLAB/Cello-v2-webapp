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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.common.CObject;
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.exception.CelloWebException;
import org.cellocad.cello2.webapp.exception.LibraryException;
import org.cellocad.cello2.webapp.exception.ProjectException;
import org.cellocad.cello2.webapp.results.Result;
import org.cellocad.cello2.webapp.specification.Specification;
import org.cellocad.cello2.webapp.user.ApplicationUser;
import org.cidarlab.eugene.util.FileUtils;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public abstract class Project extends CObject {

	@Id
    private ObjectId id;
    private File filepath;
    private Date created;
    
    private File verilogFile;
    private File optionsFile;
    private File netlistConstraintFile;
    private File targetDataFile;

    private Collection<Result> results;
    private static final Logger logger = LogManager.getLogger(Project.class.getSimpleName());
    
    public Project(ApplicationUser user, String name, Specification specification) throws ProjectException {
    	super(name,-1,-1);
    	ObjectMapper mapper = new ObjectMapper();
    	ProjectUtils.createProjectDirectory(user,name);
    	this.id = new ObjectId();
    	this.filepath = new File(ProjectUtils.getProjectDirectory(user,name));
    	// verilog
    	String verilogFilepath = filepath.toString() + Utils.getFileSeparator() + name + ".v";
    	Utils.createFile(verilogFilepath);
    	Utils.writeToFile(specification.getVerilog(), verilogFilepath);
    	this.verilogFile = new File(verilogFilepath);
    	// options
    	String optionsFilepath = filepath.toString() + Utils.getFileSeparator() + name + "_options.csv";
    	Utils.createFile(optionsFilepath);
    	try {
			Utils.writeToFile(specification.getSettings().toCSV(), optionsFilepath);
		} catch (IOException e) {
			throw new ProjectException(e);
		}
    	this.optionsFile = new File(optionsFilepath);
    	// netlist constraint
    	String netlistConstraintFilepath = filepath.toString() + Utils.getFileSeparator() + name + "_netlistconstraints.json";
    	Utils.createFile(netlistConstraintFilepath);
    	try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(netlistConstraintFilepath), specification.getConstraints());
		} catch (IOException e) {
			throw new ProjectException(e);
		}
    	this.netlistConstraintFile = new File(netlistConstraintFilepath);
    	// target data
    	String targetDataFilepath = filepath.toString() + Utils.getFileSeparator() + name + "_targetdata.json";
    	Utils.createFile(targetDataFilepath);
    	try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(targetDataFilepath), specification.getLibraryResource().getLibrary());
		} catch (IOException | LibraryException e) {
			throw new ProjectException(e);
		}
    	this.targetDataFile = new File(targetDataFilepath);
    }
    
	public abstract void execute() throws CelloWebException;
	
	public void delete() throws IOException {
		FileUtils.deleteDirectory(this.getFilepath());
	}
	
	/**
	 * Getter for <i>id</i>
	 * @return value of <i>id</i>
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Getter for <i>filepath</i>
	 * @return value of <i>filepath</i>
	 */
	public File getFilepath() {
		return filepath;
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
	public File getVerilogFile() {
		return verilogFile;
	}

	/**
	 * Getter for <i>optionsFile</i>
	 * @return value of <i>optionsFile</i>
	 */
	public File getOptionsFile() {
		return optionsFile;
	}

	/**
	 * Getter for <i>netlistConstraintFile</i>
	 * @return value of <i>netlistConstraintFile</i>
	 */
	public File getNetlistConstraintFile() {
		return netlistConstraintFile;
	}

	/**
	 * Getter for <i>targetDataFile</i>
	 * @return value of <i>targetDataFile</i>
	 */
	public File getTargetDataFile() {
		return targetDataFile;
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

	/**
	 *  Returns the Logger instance for the class
	 *  @return the Logger instance for the class
	 */
	protected Logger getLogger() {
		return Project.logger;
	}

}
