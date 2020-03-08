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
package org.cellocad.v2.webapp.project;

import org.cellocad.v2.webapp.ApplicationUtils;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.user.ApplicationUser;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-02-16
 *
 */
public class ProjectUtils {

    /**
     * Get the projects directory for the specified user.
     * 
     * @param user The user.
     * @return The projects directory.
     */
    public static String getUserDirectory(ApplicationUser user) {
        String rtn = "";
        rtn += ApplicationUtils.getProjectsDirectory() + Utils.getFileSeparator() + user.getUsername().toString();
        return rtn;
    }

    /**
     * Create a projects directory for the specified user.
     * 
     * @param user The user.
     */
    public static void createUserDirectory(ApplicationUser user) {
        String path = ProjectUtils.getUserDirectory(user);
        Utils.makeDirectory(path);
    }

    /**
     * Get the project directory for the specified user and project.
     * 
     * @param user The user.
     * @param name The project name.
     * @return The project directory.
     */
    public static String getProjectDirectory(ApplicationUser user, String name) {
        String rtn = "";
        rtn += ProjectUtils.getUserDirectory(user);
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
    public static String createProjectDirectory(ApplicationUser user, String name) {
        String rtn = ProjectUtils.getProjectDirectory(user, name);
        Utils.makeDirectory(rtn);
        return rtn;
    }

}
