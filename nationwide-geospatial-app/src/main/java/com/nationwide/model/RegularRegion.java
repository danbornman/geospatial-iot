package com.nationwide.model;

public class RegularRegion {

	
	private String region_type;
	private String name;
	private boolean notifyOnExit;
	private Double center_latitude;
	private Double center_longitude;
	private int number_of_sides;
	private int distance_to_vertices;
	
	public RegularRegion() {
		// default constructor
	}
	
	public RegularRegion(String region_type, String name, boolean notifyOnExit, Double center_latitude, Double center_longitude, 
															int number_of_sides, int distance_to_vertices){
		this.region_type = region_type;
		this.name = name;
		this.center_latitude = center_latitude;
		this.center_longitude = center_longitude;
		this.notifyOnExit = notifyOnExit;
		this.number_of_sides = number_of_sides;
		this.distance_to_vertices = distance_to_vertices;
	}

	public String getRegion_type() {
		return region_type;
	}

	public void setRegion_type(String region_type) {
		this.region_type = region_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNotifyOnExit() {
		return notifyOnExit;
	}

	public void setNotifyOnExit(boolean notifyOnExit) {
		this.notifyOnExit = notifyOnExit;
	}

	public Double getCenter_latitude() {
		return center_latitude;
	}

	public void setCenter_latitude(Double center_latitude) {
		this.center_latitude = center_latitude;
	}

	public Double getCenter_longitude() {
		return center_longitude;
	}

	public void setCenter_longitude(Double center_longitude) {
		this.center_longitude = center_longitude;
	}

	public int getNumber_of_sides() {
		return number_of_sides;
	}

	public void setNumber_of_sides(int number_of_sides) {
		this.number_of_sides = number_of_sides;
	}

	public int getDistance_to_vertices() {
		return distance_to_vertices;
	}

	public void setDistance_to_vertices(int distance_to_vertices) {
		this.distance_to_vertices = distance_to_vertices;
	}
	
	
}
