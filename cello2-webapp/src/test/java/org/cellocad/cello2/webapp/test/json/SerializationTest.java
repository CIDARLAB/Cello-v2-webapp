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
package org.cellocad.cello2.webapp.test.json;

import java.io.IOException;

import org.cellocad.cello2.webapp.specification.Specification;
import org.cellocad.cello2.webapp.specification.library.LibraryResource;
import org.cellocad.cello2.webapp.specification.library.SynBioHubLibraryResource;
import org.cellocad.cello2.webapp.specification.library.TargetDataLibraryResource;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-25
 *
 */
public class SerializationTest {

	private ObjectMapper mapper;

	@Before
	public void setup() {
		mapper = new ObjectMapper();
	}

	// FIXME
	// @Test
	public void test_ucf_library_resource() throws JsonParseException, JsonMappingException, IOException {
		final String name = "foo";
		LibraryResource resource;
		String json = String.format("{\"ucf\":\"%s\"}", name);
		resource = mapper.readValue(json, LibraryResource.class);
		assert (resource instanceof TargetDataLibraryResource);
		TargetDataLibraryResource ucf = (TargetDataLibraryResource) resource;
		// assert(ucf.getFile().getName().equals("foo"));
	}

	@Test
	public void test_synbiohub_library_resource() throws JsonParseException, JsonMappingException, IOException {
		final String registry = "https://synbiohub.programmingbiology.org";
		final String collection = "https://synbiohub.programmingbiology.org";
		LibraryResource resource;
		String json = String.format("{\"registry\":\"%s\",\"collection\":\"%s\"}", registry, collection);
		resource = mapper.readValue(json, LibraryResource.class);
		assert (resource instanceof SynBioHubLibraryResource);
		SynBioHubLibraryResource sbh = (SynBioHubLibraryResource) resource;
		assert (sbh.getRegistry().toString().equals(registry));
		assert (sbh.getCollection().toString().equals(collection));
	}

	@Test
	public void test_specificaton() throws JsonParseException, JsonMappingException, IOException {
		Specification specification;
		String verilog = "module a(o,i);\\ninput a;\\noutput o;\\nnot(o,a);\\nendmodule";
		String json = String.format("{\"verilog\":\"%s\"}", verilog);
		specification = mapper.readValue(json, Specification.class);
	}

}
