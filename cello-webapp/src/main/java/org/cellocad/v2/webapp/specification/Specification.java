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
package org.cellocad.v2.webapp.specification;

import org.cellocad.v2.webapp.specification.constraints.Constraints;
import org.cellocad.v2.webapp.specification.library.LibraryResource;
import org.cellocad.v2.webapp.specification.settings.Settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public class Specification {
	
	private String verilog;
	private Settings settings;
	private Constraints constraints;
	private LibraryResource libraryResource;
	
	@JsonCreator
	public Specification(@JsonProperty("verilog") String verilog, @JsonProperty("settings") Settings settings, @JsonProperty("constraints") Constraints constraints, @JsonProperty("library") LibraryResource resource) {
		this.verilog = verilog;
		this.settings = settings;
		this.constraints = constraints;
		this.libraryResource = resource;
	}

	/**
	 * Getter for <i>verilog</i>
	 * @return value of <i>verilog</i>
	 */
	public String getVerilog() {
		return verilog;
	}

	/**
	 * Getter for <i>settings</i>
	 * @return value of <i>settings</i>
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * Getter for <i>constraints</i>
	 * @return value of <i>constraints</i>
	 */
	public Constraints getConstraints() {
		return constraints;
	}

	/**
	 * Getter for <i>libraryResource</i>
	 * @return value of <i>libraryResource</i>
	 */
	public LibraryResource getLibraryResource() {
		return libraryResource;
	}
	
}
