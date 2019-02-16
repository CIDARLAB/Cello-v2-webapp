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
package org.cellocad.cello2.webapp.schemas;

import java.util.Date;

import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.database.Database;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author frascog
 */
@Entity("sessions")
public class Session {
    
    @Id
    private ObjectId id;
    private final ObjectId userId;
    private final Date createdOn;
    private String token;

    public Session() {
        this.userId = null;
        this.createdOn = null;
    }

    public Session(User user,ObjectId key) {
        this.userId = user.getId();
        this.createdOn = new Date();
        this.token = BCrypt.hashpw(key.toString(), BCrypt.gensalt());
    }
    
    private static boolean validateSession(Session session,String key) {
        return BCrypt.checkpw(key, session.getToken());
    }
    
    public static Session findByCredentials(String id,String key) {
        
        Session session = Database.getInstance().getDatastore().get(Session.class,new ObjectId(id));
        if(session != null) {
           if(Session.validateSession(session, key)) {
            return session;
            } 
        }
        return null;
    }
    
    public static User getUser(Session session) {
        return Database.getInstance().getDatastore().get(User.class,session.getUserId());
    }
    
    public static void deleteSessionForUser(User user) {
        Query<Session> query = Database.getInstance().getDatastore().createQuery(Session.class).filter("userId", user.getId());
        Database.getInstance().getDatastore().findAndDelete(query);
    }

	/**
	 * Getter for <i>id</i>
	 * @return value of <i>id</i>
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Setter for <i>id</i>
	 * @param id the value to set <i>id</i>
	 */
	public void setId(final ObjectId id) {
		this.id = id;
	}

	/**
	 * Getter for <i>userId</i>
	 * @return value of <i>userId</i>
	 */
	public ObjectId getUserId() {
		return userId;
	}

	/**
	 * Getter for <i>createdOn</i>
	 * @return value of <i>createdOn</i>
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * Getter for <i>token</i>
	 * @return value of <i>token</i>
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Setter for <i>token</i>
	 * @param token the value to set <i>token</i>
	 */
	public void setToken(final String token) {
		this.token = token;
	}

}
