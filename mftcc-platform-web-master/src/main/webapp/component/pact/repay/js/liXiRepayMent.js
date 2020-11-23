/**
 * 实还总额变化事件 1、首先 需要把每个金额重置为各自的应还金额 2、然后根据输入的实还金额
 * 按照扣款顺序依次逐次递减，最后一个金额如果不足，修改为最后的余额 3、重新计算各项合计值
 */
function liXiRepayTotalChg() {
	var jieYuTuiKuanVal = $("#jieYuTuiKuan_input_hidden").val();
	// cmpdRateType 复利利息是否收取：0-不收取、1-收取 
	// 获取实收总计
	var totalAmt = $("#shiShouZongJi").val().replace(/,/g, "");
	var totalPayVal = $("#totalPay_input_hidden").val();
	// 修改操作状态
	if (totalAmt != "") {
		// 冲抵金额
		var chongdiAmt = $("#benCiChongDi").text().replace(/,/g, "");
		if ("2" == jieYuTuiKuanVal) {// 允许退款
			chongdiAmt = "0.00";
		}
		totalAmt = totalAmt * 1 + chongdiAmt * 1;
	}
	var loadAmt = totalAmt;// 实收总额+冲抵金额
	// 如果实还总额 不能大于该笔贷款的可还总额
	var lastTermBalanceType=$("#lastTermBalanceType_input_hidden").val()//允许最后一期结余：0-不允许、1-允许
	if(lastTermBalanceType !="1"){//不允许
		if (totalPayVal * 1 < totalAmt * 1) {
			alert(top.getMessage("NOT_FORM_TIME", {
				"timeOne" : '实还总额',
				"timeTwo" : '该笔贷款的可还总额'
			}), 0);
			$("#shiShouZongJi").val("0.00");
			return;
		}
	}
	// 按照还款顺序 计算相关金额
	checkLiXiAmtOneByOne(loadAmt);
	// 同步实际还款合计
	synchronizeLiXiRealAmt();
	// 进入编辑状态
	adjustLiXiPayDetail();
} 


/**
 * 还款日期修改使用 1 判断是否存在逾期的还款计划 2 如果存在逾期的还款计划则需要修改还款页面相关值 3 如果不存在逾期的还款计划 则还款页面不必进行修改
 */
function repayDateChange() {

}
/**
 * 隐藏相关还款信息
 * @returns wd
 */
function hideRepayInfo(){
    var cmpdRateType = $("#cmpdRateType_input_hidden").val();//复利利息是否收取：0-不收取、1-收取 
	if("0" == cmpdRateType){//复利利息不展示
		$("#shiShouFuLiLiXiTd").hide();
		$("#shiShouFuLiLiXiTdValue").hide();
		$('#yingShou_List_table_001 tr th:eq(2)').hide();
		$('#yingShou_List_table_001 tr').find('td:eq(2)').hide();		
	}
    //是否支持减免 1-是 0-否  启用利息减免：还款时，利息支持减免优惠，包括正常利息和罚息
    var interestDerateFlag = $("#interestDerateFlag_input_hidden").val();	
	if("0" == interestDerateFlag){//减免不显示
		$("#yiJianMianSpan").hide();
		$('#yingShou_List_table_001 tr th:eq(9)').hide();
		$('#yingShou_List_table_001 tr').find('td:eq(9)').hide();		
	}
	//是否是利率浮动分开 为利率浮动时 逾期利率和复利利率保存到数据库的值：0-正常年利率加浮动利率值（正常存放 存合计值 存1+0.5计算出的值）、1-浮动利率值（只存浮动值 存0.5计算出的值）
	var overCmpdFltSaveflag = $("#overCmpdFltSaveflag_input_text").val();
	if("0" == overCmpdFltSaveflag){
		$('#yingShou_List_table_001 tr th:eq(3)').hide();
		$('#yingShou_List_table_001 tr').find('td:eq(3)').hide();	
	}
	//逾期违约金
	var yuQiWeiYueJinShowFlag = $("#yuQiWeiYueJinShowFlag_input_text").val();
	if("0" == yuQiWeiYueJinShowFlag){
		$('#yingShou_List_table_001 tr th:eq(4)').hide();
		$('#yingShou_List_table_001 tr').find('td:eq(4)').hide();	
	}
	//费用是否展示标志
	var feeYongShowFlag = $("#feeYongShowFlag_input_text").val();
	var feiYongFaXiFlag = $("#feiYongFaXiFlag_input_text").val();
	if("0" == feiYongFaXiFlag){
		$('#yingShou_List_table_001 tr th:eq(7)').hide();
		$('#yingShou_List_table_001 tr').find('td:eq(7)').hide();	
    }
	//三方还款 使用  0 代表正常  1 代表 正在还款中  不允许再次发起三方还款
	var thirdRepaymentFlag = $("#thirdRepaymentFlag_input_hidden").val();
	if(thirdRepaymentFlag==1){
		$(".third-repay").attr({"disabled":"disabled"});
		$(".third-repay").val("还款处理中");
	}
	
}
/**
 * 同步结余=实还总额+优惠总额+冲抵金额-各期实还之和
 * 
 * @author WD
 */
