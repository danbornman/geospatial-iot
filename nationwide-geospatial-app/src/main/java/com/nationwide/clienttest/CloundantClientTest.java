package com.nationwide.clienttest;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import com.nationwide.model.TripDetail;

public class CloundantClientTest {

	public static void main(String[] args){
		try {
			
			getRequest("20647");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void getRequest(String batchNum) throws InterruptedException{
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://cloudant-rest-example.mybluemix.net/webapi/");
		
		List<TripDetail> response = target.path("tripdata/" + batchNum)
									.request().get(new GenericType<List<TripDetail>>() {});
		Iterator<TripDetail> iter = response.iterator();
		while(iter.hasNext()){
			TripDetail td = iter.next();
			System.out.println("Lat: " + td.getLatitude() + " Long: " + td.getLongitude());
			Thread.sleep(200);
		}
	}
	

}
