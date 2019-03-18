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

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.cellocad.cello2.webapp.exception.LibraryException;
import org.synbiohub.frontend.SynBioHubException;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-19
 *
 */
public class SynBioHubLibraryResource extends LibraryResource {
	
	private URL registry;
	private URI collection;

	public SynBioHubLibraryResource(URL registry, URI collection) {
		this.registry = registry;
		this.collection = collection;
	}

	/* (non-Javadoc)
	 * @see org.cellocad.cello2.webapp.specification.library.LibraryTemplate#getLibrary()
	 */
	@Override
	public Library getLibrary() throws LibraryException {
		Library rtn = null;
		SynBioHubLibraryBuilder builder = null;
		try {
			builder = new SynBioHubLibraryBuilder(this.getRegistry(),this.getCollection());
			rtn = builder.build();
		} catch (IOException | SynBioHubException e) {
			throw new LibraryException(e);
		}
		return rtn;
	}

	/**
	 * Getter for <i>registry</i>
	 * @return value of <i>registry</i>
	 */
	public URL getRegistry() {
		return registry;
	}

	/**
	 * Getter for <i>collection</i>
	 * @return value of <i>collection</i>
	 */
	public URI getCollection() {
		return collection;
	}

}