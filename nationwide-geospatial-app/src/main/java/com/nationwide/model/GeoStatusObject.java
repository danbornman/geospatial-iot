package com.nationwide.model;

import java.util.ArrayList;
import java.util.List;

public class GeoStatusObject {

	private int status_code;
	private CustomRegion custom_regions;
	private RegularRegion regular_regions;
	
	// fix these classes to uses boolean
	// arrange to get regions from Status call
	
	public GeoStatusObject(){
		// default constructor
	}
	
	public GeoStatusObject(int status_code, CustomRegion customRegion, RegularRegion regularRegion){
		this.status_code = status_code;
		this.custom_regions = customRegion;
		this.regular_regions = regularRegion;
	}

	public int getStatus_code() {
		return status_code;
	}

	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}

	public CustomRegion getCustom_regions() {
		return custom_regions;
	}

	public void setCustom_regions(CustomRegion custom_regions) {
		this.custom_regions = custom_regions;
	}

	public RegularRegion getRegular_regions() {
		return regular_regions;
	}

	public void setRegular_regions(RegularRegion regular_regions) {
		this.regular_regions = regular_regions;
	}
			
}
