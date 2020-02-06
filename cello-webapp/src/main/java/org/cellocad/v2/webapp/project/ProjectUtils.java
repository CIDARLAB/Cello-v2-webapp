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
	 * @param userId
	 * @return
	 */
	public static String getUserDirectory(ApplicationUser user) {
		String rtn = "";
		rtn += ApplicationUtils.getProjectsDirectory() + Utils.getFileSeparator() + user.getUsername().toString();
		return rtn;
	}

	/**
	 * @param user
	 */
	public static void createUserDirectory(ApplicationUser user) {
		String path = ProjectUtils.getUserDirectory(user);
		Utils.makeDirectory(path);
	}

	/**
	 * @param userId
	 * @param name
	 * @return
	 */
	public static String getProjectDirectory(ApplicationUser user, String name) {
		String rtn = "";
		rtn += ProjectUtils.getUserDirectory(user);
		rtn += Utils.getFileSeparator();
		rtn += name;
		return rtn;
	}

	/**
	 * @param userId
	 * @param name
	 * @return
	 */
	public static String createProjectDirectory(ApplicationUser user, String name) {
		String rtn = ProjectUtils.getProjectDirectory(user,name);
		Utils.makeDirectory(rtn);
		return rtn;
	}

}
