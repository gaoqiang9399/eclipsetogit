;
var OaConsCheck = function(window, $) {
	var _init = function() {
		
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
		_bindUpdateAjax("#OaConsUpdate");
	};

	var _bindClose = function() {
		$(".cancel").bind("click", function(event) {
			myclose();
		});
	};

	var _bindUpdateAjax = function(obj) {
		$(".updateAjax").bind("click",function(event) {
			var flag = submitJsMethod($(obj).get(0), '');
			var operateNum = $("input[name='operateNum']").val();
			if(operateNum < 0){
				flag = false;
				alert("盘点库存输入错误");
			}
			$("input[name='consNo']").val($("input[name='consId']").val());
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj)
						.serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						formId : "conscheck0001"
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							var url = webPath+"/mfOaCons/getListPage";
							window.top.alert(data.msg,1);
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
							myclose();
							
						} else {
							window.top.alert(data.msg,0);
						}
					},
					error : function() {
						loadingAnimateClose();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
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