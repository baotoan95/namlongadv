var geocoder = new google.maps.Geocoder();
var lat = -34.397;
var lng = 50.644;

var apiGeolocationSuccess = function(position) {
	lat = position.coords.latitude;
	lng = position.coords.longitude;

	updateGeo();
};

var tryAPIGeolocation = function() {
	jQuery
			.post(
					"https://www.googleapis.com/geolocation/v1/geolocate?key=AIzaSyDCa1LUe1vOczX1hO_iGYgyo8p_jYuGOPU",
					function(success) {
						apiGeolocationSuccess({
							coords : {
								latitude : success.location.lat,
								longitude : success.location.lng
							}
						});
					}).fail(function(err) {
				alert("API Geolocation error! \n\n" + err);
			});
};

var browserGeolocationSuccess = function(position) {
	lat = position.coords.latitude;
	lng = position.coords.longitude;

	updateGeo();
};

var browserGeolocationFail = function(error) {
	switch (error.code) {
	case error.TIMEOUT:
		alert("Browser geolocation error !\n\nTimeout.");
		break;
	case error.PERMISSION_DENIED:
		if (error.message.indexOf("Only secure origins are allowed") == 0) {
			tryAPIGeolocation();
		}
		break;
	case error.POSITION_UNAVAILABLE:
		alert("Browser geolocation error !\n\nPosition unavailable.");
		break;
	}
};

var tryGeolocation = function() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(browserGeolocationSuccess,
				browserGeolocationFail, {
					maximumAge : 50000,
					timeout : 20000,
					enableHighAccuracy : true
				});
	}
};

function geocodePosition(pos) {
	geocoder.geocode({
		latLng : pos
	}, function(responses) {
		if (responses && responses.length > 0) {
			updateMarkerAddress(responses[0].formatted_address);
		} else {
			updateMarkerAddress('Cannot determine address at this location.');
		}
	});
}

function updateGeo() {
	var latLng = new google.maps.LatLng(lat, lng);
	var map = new google.maps.Map(document.getElementById('mapCanvas'), {
		zoom : 8,
		center : latLng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	});
	var marker = new google.maps.Marker({
		position : latLng,
		title : 'Point A',
		map : map,
		draggable : true
	});

	// Update current position info.
	updateMarkerPosition(latLng);
	geocodePosition(latLng);

	// Add dragging event listeners.
	google.maps.event.addListener(marker, 'dragstart', function() {
		updateMarkerAddress('Dragging...');
	});

	google.maps.event.addListener(marker, 'drag', function() {
		updateMarkerStatus('Dragging...');
		updateMarkerPosition(marker.getPosition());
	});

	google.maps.event.addListener(marker, 'dragend', function() {
		updateMarkerStatus('Drag ended');
		geocodePosition(marker.getPosition());
	});
}

function updateMarkerStatus(str) {
	document.getElementById('markerStatus').innerHTML = str;
}

function updateMarkerPosition(latLng) {
	document.getElementById('info').innerHTML = [ latLng.lat(), latLng.lng() ]
			.join(', ');
	$('input[id=coordinates]').val([ latLng.lat(), latLng.lng() ].join(', '));

}

function updateMarkerAddress(str) {
	document.getElementById('address').innerHTML = str;
	$('input[id=location]').val(str);
}

function initialize() {
	var coord = $('#coordinates').val();
	if (coord.trim().length > 0) {
		var location = coord.split(",");
		lat = location[0].trim();
		lng = location[1].trim();
		updateGeo();
	} else {
		tryGeolocation();
	}
}

// Onload handler to fire off the app.
google.maps.event.addDomListener(window, 'load', initialize);

// Init date fields
var date = new Date();
$('input[type=date]')
		.val(
				date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
						+ date.getDate());
