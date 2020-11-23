;
var templateBiz_printSurveyReportList = function(window, $) {
	// 显示文档
	var _printFile = function(templateBizConfigId) {
		var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;

		$.ajax({
			url : url + "&" + temParm,
			data : {
				"functionPoint" : "jdbg"
			},
			type : 'post',
			dataType : 'json',
			beforeSend : function() {
				LoadingAnimate.start();
			},
			complete : function() {
				LoadingAnimate.stop();
			},
			error : function() {
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			},
			success : function(data) {
				var poCntObj = $.parseJSON(data.poCnt);
				mfPageOffice.openPageOffice(poCntObj);
			}
		});
	};

	return {
		printFile : _printFile
	};
}(window, jQuery);
