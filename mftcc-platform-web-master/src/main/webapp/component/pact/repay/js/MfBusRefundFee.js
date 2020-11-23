
var MfBusRefundFee = function(window,$){
	
	var _init = function(){
		//初始化滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
		//动态 添加费用信息明细
		addRefundFeeDetail();
	};
	/**
	 * 动态添加费用信息明细
	 */
	function addRefundFeeDetail(){
		$.each(mfRefundFeeDetailList, function(i, mfRefundFeeDetail) {
			var htmlStr='<tr>'
			+'<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">实还'+mfRefundFeeDetail.feeAmtName+'</label></td>'
			+'<td class="tdvalue  right" colspan="1" rowspan="1">'
			+'<div class="input-group">'
			+'<input type="text" title=实还'+mfRefundFeeDetail.feeAmtName+' name=feeAmt'+mfRefundFeeDetail.itemNo+' datatype="12" mustinput="0"'
			+'class="form-control" maxlength="14" size="0.00" readonly="" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);"'
			+'onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" value='+mfRefundFeeDetail.feeAmt+'>'
			+'<span class="input-group-addon">元</span></div></td>'
			+'<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">'+mfRefundFeeDetail.feeAmtName+'退款</label></td>'
			+'<td class="tdvalue  right" colspan="1" rowspan="1"><div class="input-group"><input type="text" title='+mfRefundFeeDetail.feeAmtName+'退款  name=applyFeeAmt'+mfRefundFeeDetail.itemNo+' datatype="12" mustinput="0" class="form-control" maxlength="14" size="0.00" placeholder=请输入退款'+mfRefundFeeDetail.feeAmtName+'总额  onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="MfBusRefundFee.validateInputAmt(this);toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();">'
            +'<span class="input-group-addon">元</span></div></td>'
			+'</tr>';
			$("#MfBusRefundFeeApplyForm").find("table").find("tbody").append(htmlStr);
		});
		//添加申请总额信息
		var htmlStrSumInfo='<tr>'
		+'<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">申请退款总额</label></td>'	
		+'<td class="tdvalue  right" colspan="1" rowspan="1"><div class="input-group"><input type="text" title="申请退款总额" name="applySum" datatype="12" mustinput="0" class="form-control" maxlength="14" size="0.00" readonly="" onblur="func_uior_valTypeImm(this);resetTimes();" onfocus="selectInput(this);" onkeyup="toMoney(this)" onmousedown="enterKey()" onkeydown="enterKey();" value="0.00">'
		+'<span class="input-group-addon">元</span></div></td>'
		+'</tr>';
	    $("#MfBusRefundFeeApplyForm").find("table").find("tbody").append(htmlStrSumInfo);
	}
	
	var _initDetail = function(){
		//初始化滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
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
   var  round = function round(v, ee) {

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
    };
   
	//验证输入金额
	var _validateInputAmt = function(obj){
		//申请退款项名称
		var applyName = $(obj).attr("name");
		//实还金额名称
		var normName=applyName.substring(5, applyName.length);
		var fristStrUpper=normName.substring(0,1);//第一个字母 并且是大写
		var fristStrLower=fristStrUpper.toLowerCase();//转换为小写
		var repayName=fristStrLower+normName.substring(1, applyName.length);
		//实还的金额
		var normIntstSum = $('[name='+repayName+']').val().replace(/,/g, "");
		//申请退款的金额
		var applyAmt= $('[name='+applyName+']').val().replace(/,/g, "");
		if (normIntstSum * 1  < applyAmt *1) {// 申请减免/退费正常利息不能大于实收正常利息
			alert(top.getMessage("NOT_FORM_TIME", {
				"timeOne" : '申请退款的金额',
				"timeTwo" : '实还的金额'
			}), 0);
			$(obj).val("");
//			return false;
		}
		//处理申请总额
		var applySum =0.00;
		var applyNormIntstSum = $("[name=applyNormIntstSum]").val().replace(/,/g, "");
		var applyOverIntstSum = $("[name=applyOverIntstSum]").val().replace(/,/g, "");
		var applyCmpdIntstSum = $("[name=applyCmpdIntstSum]").val().replace(/,/g, "");
		var applyPenaltySum = $("[name=applyPenaltySum]").val().replace(/,/g, "");
		var applyBalAmtSum = $("[name=applyBalAmtSum]").val().replace(/,/g, "");
		applySum=CalcUtil.add(applySum,applyNormIntstSum);
		applySum=CalcUtil.add(applySum,applyOverIntstSum);
		applySum=CalcUtil.add(applySum,applyCmpdIntstSum);
		applySum=CalcUtil.add(applySum,applyPenaltySum);
		applySum=CalcUtil.add(applySum,applyBalAmtSum);
		//获取相关费用处理
		$.each(mfRefundFeeDetailList, function(i, mfRefundFeeDetail) {
			var applyFeeAmt=$('[name=applyFeeAmt'+mfRefundFeeDetail.itemNo+']').val().replace(/,/g, "");
			applySum=CalcUtil.add(applySum,applyFeeAmt);
		});
		applySum=round(applySum,"2");
		$("[name=applySum]").val(applySum);	
	};
	
	//申请保存方法
	var _insertRefundFeeApply = function(obj){
		//验证申请总额是否大于0
		var applySumTotal= $("[name=applySum]").val().replace(/,/g, "");
		if (parseFloat(applySumTotal) <= 0) {
			window.top.alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":"申请退款金额" , "timeTwo": "0.00"}), 3);
			return;
		}
		//验证申请总额和明细相加是否一致
		var applySum =0.00;
		var applyNormIntstSum = $("[name=applyNormIntstSum]").val().replace(/,/g, "");
		var applyOverIntstSum = $("[name=applyOverIntstSum]").val().replace(/,/g, "");
		var applyCmpdIntstSum = $("[name=applyCmpdIntstSum]").val().replace(/,/g, "");
		var applyPenaltySum = $("[name=applyPenaltySum]").val().replace(/,/g, "");
        var applyBalAmtSum = $("[name=applyBalAmtSum]").val().replace(/,/g, "");
		applySum=CalcUtil.add(applySum,applyNormIntstSum);
		applySum=CalcUtil.add(applySum,applyOverIntstSum);
		applySum=CalcUtil.add(applySum,applyCmpdIntstSum);
		applySum=CalcUtil.add(applySum,applyPenaltySum);
		applySum=CalcUtil.add(applySum,applyBalAmtSum);
		//获取相关费用处理
		$.each(mfRefundFeeDetailList, function(i, mfRefundFeeDetail) {
			var applyFeeAmt=$('[name=applyFeeAmt'+mfRefundFeeDetail.itemNo+']').val().replace(/,/g, "");
			applySum=CalcUtil.add(applySum,applyFeeAmt);
		});
		applySum=round(applySum,"2");
		if(parseFloat(applySum) != parseFloat(applySumTotal)){
			window.top.alert(top.getMessage("NOT_MONEY_SAME",{"money1":"申请退款总额" , "money2": "申请退款各项金额合计"}), 3);
			return;
		 }
		//提交退费申请
		 alert(top.getMessage("CONFIRM_COMMIT"),2,function(){//确定
			 saveApplyInfo(obj);
		  },function(){//取消
			  return ;
		  });
	};
	//保存申请信息
	var saveApplyInfo = function(obj){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url : url,
			data : {ajaxData : dataParam},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
					top.flag = true;
					window.top.alert(data.msg, 3);
					myclose_click();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},complete:function(){
				LoadingAnimate.stop();
			}
		});
	};
	
	
	return{ 
		init:_init,
		initDetail:_initDetail,
		validateInputAmt:_validateInputAmt,
		insertRefundFeeApply:_insertRefundFeeApply,
	};
	 
}(window,jQuery);