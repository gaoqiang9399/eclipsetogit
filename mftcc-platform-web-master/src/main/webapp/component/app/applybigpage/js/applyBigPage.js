;
var applyBigPage = function(window, $) {
	/** 新增页面初始化 */
	var _init = function() {
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});

		// 证件类型选择组件
		$("select[name=idType]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false,// 单选
			changeCallback : function(elem) {
				$("[name=popsidType]").trigger("change");
			}
		});

		// 由于初始时不确定是何种类型
		var idType = $("[name=idType]").val();
		var $idNum = $("[name=idType]").parents("table").find("input[name=idNum]")[0];
		if (idType == "0") {// 身份证样式格式
			// 如果是身份证，添加校验
			changeValidateType($idNum, "idnum");
		} else {
			changeValidateType($idNum, "");
		}
		// 同一个客户申请第二笔数据时，配偶的性别会变化 getBirthdayAndSex();

		// 政治面貌选择组件
		$("select[name=ifParty]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false
		});

		// 民族选择组件
		$("select[name=nationality]").popupSelection({
			searchOn : true,// 启用搜索
			inline : false,// 弹框模式
			multiple : false,// 单选
			valueClass : "show-text",// 自定义显示值样式
			labelShow : false
		});

		// 个人健康状况选择组件
		$("select[name=healthStat]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false
		});

		// 婚姻状况选择组件
		$("select[name=marrige]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false
		});

		// 最高学历选择组件
		$("select[name=education]").popupSelection({
			searchOn : true,// 启用搜索
			inline : true,// 下拉模式
			multiple : false
		});

		// 客户所属区域选择组件
		if ($("input[name=careaProvice]").length > 0 || $(".alertDilog input[name='careaCounty']").length > 0) {
			setAreaDataToDom(areaData);
		}
	};

	/** 新增业务按钮 */
	var _applyInsertByBigPage = function(cusNo) {
		window.location.href = "ApplyBigPageAction_inputPage.action?cusNo=" + cusNo;
	};

	/** 详情按钮 */
	var _detailPage = function(obj, url) {
		var cusNo;
		var parmStr = url.split("?")[1];
		var parms = parmStr.split("&");
		for ( var idx in parms) {
			if (parms[idx].split("=")[0] == "cusNo") {
				cusNo = parms[idx].split("=")[1];
			}
		}
		jQuery.ajax({
			url : "ApplyBigPageActionAjax_getStateAjax.action?cusNo=" + cusNo,
			data : {},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.state == "2") {// 已提交
					mfBusApplyList.getDetailPage(obj, url);
					event.stopPropagation();
				} else {
					window.location.href = "ApplyBigPageAction_inputPage.action?cusNo=" + cusNo;
				}
			}
		});
	};

	/** 保存 */
	var _insert = function(cusNo) {
		var dataParam = _getFormData();// 获取序列化后的表单数据

		jQuery.ajax({
			url : "ApplyBigPageActionAjax_insertAjax.action?cusNo=" + cusNo,
			data : {
				ajaxData : dataParam
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.flag == "success") {
					alert(top.getMessage("SUCCEED_OPERATION"), 1);
				} else if (data.flag == "error") {
					if (data.flag !== undefined && data.flag != null && data.flag != "") {
						alert(data.msg, 0);
					} else {
						alert(top.getMessage("FAILED_OPERATION", " "), 0);
					}
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	};

	/** 提交 */
	var _submit = function(cusNo) {
		// 校验表单
		var flag = true;
		$("#forms_div").find("form").each(function(index, element) {
			flag = flag && submitJsMethod(element, '');
		});

		var dataParam = _getFormData();// 获取序列化后的表单数据

		if (flag) {
			jQuery.ajax({
				url : "ApplyBigPageActionAjax_submitAjax.action?cusNo=" + cusNo,
				data : {
					ajaxData : dataParam
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.flag == "success") {
						alert(top.getMessage("SUCCEED_OPERATION"), 1);
						if (data.sysFlag != "" && data.sysFlag == "trench") {
							window.location.href = "MfBusApplyAction_getTrenchApplySummary.action?busEntrance=1&appId=" + data.appId;
						} else {
							window.location.href = "MfBusApplyAction_getSummary.action?busEntrance=1&appId=" + data.appId;
						}
					} else if (data.flag == "error") {
						if (data.flag !== undefined && data.flag != null && data.flag != "") {
							alert(data.msg, 0);
						} else {
							alert(top.getMessage("FAILED_OPERATION", " "), 0);
						}
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
		}
	};

	/** 取消 */
	var _cancelApply = function(cusNo) {
		window.location.href = webPath + "/mfCusCustomer/getById?cusNo=" + cusNo;
	};

	/** 封装数据 */
	var _getFormData = function() {
		var formsData = new Object();
		$("#forms_div").find("form").each(function(index, element) {
			var $form = $(element);
			var bpcId = $form.find("#bpcId").val();
			var array = formsData[bpcId];
			if (!array) {
				array = new Array();
			}
			array.push(_formSerialize(element));
			formsData[bpcId] = array;
		});
		return JSON.stringify(formsData);
	};

	/** 解析form数据 */
	var _formSerialize = function(form) {
		var formDataArray = new Array();
		$(form).find(":text,:input:hidden").each(function(index, element) {
			var formData = new Object();
			formData["name"] = element.name;
			formData["value"] = element.value;
			formData["datatype"] = $(element).attr("datatype");
			formDataArray.push(formData);
		});

		$(form).find(":radio:checked").each(function(index, element) {
			var formData = new Object();
			formData["name"] = element.name;
			formData["value"] = element.value;
			formData["datatype"] = $(element).attr("datatype");
			formDataArray.push(formData);
		});

		$(form).find("select").each(function(index, element) {
			var formData = new Object();
			formData["name"] = element.name;
			formData["value"] = element.value;
			formData["datatype"] = $(element).attr("datatype");
			formDataArray.push(formData);
		});

		$(form).find("textarea").each(function(index, element) {
			var formData = new Object();
			formData["name"] = element.name;
			formData["value"] = element.value;
			formData["datatype"] = $(element).attr("datatype");
			formDataArray.push(formData);
		});

		return formDataArray;
	};

	/**
	 * 添加多条记录<br>
	 * 1.允许有多条记录的数据<br>
	 * 2.不允许有多条且非必须录入数据，删除后又重新添加<br>
	 */
	var _addFormButOnclick = function(obj, bpcId, tableName, canMore, funDescribe) {
		if (canMore != '1' && $("#forms_div").find("#formDiv_" + bpcId).size() > 0) {
			alert("只允许添加一个" + funDescribe, 0);
			return false;
		}

		var $formDiv = $("#forms_div_base").find("#formDiv_" + bpcId).clone();
		$("#" + bpcId).prepend($formDiv);

		if (tableName == "pledge_base_info") {
			pledge_base_info.collClassInit();
			pledge_base_info.formInit($formDiv.find("form"));
		} else if (tableName == "mf_cus_person_job") {
			// 阳光金控，公司类型锚点控制
			$formDiv.find("[name='corpKind']").initAchor();
		} else if (tableName == "mf_cus_bank_acc_manage") {
			mf_cus_bank_acc_manage.formInit($formDiv.find("form"));
		} else if (tableName == "mf_cus_family_info") {
			mf_cus_family_info.formInit($formDiv.find("form"));
		}
	};

	/**
	 * 删除记录<br>
	 * 1.必须录入数据需要保留一个<br>
	 */
	var _delFormButOnclick = function(obj, bpcId, mustInput, funDescribe) {
		if (mustInput == '1' && $("#forms_div").find("#formDiv_" + bpcId).size() == 1) {
			alert("必须录入" + funDescribe, 0);
			return false;
		}
		$(obj).parents("div[id^='formDiv_']").remove();
	};

	/** 获取页面共同借款人数据 */
	var _getCusRelationData = function() {
		var data = new Array();

		$("#forms_div").find("form[name='form_mf_cus_family_info']").each(function(index, element) {
			var idNum = $(this).find("[name='idNum']").val();// 证件号
			var relName = $(this).find("[name='relName']").val();// 姓名

			if (idNum && relName) {// 证件号、姓名都不为空
				var obj = new Object();
				obj["id"] = idNum;
				obj["name"] = relName;

				data.push(obj);
			}
		});

		return data;
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		applyInsertByBigPage : _applyInsertByBigPage,
		insert : _insert,
		submit : _submit,
		cancelApply : _cancelApply,
		detailPage : _detailPage,
		addFormButOnclick : _addFormButOnclick,
		delFormButOnclick : _delFormButOnclick,
		getCusRelationData : _getCusRelationData
	};
}(window, jQuery);

// ---------- 兼容已有基础表单，下面未做js封包 ----------

// 证件类型变化是修改证件号码验证规则
function idTypeChange(obj) {
	// 证件号码格式验证
	var idType = $(obj).val();
	var $idNum = $(obj).parents("table").find("input[name=idNum]")[0];
	if (idType == "0") {// 身份证样式格式
		// 如果是身份证，添加校验
		changeValidateType($idNum, "idnum");
	} else {
		changeValidateType($idNum, "");
	}
}

function setAreaDataToDom() {
	$("input[name=careaProvice]").popupSelection({
		// ajaxUrl : "NmdAreaActionAjax_getAreaListAllAjax.action",
		items : areaData,
		searchOn : true,// 启用搜索
		multiple : false,// 单选
		valueClass : "show-text",// 自定义显示值样式
		ztree : true,
		// ztreeSetting : setting,
		title : "客户所属地区",
		handle : BASE.getIconInTd($("input[name=careaProvice]")),
		changeCallback : function(elem) {
			var areaNo = elem.data("values").val();
			var node = elem.data("treeNode");
			var parNode = node.getParentNode();
			var address = node.name;
			while (parNode) {
				address = parNode.name + address;
				parNode = parNode.getParentNode();
			}
			BASE.removePlaceholder($("input[name=regHomeAddre]"));
			$("input[name=regHomeAddre]").val(address);
			$("input[name=careaCity]").val(address);
			var $careaProviceObj = $("input[name=careaProvice]").parent(".input-group").find(".pops-label-alt");
			$careaProviceObj.removeClass("pops-label-alt");
			$careaProviceObj.html(address);
		}
	});

	// 住宅地址区域-阳光金控添加
	$(".alertDilog input[name='careaCounty']").popupSelection({
		// ajaxUrl : "NmdAreaActionAjax_getAreaListAllAjax.action",
		items : areaData,
		searchOn : true,// 启用搜索
		multiple : false,// 单选
		valueClass : "show-text",// 自定义显示值样式
		ztree : true,
		// ztreeSetting : setting,
		title : "住宅地址",
		handle : BASE.getIconInTd($("input[name=careaCounty]")),
		changeCallback : function(elem) {
			var areaNo = elem.data("values").val();
			var node = elem.data("treeNode");
			var parNode = node.getParentNode();
			var address = node.name;
			while (parNode) {
				address = parNode.name + address;
				parNode = parNode.getParentNode();
			}
			BASE.removePlaceholder($("input[name=careaCounty]"));
			$("input[name=commAddress]").val(address);
			var $careaCountyObj = $("input[name=careaCounty]").parent(".input-group").find(".pops-label-alt");
			$careaCountyObj.removeClass("pops-label-alt");
			$careaCountyObj.html(address);
		}
	});
}

function func_using_IDcard_to_set_sex(obj) {
	StringUtil.setBirthyAndSexByID(obj, "sex", null, "ext1");
	var sex = $(obj).parents("form").find("input[name=sex]").val();
	$(obj).parents("form").find("select[name=popssex]").popupSelection("selectedById", sex);
}

// 在账户名项添加onChange事件 修改账户名 清空关联的账户信息
function accountNameChange_reset(obj) {
	$(obj).parents("form").find("input[name='idNum']").val("");
	$(obj).parents("form").find("input[name='reservedPhone']").val("");
	$(obj).parents("form").find("input[name='accountNo']").val("");
}

function getBankByCardNumber(obj) {
	var identifyNumber = obj.value.trim().replace(/\s/g, "");
	$.ajax({
		url : "BankIdentifyActionAjax_getByIdAjax.action",
		data : {
			identifyNumber : identifyNumber
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			if (data.flag == "success") {
				BASE.removePlaceholder($("input[name=bankNumbei]"));
				BASE.removePlaceholder($("input[name=bank]"));
				$(obj).parents("form").find("input[name=bankNumbei]").val(data.bankId);
				$(obj).parents("form").find("input[name=bank]").val(data.bankName);

				$(obj).parents("form").find("[name='bankNumbei']").parent().find(".error").remove();
			} else {
				$(obj).parents("form").find("input[name=bankNumbei]").val("");
				$(obj).parents("form").find("input[name=bank]").val("");
			}
		}
	});
}

// 验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
function checkByKindInfo(obj) {
	var name = $(obj).attr("name");
	var title = $(obj).attr("title").split("(")[0];

	var minAmt = $(obj).parents("form").find("input[name=minAmt]").val();
	var maxAmt = $(obj).parents("form").find("input[name=maxAmt]").val();

	// 申请金额
	if (name == "appAmt") {
		if (maxAmt != null && minAmt != null && maxAmt != "" && minAmt != "") {
			var maxAmtNum =  Number(maxAmt);
			var minAmtNum =  Number(minAmt);
			var appAmt = $(obj).val();
			appAmt = appAmt.replace(/,/g, "");
			if (parseFloat(appAmt) < parseFloat(minAmtNum) || parseFloat(appAmt) > parseFloat(maxAmtNum)) {// 判断申请金额是否在产品设置范围内
				$(obj).val(null);
				alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
					"info" : "产品设置",
					"field" : title,
					"value1" : fmoney(minAmtNum, 2),
					"value2" : fmoney(maxAmtNum, 2)
				}), 0);
			} else {
				if (creditAmt != null && creditAmt != "") { // 判断申请金额是否在授信余额内
					creditNum = new Number(creditAmt);
					if (parseFloat(appAmt) > parseFloat(creditNum)) {
						$(obj).val(null);
						alert(top.getMessage("NOT_APPLY_VALUE_BIG", {
							"info" : "该客户授信",
							"field" : title,
							"value" : fmoney(creditNum, 2)
						}), 0);
					} else {
						if (kindCreditAmt != null && kindCreditAmt != "") { // 判断申请金额是否在产品授信余额内
							kindCreditNum = new Number(kindCreditAmt);
							if (parseFloat(appAmt) > parseFloat(kindCreditNum)) {
								$(obj).val(null);
								alert(top.getMessage("NOT_APPLY_VALUE_BIG", {
									"info" : "该客户产品授信",
									"field" : title,
									"value" : fmoney(kindCreditNum, 2)
								}), 0);
							}
						}
					}
				}
			}
		}
	} else if (name == "fincRate") {// 检测融资利率
		if (minFincRate != null && maxFincRate != null && minFincRate != "" && maxFincRate != "") {
			var maxFincRateNum = new Number(maxFincRate);
			var minFincRateNum = new Number(minFincRate);
			var fincRate = $(obj).val();
			if (parseFloat(fincRate) < parseFloat(minFincRateNum) || parseFloat(fincRate) > parseFloat(maxFincRateNum)) {// 判断申请金额是否在产品设置范围内
				$(obj).val(null);
				alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
					"info" : "产品设置",
					"field" : title,
					"value1" : minFincRateNum,
					"value2" : maxFincRateNum
				}), 0);
			}
		}
	}
}