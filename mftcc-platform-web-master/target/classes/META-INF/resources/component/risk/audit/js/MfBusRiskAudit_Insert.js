;
var MfBusRiskAudit_Insert = function(window, $) {
	var _init = function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	
	var _submitForm = function(obj) {
		var dataParam = JSON.stringify($(obj).serializeArray());
        var url = $(obj).attr("action");
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            LoadingAnimate.start();
            $.ajax({
                url : url,
                data : { ajaxData : dataParam },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        top.flag = true;
                        myclose_click();
                    }else if (data.flag == "finish") {
                        top.flag = true;
                        myclose_click();
                    } else {
                        alert(top.getMessage("FAILED_SAVE"), 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_SAVE"), 0);
                }
            });
        }
	};
	


    //如果登记了到账金额，系统校验应≥应收担保费+应收评审费。
    var _getFeiCha = function (obj) {
        var accountAmt = $("input[name=accountAmt]").val();
        var totalAmt = guaranteeAmt+reviewAmt;
        if(Number(totalAmt) > Number(accountAmt)){
            alert("到账金额应大于应收担保费加应收评审费。", 2);
            $("input[name=accountAmt]").val(0);
        }
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

    //打开登记页面
    var _chooseAuditConfig = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag) {
            var dataParam = JSON.stringify($(obj).serializeArray());
            var templateId = $("select[name='templateId']").val();
            url = webPath + '/mfBusRiskAudit/input?appId=' + appId+'&templateId='+templateId+'&busModel='+busModel;
            window.location.href= url;
        }
    };


	return {
		init : _init,
		submitForm : _submitForm,
        getFeiCha :_getFeiCha,
        selectBankAcc:_selectBankAcc,
        chooseAuditConfig:_chooseAuditConfig
	};
}(window, jQuery);
