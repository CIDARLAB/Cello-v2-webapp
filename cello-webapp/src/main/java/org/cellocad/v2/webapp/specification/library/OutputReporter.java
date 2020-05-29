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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.cellocad.v2.webapp.exception.LibraryException;
import org.cellocad.v2.webapp.specification.library.serialization.OutputReporterSerializer;
import org.sbolstandard.core2.Component;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLValidationException;

/**
 * An output reporter.
 *
 * @author Timothy Jones
 * @date 2019-03-20
 */
@JsonSerialize(using = OutputReporterSerializer.class)
public class OutputReporter {

  private String name;
  private Collection<Part> parts;
  private URI uri;

  /**
   * Initialze an output reporter.
   *
   * @param name The name of the reporter.
   * @param parts The parts associate with the reporter.
   */
  public OutputReporter(final String name, final Collection<Part> parts, final URI uri) {
    this.name = name;
    this.parts = parts;
    this.uri = uri;
  }

  /**
   * Initialize an output reporter from an SBOL component definition.
   *
   * @param cd A component definition.
   * @param document The SBOL document containing the component definition.
   * @throws LibraryException Unable to build the output reporter.
   */
  public OutputReporter(final ComponentDefinition cd, final SBOLDocument document)
      throws LibraryException {
    name = cd.getDisplayId();
    parts = new ArrayList<>();
    List<Component> components;
    try {
      components = cd.getSortedComponents();
    } catch (final SBOLValidationException e) {
      throw new LibraryException("Error with ComponentDefinition.");
    }
    for (final Component c : components) {
      parts.add(new Part(c.getDefinition()));
    }
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
   * Getter for {@code parts}.
   *
   * @return The value of {@code parts}.
   */
  public Collection<Part> getParts() {
    return parts;
  }

  /**
   * Getter for {@code uri}.
   *
   * @return The value of {@code uri}.
   */
  public URI getUri() {
    return uri;
  }
}
