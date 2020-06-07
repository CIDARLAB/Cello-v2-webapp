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

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Constants representing the different overwrite strategies available on SynBioHub. These
 * correspond to the valid values for the {@code overwrite_merge} request parameter in SynBioHub.
 *
 * @see <a href="https://synbiohub.github.io/api-docs/#submit">SynBioHub API</a>
 * @author Timothy Jones
 * @date 2020-06-06
 */
public enum OverwriteMerge {
  /** Prevent overwrite if the submission exists. */
  PREVENT_IF_EXISTS("0"),
  /** Overwrite if the submission exists. */
  OVERWRITE_IF_EXISTS("1"),
  /** Merge and prevent overwrite if the submission exists. */
  MERGE_AND_PREVENT_IF_EXISTS("2"),
  /** Merge and overwrite matching URIs. */
  MERGE_AND_OVERWRITE_MATCHING_URIS("3");

  private final String code;

  OverwriteMerge(final String code) {
    this.code = code;
  }

  @JsonValue
  public String getValue() {
    return this.code;
  }
}
