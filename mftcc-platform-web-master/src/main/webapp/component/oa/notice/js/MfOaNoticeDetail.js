;
var OaNoticeDetail = function(window, $) {
	var ue;
	
	var _init = function () {	
		$(".container").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		});
		_bindClose();
	};
	
	var _getUeditor = function () {
		$("textarea[name=noticeContent]").parents(".rows").hide();
		ue = UM.getEditor('noticeContent', {
			width : "100%"
	    });
		ue.ready(function() {
			var oriVal = $("#OaNoticeDetail textarea[name='noticeContent']").val();
			ue.setContent(oriVal);
			ue.disable();
		 });
	};
	
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
		myclose();
		});
	}; 
     
	/*var _addContent = function (){
		var noticeContentHtml = $("#OaNoticeDetail textarea[name='noticeContent']").val();
		alert(noticeContentHtml);
		$("#noticeDivContent").append(noticeContentHtml);
	}*/
	var _bindUpdateAjax = function(obj){
		$(".updateAjax").bind("click", function(event){
			var noticeContentVal = ue.getContent();
			$("textarea[name=noticeContent]").val(noticeContentVal);
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery
						.ajax({
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
									//					  alert(top.getMessage("SUCCEED_OPERATION"),1);
									top.addFlag = true;
									if (data.htmlStrFlag == "1") {
										top.htmlStrFlag = true;
										top.htmlString = data.htmlStr;
									}
									$(top.window.document).find(
											"#bigFormShow .close").click();

									if (callback
											&& typeof (callback) == "function") {
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