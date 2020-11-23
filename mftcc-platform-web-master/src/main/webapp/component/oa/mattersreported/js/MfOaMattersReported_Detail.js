;
var MfOaMattersReported_Detail=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({//加载滚动条
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