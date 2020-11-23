;
var OaNoticeInsert = function(window, $) {
	var ue;
	var _init = function () {
		_getUeditor();
		$("textarea[name=noticeContent]").parents("tr").hide();
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
		_bindInsertAjax("#OaNoticeInsert");
		_bindSaveAjax("#OaNoticeInsert");
		var frameObj=$("#bigFormShowiframe", window.parent.document);
		var tmpHeight=$(frameObj).height();
		$(document).height(tmpHeight);
		
		
	};
	
	var _getNoticeScorpDialog = function(noticeScorpInfo){
		console.log(noticeScorpInfo);
		$("input[name=noticeScorp]").val(noticeScorpInfo.brNo);
		$("input[name=noticeScorpName]").val(noticeScorpInfo.brName);
	};
	
	var _getUeditor = function () {
		/*var oriVal = $("#OaNoticeInsert textarea[name='noticeContent']").val();*/
		ue = UM.getEditor('noticeContent', {
			width : "100%"
	    });
	};
	
	
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	}; 
	
	var _bindInsertAjax = function(obj){
		$(".insertAjax").bind("click", function(event){
			var buttonType= "1";
			_insertAjax(obj,buttonType);
		});
	};
	
	var _insertAjax = function(obj,buttonType){
		$("input[name=noticeSts]").val(buttonType);
		var noticeContentVal = ue.getContent();
		var noticeContentText = ue.getContentTxt();
		if(noticeContentText.length==0){
			alert(top.getMessage("NOT_FORM_EMPTY", "公告内容"),0);
			return;
		}else if(noticeContentText.length<100)
			{
			$("input[name=contentAbstract]").val(noticeContentText);
		}else{
			$("input[name=contentAbstract]").val(noticeContentText.substring(0,90)+"......");
		}
		$("textarea[name=noticeContent]").val(noticeContentVal);
		var ddd = $("textarea[name=noticeContent]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			dataParam = window.encodeURI(dataParam);
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
						window.top.alert(data.msg, 1);
						 top.addFlag = true;
						 myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}	
	}
	
	var _bindSaveAjax = function(obj){
		$(".saveAjax").bind("click", function(event){
			var buttonType = "0";
			_insertAjax(obj,buttonType);
		});
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getNoticeScorpDialog : _getNoticeScorpDialog	
	};
}(window, jQuery);