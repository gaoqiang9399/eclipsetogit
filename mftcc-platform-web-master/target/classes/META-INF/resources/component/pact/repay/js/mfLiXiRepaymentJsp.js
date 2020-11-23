/**
 * 利息还款操作方法 1 验证还款金额 2 还款
 * @author WD
 */
window.ajaxLiXiRepayment = function(obj, callback) {
	var flag = submitJsMethod(obj, '');
	if (flag) {
		var isWrong=false;
		// 验证是否能够还款 验证方法
		isWrong=ifCanLiXiReapayment();
		if (isWrong) {// 不满足条件 不允许还款
			return;
		}
		var url = $(obj).attr("action");
		// 获取应收列表List的JSON字符串
		var loanReceivableBeanListRepaymentVar = getLiXiTianXieYingShouListInputTextNew();
		var dataParam = getMfLiXiRepaymentJsonStr();
		var listParam = JSON.stringify(loanReceivableBeanListRepaymentVar);
		if (loanReceivableBeanListRepaymentVar.length == 0) {
			alert(top.getMessage("FIRST_SELECT_ITEM", ""), 0);
		} else {
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					ajaxList : listParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
					LoadingAnimate.start();
				},
				success : function(data) {
					if (data.flag == "success") {
						$.each(data, function(name, value) {
							setFormEleValue(name, value);// 调用公共js文件的方法表单赋值
						});
						top.flag = true;
						window.top.alert(data.msg, 3);
//						 var url=webPath+"/mfBusPact/getPactFincById?fincId="+$(fincId).val()+"&appId="+appId;
//							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION", "还款"), 0);
				},
				complete : function() {
					LoadingAnimate.stop();
				}
			});
		}

	}
};

/**
 * 正常还款试算
 */
window.ajaxRepaymentTrial = function(obj, callback) {
	var flag = submitJsMethod(obj, '');
	if (flag) {
		var lsbqHuanBenFlag=$("#lsbqHuanBenFlag_input_hidden").val();
		var isWrong=false;
		if(lsbqHuanBenFlag == 3 ){//利随本清还款 并且是 按照还款本金还款
			isWrong=ifCanLiXiReapaymentByLsbq();
		}else{
			// 验证是否能够还款 验证方法
			isWrong=ifCanLiXiReapayment();
		}
		if (isWrong) {// 不满足条件 不允许还款
			return;
		}
		var url = webPath+"/mfRepayPlanTrial/repaymentTrialAjax";
		// 获取应收列表List的JSON字符串
		var loanReceivableBeanListRepaymentVar = getLiXiTianXieYingShouListInputTextNew();
		var dataParam = getMfLiXiRepaymentJsonStr();
		var listParam = JSON.stringify(loanReceivableBeanListRepaymentVar);
		if (loanReceivableBeanListRepaymentVar.length == 0) {
			alert(top.getMessage("FIRST_SELECT_ITEM", ""), 0);
		} else {
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					ajaxList : listParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
					LoadingAnimate.start();
				},
				success : function(data) {
					if (data.flag == "success") {
						$.each(data, function(name, value) {
							setFormEleValue(name, value);// 调用公共js文件的方法表单赋值
						});
						top.flag = true;
						window.top.alert(data.msg, 3);
					} else {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION", "还款试算"), 0);
				},
				complete : function() {
					LoadingAnimate.stop();
				}
			});
		}
	}
}

/**
 * 三方还款操作方法 1 验证还款金额 2 还款
 * 
 * @author WD
 */
