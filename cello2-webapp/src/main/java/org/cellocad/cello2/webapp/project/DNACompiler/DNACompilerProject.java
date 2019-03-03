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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.cellocad.cello2.DNACompiler.runtime.Main;
import org.cellocad.cello2.DNACompiler.runtime.environment.DNACompilerArgString;
import org.cellocad.cello2.common.CelloException;
import org.cellocad.cello2.webapp.CelloWebException;
import org.cellocad.cello2.webapp.project.Project;

/**
 * 
 * 
 * @author Timothy Jones
 * 
 * @date 2019-02-16
 *
 */
public class DNACompilerProject extends Project {
	
	public class DNACompilerMainCallable implements Callable<Void> {
		
		private String[] args;
		
		public DNACompilerMainCallable(final String[] args) {
	        this.args = args;
	    }

		/* (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Void call() throws CelloException {
			Main.main(this.args);
			return null;
		}
		
	}
	
//	public class MainRunnable implements Runnable {
//
//	    private String[] args;
//
//	    public MainRunnable(final String[] args) {
//	        this.args = args;
//	    }
//
//	    public void run() {
//	    	Main.main(this.args);
//	    }
//	}

	/**
	 * @param userId
	 * @param directory
	 * @param jobId
	 */
	public DNACompilerProject(String userId, String jobId, String directory) {
		super(userId, jobId, directory);
	}

	/* (non-Javadoc)
	 * @see org.cellocad.cello2.webapp.project.Project#execute()
	 */
	@Override
	public void execute() throws CelloWebException {
		List<String> args = new ArrayList<>();
		args.add("-" + DNACompilerArgString.INPUTNETLIST);
		args.add(this.getSpecification().getVerilogFile());
		args.add("-" + DNACompilerArgString.TARGETDATAFILE);
		args.add(this.getSpecification().getTargetDataFile());
		args.add("-" + DNACompilerArgString.NETLISTCONSTRAINTFILE);
		args.add(this.getSpecification().getNetlistConstraintFile());
		args.add("-" + DNACompilerArgString.OPTIONS);
		args.add(this.getSpecification().getOptionsFile());
		args.add("-" + DNACompilerArgString.OUTPUTDIR);
		args.add(this.getDirectory());
		// main
		DNACompilerMainCallable main = new DNACompilerMainCallable(args.toArray(new String[1]));
		ExecutorService executor = new ScheduledThreadPoolExecutor(5);
		Future<Void> future = executor.submit(main);
        //Thread t = new Thread(main);
        //t.start();
		try {
			future.get();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new CelloWebException();
		}		
	}

}
