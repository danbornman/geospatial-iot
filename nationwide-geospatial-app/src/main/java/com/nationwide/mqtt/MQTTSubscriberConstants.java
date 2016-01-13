package com.nationwide.mqtt;

public class MQTTSubscriberConstants {
	public static final String TCPADDRESS = "tcp://52.4.65.48:1883";
	public static final String CLIENTID = "gpssubscriber";
	public static final boolean CLEANSESSION = true;
	public static final int KEEPALIVEINTERVAL = 20;
	public static final int QOS0 = 0;
	public static final String TOPICGPS = "location/gps/coordinates";
	public static final String TOPICLOCATION = "location/gps/#";
}