window.ajaxThirdRepayment = function(obj, callback) {

	var flag = submitJsMethod(obj, '');
	if (flag) {
		var lsbqHuanBenFlag=$("#lsbqHuanBenFlag_input_hidden").val();
		var isWrong=false;
		if(lsbqHuanBenFlag == 3 ){//利随本清还款 并且是 按照还款本金还款
			isWrong=ifCanLiXiReapaymentByLsbq();
		}else{
			// 验证是否能够还款 验证方法
			isWrong=ifCanLiXiReapayment();
		}
		if (isWrong) {// 不满足条件 不允许还款
			return;
		}
		// 获取应收列表List的JSON字符串
		var loanReceivableBeanListRepaymentVar = getLiXiTianXieYingShouListInputTextNew();
		var dataParam = getMfLiXiRepaymentJsonStr();
		var listParam = JSON.stringify(loanReceivableBeanListRepaymentVar);
		if (loanReceivableBeanListRepaymentVar.length == 0) {
			alert(top.getMessage("FIRST_SELECT_ITEM", ""), 0);
		} else {
			jQuery.ajax({
                url : webPath+'/mfRepayment/thirdRepaymentAjax',
				data : {
					ajaxData : dataParam,
					ajaxList : listParam
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
					LoadingAnimate.start();
				},
				success : function(data) {
					if (data.code == "0000") {
						$.each(data, function(name, value) {
							setFormEleValue(name, value);// 调用公共js文件的方法表单赋值
						});
						top.flag = true;
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION", "还款"), 0);
				},
				complete : function() {
					LoadingAnimate.stop();
				}
			});
		}

	}
};

/**
 * 三方还款操作方法 1 验证还款金额 2 还款
 *
 * @author WD
 */
window.ajaxThirdRepayment_hm = function(obj, callback) {
    var flag = submitJsMethod(obj, '');
    if (flag) {
        var lsbqHuanBenFlag=$("#lsbqHuanBenFlag_input_hidden").val();
        var isWrong=false;
        if(lsbqHuanBenFlag == 3 ){//利随本清还款 并且是 按照还款本金还款
            isWrong=ifCanLiXiReapaymentByLsbq();
        }else{
            // 验证是否能够还款 验证方法
            isWrong=ifCanLiXiReapayment();
        }
        if (isWrong) {// 不满足条件 不允许还款
            return;
        }
        // 获取应收列表List的JSON字符串
        var loanReceivableBeanListRepaymentVar = getLiXiTianXieYingShouListInputTextNew();
        var dataParam = getMfLiXiRepaymentJsonStr();
        var listParam = JSON.stringify(loanReceivableBeanListRepaymentVar);
        if (loanReceivableBeanListRepaymentVar.length == 0) {
            alert(top.getMessage("FIRST_SELECT_ITEM", ""), 0);
        } else {
            jQuery.ajax({
                url : webPath+'/repay/repaySingle',
                data : {
                    ajaxData : dataParam,
                    ajaxList : listParam,
                    compensatoryNo : $("#compensatoryNo").val()
                },
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                    LoadingAnimate.start();
                },
                success : function(data) {
                    if (data.code == "0000") {
                        $.each(data, function(name, value) {
                            setFormEleValue(name, value);// 调用公共js文件的方法表单赋值
                        });
                        top.flag = true;
                        window.top.alert(data.msg, 3);
                        myclose_click();
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    alert(top.getMessage("FAILED_OPERATION", "还款"), 0);
                },
                complete : function() {
                    LoadingAnimate.stop();
                }
            });
        }

    }
};

/**
 * 验证页面相关金额是否符合还款要求 返回 false 能还款 true 不能还款
 * 
 * @author WD
 */
function ifCanLiXiReapayment() {
	// 包括当前期之后的本金
	var totalPayVal = $("#totalPay_input_hidden").val();
	var isWrong = false;
	// 获取实收总额
	var shiShouZongJi = $("#shiShouZongJi").val().replace(/,/g, "");
	// 获取本次冲抵金额
	var benCiCongDi = "0.00";
	// 还款方式
	var repayTypeVal = $("#repayType_input_hidden").val();
	//优惠总额
	var youhuizongZongEr = $("#shiShouYouHuiZongJi_input_text").val();
    var sszj = CalcUtil.add(shiShouZongJi, benCiCongDi);
    sszj=CalcUtil.add(sszj, youhuizongZongEr);
    var errormsg = '实还总额';
    errormsg = $("#shiShouZongJi").parents("td").prev().text();
    if(errormsg == null || errormsg == "" || typeof(errormsg) == "undefined") {
    	errormsg = '实还总额';
    }
	if (sszj <= 0) {// 实收总额+优惠总额 不能为0
		isWrong = true;
		alert(top.getMessage("NOT_SMALL_TIME", {
			"timeOne" : errormsg,
			"timeTwo" : '0.00'
		}), 0);
		return isWrong;
	}
    var timeOne,timeTwo;
	// 实收总额+优惠合计 不能大于 剩余应还总额 youhuizongZongEr
	if (shiShouZongJi * 1 + youhuizongZongEr * 1 > totalPayVal * 1) {
		timeOne = "实收总额,优惠合计";
		timeTwo = "该笔数据应还总额";
		isWrong = true;
		alert(top.getMessage("NOT_FORM_TIME", {
			"timeOne" : timeOne,
			"timeTwo" : timeTwo
		}), 0);
		return isWrong;
	}

    // 实收总额+优惠合计 不能大于 剩余应还总额 youhuizongZongEr
	var yingShouZongJiAll = $("#yingShouZongJiAll_input_text").val()
    if (shiShouZongJi * 1 + youhuizongZongEr * 1 < yingShouZongJiAll * 1) {
        timeOne = "实收总额,优惠合计";
        timeTwo = "该笔数据应还总额";
        isWrong = true;
        alert(top.getMessage("NOT_SMALL_TIME", {
            "timeOne" : timeOne,
            "timeTwo" : timeTwo
        }), 0);
        return isWrong;
    }


	// 判断各期的优惠金额是否 大于 各期的罚息，违约金，利息，费用之和
	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
		if (!isWrong) {
			var tmpVal = 0.00;
			var tmpSumVal = 0.00;// 实收合计
			var termNum = $(this).attr("termNum");// 获取期号
			// 计算逾期利息
			tmpVal = $("#input_text_" + termNum + "_yingShouYuQiLiXi_Text").val();
			tmpSumVal = CalcUtil.add(tmpSumVal, tmpVal);
			
			// 计算复利利息
			tmpVal = $("#input_text_" + termNum + "_yingShouFuLiLiXi_Text").val();
			tmpSumVal = CalcUtil.add(tmpSumVal, tmpVal);
	
			// 计算复利利息利率浮动
			tmpVal = $("#input_text_" + termNum + "_yingShouFuLiLiXiPart_Text").val();
			tmpSumVal = CalcUtil.add(tmpSumVal, tmpVal);
			
			// 计算违约金
			tmpVal = $("#input_text_" + termNum + "_yingShouYuQiWeiYueJin_Text").val();
			tmpSumVal = CalcUtil.add(tmpSumVal, tmpVal);
			// 计算利息
			tmpVal = $("#input_text_" + termNum + "_yingShouLiXi_Text").val();
			tmpSumVal = CalcUtil.add(tmpSumVal, tmpVal);
			// 计算费用
			tmpVal = $("#input_text_" + termNum + "_yingShouFeiYong_Text").val();
			tmpSumVal = CalcUtil.add(tmpSumVal, tmpVal);
			// 计算费用罚息
			tmpVal = $("#input_text_" + termNum + "_yingShouFeiYongFaXi_Text").val();
			tmpSumVal = CalcUtil.add(tmpSumVal, tmpVal);
			
			// 优惠金额
			var tmpYouhuiAmt = $("#" + termNum + "_youHuiJine_input").val();
			if (tmpYouhuiAmt * 1 > tmpSumVal * 1) {
				var timeOne = "第" + termNum + "期的优惠金额";
				var timeTwo = "第" + termNum + "期的罚息，违约金，利息，费用之和";
				// 实收金额 不能大于 应收金额
				isWrong = true;
				alert(top.getMessage("NOT_FORM_TIME", {
					"timeOne" : timeOne,
					"timeTwo" : timeTwo
				}), 0);
				return isWrong;
			}
		}
	});
	if (isWrong) {
		return isWrong;
	}

	// 判断 勾选金额明细和结余合计与实收总额,优惠合计 是否一致
	var shishouheji = $("#all_item_sum").text().replace(/,/g, "");// 实收合计
	var shishouzonge = $("#shiShouZongJi").val().replace(/,/g, "");// 实收总额
	// 输入的总金额
	var youhuizongji = $("#shiShouYouHuiZongJi_input_text").val();// 优惠总计

	if (shishouheji * 1  != shishouzonge * 1 ) {// 勾选金额明细与实收总额,优惠合计
		// 是否一致
		var money1 = "应收金额明细合计";
		var money2 = "实收总额,优惠合计"
		isWrong = true;
		alert(top.getMessage("NOT_MONEY_SAME", {
			"money1" : money1,
			"money2" : money2
		}), 0);
		return isWrong;
	}
	return isWrong;
}