function synchronizeLiXiJieyuAmt() {
	var returnPlanPoint = $("#returnPlanPoint_hidden").val();
	var jieYuTuiKuanVal = $("#jieYuTuiKuan_input_hidden").val();
	var totalRealPayAmt = $("#shiShouZongJi").val().replace(/,/g, "");
	var totalYouhuiAmt = getLiXiYouhuiTotalAmt();
	var chongdiAmt = "0.00";
	var termNum = "";
	var shouldVal = "";
	var orderAry = payOrder.split(",");
	var realItemAmt = 0.00;
	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
		termNum = $(this).attr("termNum");// 获取期号
		var orderLen = orderAry.length;
		for ( var i = 0; i < orderLen; i++) {
			shouldVal = $("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").val();
			realItemAmt = CalcUtil.add(realItemAmt,shouldVal);
		}
	});
	var curJieyu = 0.00;
	curJieyu = CalcUtil.add(curJieyu, totalRealPayAmt);
	curJieyu = CalcUtil.add(curJieyu, totalYouhuiAmt);
	curJieyu = CalcUtil.add(curJieyu, chongdiAmt);
	curJieyu = CalcUtil.subtract(curJieyu, realItemAmt);
	if (curJieyu * 1 < 0.00001) {
		curJieyu = 0;
	}
	$("#benCiJieYu").text(CalcUtil.formatMoney(curJieyu, returnPlanPoint));
	$("#benCiJieYu_input_text").val(curJieyu);
}

/**
 * 获取优惠合计
 * 
 * @returns {Number}
 * @author WD
 */
function getLiXiYouhuiTotalAmt() {
	var totalYouhuiAmt = 0.00;
	$("input[id$='_youHuiJine_input']").each(function() {
		totalYouhuiAmt = CalcUtil.add(totalYouhuiAmt,$(this).val());
	});
	return totalYouhuiAmt;
}

/**
 * 重置应还金额
 */
function resetShouldPayAmt() {
	var termNum = "";
	var shouldVal = "";
	var orderAry = payOrder.split(",");
	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
		termNum = $(this).attr("termNum");// 获取期号
		var orderLen = orderAry.length;
		for ( var i = 0; i < orderLen; i++) {
			shouldVal = $("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").attr("fromVal");
			$("#input_span_" + termNum + "_yingShou" + orderAry[i] + "_td_span").text(shouldVal);
			$("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").val(shouldVal);
		}
	});
	clearYouhuiAmt();// 重置优惠金额
}
/**
 * 计算还款计划的应还金额之和（本金+利息）
 * 
 * @param plans
 *            还款计划
 */
function calcPaySumOfPlans(plans) {
	var resSum = 0.00 * 1;
	if (plans != null) {
		var len = plans.length;
		if (len > 0) {
			for ( var i = 0; i < len; i++) {
				resSum = CalcUtil.add(resSum,plans[i].returnsum);				
				resSum = CalcUtil.add(resSum,plans[i].rateincome);
			}
		}
	}
	return resSum;

}
/**
 * 在指定期号之后加载新的应还信息
 * 
 * @param termNum
 *            期号
 * @param plans
 *            应收信息
 */
function addNewPlans(termNum, plans) {
	var len = plans.length;
	if (len > 0) {
		var payTab = $("#yingShou_List_table_001");
		var newTerm = termNum * 1 + 1;
		for ( var i = 0; i < len; i++) {
			addOneTrForPayInfo(plans[i], payTab, newTerm * 1 + i);
		}
	}

}
/**
 * 按照还款顺序 计算相关金额
 * 
 * @param paySum
 *            实还总额
 * @author WD
 */
