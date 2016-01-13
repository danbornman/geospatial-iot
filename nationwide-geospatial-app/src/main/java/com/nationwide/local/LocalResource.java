/*
package com.nationwide.local;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.nationwide.model.TripDetail;

@Path("/")
public class LocalResource {

	@GET
	@Path("tripdata/{batchNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TripDetail> getRequest(@PathParam("batchNum") String batchNum){
		LocalData localData = new LocalData();
		return localData.getDetailsList(batchNum);
	}
	
	@GET
	@Path("getvin/{batchNum}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getVinRequest(@PathParam("batchNum") String batchNum){
		LocalData localData = new LocalData();
		return localData.getVinNumber(batchNum);
		
	}
	
}
*/