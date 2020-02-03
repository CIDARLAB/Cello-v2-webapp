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

import java.io.File;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-19
 *
 */
public class TargetDataLibraryResource extends LibraryResource {

	private File userConstraintsFile;
	private File inputSensorFile;
	private File outputDeviceFile;

	public TargetDataLibraryResource(File userConstraintsFile, File inputSensorFile, File outputDeviceFile) {
		this.userConstraintsFile = userConstraintsFile;
		this.inputSensorFile = inputSensorFile;
		this.outputDeviceFile = outputDeviceFile;
	}

	/**
	 * Getter for <i>userConstraintsFile</i>
	 * 
	 * @return value of <i>userConstraintsFile</i>
	 */
	public File getUserConstraintsFile() {
		return userConstraintsFile;
	}

	/**
	 * Getter for <i>inputSensorFile</i>
	 * 
	 * @return value of <i>inputSensorFile</i>
	 */
	public File getInputSensorFile() {
		return inputSensorFile;
	}

	/**
	 * Getter for <i>outputDeviceFile</i>
	 * 
	 * @return value of <i>outputDeviceFile</i>
	 */
	public File getOutputDeviceFile() {
		return outputDeviceFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cellocad.cello2.webapp.specification.library.LibraryTemplate#getLibrary()
	 */
	@Override
	public Library getLibrary() {
		Library rtn = null;
		return rtn;
	}

}
