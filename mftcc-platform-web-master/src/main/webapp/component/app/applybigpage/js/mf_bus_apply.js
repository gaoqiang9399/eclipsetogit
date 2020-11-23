;
var mf_bus_apply = function(window, $) {
	var _init = function() {

		// 初始化表单
		_formInit($("#forms_div").find("form[name='form_mf_bus_apply']"));
	};

	// 初始化表单
	var _formInit = function(form) {
		// 初始化产品
		_kindInit($(form).find("[name='kindNo']").val(), form);

		// 产品改变事件
		$(form).find("[name='kindNo']").bind("change", function() {
			_kindInit($(this).val(), form);
		});

		$(form).find("[name='pledgeSeq']").bind("change", function() {// 抵押顺位
			// 押品抵押顺位随申请变动
			$("#forms_div").find("form[name='form_pledge_base_info']").find('select[name=extOstr01]').val($(this).val());
		});

		// 共同借款人
		_coborrNameSelectInit(form);

		//如果当前系统为渠道业务系统,受理城市、渠道字段自动带出，不允许编辑
		if(sysFlag=="trench"){
			$("#form_mf_bus_apply").find("input[name=ext1]").attr("readonly","true");
			$("#form_mf_bus_apply").find("input[name=channelSource]").attr("readonly","true");
		}else{
			//初始化受理城市
			_dealCityInit(form);
			//渠道初始化
			_channelInit(form,"");
		}
	};

	/** 共同借款人选择加载 */
	var _coborrNameSelectInit = function(form) {
		var data = applyBigPage.getCusRelationData();// 获取页面共同借款人数据

		// 创建select
		var $select = $('<select multiple="multiple">');
		for ( var idx in data) {
			var $option = $('<option value="' + data[idx].id + '">' + data[idx].name + '</option>');
			$select.append($option);
		}
		$(form).find("[name='coborrName']").after($select);
		$(form).find("[name='coborrName']").hide();

		// 创建select控件
		$select.fSelect({
			numDisplayed : 5,// 标签值最多显示个数
			showSearch : false,
			changeCallback : function(vals) {// 回调
				var id = [];
				var name = [];
				for ( var idx in vals) {
					id.push(vals[idx].id);
					name.push(vals[idx].name);
				}

				$(form).find("[name='coborrNum']").val(id.join('|'));
				$(form).find("[name='coborrName']").val(name.join('|'));
			}
		});

		// 动态更新select控件选择项
		$(form).find("[name='coborrName']").parent().find(".fs-label-wrap").bind("click", function() {
			var $label = $(this);
			var $wrap = $label.parent(".fs-wrap");
			var $select = $wrap.find("select");
			var $fSelect = $select.data('fSelect');

			var data = applyBigPage.getCusRelationData();// 获取页面共同借款人数据
			$select.empty();
			for ( var idx in data) {
				var $option = $('<option value="' + data[idx].id + '">' + data[idx].name + '</option>');
				$select.append($option);
			}

			var choices = $fSelect.buildOptions($select);
			$wrap.find('.fs-options').empty();
			$wrap.find('.fs-options').html(choices);
		});
	};

	var count = 0;
	//初始化受理城市
	var _dealCityInit = function(form){
		$(form).find("input[name='ext1']").popupSelection({
			ajaxUrl : "NmdAreaActionAjax_getAllCityAjax.action",
			searchOn : true,// 启用搜索
			multiple : false,// 单选
			valueClass : "show-text",// 自定义显示值样式
			ztree : true,
			// ztreeSetting : setting,
			title : "受理城市",
			handle : BASE.getIconInTd($(form).find("input[name='ext1']")),
			changeCallback : function(elem) {
				var areaNo=elem.data("values").val();
				var areaName = elem.data("treeNode").name;
				$(form).find("input[name='ext1']").val(areaName);
				$(form).find("input[name='ext2']").val(areaNo);
				$(form).find('input[name=channelSource]').next(".pops-value").remove();
				_channelInit(form,areaNo);
				//第一次不清理
				if(count > 0){
					$(form).find('input[name=channelSource]').val("");
					$(form).find('input[name=channelSourceNo]').val("");
					$(form).find('input[name=channelSource]').nextAll(".pops-value").html("");
				}
				count ++;
			}
		});
	};
	
	//渠道初始化
	var _channelInit = function(form,businessAreaNo){
		if(businessAreaNo == ""){
			businessAreaNo =  $(form).find('input[name=ext1]').val();
		}
		var formId = $(form).find('input[name=formId]').val();
		//绑定事件的input框
		$(form).find('input[name=channelSource]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"MfUserPermissionActionAjax_getChannelSourceAjax.action?formId="+formId+"&element=channelSource&businessAreaNo="+businessAreaNo,//请求数据URL
//			valueElem:"input[name='channelSourceNo']",//真实值选择器
			valueElem: $(form).find('input[name=channelSourceNo]'),//真实值选择器
			title: '选择渠道商',//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($(form).find("input[name='channelSource']"));
				MfBusTrenchComm.checkTrenchCreditAmtByApply();
			},
			tablehead:{//列表显示列配置
				"trenchUid":"渠道商",
				"trenchName":"渠道商编号"
			},
			returnData:{//返回值配置
				disName:"trenchName",//显示值
				value:"trenchUid"//真实值
			}
		});
	};

	// 初始化产品
	var _kindInit = function(kindNo, form) {
		var dataParam = '{"kindNo":"' + kindNo + '"}';
		jQuery.ajax({
			url : "ApplyBigPageActionAjax_getKindDataAjax.action",
			data : {
				ajaxData : dataParam
			},
			type : "POST",
			dataType : "json",
			async : false,
			success : function(data) {
				var kindObj = JSON.parse(data.sysKind);

				termType = kindObj.termType;// 合同期限 1月 2日
				minTerm = kindObj.minTerm;// 合同期限下限
				maxTerm = kindObj.maxTerm;// 合同期限上限
				checkTerm($(form).find("[name='term']"));

				minAmt = kindObj.minAmt;// 融资金额下限
				maxAmt = kindObj.maxAmt;// 融资金额上限
				minFincRate = kindObj.minFincRate;// 融资默认利率下限
				maxFincRate = kindObj.maxFincRate;// 融资默认利率上限
				rateUnit = data.rateUnit;// 利率单位
				checkByKindInfo($(form).find("[name='appAmt']"));
				checkByKindInfo($(form).find("[name='fincRate']"));
			},
			error : function(data) {
				alert("产品数据加载失败", 0);
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

// ---------- 兼容已有基础表单，下面未做js封包 ----------

// 验证申请期限
var termType;// 合同期限 1月 2日
var minTerm;// 合同期限下限
var maxTerm;// 合同期限上限
function checkTerm(obj) {
	var appTerm = $(obj).val().replace(/,/g, "");
	var appTermType = $(obj).parents("form").find("[name=termType]").val();
	// 申请期限
	if (minTerm && maxTerm && termType) {
		var minTermNum = new Number(minTerm);
		var maxTermNum = new Number(maxTerm);
		var unit = appTermType == "1" ? "个月" : "天";
		if (appTermType == "1") {// 表单填写申请期限为月
			if (termType == "2") {// 产品申请期限为日
				minTermNum = (minTerm / 30).toFixed();
				maxTermNum = (maxTerm / 30).toFixed();
			}
		}
		if (appTermType == "2") {// 表单填写申请期限为日
			if (termType == "1") {// 产品申请期限为月
				minTermNum = (minTerm * 30).toFixed();
				maxTermNum = (maxTerm * 30).toFixed();
			}
		}
		var appMinTerm = minTermNum + unit;
		var appMaxTerm = maxTermNum + unit;

		$(obj).attr("placeholder", appMinTerm + "-" + appMaxTerm);
		if (parseFloat(appTerm) < parseFloat(minTermNum) || parseFloat(appTerm) > parseFloat(maxTermNum)) {
			alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
				"info" : "产品设置",
				"field" : $(obj).attr("title").split("(")[0],
				"value1" : appMinTerm,
				"value2" : appMaxTerm
			}), 0, function() {
				$(obj).val("");
			});
		}
	}
}

// 验证申请金额
var minAmt;// 融资金额下限
var maxAmt;// 融资金额上限
var minFincRate;// 融资默认利率下限
var maxFincRate;// 融资默认利率上限
var rateUnit;// 利率单位
function checkByKindInfo(obj) {
	var name = $(obj).attr("name");

	if (name == "appAmt") {// 申请金额
		if (maxAmt && minAmt) {
			var maxAmtNum = new Number(maxAmt);
			var minAmtNum = new Number(minAmt);

			$(obj).attr("placeholder", fmoney(minAmtNum, 2) + "-" + fmoney(maxAmtNum, 2) + "元");
			var appAmt = $(obj).val().replace(/,/g, "");
			if (parseFloat(appAmt) < parseFloat(minAmtNum) || parseFloat(appAmt) > parseFloat(maxAmtNum)) {// 判断申请金额是否在产品设置范围内
				alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
					"info" : "产品设置",
					"field" : $(obj).attr("title").split("(")[0],
					"value1" : fmoney(minAmtNum, 2),
					"value2" : fmoney(maxAmtNum, 2)
				}), 0, function() {
					$(obj).val("");
				});
			}
			//验证申请金额是否大于渠道授信余额，如果大于给予提醒		
			MfBusTrenchComm.checkTrenchCreditAmtByApply();
		}
	} else if (name == "fincRate") {// 检测融资利率
		if (minFincRate && maxFincRate) {
			var maxFincRateNum = new Number(maxFincRate);
			var minFincRateNum = new Number(minFincRate);

			$(obj).attr("placeholder", fmoney(minFincRateNum, 2) + "-" + fmoney(maxFincRateNum, 2) + rateUnit);
			var fincRate = $(obj).val();
			if (parseFloat(fincRate) < parseFloat(minFincRateNum) || parseFloat(fincRate) > parseFloat(maxFincRateNum)) {// 判断申请金额是否在产品设置范围内
				alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
					"info" : "产品设置",
					"field" : $(obj).attr("title").split("(")[0],
					"value1" : minFincRateNum,
					"value2" : maxFincRateNum
				}), 0, function() {
					$(obj).val("");
				});
			}
		}
	}
}
