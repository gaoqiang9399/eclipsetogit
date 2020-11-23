;
var WkfAssetViewApprove=function(window,$){
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
		$("input[name=classId]").prev().unbind("click");
		$("input[name=classId]").prev().attr("style","cursor:not-allowed;");
		$("input[name=cusNo]").prev().attr("style","cursor:not-allowed;");
		$("#MfAssetProtectRecordForm").find("input[name=pactId]").parents(".input-group").find(".pops-select").remove();
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").val(cusNo);
		$("#pledgeBaseInfoInsert").find("input[name=cusName]").val(cusName);
		$("#pledgeBaseInfoInsert").find("input[name=cusNo]").parents(".input-group").find(".pops-value").html(cusName);
		$("#pledgeBaseInfoInsert").find("font[color=#FF0000]").remove();
		$("#pledgeBaseInfoInsert").find("input[datatype=6]").attr("onclick","");
		$("#pledgeBaseInfoInsert").find("input[datatype=6]").parent().find("i").remove();
	};
	//进入审批页面
	var _getApprovaPage=function(){
		$("#infoDiv").css("display","none"); 
	 	$("#approvalBtn").css("display","none"); 
	 	$("#approvalDiv").css("display","block"); 
	 	$("#submitBtn").css("display","block"); 
	};
	//返回详情页面
	var _approvalBack=function(){
		$("#infoDiv").css("display","block"); 
	 	$("#approvalBtn").css("display","block"); 
	 	$("#approvalDiv").css("display","none"); 
	 	$("#submitBtn").css("display","none");
	};
	//提交审批意见
	var _doSubmit=function(obj){
		var opinionType = $("select[name=opinionType]").val();
		var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			commitProcess(webPath+"/mfAssetProtectRecord/submitUpdateAjax?opinionType="+opinionType+"&examHisId="+protectId,obj,'assetSP');
		}
	};
	return{
		init:_init,
		doSubmit:_doSubmit,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage,
	};
}(window,jQuery);