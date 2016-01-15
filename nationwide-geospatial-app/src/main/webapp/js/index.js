
$(document).ready(function(){
	
	// base url for all rest calls
	//var rootURL = "http://nationwide-geospatial-app.mybluemix.net/webapi";
	
	jQuery(function () {
        $('#tabs a:first').tab('show');
    });
	
	// reset html forms on page load
	function resetForms(){
		document.forms['statusInfoForm'].reset();
	}
	resetForms();
	
	// initialize the map displayed on the main page
	var myLatlng = new google.maps.LatLng(39.9685973,-83.0021949);
	var myOptions = {
		zoom: 15,
	    center: myLatlng,
	    mapTypeId: google.maps.MapTypeId.ROADMAP
	}
	var map = new google.maps.Map(document.getElementById("map-canvas"), myOptions);
	
	// function to draw polygons at each defined region in the bluemix geospatial service
	function drawRegions(){
		getCoordsFromRegions(function(data) {
			var status = $.parseJSON(data);
			var numRegions = status.custom_regions.length;
			
			console.log("number of defined regions: " + numRegions);
			if (numRegions > 0){
				var customRegions = status.custom_regions;
				$.each(customRegions, function(index, item) {
					var thisId = this.id;
					var polygonObj = this.polygon;
					var str = JSON.stringify(polygonObj);
					str = str.replace(/"latitude":/g, '"lat":');
					str = str.replace(/"longitude":/g, '"lng":');
					jsonData = JSON.parse(str);
					
					var areaPolygon = new google.maps.Polygon({
						paths: jsonData,
						strokeColor: '#013569',
					    strokeOpacity: 0.8,
					    strokeWeight: 1,
					    fillColor: '#0080FF',
					    fillOpacity: 0.4 
					});
					areaPolygon.setMap(map);
				});	
			}		
			
		});
	}
	drawRegions();
	// ajax call to status used by drawRegions()
	function getCoordsFromRegions(callback) {
		$.ajax({
			url: rootURL + '/geostatus',
			type: 'GET',
			dataType: 'text',
			success: function(data){
				callback(data);
			}
		});
	}

	// manage page components that should be hidden at start up
	$('#btnMap').hide();
	$('#geo-output').hide();
	$('#region-panel').hide();
	$('#region-output').hide();
	$('#geo-panel').hide();
	
	/*
	 * This section includes button click actions
	 */
	$('#btnSearch').click(function(){
		tripDataRequest($('#batchTripId').val());
		return false;
	});
	
	
	$('#btnMap').click(function(){
		tripDetailsRequest($('#batchTripId').val());
		
		client.connect(options);
		return false;
	});
	
	
	$('#btnStatus').click(function(){
		$('#geo-panel').hide();
		$('#display-panel').show();
		getGeoServiceStatus();
		return false;
	});
	
	$('#btnRemove').click(function(){
		removeGeoRegion();
		return false;
	});
	
	$('#btnStart').click(function(){
		startGeoService();
		return false;
	});
	
	$('#btnStop').click(function(){
		stopGeoService();
		return false;
	});
	
	$('#btnRestart').click(function(){
		restartGeoService();
		return false;
	});

	$('#btnAdd').click(function(){
		showAdd();
		selectionMap();
		return false;
	});

	function showAdd(){
		$('#display-panel').hide();
		$('#geo-panel').show();
	}
	
/*	
	registered device
	id: auto
	userName: use-auth-token
	password: T2mDbnQ+cOT&WLi3px
	
	// my account
	API Key: 	a-hl3q6t-csof6mpfy7
	Auth Token:	X0bAwfQXcTD5CzVxBc
	
	// nationwide account
	API Key: 	a-efaqo1-4ml4vkwnpn
	Auth Token:	3becQNzI3jApFGfu1G
*/	
	// create a mqtt client object. using the paho client library.
	//var client = new Messaging.Client("efaqo1.messaging.internetofthings.ibmcloud.com",8883,"a:efaqo1:autoSim1");
	var client = new Messaging.Client(hostOptions.host,8883,hostOptions.deviceID);
	
	client.onConnectionLost = function (responseObject) {
	    //alert("connection lost: " + responseObject.errorMessage);
		console.log("connection lost: " + responseObject.errorMessage);
	};
	// print recieved subscribed message to the console
	var messageCount = 0;
	client.onMessageArrived = function (message) {
		messageCount++;
	    //$('#events-output').append('<span>' + message.payloadString + '</span><br/>');
		var eventString = $.parseJSON(message.payloadString);
		var eventType = eventString["eventType"];
		var regionId = eventString["regionId"];
		var deviceInfo = eventString["deviceInfo"];
		var id = deviceInfo["id"];
		var lat = deviceInfo["location"].latitude;
		var lng = deviceInfo["location"].longitude;
		
	    //console.log(message.destinationName + ": " + message.payloadString);
	    console.log("event:"+eventType+" region:"+regionId+" id:"+id+" lat:"+lat+" lng:"+lng);
	    if (messageCount == 1){
	    	$('#eventTable').append('<tr><th>EventType</th><th>RegionName</th><th>TripID</th><th>Latitude</th><th>Longitude</th></tr>');
	    	$('#eventTable').append('<tr><td>'+eventType+'</td><td>'+regionId+'</td><td>'+id+'</td><td>'+lat+'</td><td>'+lng+'</td></tr>');
	    } else {
	    	$('#eventTable').append('<tr><td>'+eventType+'</td><td>'+regionId+'</td><td>'+id+'</td><td>'+lat+'</td><td>'+lng+'</td></tr>');
	    }

	};
	// options passed to the call to connect (used in button click event)
	var options = {
	    timeout: 30,
	    //userName: "a-efaqo1-4ml4vkwnpn",
	    //password: "3becQNzI3jApFGfu1G",
	    userName: iotOptions.iotKey,
	    password: iotOptions.authToken,
	    useSSL: true,
	    keepAliveInterval: 120,
	    onSuccess: function () {
	        //alert("Connected");
	    	console.log("connected");
	    },
	    onFailure: function (message) {
	    	//alert("Connection failed: " + message.errorMessage);
	    	console.log("connection failed");
	    }	
	};
	// define publish function
	var publish = function (payload, topic, qos) {
	    var message = new Messaging.Message(payload);
	    message.destinationName = topic;
	    message.qos = qos;
	    client.send(message);
	}	
	
	/*
	 * Start the Geospatial Service
	 */

	function startGeoService(){
		$.ajax({
			url: rootURL + '/geostart',
			type: 'GET',
			dataType: 'text',
			success: function(data){
				console.log('start response:', data);
				$('#geo-panel').hide();
				$('#region-output').hide();
				$('#display-panel').show();
				$('#region-panel').empty()
				.html("<div class=\"alert alert-success\" role=\"alert\">Service started successfully</div>");
			},
			error: function (jqXHR, status, err) {
				$('#geo-panel').hide();
				$('#region-output').hide();
				$('#display-panel').show();
				$('#region-panel').empty()
				.html("<div class=\"alert alert-danger\" role=\"alert\">Unable to start service</div>");
				console.log(status, err);
			}
		});
	}
	
	/*
	 * Stop the Geospatial Service
	 */
	function stopGeoService(){
		$.ajax({
			url: rootURL + '/geostop',
			type: 'GET',
			dataType: 'text',
			success: function(data){
				console.log('stop response:', data);
				$('#geo-panel').hide();
				$('#region-output').hide();
				$('#display-panel').show();
				$('#region-panel').empty()
				.html("<div class=\"alert alert-success\" role=\"alert\">Service stopped successfully</div>");
			},
			error: function (jqXHR, status, err) {
				$('#geo-panel').hide();
				$('#region-output').hide();
				$('#display-panel').show();
				$('#region-panel').empty()
				.html("<div class=\"alert alert-danger\" role=\"alert\">Unable to stop service</div>");
				console.log(status, err);
			}
		});
	}
	
	/*
	 * Restart the Geospatial Service
	 */
	function restartGeoService(){
		$.ajax({
			url: rootURL + '/georestart',
			type: 'GET',
			dataType: 'text',
			success: function(data){
				console.log('stop response:', data);
				$('#geo-panel').hide();
				$('#region-output').hide();
				$('#display-panel').show();
				$('#region-panel').empty()
				.html("<div class=\"alert alert-success\" role=\"alert\">Service restarted successfully</div>");
			},
			error: function (jqXHR, status, err) {
				$('#geo-panel').hide();
				$('#region-output').hide();
				$('#display-panel').show();
				$('#region-panel').empty()
				.html("<div class=\"alert alert-danger\" role=\"alert\">Unable to restart service</div>");
				console.log(status, err);
			}
		});
	}
	
	/*
	 * Get the Status of the Geospatial Service - run status and regions defined
	 * Report the status to the page
	 */
	function getGeoServiceStatus(){
		$.ajax({
			url: rootURL + '/geostatus',
			type: 'GET',
			dataType: 'text',
			success: function(data){
				//console.log('status response:', data);
				$('#geo-panel').hide();
				$('#display-panel').show();
				$('#region-panel').show();
				$('#region-output').show();
				parseStatus(data);
			},
		});
	}
	
	// called by status call. parses json data coming back.
	function parseStatus(statusObject){
		var jsonStatus = $.parseJSON(statusObject);
		console.log(statusObject);
		// parse status code
		var statusCode = jsonStatus.status_code;	
		switch(statusCode){
			case 0: document.getElementById("statusMessage").value = "Never Started"
			break;
			case 1: document.getElementById("statusMessage").value = "Starting"
			break;
			case 2: document.getElementById("statusMessage").value = "Running"
			break;
			case 3: document.getElementById("statusMessage").value = "Stopped"
			break;
			case 11: document.getElementById("statusMessage").value = "Failed at run time"
			break;
			case 12: document.getElementById("statusMessage").value = "Failed at code generation"
			break;
		}
		var numCustom = jsonStatus.custom_regions.length;
		document.getElementById("numberAllRegions").value = numCustom;
		$('#region-output').empty();
		
		if (numCustom > 0){			
			// get all custom_regions as a json object
			var customRegions = jsonStatus.custom_regions;
			$.each(customRegions, function(index, item) {
				console.log("ID: " + this.id);
				thisId = this.id;
				$('#region-output').append("<div class='col-md-12'><div class='panel panel-default'><div class='panel-heading'>Monitored Region " + index + "</div><div class='panel-body' id='region-panel-body" + index + "'></div></div></div>");
  				$('#region-panel-body' + index).append("<div><label>Region Name:  </label><p id='region-data" + index + "'</p></div>");
  				$('#region-data' + index).append(thisId);
  				$('#region-panel-body' + index).append("<div><label id='coordinates-label" + index + "'>Region Coordinates:</label></div><p id='coordinates-data" + index + "'></p>");
  				var tableTags = "<table id='coords-table" + index + "'></table>";
  				$('#region-panel-body' + index).append(tableTags);
  				
				var polygonArray = this.polygon;
				$.each(polygonArray, function(idx, coords) {
					$('#coords-table' + index).append("<tr><td>Lat/Long</td><td>" + coords.latitude + "</td><td>" + coords.longitude + "</td></tr>");
				});
			});
		}
	}
	
	
	// Map a trip
	// function called by Map button click
	var jsonDetails = "";
	function tripDetailsRequest(batchId){
		$.ajax({
			url: rootURL + '/tripdetails/' + batchId,
			type: 'GET',
			dataType: 'text',
			success: function(data){
				// parse all the data returned from GET request
				// creates a big ass array of strings in json format - see below for example
				jsonDetails = $.parseJSON(data);
				parseTripDetails(batchId,jsonDetails);
			},
		});
	}
	// once trip details are returned, function is called to...
	// - subscribe to mqtt topic
	// - iterate over trip details data structure - add delay in iteration
	// - build mqtt message and publish to mqtt topic
	// - call buildPath() to draw a polyline on the map
	// - call drawNewMap() to adjust the map focus
	function parseTripDetails(batchId,jsonDetails){

		client.subscribe("iot-2/type/auto/id/01/evt/fence/fmt/json");		
		// iterate array and parse each element to output named key:value pairs
		var dataString = {};
		var payload = {};
		$.each(jsonDetails, function(detailsArray, jsonString) {
			var el = $(this);
			setTimeout(function(){
				var jsonElements = $.parseJSON(jsonString);
				dataString["id"] = batchId;
				dataString["lat"] = Number(jsonElements.latitude);
				dataString["lng"] = Number(jsonElements.longitude);
				// iot-2/type/[device_type]/id/[device_id]/evt/[event_id]/fmt/[format_string]
				publish(JSON.stringify(dataString),'iot-2/type/auto/id/01/evt/geo/fmt/json',0);					
				buildPath(jsonElements.latitude,jsonElements.longitude);
				drawNewMap(jsonElements.latitude,jsonElements.longitude);
			}, detailsArray * 100);	
		});
	}
	var coordinateObject = {};
	var coordPath = [];
	function buildPath(lat,long){
		coordinateObject["lat"] = lat;	
		coordinateObject["long"] = long;
		pathPoints = new google.maps.LatLng(lat,long);
		coordPath.push(pathPoints);
		return coordPath;
	}
	function drawNewMap(lat,long){
		// move map to current coordinates and pan to each lat/long
		var myLatlng = new google.maps.LatLng(lat,long);
		map.panTo(myLatlng);
		var point = new google.maps.LatLng(lat,long);
		var flightPath = new google.maps.Polyline({
		    path: buildPath(lat,long),
		    geodesic: true,
		    strokeColor: '#FF0000',
		    strokeOpacity: 0.2,
		    strokeWeight: 10
		  });
		flightPath.setMap(map);
	}
	
	
	// called at button click for batchId search
	// returns a high level summary of trip data
	function tripDataRequest(batchId){
		$('#spinBox').show().spin();	
		$.ajax({
			url: rootURL + '/trip/' + batchId,
			type: 'GET',
			dataType: "text",
			success: function(data){
				console.log('success', data);
				$('#btnMap').show();
				var jsonArr = $.parseJSON(data);
				$('#tripSummary').empty();
				//$('#events-output').empty();
				$.each(jsonArr, function(key, value) {
					$('#spinBox').hide().spin(false);
					$('#tripSummary').append("<b>" + key + ":</b> " + value + "</br>");
				});
			},
			error: function (jqXHR, status, err) {
				$('#tripSummary').empty()
				.html("<div class=\"alert alert-danger\" role=\"alert\">Oh snap! No trip found.</div>");		
			}
		});
	}
	
	// NOT USED
	function getStatusForPolygons(){
		$.ajax({
			url: rootURL + '/geostatus',
			type: 'GET',
			dataType: 'text',
			success: function(data){
				processStatusData(data);
			},
		});
	}
	
	// called by Region Add button click
	// 
	function selectionMap(){
		var myLatlng = new google.maps.LatLng(39.9685973,-83.0021949);
		var myOptions = {
			zoom: 15,
		    center: myLatlng,
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		}
		var map = new google.maps.Map(document.getElementById("map-select"), myOptions);
		var creator = new PolygonCreator(map);
		
		$('#btnResetSelection').click(function(){
            creator.destroy();
            creator=null;              
            creator=new PolygonCreator(map);  
            $('#map-coords').empty();
            return false;
		});  
		
		$('#btnSaveRegion').click(function() {
            var gpsData = creator.showData();
            createRegion(gpsData);
			return false;
         });	  
	}
	// called by Save button in Add Region
	// call addGeoRegion > makes ajax call to AddRegion to Geospatial Service
	function createRegion(gpsData){
		var re = /-*\d+\.\d+,\s*-*\d+\.\d+/g;
		var array = gpsData.match(re);
		
		var allCoords = [];
		var customRegion = {};
		var newRegions = [];
		var newCustomRegion = {};
	
		$.each(array, function(index, item) {
			var coordinates = item.replace(/\s/g,'');
			var latlng = coordinates.split(",");
			var coordObject = {};
			coordObject["latitude"] = latlng[0];
			coordObject["longitude"] = latlng[1];
			allCoords.push(coordObject);
		});
		
		customRegion["region_type"] = "custom";
		customRegion["name"] = document.getElementById('newRegionName').value;
		customRegion["notifyOnExit"] = document.getElementById('notify-select').value;
		customRegion["polygon"] = allCoords
		
		newRegions.push(customRegion);
		newCustomRegion["regions"] = newRegions
		console.log(JSON.stringify(newCustomRegion));
		
		addGeoRegion(JSON.stringify(newCustomRegion));

	}
	
	// make ajax call to Add a region in the Geospatial Service
	function addGeoRegion(myNewRegion){
		$.ajax({
			url: rootURL + '/georegion',
			type: 'POST',
			dataType: 'text',
			data: myNewRegion,
			contentType: "application/json; charset=utf-8",
			success: function(resp){
				console.log('status response:', resp);	
			},
			error: function (jqXHR, status, err) {
				$('#region-output').hide();
				$('#region-panel').hide();
				$('#geo-output').show();
				$('#geo-output').empty()
				.html("<div class=\"alert alert-danger\" role=\"alert\">Unable to add region</div>");
				console.log(status, err);
			}
		});

	}
	
	// Remove a Region from the Geospatial Service
	function removeGeoRegion(){
		var removeObject = {};
		removeObject["region_type"] = document.getElementById('region-select').value;
		removeObject["region_name"] = document.getElementById('region-name').value;
		var removeString = JSON.stringify(removeObject);
		console.log(removeString);
		
		$.ajax({
			url: rootURL + '/georemove',
			type: 'POST',
			dataType: 'text',
			data: removeString,
			contentType: "application/json; charset=utf-8",
			success: function(resp){
				console.log('status response:', resp);
				$('#geo-panel').hide();
				$('#display-panel').show();
				$('#region-panel').empty()
				.html("<div class=\"alert alert-success\" role=\"alert\">Region removed successfully</div>");
				
			},
			error: function (jqXHR, status, err) {
				$('#geo-panel').hide();
				$('#display-panel').show();
				$('#region-panel').empty()
				.html("<div class=\"alert alert-danger\" role=\"alert\">Unable to remove region</div>");
				console.log(status, err);
			}
		});
	}

});