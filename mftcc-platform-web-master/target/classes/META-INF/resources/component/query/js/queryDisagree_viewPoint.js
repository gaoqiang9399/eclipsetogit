var queryDisagree_viewPoint = function(window, $) {
	var _init = function() {
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});

		if ($("input[name=term]").length > 0) {
			$("input[name=term]").bind("change", function() {
				var term = $("input[name=term]").val();
				if (parseFloat(term) < parseFloat(minTerm) || parseFloat(term) > parseFloat(maxTerm)) {
					$("input[name=term]").val(appTerm);
					alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
						"info" : "产品设置",
						"field" : "融资期限",
						"value1" : minTime,
						"value2" : maxTime
					}), 0);
				}
			});
		}

		if ($("input[name=nextOpNo]").length > 0) {
			$("input[name=nextOpNo]").popupSelection({
				searchOn : true,// 启用搜索
				inline : false,// 弹窗模式
				multiple : false,// 单选
				labelShow : false,// 已选项显示
				items : ajaxData.userJsonArray
			});
		}

		if ($("input[name=vouType]").is(':visible')) {
			$("input[name=vouType]").popupSelection({
				searchOn : true,// 启用搜索
				inline : false,// 下拉模式
				multiple : true,// 多选
				items : ajaxData.map,
				title : "担保方式",
				handle : false
			});
			$("input[name=vouType]").parent().children(".pops-value").unbind("click");
			$("input[name=vouType]").parent().children(".pops-value").children().children(".pops-close").remove();
		}

		bindVouTypeByKindNo($("input[name=vouType]"), '');
		var idIndex = $("#busfee-div").find("thead th[name=id]").index();
		$("#busfee-div").find("thead th[name=id]").hide();
		// 意见类型新版选择组件
		$('select[name=opinionType]').popupSelection({
			inline : true, // 下拉模式
			multiple : false, // 单选
			changeCallback : WkfApprove.opinionTypeChange
		});

		var rateScaleIndex = $("#busfee-div").find("thead th[name=rateScale]").index();
		$("#busfee-div").find("tbody tr").each(function(index) {
			$thisTr = $(this);
			$thisTr.find("td").eq(idIndex).hide();
			var $rateScaleTd = $thisTr.find("td").eq(rateScaleIndex);
			var rateScale = $rateScaleTd.html().trim();
			$rateScaleTd.html('<input value="' + rateScale + '">');
		});

		// 是否隐藏 复利利率上浮字段
		if (cmpdRateType == "0") {// 隐藏
			$('input[name=cmpdFloat]').parent('.input-group').hide();
			$('input[name=cmpdFloat]').parents('.tdvalue').prev('td').find('label').hide();
			$('input[name=cmpdFloat]').removeAttr("mustinput");
		}
	};

	// 审批提交
	var _doSubmit = function(obj) {
		var opinionType = $("input[name=opinionType]").val();// 下拉框换成选择组件后，直接从input中取值
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			// 审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
			commitProcess(webPath+"/mfQueryDisagree/submitUpdateAjax?appNo="+appId+"&appId=" + appId + "&opinionType=" + opinionType, obj, 'applySP');
		}
	};

	// 审批页面
	var _getApprovaPage = function() {
		$("#infoDiv").css("display", "none");
		$("#approvalBtn").css("display", "none");
		$("#approvalDiv").css("display", "block");
		$("#submitBtn").css("display", "block");
	};

	// 返回详情页面
	var _approvalBack = function() {
		$("#infoDiv").css("display", "block");
		$("#approvalBtn").css("display", "block");
		$("#approvalDiv").css("display", "none");
		$("#submitBtn").css("display", "none");
	};

	return {
		init : _init,
		doSubmit : _doSubmit,
		getApprovaPage : _getApprovaPage,
		approvalBack : _approvalBack
	};

}(window, jQuery);
