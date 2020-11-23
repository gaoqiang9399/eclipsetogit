var MfCreditQueryApp_Detail = function(window, $){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	
	return{
		init:_init,
	};
}(window, jQuery); 