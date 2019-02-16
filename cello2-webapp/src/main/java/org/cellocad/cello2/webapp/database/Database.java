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
package org.cellocad.cello2.webapp.database;

import org.cellocad.cello2.webapp.schemas.Entities;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

/**
 *
 * @author frascog
 */
public class Database {
    
    private static Database instance;
    
    private final String uri = this.setValue(System.getenv("database_uri"), "localhost");
    private final int port = new Integer(this.setValue(System.getenv("database_port"), "27017"));
    private final String name = this.setValue(System.getenv("database_name"), "cello2");
    
    
    private MongoClient mongoClient;
    private Morphia morphia;
    private Datastore datastore;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Database() {
        this.mongoClient = new MongoClient();
        this.morphia = new Morphia();
        this.morphia.map(Entities.class);
        this.datastore = this.morphia.createDatastore(mongoClient, this.name);
        this.datastore.ensureIndexes();
    }
    
    public static void init() {
        Database.getInstance();
    }
    
    private String setValue(String value, String defaultValue) {
        return isNotNullOrEmpty(value) ? value : defaultValue;
    }
    
    private static boolean isNotNullOrEmpty(String str){
        return (str != null && !str.isEmpty());
    }
    
    public void save(Object object) {
        this.datastore.save(object);
    }

	/**
	 * Getter for <i>datastore</i>
	 * @return value of <i>datastore</i>
	 */
	public Datastore getDatastore() {
		return datastore;
	}
    
}


