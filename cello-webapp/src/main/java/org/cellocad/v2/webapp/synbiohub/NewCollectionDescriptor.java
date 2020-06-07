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

package org.cellocad.v2.webapp.synbiohub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A collection to be created on a SynBioHub instance.
 *
 * @author Timothy Jones
 * @date 2020-06-06
 */
public class NewCollectionDescriptor extends SubmittableCollectionDescriptor {

  private String name;
  private String description;
  private String id;
  private String version;
  private String citations;
  private OverwriteMerge overwrite;

  /** Initializes a newly created {@link NewCollectionDescriptor}. */
  @JsonCreator
  public NewCollectionDescriptor(
      @JsonProperty("name") final String name,
      @JsonProperty("description") final String description,
      @JsonProperty("id") final String id,
      @JsonProperty("version") final String version,
      @JsonProperty("citations") final String citations,
      @JsonProperty("overwrite") final OverwriteMerge overwrite) {
    this.name = name;
    this.description = description;
    this.id = id;
    this.version = version;
    this.citations = citations;
    this.overwrite = overwrite;
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
   * Getter for {@code description}.
   *
   * @return The value of {@code description}.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Getter for {@code id}.
   *
   * @return The value of {@code id}.
   */
  public String getId() {
    return id;
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
   * Getter for {@code citations}.
   *
   * @return The value of {@code citations}.
   */
  public String getCitations() {
    return citations;
  }

  /**
   * Getter for {@code overwrite}.
   *
   * @return The value of {@code overwrite}.
   */
  public OverwriteMerge getOverwrite() {
    return overwrite;
  }
}
