/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cellocad.cello2.webapp.schemas;

import java.util.Date;
import org.bson.types.ObjectId;
import org.cellocad.cello2.webapp.database.Database;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author frascog
 */
@Entity("users")
public class User {
    
    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String institution;
    private final Date createdOn;
    private String password;
    private boolean verfied;
    private String verfiedId;
    private String forgotPasswordKey;
    private boolean advancedUser;
    private String emailOptions;
    private String[] registries;

    public User() {
        this.createdOn = null;
    }
    
    public User(String name,String email,String password,String institution) {
        this.id = new ObjectId();
        this.createdOn = new Date();
        this.name = name;
        this.email = email;
        this.institution = institution;
        this.setPassword(password);
        this.verfied = false;
        this.verfiedId = new ObjectId().toString();
    }
    
    public void setPassword(String plainText) {
        this.password =  BCrypt.hashpw(plainText, BCrypt.gensalt());
    }
    
    public static User findByCredentials(String email, String plainTextPassword){
        User user = Database.getInstance().getDatastore().createQuery(User.class).filter("email", email).get();
        if(user != null) {
            if(BCrypt.checkpw(plainTextPassword, user.getPassword())) {
                return user;
            }  
        }
        return null;
    }
    
    public static User findByVerifyId(String id) {
        return Database.getInstance().getDatastore().createQuery(User.class).filter("verfiedId", id).get();
    }
    
    public void verifyUser() {
        if(!this.verfied) {
            this.verfied = true;
            Database.getInstance().save(this);
        }
    }
    
    public static boolean userExists(String email) {
        return Database.getInstance().getDatastore().createQuery(User.class).filter("email", email).get() != null;
    }
    
    public static User getUserByEmail(String email) {
        return Database.getInstance().getDatastore().createQuery(User.class).filter("email", email).get();
    }
    
    public void setForgotPasswordKey(String key) {
        if(key == null) {
            this.forgotPasswordKey = null;
        } else {
            this.forgotPasswordKey = BCrypt.hashpw(key, BCrypt.gensalt());
        }
        Database.getInstance().save(this);
    }

    public boolean checkForgotPasswordKey(String key) {
        if(this.forgotPasswordKey != null) {
            return BCrypt.checkpw(key, this.forgotPasswordKey);
        }
        return false;
    }
    
    public void save() {
        Database.getInstance().save(this);
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
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Getter for <i>email</i>
	 * @return value of <i>email</i>
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for <i>email</i>
	 * @param email the value to set <i>email</i>
	 */
	public void setEmail(final String email) {
		this.email = email;
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
	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	/**
	 * Getter for <i>advancedUser</i>
	 * @return value of <i>advancedUser</i>
	 */
	public boolean isAdvancedUser() {
		return advancedUser;
	}

	/**
	 * Setter for <i>advancedUser</i>
	 * @param advancedUser the value to set <i>advancedUser</i>
	 */
	public void setAdvancedUser(final boolean advancedUser) {
		this.advancedUser = advancedUser;
	}

	/**
	 * Getter for <i>emailOptions</i>
	 * @return value of <i>emailOptions</i>
	 */
	public String getEmailOptions() {
		return emailOptions;
	}

	/**
	 * Setter for <i>emailOptions</i>
	 * @param emailOptions the value to set <i>emailOptions</i>
	 */
	public void setEmailOptions(final String emailOptions) {
		this.emailOptions = emailOptions;
	}

	/**
	 * Getter for <i>registries</i>
	 * @return value of <i>registries</i>
	 */
	public String[] getRegistries() {
		return registries;
	}

	/**
	 * Setter for <i>registries</i>
	 * @param registries the value to set <i>registries</i>
	 */
	public void setRegistries(final String[] registries) {
		this.registries = registries;
	}

	/**
	 * Getter for <i>createdOn</i>
	 * @return value of <i>createdOn</i>
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * Getter for <i>password</i>
	 * @return value of <i>password</i>
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Getter for <i>forgotPasswordKey</i>
	 * @return value of <i>forgotPasswordKey</i>
	 */
	public String getForgotPasswordKey() {
		return forgotPasswordKey;
	}

}