/**
 * 获取填写的应收列表List的JSON字符串
 * 
 * @author WD
 */
function getLiXiTianXieYingShouListInputTextNew() {
	var loanReceivableBeanListInputTextVar = new Array();
	//是否是利率浮动分开 为利率浮动时 逾期利率和复利利率保存到数据库的值：0-正常年利率加浮动利率值（正常存放 存合计值 存1+0.5计算出的值）、1-浮动利率值（只存浮动值 存0.5计算出的值）
	var overCmpdFltSaveflag = $("#overCmpdFltSaveflag_input_text").val();
	//费用罚息是否展示 0 不展示 1 展示
	var feiYongFaXiFlag = $("#feiYongFaXiFlag_input_text").val();
	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function(i, val) {
		var loanReceivableBean = new Object();
		var termNumVar = $(this).attr("termNum");
		// 应收本金
		var yingShouBenJin = $("#input_text_" + termNumVar + "_yingShouBenJin_Text").val();
        yingShouBenJin="0.00";
		loanReceivableBean.yingShouBenJin = yingShouBenJin.replace(/,/g, "");
		
		// 应收利息
		var yingShouLiXi = $("#input_text_" + termNumVar + "_yingShouLiXi_Text").val();
		loanReceivableBean.yingShouLiXi = yingShouLiXi.replace(/,/g, "");
		
		// 应收逾期利息
		var yingShouYuQiLiXi = $("#input_text_" + termNumVar + "_yingShouYuQiLiXi_Text").val();
		loanReceivableBean.yingShouYuQiLiXi = yingShouYuQiLiXi.replace(/,/g, "");
		
		// 应收复利利息
		var yingShouFuLiLiXi = $("#input_text_" + termNumVar + "_yingShouFuLiLiXi_Text").val();
		loanReceivableBean.yingShouFuLiLiXi = yingShouFuLiLiXi.replace(/,/g, "");
		
		// 应收复利利息利率浮动
		var yingShouFuLiLiXiPart = $("#input_text_" + termNumVar + "_yingShouFuLiLiXiPart_Text").val();
		loanReceivableBean.yingShouFuLiLiXiPart = yingShouFuLiLiXiPart.replace(/,/g, "");
		
		// 应收罚息
		var yingShouFaXi =  CalcUtil.add(yingShouYuQiLiXi, yingShouFuLiLiXi);
		loanReceivableBean.yingShouFaXi = yingShouFaXi;
		
		// 应收费用
		var yingShouFeiYong = $("#input_text_" + termNumVar + "_yingShouFeiYong_Text").val();
		loanReceivableBean.yingShouFeiYong = yingShouFeiYong.replace(/,/g, "");
		
		// 应收费用罚息
		var yingShouFeiYongFaXi = $("#input_text_" + termNumVar + "_yingShouFeiYongFaXi_Text").val();
		loanReceivableBean.yingShouFeiYongFaXi = yingShouFeiYongFaXi.replace(/,/g, "");
		
		// 应收逾期违约金
		var yingShouYuQiWeiYueJin = $("#input_text_" + termNumVar + "_yingShouYuQiWeiYueJin_Text").val();
		loanReceivableBean.yingShouYuQiWeiYueJin = yingShouYuQiWeiYueJin.replace(/,/g, "");
		
		// 应收优惠利息
		var youHuiJine = $("#" + termNumVar + "_youHuiJine_input").val();
		loanReceivableBean.youHuiJine = youHuiJine.replace(/,/g, "");
		// benQiYingShouZongJi本期应收总计 （本期应该收到的钱）
		var benQiYingShouZongJi = $("#input_hidden_" + termNumVar + "_benQiYingShouZongJi_input").val();
        benQiYingShouZongJi = benQiYingShouZongJi.replace(/,/g, "");
		loanReceivableBean.benQiYingShouZongJi = benQiYingShouZongJi;
		// outFlag标示
		var outFlag = $("#input_hidden_" + termNumVar + "_outFlag_input").val();
		loanReceivableBean.outFlag = outFlag;
		
		// 每期returnPlanId
		var returnPlanId = $("#input_hidden_" + termNumVar + "_returnPlanId_input").val();
		loanReceivableBean.returnPlanId = returnPlanId;
		
		// 期号
		loanReceivableBean.termNum = termNumVar;
		
		// 借据号
		loanReceivableBean.fincId = $("#fincId").val();
		
		// 合同号
		loanReceivableBean.pactId = $("#pactId").val();
		
		// 应还信息
		// 应还逾期利息
		var yingHuanYuQiLiXi = $("#input_hidden_" + termNumVar + "_yingHuanYuQiLiXi_input").val();
		loanReceivableBean.yingHuanYuQiLiXi = yingHuanYuQiLiXi.replace(/,/g, "");
		
		// 应还复利利息
		var yingHuanFuLiLiXi = $("#input_hidden_" + termNumVar + "_yingHuanFuLiLiXi_input").val();
		loanReceivableBean.yingHuanFuLiLiXi = yingHuanFuLiLiXi.replace(/,/g, "");
		
		// 应还复利利息 利率浮动 0.5 的部分
		var yingHuanFuLiLiXiPart = $("#input_hidden_" + termNumVar + "_yingHuanFuLiLiXiPart_input").val();
		loanReceivableBean.yingHuanFuLiLiXiPart = yingHuanFuLiLiXiPart.replace(/,/g, "");
		
	    //应还罚息
		var yingHuanFaXi =  CalcUtil.add(yingHuanYuQiLiXi, yingHuanFuLiLiXi);
		loanReceivableBean.yingHuanFaXi = yingHuanFaXi;

		var yingHuanYuQiWeiYueJin = $("#input_hidden_" + termNumVar + "_yingHuanYuQiWeiYueJin_input").val();
		loanReceivableBean.yingHuanYuQiWeiYueJin = yingHuanYuQiWeiYueJin.replace(/,/g, "");

		var yingHuanFeiYong = $("#input_hidden_" + termNumVar + "_yingHuanFeiYong_input").val();
		loanReceivableBean.yingHuanFeiYong = yingHuanFeiYong.replace(/,/g, "");
        var yingHuanFeiYongFaXi="0.00";
        if(feiYongFaXiFlag==1){
        	yingHuanFeiYongFaXi = $("#input_hidden_" + termNumVar + "_yingHuanFeiYongFaXi_input").val();
        }
        loanReceivableBean.yingHuanFeiYongFaXi = yingHuanFeiYongFaXi.replace(/,/g, "");
        
		var yingHuanLixi = $("#input_hidden_" + termNumVar + "_yingHuanLiXi_input").val();
		loanReceivableBean.yingHuanLixi = yingHuanLixi.replace(/,/g, "");
		//overCmpdFltSaveflag 为1  时 利息=正常利息+逾期利息（利率浮动为 1 计算出的值）
		var yingHuanLiXiNormPart="0.00";
		var yingHuanLiXiOverPart="0.00";
		if(overCmpdFltSaveflag==1){
			yingHuanLiXiNormPart=$("#input_hidden_" + termNumVar + "_yingHuanLiXiNormPart_input").val();
			yingHuanLiXiOverPart=$("#input_hidden_" + termNumVar + "_yingHuanLiXiOverPart_input").val();
		}
		loanReceivableBean.yingHuanLiXiNormPart = yingHuanLiXiNormPart.replace(/,/g, "");
		loanReceivableBean.yingHuanLiXiOverPart = yingHuanLiXiOverPart.replace(/,/g, "");
		
		var yingHuanBenjin = $("#input_hidden_" + termNumVar + "_yingHuanBenJin_input").val();
		loanReceivableBean.yingHuanBenjin = yingHuanBenjin.replace(/,/g, "");

		var yingShouSumVar = 0.00;
		yingShouSumVar = CalcUtil.add(yingShouSumVar, loanReceivableBean.yingShouBenJin);
		yingShouSumVar = CalcUtil.add(yingShouSumVar, loanReceivableBean.yingShouLiXi);
		yingShouSumVar = CalcUtil.add(yingShouSumVar, loanReceivableBean.yingShouFeiYong);
		yingShouSumVar = CalcUtil.add(yingShouSumVar, loanReceivableBean.yingShouFeiYongFaXi);
		yingShouSumVar = CalcUtil.add(yingShouSumVar, loanReceivableBean.yingShouYuQiLiXi);
		yingShouSumVar = CalcUtil.add(yingShouSumVar, loanReceivableBean.yingShouFuLiLiXi);
		yingShouSumVar = CalcUtil.add(yingShouSumVar, loanReceivableBean.yingShouFuLiLiXiPart);
		yingShouSumVar = CalcUtil.add(yingShouSumVar, loanReceivableBean.yingShouYuQiWeiYueJin);
		if (yingShouSumVar*1 > 0*1) {
			loanReceivableBeanListInputTextVar.push(loanReceivableBean);
		}
	});
	return loanReceivableBeanListInputTextVar;
}

