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

import java.io.IOException;

import org.cellocad.v2.webapp.specification.library.InputSensor;
import org.cellocad.v2.webapp.specification.library.Part;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2019-03-18
 *
 */
public class InputSensorSerializer extends StdSerializer<InputSensor> {
	
	private static final long serialVersionUID = -7335688380170965175L;

	public InputSensorSerializer() {
		this(null);
	}

	/**
	 * @param t
	 */
	public InputSensorSerializer(Class<InputSensor> t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(InputSensor value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField(LibrarySerializationConstants.S_UCF_COLLECTION, InputSensorSerializationConstants.S_UCF_COLLECTION);
		gen.writeStringField(InputSensorSerializationConstants.S_UCF_NAME, value.getName());
		gen.writeStringField(InputSensorSerializationConstants.S_UCF_PROMOTER, value.getPromoter().getName());
		gen.writeNumberField(InputSensorSerializationConstants.S_UCF_SIGNAL_LOW, value.getLow());
		gen.writeNumberField(InputSensorSerializationConstants.S_UCF_SIGNAL_HIGH, value.getHigh());
		gen.writeArrayFieldStart(InputSensorSerializationConstants.S_UCF_PARTS);
		for (Part part : value.getParts()) {
			gen.writeString(part.getName());
		}
		gen.writeEndArray();
		gen.writeStringField(InputSensorSerializationConstants.S_UCF_URI, value.getUri().toString());
		gen.writeEndObject();
	}

}