function checkLiXiAmtOneByOne(paySum) {
	var tmpSum = paySum;// 实收总额
	var tmpAmt = 0;// 每项应还金额
	var termNum = "1";// 期号
	var ifSetZero = false;// 是否为0
	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
		termNum = $(this).attr("termNum");// 获取期号
		var orderAry = payOrder.split(",");// 还款顺序
		// "YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,BenJin";//代表还款顺序
		// 应该取传过来的值 YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,FeiYongFaXi,BenJin
		var orderLen = orderAry.length;
		for ( var i = 0; i < orderLen; i++) {
			// 每项应还金额
			tmpAmt = $("#input_hidden_" + termNum + "_yingHuan" + orderAry[i] + "_input").val();
			tmpSum = CalcUtil.subtract(tmpSum, tmpAmt);
			if (ifSetZero) {
				$("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").val("0.00");
				$("#input_span_" + termNum + "_yingShou" + orderAry[i] + "_td_span").text("0.00");
			} else {
				if (tmpSum <= 0) {
					//因为tmpSum是负数或者0，因此这里用 add
					var spicalVal = CalcUtil.add(tmpAmt, tmpSum);
					$("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").val(spicalVal);
					$("#input_span_" + termNum + "_yingShou" + orderAry[i] + "_td_span").text(spicalVal);
					ifSetZero = true;
				} else {// 还是原来的 每项应还的金额
					$("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").val(tmpAmt);
					$("#input_span_" + termNum + "_yingShou" + orderAry[i] + "_td_span").text(tmpAmt);
				}
			}
		}

	});
}

/**
 * 减少金额后自动选择。
 * 
 * @param chgAmt
 *            减少的金额
 * @param curTermNo
 *            当前的旗号
 * @param curAmtAddr
 *            当前的金额位置 1、按照扣款顺序寻找最后一个勾选的金额 的位置坐标， 2、找到下一个位置，如果没有，需要自动增加一期
 *            3、自新的位置起逐次递减金额。本期全部减完，需要移动到下一期，如果没有下一期需要自动增加一期。
 * 
 */
function reduceAmtSelfSelected(chgAmt, curTermNo, curAmtAddr) {
	var address = findLastSltAddr();// 获取最后一个勾选的金额 的位置坐标
	var tmpNextAddr = findNextAmtAddr(address);
	if (tmpNextAddr[0] == curTermNo && tmpNextAddr[1] == curAmtAddr) {
		// 如果最后一个勾选金额是当前金额 的前一个 （证明你勾选的是最后后一个金额），需要从下一个金额开始
		address = tmpNextAddr;
	}

	while (chgAmt > 0) {
		address = findNextAmtAddr(address);// 获取下一个金额
		chgAmt = useAmtOfAddr(address[0], address[1], chgAmt);
	}

}

/**
 * 增加金额后自动选择。
 * 
 * @param chgAmt
 * @param termNo
 *            勾选的金额期号
 * @param amtAddr
 *            勾选的金额位置
 * 
 * 1、获取最后一个勾选金额的坐标 2、自新的位置起逐次放弃金额。知道全部金额完毕，或者全部放弃完毕
 */
function addAmtSelfSelected(chgAmt, termNo, amtAddr) {
	var fromAmt = chgAmt;
	var address = findLastSltAddr();// 获取最后一个勾选的金额 的位置坐标
	while (chgAmt > 0 && address != null && (address[0] != termNo || address[1] != amtAddr)) {
		chgAmt = giveupAmtOfAddr(address[0], address[1], chgAmt);
		address = findLastAmtAddr(address);// 获取上一个勾选金额
	}
	if (address[0] == termNo && address[1] == amtAddr) {
		var tmpVal = CalcUtil.subtract(fromAmt, chgAmt);
		$("#input_text_" + termNo + "_yingShou" + amtAddr + "_Text").val(tmpVal);
		$("#input_text_" + termNo + "_yingShou" + amtAddr + "_Text").attr("fromVal", tmpVal);
		$("#input_span_" + termNo + "_yingShou" + amtAddr + "_td_span").text(tmpVal);
	}
}

/**
 * 按照扣款顺序寻找最后一个金额勾选的位置。
 */
