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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * A library file header.
 *
 * @author Timothy Jones
 * @date 2020-05-30
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Header {

  private String description;
  private String version;
  private String date;
  private List<String> author;
  private String organism;
  private String genome;
  private String media;
  private String temperature;
  private String growth;

  /**
   * Getter for {@code description}.
   *
   * @return The value of {@code description}.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Getter for {@code version}.
   *
   * @return The value of {@code version}.
   */
  public String getVersion() {
    return version;
  }

  /**
   * Getter for {@code date}.
   *
   * @return The value of {@code date}.
   */
  public String getDate() {
    return date;
  }

  /**
   * Getter for {@code author}.
   *
   * @return The value of {@code author}.
   */
  public List<String> getAuthor() {
    return author;
  }

  /**
   * Getter for {@code organism}.
   *
   * @return The value of {@code organism}.
   */
  public String getOrganism() {
    return organism;
  }

  /**
   * Getter for {@code genome}.
   *
   * @return The value of {@code genome}.
   */
  public String getGenome() {
    return genome;
  }

  /**
   * Getter for {@code media}.
   *
   * @return The value of {@code media}.
   */
  public String getMedia() {
    return media;
  }

  /**
   * Getter for {@code temperature}.
   *
   * @return The value of {@code temperature}.
   */
  public String getTemperature() {
    return temperature;
  }

  /**
   * Getter for {@code growth}.
   *
   * @return The value of {@code growth}.
   */
  public String getGrowth() {
    return growth;
  }
}
