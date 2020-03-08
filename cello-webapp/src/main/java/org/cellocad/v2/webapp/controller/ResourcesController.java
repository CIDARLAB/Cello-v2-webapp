/**
 * Copyright (C) 2020 Boston University (BU)
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
package org.cellocad.v2.webapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 *
 * @author Timothy Jones
 *
 * @date 2020-02-02
 *
 */
@RestController
@RequestMapping("/resources")
public class ResourcesController {

	@GetMapping(value = "/user_constraints_files", produces = { MediaType.APPLICATION_JSON_VALUE })
	public JsonNode userConstraintsFiles(HttpServletResponse res) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode rtn = mapper.createArrayNode();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resources[] = resolver.getResources("classpath:/lib/files/v2/ucf/**/*.UCF.json");
		for (Resource r : resources) {
			ObjectNode node = mapper.createObjectNode();
			node.put("file", r.getFilename());
			node.put("version", r.getFilename());
			node.put("organism", "Escherichia coli NEB 10-beta");
			rtn.add(node);
		}
		return rtn;
	}

	@GetMapping(value = "/input_sensor_files", produces = { MediaType.APPLICATION_JSON_VALUE })
	public JsonNode inputSensorFiles(HttpServletResponse res) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode rtn = mapper.createArrayNode();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resources[] = resolver.getResources("classpath:/lib/files/v2/input/**/*.input.json");
		for (Resource r : resources) {
			ObjectNode node = mapper.createObjectNode();
			node.put("name", r.getFilename());
			rtn.add(node);
		}
		return rtn;
	}

	@GetMapping(value = "/output_device_files", produces = { MediaType.APPLICATION_JSON_VALUE })
	public JsonNode outputDeviceFiles(HttpServletResponse res) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode rtn = mapper.createArrayNode();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resources[] = resolver.getResources("classpath:/lib/files/v2/output/**/*.output.json");
		for (Resource r : resources) {
			ObjectNode node = mapper.createObjectNode();
			node.put("name", r.getFilename());
			rtn.add(node);
		}
		return rtn;
	}

}
