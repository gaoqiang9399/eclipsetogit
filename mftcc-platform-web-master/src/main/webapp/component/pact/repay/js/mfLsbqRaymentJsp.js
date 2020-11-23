/**
 * 利随本清还本金额变化 实收利息 和违约金都变化
 */
function shiShouBenJinByLsbqHuanKuanInputOnblur(){
	// 获取利随本清还本金额
	var huanKuanBenJin=$("#huanKuanBenJin").val().replace(/,/g, "");

	//剩余本金 
	var shengYuBenJin=$("#shengYuBenJin_input_text").val();
	//还款日期
	var repayDate=$("#systemDateLong").val();  
	//借据号
	var fincId=$("#fincId").val();
    var money1,money2;
	if(huanKuanBenJin*1<='0'){
   		money1="还款本金";
   		money2=0.00+"元";
		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		return isWrong;
   	}
	
    // 利随本清还款本金 不能大于剩余本金 
    if(huanKuanBenJin*1>shengYuBenJin*1){
    	money1="利随本清还款本金";
   		money2="剩余本金："+shengYuBenJin+"元";
		alert(top.getMessage("NOT_FORM_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		$("#huanKuanBenJin").val("0.00");
		$("#shiShouLiXi").text("0.00");
		$("#shiShouLiXi_input_text").val("0.00");
    	//计算实收总计
    	$("#shiShouZongJi").text("0.00");
    	$("#shiShouZongJi_input_text").val("0.00");
		return ;
    }
    //计算利随本清还款 利息
    $.ajax({
		url:webPath+'/mfRepayment/calcLiXiLsbqHuanKuanAjax.action',
		data:{"repayDate":repayDate,"fincId":fincId,"huanKuanBenJin":huanKuanBenJin},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},
		success:function(data){
			if(data.flag == "success"){
				//实收利息 shiShouLiXiFormat 和违约金 tiQianHuanKuanWeiYueJinFormat 重新赋值
				$("#shiShouLiXi").text(data.shiShouLiXiFormat);
				$("#shiShouLiXi_input_text").val(data.shiShouLiXi);
				$("#shiShouBenJin").text(data.shiShouBenJinFormat);
				$("#shiShouBenJin_input_text").val(data.shiShouBenJin);		
				// 计算实收总额
				calcshiShouZongJiCallByLsbq();
			}
		},error:function(data){
			 LoadingAnimate.stop();
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}
    // 计算实收总额
    function calcshiShouZongJiCallByLsbq() {
        //利随本清还本
    	var huanKuanBenJin=$("#huanKuanBenJin").val().replace(/,/g, "");
    	//实收利息
    	var shiShouLiXi=$("#shiShouLiXi_input_text").val();
    	//本次结余
    	var benCiJieYu=$("#benCiJieYu_input_text").val();
    	//本次冲抵
    	var benCiChongDi=$("#benCiChongDi_input_text").val();
    	//实收总计
    	var shiShouZongJi="0.00";
    	shiShouZongJi=huanKuanBenJin*1+shiShouLiXi*1+benCiJieYu*1-benCiChongDi*1
    	shiShouZongJi=round(shiShouZongJi*1,2);
    	//计算实收总计
    	$("#shiShouZongJi").text(shiShouZongJi);
    	$("#shiShouZongJi_input_text").val(shiShouZongJi);
    	//封装利随本清金额
    	checkAmtOneByOneLsbq();
    	//同步总计
    	synchronizeRealAmtByLsbq();
		// 进入编辑状态
    	adjustPayDetailByLsbq();
    }
    
    /**
     * 利随本清 按照还款顺序 计算相关金额
     * 
     * @param paySum
     *            实还总额
     * @author WD
     */
    function checkAmtOneByOneLsbq(){
    	//还款本金
	   	var benJin = $("#shiShouBenJin_input_text").val();
	   	//还款利息
	   	var liXi = $("#shiShouLiXi_input_text").val();
    	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
    		termNum = $(this).attr("termNum");// 获取期号
    		//"YuQiLiXi,FuLiLiXi,YuQiWeiYueJin,LiXi,FeiYong,BenJin"
    		$("#input_text_" + termNum + "_yingShou" + "YuQiLiXi" + "_Text").val("0.00");
			$("#input_span_" + termNum + "_yingShou" + "YuQiLiXi"+ "_td_span").text("0.00");
			
    		$("#input_text_" + termNum + "_yingShou" + "FuLiLiXi" + "_Text").val("0.00");
			$("#input_span_" + termNum + "_yingShou" + "FuLiLiXi"+ "_td_span").text("0.00");
			
    		$("#input_text_" + termNum + "_yingShou" + "YuQiWeiYueJin" + "_Text").val("0.00");
			$("#input_span_" + termNum + "_yingShou" + "YuQiWeiYueJin"+ "_td_span").text("0.00");
			
    		$("#input_text_" + termNum + "_yingShou" + "LiXi" + "_Text").val(liXi);
			$("#input_span_" + termNum + "_yingShou" + "LiXi"+ "_td_span").text(liXi);
			
    		$("#input_text_" + termNum + "_yingShou" + "FeiYong" + "_Text").val("0.00");
			$("#input_span_" + termNum + "_yingShou" + "FeiYong"+ "_td_span").text("0.00");
			
    		$("#input_text_" + termNum + "_yingShou" + "BenJin" + "_Text").val(benJin);
			$("#input_span_" + termNum + "_yingShou" + "BenJin"+ "_td_span").text(benJin);
    	});
    }

    
    /**
     * 利随本清  正常还款时 同步实际还信息
     * @author WD
     */
    function synchronizeRealAmtByLsbq() {
    	var returnPlanPoint = $("#returnPlanPoint_hidden").val();
    	var cmpdRateType = $("#cmpdRateType_input_hidden").val();//复利利息是否收取：0-不收取、1-收取 
	   	var tmpBenjinSum = 0;
	   	var tmpLixiSum = 0;
    	var tmpFaxiSum = 0;
    	var tmpYuQiLiXiSum = 0;
    	var tmpFuLiLiXiSum = 0;
    	var tmpWeiyuejinSum = 0;
    	var tmpFeiyongSum = 0;
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
    		
    		// 计算违约金
    		tmpVal = $("#input_text_" + termNum + "_yingShouYuQiWeiYueJin_Text").val();
    		tmpWeiyuejinSum = CalcUtil.add(tmpWeiyuejinSum, tmpVal);
    		
    		// 计算利息
    		tmpVal = $("#input_text_" + termNum + "_yingShouLiXi_Text").val();
    		tmpLixiSum = CalcUtil.add(tmpLixiSum, tmpVal);
    		
    		// 计算费用
    		tmpVal = $("#input_text_" + termNum + "_yingShouFeiYong_Text").val();
    		tmpFeiyongSum = CalcUtil.add(tmpFeiyongSum, tmpVal);
    		
    		// 计算本金
    		tmpVal = $("#input_text_" + termNum + "_yingShouBenJin_Text").val();
    		tmpBenjinSum = CalcUtil.add(tmpBenjinSum, tmpVal);
    		
    		// 计算优惠减免
    		tmpVal = $("#" + termNum + "_youHuiJine_input").val();
    		tmpYouhuiAmt = CalcUtil.add(tmpYouhuiAmt, tmpVal);
    	});
    	var tmpSumVal = 0.00;// 实收合计
    	tmpSumVal = CalcUtil.add(tmpSumVal, tmpBenjinSum);
    	tmpSumVal = CalcUtil.add(tmpSumVal, tmpLixiSum);
    	tmpSumVal = CalcUtil.add(tmpSumVal, tmpYuQiLiXiSum);
    	tmpSumVal = CalcUtil.add(tmpSumVal, tmpFuLiLiXiSum);
    	tmpSumVal = CalcUtil.add(tmpSumVal, tmpFeiyongSum);
    	tmpSumVal = CalcUtil.add(tmpSumVal, tmpWeiyuejinSum);
    	// 减去优惠
    	var allItemSumVar = CalcUtil.subtract(tmpSumVal,tmpYouhuiAmt);
    	
    	$("#shiShouBenJin_input_text").val(tmpBenjinSum);
    	var tmpBenjinSumFormat = CalcUtil.formatMoney(tmpBenjinSum, returnPlanPoint);
    	$("#shiShouBenJin").text(tmpBenjinSumFormat);
    	$("#item_sum_benjin").text(tmpBenjinSumFormat);
    	
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
    	
    	$("#shiShouFeiYong_input_text").val(tmpFeiyongSum);
    	var tmpFeiyongSumFormat = CalcUtil.formatMoney(tmpFeiyongSum, returnPlanPoint);
    	$("#shiShouFeiYong").text(tmpFeiyongSumFormat);
    	$("#item_sum_feiyong").text(tmpFeiyongSumFormat);
    	
    	$("#shiShouYuQiWeiYueJin_input_text").val(tmpWeiyuejinSum);
    	var tmpWeiyuejinSumFormat = CalcUtil.formatMoney(tmpWeiyuejinSum, returnPlanPoint);
    	$("#item_sum_wyj").text(tmpWeiyuejinSumFormat);
    	$("#shiShouYuQiWeiYueJin").text(tmpWeiyuejinSumFormat);
    	
    	$("#all_item_sum").text(CalcUtil.formatMoney(allItemSumVar, returnPlanPoint));

    }
    
    /**
     * 利随本清 还款明细调整
     * @author WD
     */
    function adjustPayDetailByLsbq() {
        var cmpdRateType = $("#cmpdRateType_input_hidden").val();//复利利息是否收取：0-不收取、1-收取 
    	var termNum = "1";
    	$("#yingShou_List_table_001").find("tr[id^='tr_']").each(function() {
    		termNum = $(this).attr("termNum");// 获取期号
    		var orderAry = payOrder.split(",");
    		var orderLen = orderAry.length;
    		for ( var i = 0; i < orderLen; i++) {
    			$("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").show();
    			$("#input_shouldPay_" + termNum + "_yingShou" + orderAry[i]).show();
    			$("#input_text_" + termNum + "_yingShou" + orderAry[i] + "_Text").prop("disabled", true);
    			$("#input_span_" + termNum + "_yingShou" + orderAry[i] + "_td_span").hide();
    		}
    		$("#" + termNum + "_youHuiJine_input").prop("disabled", true);
    	});
    }
    /**
     * 四舍五入的转换函数
     * 
     * @param v
     *            表示要转换的值
     * @param e
     *            表示要保留的位数
     * @returns {Number}
     * @wd
     */
    function round(v, ee) {

    	var t = 1;
    	var fromE = ee;
    	for (; ee > 0; t *= 10, ee--)
    		;

    	for (; ee < 0; t /= 10, ee++)
    		;
    	var res = Math.round(v * t) / t;
    	res = res + "";
    	if ("1" == fromE) {
    		if (res.indexOf(".") < 0) {
    			res = res + ".0";
    		}
    	}
    	if ("2" == fromE) {
    		if (res.indexOf(".") < 0) {
    			res = res + ".00";
    		} else {
    			var tmpAry = res.split(".");
    			if (tmpAry[1].length == 1) {
    				res = res + "0";
    			}
    		}
    	}

    	return res;
    }
