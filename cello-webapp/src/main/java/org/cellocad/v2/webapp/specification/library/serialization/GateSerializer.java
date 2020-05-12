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

package org.cellocad.v2.webapp.specification.library.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.cellocad.v2.webapp.specification.library.Gate;
import org.cellocad.v2.webapp.specification.library.GateParts;
import org.cellocad.v2.webapp.specification.library.Part;
import org.cellocad.v2.webapp.specification.library.ResponseFunctionParameter;
import org.cellocad.v2.webapp.specification.library.ResponseFunctionVariable;

/**
 * Serializer for {@link Gate}.
 *
 * @author Timothy Jones
 * @date 2019-03-18
 */
public class GateSerializer extends StdSerializer<Gate> {

  private static final long serialVersionUID = -6137334978863944991L;

  public GateSerializer() {
    this(null);
  }

  public GateSerializer(final Class<Gate> t) {
    super(t);
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
   */
  @Override
  public void serialize(
      final Gate value, final JsonGenerator gen, final SerializerProvider provider)
      throws IOException {
    // gate
    gen.writeStartObject();
    gen.writeStringField(
        LibrarySerializationConstants.S_UCF_COLLECTION,
        GateSerializationConstants.S_UCF_COLLECTION);
    gen.writeStringField(GateSerializationConstants.S_UCF_REGULATOR, value.getRegulator());
    gen.writeStringField(GateSerializationConstants.S_UCF_GROUP_NAME, value.getGroup());
    gen.writeStringField(GateSerializationConstants.S_UCF_GATE_NAME, value.getName());
    gen.writeStringField(GateSerializationConstants.S_UCF_GATE_TYPE, value.getType());
    gen.writeStringField(GateSerializationConstants.S_UCF_SYSTEM, value.getSystem());
    gen.writeStringField(GateSerializationConstants.S_UCF_COLOR_HEXCODE, value.getColor());
    gen.writeStringField(GateSerializationConstants.S_UCF_URI, value.getUri().toString());
    gen.writeEndObject();
    // response function
    gen.writeStartObject();
    gen.writeStringField(
        LibrarySerializationConstants.S_UCF_COLLECTION,
        ResponseFunctionSerializationConstants.S_UCF_COLLECTION);
    gen.writeStringField(ResponseFunctionSerializationConstants.S_UCF_GATE_NAME, value.getName());
    gen.writeStringField(
        ResponseFunctionSerializationConstants.S_UCF_EQUATION,
        value.getResponseFunction().getEquation());
    gen.writeArrayFieldStart(ResponseFunctionSerializationConstants.S_UCF_VARIABLES);
    for (final ResponseFunctionVariable variable : value.getResponseFunction().getVariables()) {
      gen.writeStartObject();
      gen.writeStringField(
          ResponseFunctionSerializationConstants.S_UCF_VARIABLE_NAME, variable.getName());
      gen.writeNumberField(
          ResponseFunctionSerializationConstants.S_UCF_VARIABLE_OFF_THRESHOLD,
          variable.getOffThreshold());
      gen.writeNumberField(
          ResponseFunctionSerializationConstants.S_UCF_VARIABLE_ON_THRESHOLD,
          variable.getOnThreshold());
      gen.writeEndObject();
    }
    gen.writeEndArray();
    gen.writeArrayFieldStart(ResponseFunctionSerializationConstants.S_UCF_PARAMETERS);
    for (final ResponseFunctionParameter parameter : value.getResponseFunction().getParameters()) {
      gen.writeStartObject();
      gen.writeStringField(
          ResponseFunctionSerializationConstants.S_UCF_PARAMETER_NAME, parameter.getName());
      gen.writeNumberField(
          ResponseFunctionSerializationConstants.S_UCF_PARAMETER_VALUE, parameter.getValue());
      gen.writeEndObject();
    }
    gen.writeEndArray();
    gen.writeEndObject();
    // gate parts
    gen.writeStartObject();
    gen.writeStringField(
        LibrarySerializationConstants.S_UCF_COLLECTION,
        GatePartsSerializationConstants.S_UCF_COLLECTION);
    gen.writeStringField(GatePartsSerializationConstants.S_UCF_GATE_NAME, value.getName());
    gen.writeArrayFieldStart(GatePartsSerializationConstants.S_UCF_EXPRESSION_CASSETTES);
    final GateParts gateParts = value.getGateParts();
    for (final ResponseFunctionVariable variable : value.getResponseFunction().getVariables()) {
      gen.writeStartObject();
      gen.writeStringField(
          GatePartsSerializationConstants.S_UCF_MAPS_TO_VARIABLE, variable.getName());
      gen.writeArrayFieldStart(GatePartsSerializationConstants.S_UCF_CASSETTE_PARTS);
      for (final Part part : gateParts.getCassetteParts(variable.getName()).getParts()) {
        gen.writeString(part.getName());
      }
      gen.writeEndArray();
      gen.writeEndObject();
    }
    gen.writeEndArray();
    gen.writeStringField(
        GatePartsSerializationConstants.S_UCF_PROMOTER, gateParts.getPromoter().getName());
    gen.writeEndObject();
    for (final JsonNode obj : value.getObjects()) {
      gen.writeObject(obj);
    }
  }
}
