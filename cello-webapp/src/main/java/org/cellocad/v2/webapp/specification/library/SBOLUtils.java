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

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;
import org.sbolstandard.core2.Annotation;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.Identified;
import org.sbolstandard.core2.Interaction;
import org.sbolstandard.core2.ModuleDefinition;
import org.sbolstandard.core2.Participation;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SystemsBiologyOntology;

/**
 * Utilities for managing SBOL-ecoded data and objects for Cello.1)
 *
 * @author Timothy Jones
 * @date 2019-03-18
 */
public class SBOLUtils {

  /** The Cello namespace prefix used to annotate Cello objects, e.g. gates. */
  public static final String S_CELLO_TERM_NAMESPACE = "http://cellocad.org/Terms/cello#";

  /**
   * Get a string annotation by name from within the given SBOL object. Only annotations within the
   * Cello namespace are searched.
   *
   * <p>For example, if the {@link Identified} object {@code i} contains the annotation {@code
   * http://cellocad.org/Terms/cello#K} with value {@code 1.1}, as well as the annotation {@code
   * http://example.org/myterm#K} with value {@code foobar}, then {@code getCelloAnnotationString(i,
   * "K")} will return a string with value {@code 1.1}.
   *
   * @see #S_CELLO_TERM_NAMESPACE
   * @param i The object in which to search for the given annotation name.
   * @param name The annotation name.
   * @return A string annotation by name from within the given SBOL object.
   */
  public static String getCelloAnnotationString(final Identified i, final String name) {
    String rtn = null;
    final Annotation value = i.getAnnotation(new QName(SBOLUtils.S_CELLO_TERM_NAMESPACE, name));
    if (value != null) {
      rtn = value.getStringValue();
    }
    return rtn;
  }

  /**
   * Get the interactions, filtered by type, in which the given component definition is a
   * participant.
   *
   * @param document The document in which to search for interactions.
   * @param cd A component definition. Only interactions containing this component definition as a
   *     participant are returned.
   * @param type An interaction type. See {@link SystemsBiologyOntology} or related ontologies for
   *     URIs.
   * @return A set of the interactions that are of the given type and that contain the given
   *     component definition as a participant.
   */
  public static Set<Interaction> getInteractionsByType(
      final SBOLDocument document, final ComponentDefinition cd, final URI type) {
    final Set<Interaction> rtn = new HashSet<>();
    for (final ModuleDefinition md : document.getRootModuleDefinitions()) {
      for (final Interaction i : md.getInteractions()) {
        if (i.getTypes().contains(type)) {
          for (final Participation p : i.getParticipations()) {
            if (p.getParticipantDefinition().equals(cd)) {
              rtn.add(i);
            }
          }
        }
      }
    }
    return rtn;
  }

  /**
   * Get a set of participants (component definitions) that exist within the given interactions and
   * have the specified role.
   *
   * @param i The object in which to search for participants.
   * @param role A role. Only component definitions whose participation has this role are returned.
   * @return A set of participants (component definitions) that exist within the given interactions
   *     and have the specified role.
   */
  public static Set<ComponentDefinition> getParticipantsByRole(
      final Interaction i, final URI role) {
    final Set<ComponentDefinition> rtn = new HashSet<>();
    for (final Participation p : i.getParticipations()) {
      if (p.containsRole(role)) {
        rtn.add(p.getParticipantDefinition());
      }
    }
    return rtn;
  }

