	var print = function(){
		document.getElementById("PDFCtrl").ShowDialog(4);
	};
	var switchFullScreen = function(){
		document.getElementById("PDFCtrl").FullScreen = !document
		.getElementById("PDFCtrl").FullScreen;
	};
	var setPageReal = function(){
		document.getElementById("PDFCtrl").SetPageFit(1);
	};
	var setPageFit = function(){
		document.getElementById("PDFCtrl").SetPageFit(2);
	};
	var setPageWidth = function(){
		document.getElementById("PDFCtrl").SetPageFit(3);
	};
	var zoomIn = function(){
		document.getElementById("PDFCtrl").ZoomIn();
	};
	var zoomOut = function(){
		document.getElementById("PDFCtrl").ZoomOut();
	};
	var firstPage = function(){
		document.getElementById("PDFCtrl").GoToFirstPage();
	};
	var previousPage = function(){
		document.getElementById("PDFCtrl").GoToPreviousPage();
	};
	var nextPage = function(){
		document.getElementById("PDFCtrl").GoToNextPage();
	};
	
	var lastPage = function () {
		document.getElementById("PDFCtrl").GoToLastPage();
	};
	var rotateRight = function () {
		document.getElementById("PDFCtrl").RotateRight();
	};
	var rotateLeft = function () {
		document.getElementById("PDFCtrl").RotateLeft();
	};
	