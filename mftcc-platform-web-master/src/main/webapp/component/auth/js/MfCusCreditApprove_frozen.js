var MfCusCreditApprove_frozen = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$('select[name=opinionType]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:WkfApprove.opinionTypeChange
		});
	};
	 //审批页面
	var _getApprovaPage = function(){
	 	$("#infoDiv").css("display","none");
	 	$("#approvalBtn").css("display","none");
	 	$("#approvalDiv").css("display","block");
	 	$("#submitBtn").css("display","block"); 
	 }
	 //返回详情页面
	 var _approvalBack = function(){
	 	$("#infoDiv").css("display","block"); 
	 	$("#approvalBtn").css("display","block"); 
	 	$("#approvalDiv").css("display","none"); 
	 	$("#submitBtn").css("display","none");
	 }
	
	 //审批提交
	var _doSubmit = function(obj){
		//var opinionType = $("input[name=approveResult]").val();
		var approvalOpinion  = $("textarea[name=approveRemark]").val();
		var id = $("input[name=id]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var opinionType = $("[name=opinionType]").val();
			commitProcess(webPath+"/mfCreditIntentionApply/submitFrozenApproveAjax?appNo="+operaId+"&opinionType="+opinionType+"&operaId="+operaId,obj,'creditSP');
		}
	};
	return{
		init:_init,
		doSubmit:_doSubmit,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage,
	};
}(window,jQuery);