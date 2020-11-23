;

//合同详情信息
var pubFincMainDetailInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusPact/getPactDetailFormAjax?appId="+appId+"&pactId="+pactId+"&formId="+pactFormId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				$("#pactDetailForm").empty().html(html);
				if(data.busModel=='31'||(data.busModel=='29'&&data.kindNo=='1006')){//华北小贷的中创产品要求
					$("#hbxdA").show();
					$("#hbxdB").show();
					$("#hbxdC").show();
				}
			}
		});
	};
	function showElecPact(tempNo){
		$.ajax({
			url:webPath+'/mfBusPact/getElecFileAjax?pactId='+pactId+'&tempNo='+tempNo,
			dataType:'json',
			type:'post',
			success:function(data){
				window.open(data.url);
			}
		})
	}
	return {
		init : _init,
		showElecPact:showElecPact
	};
}(window, jQuery);

//合同详情信息
var pubFincMainHeadInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusFincAppMain/getFincMainHeadInfoAjax?appId="+appId+"&pactId="+pactId+"&fincMainId="+fincMainId,
			type:'post',
			dataType:'json',
			success:function(dataMap){
				if(dataMap){
					var mfBusPact = dataMap.mfBusPact;
					var mfBusApply = dataMap.mfBusApply;
					var mfBusFincAppMain = dataMap.mfBusFincAppMain;
					//产品信息
					if(mfBusPact.kindName.length>8){
						$('.head-info .btn-link').attr('title', mfBusPact.kindName).text(mfBusPact.kindName.substring(0,8));
					}else{
						$('.head-info .btn-link').text(mfBusPact.kindName);
					}
					//头部名称
					$('.head-info .head-title').attr('title', mfBusPact.appName).text(mfBusPact.appName);
					var moreApplyCount = dataMap.moreApplyCount;
					$('.head-info .more-apply-count').text(moreApplyCount);

					var morePactCount = dataMap.morePactCount;
					$('.head-info .more-pact-count').text(morePactCount);

					var moreFincCount = dataMap.moreFincCount;
					$('.head-info .more-finc-count').text(moreFincCount);

					var moreAssureCount = dataMap.moreAssureCount;
					$('.head-info .more-assure-count').text(moreAssureCount);

                    var moreFincFinishCount = dataMap.moreFincFinishCount;
                    $('.head-info .more-finc-finish-count').text(moreFincFinishCount);
					
					//风险检查
					$('.head-info .apply-risk-level').addClass('risklevel' + dataMap.riskLevel).find('span').text(dataMap.riskName);
					//金额、期限、利率
					$('.head-info #fincAmt').text(dataMap.fincAmt);
					$('.head-info #fincBal').text(dataMap.fincBal);
                    $('.head-info #overDays').text(dataMap.overDays);
				}
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//放款申请详情信息
var pubFincHeadInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusFincApp/getFincHeadInfoAjax?appId="+appId+"&pactId="+pactId+'&fincId='+fincId,
			type:'post',
			dataType:'json',
			success:function(dataMap){
				if(dataMap){
					var mfBusPact = dataMap.mfBusPact;
					var mfBusApply = dataMap.mfBusApply;
					//产品信息
					if(mfBusPact.kindName.length>8){
						$('.head-info .btn-link').attr('title', mfBusPact.kindName).text(mfBusPact.kindName.substring(0,8));
					}else{
						$('.head-info .btn-link').text(mfBusPact.kindName);
					}
					//头部名称
					$('.head-info .head-title').attr('title', mfBusPact.appName).text(mfBusPact.appName);
					var moreApplyCount = dataMap.moreApplyCount;
					$('.head-info .more-apply-count').text(moreApplyCount);
//					if(moreApplyCount > 1){
//					}else{
//						$('.head-info .moreCnt_apply').remove();
//					}
					var morePactCount = dataMap.morePactCount;
					$('.head-info .more-pact-count').text(morePactCount);
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
                    var moreVouAfterCount = dataMap.moreVouAfterCount;
                    $('.head-info .more-early-warning-count').text(moreVouAfterCount);

					var moreAssureCount = dataMap.moreAssureCount;
					$('.head-info .more-assure-count').text(moreAssureCount);

                    var moreFincFinishCount = dataMap.moreFincFinishCount;
                    $('.head-info .more-finc-finish-count').text(moreFincFinishCount);

                    //查看风险检查
					$('.head-info .apply-risk-level').addClass('risklevel' + dataMap.riskLevel).find('span').text(dataMap.riskName);
					//金额、期限、利率
					$('.head-info #fincAmt').text(mfBusPact.fincAmt);
					$('.head-info #pactBal').text(dataMap.pactBal);
					$('.head-info #overDays').text(dataMap.overDays);
					
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
					$('.head-info .btn-special').empty().append($relate);
					
				}
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//放款申请详情信息
var pubFincAppDetail = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusFincApp/getFincAppDetailFormAjax?fincId="+fincId+"&formId="+fincFormId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				$("#fincAppDetail").empty().html(html);
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//收款计划列表信息
var pubMfRepayPlanListForMain = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusFincAppMain/getMfRepayPlanListAjax?fincMainId="+fincMainId+"&tableId="+planTableId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
                if(html==''){
                    $('#mfRepayHistoryList-block').addClass('hidden');
                }else{
                    $('#mfRepayHistoryList-block').removeClass('hidden');
                    $("#mfRepayHistoryList").empty().html(html);
                }
                $("#tableList").show();
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);


//还款历史列表信息
var pubMfRepayHistoryListForMain = function(window, $) {
	//正常初始化
    var _init = function (){
        $.ajax({
            url:webPath+"/mfBusFincAppMain/getMfRepayPlanListAjax?fincMainId="+fincMainId+"&tableId="+planTableId,
            type:'post',
            dataType:'json',
            success:function(data){
                var html = data.htmlStr;
                if(html==''){
                    $('#mfRepayHistoryList-block').addClass('hidden');
                }else{
                    $('#mfRepayHistoryList-block').removeClass('hidden');
                    $("#mfRepayHistoryList").empty().html(html);
                }
                $("#tableList").show();
            }
        });
    };
	return {
		init : _init
	};
}(window, jQuery);


//收费历史列表信息
var pubMfRepayFeeHistoryList = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusFeePlanHistoryDetail/getMfRepayFeeHistoryListAjax?pactId="+pactId+"&fincId="+fincId+"&tableId="+repayFeeHisTableId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				if(html==''){
                    $('#mfRepayFeeHistoryList-block').addClass('hidden');
				}else{
					$('#mfRepayFeeHistoryList-block').removeClass('hidden');
					$("#mfRepayFeeHistoryList").empty().html(html);
				}
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//检查历史列表信息
var pubMfExamineHisList = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfExamineHis/getMfExamineHisListAjax?appId="+appId+"&pactId="+pactId+"&tableId="+examTableId+"&fincId="+fincId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.size>0){
					var html = data.htmlStr;
					$("#examineHis").empty().html(html);
					$("#mfExamineHis-div").show();
				}else{
					$("#mfExamineHis-div").hide();
				}
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//放款历史列表
var putoutHisList = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url: webPath+'/mfBusFincApp/getPutOutHisListAjax?pactId='+pactId+"&tableId="+hisTableId,
			type:'post',
			dataType:'json',
			success:function(data){
				var $html = $(data.tableData);
				$(".content[name=putoutHis]").html(data.tableData);
				if($("#putoutHis").find("table #tab tr").length>0){
					var tableStr = $html.find("table").prop("outerHTML");
					$('#putout-his-block').show();
				}else{
					$('#putout-his-block').hide();
				}
				var putoutSts=data.putoutSts;
				var putoutStsText="";
				var putoutStsIcolor="";
				if(putoutSts=="1"){//可放款
					putoutStsText="可放款";
					putoutStsIcolor="#32b5cb";
				}else if(putoutSts=="2"){//放款中
					putoutStsText="放款中";
					putoutStsIcolor="#E4BA58";
				}else if(putoutSts=="3"){//放款结束
					putoutStsText="放款完结";
					putoutStsIcolor="#adadad";
				}else if(putoutSts=="4"){//已否决
                    putoutStsText="已否决";
                    putoutStsIcolor="#adadad";
                }
				$("#putoutSts-i").css("color",putoutStsIcolor);
				$("#putoutSts").html(putoutStsText);
				$("#putoutSts").css("color",putoutStsIcolor); 
				$("#putoutSts").css("top","79px"); 
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	var _getPutoutDetail=function(obj,url){
		top.window.openBigForm(url,"放款详情",function(){});
	};
	return {
		init : _init,
		getPutoutDetail:_getPutoutDetail
	};
}(window, jQuery);
//展期申请详情信息
var pubExtenAppDetail = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusExtensionApply/getByIdAjax?fincId="+fincId+"&formId=extensionapply0002",
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag=="success"){
					var extensionApply=data.extensionApply;
					var extenAppSts = extensionApply.extenAppSts;
					var extenBusStage = extensionApply.extenBusStage;
					//展期业务完成前，展示展期详情和展期业务审批历史流程图
					if(extenBusStage!="7"){
						var html = data.htmlStr;
						$("#ExtenAppDetail").empty().html(html);
						$("#ExtenAppDetail-div").show();
						//展期业务状态审批中、发回重审、退回补充资料展示审批历史流程图
						if (extenAppSts == '1'||extenAppSts == '4'||extenAppSts == '3') {
							$("#extension-wj-modeler").empty();
							showWkfFlowVertical($("#extension-wj-modeler"), extensionApply.extensionApplyId, "16","exten_apply_approval");
							$("#extenSpInfo-block").show();
						} else {
							$("#extenSpInfo-block").hide();
						}
					}
				}else{
					$("#ExtenAppDetail-div").hide();
				}
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//资方收款计划列表信息
var pubMfManagePlanList = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfPlanInfoWx/getMfManagePlanListAjax?fincId="+fincId+"&tableId="+manageplanTableId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				$("#mfManagePlanList").empty().html(html);
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//GPS详情信息
var pubMfGpsDetailInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusGpsReg/getByIdAjax?appId="+appId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				$("#gpsDetailForm").empty().html(html);
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//GPS列表展示信息
var pubMfGpsListInfo = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusGpsReg/getGpsInfoListAjax?appId="+appId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					if(data.complete == "1" ){
						$("#gpsListInfo").empty().html(data.tableHtml);
					}else{
						$('#gpsListDiv').hide();
					}
				}
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);

//费用计划列表信息
var pubMfBusFeePlanList = function(window, $) {
	//正常初始化
	var _init = function (){
		$.ajax({
			url:webPath+"/mfBusFeePlan/getMfBusFeePlanListAjax?fincId="+fincId+"&tableId="+feePlanTableId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				if(html==''){
				}else{
					$('#mfBusFeePlanList-block').removeClass('hidden');
					$("#mfBusFeePlanList").empty().html(html);
				}
			}
		});
	};
	return {
		init : _init
	};
}(window, jQuery);