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

import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.cellocad.v2.webapp.user.UserUtils;

/**
 * A Utils class for user projects.
 *
 * @author Timothy Jones
 * @date 2019-02-16
 */
public class ProjectUtils {

  /**
   * Get the projects directory of a user.
   *
   * @param user The user.
   * @return The projects directory of the user.
   */
  public static String getUserProjectsDirectory(final ApplicationUser user) {
    String rtn = "";
    rtn += UserUtils.getUserDirectory(user);
    rtn += Utils.getFileSeparator();
    rtn += "projects";
    return rtn;
  }

  private static void createUserProjectsDirectory(final ApplicationUser user) {
    final String path = getUserProjectsDirectory(user);
    Utils.makeDirectory(path);
  }

  public static void initUserProjectsDirectory(final ApplicationUser user) {
    createUserProjectsDirectory(user);
  }

  /**
   * Get the project directory for the specified project of the specified user.
   *
   * @param user The user.
   * @param name The project name.
   * @return The project directory.
   */
  public static String getUserProjectDirectory(final ApplicationUser user, final String name) {
    String rtn = "";
    rtn += getUserProjectsDirectory(user);
    rtn += Utils.getFileSeparator();
    rtn += name;
    return rtn;
  }

  /**
   * Create a project directory for the specified user and project.
   *
   * @param user The user.
   * @param name The project name.
   * @return The directory created.
   */
  private static String createUserProjectDirectory(final ApplicationUser user, final String name) {
    final String rtn = ProjectUtils.getUserProjectDirectory(user, name);
    Utils.makeDirectory(rtn);
    return rtn;
  }

  public static void initUserProjectDirectory(final ApplicationUser user, final String name) {
    createUserProjectDirectory(user, name);
  }
}
