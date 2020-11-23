;
var MfReceivablesDisputedAppDetail = function(w,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
// 				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	var _myclose = function(obj){
		var $this = $(obj);
		$(".dhccModalDialog .i-x5",parent.document).last().click();//多个弹框先选择最后一个弹框关闭
//		$(top.window.document).find(".dhccModalDialog .i-x5").click();
//		myclose_click();//统一关闭表单
	};
	return {
		init:_init,
		myclose:_myclose
	}
}(window,jQuery);