package com.nationwide.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class MqttPublisher {

	public static void main(String[] args) {

		try {
			//a. Create an instance of MQTT client
			MqttClient client = new MqttClient(MQTTPublisherConstants.TCPADDRESS,
												MQTTPublisherConstants.CLIENTID);
			//b. Prepare connection options
			//Use default connection options in this sample.

			//c. Connect to server with the connection options
			client.connect();
			
			//d. Publish message to topics
			MqttTopic topic = client.getTopic(MQTTPublisherConstants.TOPICGPS);
			MqttMessage message = new MqttMessage(
					MQTTPublisherConstants.GPS_PAYLOAD.getBytes());
			
			message.setQos(MQTTPublisherConstants.QOS0);
			
			System.out.println("Waiting for up to " + MQTTPublisherConstants.SLEEPTIMEOUT / 1000 + 
					" seconds for publication of \"" + message.toString() + "\" with QoS = " +
					message.getQos());
			System.out.println("On topic \"" + topic.getName() + "\" for client instance: \""+ 
					client.getClientId() + "\" on address " + client.getServerURI() + "\"");
			MqttDeliveryToken token = topic.publish(message);
			token.waitForCompletion(MQTTPublisherConstants.SLEEPTIMEOUT);
			
			System.out.println("Delivery token \"" + token.hashCode() + 
					"\" has been received:" + token.isComplete());
			
			//e. Disconnect to server
			client.disconnect();
			
		} // end try
		catch (Exception e) {
				e.printStackTrace();
		}
	} // end main
}
