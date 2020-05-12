/*
 * Copyright (C) 2019 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.cellocad.v2.webapp.specification.constraints.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.cellocad.v2.webapp.specification.constraints.Constraints;

/**
 * Serializer for {@link Constraints}.
 *
 * @author Timothy Jones
 * @date 2019-03-18
 */
public class ConstraintsSerializer extends StdSerializer<Constraints> {

  private static final long serialVersionUID = -8821918895368017542L;

  public ConstraintsSerializer() {
    this(null);
  }

  public ConstraintsSerializer(final Class<Constraints> t) {
    super(t);
    // TODO Auto-generated constructor stub
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.
   * Object, com.fasterxml.jackson.core.JsonGenerator,
   * com.fasterxml.jackson.databind.SerializerProvider)
   */
  @Override
  public void serialize(
      final Constraints value, final JsonGenerator gen, final SerializerProvider provider)
      throws IOException {
    gen.writeStartArray();
    if (value.getSensors() != null) {
      gen.writeStartObject();
      gen.writeStringField(
          ConstraintsSerializationConstants.S_COLLECTION,
          ConstraintsSerializationConstants.S_SENSORS);
      gen.writeObjectField(ConstraintsSerializationConstants.S_SENSOR_MAP, value.getReporters());
      gen.writeEndObject();
    }
    if (value.getReporters() != null) {
      gen.writeStartObject();
      gen.writeStringField(
          ConstraintsSerializationConstants.S_COLLECTION,
          ConstraintsSerializationConstants.S_REPORTERS);
      gen.writeObjectField(ConstraintsSerializationConstants.S_REPORTER_MAP, value.getReporters());
      gen.writeEndObject();
    }
    gen.writeEndArray();
  }
}
