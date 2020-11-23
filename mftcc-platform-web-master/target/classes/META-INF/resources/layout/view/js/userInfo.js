var userInfo=function(window, $){
	var _init=function(){$(function(){
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	});};
	return{
		init:_init,
	};
}(window, jQuery);
