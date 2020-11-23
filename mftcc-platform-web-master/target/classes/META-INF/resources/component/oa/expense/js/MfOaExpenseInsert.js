;
var OaAExpense = function(window, $) {
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
		_bindInsertAjax("#OaAExpenseInsert");
	};
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	}; 
	var _bindInsertAjax = function(obj){
		$(".insertAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {		
						ajaxData : dataParam,
						query:"query"
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							if("query"=="perCenter"){
								window.top.alert(data.msg, 3);
								myclose_click();
								
							}else{
								window.top.alert(data.msg, 3);
								var url=webPath+"/mfOaDebtexpense/getListPage";
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
								myclose();
							}
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}
		});
	}
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
		
	};
}(window, jQuery);