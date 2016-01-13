package com.nationwide.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
// http://cloudant-rest-example.mybluemix.net/webapi
@Path("/")
public class GeoRestService {

	private int status;
	
	public GeoRestService(){
		// default constructor
	}
	
	@GET
	@Path("geostart")
	// http://cloudant-rest-example.mybluemix.net/webapi/geostart
	@Produces(MediaType.TEXT_PLAIN)
	public int startGeoService() {
		//GeospatialManager geoManager = new GeospatialManager();
		try {
			status = GeospatialManager.geoStart();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	@Path("georestart")
	// http://cloudant-rest-example.mybluemix.net/webapi/geostart
	@Produces(MediaType.TEXT_PLAIN)
	public int restartGeoService() {
		//GeospatialManager geoManager = new GeospatialManager();
		try {
			status = GeospatialManager.geoRestart();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	@GET
	@Path("geostatus")
	@Produces(MediaType.APPLICATION_JSON)
	public String statusGeoService(){
		String message = "";
		//GeospatialManager geoManager = new GeospatialManager();
		try {
			message = GeospatialManager.geoStatus();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
	
	
	@GET
	@Path("geostop")
	@Produces(MediaType.TEXT_PLAIN)
	public int stopGeoService(){
		//GeospatialManager geoManager = new GeospatialManager();
		try {
			status = GeospatialManager.geoStop();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	@POST
	@Path("georegion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int regionsGeoService(String addInfo){
		try {
			status = GeospatialManager.geoRegions(addInfo);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	@POST
	@Path("georemove")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int removeGeoRegion(String removeInfo){
		try {
			status = GeospatialManager.geoRemove(removeInfo);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}
