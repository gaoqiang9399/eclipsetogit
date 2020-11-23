;
var WkfMoveabledBuybackViewPoint=function(window,$){
	var _init=function(){
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
		_getPledgeData();
		_refreshGoodsDetailList();
	};
	//初始化选择押品数据源
	var _getPledgeData=function(){
		var busPleId=$("input[name=busPleId]").val();
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=pledgeNo]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:true,//多选
						items:data.items
					});
					var $obj=$("input[name=pledgeNo]").parents("td").eq(0);
					$obj.find("[name=popspledgeNo]").remove();
					$obj.find("[class=pops-select]").remove();
					$obj.find("[class=pops-close]").remove();

				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	var _refreshGoodsDetailList=function(){
		var pledgeNo=$("input[name=pledgeNo]").val();
		jQuery.ajax({
			url : webPath+"/pledgeBaseInfoBill/getTableDataByPledgeNoAjax",
			type : "POST",
			dataType : "json",
			data:{tableId:"tabledlpledgebaseinfobill00006",pledgeNo:pledgeNo},
			async:false,
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#goodsDetailList").html(data.tableData);
					$("input[name=pledgeWorth]").val(data.goodsValus);
					$("#goodsDetailListdiv").show();
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
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
			commitProcess(webPath+"/mfMoveableBuybackApply/submitUpdateAjax?opinionType"+opinionType+"&backId="+backId
			+"&appId="+appId,obj,'moveTransSP');
		}
	};
	return{
		init:_init,
		doSubmit:_doSubmit,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage
	};
}(window,jQuery);