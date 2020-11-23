;
var MfBusExtensionInvestigation_Input=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$(".control-label").find("font").hide();
		$("input[name=manageOpName]").removeAttr("onclick");
	};
	//发起展期申请
	var _saveInvestigationReportAjax=function(formObj){

		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
					"nodeNo":nodeNo,
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.extenFlag=true;
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	}
	return{
		saveInvestigationReportAjax:_saveInvestigationReportAjax,
		init:_init
	};
}(window, jQuery);