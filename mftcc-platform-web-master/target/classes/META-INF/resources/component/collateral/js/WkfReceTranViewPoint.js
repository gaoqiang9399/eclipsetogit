;
var WkfReceTranViewPoint=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
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
	var _doSubmit=function(obj){
		var opinionType = $("select[name=opinionType]").val();
		var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			commitProcess(webPath+"/mfReceivablesTransferInfo/submitUpdateAjax?opinionType"+opinionType+"&receTranAppId="+receTranAppId
			+"&appNo="+appId,obj,'receTranSP');
		}
	};
	return{
		init:_init,
		doSubmit:_doSubmit,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage
	};
}(window,jQuery);