/**
 * 利随本清还款  还款日期 变化时 执行的方法
 * @author WD
 */
function  lsbqHuanKuanRiQiChange(){
	$("#shiShouLiXi").text("0.00");
	$("#shiShouLiXi_input_text").val("0.00")
	shiShouBenJinByLsbqHuanKuanInputOnblur();
}
/**
 * 还款页面金额控制 金额控制
 */
function funcCheckPRepay(obj){
	//验证数据类型
   func_uior_valType(obj);
}

/**
 * 利随本清还款操作方法
 * 1 验证还款金额
 * 2 还款
 * @author WD
 */
window.ajaxLsbqRapayment = function(obj,callback){ 
		var flag = submitJsMethod(obj, '');
		//return ;
		if(flag){
			//验证是否能够利随本清还款  验证方法 
			var isWrong= ifCanLsbqRapayment();    
			if(isWrong){//不满足条件 不允许还款
			    return;
			  }		
			var url = $(obj).attr("action");
			//获取利随本清还款 还款信息
			var dataParam = getMfLsbqRepaymentJsonStr();
			jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
						LoadingAnimate.start();
					},success:function(data){
						if(data.flag == "success"){
							  $.each(data,function(name,value) {
								   setFormEleValue(name, value);//调用公共js文件的方法表单赋值
							  });
							  top.flag=true;
							  top.wkfRepayId = data.wkfRepayId;
							  window.top.alert(data.msg,3);
							  myclose_click();													  
						}else{//不满足利随本清还款条件
							window.top.alert(data.msg,0);
						}
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION","还款"),0);
					},complete: function(){
   						LoadingAnimate.stop();
   					}
				});
		  }
	}

