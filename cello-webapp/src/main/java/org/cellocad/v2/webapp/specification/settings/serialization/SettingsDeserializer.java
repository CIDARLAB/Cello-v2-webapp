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
package org.cellocad.v2.webapp.specification.settings.serialization;

import java.io.IOException;
import java.util.Map;

import org.cellocad.v2.webapp.specification.settings.Settings;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-25
 *
 */
public class SettingsDeserializer extends StdDeserializer<Settings> {

	private static final long serialVersionUID = -5495426255886767531L;

	public SettingsDeserializer() {
		this(null);
	}

	/**
	 * @param t
	 */
	protected SettingsDeserializer(Class<?> t) {
		super(t);
	}

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public Settings deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Settings rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = p.getCodec().readTree(p);
		Map<String,String> settings = mapper.convertValue(node.get(SettingsSerializationConstants.S_PARAMETERS), new TypeReference<Map<String,String>>(){});
		String application = node.get(SettingsSerializationConstants.S_APPLICATION).asText();
		rtn = new Settings(settings,application);
		return rtn;
	}

}
