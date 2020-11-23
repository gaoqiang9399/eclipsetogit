;
var MfCusGroup_Detail = function (window,$){
	var _init = function(){
		$(".scroll-content").mCustomScrollbar({//滚动条的生成
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
	};
	
	return {
		init:_init,
	};
}(window,jQuery);