/**
 * 验证页面相关金额是否符合利随本清还款要求
 * 返回 false 能还款 true 不能还款
 * @author WD
 */
function ifCanLsbqRapayment(){
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

/**
 * 获取利随本清还款 还款信息
 * @returns
 */
function getMfLsbqRepaymentJsonStr(){
	var datas=[];
	var repaymentObj ={};
	// 借据号
	repaymentObj.name = "fincId";
	repaymentObj.value = $("#fincId").val();
	datas.push(repaymentObj);
	// 合同号
	repaymentObj={};
	repaymentObj.name = "pactId";
	repaymentObj.value  =  $("#pactId").val();
	datas.push(repaymentObj);
	// 还款日期
	repaymentObj={};
	repaymentObj.name = "huanKuanRiQi";
	repaymentObj.value  =  $("#systemDateLong").val();
	datas.push(repaymentObj);
	// 利随本清还本
	repaymentObj={};
	repaymentObj.name = "huanKuanBenJin";
	repaymentObj.value =  $("#huanKuanBenJin").val();
	datas.push(repaymentObj);
	// 实收总计
	repaymentObj={};
	repaymentObj.name = "shiShouZongJi";
	repaymentObj.value  =  $("#shiShouZongJi_input_text").val();
	datas.push(repaymentObj);
	//剩余本金
	repaymentObj={};
	repaymentObj.name = "loanBal";
	repaymentObj.value  =  $("#shengYuBenJin_input_text").val();
	datas.push(repaymentObj);
	//实收利息
	repaymentObj={};
	repaymentObj.name = "shiShouLiXi";
	repaymentObj.value  =  $("#shiShouLiXi_input_text").val();
	datas.push(repaymentObj);
	//本次冲抵
	repaymentObj={};
	repaymentObj.name = "benCiChongDi";
	repaymentObj.value  =  $("#benCiChongDi_input_text").val();
	datas.push(repaymentObj);
	//本次结余
	repaymentObj={};
	repaymentObj.name = "benCiJieYu";
	repaymentObj.value  =  $("#benCiJieYu_input_text").val();
	datas.push(repaymentObj);
	//还款说明
	repaymentObj = {};
	repaymentObj.name = "remark";
	repaymentObj.value = $("#remark").val()?$("#remark").val():"";
	datas.push(repaymentObj);
	var repaymentJsonStr = JSON.stringify(datas);
	return repaymentJsonStr;
};

