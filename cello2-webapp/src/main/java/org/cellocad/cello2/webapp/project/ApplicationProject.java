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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.cello2.webapp.common.CObject;
import org.cellocad.cello2.webapp.specification.ApplicationSpecification;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public abstract class ApplicationProject extends CObject {

    private String userId;
    private String jobId;
    private String directory;
    private ApplicationSpecification specification;
    private static final Logger logger = LogManager.getLogger(ApplicationProject.class.getSimpleName());
    
    public ApplicationProject(String userId, String jobId, String directory) {
    	this.userId = userId;
    	this.jobId = jobId;
    	this.directory = directory;
    }
    
	public abstract void execute();

	/**
	 * Getter for <i>userid</i>
	 * @return value of <i>userid</i>
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Getter for <i>jobId</i>
	 * @return value of <i>jobId</i>
	 */
	public String getJobId() {
		return jobId;
	}
	
	/**
	 * Getter for <i>directory</i>
	 * @return value of <i>directory</i>
	 */
	public String getDirectory() {
		return directory;
	}
	
	/**
	 * Getter for <i>specification</i>
	 * @return value of <i>specification</i>
	 */
	public ApplicationSpecification getSpecification() {
		return specification;
	}

	/**
	 * Setter for <i>specification</i>
	 * @param specification the value to set <i>specification</i>
	 */
	public void setSpecification(ApplicationSpecification specification) {
		this.specification = specification;
	}

	/**
	 *  Returns the Logger instance for the class
	 *  @return the Logger instance for the class
	 */
	protected Logger getLogger() {
		return ApplicationProject.logger;
	}

}