function findLastSltAddr() {
	var orderAry = payOrder.split(",");
	var termIndex = "1";
	var amtIndex = orderAry[0];
	var termNum = 1;
	var orderLen = orderAry.length;
	var tmpCheck = false;
	var tmpCnt = 0;
	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
		tmpCnt = 0;
		termNum = $(this).attr("termNum");
		for ( var i = 0; i < orderLen; i++) {
			// tmpCheck=$("#input_checkbox_"+termNum+"_yingShou"+orderAry[i]+"_input").prop("checked");
			if (tmpCheck) {
				tmpCnt = tmpCnt * 1 + 1;
				amtIndex = orderAry[i];
			}
		}
		if (tmpCnt > 0) {
			termIndex = termNum;
		}
		termNum++;

	});
	var res = new Array();
	res.push(termIndex);
	res.push(amtIndex);
	return res;

}
/**
 * 找到下一期
 * 
 * @param fromAddr
 *            开始位置
 */
function findNextAmtAddr(fromAddr) {
	var termNo = $("#yingShou_List_table_001 tr[id^='tr_']:last").attr("termNum");// 获取当前期（最后一期）的期号
	var orderAry = payOrder.split(",");
	var len = orderAry.length;
	var addrInfo = new Array();
	addrInfo.push(fromAddr[0]);
	addrInfo.push(fromAddr[1]);
    var i;
	if (termNo == addrInfo[0] && orderAry[len - 1] == addrInfo[1]) {
		// 如果开始位置就是最后一期的最后一个金额,需要添加一期
		var dueno = $("#input_hidden_loanNo").val();
		var nextPlans = getNextTermPayInfo(dueno, termNo, "1");
		addNewPlans(termNo, nextPlans);// 加载新的应还信息
		addrInfo[0] = addrInfo[0] * 1 + 1;
		addrInfo[1] = orderAry[0];
		// 默认全部不勾选
		for ( i = 0; i < len; i++) {
			$("#input_checkbox_" + addrInfo[0] + "_yingShou" + orderAry[i] + "_input").prop("checked", false);
		}

	} else {

		if (orderAry[len - 1] != addrInfo[1]) {
			// 如果不是最后一个金额 直接获取一个金额
			var tmpInd = 0;
			for ( i = 0; i < len; i++) {
				if (orderAry[i] == addrInfo[1]) {
					tmpInd = i;
					break;
				}
			}
			tmpInd++;
			addrInfo[1] = orderAry[tmpInd];
		} else {
			// 如果是最后一个金额 获取下一期的第一个
			addrInfo[0] = addrInfo[0] * 1 + 1;
			addrInfo[1] = orderAry[0];
		}

	}
	return addrInfo;
}
/**
 * 使用某个位置的金额
 * 
 * @param termNo
 *            期号
 * @param amdAddr
 *            金额位置
 * @param bal
 *            被减的金额
 * 
 * 首先选中该位置的金额 如果被减金额 小于 应还金额 ，修改实还金额为 被减金额 。 返回 被减金额-应还金额
 */
function useAmtOfAddr(termNo, amdAddr, bal) {
	var shouldAmt = $("#input_text_" + termNo + "_yingShou" + amdAddr + "_Text").val();
	if (bal * 1 < shouldAmt * 1) {
		$("#input_text_" + termNo + "_yingShou" + amdAddr + "_Text").val(bal);
		$("#input_text_" + termNo + "_yingShou" + amdAddr + "_Text").attr("fromVal", bal);
		$("#input_span_" + termNo + "_yingShou" + amdAddr + "_td_span").text(bal);
	}
	var resultVar = CalcUtil.subtract(bal, shouldAmt);
	return resultVar;
}

/**
 * 找到上一个金额位置
 * 
 * @param fromAddr
 *            开始位置
 */
