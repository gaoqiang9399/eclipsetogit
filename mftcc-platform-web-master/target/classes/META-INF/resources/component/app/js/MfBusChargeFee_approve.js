;
var MfBusChargeFee_approve = function(window, $) {
	var _init = function() {

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});

	};
	
	var _submitForm = function(obj) {
	    if(busModel != "12"){
            if(!_getFeiCha()){
                return false;
            }
        }
		var dataParam = JSON.stringify($(obj).serializeArray());
        var url = $(obj).attr("action");
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            LoadingAnimate.start();
            $.ajax({
                url : url,
                data : { ajaxData : dataParam ,chargeId:chargeIdStr,taskId:taskId},
                type : 'post',
                dataType : 'json',
                async:false,
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        top.flag = true;
                        $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",webPath+"/sysTaskInfo/getListPage?entranceNo=1");
                        myclose_task();
                    }
                    else if (data.flag == "finish") {
                        top.flag = true;
                        myclose_task();
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    window.top.alert(top.getMessage("FAILED_SAVE"), 0);
                }
            });
        }
		
	};
	
	var _setFeeMain = function(sysOrg){
		$("input[name=feeMainNo]").val(sysOrg.brNo);
		$("input[name=feeMainName]").val(sysOrg.brName);
	};

    //判断实收费用是否大于应收费用
    var _getFeiCha = function () {
        var ruleFlag = true;
        var accountAmt = $("input[name=accountAmt]").val().replace(/,/g, "");
        var pactAmt = $("input[name=pactAmt]").val().replace(/,/g, "");
        var guaranteeAmt = $("input[name=guaranteeAmt]").val().replace(/,/g, "");
        var reviewAmt = $("input[name=reviewAmt]").val().replace(/,/g, "");
        var totalAmt = CalcUtil.add(Number(guaranteeAmt),Number(reviewAmt));
        if(Number(totalAmt) > Number(accountAmt)){
            alert("到账金额应大于应收担保费加应收评审费。",1);
            $("input[name=accountAmt]").val(0);
            ruleFlag = false;
        }
        if(Number(accountAmt) > Number(pactAmt)){
            alert("本次收费金额不能超过合同金额。",1);
            $("input[name=accountAmt]").val(0);
            ruleFlag = false;
        }
        return ruleFlag;
    };

    function _selectBankAcc(){
        var cusNo = $("input[name='cusNo']").val();
        selectBankAccDialog(getBankAccInfoArtDialog,cusNo);
    }
    function getBankAccInfoArtDialog(accountInfo){
        var accountNo = accountInfo.accountNo;
        var space = " ";
        var formatAccountNo = accountNo.substring(0,4)+space+accountNo.substring(4,8)+space+accountNo.substring(8,12)+space+accountNo.substring(12,16)+space+accountNo.substring(16);
        $("input[name='collectAccount']").val(formatAccountNo);
        $("input[name='collectBank']").val(accountInfo.bank);
        $("input[name='collectAccName']").val(accountInfo.accountName);
        $("input[name=collectAccId]").val(accountInfo.id);//银行卡Id
    };
    //计算总额
    var _getToTalAmt = function () {
        var guaranteeAmt = $("input[name=guaranteeAmt]").val().replace(/,/g, "");
        var reviewAmt = $("input[name=reviewAmt]").val().replace(/,/g, "");
        var accountAmt = CalcUtil.add(Number(guaranteeAmt),Number(reviewAmt));
        $("input[name=accountAmt]").val(accountAmt);
        _getFeiCha();
    };
    var _calRefundAmt =  function(){
        $("input[name='refundAmt']").val("");
        var reguaranteeAmt = parseFloat($("input[name='guaranteeAmt']").val().replace(/,/g,''));//应收担保费
        var actualReceivedAmt = parseFloat($("input[name='actualReceivedAmt']").val().replace(/,/g,''));//实际到账金额
        if("12" != busModel){
            var reassessAmt = parseFloat($("input[name='reviewAmt']").val().replace(/,/g,''));//应收评审费
            if(actualReceivedAmt > (reguaranteeAmt + reassessAmt)){
                var refundAmt = actualReceivedAmt - (reguaranteeAmt + reassessAmt);
                $("input[name='refundAmt']").val(refundAmt.toFixed(2));
            }
        }else{
            var accountAmt = parseFloat($("input[name='accountAmt']").val().replace(/,/g,''));//应收总额
            if(actualReceivedAmt > accountAmt){
                var refundAmt = actualReceivedAmt - accountAmt;
                $("input[name='refundAmt']").val(refundAmt.toFixed(2));
            }else{
                $("input[name='refundAmt']").val(0.00);
            }
        }
    }
	return {
		init : _init,
		submitForm : _submitForm,
		setFeeMain :_setFeeMain,
        getToTalAmt:_getToTalAmt,
        getFeiCha :_getFeiCha,
        selectBankAcc:_selectBankAcc,
        calRefundAmt:_calRefundAmt
	};
}(window, jQuery);