/**
 * 获取还款页面 表单相关字段信息
 * 
 * @author WD
 * @returns
 */
function getMfLiXiRepaymentJsonStr() {
	var datas = [];
	var repaymentObj = {};
	// 借据号
	repaymentObj.name = "fincId";
	repaymentObj.value = $("#fincId").val();
	datas.push(repaymentObj);
	// 合同号
	repaymentObj = {};
	repaymentObj.name = "pactId";
	repaymentObj.value = $("#pactId").val();
	datas.push(repaymentObj);
	// 还款日期
	repaymentObj = {};
	repaymentObj.name = "repayDate";
	repaymentObj.value = $("#systemDateLong").val();
	datas.push(repaymentObj);
	// 实收本金
	repaymentObj = {};
	repaymentObj.name = "shiShouBenJin";
	// repaymentObj.value = $("#shiShouBenJin_input_text").val();
	repaymentObj.value = "0.00";
	datas.push(repaymentObj);
	// 实收本金的利息
	repaymentObj = {};
	repaymentObj.name = "shiShouLiXi";
	repaymentObj.value = $("#shiShouLiXi_input_text").val();
	datas.push(repaymentObj);
	
	// 实收逾期利息
	repaymentObj = {};
	repaymentObj.name = "shiShouYuQiLiXi";
	repaymentObj.value = $("#shiShouYuQiLiXi_input_text").val();
	datas.push(repaymentObj);
	
	// 实收复利利息
	repaymentObj = {};
	repaymentObj.name = "shiShouFuLiLiXi";
	repaymentObj.value = $("#shiShouFuLiLiXi_input_text").val();
	datas.push(repaymentObj);
	
	// 实收复利利息浮动部分
	repaymentObj = {};
	repaymentObj.name = "shiShouFuLiLiXiPart";
	repaymentObj.value = $("#shiShouFuLiLiXiPart_input_text").val();
	datas.push(repaymentObj);
	
	// 实收罚息
	repaymentObj = {};
	repaymentObj.name = "shiShouFaXi";
	var yuqilixi= $("#shiShouYuQiLiXi_input_text").val();
	var fulilixi= $("#shiShouFuLiLiXi_input_text").val();
	repaymentObj.value = CalcUtil.add(yuqilixi, fulilixi);
	datas.push(repaymentObj);

	// 实收逾期时收取的违约金
	repaymentObj = {};
	repaymentObj.name = "shiShouYuQiWeiYueJin";
	repaymentObj.value = $("#shiShouYuQiWeiYueJin_input_text").val();
	datas.push(repaymentObj);
	// 实收费用
	repaymentObj = {};
	repaymentObj.name = "shiShouFeiYong";
	repaymentObj.value = $("#shiShouFeiYong_input_text").val();
	datas.push(repaymentObj);
	// 实收费用罚息
	repaymentObj = {};
	repaymentObj.name = "shiShouFeiYongFaXi";
	repaymentObj.value = $("#shiShouFeiYongFaXi_input_text").val();
	datas.push(repaymentObj);
	// 实收优惠总计
	repaymentObj = {};
	repaymentObj.name = "shiShouYouHuiZongJi";
	repaymentObj.value = $("#shiShouYouHuiZongJi_input_text").val();
	datas.push(repaymentObj);
	// 应收总额
	repaymentObj = {};
	repaymentObj.name = "yingShouZongJiAll";
	repaymentObj.value = $("#yingShouZongJiAll_input_text").val();
	datas.push(repaymentObj);
	// 实收总额
	repaymentObj = {};
	repaymentObj.name = "shiShouZongJi";
	repaymentObj.value = $("#shiShouZongJi_input_text").val().replace(/,/g, "");
	datas.push(repaymentObj);
	// 本次结余
	repaymentObj = {};
	repaymentObj.name = "benCiJieYu";
	repaymentObj.value = $("#benCiJieYu_input_text").val();
	datas.push(repaymentObj);
	// 本次冲抵
	repaymentObj = {};
	repaymentObj.name = "benCiChongDi";
	repaymentObj.value = $("#benCiChongDi_input_text").val();
	datas.push(repaymentObj);
	// 客户号
	repaymentObj = {};
	repaymentObj.name = "cusNo";
	repaymentObj.value = $("#cusNo_input_hidden").val();
	datas.push(repaymentObj);
	// 客户名称
	repaymentObj = {};
	repaymentObj.name = "cusName";
	repaymentObj.value = $("#cusName_input_hidden").val();
	datas.push(repaymentObj);
	//项目名称
	repaymentObj = {};
	repaymentObj.name = "appName";
	repaymentObj.value = $("#appName_input_hidden").val();
	datas.push(repaymentObj);
	//借据余额
	repaymentObj = {};
	repaymentObj.name = "loanBal";
	repaymentObj.value = $("#loanBal_input_hidden").val();
	datas.push(repaymentObj);
	//还款方式
	repaymentObj = {};
	repaymentObj.name = "ext1";
	repaymentObj.value = $("#onlinePaymentMethod").val()?$("#onlinePaymentMethod").val():"";
	datas.push(repaymentObj);
	//还款说明
	repaymentObj = {};
	repaymentObj.name = "remark";
	repaymentObj.value = $("#remark").val()?$("#remark").val():"";
	datas.push(repaymentObj);
	//利随本清还本标志
	repaymentObj = {};
	repaymentObj.name = "lsbqHuanBenFlag";
	repaymentObj.value = $("#lsbqHuanBenFlag_input_hidden").val();
	datas.push(repaymentObj);
	
	//是否是利率浮动分开 为利率浮动时 逾期利率和复利利率保存到数据库的值：0-正常年利率加浮动利率值（正常存放 存合计值 存1+0.5计算出的值）、1-浮动利率值（只存浮动值 存0.5计算出的值）
	repaymentObj = {};
	repaymentObj.name = "overCmpdFltSaveflag";
	repaymentObj.value = $("#overCmpdFltSaveflag_input_text").val();
	//费用罚息是否展示 0 不展示 1 展示
	repaymentObj = {};
	repaymentObj.name = "feiYongFaXiFlag";
	repaymentObj.value = $("#feiYongFaXiFlag_input_text").val();
	
	datas.push(repaymentObj);

	var repaymentJsonStr = JSON.stringify(datas);
	return repaymentJsonStr;
};




