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
package org.cellocad.v2.webapp.specification.library.serialization;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.cellocad.v2.webapp.specification.library.LibraryResource;
import org.cellocad.v2.webapp.specification.library.SynBioHubLibraryResource;
import org.cellocad.v2.webapp.specification.library.TargetDataLibraryResource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-25
 *
 */
public class LibraryResourceDeserializer extends StdDeserializer<LibraryResource> {

	private static final long serialVersionUID = -2264378507506091919L;

	public LibraryResourceDeserializer() {
		this(null);
	}

	/**
	 * @param t
	 */
	protected LibraryResourceDeserializer(Class<?> t) {
		super(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.
	 * jackson.core.JsonParser,
	 * com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public LibraryResource deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		LibraryResource rtn = null;
		JsonNode node = p.getCodec().readTree(p);
		JsonNode userConstraintsFileName = node.get(LibraryResourceSerializationConstants.S_USERCONSTRAINTSFILE);
		JsonNode inputSensorFileName = node.get(LibraryResourceSerializationConstants.S_INPUTSENSORFILE);
		JsonNode outputDeviceFileName = node.get(LibraryResourceSerializationConstants.S_OUTPUTDEVICEFILE);
		JsonNode registry = node.get(LibraryResourceSerializationConstants.S_REGISTRY);
		JsonNode collection = node.get(LibraryResourceSerializationConstants.S_COLLECTION);

		if ((userConstraintsFileName != null) && (inputSensorFileName != null) && (outputDeviceFileName != null)) {
			File userConstraintsFile = new File(userConstraintsFileName.asText());
			File inputSensorFile = new File(inputSensorFileName.asText());
			File outputDeviceFile = new File(outputDeviceFileName.asText());
			rtn = new TargetDataLibraryResource(userConstraintsFile, inputSensorFile, outputDeviceFile);
		} else {
			rtn = new SynBioHubLibraryResource(new URL(registry.asText()), URI.create(collection.asText()));
		}
		return rtn;
	}

}
