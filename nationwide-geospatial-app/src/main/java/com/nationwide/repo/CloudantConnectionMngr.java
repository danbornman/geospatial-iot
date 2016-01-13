package com.nationwide.repo;

import java.util.Map.Entry;

import org.lightcouch.CouchDbException;

import java.util.Set;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CloudantConnectionMngr {

	private static CloudantClient cloudant = null;
    private static Database db = null;

    private static String databaseName = "smartride";

    private static String url = null;
    private static String user = null;
    private static String password = null;
	
    // initialize connection to cloudant service initClient() --> creatClient()
    private static void initClient() {
        if (cloudant == null) {
            synchronized (CloudantConnectionMngr.class) {
                if (cloudant != null) {
                    return;
                }
                cloudant = createClient();

            }// end synchronized
        }
    }
    
    // default constructor
    public CloudantConnectionMngr() {}
    
/*
 * VCAP_SERVICES data
 *     {
    	  "cloudantNoSQLDB": [
    	    {
    	      "name": "Cloudant NoSQL DB-f1",
    	      "label": "cloudantNoSQLDB",
    	      "plan": "Shared",
    	      "credentials": {
    	        "username": "dc833ee4-c842-4a2d-aae4-82a741c7267d-bluemix",
    	        "password": "b1674f9cad3e05aceb1df2909896975ab838dcfab586c49e69960fa80076bfe2",
    	        "host": "dc833ee4-c842-4a2d-aae4-82a741c7267d-bluemix.cloudant.com",
    	        "port": 443,
    	        "url": "https://dc833ee4-c842-4a2d-aae4-82a741c7267d-bluemix:b1674f9cad3e05aceb1df2909896975ab838dcfab586c49e69960fa80076bfe2@dc833ee4-c842-4a2d-aae4-82a741c7267d-bluemix.cloudant.com"
    	      }
    	    }
    	  ]
    	}
*/    
    // use VCAP_SERVICES variables to connect to cloudant service
    private static CloudantClient createClient() {
        String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
        String serviceName = null;

        if (VCAP_SERVICES != null) {
            // parse the VCAP JSON structure
            JsonObject obj = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
            Entry<String, JsonElement> dbEntry = null;
            Set<Entry<String, JsonElement>> entries = obj.entrySet();
            // Look for the VCAP key that holds the cloudant smartride db
            for (Entry<String, JsonElement> eachEntry : entries) {
                if (eachEntry.getKey().equals("cloudantNoSQLDB")) {
                    dbEntry = eachEntry;
                    break;
                }
            }
            if (dbEntry == null) {
                throw new RuntimeException("Could not find cloudantNoSQLDB key in VCAP_SERVICES env variable");
            }
            obj = (JsonObject) ((JsonArray) dbEntry.getValue()).get(0);
            serviceName = (String) dbEntry.getKey();
            System.out.println("Service Name - " + serviceName);

            obj = (JsonObject) obj.get("credentials");

            user = obj.get("username").getAsString();
            password = obj.get("password").getAsString();
            url = obj.get("url").getAsString();
            System.out.println("username: " + user);
            System.out.println("password: " + password);
            System.out.println("url: " + url);

        } else {
            throw new RuntimeException("VCAP_SERVICES not found");
        }

        
        try {
            return new CloudantClient(user, user, password);
        } catch (CouchDbException e) {
            throw new RuntimeException("Unable to connect to repository", e);
        }
        
    }
    
    public static Database getDB() {
        if (cloudant == null) {
            initClient();
        }
        if (db == null) {
            try {
                db = cloudant.database(databaseName, true);
            } catch (Exception e) {
                throw new RuntimeException("DB Not found", e);
            }
        }
        return db;
    }

    
    
    public static String getDatabaseURL() {
        return url + "/" + databaseName + "/";
    }

}
