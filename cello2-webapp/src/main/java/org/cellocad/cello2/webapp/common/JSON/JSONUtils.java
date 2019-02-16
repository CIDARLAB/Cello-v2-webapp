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

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public class JSONUtils {
	
	
	/**
	 * Returns a <i>JSONObject</i> formed by merging the contents of <i>json1</i> and <i>json2</i>
	 * Ref: https://crunchify.com/how-to-merge-concat-multiple-jsonobjects-in-java-best-way-to-combine-two-jsonobjects/
	 * 
	 * @param json1 the <i>JSONObject</i> to merge
	 * @param json2 the <i>JSONObject</i> to merge
	 * @return the <i>JSONObject</i> formed by merging the contents of <i>json1</i> and <i>json2</i>
	 */
	public static JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2) {
		JSONObject rtn = new JSONObject();
		try {
			rtn = new JSONObject(json1, JSONObject.getNames(json1));
			for (String key : JSONObject.getNames(json2)) {
				rtn.put(key, json2.get(key));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
}
