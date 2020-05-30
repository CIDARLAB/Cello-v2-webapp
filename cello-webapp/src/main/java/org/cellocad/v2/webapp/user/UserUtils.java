/*
 * Copyright (C) 2020 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cellocad.v2.webapp.user;

import java.io.IOException;
import org.cellocad.v2.webapp.ApplicationUtils;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.project.ProjectUtils;
import org.cellocad.v2.webapp.resource.UserResourceUtils;

/**
 * Utilities for {@link ApplicationUser}.
 *
 * @author Timothy Jones
 * @date 2020-05-30
 */
public class UserUtils {

  /**
   * Get the users directory.
   *
   * @return The users directory.
   */
  public static String getUsersDirectory() {
    String rtn = "";
    rtn += ApplicationUtils.getApplicationDirectory() + "users";
    return rtn;
  }

  private static void createUsersDirectory() {
    Utils.makeDirectory(getUsersDirectory());
  }

  /** Create the projects directory. */
  public static void initUsersDirectory() {
    createUsersDirectory();
  }

  /**
   * Get the projects directory for the specified user.
   *
   * @param user The user.
   * @return The projects directory.
   */
  public static String getUserDirectory(final ApplicationUser user) {
    String rtn = "";
    rtn += getUsersDirectory() + Utils.getFileSeparator() + user.getUsername().toString();
    return rtn;
  }

  /**
   * Create a projects directory for the specified user.
   *
   * @param user The user.
   */
  private static void createUserDirectory(final ApplicationUser user) {
    final String path = getUserDirectory(user);
    Utils.makeDirectory(path);
  }

  /**
   * Initialize a user directory.
   *
   * @param user The user.
   * @throws IOException Unable to initialize user directory.
   */
  public static void initUserDirectory(final ApplicationUser user) throws IOException {
    createUserDirectory(user);
    ProjectUtils.initUserProjectsDirectory(user);
    UserResourceUtils.initResources(user);
  }
}
