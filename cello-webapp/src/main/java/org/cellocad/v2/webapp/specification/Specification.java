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

package org.cellocad.v2.webapp.specification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.cellocad.v2.webapp.specification.constraints.Constraints;
import org.cellocad.v2.webapp.specification.library.LibraryResource;
import org.cellocad.v2.webapp.specification.settings.Settings;

/**
 * A project specification.
 *
 * @author Timothy Jones
 * @date Feb 16, 2019
 */
public class Specification {

  private String verilog;
  private Settings settings;
  private Constraints constraints;
  private LibraryResource libraryResource;

  /**
   * Initialize a project specification.
   *
   * @param verilog The Verilog text.
   * @param settings The settings.
   * @param constraints The constraints.
   * @param resource The library resource.
   */
  @JsonCreator
  public Specification(
      @JsonProperty("verilog") final String verilog,
      @JsonProperty("settings") final Settings settings,
      @JsonProperty("constraints") final Constraints constraints,
      @JsonProperty("library") final LibraryResource resource) {
    this.verilog = verilog;
    this.settings = settings;
    this.constraints = constraints;
    libraryResource = resource;
  }

  /**
   * Getter for {@code verilog}.
   *
   * @return The value of {@code verilog}.
   */
  public String getVerilog() {
    return verilog;
  }

  /**
   * Getter for {@code settings}.
   *
   * @return The value of {@code settings}.
   */
  public Settings getSettings() {
    return settings;
  }

  /**
   * Getter for {@code constraints}.
   *
   * @return The value of {@code constraints}.
   */
  public Constraints getConstraints() {
    return constraints;
  }

  /**
   * Getter for {@code libraryResource}.
   *
   * @return The value of {@code libraryResource}.
   */
  public LibraryResource getLibraryResource() {
    return libraryResource;
  }
}
