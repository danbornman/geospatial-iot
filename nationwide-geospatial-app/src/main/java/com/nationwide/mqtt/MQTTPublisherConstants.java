package com.nationwide.mqtt;

public final class MQTTPublisherConstants {

	public static final String TCPADDRESS = "tcp://52.4.65.48:1883";
	public static final String CLIENTID = "gpspublisher";
	public static final int SLEEPTIMEOUT = 10000;
	public static final int QOS0 = 0;
	public static final boolean RETAINED = false;
	public static final String TOPICGPS = "location/gps/coordinates";
	public static final String GPS_PAYLOAD = "{lat:41.428917,lon:-93.726277}";
	
}
