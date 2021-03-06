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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cellocad.v2.webapp.common.Utils;
import org.cellocad.v2.webapp.exception.CelloWebException;
import org.cellocad.v2.webapp.security.SecurityConstants;
import org.cellocad.v2.webapp.user.ApplicationUser;
import org.cellocad.v2.webapp.user.ApplicationUserRepository;
import org.cellocad.v2.webapp.user.UserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * User controller.
 *
 * @author Timothy Jones
 * @date 2019-03-17
 */
@RestController
public class UserController {

  private ApplicationUserRepository applicationUserRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserController(
      final ApplicationUserRepository applicationUserRepository,
      final BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.applicationUserRepository = applicationUserRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  private static Logger getLogger() {
    return LogManager.getLogger(UserController.class);
  }

  /** Initialize controller. */
  @PostConstruct
  public void init() {
    final String dir = UserUtils.getUsersDirectory();
    if (!Utils.isValidFilepath(dir)) {
      UserUtils.initUsersDirectory();
      getLogger().debug("Users directory initialized.");
    }
  }

  /**
   * User signup.
   *
   * @param node The request body including username and password.
   * @param res The response.
   * @throws CelloWebException Unable to initialize user.
   */
  @RequestMapping(method = RequestMethod.POST, value = "/users")
  public void signup(@Valid @RequestBody final JsonNode node, final HttpServletResponse res)
      throws CelloWebException {
    final ObjectMapper mapper = new ObjectMapper();
    ApplicationUser user;
    try {
      user = mapper.convertValue(node, ApplicationUser.class);
    } catch (final Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    if (applicationUserRepository.findByUsername(user.getUsername()) != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User exists.");
    }
    applicationUserRepository.save(user);
    try {
      UserUtils.initUserDirectory(user);
    } catch (IOException e) {
      throw new CelloWebException("Unable to initialize user.");
    }
    final String token =
        JWT.create()
            .withSubject(user.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
  }
}
