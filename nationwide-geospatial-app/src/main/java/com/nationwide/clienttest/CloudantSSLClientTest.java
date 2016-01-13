package com.nationwide.clienttest;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import com.nationwide.client.CustomSSLContext;

public class CloudantSSLClientTest {
	
	private static Client client;
	
	public static void main(String[] args){
		//getRequest("20647");
		System.out.println("print something");
		getRequest("20647");
	}
	
	public static void getRequest(String batchNum){
		
		try {
			client = CustomSSLContext.initClient();
		} catch (KeyManagementException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
		WebTarget target = client.target("http://cloudant-rest-example.mybluemix.net/webapi/");
		String response = target.path("tripdata/" + batchNum)
				.request().get(String.class);
		System.out.println(response);
	}

}
