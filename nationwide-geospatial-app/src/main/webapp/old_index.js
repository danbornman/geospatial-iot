$(document).ready(function(){

	// automatically start the Geospatial Service
	//geoStartRequest();

	var rootURL = "http://cloudant-rest-example.mybluemix.net/webapi";
	//var rootURL = "http://localhost:8080/cloudant-rest-example/webapi";
	
	$('#btnSearch').click(function(){
		tripDataRequest($('#batchId').val());
		//vinRequest($('#batchId').val());
	
	});
	
	$('#btnStart').click(function(){
		geoStartRequest();
		//vinRequest($('#batchId').val());
	
	});
	
	$('#btnRegion').click(function(){
		geoRegionRequest();
	});
	

	function vinRequest(batchId){
		$.ajax({
			url: rootURL + '/getvin/' + batchId,
			type: 'GET',
			dataType: "text",
			success: function(data){
				$('#output').val(data);
			}
		});
	}
	
	function tripDataRequest(batchId){
		$.ajax({
			url: rootURL + '/tripdata/' + batchId,
			type: 'GET',
			dataType: "text",
			success: function(data){
				//console.log('success', data);
				$('#tripData').val(data);
			}
		});
	}
	
	function geoStartRequest(){
		$.ajax({
			url: rootURL + '/geostart',
			type: 'GET',
			dataType: "text",
			success: function(data){
				console.log('Geospatial Analytics service started successfully', data);
			}
			//	$('#consoleOutput').load("Geospatial Analytics service started successfully");
			//}
		});
	}
	
	function geoRegionRequest(){
		$.ajax({
			url: rootURL + '/getregion',
			type: 'GET',
			dataType: "text",
			success: function(data){
				console.log('Region added successfully', data);
			}
		});
	}
	

});
