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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;
import org.cellocad.v2.webapp.exception.LibraryException;
import org.cellocad.v2.webapp.specification.library.serialization.ResponseFunctionSerializationConstants;
import org.sbolstandard.core2.Annotation;
import org.sbolstandard.core2.ComponentDefinition;

/**
 * A response function.
 *
 * @author Timothy Jones
 * @date 2019-03-19
 */
public class ResponseFunction {

  private String equation;
  private Collection<ResponseFunctionVariable> variables;
  private Collection<ResponseFunctionParameter> parameters;

  /**
   * Initialize a response function based on an SBOL component definition.
   *
   * @param cd A component definition.
   * @throws LibraryException Unable to initialize the response function.
   */
  public ResponseFunction(final ComponentDefinition cd) throws LibraryException {
    equation =
        SBOLUtils.getCelloAnnotationString(
            cd, ResponseFunctionSerializationConstants.S_SYNBIOHUB_EQUATION);
    parameters = new ArrayList<>();
    final Map<String, ResponseFunctionVariable> variables = new HashMap<>();
    final Iterator<Annotation> it = cd.getAnnotations().iterator();
    while (it.hasNext()) {
      final Annotation a = it.next();
      final String local = a.getQName().getLocalPart();
      final String name = local.split("_")[0];
      if (local.endsWith("_off_threshold") || local.endsWith("_on_threshold")) {
        variables.put(name, null);
      }
    }
    for (final String name : variables.keySet()) {
      final Annotation off =
          cd.getAnnotation(
              new QName(SBOLUtils.S_CELLO_TERM_NAMESPACE, String.format("%s_off_threshold", name)));
      final Annotation on =
          cd.getAnnotation(
              new QName(SBOLUtils.S_CELLO_TERM_NAMESPACE, String.format("%s_on_threshold", name)));
      if (off == null || on == null) {
        throw new LibraryException("Error with ResponseFunctionVariable.");
      }
      variables.put(
          name,
          new ResponseFunctionVariable(
              name, Double.valueOf(off.getStringValue()), Double.valueOf(on.getStringValue())));
    }
    this.variables = variables.values();
    final String[] parameters = {"n", "K", "ymax", "ymin"};
    for (final String parameter : parameters) {
      final Annotation a = cd.getAnnotation(new QName(SBOLUtils.S_CELLO_TERM_NAMESPACE, parameter));
      if (a == null) {
        throw new LibraryException("Error with ResponseFunctionParameter.");
      }
      this.parameters.add(
          new ResponseFunctionParameter(parameter, Double.valueOf(a.getStringValue())));
    }
  }

  /**
   * Getter for {@code equation}.
   *
   * @return The value of {@code equation}.
   */
  public String getEquation() {
    return equation;
  }

  /**
   * Getter for {@code variables}.
   *
   * @return The value of {@code variables}.
   */
  public Collection<ResponseFunctionVariable> getVariables() {
    return variables;
  }

  /**
   * Getter for {@code parameters}.
   *
   * @return The value of {@code parameters}.
   */
  public Collection<ResponseFunctionParameter> getParameters() {
    return parameters;
  }
}
