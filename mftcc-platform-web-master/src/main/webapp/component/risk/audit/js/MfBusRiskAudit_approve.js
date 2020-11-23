;
var MfBusRiskAudit_approve = function(window, $) {
	var _init = function() {

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
        $('.hidden-content').addClass('hidden');
	};
	
	var _submitForm = function(obj) {
		var dataParam = JSON.stringify($(obj).serializeArray());
        var url = $(obj).attr("action");
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            LoadingAnimate.start();
            $.ajax({
                url : url,
                data : { ajaxData : dataParam ,auditId:auditIdStr,taskId:taskId},
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
                    }else if (data.flag == "finish") {
                        top.flag = true;
                        myclose_task();
                    } else {
                        alert(top.getMessage("FAILED_SAVE"), 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_SAVE"), 0);
                }
            });
            myclose_task();
        }
		
	};
	
	var _setFeeMain = function(sysOrg){
		$("input[name=feeMainNo]").val(sysOrg.brNo);
		$("input[name=feeMainName]").val(sysOrg.brName);
	};

    //判断实收费用是否大于应收费用
    var _getFeiCha = function (obj) {
        var fundsReceived = $("input[name=fundsReceived]").val();
        var feeTotalAmt = $("input[name=feeTotalAmt]").val();
        if(Number(fundsReceived) > Number(feeTotalAmt)){
            alert("实收费用不能大于应收费用", 2);
            $("input[name=fundsReceived]").val(feeTotalAmt);
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

	return {
		init : _init,
		submitForm : _submitForm,
		setFeeMain :_setFeeMain,
        getFeiCha :_getFeiCha,
        selectBankAcc:_selectBankAcc
	};
}(window, jQuery);
