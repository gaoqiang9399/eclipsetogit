;
var MfBusCompensatoryApprove = function (window,$){
	var _init = function(){
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		//意见类型新版选择组件
		$('select[name=approveResult]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:WkfApprove.opinionTypeChange
		});
	};
	var _doSubmitAjax = function(formObj){
		var datas = [];
		$.each($("#mfBusCompensatoryApplyList").find("tbody tr"),function(index) {
			var entity = {};
			$thisTr = $(this);
			entity.termNum = $thisTr.find("input[name=termNum]").val();
			entity.planId = $thisTr.find("input[name=planId]").val();
			entity.fincId = $thisTr.find("input[name=fincId]").val();
			entity.compensatoryPrcp = $thisTr.find(".compensatoryPrcp").html().replace(/,/g, "");
			entity.compensatoryIntst = $thisTr.find(".compensatoryIntst").html().replace(/,/g, "");
			entity.compensatoryOverIntst = $thisTr.find(".compensatoryOverIntst").html().replace(/,/g, "");
			entity.compensatoryPenalty = $thisTr.find(".compensatoryPenalty").html().replace(/,/g, "");
			entity.compensatoryFee =  $thisTr.find("input[name=compensatoryFee]").val();
			entity.compensatoryFeeSum = $thisTr.find(".compensatoryFeeSum").html().replace(/,/g, "");
			datas.push(entity);
		});
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var approveResult = $("input[name=approveResult]").val();//下拉框换成选择组件后，直接从input中取值
			//没有选择审批意见默认同意
			if(approveResult == undefined || approveResult == ''){
				approveResult = "1";
			}
			//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
			commitProcess(webPath+"/mfBusCompensatoryApprove/submitUpdateAjax?appNo="+id+"&opinionType="
					+approveResult+"&ajaxData="+ escape(JSON.stringify(datas)),formObj,'dimission');
		}
	};
	//返回详情页面
	var _approvalBack=function(){
		$("#infoDiv").css("display","block"); 
	 	$("#approvalBtn").css("display","block"); 
	 	$("#approvalDiv").css("display","none"); 
	 	$("#submitBtn").css("display","none");
	};
	//进入审批页面
	var _getApprovaPage=function(){
		$("#infoDiv").css("display","none"); 
	 	$("#approvalBtn").css("display","none"); 
	 	$("#approvalDiv").css("display","block");
	 	$("#submitBtn").css("display","block"); 
	};
	
	return {
		init:_init,
		doSubmitAjax:_doSubmitAjax,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage
	};
}(window,jQuery);