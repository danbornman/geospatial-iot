package com.nationwide.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TripDetail {

	private String latitude;
	private String longitude;
	private String heading;
	private String positionalQual;
	private String time;
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getPositionalQual() {
		return positionalQual;
	}
	public void setPositionalQual(String positionalQual) {
		this.positionalQual = positionalQual;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
