package com.nationwide.service;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.nationwide.client.CustomSSLContext;
import com.nationwide.listener.PropertiesListener;
import com.nationwide.model.JsonUtils;
import com.nationwide.mqtt.GeoServiceStartMqtt;

public class GeoServiceManager {

	private static Logger logger = Logger.getLogger(GeoServiceManager.class.getName());
/*	
	private static String geoHost = "streams-broker.ng.bluemix.net";
	private static String startPath =         "/jax-rs/geo/start/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
	private static String restartPath =     "/jax-rs/geo/restart/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
	private static String statusPath =       "/jax-rs/geo/status/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
	private static String stopPath =           "/jax-rs/geo/stop/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
	private static String addPath =       "/jax-rs/geo/addRegion/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
	private static String removePath = "/jax-rs/geo/removeRegion/service_instances/51e74520-204d-4993-9603-b58877850ed2/service_bindings/c13ef3c5-25cf-4856-91d6-45c9d53e6195";
	private static String username = "e84012e0-1b46-4b5f-bf43-9eee4fb3f60a";
	private static String password = "f5a33688-c2e4-4639-988f-d4bf8d505857";
*/	
	private static String geoHost = GeoServiceConnectionMngr.getGeoHost();
	private static String startPath = GeoServiceConnectionMngr.getStartPath();
	private static String restartPath = GeoServiceConnectionMngr.getRestartPath();
	private static String statusPath = GeoServiceConnectionMngr.getStatusPath();
	private static String stopPath = GeoServiceConnectionMngr.getStopPath();
	private static String addPath = GeoServiceConnectionMngr.getAddPath();
	private static String removePath = GeoServiceConnectionMngr.getRemovePath();
	private static String username = GeoServiceConnectionMngr.getUsername();
	private static String password = GeoServiceConnectionMngr.getPassword();
	
	private static String mqtt_uid = PropertiesListener.applicationProperties.get("mqtt_uid");
	private static String mqtt_pw = PropertiesListener.applicationProperties.get("mqtt_pw");
	private static String mqtt_uri = PropertiesListener.applicationProperties.get("mqtt_uri");
	private static String mqtt_input_topic = PropertiesListener.applicationProperties.get("mqtt_input_topic");
	private static String mqtt_client_id_input = PropertiesListener.applicationProperties.get("mqtt_client_id_input");
	private static String mqtt_notify_topic = PropertiesListener.applicationProperties.get("mqtt_notify_topic");
	private static String mqtt_client_id_notify = PropertiesListener.applicationProperties.get("mqtt_client_id_notify");
	
	private static Response response;
	
	private static GeoServiceStartMqtt startMqtt = new GeoServiceStartMqtt(mqtt_uid, mqtt_pw, mqtt_uri,
													mqtt_input_topic, mqtt_client_id_input, 
													mqtt_notify_topic, mqtt_client_id_notify,
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
		logger.info(mqttJson);
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
		WebTarget webTarget = client.target(statusURI.toString()).register(feature);
		Invocation invocation = webTarget.request(MediaType.APPLICATION_JSON)
				.buildPut(Entity.entity(region, MediaType.APPLICATION_JSON));
		response = invocation.invoke();
		logger.info("removeGeoRegion(): " + response.getStatus());
		return response;				
	}
	
}
