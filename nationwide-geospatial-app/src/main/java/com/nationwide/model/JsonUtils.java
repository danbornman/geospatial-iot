package com.nationwide.model;

import java.util.List;

import com.google.gson.Gson;
import com.nationwide.mqtt.GeoServiceStartMqtt;

public class JsonUtils {

	public static String getJson(Trip t){
		Gson gson = new Gson();
		String json = gson.toJson(t);
		return json;
	}
	public static Trip getTripFromJson(String json){
		Gson gson = new Gson();
		Trip t = gson.fromJson(json, Trip.class);
		return t;
	}
	
	public static String getJson(TripDetail td){
		Gson gson = new Gson();
		String json = gson.toJson(td);
		return json;
	}
	public static TripDetail getTripDetail(String json){
		Gson gson = new Gson();
		TripDetail td = gson.fromJson(json, TripDetail.class);
		return td;
	}
	
	public static StatusOutJson getStatusOutFromJson (String json){
		Gson gson = new Gson();
		StatusOutJson s = gson.fromJson(json, StatusOutJson.class);
		return s;
	}
	
	public static String getJson(StatusOutJson s){
		Gson gson = new Gson();
		String json = gson.toJson(s);
		return json;
	}
	
	public static GeoServiceStartMqtt getStartMqtt (String json){
		Gson gson = new Gson();
		GeoServiceStartMqtt mq = gson.fromJson(json, GeoServiceStartMqtt.class);
		return mq;
	}
	
	public static String getJson(GeoServiceStartMqtt mq){
		Gson gson = new Gson();
		String json = gson.toJson(mq);
		return json;
	}
	
	public static CustomRegion getCustomRegionFromJson (String json){
		Gson gson = new Gson();
		CustomRegion customRegion = gson.fromJson(json, CustomRegion.class);
		return customRegion;
	}
	
	public static String getJson(CustomRegion customRegions){
		Gson gson = new Gson();
		String json = gson.toJson(customRegions);
		return json;
	}
	
}
