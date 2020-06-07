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

package org.cellocad.v2.webapp.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.cellocad.v2.webapp.project.Project;
import org.cellocad.v2.webapp.specification.library.TargetDataLibraryResource;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * A user.
 *
 * @author Timothy Jones
 * @date 2019-03-17
 */
@JsonIgnoreProperties(ignoreUnknown = true) // added to ignore 'remember' property
@Persistent
public class ApplicationUser {

  @Id private ObjectId id = new ObjectId();

  @NotNull(message = "Missing username.")
  private String username;

  @NotNull(message = "Missing password.")
  private String password;

  private String institution;
  private String name;

  private Collection<TargetDataLibraryResource> ucfLibraryResources = new ArrayList<>();

  @DBRef private Collection<Project> projects = new LinkedList<>();

  public ObjectId getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  /**
   * Getter for {@code institution}.
   *
   * @return The value of {@code institution}.
   */
  public String getInstitution() {
    return institution;
  }

  /**
   * Setter for {@code institution}.
   *
   * @param institution The value to set {@code institution}.
   */
  public void setInstitution(final String institution) {
    this.institution = institution;
  }

  /**
   * Getter for {@code name}.
   *
   * @return The value of {@code name}.
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for {@code name}.
   *
   * @param name The value to set {@code name}.
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Getter for {@code ucfLibraryResources}.
   *
   * @return The value of {@code ucfLibraryResources}.
   */
  public Collection<TargetDataLibraryResource> getUcfLibraryResources() {
    return ucfLibraryResources;
  }

  /**
   * Getter for {@code projects}.
   *
   * @return The value of {@code projects}.
   */
  public Collection<Project> getProjects() {
    return projects;
  }

  /**
   * Setter for {@code projects}.
   *
   * @param projects The value to set {@code projects}.
   */
  public void setProjects(final Collection<Project> projects) {
    this.projects = projects;
  }
}
