package com.nationwide.service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nationwide.model.Trip;
import com.nationwide.model.TripDetail;
import com.nationwide.repo.CloudantConnectionMngr;

public class DataServiceTest {

	public String batchNum = "20647";
	Database db = getDB();
	
	public DataServiceTest() {}

	// return the entire list of stored TripDetail objects
	public List<TripDetail> getDetailsList(String batchNum){
		Trip myTrip = getTrip(batchNum);
		List<TripDetail> details = myTrip.getTripDetails();
		return details;
	}
	// create a Trip object from the provide trip batch number
	public Trip getTrip(String batchNum){
		Trip myTrip = new Trip();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> obj = db.find(HashMap.class, batchNum);
		String vin = (String) obj.get("vin");
		String date = (String) obj.get("date");
		System.out.println("VIN found: " + vin);
		System.out.println("Date found: " + date);
		String detailsString = obj.get("trip_details").toString();
		Gson gson = new Gson();
		Type type = new TypeToken<List<TripDetail>>(){}.getType();
		List<TripDetail> tripDetails = gson.fromJson(detailsString, type);
		//@SuppressWarnings("unchecked")
		//List<TripDetail> tripDetails = (List<TripDetail>) obj.get("trip_details");
		myTrip.setVin(vin);
		myTrip.setDate(date);
		myTrip.setTripDetails(tripDetails);
		TripDetail firstDetail = tripDetails.get(0);
		System.out.println("Lat of first Detail: " + firstDetail.getLatitude());
		System.out.println("Long of first Detail: " + firstDetail.getLongitude());

		return myTrip;
	}
	
	private Database getDB(){
		return CloudantConnectionMngr.getDB();
	}
	
		
	public String getVinFromTrip(String batchNum){
		Trip myTrip = new Trip();
		myTrip = getTrip(batchNum);
		String myVin = myTrip.getVin();
		return myVin;
	}
	
	public String getDateFromTrip(String batchNum){
		Trip myTrip = new Trip();
		myTrip = getTrip(batchNum);
		String myDate = myTrip.getDate();
		return myDate;
	}
	
	public String hashSize(){
		@SuppressWarnings("unchecked")
		HashMap<String, Object> obj = db.find(HashMap.class, batchNum);
		int numPairs = obj.size();
		//return numPairs;
		return "Number of key:value pairs = " + numPairs;
	}

}
