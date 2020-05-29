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
import java.util.Set;
import org.cellocad.v2.webapp.exception.LibraryException;
import org.cellocad.v2.webapp.specification.library.serialization.InputSensorSerializer;
import org.sbolstandard.core2.Component;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.Interaction;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLValidationException;
import org.sbolstandard.core2.SequenceOntology;

/**
 * An input sensor.
 *
 * @author Timothy Jones
 * @date 2019-03-20
 */
@JsonSerialize(using = InputSensorSerializer.class)
public class InputSensor {

  private String name;
  private Collection<Part> parts;
  private Part promoter;
  private Double low;
  private Double high;
  private URI uri;

  /**
   * Initialize a new input sensor.
   *
   * @param name The name of the sensor.
   * @param parts The parts associated with the sensor.
   * @param promoter The promoter associated with the sensor.
   * @param low The minimal expression of the sensor.
   * @param high The maximal expression of the sensor.
   * @param uri The URI pointing to the sensor definition.
   */
  public InputSensor(
      final String name,
      final Collection<Part> parts,
      final Part promoter,
      final Double low,
      final Double high,
      final URI uri) {
    this.name = name;
    this.parts = parts;
    this.promoter = promoter;
    this.low = low;
    this.high = high;
    this.uri = uri;
  }

  /**
   * Build an input sensor from an appropriately constructed {@link ComponentDefinition}, i.e. one
   * created by <a href=https://github.com/MyersResearchGroup/UCF2SBOL>UCF2SBOL</a>.
   *
   * @param cd The component definition encoding the input sensor.
   * @param document The document that contains the component definition.
   * @throws LibraryException Unable to build input sensor.
   */
  public InputSensor(final ComponentDefinition cd, final SBOLDocument document)
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
    ComponentDefinition cds = null;
    for (final Component c : cd.getComponents()) {
      final ComponentDefinition def = c.getDefinition();
      if (def.getRoles().contains(SequenceOntology.CDS)) {
        cds = def;
        break;
      }
    }
    if (cds == null) {
      throw new LibraryException("Error with ComponentDefinition.");
    }
    final Set<Interaction> regulations = SBOLUtils.getRegulations(document, cds);
    final Interaction regulation = regulations.iterator().next();
    low = Double.valueOf(SBOLUtils.getCelloAnnotationString(regulation, "ymax"));
    high = Double.valueOf(SBOLUtils.getCelloAnnotationString(regulation, "ymin"));
    final ComponentDefinition promoter = SBOLUtils.getRegulated(document, cds).iterator().next();
    this.promoter = new Part(promoter);
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
   * Getter for {@code promoter}.
   *
   * @return The value of {@code promoter}.
   */
  public Part getPromoter() {
    return promoter;
  }

  /**
   * Getter for {@code low}.
   *
   * @return The value of {@code low}.
   */
  public Double getLow() {
    return low;
  }

  /**
   * Getter for {@code high}.
   *
   * @return The value of {@code high}.
   */
  public Double getHigh() {
    return high;
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
