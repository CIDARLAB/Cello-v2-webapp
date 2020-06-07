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

package org.cellocad.v2.webapp.project.DNACompiler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.apache.logging.log4j.ThreadContext;
import org.cellocad.v2.DNACompiler.runtime.Main;
import org.cellocad.v2.common.exception.CelloException;
import org.cellocad.v2.common.runtime.environment.ArgString;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.exception.ProjectException;
import org.cellocad.v2.webapp.project.Project;
import org.cellocad.v2.webapp.specification.Specification;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A project for the {@code DNACompiler} application.
 *
 * @author Timothy Jones
 * @date 2019-02-16
 */
@Document(collection = "projects")
@TypeAlias("dnaCompilerProject")
public class DNACompilerProject extends Project {

  public class DNACompilerMainCallable implements Callable<Void> {

    private String[] args;
    private String outputDir;

    public DNACompilerMainCallable(final String[] args, final String outputDir) {
      this.args = args;
      this.outputDir = outputDir;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Void call() throws CelloException {
      ThreadContext.put("logFilepath", outputDir);
      Main.main(args);
      return null;
    }
  }

  public DNACompilerProject() {
    super();
  }

  public DNACompilerProject(
      final String name,
      final String filepath,
      final Date created,
      final String verilogFile,
      final String optionsFile,
      final String netlistConstraintFile,
      final String targetDataFile) {
    super(name, filepath, created, verilogFile, optionsFile, netlistConstraintFile, targetDataFile);
  }
  // protected DNACompilerProject(Path filepath, Date created, Path verilogFile,
  // Path
  // optionsFile, Path netlistConstraintFile, Path targetDataFile) {
  // super(filepath,created,verilogFile,optionsFile,netlistConstraintFile,targetDataFile);
  // }

  /**
   * Initialize a {@code DNACompiler} project.
   *
   * @param user The user to whom the project belongs.
   * @param specification The project specification.
   * @throws ProjectException Unable to initialize project.
   */
  public DNACompilerProject(final ApplicationUser user, final Specification specification)
      throws ProjectException {
    super(user, specification);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.cellocad.v2.webapp.project.Project#execute()
   */
  @Override
  public void execute() throws CelloWebException {
    final List<String> args = new ArrayList<>();
    args.add("-" + ArgString.INPUTNETLIST);
    args.add(getVerilogFile());
    args.add("-" + ArgString.USERCONSTRAINTSFILE);
    args.add(getTargetDataFile());
    args.add("-" + ArgString.INPUTSENSORFILE);
    args.add(getInputSensorFile());
    args.add("-" + ArgString.OUTPUTDEVICEFILE);
    args.add(getOutputDeviceFile());
    args.add("-" + ArgString.NETLISTCONSTRAINTFILE);
    args.add(getNetlistConstraintFile());
    args.add("-" + ArgString.OPTIONS);
    args.add(getOptionsFile());
    args.add("-" + ArgString.OUTPUTDIR);
    args.add(getFilepath().toString());
    args.add("-" + ArgString.PYTHONENV);
    // FIXME
    args.add("python");
    // main
    final DNACompilerMainCallable main =
        new DNACompilerMainCallable(args.toArray(new String[1]), getFilepath().toString());
    final ExecutorService executor = new ScheduledThreadPoolExecutor(5);
    final Future<Void> future = executor.submit(main);
    try {
      future.get();
    } catch (ExecutionException | InterruptedException e) {
      throw new CelloWebException(e.getCause());
    }
    executor.shutdown();
  }
}
