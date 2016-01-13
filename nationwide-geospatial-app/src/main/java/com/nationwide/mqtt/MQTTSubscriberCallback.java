package com.nationwide.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTTSubscriberCallback implements MqttCallback{

	private String instanceData = "";

	public MQTTSubscriberCallback(String instance) {
		instanceData = instance;
	}

	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost on instance \"" + instanceData + "\" with cause" +
				"\"" + cause.getMessage() + "\" Reason code " +
				((MqttException)cause).getReasonCode() + "\" Cause \"" +
				((MqttException)cause).getCause() + "\"");
		cause.printStackTrace();
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		try {
			System.out.println("Delivery token \"" + token.hashCode() + 
					"\" received by instance \" " + instanceData + "\" ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		try {
			System.out.println("Message arrived: \"" + message.toString() + 
					"\" on topic \"" + topic.toString() + "\" for instance \"" +
					instanceData + "\"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
