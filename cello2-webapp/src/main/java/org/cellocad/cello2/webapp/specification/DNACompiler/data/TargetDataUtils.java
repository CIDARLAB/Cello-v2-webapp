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
package org.cellocad.cello2.webapp.specification.DNACompiler.data;

import java.io.File;
import java.io.IOException;

import org.cellocad.cello2.webapp.CelloWebException;
import org.cellocad.cello2.webapp.common.Utils;
import org.cellocad.cello2.webapp.common.JSON.JsonUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date Feb 16, 2019
 *
 */
public class TargetDataUtils {
	
	public static File writeTargetData(File UCF, File PartitionProfile, String filename) throws CelloWebException {
		File rtn = null;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json1;
		JsonNode json2;
		try {
			json1 = mapper.readTree(new File(UCF.getPath()));
			json2 = mapper.readTree(PartitionProfile.getPath());
		} catch (IOException e) {
			throw new CelloWebException("Error with file.");
		}
		JsonNode json = JsonUtils.mergeJsonNodes(json1, json2);
		Utils.writeToFile(json.toString(), filename);
		return rtn;
	}

}
