
var MfPreRepayApplyInsert = function(window,$){
    var  applyBeginDate ="";
    var  applyEndDate = "";
    var _init = function(){
		//初始化滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
        // $('input[name=planRepayDate]').onfocus({
        //     fPopUpCalendarDlg({min:'',choose:repayDateChangeEvent});
        // });
		//初始化借据号选择组件
		$('input[name=fincShowId]').popupList({
			searchOn: true, //启用搜索
			multiple: false, //false单选，true多选，默认多选
			ajaxUrl:webPath+"/mfPreRepayApply/getPreRepayFincListAjax",//请求数据URL
			valueElem:"input[name=fincId]",//真实值选择器
			title: "选择借据",//标题
            searchplaceholder: "合同编号/借据号",//查询输入框的悬浮内容
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=fincShowId]"));
				var fincId = $("input[name='fincId']").val();
				$.ajax({
					url:webPath+"/mfPreRepayApply/getFincInfoAjax",
					data:{fincId:fincId},
					success:function(data){
						$("[name=cusName]").val(data.mfCusCustomer.cusName);//客户名称
						$("[name=appName]").val(data.mfBusFincApp.appName);//项目名称
						$("[name=pactNo]").val(data.mfBusFincApp.pactNo);//合同号
						$("[name=appId]").val(data.mfBusFincApp.appId);//申请编号
						$("[name=pactId]").val(data.mfBusFincApp.pactId);//合同编号
						$("[name=kindName]").val(data.mfBusFincApp.kindName);//产品种类
						$("[name=pactAmt]").val(data.pactAmt);//合同金额
						$("[name=fincRate]").val(data.mfBusFincApp.fincRate);//合同利率
						$("[name=fincRate]").parent(".input-group").find(".input-group-addon").remove();
						$("[name=fincRate]").parent(".input-group").append('<span class="input-group-addon">'+data.rateUnit+'</span>');
						$("[name=intstBeginDate]").val(data.mfBusFincApp.intstBeginDate);//借据起息日
						$("[name=intstEndDate]").val(data.mfBusFincApp.intstEndDate);//借据到期日
						$("[name=putoutAmt]").val(data.putoutAmt);//借据金额
						$("[name=loanBal]").val(data.loanBal);//剩余本金
						
						$("[name=appAmt]").val(0.00);
						preRepayType = data.mfPrepaymentBean.preRepayType;
						if(preRepayType=="1"|| preRepayType=="3" ){//1-提前还款结清  3-一次性偿还所有未还本金、利息';
							$("[name=appAmt]").val(data.mfPrepaymentBean.shengYuBenJinFormat);//提前还款申请金额
						}
						$("[name=dangQiBenJin]").val(data.mfPrepaymentBean.dangQiBenJinFormat);//当期本金
						$("[name=penaltyAmt]").val(data.mfPrepaymentBean.tiQianHuanKuanWeiYueJinFormat);//提前还款违约金
						$("[name=intstAmt]").val(data.mfPrepaymentBean.shiShouLiXiFormat);//利息
						$("[name=planRepayDate]").val(data.applyEndDate);//计划还款日期（默认当天）
						termInstMustBack = data.mfPrepaymentBean.termInstMustBack;
						returnPlanPoint = data.mfPrepaymentBean.returnPlanPoint;
						applyBeginDate = data.applyBeginDate;
						applyEndDate = data.applyEndDate;
/*                        $("input[name=planRepayDate]").on("click",function () {
                            fPopUpCalendarDlg({min:applyBeginDate,max:applyEndDate,choose:_repayDateChangeEvent()});
                        });*/
					},
					error:function(data){
						alert(top.getMessage("FAILED_OPERATION","选择借据"), 0);
					}
				});
			},
			tablehead:{//列表显示列配置
				"pactNo":"合同编号",
				"appName":"项目申请号",
				"fincShowId":"借据号"
			},
			returnData:{//返回值配置
				disName:"fincShowId",//显示值
				value:"fincId"//真实值
			}
		});
	};
	
	var _initDetail = function(preRepayAppId){
		//初始化滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
        showWkfFlowVertical($("#wj-modeler"),preRepayAppId,"14","pre_repay_approval");
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
	
	/**
	 * 提前还本金额变化 实收利息 和违约金都变化
	 */
	var _appAmtChangeEvent = function(obj){
		// 获取提前还本金额
		var appAmt=$("[name=appAmt]").val().replace(/,/g, "");
		// 获取当期本金
		var dangQiBenJin=$("[name=dangQiBenJin]").val().replace(/,/g, "");
		//剩余本金 
		var shengYuBenJin=$("[name=loanBal]").val().replace(/,/g, "");
		//还款日期
		var repayDate = $("[name=planRepayDate]").val();
		//借据号
		var fincId  = $("[name=fincId]").val();
		if(fincId==""){
			return ;
		}
        var money1,money2;
		//如果是提前结清 
		if(preRepayType=="1"|| preRepayType=="3"){
			if(appAmt*1<shengYuBenJin*1){
	       		money1="提前还款本金";
	       		money2="剩余本金："+shengYuBenJin+"元";
	    		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
	    		$(obj).val("0.00");
	    		return false;
	       	}
		}
		//判断提前还款是否结清本息
	    if(termInstMustBack=="1"){//当期本息是否必须归还：1-是、0-否
	    	//NOT_FORM_TIME  不能大于        NOT_SMALL_TIME  不能小于
	       	if(appAmt*1<dangQiBenJin*1){
	       		money1="提前还款本金";
	       		money2="当期本金："+dangQiBenJin+"元";
	    		alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":money1,"timeTwo":money2}),0);
	    		$(obj).val("0.00");
	    		return false;
	       	}
	    }
	    // 提前还款本金 不能大于剩余本金 
	    if(appAmt*1>shengYuBenJin*1){
	    	money1="提前还款本金";
	   		money2="剩余本金："+shengYuBenJin+"元";
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":money1,"timeTwo":money2}),0);
			$(obj).val("0.00");
			return false;
	    }
	    //计算提前还款 利息 和 违约金
	    $.ajax({
			url:webPath+'/mfRepayment/calcLiXiTiQianHuanKuanAjax',
			data:{"repayDate":repayDate,"fincId":fincId,"tiQianHuanBen":appAmt},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},
			success:function(data){
				if(data.flag == "success"){
					//实收利息 shiShouLiXiFormat 和违约金 tiQianHuanKuanWeiYueJinFormat 重新赋值
					$("[name=intstAmt]").val(data.shiShouLiXiFormat);
					$("[name=penaltyAmt]").val(data.tiQianHuanKuanWeiYueJinFormat);
					// 计算实收总额
					calcshiShouZongJiCallByTiQian();
				}
			},error:function(data){
				 LoadingAnimate.stop();
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	
	 // 计算实收总额
    function calcshiShouZongJiCallByTiQian() {
        //提前还本
    	var tiQianHuanBen=$("[name=appAmt]").val().replace(/,/g, "");
        //应收利息
    	var yingShouLiXi = $("[name=intstAmt]").val().replace(/,/g, "");
        //优惠总额
        var shiShouYouHuiZongJi = $("[name=deductAmt]").val().replace(/,/g, "");
    	//实收利息
        var shiShouLiXi=yingShouLiXi*1-shiShouYouHuiZongJi*1;
        shiShouLiXi=round(shiShouLiXi*1,returnPlanPoint);
    	//提前还款违约金
    	var tiQianHuanKuanWeiYueJin=$("[name=penaltyAmt]").val().replace(/,/g, "");
    	//本次结余
    	var benCiJieYu="0.00";
    	//本次冲抵
    	var benCiChongDi="0.00";
    	//实收总计
    	var shiShouZongJi="0.00";
    	shiShouZongJi=tiQianHuanBen*1+shiShouLiXi*1+tiQianHuanKuanWeiYueJin*1+benCiJieYu*1-benCiChongDi*1
    	shiShouZongJi=round(shiShouZongJi*1,returnPlanPoint);
    	//计算实收总计
    	$("[name=shiShouZongJi]").val(shiShouZongJi);
    }
		
	//计划还款日期改变，重新计算利息、违约金
	var _repayDateChangeEvent = function(){
		_appAmtChangeEvent();
	};
	
	/**
	 * 是否允许提前还款 
	 * @param dueNo 借据号
	 * @returns {Boolean}
	 */
	function  ifCanPreRepay(dueNo){
		var flag=true;
		$.ajax({
			url:webPath+'/mfPreRepayApply/ifCanPreRepayAjax',
			data:{"fincId":dueNo},
			type : "POST",
			dataType : "json",
			async:false,
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					var res=data.result;
					if(res=="1"){
						flag=true;
					}else
						flag=false;
				} else {// 不满足提前还款条件
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
		return flag;
	}
	
	//新增提前还款申请
	var _insertPreRepayApply= function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			
			//检查提前还款是否需要申请
			var  fincIdVal=$(obj+" input[name='fincId']").val();
			var  ifCanPreRepayVal=ifCanPreRepay(fincIdVal);
			if(!ifCanPreRepayVal){
				window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"不符合规则","content2":"提前还款"}), 1);
				return ;
			}
			
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
	var saveApplyInfo = function(obj,startFlowFlag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url : url,
			data : {ajaxData : dataParam,startFlowFlag:startFlowFlag},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
                    top.DIALOG.msg(data.msg,1,function(){
					});
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
	
	/**
	 * 获取提前还款 还款信息
	 * @returns
	 */
	function getMfPrepaymentJsonStr(){
		var datas=[];
		var repaymentObj ={};
		// 借据号
		repaymentObj.name = "fincId";
		repaymentObj.value = $("[name=fincId]").val();
		datas.push(repaymentObj);
		// 合同号
		repaymentObj={};
		repaymentObj.name = "pactId";
		repaymentObj.value  =  $("[name=pactId]").val();
		datas.push(repaymentObj);
		// 还款日期
		repaymentObj={};
		repaymentObj.name = "huanKuanRiQi";
		repaymentObj.value  =  $("[name=planRepayDate]").val();
		datas.push(repaymentObj);
		// 提前还本
		repaymentObj={};
		repaymentObj.name = "tiQianHuanBen";
		repaymentObj.value =  $("[name=appAmt]").val().replace(/,/g, "");
		datas.push(repaymentObj);
		//优惠总额
		repaymentObj={};
		repaymentObj.name = "youHuiZongEr";
		repaymentObj.value  = $("[name=deductAmt]").val().replace(/,/g, "");
		datas.push(repaymentObj);
		//提前还款违约金
		repaymentObj={};
		repaymentObj.name = "tiQianHuanKuanWeiYueJin";
		repaymentObj.value  = $("[name=penaltyAmt]").val().replace(/,/g, "");
		datas.push(repaymentObj);
		// 实收总计
		repaymentObj={};
		repaymentObj.name = "shiShouZongJi";
		repaymentObj.value  =  $("[name=shiShouZongJi]").val().replace(/,/g, "");
		datas.push(repaymentObj);
		//还款计划调整参数
		repaymentObj={};
		repaymentObj.name = "jiHuaTiaoZhengCanShu";
		repaymentObj.value  =  "0";
		datas.push(repaymentObj);	
		//剩余本金
		repaymentObj={};
		repaymentObj.name = "loanBal";
		repaymentObj.value  =  $("[name=loanBal]").val().replace(/,/g, "");
		datas.push(repaymentObj);
		//当期本金
		repaymentObj={};
		repaymentObj.name = "dangQiBenJin";
		repaymentObj.value  =  $("[name=dangQiBenJin]").val().replace(/,/g, "");
		datas.push(repaymentObj);
		//实收利息
		repaymentObj={};
		repaymentObj.name = "shiShouLiXi";
		repaymentObj.value  =  $("[name=intstAmt]").val().replace(/,/g, "");
		datas.push(repaymentObj);
		//本次冲抵
		repaymentObj={};
		repaymentObj.name = "benCiChongDi";
		repaymentObj.value  = "0.00";
		datas.push(repaymentObj);
		//本次结余
		repaymentObj={};
		repaymentObj.name = "benCiJieYu";
		repaymentObj.value  = "0.00";
		datas.push(repaymentObj);
		var repaymentJsonStr = JSON.stringify(datas);
		return repaymentJsonStr;
	};


	
	//提前还款
	var _ajaxPrepayment = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			//获取提前还款 还款信息
			var dataParam = getMfPrepaymentJsonStr();
			jQuery.ajax({
					url:webPath+"/mfRepayment/prepaymentAjax",
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
						LoadingAnimate.start();
					},success:function(data){
						if(data.flag == "success"){
							window.top.alert(data.msg,3);
						    top.flag=true;
							myclose_click();
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
	};
	
	var _deductAmtChangeEvent = function(obj){
		//实收利息
		var yingShouLiXi = $("[name=intstAmt]").val().replace(/,/g, "");
		//优惠总额
		var shiShouYouHuiZongJi = $("[name=deductAmt]").val().replace(/,/g, "");
		if(shiShouYouHuiZongJi*1>yingShouLiXi*1){//优惠总额 不能大于 本次提前还款的利息
			var money1="优惠总额";
	   		var money2="本次提前还款的利息";     		
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":money1,"timeTwo":money2}),0);
			$("[name=deductAmt]").val("0.00");
			return false;
		}else{
			// var shiShouLixi=yingShouLiXi*1-shiShouYouHuiZongJi*1;
			// shiShouLixi=round(shiShouLixi*1,returnPlanPoint);
			// $("[name=intstAmt]").val(shiShouLixi);
			// 计算实收总额
			calcshiShouZongJiCallByTiQian();
		}
	};
    //计划还款日期 对应的事件
    var _fPopUpCalendarDlgByLastDate = function(){

        fPopUpCalendarDlg({min:applyBeginDate,max:applyEndDate,choose:_repayDateChangeEvent()});
    };
	return{ 
		init:_init,
		initDetail:_initDetail,
		repayDateChangeEvent:_repayDateChangeEvent,
		appAmtChangeEvent:_appAmtChangeEvent,
		insertPreRepayApply:_insertPreRepayApply,
		ajaxPrepayment:_ajaxPrepayment,
		deductAmtChangeEvent:_deductAmtChangeEvent,
        fPopUpCalendarDlgByLastDate:_fPopUpCalendarDlgByLastDate,
	};
	 
}(window,jQuery);