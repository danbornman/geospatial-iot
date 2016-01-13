package com.nationwide.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nationwide.model.JsonUtils;
import com.nationwide.model.Trip;
import com.nationwide.model.TripDetail;


@Path("/")
public class RestServiceTest {
	
	public String seconds = "0";

	@GET
	@Path("tripdata/{batchNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TripDetail> getRequest(@PathParam("batchNum") String batchNum){
		DataServiceTest dataService = new DataServiceTest();
		return dataService.getDetailsList(batchNum); // returns List<TripDetail>
	}
	
	@GET
	@Path("tripdetails/{batchNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJsonDetails(@PathParam("batchNum") String batchNum){
		DataServiceTest dataService = new DataServiceTest();
		List<TripDetail> detailsList = dataService.getDetailsList(batchNum);
		List<String> details = new ArrayList<String>();
		
		Iterator<TripDetail> iter = detailsList.iterator();
		while(iter.hasNext()){
			TripDetail td = iter.next();
			String detailString = JsonUtils.getJson(td);
			details.add(detailString);
		}
		
		Gson gson = new Gson();
		String jsonDetails = gson.toJson(details, new TypeToken<List<String>>() {}.getType());
		return jsonDetails;
	}
	
	@GET
	@Path("trip/{batchNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTripRequest(@PathParam("batchNum") String batchNum){
		DataServiceTest dataService = new DataServiceTest();
		Trip trip = dataService.getTrip(batchNum);
		
		Map<String,String> tripSummary = new LinkedHashMap<String,String>();
		tripSummary.put("Batch ID",batchNum);
		tripSummary.put("VIN", trip.getVin());
		tripSummary.put("Date", trip.getDate());
		TripDetail td = trip.getTripDetails().get(0);
		tripSummary.put("Starting Latitude", td.getLatitude());
		tripSummary.put("Starting Longitude", td.getLongitude());
		String tripLength = new Integer(trip.getTripDetails().size()).toString();
		tripSummary.put("Number of data points", tripLength);

		Gson gson = new Gson();
		String element = gson.toJson(tripSummary,
				new TypeToken<Map<String,String>>() {}.getType());
		return element;
	}
	
	@GET
	@Path("getvin/{batchNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVinRequest(@PathParam("batchNum") String batchNum){
		DataServiceTest dataService = new DataServiceTest();
		return dataService.getVinFromTrip(batchNum); // returns Vin as String
	}

	@GET
	@Path("getdate/{batchNum}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDateRequest(@PathParam("batchNum") String batchNum){
		DataServiceTest dataService = new DataServiceTest();
		return dataService.getDateFromTrip(batchNum); // returns Date as String
	}
}

