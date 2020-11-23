/**
 * 页面数据初始化
 * 
 */
$(function() {
	if(preRepayType=="1"|| preRepayType=="3" ){//1-提前还款结清  3-一次性偿还所有未还本金、利息';
		$("#tiQianHuanBen").val(shengyubenjin);
		$("#tiQianHuanKuanWeiYueJin").text(tiQianHuanKuanWeiYueJin);
		$("#tiQianHuanKuanWeiYueJin_input_text").val(tiQianHuanKuanWeiYueJin);
	}
	var  shiShouZongji=$("#shiShouZongJi_input_text").val();
	$("#shiShouZongJi").text(shiShouZongji);
	//获取还款方式 1 等额本息  2 等额本金 时 
	var returnMethod=$("#returnMethod_input_text").val();
	if(returnMethod !="1"&& returnMethod !="2"){//隐藏还款计划调整
		$("#huanKuanJiHuaTiaoZheng").hide();
	}	
	initRepaymentDoc.initDoc("advanceDoc");
}); 
/**
 * 提前还本金额变化 实收利息 和违约金都变化
 */
function shiShouBenJinByTiQianHuanKuanInputOnblur(){
	// 获取提前还本金额
	var tiQianHuanBen=$("#tiQianHuanBen").val().replace(/,/g, "");
	// 获取当期本金
	var dangQiBenJin=$("#dangQiBenJin_input_text").val();
	//剩余本金 
	var shengYuBenJin=$("#shengYuBenJin_input_text").val();
	//还款日期
	var repayDate=$("#systemDateLong").val();
	//借据号
	var fincId=$("#fincId").val();
    var money1,money2;
	//如果是提前结清 
	if(preRepayType=="1"|| preRepayType=="3"){
		if(tiQianHuanBen*1<shengYuBenJin*1){
       		money1="提前还款本金";
       		money2="剩余本金："+shengYuBenJin+"元";
    		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
    		$("#tiQianHuanBen").val("0.00");
    		return ;
       	}
	}
	
	//判断提前还款是否结清本息
    if(termInstMustBack=="1"){//当期本息是否必须归还：1-是、0-否
    	//NOT_FORM_TIME  不能大于        NOT_SMALL_TIME  不能小于
       	if(tiQianHuanBen*1<dangQiBenJin*1){
       		money1="提前还款本金";
       		money2="当期本金："+dangQiBenJin+"元";
    		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
    		$("#tiQianHuanBen").val("0.00");
    		return ;
       	}
    }
    // 提前还款本金 不能大于剩余本金 
    if(tiQianHuanBen*1>shengYuBenJin*1){
    	money1="提前还款本金";
   		money2="剩余本金："+shengYuBenJin+"元";
		alert(top.getMessage("NOT_FORM_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		$("#tiQianHuanBen").val("0.00");
		return ;
    }
    //计算提前还款 利息 和 违约金
    $.ajax({
		url:webPath+'/mfRepayment/calcLiXiTiQianHuanKuanAjax',
		data:{"repayDate":repayDate,"fincId":fincId,"tiQianHuanBen":tiQianHuanBen},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},
		success:function(data){
			if(data.flag == "success"){
				//实收利息 shiShouLiXiFormat 和违约金 tiQianHuanKuanWeiYueJinFormat 重新赋值
				$("#shiShouLiXi").text(data.shiShouLiXiFormat);
				$("#shiShouLiXi_input_text").val(data.shiShouLiXi);
				$("#shiShouLiXiTip_input_text").val(data.shiShouLiXi);	
				$("#tiQianHuanKuanWeiYueJin").text(data.tiQianHuanKuanWeiYueJin);
				$("#tiQianHuanKuanWeiYueJin_input_text").val(data.tiQianHuanKuanWeiYueJin);	
				$("#tiQianHuanKuanWeiYueJinTip_input_text").val(data.tiQianHuanKuanWeiYueJin);
				// 计算实收总额
				calcshiShouZongJiCallByTiQian();
			}
		},error:function(data){
			 LoadingAnimate.stop();
			 alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}
    // 计算实收总额
    function calcshiShouZongJiCallByTiQian() {
        //提前还本
    	var tiQianHuanBen=$("#tiQianHuanBen").val().replace(/,/g, "");
    	//实收利息
    	var shiShouLiXi=$("#shiShouLiXi_input_text").val();
    	//提前还款违约金
    	var tiQianHuanKuanWeiYueJin=$("#tiQianHuanKuanWeiYueJin_input_text").val();
    	//本次结余
    	var benCiJieYu=$("#benCiJieYu_input_text").val();
    	//本次冲抵
    	var benCiChongDi=$("#benCiChongDi_input_text").val();
    	//实收总计
    	var shiShouZongJi="0.00";
    	shiShouZongJi=tiQianHuanBen*1+shiShouLiXi*1+tiQianHuanKuanWeiYueJin*1+benCiJieYu*1-benCiChongDi*1
    	shiShouZongJi=round(shiShouZongJi*1,returnPlanPoint);
    	//计算实收总计
    	$("#shiShouZongJi").text(shiShouZongJi);
    	$("#shiShouZongJi_input_text").val(shiShouZongJi);
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
 * 提前还款  还款日期 变化时 执行的方法
 * @author WD
 */
function  tiQianHuanKuanRiQiChange(){
	$("#YouHuiZongEr").val("0.00");
	$("#YouHuiZongEr_input_text").val("0.00");
	$("#shiShouLiXi").text("0.00");
	$("#shiShouLiXi_input_text").val("0.00")
	shiShouBenJinByTiQianHuanKuanInputOnblur();
}
//提前还款优惠总额变动时 执行的方法
function calcTiQianYouHuiInputTextOnblur() {
	//实收利息
	var yingShouLiXi = $("#shiShouLiXi_input_text").val();
	//优惠总额
	var shiShouYouHuiZongJi = $("#YouHuiZongEr").val();
	if(shiShouYouHuiZongJi*1>yingShouLiXi*1){//优惠总额 不能大于 本次提前还款的利息
		var money1="优惠总额";
   		var money2="本次提前还款的利息";     		
		alert(top.getMessage("NOT_FORM_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		$("#YouHuiZongEr").val("0.00");
		$("#YouHuiZongEr_input_text").val("0.00");
		return ;
	}else{
		var yingShouLiXiTip=$("#shiShouLiXiTip_input_text").val();
		var shiShouLixi=yingShouLiXiTip*1-shiShouYouHuiZongJi*1;
		shiShouLixi=round(shiShouLixi*1,returnPlanPoint);
		$("#shiShouLiXi").text(shiShouLixi);
		$("#shiShouLiXi_input_text").val(shiShouLixi)
		// 计算实收总额
		calcshiShouZongJiCallByTiQian();
	}
}
//提前还款违约金
function calcTiQianWeiYueJinInputTextOnblur() {
	var weiYueJin=$("#tiQianHuanKuanWeiYueJin").val();
	var yuanWeiYueJin=$("#tiQianHuanKuanWeiYueJinTip_input_text").val();
	if(weiYueJin*1 < yuanWeiYueJin*1){//违约金不能小于原违约金
		var money1="违约金";
   		var money2=yuanWeiYueJin+"元";       		
		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		$("#tiQianHuanKuanWeiYueJin").text("0.00");
		$("#tiQianHuanKuanWeiYueJin_input_text").val("0.00");
		calcshiShouZongJiCallByTiQian();
	}else{
		$("#tiQianHuanKuanWeiYueJin_input_text").val(weiYueJin);	
		// 计算实收总额
		calcshiShouZongJiCallByTiQian();
	}
	
}
/**
 * 还款页面金额控制 金额控制
 */
function funcCheckPRepay(obj){
	//验证数据类型
   func_uior_valType(obj);
}

/**
 * 提前还款操作方法
 * 1 验证还款金额
 * 2 还款
 * @author WD
 */
window.ajaxPrepayment = function(obj,callback){ 
		var flag = submitJsMethod(obj, '');
		//return ;
		if(flag){
			//验证是否能够提前还款  验证方法 
			var isWrong= ifCanPreapayment();		    
			if(isWrong){//不满足条件 不允许还款
			    return;
			  }		
			var url = $(obj).attr("action");
			//获取提前还款 还款信息
			var dataParam = getMfPrepaymentJsonStr();
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

                            if (typeof(mfBusEditorRepayplanFlag) != "undefined" && mfBusEditorRepayplanFlag == '1') {
                                window.location.href = webPath + "/mfRepayPlan/preRepayPlanList?fincIdNew=" + fincId + "&appId=" + appId;
                                window.top.alert("提前还款操作成功", 3);
                            } else {
                                window.top.alert("提前还款操作成功", 3);
                                myclose_click();
                            }
//							  var url=webPath+"/mfBusPact/getPactFincById?fincId="+$(fincId).val()+"&appId="+appIdVal;
//								$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);

                        }else{//不满足提前还款条件
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
 * 验证页面相关金额是否符合提前还款要求
 * 返回 false 能还款 true 不能还款
 * @author WD
 */
function ifCanPreapayment(){
	var isWrong=false;
	// 获取提前还本金额
	var tiQianHuanBen=$("#tiQianHuanBen").val();
	// 获取当期本金
	var dangQiBenJin=$("#dangQiBenJin_input_text").val();
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
	if(shiShouZongJi*1<='0'){
   		money1="实收总计";
   		money2=0.00+"元";
		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		isWrong=true;
		return isWrong;
   	}
	
	//如果是提前结清 
	if(preRepayType=="1" || preRepayType=="3"){
		if(tiQianHuanBen*1<shengYuBenJin*1){
       		money1="提前还款本金";
       		money2="剩余本金："+shengYuBenJin+"元";
    		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
    		$("#tiQianHuanBen").val("0.00");
    		isWrong=true;
    		return isWrong;
       	}
	}
	//判断提前还款是否结清本息
    if(termInstMustBack=="1"){//当期本息是否必须归还：1-是、0-否
    	//NOT_FORM_TIME  不能大于        NOT_SMALL_TIME  不能小于
       	if(tiQianHuanBen*1<dangQiBenJin*1){
       		money1="提前还款本金";
       		money2="当期本金："+dangQiBenJin+"元";
    		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
    		$("#tiQianHuanBen").val("0.00");
    		isWrong=true;
    		return isWrong;
       	}
    }
    // 提前还款本金 不能大于剩余本金 
    if(tiQianHuanBen*1>shengYuBenJin*1){
    	money1="提前还款本金";
   		money2="剩余本金："+shengYuBenJin+"元";
		alert(top.getMessage("NOT_FORM_TIME",{"timeOne":money1,"timeTwo":money2}),0);
		$("#tiQianHuanBen").val("0.00");
		isWrong=true;
		return isWrong;
    }
//	var weiYueJin=$("#tiQianHuanKuanWeiYueJin").val();
//	var yuanWeiYueJin=$("#tiQianHuanKuanWeiYueJinTip_input_text").val();
//	if(weiYueJin*1<yuanWeiYueJin*1){//违约金不能小于应还违约金
//		var money1="违约金";
//   		var money2=yuanWeiYueJin+"元";       		
//		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
//		$("#tiQianHuanKuanWeiYueJin").val("0.00");
//		isWrong=true;
//		return isWrong;
//	}
    return isWrong;
}

/**
 * 获取提前还款 还款信息
 * @returns
 */
function getMfPrepaymentJsonStr(){
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
	// 提前还本
	repaymentObj={};
	repaymentObj.name = "tiQianHuanBen";
	repaymentObj.value =  $("#tiQianHuanBen").val();
	datas.push(repaymentObj);
	//优惠总额
	repaymentObj={};
	repaymentObj.name = "youHuiZongEr";
	repaymentObj.value  = $("#YouHuiZongEr").val();
	datas.push(repaymentObj);
	//提前还款违约金
	repaymentObj={};
	repaymentObj.name = "tiQianHuanKuanWeiYueJin";
	repaymentObj.value  = $("#tiQianHuanKuanWeiYueJin_input_text").val();
	datas.push(repaymentObj);
	// 实收总计
	repaymentObj={};
	repaymentObj.name = "shiShouZongJi";
	repaymentObj.value  =  $("#shiShouZongJi_input_text").val();
	datas.push(repaymentObj);
	//还款计划调整参数
	repaymentObj={};
	repaymentObj.name = "jiHuaTiaoZhengCanShu";
	repaymentObj.value  =  $("#jiHuaTiaoZhengCanShu option:selected").val();
	datas.push(repaymentObj);	
	//剩余本金
	repaymentObj={};
	repaymentObj.name = "loanBal";
	repaymentObj.value  =  $("#shengYuBenJin_input_text").val();
	datas.push(repaymentObj);
	//当期本金
	repaymentObj={};
	repaymentObj.name = "dangQiBenJin";
	repaymentObj.value  =  $("#dangQiBenJin_input_text").val();
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