function findLastAmtAddr(fromAddr) {

	var addrInfo = new Array();
	addrInfo.push(fromAddr[0]);
	addrInfo.push(fromAddr[1]);
	var orderAry = payOrder.split(",");
	var len = orderAry.length;
	if ("1" == addrInfo[0] && orderAry[len - 1] == addrInfo[1]) {
		// 如果开始位置就是第一期的第一个金额,
		return null;
	} else {
		if (addrInfo[0] > 1 && orderAry[0] != addrInfo[1]) {
			// 如果不是第一期，且不是第一个金额，直接获取上一个金额
			var tmpInd = 0;
			for ( var i = 0; i < len; i++) {
				if (orderAry[i] == addrInfo[1]) {
					tmpInd = i;
					break;
				}
			}
			tmpInd--;
			addrInfo[1] = orderAry[tmpInd];
		} else {
			// 如果是最后一个金额 获取下一期的第一个
			addrInfo[0] = addrInfo[0] * 1 - 1;
			addrInfo[1] = orderAry[len - 1];
		}

	}
	return addrInfo;
}
/**
 * 修改金额
 * 
 * @param termNo
 *            期号
 * @param amtAddr金额坐标
 * @author WD
 */
function modifyLiXiAmtFun(termNo, amtAddr) {
	var fromAmt = $("#input_text_" + termNo + "_yingShou" + amtAddr + "_Text").attr("fromVal");// 原始金额
	var newAmt = $("#input_text_" + termNo + "_yingShou" + amtAddr + "_Text").val();// 修改后的金额
	$("#input_span_" + termNo + "_yingShou" + amtAddr + "_td_span").text(newAmt);// 修改只读展示
	if (fromAmt * 1 < newAmt * 1) {
		// 实收金额 不能大于 应收金额
		alert(top.getMessage("NOT_FORM_TIME", {
			"timeOne" : '实收金额',
			"timeTwo" : '应收金额' + fromAmt + '元'
		}), 0);
		$("#input_text_" + termNo + "_yingShou" + amtAddr + "_Text").val(fromAmt);
		return;
	} else {
		$("#input_text_" + termNo + "_yingShou" + amtAddr + "_Text").css({
			"color" : "#0099ff"
		});
	}
	// 同步实际还款合计
	synchronizeLiXiRealAmt();
}
/**
 * 修改减免金额 实收金额减少
 * 
 * @param termNo
 *            期号
 * @param amtAddr
 *            金额坐标
 * @author WD
 */
function modifyFavorableLiXiAmt(termNo, amtAddr) {
	var fromAmt = $("#" + termNo + "_youHuiJine_input").attr("fromVal");// 原始金额
	var newAmt = $("#" + termNo + "_youHuiJine_input").val();// 修改后的金额
	if (fromAmt * 1 == newAmt * 1) {
		return;// 如果没有发生金额变化，直接返回。
	}
	var thisVal = $("#" + termNo + "_youHuiJine_input").val();
	// 实际还的 利息和罚息
	var shishouyuqili = $("#input_hidden_" + termNo + "_yingHuanYuQiLiXi_input").val().replace(/,/g, "");
	// 实际还的 利息和罚息 
	var shishoufulilixi = $("#input_hidden_" + termNo + "_yingHuanFuLiLiXi_input").val().replace(/,/g, "");
	
	var shishouweiyujin = $("#input_hidden_" + termNo + "_yingHuanYuQiWeiYueJin_input").val().replace(/,/g, "");
	var shishoulixi = $("#input_hidden_" + termNo + "_yingHuanLiXi_input").val().replace(/,/g, "");
	var shishoufeiyong = $("#input_hidden_" + termNo + "_yingHuanFeiYong_input").val().replace(/,/g, "");
	var maxVal = 0.00;
	maxVal = CalcUtil.add(maxVal, shishoulixi);
	maxVal = CalcUtil.add(maxVal, shishouyuqili);
	maxVal = CalcUtil.add(maxVal, shishoufulilixi);
	maxVal = CalcUtil.add(maxVal, shishouweiyujin);
	maxVal = CalcUtil.add(maxVal, shishoufeiyong);
	if (thisVal * 1 > maxVal * 1) {
		alert(top.getMessage("NOT_FORM_TIME", {
			"timeOne" : '减免金额',
			"timeTwo" : maxVal + '元'
		}), 0);
		$("#" + termNo + "_youHuiJine_input").val("0.00");
		return;
	}
	// 同步实收息费总额
	var shiShouZongJi = $("#shiShouZongJi").val().replace(/,/g, "");
	// 同步优惠金额
	synchronizeLiXiYouhuiAmt(termNo);
	// 同步实际还款合计
    synchronizeLiXiRealAmt();

	$("#" + termNo + "_youHuiJine_input").attr("fromVal", newAmt); // 设置原始值为当前值
}
/**
 * 同步优惠金额
 * 
 * @author WD
 */
