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

package org.cellocad.v2.webapp;

import org.cellocad.v2.webapp.common.Utils;

/**
 * Utility methods for the {@link Application}.
 *
 * @author Timothy Jones
 * @date 2019-02-23
 */
public class ApplicationUtils {

  // https://github.com/spring-projects/spring-boot/blob/4b670f8696e252f4a5cc596b9f8a96ca7978daa1/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/system/ApplicationHome.java#L105
  private static boolean isUnitTest() {
    try {
      final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      for (int i = stackTrace.length - 1; i >= 0; i--) {
        if (stackTrace[i].getClassName().startsWith("org.junit.")) {
          return true;
        }
      }
    } catch (final Exception e) {
      return false;
    }
    return false;
  }

  /**
   * Get the application directory.
   *
   * @return The application directory.
   */
  public static String getApplicationDirectory() {
    String rtn = "";
    rtn += Utils.getFilepath();
    if (ApplicationUtils.isUnitTest()) {
      rtn += "target";
      rtn += Utils.getFileSeparator();
      rtn += "test-classes";
      rtn += Utils.getFileSeparator();
    }
    return rtn;
  }

  /**
   * Get the resources directory.
   *
   * @return The resources directory.
   */
  public static String getResourcesDirectory() {
    String rtn = "";
    rtn += ApplicationUtils.getApplicationDirectory() + "resources";
    return rtn;
  }

  /** Create the resources directory. */
  public static void createResourcesDirectory() {
    Utils.makeDirectory(ApplicationUtils.getResourcesDirectory());
  }
}
