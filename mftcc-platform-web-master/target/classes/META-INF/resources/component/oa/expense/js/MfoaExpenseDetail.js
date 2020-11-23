;
var OaExpenseDe = function(window, $) {
	var _init = function (flag) {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_bindClose();
		_closeCallBack();
	};
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	};
	var _closeCallBack = function (){
    	myclose();
   };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);