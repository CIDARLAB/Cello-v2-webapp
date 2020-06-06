/*
 * Copyright (C) 2020 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
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

import java.io.IOException;
import java.util.Properties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for basic application properties and resources.
 *
 * @author Timothy Jones
 * @date 2020-06-05
 */
@RestController
public class ApplicationController {

  /**
   * Get the version identifier of the core Cello application.
   *
   * @return The version identifier of the core Cello application.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/version-core",
      produces = {MediaType.TEXT_PLAIN_VALUE})
  public String getCelloVersion() {
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
  }

  /**
   * Get the version identifier of the API.
   *
   * @return The version identifier of the API.
   */
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/version-api",
      produces = {MediaType.TEXT_PLAIN_VALUE})
  public String getApiVersion() {
    String rtn = null;
    final Properties properties = new Properties();
    try {
      properties.load(this.getClass().getClassLoader().getResourceAsStream(".properties"));
      rtn = properties.getProperty("cello-v2.api.version");
    } catch (IOException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error accessing resource.", e);
    }
    return rtn;
  }
}
