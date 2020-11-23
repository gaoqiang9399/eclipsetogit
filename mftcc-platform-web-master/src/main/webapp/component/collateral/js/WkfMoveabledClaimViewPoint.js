;
var WkfMoveabledClaimViewPoint=function(window,$){
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
		_getPledgeData();
	};
	//初始化选择押品数据源
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=claimPledge]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:true,//单选
						items:data.items,
					});
					//_refreshGoodsDetailList();
					var $obj=$("input[name=claimPledge]").parents("td").eq(0);
					$obj.find("[name=popsclaimPledge]").remove();
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
		var pledgeNo=$("input[name=claimPledge]").val();
		jQuery.ajax({
			url : webPath+"/pledgeBaseInfoBill/getTableDataByPledgeNoAjax",
			type : "POST",
			dataType : "json",
			data:{tableId:"tabledlpledgebaseinfobill0006",pledgeNo:pledgeNo},
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#goodsDetailList").html(data.tableData);
					if($('.table_content #tab').find($('input[type=checkbox]'))>0){
					$("#goodsDetailListdiv").show();
					}
					$("th[name=pledgeBillNo]").html('<a href="javascript:void(0);" onclick="MfMoveableClaimGoodsApply.checkAllGoodsDetail()">全选</a>');
					$('.table_content #tab').find($('input[type=checkbox]')).attr("checked","checked");
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
			commitProcess(webPath+"/mfMoveableClaimGoodsApply/submitUpdateAjax?opinionType="+opinionType+"&claimId="+claimId
			+"&appNo="+claimId,obj,'moveClaimSP');
		}
	};
	return{
		init:_init,
		doSubmit:_doSubmit,
		approvalBack:_approvalBack,
		getApprovaPage:_getApprovaPage,
		refreshGoodsDetailList:_refreshGoodsDetailList
	};
}(window,jQuery);