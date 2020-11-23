var MfCusSurveySocialCredit_Insert = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//bindInvestigationPeople();
	};

	return {
		init : _init
	};
}(window, jQuery);