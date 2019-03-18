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
package org.cellocad.cello2.webapp.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.cello2.webapp.ApplicationUtils;
import org.cellocad.cello2.webapp.common.Utils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 
 * 
 * @author Timothy Jones
 * 
 * @date 2019-02-15
 *
 */
@CrossOrigin(origins="*")
@Controller
@RequestMapping("/synbiohub")
public class SynBioHubController {

	private static Logger getLogger() {
		return LogManager.getLogger(SynBioHubController.class);
	}

	@PostConstruct
	public void init() {
		String projects = ApplicationUtils.getProjectsDirectory();
		if (!Utils.isValidFilepath(projects)) {
			Utils.makeDirectory(projects);
		}
		String users = ApplicationUtils.getUsersFile();
		if(!Utils.isValidFilepath(users)){
			ApplicationUtils.createUsersFile();
		}
	}

	@ResponseBody
	@GetMapping(value = "/collections", produces = {MediaType.APPLICATION_JSON_VALUE})
	public JsonNode collections(
			@RequestParam(name="u") URL registry,
			@RequestHeader(value="X-authorization", required=false) String token,
			HttpServletResponse response) {
		if (token == null) {
			URL url = null;
			try {
				url = new URL(url,"rootCollections");
			} catch (MalformedURLException e) {
				throw new RuntimeException("Error with SynBioHub url.");
			}
			RestTemplate rest = new RestTemplate();
			JsonNode collections = rest.getForObject(url.toString(),JsonNode.class);
			return collections;
		} else {
			URI endpoint = null;
			try {
				URL url = new URL(registry,"search/objectType%3DCollection%26/?offset=0&limit=10000");
				endpoint = url.toURI();
			} catch (MalformedURLException | URISyntaxException e) {
				throw new RuntimeException("Error with SynBioHub url.");
			}
			RestTemplate rest = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("X-authorization", token);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<String> result = rest.exchange(endpoint, HttpMethod.GET, entity, String.class);
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode collections = mapper.createArrayNode();
			try {
				JsonNode arr = mapper.readTree(result.getBody());
				for (JsonNode obj : arr) {
					if (obj.get("uri").asText().contains("/public/"))
						continue;
					collections.add(obj);
				}
			} catch (NullPointerException | IOException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error retrieving collections.");
			}
			return collections;
		}
	}
	
	@ResponseBody
	@PostMapping(value = "/login")
	public String login(
			@RequestParam(name="u") URL registry,
			@RequestBody String request,
			HttpServletResponse response) {
		String rtn = null;
		URI endpoint = null;
		try {
			URL url = new URL(registry,"login");
			endpoint = url.toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException("Error with SynBioHub url.");
		}
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(request, headers);
		ResponseEntity<String> result = rest.exchange(endpoint, HttpMethod.POST, entity, String.class);
		rtn = result.getBody();
		return rtn;
	}
	
	@ResponseBody
	@PostMapping(value = "/submit/{project}/{file}")
	public void submit(
			@RequestParam(name="u") URL registry,
			@RequestHeader(value="X-authorization") String token,
			@PathVariable(value="project") String projectName,
			@PathVariable(value="file") String fileName, 
			@RequestBody JsonNode request,
			HttpServletResponse response) throws IOException {
		// rest
		RestTemplate rest = new RestTemplate();

		// url
		URI uri;
		try {
			URL url = new URL(registry,"submit");
			uri = url.toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			return;
		}

		// headers
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("X-authorization", token);

		// body
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        if (!request.has("collection"))
        	return;
        JsonNode coll = request.get("collection");
        if (coll.has("uri") && !coll.get("uri").asText("").equals("")) {
        	map.add("rootCollections", coll.get("uri").toString());
        } else {
        	try {
				map.add("id",coll.get("id").asText());
				map.add("version",coll.get("version").asText());
				map.add("name",coll.get("name").asText());
				map.add("description",coll.get("description").asText());
				map.add("citations",coll.get("citations").asText(""));
			} catch (NullPointerException e) { return; }
        	map.add("collectionChoices","");
        }
        try {
        	map.add("overwrite_merge",coll.get("overwrite").asBoolean()?1:0);
        } catch (NullPointerException e) { return; }
        map.add("user",token);

//        // file
//        String filePath = ResultsUtils.getResultFilePath(userId, projectName, fileName);
//        File file = new File(filePath);
//        map.add("file",new ByteArrayResource(Files.readAllBytes(file.toPath())));
//
//		// entity
//		HttpEntity<?> entity = new HttpEntity<>(map, headers);
//
//		// response
//		ResponseEntity<String> result = null;
//		try {
//			result = rest.exchange(uri, HttpMethod.POST, entity, String.class);
//		} catch (RestClientException e) {
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//			writeMessage("Error with upload. Verify that the collection doesn't already exist.",response);
//		}
//		response.setStatus(HttpServletResponse.SC_OK);
	}

}

