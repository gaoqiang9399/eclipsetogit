;
var MfAssetProtectInsertChange=function(window,$){
	var _init=function(){
		//滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//合同组件
		$("input[name=pactId]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxOverduePactData.overduePact,
		});
		//客户新组件
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:cusData.cusArray,
		});
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").parents(".input-group").find(".pops-select").remove();
		//押品新组件
		$("input[name=classId]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxCollClassData.collClass,
			changeCallback : function (obj, elem) {
				$("input[name=classSecondName]").val(obj.data("text"));
				CollateralCommon.freshPledgeBaseForm("","",entrFlag);
			}
		});
		$("#MfAssetProtectRecordForm").find("input[name=pactId]").parents(".input-group").find(".pops-select").remove();
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").val(cusNo);
		$("#pledgeBaseInfoInsert").find("input[name=cusName]").val(cusName);
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").parents(".input-group").find(".pops-value").html(cusName);
		$("input[name=pledgeName]").after('<span id="selectpledge" class="input-group-addon">'+
		'<i class="i i-fangdajing pointer" onclick="MfAssetProtectInsertChange.selectAssetProtectData();"></i></span>');
	};
	//选择抵债资产
	var _selectAssetProtectData=function(){
		selectBussCollateralDataDialog(_setAssetProtectData,appId,pledgeNoStr);
	};
	//选择客户押品回调设置押品相关字段。
	var _setAssetProtectData=function(data){
		var pledgeNo = data.pledgeNo;
		jQuery.ajax({
			url :webPath+"/mfBusCollateralRel/getAddPledgeBaseHtmlAjax",
			data : {pledgeNo:pledgeNo},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					$("#pledgeBaseInfoInsert").find("table").remove();
					$("#pledgeBaseInfoInsert").find(".hidden-content").remove();
					$("#pledgeBaseInfoInsert").html(data.htmlStr);
					isQuote="1";
					$("input[name=classId]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,//下拉模式
						multiple:false,//多选选
						items:data.collClass,
					});
					//添加押品放大镜
					$("input[name=pledgeName]").after('<span id="selectpledge" class="input-group-addon">'+
					'<i class="i i-fangdajing pointer" onclick="MfAssetProtectRecordInsert.selectAssetProtectData();"></i></span>');
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//保存资产信息
	var _saveAssetProtectAjax=function(){
		var appFlag = submitJsMethod($("#MfAssetProtectRecordForm").get(0), '');
		var pledgeFlag = submitJsMethod($("#pledgeBaseInfoInsert").get(0), '');
		if (pledgeFlag&&appFlag) {
			var url = webPath+"/mfAssetProtectRecord/updateAssetProtectAjax";
			var appDataParam = JSON.stringify($("#MfAssetProtectRecordForm").serializeArray());
			var pledgeDataParam = JSON.stringify($("#pledgeBaseInfoInsert").serializeArray());
			LoadingAnimate.start();
			jQuery.ajax({
				url : url,
				data : {
					ajaxAppData : appDataParam,
					ajaxPledgeData:pledgeDataParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.flag=true;
						alert(top.getMessage("CONFIRM_OPERATION","资产保全审批提交"),2,function(){
							_submitAjax();
						});
					}else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
			});
		}
	};
	//提交到审批
	var _submitAjax=function(){
		jQuery.ajax({
			url : webPath+"/mfAssetProtectRecord/submitAjax",
			data : {
				protectId : protectId,
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				LoadingAnimate.stop();
				if (data.flag == "success") {
					window.top.alert(data.msg,3);
					myclose_click();
				} else if (data.flag == "error") {
					window.top.alert(data.msg,0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	return{
		init:_init,
		saveAssetProtectAjax:_saveAssetProtectAjax,
		selectAssetProtectData:_selectAssetProtectData
	};
}(window,jQuery);