	package com.nationwide.mqtt;

import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class MQTTSubscriber {

	public static void main(String[] args) {
		try {
			
			// a. Create an instance of MQTT client
			MqttClient client = new MqttClient(MQTTSubscriberConstants.TCPADDRESS, MQTTSubscriberConstants.CLIENTID);
			
			// b. Prepare connection options
			MQTTSubscriberCallback callback = new MQTTSubscriberCallback(MQTTSubscriberConstants.CLIENTID);
			client.setCallback(callback);
			MqttConnectOptions conOptions = new MqttConnectOptions();
			conOptions.setCleanSession(MQTTSubscriberConstants.CLEANSESSION);
			conOptions.setKeepAliveInterval(MQTTSubscriberConstants.KEEPALIVEINTERVAL);
			
			// c. Connect to broker with the connection options
			client.connect(conOptions);
			System.out.println("Subscribing to topic \"" + MQTTSubscriberConstants.TOPICLOCATION
					+ "\" for client instance \"" + client.getClientId() + "\" using QoS "
					+ MQTTSubscriberConstants.QOS0 + ". Clean session is " + MQTTSubscriberConstants.CLEANSESSION);
			
			// d. Subscribe interested topics.
			client.subscribe(MQTTSubscriberConstants.TOPICLOCATION, MQTTSubscriberConstants.QOS0);
			System.out.format("Subscribing to topic %s\nfor client %s using QoS%d\n\n" + "Press Q<Enter> to quit\n\n",
					MQTTSubscriberConstants.TOPICLOCATION, MQTTSubscriberConstants.CLIENTID,
					MQTTSubscriberConstants.QOS0);
			
			Scanner scanner = new Scanner(System.in);
			for (String input = ""; !input.equalsIgnoreCase("q"); input = scanner.nextLine());
			
			// e. Disconnect to broker
			client.disconnect();
			System.out.println("Subscriber ending");
			scanner.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
