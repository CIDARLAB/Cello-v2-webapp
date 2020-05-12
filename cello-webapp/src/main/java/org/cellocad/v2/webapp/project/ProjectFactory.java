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

import org.cellocad.v2.webapp.common.CObject;
import org.cellocad.v2.webapp.exception.ProjectException;
import org.cellocad.v2.webapp.project.DNACompiler.DNACompilerProject;
import org.cellocad.v2.webapp.specification.Specification;
import org.cellocad.v2.webapp.user.ApplicationUser;

/**
 * A factory for different project types.
 *
 * @author Timothy Jones
 * @date Feb 16, 2019
 */
public class ProjectFactory extends CObject {

  /**
   * Create a new project of the specified type.
   *
   * @param user The user the project belongs to.
   * @param name The name of the project.
   * @param specification The project specification. The specification contains the application
   *     definition that determines the project type of the factory.
   * @return A new project.
   * @throws ProjectException Unable to generate the project.
   */
  public Project getProject(
      final ApplicationUser user, final String name, final Specification specification)
      throws ProjectException {
    Project rtn = null;
    if (specification.getSettings().getApplication().equals("DNACompiler")) {
      rtn = new DNACompilerProject(user, name, specification);
    }
    return rtn;
  }
}
