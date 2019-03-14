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
package org.cellocad.cello2.webapp.common.JSON;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public class JsonUtils {
	
	
	/**
	 * Returns a <i>JSONObject</i> formed by merging the contents of <i>json1</i> and <i>json2</i>
	 * Ref: https://stackoverflow.com/questions/9895041/merging-two-json-documents-using-jackson
	 * 
	 * @param json1 the <i>JsonNode</i> to merge
	 * @param json2 the <i>JsonNode</i> to merge
	 * @return the <i>JSONObject</i> formed by merging the contents of <i>json1</i> and <i>json2</i>
	 */
	public static JsonNode mergeJsonNodes(JsonNode json1, JsonNode json2) {
		Iterator<String> fieldNames = json2.fieldNames();
		while (fieldNames.hasNext()) {

			String fieldName = fieldNames.next();
			JsonNode jsonNode = json1.get(fieldName);
			// if field exists and is an embedded object
			if (jsonNode != null && jsonNode.isObject()) {
				mergeJsonNodes(jsonNode, json2.get(fieldName));
			}
			else {
				if (json1 instanceof ObjectNode) {
					// Overwrite field
					JsonNode value = json2.get(fieldName);
					((ObjectNode) json1).set(fieldName, value);
				}
			}

		}

		return json1;
	}

}
