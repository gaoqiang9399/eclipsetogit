;
var MfCusAssurePayment_Insert = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({//滚动条的生成
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};

	 _myclose = function (){//关闭当前弹窗的方法
		 myclose_click();//关闭弹窗
		 window.location.href = webPath+"/mfCusAssurePayment/getListPage";//重新刷新列表
		 };
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};


	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		myclose : _myclose,
		ajaxSave:_ajaxSave,
		
	};
}(window, jQuery);
