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

package org.cellocad.v2.webapp.resource.library;

/**
 * A descriptor for an input sensor file.
 *
 * @author Timothy Jones
 * @date 2020-05-30
 */
public class InputSensorFileDescriptor {

  private String file;
  private Header header;
  private Boolean isPrivate;

  /**
   * Getter for {@code file}.
   *
   * @return The value of {@code file}.
   */
  public String getFile() {
    return file;
  }

  /**
   * Setter for {@code file}.
   *
   * @param file The value to set {@code file}.
   */
  public void setFile(String file) {
    this.file = file;
  }

  /**
   * Getter for {@code header}.
   *
   * @return The value of {@code header}.
   */
  public Header getHeader() {
    return header;
  }

  /**
   * Setter for {@code header}.
   *
   * @param header The value to set {@code header}.
   */
  public void setHeader(Header header) {
    this.header = header;
  }

  /**
   * Getter for {@code isPrivate}.
   *
   * @return The value of {@code isPrivate}.
   */
  public Boolean getIsPrivate() {
    return isPrivate;
  }

  /**
   * Setter for {@code isPrivate}.
   *
   * @param isPrivate The value to set {@code isPrivate}.
   */
  public void setIsPrivate(Boolean isPrivate) {
    this.isPrivate = isPrivate;
  }
}
