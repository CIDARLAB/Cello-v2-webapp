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
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.exception.LibraryException;
import org.sbolstandard.core2.Attachment;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.SBOLDocument;
import org.synbiohub.frontend.SynBioHubException;
import org.synbiohub.frontend.SynBioHubFrontend;

/**
 * A builder for libraries stored in a SynBioHub.
 *
 * @author Timothy Jones
 * @date Feb 18, 2019
 */
public class SynBioHubLibraryBuilder {

  private SynBioHubFrontend sbh;
  private SBOLDocument sbol;

  private static final String S_INPUT_SENSOR = "input_sensor";
  private static final String S_OUTPUT_REPORTER = "output_reporter";

  /**
   * A builder for libraries stored in a SynBioHub.
   *
   * @param registry The registry URL of the library.
   * @param collection The collection URI of the library.
   * @throws SynBioHubException Unable to communicate with SynBioHub.
   */
  public SynBioHubLibraryBuilder(final URL registry, final URI collection)
      throws SynBioHubException {
    setSbh(new SynBioHubFrontend(registry.toString()));
    final SBOLDocument sbol = sbh.getSBOL(collection);
    setSbol(sbol);
  }

  /**
   * Build the library.
   *
   * @return The library.
   * @throws LibraryException Unable to build library.
   * @throws IOException Unable to read resources.
   */
  public Library build() throws LibraryException, IOException {
    Library rtn = null;
    final Collection<Gate> gates = new ArrayList<>();
    final Collection<Part> parts = new HashSet<>();
    final Collection<InputSensor> sensors = new ArrayList<>();
    final Collection<OutputReporter> reporters = new ArrayList<>();
    final Collection<JsonNode> objects = getTopLevelJson();
    for (final ComponentDefinition cd : getSbol().getRootComponentDefinitions()) {
      if (SBOLUtils.getCelloAnnotationString(cd, "group_name") != null) {
        gates.add(new Gate(getSbol(), cd));
      }
      final String type = SBOLUtils.getCelloAnnotationString(cd, "gateType");
      if (type != null && type.equals(SynBioHubLibraryBuilder.S_INPUT_SENSOR)) {
        sensors.add(new InputSensor(cd, getSbol()));
      }
      if (type != null && type.equals(SynBioHubLibraryBuilder.S_OUTPUT_REPORTER)) {
        reporters.add(new OutputReporter(cd, getSbol()));
      }
      if (cd.getDisplayId().equals("backbone")) {
        parts.add(new Part(cd));
      }
    }
    for (final InputSensor sensor : sensors) {
      parts.add(sensor.getPromoter());
      parts.addAll(sensor.getParts());
    }
    for (final OutputReporter reporter : reporters) {
      parts.addAll(reporter.getParts());
    }
    for (final Gate gate : gates) {
      final GateParts gateParts = gate.getGateParts();
      final ResponseFunction rf = gate.getResponseFunction();
      for (final ResponseFunctionVariable variable : rf.getVariables()) {
        parts.addAll(gateParts.getCassetteParts(variable.getName()).getParts());
      }
      parts.add(gateParts.getPromoter());
    }
    rtn = new Library(gates, sensors, reporters, parts, objects);
    return rtn;
  }

  private Collection<JsonNode> getTopLevelJson() throws IOException {
    final Collection<JsonNode> rtn = new ArrayList<>();
    final Set<Attachment> attachments = getSbol().getAttachments();
    final ObjectMapper mapper = new ObjectMapper();
    for (final Attachment a : attachments) {
      if (S_VALIDSECTIONS.contains(a.getName())) {
        URL source = null;
        source = a.getSource().toURL();
        final String str = Utils.getUrlContentsAsString(source);
        final JsonNode obj = mapper.readTree(str);
        rtn.add(obj);
      }
    }
    return rtn;
  }

  /**
   * Getter for {@code sbh}.
   *
   * @return The value of {@code sbh}.
   */
  public SynBioHubFrontend getSbh() {
    return sbh;
  }

  /**
   * Setter for {@code sbh}.
   *
   * @param sbh the value to set <i>sbh</i>
   */
  private void setSbh(final SynBioHubFrontend sbh) {
    this.sbh = sbh;
  }

  /**
   * Getter for {@code sbol}.
   *
   * @return The value of {@code sbol}.
   */
  public SBOLDocument getSbol() {
    return sbol;
  }

  /**
   * Setter for {@code sbol}.
   *
   * @param sbol the value to set <i>sbol</i>
   */
  private void setSbol(final SBOLDocument sbol) {
    this.sbol = sbol;
  }

  private static final List<String> S_VALIDSECTIONS =
      Arrays.asList(
          "header.json",
          "measurement_std.json",
          "logic_constraints.json",
          "eugene_rules.json",
          "genetic_locations.json"
          // TODO motif library
          );
}
