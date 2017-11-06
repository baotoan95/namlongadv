// Get context to draw
var canvas;
var context;
// Chart properties
var xLength, yLength, cSpace, cWidth;
var maxDataValue, numLabelsY, numCols, bSpace;

function init(maxValue, data, spaceBtwCols) {
	canvas = document.getElementById('btChart');
	context = canvas.getContext('2d');

	// Make it visually fill the positioned parent
	canvas.style.width = '100%';
	// ...then set the internal size to match
	canvas.width = canvas.offsetWidth;
	canvas.height = canvas.offsetHeight;

	// Set chart properties
	cSpace = 50; // Space between chart and canvas border
	xLength = canvas.width - (cSpace * 2); // Length of x axis
	yLength = canvas.height - (cSpace * 2); // Length of y axis
	maxDataValue = maxValue; // Max of value
	numLabelsY = 10; // Number of segments on y axis
	bSpace = spaceBtwCols; // Space between two chart bar = bSpace * 2

	dataSegments = data;
	numCols = dataSegments.length; // Number of columns

	drawAxisLabelMarkers();
}

function drawAxisLabelMarkers() {
	context.lineWidth = "2.0";
	// Draw Y axis
	drawAxis(cSpace, cSpace, cSpace, yLength + cSpace);
	// Draw X axis
	drawAxis(cSpace, yLength + cSpace, xLength + cSpace, yLength + cSpace);

	context.lineWidth = "1.0";
	drawMarkers();
}

function drawAxis(x1, y1, x2, y2) {
	context.beginPath();
	context.moveTo(x1, y1);
	context.lineTo(x2, y2);
	context.closePath();
	context.stroke();
}

function drawMarkers() {
	// Find number of markers to draw on axis
	var lengthSegment = yLength / numLabelsY; // Length of each segment
	// Draw label for Y axis
	context.textAlign = "right";
	context.fillStyle = "#000";
	for (var i = 0; i <= numLabelsY; i++) {
		var labelValue = Math.round(i * (maxDataValue / numLabelsY));
		context.fillText(labelValue, cSpace - 10, canvas.height - (i * lengthSegment) - cSpace, 50);
		// Draw line for label
		context.lineWidth = "0.1";
		drawAxis(cSpace - 5, canvas.height - (i * lengthSegment) - cSpace, cSpace + xLength, canvas.height - (i * lengthSegment) - cSpace);
	}
	context.lineWidth = "1.0";

	// Draw label for X axis
	lengthSegment = (xLength / numCols);
	context.textAlign = "center";
	var width = lengthSegment - (bSpace * 2);
	for (var i = 0; i < numCols; i++) {
		// Process d
		var process = dataSegments[i].split(", ");
		var labelValue = process[0].trim();
		var colarrValue = parseInt(process[1].trim());
		var x = (i * lengthSegment) + cSpace;

		// Draw label
		context.fillText(labelValue, x + ((lengthSegment - bSpace) / 2)
				+ bSpace - 5, (canvas.height - cSpace) + 20, lengthSegment
				- bSpace);
		context.save();

		// Draw chart bar
		var y = canvas.height - ((colarrValue * yLength) / maxDataValue)
				- cSpace;
		var height = canvas.height - y - cSpace;
		drawRectangle(x + bSpace, y, width, height, true);
		context.restore();
	}
}

function drawRectangle(x, y, width, height, fill) {
	context.beginPath();
	context.rect(x, y, width, height);
	context.closePath();
	context.stroke();

	if (fill) {
		var gradient = context.createLinearGradient(0, 0, 0, 300);
		gradient.addColorStop(0, 'blue');
		gradient.addColorStop(1, 'rgba(67,203,36,.15)');
		context.fillStyle = gradient;
		context.strokeStyle = gradient;
		context.fill();
	}
}

var arr = new Array();
arr[0] = "Ngày 1, 5";
arr[1] = "Ngày 2, 20";
arr[2] = "Ngày 3, 18";
arr[3] = "Ngày 4, 16";
arr[4] = "Ngày 5, 17";
arr[5] = "Ngày 6, 15";
arr[6] = "Ngày 7, 20";
arr[7] = "Ngày 8, 4";
arr[8] = "Ngày 1, 5";
arr[9] = "Ngày, 5";
arr[10] = "Ngày 2, 20";
arr[11] = "Ngày 3, 1000";
arr[12] = "Ngày 4, 16";
arr[13] = "Ngày 5, 17";
arr[14] = "Ngày 6, 15";
arr[15] = "Ngày 7, 20";
arr[16] = "Ngày 8, 4";
arr[17] = "Ngày 1, 5";
arr[18] = "Ngày 1, 5";
arr[19] = "Ngày 1, 5";
arr[20] = "Ngày 1, 5";
arr[21] = "Ngày 2, 20";
arr[22] = "Ngày 3, 18";
arr[23] = "Ngày 4, 16";
arr[24] = "Ngày 5, 17";
arr[25] = "Ngày 6, 15";
arr[26] = "Ngày 7, 20";
arr[27] = "Ngày 8, 4";
arr[28] = "Ngày 1, 5";
arr[29] = "Ngày 1, 5";
arr[30] = "Ngày 1, 5";
arr[31] = "Ngày 1, 5";