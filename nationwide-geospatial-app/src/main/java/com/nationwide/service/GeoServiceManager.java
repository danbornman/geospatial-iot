package com.nationwide.service;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.nationwide.client.CustomSSLContext;
import com.nationwide.model.Coordinates;
import com.nationwide.model.CustomRegion;
import com.nationwide.model.JsonUtils;
import com.nationwide.model.RegularRegion;
import com.nationwide.mqtt.GeoServiceStartMqtt;



public class GeoServiceManager {
	
	/*
	 * {
  "Geospatial Analytics": [
    {
      "name": "Geospatial Analytics-d6",
      "label": "Geospatial Analytics",
      "plan": "Standard",
      "credentials": {
        "password": "c609cbb1-0b9e-4721-8a4e-36e1d072afbd",
        "geo_host": "streams-broker.ng.bluemix.net",
        "dashboard_path": "/jax-rs/dashboard/183a8027-f417-4270-9712-13d6d9d2739b",
        "stop_path": "/jax-rs/geo/stop/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd",
        "geo_port": 443,
        "remove_region_path": "/jax-rs/geo/removeRegion/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd",
        "restart_path": "/jax-rs/geo/restart/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd",
        "start_path": "/jax-rs/geo/start/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd",
        "add_region_path": "/jax-rs/geo/addRegion/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd",
        "userid": "36639337-9bf3-4f46-ba06-65b989084d18",
        "status_path": "/jax-rs/geo/status/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd"
      }
    }
  ]
}
	 */
	private static Logger logger = Logger.getLogger(GeoServiceManager.class.getName());
	private static String geoHost = "streams-broker.ng.bluemix.net";
	private static String startPath =         "/jax-rs/geo/start/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd";
	private static String restartPath =     "/jax-rs/geo/restart/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd";
	private static String statusPath =       "/jax-rs/geo/status/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd";
	private static String stopPath =           "/jax-rs/geo/stop/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd";
	private static String addPath =       "/jax-rs/geo/addRegion/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd";
	private static String removePath = "/jax-rs/geo/removeRegion/service_instances/183a8027-f417-4270-9712-13d6d9d2739b/service_bindings/4b2733ae-c8ee-48c7-94dd-d58477cee4dd";
	private static String username = "36639337-9bf3-4f46-ba06-65b989084d18";
	private static String password = "c609cbb1-0b9e-4721-8a4e-36e1d072afbd";
	
	private static Response response;
	
	private static GeoServiceStartMqtt startMqtt = new GeoServiceStartMqtt("a-hl3q6t-csof6mpfy7", "X0bAwfQXcTD5CzVxBc", 
																	"hl3q6t.messaging.internetofthings.ibmcloud.com:1883",
																	"iot-2/type/auto/id/01/evt/geo/fmt/json", "a:hl3q6t:autoSim1_input", 
																	"iot-2/type/auto/id/01/evt/fence/fmt/json", "a:hl3q6t:autoSim1_notify",
																	"id", "lat", "lng");
	
	public GeoServiceManager() {
		// default constructor 
	}

	public static Response getGeoStatus() throws KeyManagementException, NoSuchAlgorithmException{
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username,password);
		Client client = CustomSSLContext.initClient();
		URI statusURI = UriBuilder.fromPath("").host(geoHost).scheme("https")
							.path(statusPath).port(443).build();
		WebTarget webTarget = client.target(statusURI.toString()).register(feature);
		Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON).buildGet();
		response = invocation.invoke();
		return response;
	}

	public static Response startGeoService() throws KeyManagementException, NoSuchAlgorithmException{	
		String mqttJson = JsonUtils.getJson(startMqtt);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username,password);
		Client client = CustomSSLContext.initClient();
		URI statusURI = UriBuilder.fromPath("").host(geoHost).scheme("https").path(startPath).port(443).build();
		WebTarget webTarget = client.target(statusURI.toString()).register(feature);
		Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON)
				.buildPut(Entity.entity(mqttJson, MediaType.APPLICATION_JSON));
		response = invocation.invoke();
		return response;
	}
	
	public static Response restartGeoService() throws KeyManagementException, NoSuchAlgorithmException{
		String mqttJson = JsonUtils.getJson(startMqtt);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username,password);
		Client client = CustomSSLContext.initClient();
		URI statusURI = UriBuilder.fromPath("").host(geoHost).scheme("https")
							.path(restartPath).port(443).build();
		WebTarget webTarget = client.target(statusURI.toString()).register(feature);
		Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON)
				.buildPut(Entity.entity(mqttJson, MediaType.APPLICATION_JSON));
		response = invocation.invoke();
		return response;
	}
	
	// jersey complains if the message body is empty so I send the same json payload as when the start
	// service is called.
	public static Response stopGeoService() throws KeyManagementException, NoSuchAlgorithmException{
		String mqttJson = JsonUtils.getJson(startMqtt);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username,password);
		Client client = CustomSSLContext.initClient();
		URI statusURI = UriBuilder.fromPath("").host(geoHost).scheme("https")
							.path(stopPath).port(443).build();
		WebTarget webTarget = client.target(statusURI.toString())
				.register(feature);
		Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON)
				.buildPut(Entity.entity(mqttJson, MediaType.APPLICATION_JSON));
		response = invocation.invoke();
		return response;
	}
	
	// PUT request to create a GeoFence specified by String region
	// String region must be a JSON formatted string created from the CustomRegion or RegularRegion classes
	public static Response setGeoRegions(String region) throws KeyManagementException, NoSuchAlgorithmException {
		//String region = createRegularRegion();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username,password);
		Client client = CustomSSLContext.initClient();
		URI statusURI = UriBuilder.fromPath("").host(geoHost).scheme("https")
							.path(addPath).port(443).build();
		String uri = statusURI.toString();
		//System.out.println(uri);
		WebTarget webTarget = client.target(statusURI.toString()).register(feature);
		Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON)
				.buildPut(Entity.entity(region, MediaType.APPLICATION_JSON));
		response = invocation.invoke();
		return response;
	}
	
	public static Response removeGeoRegion(String region) throws KeyManagementException, NoSuchAlgorithmException {
		logger.info(region);
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username,password);
		Client client = CustomSSLContext.initClient();
		URI statusURI = UriBuilder.fromPath("").host(geoHost).scheme("https")
							.path(removePath).port(443).build();
		String uri = statusURI.toString();
		//System.out.println(uri);
		WebTarget webTarget = client.target(statusURI.toString()).register(feature);
		Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON)
				.buildPut(Entity.entity(region, MediaType.APPLICATION_JSON));
		response = invocation.invoke();
		logger.info("removeGeoRegion(): " + response.getStatus());
		return response;				
	}
	
