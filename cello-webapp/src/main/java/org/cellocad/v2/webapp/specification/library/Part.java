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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.Objects;
import org.cellocad.v2.webapp.exception.LibraryException;
import org.cellocad.v2.webapp.specification.library.serialization.LibrarySerializationConstants;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.SequenceOntology;

/**
 * A part.
 *
 * @author Timothy Jones
 * @date 2019-03-19
 */
public class Part {

  @JsonProperty(LibrarySerializationConstants.S_UCF_COLLECTION)
  private static final String S_UCF_COLLECTION = "parts";

  private String name;
  private String type;

  @JsonProperty("dnasequence")
  private String sequence;

  private URI uri;

  /**
   * Initialize a part from an SBOL component definition.
   *
   * @param cd A component definition.
   * @throws LibraryException Unable to initialize part.
   */
  public Part(final ComponentDefinition cd) throws LibraryException {
    name = cd.getDisplayId();
    try {
      sequence = cd.getSequences().iterator().next().getElements();
    } catch (final NullPointerException e) {
      throw new LibraryException("Error with Part sequence.");
    }
    final URI role = cd.getRoles().iterator().next();
    String type = "";
    if (role.equals(SequenceOntology.PROMOTER)) {
      type = "promoter";
    }
    if (role.equals(SequenceOntology.CDS)) {
      type = "cds";
    }
    if (role.equals(SequenceOntology.RIBOSOME_ENTRY_SITE)) {
      type = "rbs";
    }
    if (role.equals(SequenceOntology.TERMINATOR)) {
      type = "terminator";
    }
    if (role.equals(URI.create("http://identifiers.org/so/SO:0000374"))) {
      type = "ribozyme";
    }
    if (role.equals(URI.create("http://identifiers.org/so/SO:0001953"))) {
      type = "scar";
    }
    if (cd.getDisplayId().equals("backbone")) {
      type = "backbone";
    }
    this.type = type;
    uri = cd.getIdentity();
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
   * Getter for {@code type}.
   *
   * @return The value of {@code type}.
   */
  public String getType() {
    return type;
  }

  /**
   * Getter for {@code sequence}.
   *
   * @return The value of {@code sequence}.
   */
  public String getSequence() {
    return sequence;
  }

  /**
   * Getter for {@code uri}.
   *
   * @return The value of {@code uri}.
   */
  public URI getUri() {
    return uri;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, sequence, type, uri);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Part)) {
      return false;
    }
    final Part other = (Part) obj;
    return Objects.equals(name, other.name)
        && Objects.equals(sequence, other.sequence)
        && Objects.equals(type, other.type)
        && Objects.equals(uri, other.uri);
  }
}
