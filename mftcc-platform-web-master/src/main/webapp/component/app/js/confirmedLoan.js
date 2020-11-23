;
var confirmedLoan = function(window, $) {
	var _init = function () {

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
		_bindInsertAjax("#confirmedLoan");
		
	};

	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	};

	
	var _bindInsertAjax = function(obj){
		$(".insertAjax").bind("click",function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray());
					
					LoadingAnimate.start();
					$.ajax({
						url : url,
						data : {
							ajaxData : dataParam,
							appId : confirmedLoan.appId
						},
						type : 'post',
						dataType : 'json',
						async:false,
						success : function(data) {
							LoadingAnimate.stop();
							if (data.flag == "success") {
								window.top.alert(data.msg,3);
								top.flag = true;
								myclose_click();
							} else {
								window.top.alert(data.msg, 0);
							}
						},
						error : function() {
							LoadingAnimate.stop();
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
				});
			}				
		});
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
	};
}(window, jQuery);