;
//流程信息
var pubWkfFlow = function(window, $) {
	//正常初始化
	var _init = function (){
		// t1:渠道商操作员
		if(busEntrance=="1"||busEntrance=="2"||busEntrance=="3"||busEntrance=="t1" || busEntrance==="pact" || busEntrance=="apply"||busEntrance=="rece_finc"){//可操作页面展示业务流程
			showWkfFlow($("#wj-modeler1"),wkfAppId);
			$('.next-div').removeClass('hidden');
		}else{//审批展示审批流程
			showWkfFlow($("#wj-modeler1"),appId);
		}
	};
	return {
		init : _init
	};
}(window, jQuery);

var pubApplyHeadInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusApply/getBusDetailInfoAjax?appId="+appId,
			type:'post',
			dataType:'json',
			success:function(dataMap){
				if(dataMap){
					var mfBusApply = dataMap.mfBusApply;
					//产品信息
					if(mfBusApply.kindName.length>8){
						$('.head-info .btn-link').attr('title', mfBusApply.kindName).text(mfBusApply.kindName.substring(0,8));
					}else{
						$('.head-info .btn-link').text(mfBusApply.kindName);
					}
					var kindNo=mfBusApply.kindNo;
					if(kindNo!='1006'){
						$("#debtEvalApply").hide();
					}
					//头部名称
					$('.head-info .head-title').attr('title', mfBusApply.cusName).text(mfBusApply.cusName);
					var moreApplyCount = dataMap.moreApplyCount;
					$('.head-info .more-apply-count').text(moreApplyCount);
//					if(moreApplyCount > 1){
//					}else{
//						$('.head-info .moreCnt_apply').remove();
//					}
					var morePactCount = dataMap.morePactCount;
					$('.head-info .more-pact-count').text(morePactCount);

					var moreVouAfterCount = dataMap.moreVouAfterCount;
					$('.head-info .more-early-warning-count').text(moreVouAfterCount);
//					if(morePactCount > 1){
//					}else{
//						$('.head-info .moreCnt_pact').remove();
//					}
					var moreFincCount = dataMap.moreFincCount;
					$('.head-info .more-finc-count').text(moreFincCount);
//					if(moreFincCount > 1){
//					}else{
//						$('.head-info .moreCnt_finc').remove();
//					}
					var moreAssureCount = dataMap.moreAssureCount;
					$('.head-info .more-assure-count').text(moreAssureCount);

                    var moreFincFinishCount = dataMap.moreFincFinishCount;
                    $('.head-info .more-finc-finish-count').text(moreFincFinishCount);

                    var moreRepayCount = dataMap.moreRepayCount;
                    $('.head-info .more-repay-count').text(moreRepayCount);
                    appIdOld = mfBusApply.appIdOld;
					if(typeof (appIdOld) != "undefined" && appIdOld != null && appIdOld != "" && appIdOld != "null"){
						$("#applyOldBusInfo").show();
						let loanKind = mfBusApply.loanKind;
						if("5" == loanKind){
                            $("#analysisTable").show();
						}
					}
					//查看风险检查
					riskLevel=dataMap.riskLevel;
					var oldLevel = $('#risklevel').val();
					$('.head-info .apply-risk-level').removeClass('risklevel' + oldLevel);
					$('#risklevel').val(riskLevel);
					$('.head-info .apply-risk-level').addClass('risklevel' + dataMap.riskLevel).find('span').text(dataMap.riskName);
					//金额、期限、利率
					$('.head-info #appAmt').text(mfBusApply.fincAmt);
					if("3" == mfBusApply.termType){
                        $('.head-info #term').parent().prev().text("保函到期日");
                        $('.head-info #term').text(mfBusApply.termShow);
                    }else{
                        $('.head-info #term').text(mfBusApply.term).next('.unit-span').text(mfBusApply.termType=="1" ? "个月" : "天");
                    }
					$('.head-info #fincRate').text(dataMap.fincRate).next('.unit-span').text(dataMap.rateUnit);
					
					//机构
					var $relate = $('');
                    var method;
					if(dataMap.wareHouseCusNo!=null && dataMap.wareHouseCusNo!="" && dataMap.wareHouseCusNo!="0"){
						method = "getInfoForView('103','"+ dataMap.wareHouseCusNo + "','仓储机构');";
						$relate = $('<span  class="relate-corp" data-view="cuswarehouse"><i class="i i-cangKu"></i><span>由仓储机构<a href="javascript:void(0);" onclick="'+method+'">' + mfBusApply.cusNameWarehouse + '</a>保管货物 </span></span>');
					}else if(dataMap.coreCusNo!=null && dataMap.coreCusNo!="" && dataMap.coreCusNo!="0"){
						method = "getInfoForView('108','" + dataMap.coreCusNo + "','核心企业');";
						$relate = $('<span class="relate-corp" data-view="cuscore"><i class="i i-qiYe"></i><span>由核心企业 <a href="javascript:void(0);"  onclick="'+method+'">' + mfBusApply.cusNameCore + '</a> 推荐</span></span>');
					}else if(dataMap.fundCusNo!=null && dataMap.fundCusNo!="" && dataMap.fundCusNo !="0"){
						method = "getInfoForView('109','" + dataMap.fundCusNo + "','资金机构 ');";
						$relate = $('<span class="relate-corp" data-view="fundorg" ><i class="i i-fundorg"></i><span>由资金机构 <a href="javascript:void(0);" onclick="'+method+'">' + mfBusApply.cusNameFund + '</a> 放款</span></span>');
					}
					$('.head-info .btn-special').html($relate);
					
				}
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);