/*	public static String mqttInitialize(){

		String mqtt_uid = "a-hl3q6t-csof6mpfy7";
		String mqtt_pw = "X0bAwfQXcTD5CzVxBc";
		String mqtt_uri = "hl3q6t.messaging.internetofthings.ibmcloud.com:8883";
		String mqtt_input_topics = "iot-2/type/auto/id/01/evt/geo/fmt/json";
		String mqtt_notify_topic = "iot-2/type/auto/id/01/evt/geo/fmt/json";
		String device_id_attr_name = "id";
		String latitude_attr_name = "lat";
		String longitude_attr_name = "lng";
		
		Map<String,String> mqttObj = new HashMap<String,String>();
		mqttObj.put("mqtt_uid", mqtt_uid);
		mqttObj.put("mqtt_pw", mqtt_pw);
		mqttObj.put("mqtt_uri", mqtt_uri);
		mqttObj.put("mqtt_input_topics", mqtt_input_topics);
		mqttObj.put("mqtt_notify_topic", mqtt_notify_topic);
		mqttObj.put("device_id_attr_name", device_id_attr_name);
		mqttObj.put("latitude_attr_name", latitude_attr_name);
		mqttObj.put("longitude_attr_name", longitude_attr_name);
		
		Gson gson = new Gson();
		String mqttStart = gson.toJson(mqttObj);
		return mqttStart;
	}*/

	public static String createRemoveRegion(){
		String region_type = "regular";
		String region_name = "Promo Zone 1";
		Map<String,String> removeObj = new HashMap<String,String>();
		removeObj.put("region_type",region_type);
		removeObj.put("region_name",region_name);
		Gson gson = new Gson();
		String removeThis = gson.toJson(removeObj);
		return removeThis;
	}
	
	public static String createRegularRegion(){
		// Add a region
		String regions = "regions";
		List<RegularRegion> regularRegion = new ArrayList<RegularRegion>();
		regularRegion.add(new RegularRegion("regular","newRegion",true, 41.464309, -93.722284, 10, 50));
		Map<String,List<RegularRegion>> newRegion = new HashMap<String,List<RegularRegion>>();
		newRegion.put(regions, regularRegion);
		Gson gson = new Gson();
		//System.out.println(gson.toJson(newRegion));
		//System.out.println("Adding a region");
		String region = gson.toJson(newRegion);
		return region;
	}
	
	public static CustomRegion createCustomRegion(){
		Coordinates point1 = new Coordinates(41.464309, -93.722284);
		Coordinates point2 = new Coordinates(41.464309, -93.710868);
		Coordinates point3 = new Coordinates(41.472477, -93.710868);

		List<Coordinates> polygon = new ArrayList<Coordinates>();
		polygon.add(point1);
		polygon.add(point2);
		polygon.add(point3);
		
		CustomRegion myRegion = new CustomRegion("custom", "Geofence Test", true, polygon);
		return myRegion;
	}
	
	public static void main(String[] args) throws  NoSuchAlgorithmException, KeyManagementException {
		
/*		String initMqtt = mqttInitialize();
		System.out.println(initMqtt);
		
		GeoServiceStartMqtt startMqtt = new GeoServiceStartMqtt("iamuser", "thepass", "52.4.65.48:8000",
				"geotopic/iot/#", "geotopic/cars/events", "id", "lat", "lng");
		String mqttJson = JsonUtils.getJson(startMqtt);
		System.out.println(mqttJson);
		
		JSONObject mqttObj = createMqttObject();
		//String mqttString = mqttObj.toString();
		System.out.println(mqttObj);*/
				
	}
}
