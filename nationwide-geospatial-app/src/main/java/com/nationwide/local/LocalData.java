/*
package com.nationwide.local;

import java.util.ArrayList;
import java.util.List;

import com.nationwide.model.Trip;
import com.nationwide.model.TripDetail;

public class LocalData {

	public LocalData(){
	}
	
	public Trip getTrip(String batchNum){
		Trip myTrip = new Trip();
		List<TripDetail> detailList = new ArrayList<TripDetail>();
		myTrip.setVin("BVHN989348934");
		myTrip.setBatchNum("20647");
		myTrip.setDate("11/21/1971");
		TripDetail myDetails = new TripDetail();
		myDetails.setLatitude("41.428917");
		myDetails.setLongitude("-93.72628");
		myDetails.setHeading("0");
		myDetails.setPositionalQual(null);
		myDetails.setTime("1");
		detailList.add(myDetails);
		TripDetail newDetails = new TripDetail();
		newDetails.setLatitude("41.428917");
		newDetails.setLongitude("-93.726278");
		newDetails.setHeading("0");
		newDetails.setPositionalQual(null);
		newDetails.setTime("2");
		detailList.add(newDetails);
		myTrip.setTripDetails(detailList);
		return myTrip;
		
	}
	
	public List<TripDetail> getDetailsList(String batchNum){
		Trip myTrip = getTrip(batchNum);
		List<TripDetail> details = myTrip.getTripDetails();
		return details;
	}
	
	public String getVinNumber(String batchNum){
		Trip myTrip = getTrip(batchNum);
		return myTrip.getVin();
	}
}
*/