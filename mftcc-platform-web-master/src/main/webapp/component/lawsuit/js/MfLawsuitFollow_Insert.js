;
var MfLawsuitFollow_Insert = function(window, $) {
	var _init = function () {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		// 关闭按钮
		$(".cancel").bind("click", function(event) {
			$(top.window.document).find("#showDialog").remove();
		});
	}
	
	var _ajaxSave = function(obj){
			//var dataParam = JSON.stringify($(obj).serializeArray());
			var url = $(obj).attr("action");
			$("input[name='caseId']").val(caseId);
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {ajaxData : dataParam},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
						alert(data.msg,1);
						//myclose_click();
						myclose_showDialogClick();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});	
	};
		var _followTypeChange = function(){
			var followType = $("select[name='followType']").val();
			var url=webPath+"/mfLawsuitFollow/input?followType="+followType+"&caseId="+caseId;
	       	document.location.href=url;
			
		};
	
	/**
	 * 选择日期
	 */
	var _myselectrili = function(obj){
		var name = $(obj).prev().attr("name");
		selectrili(obj,name);
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave:_ajaxSave,
		myselectrili:_myselectrili,
		followTypeChange:_followTypeChange
	
	};
}(window, jQuery);