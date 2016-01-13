package com.nationwide.model;

import java.util.List;

public class CustomRegion {

	private String region_type;
	private String name;
	private String id;
	private String notify;
	private boolean notifyOnExit;
	private boolean remove;
	private List<Coordinates> polygon;

	public CustomRegion() {
		// default constructor
	}

	// constructor for adding custom region
	public CustomRegion(String region_type, String id, boolean notifyOnExit, List<Coordinates> polygon) {
		this.region_type = region_type;
		this.id = id;
		this.notifyOnExit = notifyOnExit;
		this.polygon = polygon;
	}

	// constructor for getting status on a custom region
	public CustomRegion(String id, boolean remove, boolean notifyOnExit, List<Coordinates> polygon) {
		this.id = id;
		this.remove = remove;
		this.notifyOnExit = notifyOnExit;
		this.polygon = polygon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public void setNotifyOnExit(boolean notifyOnExit) {
		this.notifyOnExit = notifyOnExit;
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

	public void setName(String i) {
		this.name = i;
	}

	public List<Coordinates> getPolygon() {
		return polygon;
	}

	public void setPolygon(List<Coordinates> polygon) {
		this.polygon = polygon;
	}
	/*
	 * public static void main(String[] args){
	 * 
	 * CustomRegion custom_region = new CustomRegion();
	 * custom_region.setName("2"); custom_region.setNotifyOnExit(false);
	 * 
	 * List<Coordinates> polygons = new ArrayList<Coordinates>();
	 * 
	 * Coordinates point1 = new Coordinates(); point1.setLongitude(new
	 * Double(115.161344)); point1.setLatitude(new Double(36.229714));
	 * polygons.add(point1);
	 * 
	 * Coordinates point2 = new Coordinates(); point2.setLongitude(new
	 * Double(115.16188)); point2.setLatitude(new Double(36.229457));
	 * polygons.add(point2);
	 * 
	 * custom_region.setPolygon(polygons);
	 * 
	 * Gson gson = new GsonBuilder().setPrettyPrinting().create(); String
	 * jsonOutput = gson.toJson(custom_region); System.out.println(jsonOutput);
	 * 
	 * }
	 */
}
