;
var WkfMoveabledModifyViewPoint=function(window,$){
	var _init=function(){
		_refreshGoodsDetailList(modifyId);
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
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
			commitProcess(webPath+"/mfMoveableModifyApply/submitUpdateAjax?opinionType"+opinionType+"&modifyId="+modifyId
			+"&appId="+appId,obj,'moveTransSP');
		}
	};
	var _refreshGoodsDetailList=function(modifyId){
		jQuery.ajax({
			url : webPath+"/mfMoveableModifyApply/getModifyTableDataByPledgeNoAjax",
			type : "POST",
			dataType : "json",
			data:{tableId:"tablepledgebaseinfobill0007",modifyId:modifyId},
			async:false,
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#goodsDetailList").html(data.tableData);
					$("#goodsDetailListdiv").show();
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	return{
		init:_init,
		doSubmit:_doSubmit,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage
	};
}(window,jQuery);