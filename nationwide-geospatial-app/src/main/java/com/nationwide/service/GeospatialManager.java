package com.nationwide.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

public class GeospatialManager {
	
	static String region;
	private static Logger logger = Logger.getLogger(GeospatialManager.class.getName());
	
	public GeospatialManager(){
		// default constructor
	}
	
	public static int geoStart() throws KeyManagementException, NoSuchAlgorithmException{
		Response response = GeoServiceManager.startGeoService();
		return response.getStatus();
	}
	
	public static int geoRestart() throws KeyManagementException, NoSuchAlgorithmException{
		Response response = GeoServiceManager.restartGeoService();
		return response.getStatus();
	}
	
	public static String geoStatus() throws KeyManagementException, NoSuchAlgorithmException{
		Response response = GeoServiceManager.getGeoStatus();
		//return response.getStatus();
		return response.readEntity(String.class);
	}
	
	public static int geoRegions(String addInfo) throws KeyManagementException, NoSuchAlgorithmException {
		Response response = GeoServiceManager.setGeoRegions(addInfo);
		return response.getStatus();
	}
	
	public static int geoStop() throws KeyManagementException, NoSuchAlgorithmException {
		Response response = GeoServiceManager.stopGeoService();
		return response.getStatus();
	}
	
	public static int geoRemove(String removeInfo) throws KeyManagementException, NoSuchAlgorithmException {
		Response response = GeoServiceManager.removeGeoRegion(removeInfo);
		logger.info("geoRemove(): " + response.getStatus());
		return response.getStatus();
	}

}
