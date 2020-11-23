;
var MfLitigationExpenseInout_Insert = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({//滚动条的生成
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};

	 _myclose = function (){//关闭当前弹窗的方法
		 myclose_click();//关闭弹窗
		 //window.location.href = webPath+"/mfCusWhitename/getListPage";//重新刷新列表
 	 };
	
	
	var _ajaxSave=function(obj){
        //金额校验finForminOut
        if(inoutFlag == "1"){

            //费用总额 与 收款金额是否相等
            var totalExpenses=Number($("input[name=totalExpenses]").val().replace(/,/g,''));

            var receiveAmt=Number($("input[name=receiveAmt]").val().replace(/,/g,''));

            if(totalExpenses != receiveAmt){
                alert("退款的费用总额应等于收款金额",0);
                return  false;
            }
            if(!_validateMoney()){
                return  false;
            }
            if(!_validateDate()){
                return  false;
            }
        }
		var dataParam = JSON.stringify($(obj).serializeArray());	
		var url = $(obj).attr("action");
		jQuery.ajax({
			url:webPath+'/mfLitigationExpenseInput/insertInoutAjax',
			data:{ajaxData:dataParam,flag:inoutFlag},
			type:"POST",
			dataType:"json",
			beforeSend:function(){ 
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag == "success"){
					top.alert(data.msg,1);
					myclose_click();//保存完成之后关闭弹窗，回到列表
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			},complete:function(){
				LoadingAnimate.stop();
			}
		});
	}

	
	//费用打款新增
	  var _finForminput = function(){
		top.window.openBigForm(webPath+'/mfLitigationExpenseInput/input?inoutFlag='+inoutFlag, '诉讼费用打款登记',function(){
			 		window.updateTableData();
			 	});
		};
	//费用打款新增
	   var _finForminOut = function(){
		 top.window.openBigForm(webPath+'/mfLitigationExpenseInput/input?inoutFlag='+inoutFlag, '诉讼费用退款登记',function(){
				 	window.updateTableData();
				});
		};
	
	//选择用款费用申请
	  var  _selectLitigationApply = function(){
		  $("input[name=litigationId]").popupList({
				searchOn: true, //启用搜索
				multiple: false, //false单选，true多选，默认多选
				ajaxUrl : webPath+"/mfLitigationExpenseInput/selectLitigationApply?applyStatus="+2, // 请求数据URL
				valueElem:"input[name='litigationId']",//真实值选择器
				title: "选择费用申请",//标题
				changeCallback:function(elem){//回调方法
					var tmpData = elem.data("selectData");
					$("input[name=popslitigationId]").val(tmpData.litigationId);
					$("input[name=cusName]").val(tmpData.cusName);
					$("input[name=cusNo]").val(tmpData.cusNo);
					$("input[name=litigationAmount]").val(tmpData.litigationAmount);
					$("input[name=prosecutionDate]").val(tmpData.prosecutionDate);
				},
				tablehead:{//列表显示列配置
					"litigationId":"诉讼费用申请号",
					"cusName":"客户名称"
				},
				returnData:{//返回值配置
					disName:"litigationId",//显示值
					value:"litigationId"//真实值
				}
			});  	
		  $("input[name=litigationId]").next().click();
	  };
	  
	
	//选择用款费用申请
	  var  _selectLitigationInout = function(){
		  $("input[name=litigationId]").popupList({
				searchOn: true, //启用搜索
				multiple: false, //false单选，true多选，默认多选
				ajaxUrl : webPath+"/mfLitigationExpenseInput/selectLitigationInout?inoutFlag="+0, // 请求数据URL
				valueElem:"input[name='litigationId']",//真实值选择器
				title: "选择费用申请",//标题
				changeCallback:function(elem){//回调方法
					var tmpData = elem.data("selectData");
					$("input[name=popslitigationId]").val(tmpData.litigationId);
					$("input[name=cusName]").val(tmpData.cusName);
					$("input[name=cusNo]").val(tmpData.cusNo);
					$("input[name=litigationAmount]").val(tmpData.litigationAmount);
					$("input[name=prosecutionDate]").val(tmpData.prosecutionDate);
				},
				tablehead:{//列表显示列配置
					"litigationId":"诉讼费用申请号",
					"cusName":"客户名称"
				},
				returnData:{//返回值配置
					disName:"litigationId",//显示值
					value:"litigationId"//真实值
				}
			});  	
		  $("input[name=litigationId]").next().click();
	  };


    //选择  关联账号的回调
    var  _initSelectBusBankNo  =  function(tmpData){
        $("input[name=busBankNo]").val(tmpData.receivablesNo);
        $("input[name=receiveAmt]").val(tmpData.receivablesAmt);
        $("input[name=receiveSummary]").val(tmpData.remark);
        $("input[name=receiveBankNo]").val(tmpData.accountNo);
        $("input[name=receiveBankName]").val(tmpData.accountBank);
        $("input[name=payBankNo]").val(tmpData.otherPartyAccountNo);
        $("input[name=payBankName]").val(tmpData.otherPartyAccountName);
        $("input[name=collectBank]").val(tmpData.ext4);
        $("input[name=collectBankAddress]").val(tmpData.ext5);



    }


    //选择   关联收款账号
    var  _selectBusBankNo  =  function(){
        var  cusNo =	$("input[name='cusNo']").val();
        var  useType = "6";
        _sltBankGahter(cusNo,"","",_initSelectBusBankNo,useType);
    }



    var  _sltBankGahter=function (cusNo,pactId,fincId,callback,useType) {
        $("input[name=bankTradingFlow]").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl : webPath+"/mfBankReceivablesBuss/findBussByPageAjax?fincId="+fincId+"&pactId="+pactId+"&cusNo="+cusNo+"&useType="+useType, // 请求数据URL
            valueElem:"input[name='bankTradingFlow']",//真实值选择器
            title: "选择机构",//标题
            changeCallback:function(elem){//回调方法

                var tmpData = elem.data("selectData");
                if(typeof(callback)== "function"){
                    var receivablesDate  =  tmpData.receivablesDate;
                    var	year  =  receivablesDate.substring(0,4);

                    var  month =   receivablesDate.substring(4,6);

                    var  day  =   receivablesDate.substring(6,8);

                    var    receivablesDateNew  = year +"-" + month  + "-" +  day;
                    $("input[name=collectDate]").val(receivablesDateNew);
                    $("input[name=bankTradingFlow]").val(tmpData.relId);
                    callback(tmpData);
                }else{
                    $("input[name=busBankNo]").val(tmpData.receivablesId);
                    $("input[name=accountAmt]").val(tmpData.receivablesAmt);
                    $("input[name=collectDate]").val(tmpData.receivablesDate);
                    $("input[name=bankTradingFlow]").val(tmpData.relId);
                }
            },
            tablehead:{//列表显示列配置
                "receivablesId":"银行收款编号",
                "receivablesAmt":"收款金额"
            },
            returnData:{//返回值配置
                disName:"receivablesId",//显示值
                value:"relId"//真实值
            }
        });

        $("input[name=bankTradingFlow]").next().click();

    }


    // 记账日期不允许早于收款日期
    var  _validateDate  =  function(){
        var  collectDate =	$("input[name='collectDate']").val();//收款日期
        var  touchDate =	$("input[name='touchDate']").val();//记账日期
        collectDate = parseInt(collectDate.replace(/-/g, ''));
        touchDate = parseInt(touchDate.replace(/-/g, ''));
        if(touchDate<collectDate){
            alert("记账日期不能早于收款日期!",0);
            $("input[name='touchDate']").val('');
            return false;
        }
        return true;
    }

    //诉讼费退款页面收款金额=费用总额，费用总额=>客户承担的费用
    var _validateMoney  =  function(){

        var totalExpenses = $("input[name='totalExpenses']").val();//费用总额
        var cusAssumeFee = $("input[name='cusAssumeFee']").val();//收款金额
        if(CalcUtil.compare(totalExpenses,cusAssumeFee)==-1){
            alert("费用总额应该大于等于客户承担的费用!",0);
            return false;
        }
        return true;
    }


    /**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		myclose : _myclose,
		ajaxSave:_ajaxSave,
		finForminput:_finForminput,
		selectLitigationApply:_selectLitigationApply,
		finForminOut:_finForminOut,
		selectLitigationInout:_selectLitigationInout,
        selectBusBankNo:_selectBusBankNo,
        initSelectBusBankNo:_initSelectBusBankNo,
        validateDate:_validateDate,
        validateMoney:_validateMoney,
		
	};
}(window, jQuery);