/**
 * 利随本清 验证页面相关金额是否符合还款要求 返回 false 能还款 true 不能还款
 * 
 * @author WD
 */
function ifCanLiXiReapaymentByLsbq() {
	var isWrong=false;
	// 获取利随本清还本金额
	var huanKuanBenJin=$("#huanKuanBenJin").val();
	//剩余本金 
	var shengYuBenJin=$("#shengYuBenJin_input_text").val();
	//还款日期
	var repayDate=$("#systemDateLong").val();
	//实收利息
	var shiShouLiXi=$("#shiShouLiXi_input_text").val();
	//实收总计
	var shiShouZongJi=$("#shiShouZongJi_input_text").val();
	//借据号
	var fincId=$("#fincId").val();
    var money1,money2;
	if(huanKuanBenJin*1<='0'){
   		money1="还款本金";
   		money2=0.00+"元";
		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		isWrong=true;
		return isWrong;
   	}
	
	if(shiShouZongJi*1<='0'){
   		money1="实收总计";
   		money2=0.00+"元";
		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		isWrong=true;
		return isWrong;
   	}
    // 利随本清还款本金 不能大于剩余本金 
    if(huanKuanBenJin*1>shengYuBenJin*1){
    	money1="利随本清还款本金";
   		money2="剩余本金："+shengYuBenJin+"元";
		alert(top.getMessage("NOT_FORM_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		$("#huanKuanBenJin").val("0.00");
		isWrong=true;
		return isWrong;
    }
    return isWrong;
}

