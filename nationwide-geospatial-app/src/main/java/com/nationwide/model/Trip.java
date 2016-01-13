package com.nationwide.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Trip {

	public String batchNum;
	public String vin;
	public List<TripDetail> tripDetails;
	public String date;
	
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	
	public List<TripDetail> getTripDetails() {
		return tripDetails;
	}
	public void setTripDetails(List<TripDetail> tripDetails) {
		this.tripDetails = tripDetails;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
