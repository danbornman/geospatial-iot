package com.nationwide.model;

public class Coordinates {

	private Double longitude;
	private Double latitude;

	public Coordinates() {
		//default constructor
	}
	
	public Coordinates (Double latitiude, Double longitude){
		this.latitude = latitiude;
		this.longitude = longitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
	
}