  /**
   * Get the interactions in which the given component definition participates, either directly or
   * indirectly via a complex formation, as a regulator by means of stimulation or inhibition. More
   * concretely, this method will return a set of interactions in which the given component
   * definition serves as a participant with the stimulation or inhibition role in the Systems
   * Biology Ontology (SBO). Alternatively, if the component definition participates in a complex
   * formation, i.e. it has the non-covalent binding role in the SBO, and that complex behaves as a
   * stimulator or inibitor, then the latter interaction is also added to the output set.
   *
   * @param document The document in which to search for interactions containing the given component
   *     definition.
   * @param cd A component definition. Only interactions in which this component definition
   *     participates, either directly or indirectly via a complex formation, as a stimulator or
   *     inhibitor are returned.
   * @return The set of interactions in which the given component definition participates in the
   *     manner described.
   */
  public static Set<Interaction> getRegulations(
      final SBOLDocument document, final ComponentDefinition cd) {
    final Set<Interaction> rtn = new HashSet<>();
    final Set<Interaction> productions =
        SBOLUtils.getInteractionsByType(document, cd, SystemsBiologyOntology.GENETIC_PRODUCTION);
    final Set<ComponentDefinition> products = new HashSet<>();
    for (final Interaction production : productions) {
      products.addAll(SBOLUtils.getParticipantsByRole(production, SystemsBiologyOntology.PRODUCT));
    }
    for (final ComponentDefinition product : products) {
      Set<Interaction> stimulations = null;
      Set<Interaction> inhibitions = null;
      // stimulation
      stimulations =
          SBOLUtils.getInteractionsByType(document, product, SystemsBiologyOntology.STIMULATION);
      rtn.addAll(stimulations);
      // inhibition
      inhibitions =
          SBOLUtils.getInteractionsByType(document, product, SystemsBiologyOntology.INHIBITION);
      rtn.addAll(inhibitions);
      // complex formation
      final Set<Interaction> formations =
          SBOLUtils.getInteractionsByType(
              document, product, SystemsBiologyOntology.NON_COVALENT_BINDING);
      for (final Interaction formation : formations) {
        final Set<ComponentDefinition> complexes =
            SBOLUtils.getParticipantsByRole(formation, SystemsBiologyOntology.PRODUCT);
        for (final ComponentDefinition complex : complexes) {
          stimulations =
              SBOLUtils.getInteractionsByType(
                  document, complex, SystemsBiologyOntology.STIMULATION);
          rtn.addAll(stimulations);
          inhibitions =
              SBOLUtils.getInteractionsByType(document, product, SystemsBiologyOntology.INHIBITION);
          rtn.addAll(inhibitions);
        }
      }
    }
    return rtn;
  }

  /**
   * Get the set of component definitions that are regulated by the given component definition.
   *
   * @param document The document in which to search for interactions.
   * @param cd A component definition. Only the definitions regulated by this component definition
   *     are returned.
   * @return The set of component definitions that are regulated by the given component definition.
   */
  public static Set<ComponentDefinition> getRegulated(
      final SBOLDocument document, final ComponentDefinition cd) {
    final Set<ComponentDefinition> rtn = new HashSet<>();
    final Set<Interaction> productions =
        SBOLUtils.getInteractionsByType(document, cd, SystemsBiologyOntology.GENETIC_PRODUCTION);
    final Set<ComponentDefinition> products = new HashSet<>();
    for (final Interaction production : productions) {
      products.addAll(SBOLUtils.getParticipantsByRole(production, SystemsBiologyOntology.PRODUCT));
    }
    for (final ComponentDefinition product : products) {
      Set<Interaction> stimulations = null;
      Set<Interaction> inhibitions = null;
      // stimulation
      stimulations =
          SBOLUtils.getInteractionsByType(document, product, SystemsBiologyOntology.STIMULATION);
      for (final Interaction stimulation : stimulations) {
        rtn.addAll(SBOLUtils.getParticipantsByRole(stimulation, SystemsBiologyOntology.STIMULATED));
      }
      // inhibition
      inhibitions =
          SBOLUtils.getInteractionsByType(document, product, SystemsBiologyOntology.INHIBITION);
      for (final Interaction inhibition : inhibitions) {
        rtn.addAll(SBOLUtils.getParticipantsByRole(inhibition, SystemsBiologyOntology.INHIBITED));
      }
      // complex formation
      final Set<Interaction> formations =
          SBOLUtils.getInteractionsByType(
              document, product, SystemsBiologyOntology.NON_COVALENT_BINDING);
      for (final Interaction formation : formations) {
        final Set<ComponentDefinition> complexes =
            SBOLUtils.getParticipantsByRole(formation, SystemsBiologyOntology.PRODUCT);
        for (final ComponentDefinition complex : complexes) {
          stimulations =
              SBOLUtils.getInteractionsByType(
                  document, complex, SystemsBiologyOntology.STIMULATION);
          for (final Interaction stimulation : stimulations) {
            rtn.addAll(
                SBOLUtils.getParticipantsByRole(stimulation, SystemsBiologyOntology.STIMULATED));
          }
          inhibitions =
              SBOLUtils.getInteractionsByType(document, product, SystemsBiologyOntology.INHIBITION);
          for (final Interaction inhibition : inhibitions) {
            rtn.addAll(
                SBOLUtils.getParticipantsByRole(inhibition, SystemsBiologyOntology.INHIBITED));
          }
        }
      }
    }
    return rtn;
  }
}
