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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.cellocad.cello2.webapp.exception.LibraryException;
import org.cellocad.cello2.webapp.specification.library.serialization.ResponseFunctionSerializationConstants;
import org.sbolstandard.core2.Annotation;
import org.sbolstandard.core2.ComponentDefinition;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-19
 *
 */
public class ResponseFunction {
	
	private String equation;
	private Collection<ResponseFunctionVariable> variables;
	private Collection<ResponseFunctionParameter> parameters;
	
	public ResponseFunction(ComponentDefinition cd) throws LibraryException {
		this.equation = SBOLUtils.getCelloAnnotationString(cd,ResponseFunctionSerializationConstants.S_SYNBIOHUB_EQUATION);
		this.parameters = new ArrayList<>();
		Map<String,ResponseFunctionVariable> variables = new HashMap<>();
		Iterator<Annotation> it = cd.getAnnotations().iterator();
		while (it.hasNext()) {
			Annotation a = it.next();
			String local = a.getQName().getLocalPart();
			String name = local.split("_")[0];
			if (local.endsWith("_off_threshold") || local.endsWith("_on_threshold")) {
				variables.put(name, null);
			}
		}
		for (String name : variables.keySet()) {
			Annotation off = cd.getAnnotation(new QName(SBOLUtils.S_CELLO_TERM_NAMESPACE,String.format("%s_off_threshold", name)));
			Annotation on = cd.getAnnotation(new QName(SBOLUtils.S_CELLO_TERM_NAMESPACE,String.format("%s_on_threshold", name)));
			if (off == null || on == null)
				throw new LibraryException("Error with ResponseFunctionVariable.");
			variables.put(name,new ResponseFunctionVariable(name,Double.valueOf(off.getStringValue()),Double.valueOf(on.getStringValue())));
		}
		this.variables = variables.values();
		String[] parameters = {"n","K","ymax","ymin"};
		for (String parameter : parameters) {
			Annotation a = cd.getAnnotation(new QName(SBOLUtils.S_CELLO_TERM_NAMESPACE,parameter));
			if (a == null)
				throw new LibraryException("Error with ResponseFunctionParameter.");
			this.parameters.add(new ResponseFunctionParameter(parameter,Double.valueOf(a.getStringValue())));
		}
	}

	/**
	 * Getter for <i>equation</i>
	 * @return value of <i>equation</i>
	 */
	public String getEquation() {
		return equation;
	}

	/**
	 * Getter for <i>variables</i>
	 * @return value of <i>variables</i>
	 */
	public Collection<ResponseFunctionVariable> getVariables() {
		return variables;
	}

	/**
	 * Getter for <i>parameters</i>
	 * @return value of <i>parameters</i>
	 */
	public Collection<ResponseFunctionParameter> getParameters() {
		return parameters;
	}

}