//申请详情信息
var pubApplyDetailInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusApply/getApplyDetailFormAjax?appId="+appId+"&formId="+formId + "&queryForm=" + queryForm,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag=="success"){
                    var html = data.htmlStr;
                    $("#applyDetailForm").empty().html(html);
                }else {
                    $("#applyDetailForm").empty().html(data.msg);
				}
			}
		});
	};
	//给共同借款人赋值
	var _setCoborrName=function(cusInfo){
		$("input[name=coborrName]").val(cusInfo.cusName);
		$("input[name=coborrNum]").val(cusInfo.idNum);
		$("input[name=coborrName]").parent().prev().html(cusInfo.cusName);
	};
	return {
		init : _init,
		setCoborrName:_setCoborrName
	};
}(window, jQuery);
//费用列表
var pubBusAppFee = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url: webPath+'/mfBusAppFee/getListPage?appId='+appId,
			type:'post',
			dataType:'html',
			success:function(data){
				var $html = $(data);
				if($html.find("table #tab tr").length>0){
					var tableStr = $html.find("table").prop("outerHTML");
					$(".content[name=busfee]").html(tableStr);
				}else{
					$('#bus-fee-block').remove();
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);
//还款计划
var pubBusAppRepayPlan = function(window, $) {
    //正常初始化
    var _init = function (){
        $.ajax({
            url: webPath+'/mfRepayPlan/getListPage?appId='+appId,
            type:'post',
            dataType:'html',
            success:function(data){
                var $html = $(data);
                if($html.find("table #tab tr").length>0){
                    var tableStr = $html.find("table").prop("outerHTML");
                    $(".content[name=busrepayplan]").html(tableStr);
                }else{
                    $('#busrepayplan-block').remove();
                }
            },error:function(){
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    };
    return {
        init : _init
    };
}(window, jQuery);
//业务流程历史
var pubBusApproveHis = function(window, $) {
	//正常初始化
	var _init = function (){
		//获得审批信息
		showWkfFlowVertical($("#wj-modeler2"),appId,"5","apply_approval");
	};
	return {
		init : _init
	};
}(window, jQuery);

//业务初选流程历史
var pubPrimaryBusApproveHis = function(window, $) {
	//正常初始化
	var _init = function (primaryAppId){
		//获得审批信息
		showWkfFlowVertical($("#wj-modeler-primary"),primaryAppId,"05","primary_apply_approval");
	};
	return {
		init : _init
	};
}(window, jQuery);

// 否决复议审批流程历史
var reconsiderApproveHis = function(window, $) {
	// 正常初始化
	var _init = function(reconsiderId) {
		// 获得审批信息
		showWkfFlowVertical($("#wj-modeler2"), reconsiderId, "13","apply_approval");
	};
	return {
		init : _init
	};
}(window, jQuery);

//申请阶段客户信息展示
var pubApplyCusInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfCusCustomer/getApplyCusInfoAjax?relNo="+appId+"&cusNo="+cusNo,
			success:function(data){
				MfCusDyForm.init(data.cusTableList);
			}
		});
	};
	return {
		init : _init,
	};
}(window, jQuery);
