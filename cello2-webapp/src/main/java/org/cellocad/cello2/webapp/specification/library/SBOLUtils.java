/**
 * Copyright (C) 2019 Boston University (BU)
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cellocad.cello2.webapp.specification.library;

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
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-18
 *
 */
public class SBOLUtils {
	
	public static final String S_CELLO_TERM_NAMESPACE = "http://cellocad.org/Terms/cello#";

	public static String getCelloAnnotationString(Identified i, String name) {
		String rtn = null;
		Annotation value = i.getAnnotation(new QName(S_CELLO_TERM_NAMESPACE,name));
		if (value != null) {
			rtn = value.getStringValue();
		}
		return rtn;
	}
	
	public static Set<Interaction> getInteractionsByType(SBOLDocument document, ComponentDefinition cd, URI type) {
		Set<Interaction> rtn = new HashSet<>();
		for (ModuleDefinition md : document.getRootModuleDefinitions()) {
			for (Interaction i : md.getInteractions()) {
				if (i.getTypes().contains(type)) {
					for (Participation p : i.getParticipations()) {
						if (p.getParticipantDefinition().equals(cd)) {
							rtn.add(i);
						}
					}
				}
			}
		}
		return rtn;
	}
	
	public static Set<ComponentDefinition> getParticipantsByRole(Interaction i, URI role) {
		Set<ComponentDefinition> rtn = new HashSet<>();
		for (Participation p : i.getParticipations()) {
			if (p.containsRole(role)) {
				rtn.add(p.getParticipantDefinition());
			}
		}
		return rtn;
	}
	
	public static Set<Interaction> getRegulations(SBOLDocument document, ComponentDefinition cd) {
		Set<Interaction> rtn = new HashSet<>();
		Set<Interaction> productions = SBOLUtils.getInteractionsByType(document,cd,SystemsBiologyOntology.GENETIC_PRODUCTION);
		Set<ComponentDefinition> products = new HashSet<>();
		for (Interaction production : productions) {
			products.addAll(SBOLUtils.getParticipantsByRole(production,SystemsBiologyOntology.PRODUCT));
		}
		for (ComponentDefinition product : products) {
			Set<Interaction> stimulations = null;
			Set<Interaction> inhibitions = null;
			// stimulation
			stimulations = SBOLUtils.getInteractionsByType(document,product,SystemsBiologyOntology.STIMULATION);
			rtn.addAll(stimulations);
			// inhibition
			inhibitions = SBOLUtils.getInteractionsByType(document,product,SystemsBiologyOntology.INHIBITION);
			rtn.addAll(inhibitions);
			// complex formation
			Set<Interaction> formations = SBOLUtils.getInteractionsByType(document,product,SystemsBiologyOntology.NON_COVALENT_BINDING);
			for (Interaction formation : formations) {
				Set<ComponentDefinition> complexes = SBOLUtils.getParticipantsByRole(formation,SystemsBiologyOntology.PRODUCT);
				for (ComponentDefinition complex : complexes) {
					stimulations = SBOLUtils.getInteractionsByType(document,complex,SystemsBiologyOntology.STIMULATION);
					rtn.addAll(stimulations);
					inhibitions = SBOLUtils.getInteractionsByType(document,product,SystemsBiologyOntology.INHIBITION);
					rtn.addAll(inhibitions);
				}
			}
		}
		return rtn;
	}
		
	public static Set<ComponentDefinition> getRegulated(SBOLDocument document, ComponentDefinition cd) {
		Set<ComponentDefinition> rtn = new HashSet<>();
		Set<Interaction> productions = getInteractionsByType(document,cd,SystemsBiologyOntology.GENETIC_PRODUCTION);
		Set<ComponentDefinition> products = new HashSet<>();
		for (Interaction production : productions) {
			products.addAll(getParticipantsByRole(production,SystemsBiologyOntology.PRODUCT));
		}
		for (ComponentDefinition product : products) {
			Set<Interaction> stimulations = null;
			Set<Interaction> inhibitions = null;
			// stimulation
			stimulations = getInteractionsByType(document,product,SystemsBiologyOntology.STIMULATION);
			for (Interaction stimulation : stimulations) {
				rtn.addAll(getParticipantsByRole(stimulation,SystemsBiologyOntology.STIMULATED));
			}
			// inhibition
			inhibitions = getInteractionsByType(document,product,SystemsBiologyOntology.INHIBITION);
			for (Interaction inhibition : inhibitions) {
				rtn.addAll(getParticipantsByRole(inhibition,SystemsBiologyOntology.INHIBITED));
			}
			// complex formation
			Set<Interaction> formations = getInteractionsByType(document,product,SystemsBiologyOntology.NON_COVALENT_BINDING);
			for (Interaction formation : formations) {
				Set<ComponentDefinition> complexes = getParticipantsByRole(formation,SystemsBiologyOntology.PRODUCT);
				for (ComponentDefinition complex : complexes) {
					stimulations = getInteractionsByType(document,complex,SystemsBiologyOntology.STIMULATION);
					for (Interaction stimulation : stimulations) {
						rtn.addAll(getParticipantsByRole(stimulation,SystemsBiologyOntology.STIMULATED));
					}
					inhibitions = getInteractionsByType(document,product,SystemsBiologyOntology.INHIBITION);
					for (Interaction inhibition : inhibitions) {
						rtn.addAll(getParticipantsByRole(inhibition,SystemsBiologyOntology.INHIBITED));
					}
				}
			}
		}
		return rtn;
	}
	
}
