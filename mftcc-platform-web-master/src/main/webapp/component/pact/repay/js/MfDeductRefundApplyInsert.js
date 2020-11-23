
var MfDeductRefundApplyInsert = function(window,$){
	
	var _init = function(){
		//初始化滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
		//初始化选择组件
		var appType = $("[name=appType]").val();
		if(appType=="1"){//减免
			initDeductSelectComponent();
		}else{//退费
			initRefundSelectComponent();
		}
	};
	
	var _initDetail = function(){
		//初始化滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
	}
	
	//初始化减免借据号选择组件
	var initDeductSelectComponent = function(){
		//初始化借据号选择组件
		$('input[name=fincId]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfDeductRefundApply/getDefundFincListAjax",//请求数据URL
			valueElem:"input[name=fincId]",//真实值选择器
			title: "选择借据",//标题
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=fincId]"));
				var fincId = elem.val();
				$.ajax({
					url:webPath+"/mfDeductRefundApply/getDeductFincInfoAjax",
					data:{fincId:fincId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){},
					success:function(data){
						$("[name=cusName]").val(data.mfCusCustomer.cusName);//客户名称
						$("[name=appName]").val(data.mfBusFincApp.appName);//项目名称
						$("[name=pactNo]").val(data.mfBusFincApp.pactNo);//合同号
						$("[name=pactId]").val(data.mfBusFincApp.pactId);//合同编号
						$("[name=appId]").val(data.mfBusFincApp.appId);//业务编号
						$("[name=pactAmt]").val(data.pactAmt);//合同金额
						$("[name=fincRate]").val(data.mfBusFincApp.fincRate);//合同利率
                        $("[name=kindName]").val(data.mfBusFincApp.kindName);//产品名称
						$("[name=fincRate]").parent(".input-group").find(".input-group-addon").remove();
						$("[name=fincRate]").parent(".input-group").append('<span class="input-group-addon">'+data.rateUnit+'</span>');
						$("[name=recvAmt]").val(data.mfRepaymentBean.yingShouZongJiAllFormat);//本次应还总额
						$("[name=prcpSum]").val(data.mfRepaymentBean.shiShouBenJinFormat);//本次还款本金总额
						$("[name=normIntstSum]").val(data.mfRepaymentBean.shiShouLiXiFormat);//本次还款正常利息（不包括减免的）
						$("[name=overIntstSum]").val(data.mfRepaymentBean.shiShouYuQiLiXiFormat);//本次还款逾期利息合计（不包括减免的）
						$("[name=cmpdIntstSum]").val(data.mfRepaymentBean.shiShouFuLiLiXiFormat);//本次还款复利利息合计（不包括减免的）
						$("[name=penaltySum]").val(data.mfRepaymentBean.shiShouYuQiWeiYueJinFormat);//本次划款违约金合计（不包括减免的）
						$("[name=feeSum]").val(data.mfRepaymentBean.shiShouFeiYongFormat);//本次划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
					},
					error:function(data){
						alert(top.getMessage("FAILED_OPERATION","选择借据"), 0);
					}
				});
				
			},
			tablehead:{//列表显示列配置
				"pactNo":"合同编号",
				"appName":"融资申请号",
				"fincShowId":"借据号"
			},
			returnData:{//返回值配置
				disName:"fincShowId",//显示值
				value:"fincId"//真实值
			}
		});
	};
	//初始化退费借据号的选择组件
	var initRefundSelectComponent = function(){
		//初始化借据号选择组件
		$('input[name=fincId]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfDeductRefundApply/getRefundFincListAjax",//请求数据URL
			valueElem:"input[name=fincId]",//真实值选择器
			title: "选择借据",//标题
            searchplaceholder: "合同编号/融资申请号/借据号",//查询输入框的悬浮内容
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=fincId]"));
				var fincId = elem.val();
				$.ajax({
					url:webPath+"/mfDeductRefundApply/getRefundFincInfoAjax",
					data:{fincId:fincId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){},
					success:function(data){
						$("[name=cusName]").val(data.mfBusFincApp.cusName);//客户名称
						$("[name=appName]").val(data.mfBusFincApp.appName);//项目名称
						$("[name=pactNo]").val(data.mfBusFincApp.pactNo);//合同号
						$("[name=pactId]").val(data.mfBusFincApp.pactId);//合同编号
						$("[name=appId]").val(data.mfBusFincApp.appId);//业务编号
						$("[name=pactAmt]").val(data.pactAmt);//合同金额
						$("[name=fincRate]").val(data.mfBusFincApp.fincRate);//合同利率
						$("[name=fincRate]").parent(".input-group").find(".input-group-addon").remove();
						$("[name=fincRate]").parent(".input-group").append('<span class="input-group-addon">'+data.rateUnit+'</span>');
						$("[name=recvAmt]").val(data.recvAmt);//多次还款总额合计
						$("[name=prcpSum]").val(data.prcpSum);//多次还款本金总额
						$("[name=normIntstSum]").val(data.normIntstSum);//多次还款正常利息（不包括减免的）
						$("[name=overIntstSum]").val(data.overIntstSum);//多次还款逾期利息合计（不包括减免的）
						$("[name=cmpdIntstSum]").val(data.cmpdIntstSum);//多次还款复利利息合计（不包括减免的）
						$("[name=penaltySum]").val(data.penaltySum);//多次划款违约金合计（不包括减免的）
						$("[name=feeSum]").val(data.mfRepayHistory.feeSum);//多次划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
					},
					error:function(data){
						alert(top.getMessage("FAILED_OPERATION","选择借据"), 0);
					}
				});
				
			},
			tablehead:{//列表显示列配置
				"pactNo":"合同编号",
				"appName":"项目名称",
				"fincShowId":"借据号"
			},
			returnData:{//返回值配置
				disName:"fincShowId",//显示值
				value:"fincId"//真实值
			}
		});
	};
	
	//根据申请的类型，切换不同的表单
	var _appTypeChangeEvent = function(obj){
		var appType = $(obj).val();
		$.ajax({
			url:webPath+"/mfDeductRefundApply/getFormHtmlAjax",
			data : {"appType":appType},
			success:function(data){
				if(data.flag=="success"){
					$("#MfDeductRefundApplyForm").html(data.htmlStr);	
					if(appType=="1"){//减免
						initDeductSelectComponent();
					}else{//退费
						initRefundSelectComponent ();
					}
				}else{
					alert(data.msg, 0);	
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	
	
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

		var name = $(obj).attr("name");
		//减免金额传来的数据不确定数据类型无法对比大小，将其中的逗号去掉改成数值型
		var money =  $(obj).val().replace(/,/g,"");
		if(name=="applyNormIntstSum"){//申请正常利息
			var normIntstSum = $("[name=normIntstSum]").val().replace(/,/g,"");
			if (normIntstSum * 1  < money*1) {// 申请减免/退费正常利息不能大于实收正常利息
				alert(top.getMessage("NOT_FORM_TIME", {
					"timeOne" : '申请减免/退费正常利息',
					"timeTwo" : '实收正常利息'
				}), 0);
				$(obj).val("");
				return false;
			}
		}else if(name=="applyOverIntstSum"){//逾期利息
			var  overIntstSum = $("[name=overIntstSum]").val().replace(/,/g,"");
			if (overIntstSum * 1  < money*1) {// 申请减免/退费逾期利息不能大于实收逾期利息
				alert(top.getMessage("NOT_FORM_TIME", {
					"timeOne" : '申请减免/退费逾期利息',
					"timeTwo" : '实收逾期利息'
				}), 0);
				$(obj).val("");
				return false;
			}
		}else if(name=="applyCmpdIntstSum"){//复利利息
			var  cmpdIntstSum = $("[name=cmpdIntstSum]").val().replace(/,/g,"");
			if (cmpdIntstSum * 1  < money*1) {// 申请减免/退费正常利息不能大于实收复利利息
				alert(top.getMessage("NOT_FORM_TIME", {
					"timeOne" : '申请减免/退费复利利息',
					"timeTwo" : '实收复利利息'
				}), 0);
				$(obj).val("");
				return false;
			}
		}else if(name=="applyPenaltySum"){//违约金
			var  penaltySum = $("[name=penaltySum]").val().replace(/,/g,"");
			if (penaltySum * 1  < money*1) {// 申请减免/退费正常利息不能大于实收违约金
				alert(top.getMessage("NOT_FORM_TIME", {
					"timeOne" : '申请减免/退费违约金',
					"timeTwo" : '实收违约金'
				}), 0);
				$(obj).val("");
				return false;
			}
		}else if(name=="applyFeeSum"){//费用
			var  feeSum = $("[name=feeSum]").val().replace(/,/g,"");
			if (feeSum * 1  < money*1) {// 申请减免/退费不能大于实收正常利息
				alert(top.getMessage("NOT_FORM_TIME", {
					"timeOne" : '申请减免/退费总额',
					"timeTwo" : '实收减免/退费总额'
				}), 0);
				$(obj).val("");
				return false;
			}
		}
		//处理申请总额
		var applyNormIntstSum = $("[name=applyNormIntstSum]").val().replace(/,/g, "");
		var applyOverIntstSum = $("[name=applyOverIntstSum]").val().replace(/,/g, "");
		var applyCmpdIntstSum = $("[name=applyCmpdIntstSum]").val().replace(/,/g, "");
		var applyPenaltySum = $("[name=applyPenaltySum]").val().replace(/,/g, "");
		var applyFeeSum = $("[name=applyFeeSum]").val().replace(/,/g, "");
		var appSum = applyNormIntstSum*1+applyOverIntstSum*1+applyCmpdIntstSum*1+applyPenaltySum*1+applyFeeSum*1;
		appSum=round(appSum,"2");
		$("[name=applySum]").val(appSum);
		
	};
	
	//申请保存方法
	var _insertDeductRefundApply = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var flowFlag = $("input[name=flowFlag]").val();
			if(flowFlag=="1"){
				 alert('确定要提交至审批吗?',2,function(){//确定
					 saveApplyInfo(obj,"1");
				  },function(){//取消
					  saveApplyInfo(obj,"0");
				  }); 
			}else{
				saveApplyInfo(obj,"2");
			}
		}
	};
	//保存申请信息
	var saveApplyInfo = function(obj,flowFlag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url : url,
			data : {ajaxData : dataParam,flowFlag:flowFlag},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
					window.top.alert(data.msg,3);
					//myclose_click();
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},complete:function(){
                myclose_click();
				LoadingAnimate.stop();
			}
		});
	};
	
	
	return{ 
		init:_init,
		initDetail:_initDetail,
		appTypeChangeEvent:_appTypeChangeEvent,
		validateInputAmt:_validateInputAmt,
		insertDeductRefundApply:_insertDeductRefundApply,
	};
	 
}(window,jQuery);