;
var MfTuningReport_Insert = function(window, $) {

	var _init = function() {
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};

	var _closeMyOnly = function() {
		// $(top.window.document).find(".dhccModalDialog").eq(1).remove();
		myclose_click();
	};
	var _insertTuning = function(obj) {
		var url = $(obj).attr("action");
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			alert(top.getMessage("CONFIRM_OPERATION", "提交下一步"), 2, function() {
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam
					},
					type : 'post',
					dataType : 'json',
					async : false,
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							alert(data.msg, 3);
							top.tuningReport = true;
							top.appSts = data.appSts;
							top.applyInfo = data.applyInfo;
							top.applyDetail = data.applyDetail;
							top.flag = true;
							_closeMyOnly();
						} else {
							alert(top.getMessage("FAILED_SAVE"), 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_SAVE"), 0);
					}
				});
			});
		}
	};
	var _selectMode = function() {
		var reportType = $("select[name=reportModeType]").val();
		if (reportType == '1') {
			$("textarea[name=summary]").parent().parent().parent().show();
			$("textarea[name=opinion]").parent().parent().parent().show();
			$("input[name=docTemplate]").parent().parent().parent().remove();
		}
		if (reportType == '2') {
			var htmlStr = "<tr><td class=\"tdlable right\" colspan=\"1\" rowspan=\"1\">"
					+ "<label class=\"control-label \">尽职调查模板</label></td><td class=\"tdvalue  right\" colspan=\"1\" rowspan=\"1\">"
					+ "<div class=\"input-group\"><input type=\"text\" title=\"尽职调查模板\" name=\"docTemplate\" "
					+ "datatype=\"0\" mustinput=\"\" class=\"form-control\" onblur=\"func_uior_valTypeImm(this);\" "
					+ "onmousedown=\"enterKey()\" onkeydown=\"enterKey();\"></div></td><td></td><td></td></tr>";
			$("textarea[name=summary]").parent().parent().parent().hide();
			$("textarea[name=opinion]").parent().parent().parent().hide();
			$("textarea[name=opinion]").parent().parent().parent().before(
					htmlStr);
//			var htmlButton = "<button type='button' class='btn btn-primary' onclick='busInfo.printFile();' style='border-radius: 7px;padding: 6px 54px;border-color: #b7c5c8;'>尽职调查报告</button>";
			var htmlButton = "<span class=\"option-div\" style=\"margin-bottom: 2px;\"  onclick='busInfo.printFile();'><span>添加模板</span><i class=\"i i-sanjiaoduihao\"></i></span>";
			$("input[name=docTemplate]").parent().parent().append(htmlButton);
			$("input[name=docTemplate]").hide();
		}

	};
	// 显示文档
	var _printFile = function() {
		var temParm = '&cusNo=' + cusNo + '&appId=' + appId;// 文档书签取值依赖条件，目前支持appId pactId
												// cusNo fincId repayDetailId
		var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId="
				+ templateBizConfigId;
		var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面

		$.ajax({
			url : url + "&" + temParm,
			data : {
				"returnUrl" : backUrl
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
				var obj = window.open("about:blank");

				var form = '<form  method="post" action="' + data.url + '">';

				var poCnt = '<input id="poCnt" name="poCnt" value=\''
						+ data.poCnt + '\'></input>';
				form += poCnt;

				var datatmp = '<input id="datatmp" name="datatmp" value=\''
						+ data.datatmp + '\'></input>';
				form += datatmp;

				form += '</form>';

				obj.document.write(form);
				obj.document.forms[0].submit();
			}
		});
	};

	return {
		init : _init,
		insertTuning : _insertTuning,
		closeMyOnly : _closeMyOnly,
		selectMode : _selectMode,
		printFile : _printFile,
	};
}(window, jQuery);
