package com.nationwide.mqtt;

public class GeoServiceStartMqtt {

	private String mqtt_uid;
	private String mqtt_pw;
	private String mqtt_uri;
	private String mqtt_input_topics;
	private String mqtt_client_id_input;
	private String mqtt_notify_topic;
	private String mqtt_client_id_notify;
	private String device_id_attr_name;
	private String latitude_attr_name;
	private String longitude_attr_name;
	
	public GeoServiceStartMqtt() {
		// default constructor
	}
	
	public GeoServiceStartMqtt(String mqtt_uid, String mqtt_pw, String mqtt_uri, 
			String mqtt_input_topics, String mqtt_client_id_input, String mqtt_notify_topic, String mqtt_client_id_notify, String device_id_attr_name, 
			String latitude_attr_name, String longitude_attr_name) {
		this.mqtt_uid = mqtt_uid;
		this.mqtt_pw = mqtt_pw;
		this.mqtt_uri = mqtt_uri;
		this.mqtt_input_topics = mqtt_input_topics;
		this.mqtt_client_id_input = mqtt_client_id_input;
		this.mqtt_notify_topic = mqtt_notify_topic;
		this.mqtt_client_id_notify = mqtt_client_id_notify;
		this.device_id_attr_name = device_id_attr_name;
		this.latitude_attr_name = latitude_attr_name;
		this.longitude_attr_name = longitude_attr_name;
	}

	public String getMqtt_client_id_notify() {
		return mqtt_client_id_notify;
	}

	public void setMqtt_client_id_notify(String mqtt_client_id_notify) {
		this.mqtt_client_id_notify = mqtt_client_id_notify;
	}

	public String getMqtt_client_id_input() {
		return mqtt_client_id_input;
	}

	public void setMqtt_client_id_input(String mqtt_client_id_input) {
		this.mqtt_client_id_input = mqtt_client_id_input;
	}

	public String getMqtt_uid() {
		return mqtt_uid;
	}

	public void setMqtt_uid(String mqtt_uid) {
		this.mqtt_uid = mqtt_uid;
	}

	public String getMqtt_pw() {
		return mqtt_pw;
	}

	public void setMqtt_pw(String mqtt_pw) {
		this.mqtt_pw = mqtt_pw;
	}

	public String getMqtt_uri() {
		return mqtt_uri;
	}

	public void setMqtt_uri(String mqtt_uri) {
		this.mqtt_uri = mqtt_uri;
	}

	public String getMqtt_input_topics() {
		return mqtt_input_topics;
	}

	public void setMqtt_input_topics(String mqtt_input_topics) {
		this.mqtt_input_topics = mqtt_input_topics;
	}

	public String getMqtt_notify_topic() {
		return mqtt_notify_topic;
	}

	public void setMqtt_notify_topic(String mqtt_notify_topic) {
		this.mqtt_notify_topic = mqtt_notify_topic;
	}

	public String getDevice_id_attr_name() {
		return device_id_attr_name;
	}

	public void setDevice_id_attr_name(String device_id_attr_name) {
		this.device_id_attr_name = device_id_attr_name;
	}

	public String getLatitude_attr_name() {
		return latitude_attr_name;
	}

	public void setLatitude_attr_name(String latitude_attr_name) {
		this.latitude_attr_name = latitude_attr_name;
	}

	public String getLongitude_attr_name() {
		return longitude_attr_name;
	}

	public void setLongitude_attr_name(String longitude_attr_name) {
		this.longitude_attr_name = longitude_attr_name;
	}
	
	
}
