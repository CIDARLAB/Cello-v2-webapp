/*
 * Copyright (C) 2019 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.cellocad.v2.webapp.specification.library;

import java.io.File;

/**
 * A resource indicator, in this case a set of files in JSON format, pointing to a user constraints
 * or "target data" style Cello library.
 *
 * @author Timothy Jones
 * @date 2019-03-19
 */
public class TargetDataLibraryResource extends LibraryResource {

  private File userConstraintsFile;
  private File inputSensorFile;
  private File outputDeviceFile;

  /**
   * Initialize a new target data library resource.
   *
   * @param userConstraintsFile The user constraints file.
   * @param inputSensorFile The input sensor file.
   * @param outputDeviceFile The output device file.
   */
  public TargetDataLibraryResource(
      final File userConstraintsFile, final File inputSensorFile, final File outputDeviceFile) {
    this.userConstraintsFile = userConstraintsFile;
    this.inputSensorFile = inputSensorFile;
    this.outputDeviceFile = outputDeviceFile;
  }

  /**
   * Getter for {@code userConstraintsFile}.
   *
   * @return The value of {@code userConstraintsFile}.
   */
  public File getUserConstraintsFile() {
    return userConstraintsFile;
  }

  /**
   * Setter for {@code userConstraintsFile}.
   *
   * @param userConstraintsFile The value to set {@code userConstraintsFile}.
   */
  public void setUserConstraintsFile(File userConstraintsFile) {
    this.userConstraintsFile = userConstraintsFile;
  }

  /**
   * Getter for {@code inputSensorFile}.
   *
   * @return The value of {@code inputSensorFile}.
   */
  public File getInputSensorFile() {
    return inputSensorFile;
  }

  /**
   * Setter for {@code inputSensorFile}.
   *
   * @param inputSensorFile The value to set {@code inputSensorFile}.
   */
  public void setInputSensorFile(File inputSensorFile) {
    this.inputSensorFile = inputSensorFile;
  }

  /**
   * Getter for {@code outputDeviceFile}.
   *
   * @return The value of {@code outputDeviceFile}.
   */
  public File getOutputDeviceFile() {
    return outputDeviceFile;
  }

  /**
   * Setter for {@code outputDeviceFile}.
   *
   * @param outputDeviceFile The value to set {@code outputDeviceFile}.
   */
  public void setOutputDeviceFile(File outputDeviceFile) {
    this.outputDeviceFile = outputDeviceFile;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.cellocad.v2.webapp.specification.library.LibraryTemplate#getLibrary()
   */
  @Override
  public Library getLibrary() {
    final Library rtn = null;
    return rtn;
  }
}
