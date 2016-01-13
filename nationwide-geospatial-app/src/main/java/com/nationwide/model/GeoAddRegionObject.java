package com.nationwide.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class GeoAddRegionObject {
	
	String regions;
	CustomRegion customRegion;
	RegularRegion regularRegion;
	
	public GeoAddRegionObject(){
		// default constructor
	}

	public String getRegions() {
		return regions;
	}

	public void setRegions(String regions) {
		this.regions = regions;
	}

	public CustomRegion getCustomRegion() {
		return customRegion;
	}

	public void setCustomRegion(CustomRegion customRegion) {
		this.customRegion = customRegion;
	}

	public RegularRegion getRegularRegion() {
		return regularRegion;
	}

	public void setRegularRegion(RegularRegion regularRegion) {
		this.regularRegion = regularRegion;
	}
	
	public static void main(String[] args){
		/*
		 * {
		 * "regions" : [
		 * {"region_type" : "regular", 
		 * "name" : "Kiosk 3", 
		 * "notifyOnExit" : "false", 
		 * "center_latitude" : "36.229531", 
		 * "center_longitude" : "-115.277874", 
		 * "number_of_sides" : "10", 
		 * "distance_to_vertices" : "150"}
		 * ]}
		 */
		String regions = "regions";
		List<RegularRegion> regularRegion = new ArrayList<RegularRegion>();
		regularRegion.add(new RegularRegion("regular","newRegion",true, 41.464309, -93.722284, 10, 50));
		Map<String,List<RegularRegion>> newRegion = new HashMap<String,List<RegularRegion>>();
		newRegion.put(regions, regularRegion);
		Gson gson = new Gson();
		System.out.println(gson.toJson(newRegion));
		
	}
}
