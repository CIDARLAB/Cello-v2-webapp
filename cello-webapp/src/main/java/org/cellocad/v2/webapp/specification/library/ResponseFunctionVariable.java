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

/**
 * A response function variable.
 *
 * @author Timothy Jones
 * @date 2019-03-19
 */
public class ResponseFunctionVariable {

  private String name;
  private Double offThreshold;
  private Double onThreshold;

  /**
   * A response function variable.
   *
   * @param name The name of the variable.
   * @param offThreshold The off threshold.
   * @param onThreshold The on threshold.
   */
  public ResponseFunctionVariable(
      final String name, final Double offThreshold, final Double onThreshold) {
    this.name = name;
    this.offThreshold = offThreshold;
    this.onThreshold = onThreshold;
  }

  /**
   * Getter for {@code name}.
   *
   * @return The value of {@code name}.
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for {@code offThreshold}.
   *
   * @return The value of {@code offThreshold}.
   */
  public Double getOffThreshold() {
    return offThreshold;
  }

  /**
   * Setter for {@code offThreshold}.
   *
   * @param offThreshold The value to set {@code offThreshold}.
   */
  public void setOffThreshold(final Double offThreshold) {
    this.offThreshold = offThreshold;
  }

  /**
   * Getter for {@code onThreshold}.
   *
   * @return The value of {@code onThreshold}.
   */
  public Double getOnThreshold() {
    return onThreshold;
  }

  /**
   * Setter for {@code onThreshold}.
   *
   * @param onThreshold the value to set {@code onThreshold}.
   */
  public void setOnThreshold(final Double onThreshold) {
    this.onThreshold = onThreshold;
  }
}
