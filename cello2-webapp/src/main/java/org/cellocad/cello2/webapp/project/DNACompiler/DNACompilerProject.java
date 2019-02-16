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
package org.cellocad.cello2.webapp.project.DNACompiler;

import org.cellocad.cello2.webapp.project.ApplicationProject;
import org.cellocad.cello2.webapp.specification.DNACompiler.DNACompilerSpecification;

import java.util.ArrayList;
import java.util.List;

import org.cellocad.cello2.DNACompiler.runtime.Main;
import org.cellocad.cello2.DNACompiler.runtime.environment.DNACompilerArgString;

/**
 * 
 * 
 * @author Timothy Jones
 * 
 * @date 2019-02-16
 *
 */
public class DNACompilerProject extends ApplicationProject {
	
	public class MainRunnable implements Runnable {

	    private String[] args;

	    public MainRunnable(final String[] args) {
	        this.args = args;
	    }

	    public void run() {
	    	Main.main(this.args);
	    }
	}
	
	private DNACompilerSpecification specification;

	/**
	 * @param userId
	 * @param directory
	 * @param jobId
	 */
	public DNACompilerProject(String userId, String jobId, String directory) {
		super(userId, jobId, directory);
	}
	
	/**
	 * Getter for <i>specification</i>
	 * @return value of <i>specification</i>
	 */
	public DNACompilerSpecification getSpecification() {
		return specification;
	}

	/**
	 * Setter for <i>specification</i>
	 * @param specification the value to set <i>specification</i>
	 */
	public void setSpecification(DNACompilerSpecification specification) {
		this.specification = specification;
	}

	/* (non-Javadoc)
	 * @see org.cellocad.cello2.webapp.project.Project#execute()
	 */
	@Override
	public void execute() {
		List<String> args = new ArrayList<>();
		args.add(DNACompilerArgString.INPUTNETLIST);
		args.add(this.getSpecification().getVerilogFile().getAbsolutePath());
		args.add(DNACompilerArgString.TARGETDATAFILE);
		args.add(this.getSpecification().getTargetDataFile().getAbsolutePath());
		args.add(DNACompilerArgString.NETLISTCONSTRAINTFILE);
		args.add(this.getSpecification().getNetlistConstraintFile().getAbsolutePath());
		args.add(DNACompilerArgString.OPTIONS);
		args.add(this.getSpecification().getOptionsFile().getAbsolutePath());
		args.add(DNACompilerArgString.OUTPUTDIR);
		args.add(this.getDirectory());
		// main
		MainRunnable main = new MainRunnable((String[])args.toArray());
        Thread t = new Thread(main);
        t.start();
		
	}

}
