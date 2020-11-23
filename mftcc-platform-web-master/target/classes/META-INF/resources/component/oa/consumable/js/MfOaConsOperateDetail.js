;
var OaConsOperateDetail = function(window, $) {
	
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
		var operateState = $("input[name='operateState']").val();
		var insertButton = $(".insertAjax");
		if(operateState == "未办理"){
			insertButton.val("办理");
		}
		if(operateState == "已借出"){
			insertButton.val("归还");
		}
		if(operateState == "已归还"){
			insertButton.remove();
		}
		if(operateState == "已归还" || operateState == "已办理" || operateState== "已通过" || operateState== "未通过"){
			insertButton.remove();
		}
		_bindUpdateAjax("#OaConsOperateDetail");
	};
	var _bindUpdateAjax = function(obj) {
		var url = webPath+"/mfOaConsOperate/updateAjax";
				$(".insertAjax").bind("click",function(event) {
					var operateNum = parseInt($("input[name='operateNum']").val());
					var dataParam = JSON.stringify($(obj)
									.serializeArray());
							LoadingAnimate.start();
					$.ajax({
						url : url,
						data : {
							ajaxData : dataParam
						},
						type : 'post',
						dataType : 'json',
						success : function(data) {
							LoadingAnimate.stop();
							if (data.flag == "success") {
								var url = webPath+"/mfOaConsOperate/getListPage";
								window.top.alert(data.msg,1);
								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
								myclose();
							} else {
								window.top.alert(data.msg,0);
							}
						},
						error : function() {
							loadingAnimateClose();
							alert(top.getMessage("ERROR_REQUEST_URL", url));
						}
					});
			});
	};
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	}; 
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);