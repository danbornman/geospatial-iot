package com.nationwide.service;

import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GeoServiceConnectionMngr {
	
	private static String geoHost = null;
	private static String startPath = null;
	private static String restartPath = null;
	private static String statusPath = null;
	private static String stopPath = null;
	private static String addPath = null;
	private static String removePath = null;
	private static String username = null;
	private static String password = null;
	
	public static String getGeoHost() {
		return geoHost;
	}
	public static String getStartPath() {
		return startPath;
	}
	public static String getRestartPath() {
		return restartPath;
	}
	public static String getStatusPath() {
		return statusPath;
	}
	public static String getStopPath() {
		return stopPath;
	}
	public static String getAddPath() {
		return addPath;
	}
	public static String getRemovePath() {
		return removePath;
	}
	public static String getUsername() {
		return username;
	}
	public static String getPassword() {
		return password;
	}
	public static void initGeoService(){
/*		geoHost = "streams-broker.ng.bluemix.net";
		startPath =         "/jax-rs/geo/start/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
		restartPath =     "/jax-rs/geo/restart/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
		statusPath =       "/jax-rs/geo/status/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
		stopPath =           "/jax-rs/geo/stop/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
		addPath =       "/jax-rs/geo/addRegion/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
		removePath = "/jax-rs/geo/removeRegion/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
		username = "e84012e0-1b46-4b5f-bf43-9eee4fb3f60a";
		password = "f5a33688-c2e4-4639-988f-d4bf8d505857";*/
		
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");
        String serviceName = null;
        
        if (VCAP_SERVICES != null) {
        	JsonObject obj = (JsonObject) new JsonParser().parse(VCAP_SERVICES);
        	Entry<String, JsonElement> dbEntry = null;
            Set<Entry<String, JsonElement>> entries = obj.entrySet();
            // Look for the VCAP key that holds the Geospatial credentials
            for (Entry<String, JsonElement> eachEntry : entries) {
                if (eachEntry.getKey().equals("Geospatial Analytics")) {
                    dbEntry = eachEntry;
                    break;
                }
            }
            if (dbEntry == null) {
                throw new RuntimeException("Could not find Geospatial Analytics key in VCAP_SERVICES env variable");
            }
            obj = (JsonObject) ((JsonArray) dbEntry.getValue()).get(0);
            serviceName = (String) dbEntry.getKey();
            System.out.println("Service Name - " + serviceName);

            obj = (JsonObject) obj.get("credentials");
            
            geoHost = obj.get("geo_host").getAsString();
            startPath = obj.get("start_path").getAsString();
            restartPath = obj.get("restart_path").getAsString();
            statusPath = obj.get("status_path").getAsString();
            stopPath = obj.get("stop_path").getAsString();
            addPath = obj.get("add_region_path").getAsString();
            removePath = obj.get("remove_region_path").getAsString();
            username = obj.get("userid").getAsString();
            password = obj.get("password").getAsString();
        }
	}

}
