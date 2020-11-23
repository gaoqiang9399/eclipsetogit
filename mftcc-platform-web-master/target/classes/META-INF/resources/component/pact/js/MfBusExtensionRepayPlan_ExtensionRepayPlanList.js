$(function() {
	//合并还款计划时到期结束日期限制编辑
	var multipleLoanPlanMerge=$("#multipleLoanPlanMerge").val();//合并还款计划
	if(multipleLoanPlanMerge==1){
		$("#endDate").attr("disabled","disabled");
	}
	
});
var extensionRepayPlan_List = function(window, $) {
	var termNum=0;//修改当前期数
	var repayPrcpDefault=0;//用与存放应还本金初始值
	var planEndDateDefault=0;//用与存放结束日期初始值
	var _init = function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	var _showExtensionEndDateInputValue = function(obj) {
		//1-等额本息  2-等额本金 3-利随本清  4-到期还本按月结息  5-到期还本按季结息   7-等本等息  
		var repayType=$("#repayType").val();
		if(repayType==1||repayType==2||repayType==3||repayType==4||repayType==5||repayType==7){
			return false;
		}
		
		var termNumArr = $(obj).attr("id").split("_");
		termNum = termNumArr[1];//获得当前期数
        var outFlag = $("#outFlag_" + termNum).val();
        if (outFlag == '1') {
            return false;
        }
		$("#planEndDate_"+termNum).show();
		$("#spanPlanEndDate_"+termNum).hide();
		planEndDateDefault=$("#planEndDate_"+termNum).val();
	};
	var _showExtensionRepayPrcpInputValue = function(obj) {
		//1-等额本息  2-等额本金 3-利随本清  4-到期还本按月结息  5-到期还本按季结息   7-等本等息  
		var repayType=$("#repayType").val();
		if(repayType==1||repayType==2||repayType==3||repayType==4||repayType==5||repayType==7){
			return false;
		}
		
		var termNumArr = $(obj).attr("id").split("_");
		termNum = termNumArr[1];//获得当前期数
        var outFlag = $("#outFlag_" + termNum).val();
        if (outFlag == '1') {
            return false;
        }
		$("#repayPrcp_"+termNum).show();
		$("#repayPrcp_"+termNum).focus();//获取焦点
		$("#spanRepayPrcp_"+termNum).hide();
		repayPrcpDefault=$("#repayPrcp_"+termNum).val();
	};
	var _showExtensionRepayIntsInputValue = function(obj) {
		//1-等额本息  2-等额本金 3-利随本清  4-到期还本按月结息  5-到期还本按季结息   7-等本等息  
		var repayType=$("#repayType").val();
		if(repayType==1||repayType==2||repayType==3||repayType==4||repayType==5||repayType==7){
			return false;
		}
		
		var termNumArr = $(obj).attr("id").split("_");
		termNum = termNumArr[1];//获得当前期数
        var outFlag = $("#outFlag_" + termNum).val();
        if (outFlag == '1') {
            return false;
        }
		$("#repayIntst_"+termNum).show();
		$("#repayIntst_"+termNum).focus();//获取焦点
		$("#spanRepayIntst_"+termNum).hide();
	};
	//修改本金事件
	var _changeExtensionRepayPrcpValue=function(obj){
		var repayPrcp=$("#repayPrcp_"+termNum).val();//获取修改后的本金
		if(repayPrcp==""||repayPrcp==undefined){
			alert(top.getMessage("FAILED_OPERATION","应还本金不能为空"),2);
			$("#repayPrcp_"+termNum).val(repayPrcpDefault);
			$("#repayPrcp_"+termNum).hide();
			$("#spanRepayPrcp_"+termNum).show();
			return false;
		}
        if (repayPrcp.indexOf('.') != -1 && repayPrcp.length - repayPrcp.indexOf('.') > 3) {
            alert("应还本金的小数位数不可大于两位", 3);
            $("#repayPrcp_" + termNum).val(repayPrcpDefault);
            $("#repayPrcp_" + termNum).hide();
            $("#spanRepayPrcp_" + termNum).show();
            return false;
        }
		var planListSize=$("#planListSize").val();//获取还款计划期数
		var loanAmt=$("#putoutAmtHidden").val();//借据金额
		var fincRate=$("#fincRate").val();//年利率
		var preTermNum=termNum-1;//上期期号
		var intstModify=$("#input_hide_intst_modify").val();//利息修改状态
		var preRepayPrcpBalAfters=$("#repayPrcpBalAfter_"+preTermNum).val();//获取上期期末本金余额
        //处理修改第一期的本金超过本金余额
        if (termNum == "1") {
            var repayPrcpBalAfters = $("#repayPrcpBalAfter_" + termNum).val();
            preRepayPrcpBalAfters = CalcUtil.add(repayPrcpDefault, repayPrcpBalAfters);
        }
		if(parseInt(repayPrcp)>parseInt(preRepayPrcpBalAfters)){
			//还原应还本金默认值
			$("#repayPrcp_"+termNum).val(repayPrcpDefault);
			$("#repayPrcp_"+termNum).hide();
			$("#spanRepayPrcp_"+termNum).show();
			alert(top.getMessage("FAILED_OPERATION","应还本金不能大于"+preRepayPrcpBalAfters),2);
			return false;
		}else if(parseInt(repayPrcp)<0){
			$("#repayPrcp_"+termNum).val(repayPrcpDefault);
			$("#repayPrcp_"+termNum).hide();
			$("#spanRepayPrcp_"+termNum).show();
			alert(top.getMessage("FAILED_OPERATION","应还本金不能是负数"),2);
			return false;
		}
		
		$("#spanRepayPrcp_"+termNum).html(repayPrcp);
		$("#repayPrcp_"+termNum).hide();
		$("#spanRepayPrcp_"+termNum).show();
		
		var multipleLoanPlanMerge=$("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
		var interestCollectType=$("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
		var repayDateSet=$("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日 
		var yearDays=$("#yearDays").val();//计息天数
		var normCalcType=$("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息 
		var secondNormCalcType=$("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
		var returnPlanPoint=$("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
		var returnPlanRound=$("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
		var instCalcBase=$("#instCalcBase").val();//还款利息计算基数：1-贷款金额、2-贷款余额
		var preInstCollectType=$("#preInstCollectType").val();
		var fincId=$("#fincId").val();//借据号
		if(preInstCollectType==null){
			preInstCollectType="";
		}
		var repayDateDef=$("#repayDateDef").val();
		if(repayDateDef==null){
			repayDateDef="";
		}
		
		//获取还款计划列表
		var planObjs=getPlanObjs();
		var repayPlanList = JSON.stringify(planObjs);
		var rulesNo=$("#rulesNo").val();
		//请求后台应还本金接口
		$.ajax({
            url: webPath + '/mfRepayPlan/changePlanByPrcpAjax',
			data:{"repayPlanList":repayPlanList,"appId":appId,"fincId":fincId,"planListSize":planListSize,"modTermNum":termNum,"loanAmt":loanAmt,"fincRate":fincRate,"intstModify":intstModify,"multipleLoanPlanMerge":multipleLoanPlanMerge,"interestCollectType":interestCollectType,"repayDateSet":repayDateSet,"yearDays":yearDays,"normCalcType":normCalcType,"secondNormCalcType":secondNormCalcType,"returnPlanPoint":returnPlanPoint,"returnPlanRound":returnPlanRound,"instCalcBase":instCalcBase,"preInstCollectType":preInstCollectType,"repayDateDef":repayDateDef,"rulesNo":rulesNo},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag=="success"){
					var mfBusFincAppChild=data.mfBusFincAppChild;
					var repayPlanList=data.repayPlanList;
					var intstModify=data.intstModify;
					getPlanListHtml(repayPlanList);
					$("#input_hide_intst_modify").val(intstModify);//设置修改状态
					$("#planListSize").val(repayPlanList.length);
					$("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
					$("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息
					//最后一期限制编辑
					var planListSize=repayPlanList.length;
					var lastPlanEndRate="#tdPlanEndDate_"+planListSize;//最后一期结束日期
					var lastRepayPrcp="#tdRepayPrcp_"+planListSize;//最后一期应还本金
					var lastRepayIntst="#tdRepayIntst_"+planListSize;//最后一期应还利息
					$(lastPlanEndRate).removeAttr('ondblclick');
					$(lastRepayPrcp).removeAttr('ondblclick');
					$(lastRepayIntst).removeAttr('ondblclick');
				}else{
					window.top.alert(data.msg, 0);
				}
			},error:function(data){
				 LoadingAnimate.stop();
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//修改利息事件
	var _changeExtensionRepayIntstValue=function(obj){
		var repayIntst=$("#repayIntst_"+termNum).val();//获取修改后的利息
		if(repayIntst==""||repayIntst==undefined){
			$("#repayIntst_"+termNum).val(repayIntst);
			$("#repayIntst_"+termNum).hide();
			$("#spanRepayIntst_"+termNum).show();
			alert(top.getMessage("FAILED_OPERATION","应还利息不能不能为空"),2);
			return false;
		}
		var planListSize=$("#planListSize").val();//获取还款计划期数
		var loanAmt=$("#putoutAmtHidden").val();//借据金额
		var fincRate=$("#fincRate").val();//年利率
		
		if(parseInt(repayIntst)<0){
			$("#repayIntst_"+termNum).val(repayIntst);
			$("#repayIntst_"+termNum).hide();
			$("#spanRepayIntst_"+termNum).show();
			alert(top.getMessage("FAILED_OPERATION","应还利息不能是负数"),2);
			return false;
		}
		
		$("#spanRepayIntst_"+termNum).html($("#repayIntst_"+termNum).val());
		$("#repayIntst_"+termNum).hide();
		$("#spanRepayIntst_"+termNum).show();
		
		var multipleLoanPlanMerge=$("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
		var interestCollectType=$("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
		var repayDateSet=$("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日 
		var yearDays=$("#yearDays").val();//计息天数
		var normCalcType=$("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息 
		var secondNormCalcType=$("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
		var returnPlanPoint=$("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
		var returnPlanRound=$("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
		var instCalcBase=$("#instCalcBase").val();//还款利息计算基数：1-按借据金额、2-借据余额
		var preInstCollectType=$("#preInstCollectType").val();
		if(preInstCollectType==null){
			preInstCollectType="";
		}
		var repayDateDef=$("#repayDateDef").val();
		if(repayDateDef==null){
			repayDateDef="";
		}
		
		//获取还款计划列表
		var planObjs=getPlanObjs();
		var repayPlanList = JSON.stringify(planObjs);
		var rulesNo=$("#rulesNo").val();
        var fincId = $("#fincId").val();//借据号
        var appId = $("#appId").val();//业务申请号
		//请求后台应还利息接口
		$.ajax({
            url: webPath + '/mfRepayPlan/changePlanByIntstAjax',
            data: {
                "repayPlanList": repayPlanList,
                "appId": appId,
                "fincId": fincId,
                "planListSize": planListSize,
                "modTermNum": termNum,
                "loanAmt": loanAmt,
                "fincRate": fincRate,
                "multipleLoanPlanMerge": multipleLoanPlanMerge,
                "interestCollectType": interestCollectType,
                "repayDateSet": repayDateSet,
                "yearDays": yearDays,
                "normCalcType": normCalcType,
                "secondNormCalcType": secondNormCalcType,
                "returnPlanPoint": returnPlanPoint,
                "returnPlanRound": returnPlanRound,
                "instCalcBase": instCalcBase,
                "preInstCollectType": preInstCollectType,
                "repayDateDef": repayDateDef,
                "rulesNo": rulesNo
            },
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag=="success"){
					var mfBusFincAppChild=data.mfBusFincAppChild;
					var repayPlanList=data.repayPlanList;
					getPlanListHtml(repayPlanList);
					$("#planListSize").val(repayPlanList.length);
					$("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
					$("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息
					$("#input_hide_intst_modify").val(1);//设置修改状态
					//最后一期限制编辑
					var planListSize=repayPlanList.length;
					var lastPlanEndRate="#tdPlanEndDate_"+planListSize;//最后一期结束日期
					var lastRepayPrcp="#tdRepayPrcp_"+planListSize;//最后一期应还本金
					var lastRepayIntst="#tdRepayIntst_"+planListSize;//最后一期应还利息
					$(lastPlanEndRate).removeAttr('ondblclick');
					$(lastRepayPrcp).removeAttr('ondblclick');
					$(lastRepayIntst).removeAttr('ondblclick');
				}else{
					window.top.alert(data.msg, 0);
				}
			},error:function(data){
				 LoadingAnimate.stop();
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//修改结束日期事件
	var _changeExtensionEndDateValue=function(){
		var planEndDate=$("#planEndDate_"+termNum).val();//获取修改后的结束日期
		planEndDate=planEndDate.replace(/-/g,"");
		
		var planListSize=$("#planListSize").val();//获取还款计划期数
		var loanAmt=$("#putoutAmtHidden").val();//借据金额
		var fincRate=$("#fincRate").val();//年利率
		var intstModify=$("#input_hide_intst_modify").val();//利息修改状态
		var prePlanEndDate=$("#planBeginDate_"+termNum).val();//获取本期开始日期
		prePlanEndDate=prePlanEndDate.replace(/-/g,"");
		var lastPlanEndDate=$("#planEndDate_"+planListSize).val();//获取起息结束日期
		lastPlanEndDate=lastPlanEndDate.replace(/-/g,"");
		if(parseInt(planEndDate)<parseInt(prePlanEndDate)){
			//还原应还本金默认值
			$("#planEndDate_"+termNum).val(prePlanEndDate);
			$("#planEndDate_"+termNum).hide();
			$("#spanPlanEndDate_"+termNum).show();
			alert(top.getMessage("FAILED_OPERATION","计划还款日期不能小于计划开始日期"),2);
			return false;
		}else if(parseInt(planEndDate)==parseInt(prePlanEndDate)){
			//还原应还本金默认值
			$("#planEndDate_"+termNum).val(prePlanEndDate);
			$("#planEndDate_"+termNum).hide();
			$("#spanPlanEndDate_"+termNum).show();
			alert(top.getMessage("FAILED_OPERATION","计划还款日期不能等于于计划开始日期"),2);
			return false;
		}else if(parseInt(planEndDate)>parseInt(lastPlanEndDate)){
			//还原应还本金默认值
			$("#planEndDate_"+termNum).val(prePlanEndDate);
			$("#planEndDate_"+termNum).hide();
			$("#spanPlanEndDate_"+termNum).show();
			alert(top.getMessage("FAILED_OPERATION","计划还款日期不能大于合同结束日期"),2);
			return false;
		}
		
		$("#spanPlanEndDate_"+termNum).html($("#planEndDate_"+termNum).val());
		$("#planEndDate_"+termNum).hide();
		$("#spanPlanEndDate_"+termNum).show();
		
		var multipleLoanPlanMerge=$("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
		var interestCollectType=$("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
		var repayDateSet=$("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日 
		var yearDays=$("#yearDays").val();//计息天数
		var normCalcType=$("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息 
		var secondNormCalcType=$("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
		var returnPlanPoint=$("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
		var returnPlanRound=$("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
		var instCalcBase=$("#instCalcBase").val();//还款利息计算基数：1-借据金额、2-借据余额
		var preInstCollectType=$("#preInstCollectType").val();
		if(preInstCollectType==null){
			preInstCollectType="";
		}
		var repayDateDef=$("#repayDateDef").val();
		if(repayDateDef==null){
			repayDateDef="";
		}
		//获取还款计划列表
		var planObjs=getPlanObjs();
		var repayPlanList = JSON.stringify(planObjs);
		var rulesNo=$("#rulesNo").val();
        var fincId = $("#fincId").val();//借据号
        var appId = $("#appId").val();//业务申请号
		//请求后台结束日期接口
		$.ajax({
            url: webPath + '/mfRepayPlan/changePlanByEndDateAjax',
            data: {
                "repayPlanList": repayPlanList,
                "appId": appId,
                "fincId": fincId,
                "planListSize": planListSize,
                "modTermNum": termNum,
                "loanAmt": loanAmt,
                "fincRate": fincRate,
                "intstModify": intstModify,
                "multipleLoanPlanMerge": multipleLoanPlanMerge,
                "interestCollectType": interestCollectType,
                "repayDateSet": repayDateSet,
                "yearDays": yearDays,
                "normCalcType": normCalcType,
                "secondNormCalcType": secondNormCalcType,
                "returnPlanPoint": returnPlanPoint,
                "returnPlanRound": returnPlanRound,
                "instCalcBase": instCalcBase,
                "preInstCollectType": preInstCollectType,
                "repayDateDef": repayDateDef,
                "rulesNo": rulesNo
            },
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag=="success"){
					var mfBusFincAppChild=data.mfBusFincAppChild;
					var repayPlanList=data.repayPlanList;
					getPlanListHtml(repayPlanList);
					$("#input_hide_intst_modify").val(0);//设置修改状态
					$("#planListSize").val(repayPlanList.length);
					$("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
					$("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息
					//最后一期限制编辑
					var planListSize=repayPlanList.length;
					var lastPlanEndRate="#tdPlanEndDate_"+planListSize;//最后一期结束日期
					var lastRepayPrcp="#tdRepayPrcp_"+planListSize;//最后一期应还本金
					var lastRepayIntst="#tdRepayIntst_"+planListSize;//最后一期应还利息
					$(lastPlanEndRate).removeAttr('ondblclick');
					$(lastRepayPrcp).removeAttr('ondblclick');
					$(lastRepayIntst).removeAttr('ondblclick');
				}else{
					window.top.alert(data.msg, 0);
				}
			},error:function(data){
				 LoadingAnimate.stop();
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};	
	//保存还款计划
	var _extensionRepayPlanAjax=function(){
		var beginDate=$("#beginDate").text();
		var endDate=$("#endDate").val();
		var dataParam = JSON.stringify($("#repayPlanForm").serializeArray());
		var planListSize=$("#planListSize").val();
		var rulesNo=$("#rulesNo").val();
		$.ajax({
			url:webPath+'/mfBusExtensionRepayPlan/extensionRepayPlanAjax',
			data:{"planListData":dataParam,"extensionApplyId":extensionApplyId,"beginDate":beginDate,"endDate":endDate,"planListSize":planListSize,"nodeNo":nodeNo},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},
			success:function(data){
				LoadingAnimate.stop();
				alert(data.msg,3,function(){//生成还款计划成功之后要提示一下，再执行原来的方法
//					top.flag=true;
//					top.putoutReviewFlag=true;
					top.extenFlag=true;
					top.tableHtml=data.tableHtml;
					myclose_click();
				});
			},error:function(data){
				 LoadingAnimate.stop();
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	function getPlanObjs(){
		var planObjs=new Array();
		$("#repayPlan_tab tr").each(function(){
			var planObj = new Object();
			var termNumArr = $(this).attr("id").split("_");
			var index = termNumArr[1];//获得当前行
			planObj.termNum=$("#termNum_"+index).val();//期数
			planObj.planBeginDate=$("#planBeginDate_"+index).val();//开始日期
			planObj.planEndDate=$("#planEndDate_"+index).val();//结束日期
			planObj.repayDate=$("#repayDate_"+index).val();//还款日期
			planObj.repayPrcp=$("#repayPrcp_"+index).val();//应还本金
			planObj.repayIntst=$("#repayIntst_"+index).val();//应还利息
			planObj.feeSum=$("#feeSum_"+index).val();//费用总额
			planObj.repayPrcpIntstSum=$("#repayPrcpIntstSum_"+index).val();//应还总额
			planObj.repayPrcpBalAfter=$("#repayPrcpBalAfter_"+index).val();//期末本金余额
            planObj.outFlag = $("#outFlag_" + index).val();//还款计划状态
			planObjs.push(planObj);
		});
		return planObjs;
	};
	return {
		init : _init,
		changeExtensionRepayPrcpValue:_changeExtensionRepayPrcpValue,
		changeExtensionRepayIntstValue:_changeExtensionRepayIntstValue,
		changeExtensionEndDateValue:_changeExtensionEndDateValue,
		extensionRepayPlanAjax:_extensionRepayPlanAjax,
		showExtensionEndDateInputValue:_showExtensionEndDateInputValue,
		showExtensionRepayPrcpInputValue:_showExtensionRepayPrcpInputValue,
		showExtensionRepayIntsInputValue:_showExtensionRepayIntsInputValue
	};
}(window, jQuery);

/**
 * 修改到期日期
 */
function getChangeExtensionPlanListByEndDate(){
	var beginDate=$("#beginDate").text();//获取改变的发放日期
	var endDate=$("#endDate").val();//获取到期日期
	var multipleLoanPlanMerge=$("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
	var interestCollectType=$("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
	var repayDateSet=$("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日 
	var yearDays=$("#yearDays").val();//计息天数
	var normCalcType=$("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息 
	var secondNormCalcType=$("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
	var returnPlanPoint=$("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
	var returnPlanRound=$("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
	var instCalcBase=$("#instCalcBase").val();//还款利息计算基数：1-借据金额、2-借据余额
	var preInstCollectType=$("#preInstCollectType").val();
	if(preInstCollectType==null){
		preInstCollectType="";
	}
	var repayDateDef=$("#repayDateDef").val();
	if(repayDateDef==null){
		repayDateDef="";
	}
	var rulesNo=$("#rulesNo").val();
	$.ajax({
		url:webPath+'/mfBusExtensionRepayPlan/getExtensionPlanListByEndDateAjax',
        data: {
            "appId": appId,
            "extensionApplyId": extensionApplyId,
            "beginDate": beginDate,
            "endDate": endDate,
            "multipleLoanPlanMerge": multipleLoanPlanMerge,
            "interestCollectType": interestCollectType,
            "repayDateSet": repayDateSet,
            "yearDays": yearDays,
            "normCalcType": normCalcType,
            "secondNormCalcType": secondNormCalcType,
            "returnPlanPoint": returnPlanPoint,
            "returnPlanRound": returnPlanRound,
            "instCalcBase": instCalcBase,
            "preInstCollectType": preInstCollectType,
            "repayDateDef": repayDateDef,
            "rulesNo": rulesNo
        },
		type:"POST",
		dataType:"json",
		beforeSend:function(){
		},
		success:function(data){
			if(data.flag=="success"){
				var mfBusFincAppChild=data.mfBusFincAppChild;
				var repayPlanList=data.repayPlanList;
				getPlanListHtml(repayPlanList);
				$("#planListSize").val(repayPlanList.length);
//				$("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
//				$("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息			
//				$("#span_putout_amt_real_format").html(mfBusFincAppChild.putoutAmtRealFormat);//实际发放的金额
//				$("#span_charge_interest_sum").html("&nbsp;(扣除费用利息"+mfBusFincAppChild.chargeFeeAndInterSumFormat+"元)");//提前收取的费用
				//最后一期限制编辑
				var planListSize=repayPlanList.length;
				var lastPlanEndRate="#tdPlanEndDate_"+planListSize;//最后一期结束日期
				var lastRepayPrcp="#tdRepayPrcp_"+planListSize;//最后一期应还本金
				var lastRepayIntst="#tdRepayIntst_"+planListSize;//最后一期应还利息
				$(lastPlanEndRate).removeAttr('ondblclick');
				$(lastRepayPrcp).removeAttr('ondblclick');
				$(lastRepayIntst).removeAttr('ondblclick');
			}else {
				window.top.alert(data.msg, 0);
			}
			LoadingAnimate.stop();
			top.flag=true;
			top.putoutReviewFlag=true;
		},error:function(data){
			 LoadingAnimate.stop();
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}
//封装还款计划列表HTML
function getPlanListHtml(obj){
	$("#repayPlan_tab").html("");
	$.each(obj,function(i,repayPlan){
		$("#repayPlan_tab").append(
            "<tr id='tr_" + repayPlan.termNum + "'>" +
            "<td align='center'>" +
            "<span class='tab_span' id='spanTermNum_" + repayPlan.termNum + "'>" + repayPlan.termNum + "</span>" +
            "<input type='text' name='termNum" + repayPlan.termNum + "' id='termNum_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.termNum + "'>" +
            "</td>" +
            "<td align='center'>" +
            "<span class='tab_span' id='spanPlanBeginDate_" + repayPlan.termNum + "'>" + repayPlan.planBeginDateFormat + "</span>" +
            "<input type='text' name='planBeginDate" + repayPlan.termNum + "' id='planBeginDate_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.planBeginDateFormat + "' datatype='6'>" +
            "</td>" +
            "<td align='center'>" +
            "<span class='tab_span' id='spanExpiryIntstDate_" + repayPlan.termNum + "'>" + repayPlan.expiryIntstDateFormat + "</span>" +
            "<input type='text' name='expiryIntstDate" + repayPlan.termNum + "' id='expiryIntstDate_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.expiryIntstDateFormat + "' datatype='6' mustinput='1' maxlength='10'>" +
            "</td>" +
            "<td align='center' id='tdPlanEndDate_" + repayPlan.termNum + "' ondblclick='extensionRepayPlan_List.showExtensionEndDateInputValue(this)'>" +
            "<span class='tab_span' id='spanPlanEndDate_" + repayPlan.termNum + "'>" + repayPlan.planEndDateFormat + "</span>" +
            "<input type='text' name='planEndDate" + repayPlan.termNum + "' id='planEndDate_" + repayPlan.termNum + "' datatype='6' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.planEndDateFormat + "' onfocus='fPopUpCalendarDlg({max:lastReturnDate, choose:extensionRepayPlan_List.changeExtensionEndDateValue});'  onmousedown='enterKey()' onkeydown='enterKey();' datatype='6' mustinput='1' maxlength='10'>" +
            "</td>" +
            "<td  id='tdRepayPrcp_" + repayPlan.termNum + "'  ondblclick='extensionRepayPlan_List.showExtensionRepayPrcpInputValue(this)'>" +
            "<span class='tab_span money_right' id='spanRepayPrcp_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpFormat + "</span>" +
            "<input type='text' name='repayPrcp" + repayPlan.termNum + "' id='repayPrcp_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.repayPrcp + "'   onblur='extensionRepayPlan_List.changeExtensionRepayPrcpValue(this);' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">" +
            "</td>" +
            "<td  id='tdRepayIntst_" + repayPlan.termNum + "' ondblclick='extensionRepayPlan_List.showExtensionRepayIntsInputValue(this)'>" +
            "<span class='tab_span money_right' id='spanRepayIntst_" + repayPlan.termNum + "'>" + repayPlan.repayIntstFormat + "</span>" +
            "<input type='text' name='repayIntst" + repayPlan.termNum + "' id='repayIntst_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.repayIntst + "'   onblur='extensionRepayPlan_List.changeExtensionRepayIntstValue(this);' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">" +
            "</td>" +
            "<td >" +
            "<span class='tab_span money_right' id='spanRepayPrcpIntstSum_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpIntstSumFormat + "</span>" +
            "<input type='text' name='repayPrcpIntstSum" + repayPlan.termNum + "' id='repayPrcpIntstSum_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.repayPrcpIntstSum + "'>" +
            "</td>" +
            "<td >" +
            "<span class='tab_span money_right' id='spanRrepayPrcpBalAfter_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpBalAfterFormat + "</span>" +
            "<input type='text' name='repayPrcpBalAfter" + repayPlan.termNum + "' id='repayPrcpBalAfter_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.repayPrcpBalAfter + "' >" +
            "</td>" +
            "<td align='center' style='display:none'>" +
            "<input type='text' name='repayIntstOrig" + repayPlan.termNum + "' id='repayIntstOrig_" + repayPlan.termNum + "'  value='" + repayPlan.repayIntstOrig + "'>" +
            "</td>" +
            "<td align='center' style='display:none'>" +
            "<input type='text' name='repayDate" + repayPlan.termNum + "' id='repayDate_" + repayPlan.termNum + "'  value='" + repayPlan.repayDate + "'>" +
            "</td>" +
            "<td align='center' style='display:none'>" +
            "<input type='text' name='outFlag" + repayPlan.termNum + "' id='outFlag_" + repayPlan.termNum + "'  value='" + repayPlan.outFlag + "'>" +
            "</td>" +
            "<td align='center' style='display:none'>" +
            "<input type='text' name='feeSum" + repayPlan.termNum + "' id='feeSum_" + repayPlan.termNum + "'  value='" + repayPlan.feeSum + "'>" +
            "</td>" +
            "</tr>"
		);
	 });
}
function changeMfFincAppValue(delayDateMap){
	$("#endDate").html("");
	$.each(delayDateMap,function(key,value){ 
		$("#endDate").append(
			"<option value='"+value+"'>"+value+"</option>"
		);
	});
}
