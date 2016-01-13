package com.nationwide.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StatusOutJson {

	private int status_code;
	private List<CustomRegion> custom_regions;
	private List<RegularRegion> regular_regions;
	
	public StatusOutJson(){
		// default constructor
	}
	
	public int getStatusCode() {
		return status_code;
	}
	public void setStatusCode(int statusCode) {
		this.status_code = statusCode;
	}
	public List<CustomRegion> getCustom_regions() {
		return custom_regions;
	}
	public void setCustom_regions(List<CustomRegion> custom_regions) {
		this.custom_regions = custom_regions;
	}
	public List<RegularRegion> getRegular_regions() {
		return regular_regions;
	}
	public void setRegular_regions(List<RegularRegion> regular_regions) {
		this.regular_regions = regular_regions;
	}
	
/*	@Override
	public String toString(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(StatusOutJson.class);
		return jsonOutput;
	}
*/	
	
}
