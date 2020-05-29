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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.exception.LibraryException;
import org.cellocad.v2.webapp.specification.library.serialization.GateSerializationConstants;
import org.cellocad.v2.webapp.specification.library.serialization.GateSerializer;
import org.sbolstandard.core2.Attachment;
import org.sbolstandard.core2.Component;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLValidationException;
import org.sbolstandard.core2.SequenceOntology;

/**
 * A gate.
 *
 * @author Timothy Jones
 * @date 2019-03-18
 */
@JsonSerialize(using = GateSerializer.class)
public class Gate {

  private String regulator;
  private String group;
  private String name;
  private String type;
  private String system;
  private String color;
  private URI uri;

  private GateParts gateParts;
  private ResponseFunction responseFunction;

  public Collection<JsonNode> objects;

  /**
   * Build a gate from an appropriately constructed {@link ComponentDefinition}, i.e. one created by
   * <a href=https://github.com/MyersResearchGroup/UCF2SBOL>UCF2SBOL</a>.
   *
   * @param document An SBOL document.
   * @param cd A component definition.
   * @throws LibraryException Unable to build gate.
   */
  public Gate(final SBOLDocument document, final ComponentDefinition cd) throws LibraryException {
    regulator =
        SBOLUtils.getCelloAnnotationString(cd, GateSerializationConstants.S_SYNBIOHUB_REGULATOR);
    group =
        SBOLUtils.getCelloAnnotationString(cd, GateSerializationConstants.S_SYNBIOHUB_GROUP_NAME);
    name = cd.getDisplayId();
    type = SBOLUtils.getCelloAnnotationString(cd, GateSerializationConstants.S_SYNBIOHUB_GATE_TYPE);
    system = SBOLUtils.getCelloAnnotationString(cd, GateSerializationConstants.S_SYNBIOHUB_SYSTEM);
    color =
        SBOLUtils.getCelloAnnotationString(
            cd, GateSerializationConstants.S_SYNBIOHUB_COLOR_HEXCODE);
    uri = cd.getIdentity();
    responseFunction = new ResponseFunction(cd);
    final Collection<Part> parts = new ArrayList<>();
    List<Component> components;
    try {
      components = cd.getSortedComponents();
    } catch (final SBOLValidationException e) {
      throw new LibraryException("Error with ComponentDefinition.");
    }
    for (final Component c : components) {
      parts.add(new Part(c.getDefinition()));
    }
    final String variable = responseFunction.getVariables().iterator().next().getName();
    final CassetteParts cassetteParts = new CassetteParts(parts);
    final Map<String, CassetteParts> gateParts = new HashMap<>();
    gateParts.put(variable, cassetteParts);
    ComponentDefinition cds = null;
    for (final Component c : cd.getComponents()) {
      final ComponentDefinition def = c.getDefinition();
      if (def.getRoles().contains(SequenceOntology.CDS)) {
        cds = def;
        break;
      }
    }
    final ComponentDefinition promoter = SBOLUtils.getRegulated(document, cds).iterator().next();
    this.gateParts = new GateParts(gateParts, new Part(promoter));
    objects = new ArrayList<>();
    final ObjectMapper mapper = new ObjectMapper();
    try {
      for (final Attachment a : cd.getAttachments()) {
        if (a.getName().endsWith(".json")) {
          URL source = null;
          source = a.getSource().toURL();
          final String str = Utils.getUrlContentsAsString(source);
          final JsonNode obj = mapper.readTree(str);
          objects.add(obj);
        }
      }
    } catch (final IOException e) {
      throw new LibraryException(e);
    }
  }

  /**
   * Getter for {@code regulator}.
   *
   * @return The value of {@code regulator}.
   */
  public String getRegulator() {
    return regulator;
  }

  /**
   * Getter for {@code group}.
   *
   * @return The value of {@code group}.
   */
  public String getGroup() {
    return group;
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
   * Getter for {@code objects}.
   *
   * @return The value of {@code objects}.
   */
  public Collection<JsonNode> getObjects() {
    return objects;
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
   * Getter for {@code system}.
   *
   * @return The value of {@code system}.
   */
  public String getSystem() {
    return system;
  }

  /**
   * Getter for {@code color}.
   *
   * @return The value of {@code color}.
   */
  public String getColor() {
    return color;
  }

  /**
   * Getter for {@code uri}.
   *
   * @return The value of {@code uri}.
   */
  public URI getUri() {
    return uri;
  }

  /**
   * Getter for {@code gateParts}.
   *
   * @return The value of {@code gateParts}.
   */
  public GateParts getGateParts() {
    return gateParts;
  }

  /**
   * Getter for {@code responseFunction}.
   *
   * @return The value of {@code responseFunction}.
   */
  public ResponseFunction getResponseFunction() {
    return responseFunction;
  }
}
