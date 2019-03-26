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

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.cellocad.cello2.webapp.project.ProjectUtils;
import org.cellocad.cello2.webapp.security.SecurityConstants;
import org.cellocad.cello2.webapp.specification.library.UCFLibraryResource;
import org.cellocad.cello2.webapp.user.ApplicationUser;
import org.cellocad.cello2.webapp.user.ApplicationUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 *
 * @author Timothy Jones
 *
 * 2019-03-17
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody JsonNode node, HttpServletResponse res) {
    	ObjectMapper mapper = new ObjectMapper();
    	ApplicationUser user;
		try {
			user = mapper.convertValue(node, ApplicationUser.class);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (applicationUserRepository.findByUsername(user.getUsername()) != null)
        	throw new ResponseStatusException(HttpStatus.CONFLICT, "User exists.");
        applicationUserRepository.save(user);
        ProjectUtils.createUserDirectory(user);
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    }
    
    @GetMapping("/ucfs")
    public Collection<UCFLibraryResource> ucfs(ApplicationUser user) {
    	return user.getUcfLibraryResources();
    }
	
}
