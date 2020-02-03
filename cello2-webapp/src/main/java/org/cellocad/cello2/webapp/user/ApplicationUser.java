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
package org.cellocad.cello2.webapp.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.project.Project;
import org.cellocad.cello2.webapp.specification.library.TargetDataLibraryResource;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 *
 *
 * @author Timothy Jones
 *
 * 2019-03-17
 *
 */
@Persistent
public class ApplicationUser {

	@Id
    private ObjectId id = new ObjectId();
	@NotNull(message = "Missing username.")
    private String username;
	@NotNull(message = "Missing password.")
    private String password;
	private String institution;
	private String name;

	private Collection<TargetDataLibraryResource> ucfLibraryResources = new ArrayList<TargetDataLibraryResource>();

	@DBRef
	private Collection<Project> projects = new LinkedList<Project>();

    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	/**
	 * Getter for <i>institution</i>
	 * @return value of <i>institution</i>
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 * Setter for <i>institution</i>
	 * @param institution the value to set <i>institution</i>
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * Getter for <i>name</i>
	 * @return value of <i>name</i>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for <i>name</i>
	 * @param name the value to set <i>name</i>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for <i>ucfs</i>
	 * @return value of <i>ucfs</i>
	 */
	public Collection<TargetDataLibraryResource> getUcfLibraryResources() {
		return ucfLibraryResources;
	}

	/**
	 * Getter for <i>projects</i>
	 * @return value of <i>projects</i>
	 */
	public Collection<Project> getProjects() {
		return projects;
	}

	/**
	 * Setter for <i>projects</i>
	 * @param projects the value to set <i>projects</i>
	 */
	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}
	
}