function synchronizeLiXiYouhuiAmt(termNo) {
	var returnPlanPoint = $("#returnPlanPoint_hidden").val();
	var totalYouhuiSum = getLiXiYouhuiTotalAmt();
	var totalYouhuiSumFormat = CalcUtil.formatMoney(totalYouhuiSum,returnPlanPoint);
	$("#shiShouYouHuiZongJi").text(totalYouhuiSumFormat);
	$("#shiShouYouHuiZongJi_input_text").val(totalYouhuiSumFormat);
	$("#item_sum_youhui").text(totalYouhuiSumFormat);
}
/**
 * 设置优惠金额为零
 */
function setYouhuiZero() {
	$("input[id$='_youHuiJine_input']").each(function() {
		$(this).val("0.00");

	});
	synchronizeYouhuiAmt(null);

}
/**
 * 同步实际还信息
 * 
 * @author WD
 */
function synchronizeLiXiRealAmt() {
	var returnPlanPoint = $("#returnPlanPoint_hidden").val();
	 var cmpdRateType = $("#cmpdRateType_input_hidden").val();//复利利息是否收取：0-不收取、1-收取 
	var tmpBenjinSum = 0;
	var tmpLixiSum = 0;
	var tmpFaxiSum = 0;
	var tmpYuQiLiXiSum = 0;
	var tmpFuLiLiXiSum = 0;
	var tmpFuLiLiXiPartSum=0;
	var tmpWeiyuejinSum = 0;
	var tmpFeiyongSum = 0;
	var tmpFeiyongFaXiSum = 0;
	var tmpYouhuiAmt = 0;
	var tmpVal = 0;
	var termNum = '1';
	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
		termNum = $(this).attr("termNum");// 获取期号
		
		// 计算逾期利息
		tmpVal = $("#input_text_" + termNum + "_yingShouYuQiLiXi_Text").val();
		tmpYuQiLiXiSum = CalcUtil.add(tmpYuQiLiXiSum, tmpVal);

		// 计算复利利息
		tmpVal = $("#input_text_" + termNum + "_yingShouFuLiLiXi_Text").val();
		tmpFuLiLiXiSum = CalcUtil.add(tmpFuLiLiXiSum, tmpVal);
		
		// 计算复利利息浮动部分
		tmpVal = $("#input_text_" + termNum + "_yingShouFuLiLiXiPart_Text").val();
		tmpFuLiLiXiPartSum = CalcUtil.add(tmpFuLiLiXiPartSum, tmpVal);
		
		// 计算违约金
		tmpVal = $("#input_text_" + termNum + "_yingShouYuQiWeiYueJin_Text").val();
		tmpWeiyuejinSum = CalcUtil.add(tmpWeiyuejinSum, tmpVal);
		
		// 计算利息
		tmpVal = $("#input_text_" + termNum + "_yingShouLiXi_Text").val();
		tmpLixiSum = CalcUtil.add(tmpLixiSum, tmpVal);
		
		// 计算费用
		tmpVal = $("#input_text_" + termNum + "_yingShouFeiYong_Text").val();
		tmpFeiyongSum = CalcUtil.add(tmpFeiyongSum, tmpVal);
		
		// 计算费用罚息
		tmpVal = $("#input_text_" + termNum + "_yingShouFeiYongFaXi_Text").val();
		tmpFeiyongFaXiSum = CalcUtil.add(tmpFeiyongFaXiSum, tmpVal);

		// 计算优惠减免
		tmpVal = $("#" + termNum + "_youHuiJine_input").val();
		tmpYouhuiAmt = CalcUtil.add(tmpYouhuiAmt, tmpVal);
	});
	var tmpSumVal = 0.00;// 实收合计
	tmpSumVal = CalcUtil.add(tmpSumVal, tmpLixiSum);
	tmpSumVal = CalcUtil.add(tmpSumVal, tmpYuQiLiXiSum);
	tmpSumVal = CalcUtil.add(tmpSumVal, tmpFuLiLiXiSum);
	tmpSumVal = CalcUtil.add(tmpSumVal, tmpFuLiLiXiPartSum);
	tmpSumVal = CalcUtil.add(tmpSumVal, tmpFeiyongSum);
	tmpSumVal = CalcUtil.add(tmpSumVal, tmpFeiyongFaXiSum);
	tmpSumVal = CalcUtil.add(tmpSumVal, tmpWeiyuejinSum);
	// 减去优惠
	var allItemSumVar = CalcUtil.subtract(tmpSumVal,tmpYouhuiAmt);
	$("#shiShouLiXi_input_text").val(tmpLixiSum);
	var tmpLixiSumFormat = CalcUtil.formatMoney(tmpLixiSum, returnPlanPoint);
	$("#shiShouLiXi").text(tmpLixiSumFormat);
	$("#item_sum_lixi").text(tmpLixiSumFormat);
	//逾期利息
	$("#shiShouYuQiLiXi_input_text").val(tmpYuQiLiXiSum);
	var tmpYuQiLiXiSumFormat = CalcUtil.formatMoney(tmpYuQiLiXiSum, returnPlanPoint);
	$("#shiShouYuQiLiXi").text(tmpYuQiLiXiSumFormat);
	$("#item_sum_yuqilixi").text(tmpYuQiLiXiSumFormat);
	//复利利息
	$("#shiShouFuLiLiXi_input_text").val(tmpFuLiLiXiSum);
	var tmpFuLiLiXiSumFormat = CalcUtil.formatMoney(tmpFuLiLiXiSum, returnPlanPoint);
	$("#shiShouFuLiLiXi").text(tmpFuLiLiXiSumFormat);
	$("#item_sum_fulilixi").text(tmpFuLiLiXiSumFormat);
	
	//复利利息利率浮动
	$("#shiShouFuLiLiXiPart_input_text").val(tmpFuLiLiXiPartSum);
	var tmpFuLiLiXiPartSumFormat = CalcUtil.formatMoney(tmpFuLiLiXiPartSum, returnPlanPoint);
	$("#shiShouFuLiLiXiPart").text(tmpFuLiLiXiPartSumFormat);
	$("#item_sum_fulilixipart").text(tmpFuLiLiXiPartSumFormat);
	//费用
	$("#shiShouFeiYong_input_text").val(tmpFeiyongSum);
	var tmpFeiyongSumFormat = CalcUtil.formatMoney(tmpFeiyongSum, returnPlanPoint);
	$("#shiShouFeiYong").text(tmpFeiyongSumFormat);
	$("#item_sum_feiyong").text(tmpFeiyongSumFormat);
	
	//费用罚息
	$("#shiShouFeiYongFaXi_input_text").val(tmpFeiyongFaXiSum);
	var tmpFeiyongFaXiSumFormat = CalcUtil.formatMoney(tmpFeiyongFaXiSum, returnPlanPoint);
	$("#shiShouFeiYongFaXi").text(tmpFeiyongFaXiSumFormat);
	$("#item_sum_feiyongfaxi").text(tmpFeiyongFaXiSumFormat);
	//违约金
	$("#shiShouYuQiWeiYueJin_input_text").val(tmpWeiyuejinSum);
	var tmpWeiyuejinSumFormat = CalcUtil.formatMoney(tmpWeiyuejinSum, returnPlanPoint);
	$("#item_sum_wyj").text(tmpWeiyuejinSumFormat);
	$("#shiShouYuQiWeiYueJin").text(tmpWeiyuejinSumFormat);

    //实收息费总计
    $("#shiShouZongJi").val(allItemSumVar);
    $("#shiShouZongJi_input_text").val(allItemSumVar);

	$("#all_item_sum").text(CalcUtil.formatMoney(allItemSumVar, returnPlanPoint));

}
/**
 * 还款明细调整
 * 
 * @author WD
 */
function adjustLiXiPayDetail() {
    var cmpdRateType = $("#cmpdRateType_input_hidden").val();//复利利息是否收取：0-不收取、1-收取 
	var termNum = "1";
	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
		termNum = $(this).attr("termNum");// 获取期号
		var orderAry = payOrder.split(",");
		var orderLen = orderAry.length;
		for ( var i = 0; i < orderLen; i++) {
			$("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").show();
			$("#input_shouldPay_" + termNum + "_yingShou" + orderAry[i]).show();
			$("#input_span_" + termNum + "_yingShou" + orderAry[i] + "_td_span").hide();
		}
		$("#" + termNum + "_youHuiJine_input").prop("disabled", false);
	});
}

/**
 * 还款页面金额控制 金额控制
 */
function funcCheckRepay(obj) {
	// 验证数据类型
	func_uior_valType(obj);
}
