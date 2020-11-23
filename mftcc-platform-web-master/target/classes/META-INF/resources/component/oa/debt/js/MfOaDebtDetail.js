;
var OaDebtDetail = function(window, $) {
	var _init = function () {	
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
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
		_bindUpdateAjax("#OaDebtDetail");
	};
	
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	}; 
    
	var _bindUpdateAjax = function(obj){
		$(".updateAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
							url : url,
							data : {
								ajaxData : dataParam
							},
							type : "POST",
							dataType : "json",
							beforeSend : function() {
							},
							success : function(data) {
								LoadingAnimate.stop();
								if (data.flag == "success") {
									window.top.alert(data.msg, 1);
									top.addFlag = true;
									if (data.htmlStrFlag == "1") {
										top.htmlStrFlag = true;
										top.htmlString = data.htmlStr;
									}
									myclose_click();
									if (callback&& typeof (callback) == "function") {
										callback.call(this, data);
									}
								} else if (data.flag == "error") {
									alert(data.msg, 0);
								}
							},
							error : function(data) {
								LoadingAnimate.stop();
								alert(top.getMessage("FAILED_OPERATION"," "), 0);
							}
						});
			}
		});
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
		
	};
}(window, jQuery);