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

package org.cellocad.v2.webapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Iterator;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.exception.ResourceNotFoundException;
import org.cellocad.v2.webapp.project.Project;
import org.cellocad.v2.webapp.results.Result;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

/**
 * A controller for interaction with a SynBioHub.
 *
 * @author Timothy Jones
 * @date 2019-02-15
 */
// Due to some CORS configuration in SynBioHub, the API is wrapped here instead of being used
// directly in the browser.
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/synbiohub")
public class SynBioHubController {

  private static Logger getLogger() {
    return LogManager.getLogger(SynBioHubController.class);
  }

  /**
   * Get root public and private collections.
   *
   * @param registry The registry URL.
   * @param token The SynBioHub login token.
   * @param response The response.
   * @return The collections.
   */
  @ResponseBody
  @GetMapping(
      value = "/collections",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  // TODO Don't return JsonNode. Use something like List<Collection> instead.
  public JsonNode collections(
      @RequestParam(name = "u") final URL registry,
      @RequestHeader(value = "X-authorization", required = false) final String token,
      final HttpServletResponse response) {
    if (token == null) {
      URL url = null;
      try {
        url = new URL(registry, "rootCollections");
      } catch (final MalformedURLException e) {
        throw new RuntimeException("Error with SynBioHub url.");
      }
      final RestTemplate rest = new RestTemplate();
      final JsonNode collections = rest.getForObject(url.toString(), JsonNode.class);
      return collections;
    } else {
      URI endpoint = null;
      try {
        final URL url =
            new URL(registry, "search/objectType%3DCollection%26/?offset=0&limit=10000");
        endpoint = url.toURI();
      } catch (MalformedURLException | URISyntaxException e) {
        throw new RuntimeException("Error with SynBioHub url.");
      }
      final RestTemplate rest = new RestTemplate();
      final HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
      headers.set("X-authorization", token);
      final HttpEntity<String> entity = new HttpEntity<>(headers);
      final ResponseEntity<String> result =
          rest.exchange(endpoint, HttpMethod.GET, entity, String.class);
      final ObjectMapper mapper = new ObjectMapper();
      final ArrayNode collections = mapper.createArrayNode();
      try {
        final JsonNode arr = mapper.readTree(result.getBody());
        for (final JsonNode obj : arr) {
          if (obj.get("uri").asText().contains("/public/")) {
            continue;
          }
          collections.add(obj);
        }
      } catch (NullPointerException | IOException e) {
        throw new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving collections.");
      }
      return collections;
    }
  }

  /**
   * User login to a SynBioHub.
   *
   * @param registry The registry URL.
   * @param request The request.
   * @param response The response.
   * @return A token.
   */
  @ResponseBody
  @PostMapping(value = "/login")
  public String login(
      @RequestParam(name = "u") final URL registry,
      @RequestBody final String request,
      final HttpServletResponse response) {
    String rtn = null;
    URI endpoint = null;
    try {
      final URL url = new URL(registry, "login");
      endpoint = url.toURI();
    } catch (MalformedURLException | URISyntaxException e) {
      throw new RuntimeException("Error with SynBioHub url.");
    }
    final RestTemplate rest = new RestTemplate();
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
    headers.setContentType(MediaType.APPLICATION_JSON);
    final HttpEntity<String> entity = new HttpEntity<>(request, headers);
    final ResponseEntity<String> result =
        rest.exchange(endpoint, HttpMethod.POST, entity, String.class);
    rtn = result.getBody();
    return rtn;
  }

  /**
   * Submit a project file to a SynBioHub.
   *
   * @param user The user to whom the project belongs.
   * @param registry The registry URL to submit to.
   * @param token The SynBioHub login token.
   * @param projectName The name of the project.
   * @param fileName The name of the file to submit.
   * @param request The SynBioHub request.
   * @param response The SynBioHub response.
   * @throws IOException Unable to read file.
   * @throws ResourceNotFoundException Unable to find file.
   */
  @ResponseBody
  // TODO Change endpoint to something more aligned with others, like
  // /project/{name}/result/{result}/submit or /synbiohub or /registry
  @PostMapping(value = "/submit/{project}/{file}")
  public void submit(
      final ApplicationUser user,
      @RequestParam(name = "u") final URL registry,
      @RequestHeader(value = "X-authorization") final String token,
      @PathVariable(value = "project") final String projectName,
      @PathVariable(value = "file") final String fileName,
      @RequestBody final JsonNode request,
      final HttpServletResponse response)
      throws IOException, ResourceNotFoundException {
    // rest
    final RestTemplate rest = new RestTemplate();

    // url
    URI uri;
    try {
      final URL url = new URL(registry, "submit");
      uri = url.toURI();
    } catch (MalformedURLException | URISyntaxException e) {
      return;
    }

    // headers
    final HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    headers.set("X-authorization", token);

    // body
    final MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    if (!request.has("collection")) {
      return;
    }
    final JsonNode coll = request.get("collection");
    if (coll.has("uri") && !coll.get("uri").asText("").equals("")) {
      map.add("rootCollections", coll.get("uri").toString());
    } else {
      try {
        map.add("id", coll.get("id").asText());
        map.add("version", coll.get("version").asText());
        map.add("name", coll.get("name").asText());
        map.add("description", coll.get("description").asText());
        map.add("citations", coll.get("citations").asText(""));
      } catch (final NullPointerException e) {
        return;
      }
      map.add("collectionChoices", "");
    }
    try {
      map.add("overwrite_merge", coll.get("overwrite").asBoolean() ? 1 : 0);
    } catch (final NullPointerException e) {
      return;
    }
    map.add("user", token);

    Project project = null;
    final Iterator<Project> it = user.getProjects().iterator();
    while (it.hasNext()) {
      final Project p = it.next();
      if (p.getName().equals(projectName)) {
        project = p;
      }
    }
    final Result r = Utils.findCObjectByName(fileName, project.getResults());
    map.add("file", new ByteArrayResource(Files.readAllBytes(r.getFile().toPath())));

    // entity
    final HttpEntity<?> entity = new HttpEntity<>(map, headers);

    // response
    try {
      rest.exchange(uri, HttpMethod.POST, entity, String.class);
    } catch (final RestClientException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
