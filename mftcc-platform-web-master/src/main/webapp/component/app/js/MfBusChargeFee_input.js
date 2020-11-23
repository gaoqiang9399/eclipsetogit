;
var MfBusChargeFee_input = function(window, $) {
	var _init = function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		if(typeof (entranceNo) == 'undefined' || entranceNo == ''){
            $("input[name=guaranteeAmt]").attr("title",guaranteeAmtMsg);
            $("input[name=guaranteeAmtTax]").attr("title",guaranteeAmtTaxMsg);
            if(busModel == "12"){
                $("input[name=handAmt]").attr("title",handAmtMsg);
                $("input[name=handAmtTax]").attr("title",handAmtTaxMsg);
            }else{
                $("input[name=reviewAmt]").attr("title",reviewAmtMsg);
                $("input[name=reviewAmtTax]").attr("title",reviewAmtTaxMsg);
            }
        }
        // 707 缴费环节，计算划型后，划型应不能修改
       // $("select[name='econType']").attr("disabled","");
        $("select[name='econType']").attr("disabled","disabled");
	};
	
	var _submitForm = function(obj) {
        $("select[name='econType']").removeAttr("disabled");
        if(busModel != "12") {
            if (!_getFeiCha()) {
                return false;
            }
        }
		var dataParam = JSON.stringify($(obj).serializeArray());
        var url = $(obj).attr("action");
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var invoiceType =  $("select[name='invoiceType']").val();
            if(invoiceType=="2"){
                    var area= $("input[name='area']").val();
                    var contactsTel= $("input[name='contactsTel']").val();
                    var payBank= $("input[name='payBank']").val();
                    var payBankNo= $("input[name='payBankNo']").val();
                    if(area==""||contactsTel==""||payBank==""||payBankNo==""){
                        alert("您选择发票类型是专票，请完善客户信息（地址电话、开户行及账号）!",3);
                        return fa;
                    }
            }

            LoadingAnimate.start();
            $.ajax({
                url : url,
                data : { ajaxData : dataParam },
                type : 'post',
                dataType : 'json',
                async:false,
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        if(data.feeCollectType == '1'){
                            top.flag = true;
                            myclose_click();
                        }else{
                            window.location.href=webPath+"/mfBusFeeCollect/getListPage";
                        }

                    }else if (data.flag == "finish") {
                        top.flag = true;
                        myclose_click();
                    } else {
                        window.top.alert(data.msg, 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(data.msg, 0);
                }
            });
        }
		
	};
	//暂存
    var _updateForm = function(obj) {
        $("select[name='econType']").removeAttr("disabled");
        if(busModel != "12") {
            if (!_getFeiCha()) {
                return false;
            }
        }
        var dataParam = JSON.stringify($(obj).serializeArray());
        var url =webPath+ "/mfBusChargeFee/chargeUpdateAjax";
        //var flag = submitJsMethod($(obj).get(0), '');
        //if (flag) {
            LoadingAnimate.start();
            $.ajax({
                url : url,
                data : { ajaxData : dataParam },
                type : 'post',
                dataType : 'json',
                async:false,
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        if (data.flag == "success") {
                            // window.location.href = webPath + "/mfBusChargeFee/chargeFee?&appId=" + appId ;
                            $("input[name='chargeId']").val(data.chargeId);
                            $("select[name='econType']").attr("disabled","disabled");
                            window.top.alert(data.msg,1);
                        }else{
                            window.top.alert(data.msg, 0);
                        }
                        // window.location.href = webPath + "/mfBusChargeFee/chargeFee?&appId=" + appId ;
                        $("input[name='chargeId']").val(data.chargeId);
                        window.top.alert(data.msg,1);
                    } else {
                        alert(top.getMessage("FAILED_SAVE"), 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_SAVE"), 0);
                }
            });
        //}

    };
	
	var _setFeeMain = function(sysOrg){
		$("input[name=feeMainNo]").val(sysOrg.brNo);
		$("input[name=feeMainName]").val(sysOrg.brName);
	};

    //如果登记了到账金额，系统校验应≥应收担保费+应收评审费。
    var _getFeiCha = function () {
        var ruleFlag = true;
        var accountAmt = $("input[name=accountAmt]").val().replace(/,/g, "");
        var pactAmt = $("input[name=pactAmt]").val().replace(/,/g, "");
        var guaranteeAmt = $("input[name=guaranteeAmt]").val().replace(/,/g, "");
        var reviewAmt = $("input[name=reviewAmt]").val().replace(/,/g, "");
        var totalAmt = CalcUtil.add(Number(guaranteeAmt),Number(reviewAmt));
        if(Number(totalAmt) > Number(accountAmt)){
            alert("到账金额应大于应收担保费加应收评审费。",3);
            $("input[name=accountAmt]").val(0);
            ruleFlag = false;
        }
        if(Number(accountAmt) > Number(pactAmt)){
            alert("本次收费金额不能超过合同金额。",3);
            $("input[name=accountAmt]").val(0);
            ruleFlag = false;
        }
        return ruleFlag;
    };
    //计算总额
    var _getToTalAmt = function () {
        var guaranteeAmt = $("input[name=guaranteeAmt]").val().replace(/,/g, "");
        if("12" != busModel){
            var reviewAmt = $("input[name=reviewAmt]").val().replace(/,/g, "");
            var accountAmt = CalcUtil.add(Number(guaranteeAmt),Number(reviewAmt));
            $("input[name=accountAmt]").val(accountAmt);
            _getFeiCha();
        }else{
            var handAmt = $("input[name=handAmt]").val().replace(/,/g, "");
            var bond = $("input[name=bond]").val().replace(/,/g, "");
            var accountAmt = CalcUtil.add(CalcUtil.add(Number(handAmt),Number(bond)),guaranteeAmt);
            $("input[name=accountAmt]").val( CalcUtil.formatMoney(accountAmt,2));
        }
    };

    function _selectBankAcc(){
        selectCusBankAccDialog(getBankAccInfoArtDialog,"选择公司账号","03");
    }
    function getBankAccInfoArtDialog(accountInfo){
        var accountNo = accountInfo.accountNo;
        var space = " ";
        var formatAccountNo = accountNo.substring(0,4)+space+accountNo.substring(4,8)+space+accountNo.substring(8,12)+space+accountNo.substring(12,16)+space+accountNo.substring(16);
        $("input[name='collectAccount']").val(accountNo);
        $("input[name='collectBank']").val(accountInfo.bank);
        $("input[name='collectAccName']").val(accountInfo.accountName);
        $("input[name=collectAccId]").val(accountInfo.id);//银行卡Id
    };
    var _selectInvoiceInfog=function(callback){
        var url = "/mfCusInvoiceMation/getList?cusNo="+cusNo;
        dialog({
            id:'invoiceDialog',
            title:"选择开票信息",
            url:webPath+url,
            width:900,
            height:400,
            backdropOpacity:0,
            onshow:function(){
                this.returnValue = null;
            },onclose:function(){
                if(this.returnValue){
                    _selectBack(this.returnValue);
                }
            }
        }).showModal();
    };
    var _getProjectSize=function(obj){
        var wayClass =$("input[name='wayClass']").val();
        var empCnt =$("input[name='empCnt']").val();
        var saleAmt =$("input[name='saleAmt']").val();
        var assetsAmt =$("input[name='assetsAmt']").val();
        if(wayClass==""){
            alert("请选择行业分类",3);
            return;
        }
        if(wayClass.length!=5){
            alert("请去客户工商信息重新完善行业分类信息！",3);
            return;
        }
        if(empCnt==""){
            alert("请填写员工人数",3);
            return;
        }
        if(saleAmt==""){
            alert("请填写最近12个月销售收入",3);
            return;
        }
        if(assetsAmt==""){
            alert("请填写最近一期资产总额",3);
            return;
        }
        saleAmt = saleAmt.replace(/,/g,"");
        assetsAmt = assetsAmt.replace(/,/g,"");
        $.ajax({
            url : webPath+"/mfBusChargeFee/getProjectSizeAjax",
            data : { wayClass : wayClass, empCnt:empCnt,saleAmt:saleAmt,assetsAmt:assetsAmt},
            type : 'post',
            dataType : 'json',
            async:false,
            success : function(data) {
                if (data.flag == "success") {
                    if (data.flag == "success") {
                        $("select[name='econType']").removeAttr("disabled");
                        $("select[name='econType']").val(data.projectSize);
                        $("select[name='econType']").attr("disabled","disabled");
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                } else {
                    alert(top.getMessage("FAILED_SAVE"), 0);
                }
            },
            error : function() {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_SAVE"), 0);
            }
        });
    };

    //回调
    var _selectBack=function(mfCusInvoiceMation){
        var taxpayerNo=mfCusInvoiceMation.taxpayerNo;
        var address=mfCusInvoiceMation.address;
        var tel=mfCusInvoiceMation.tel;
        var bankName=mfCusInvoiceMation.bankName;
        var accountNumber=mfCusInvoiceMation.accountNumber;
        var id = mfCusInvoiceMation.id;
        $("input[name=payTaxesNo]").val(taxpayerNo);
        $("input[name=area]").val(address);
        $("input[name=contactsTel]").val(tel);
        $("input[name=payBank]").val(bankName);
        $("input[name=payBankNo]").val(accountNumber);
        $("input[name=payTaxesId]").val(id);
    };

    var _calcFeeAmt = function () {
        var feeCollectType = $("select[name='feeChargeType'] option:selected").val();
        if(typeof(feeCollectType) != 'undefined' && feeCollectType == '3'){//第二年收费
            var pactId = $("input[name='pactId']").val();
            $.ajax({
                url : webPath+"/mfBusChargeFee/calcSecondFeeAmt?pactId="+pactId,
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                        $("input[name='guaranteeAmt']").val(data.guaranteeAmt);
                        $("input[name='reviewAmt']").val(data.reviewAmt);
                        $("input[name='guaranteeAmtTax']").val(data.guaranteeAmtTax);
                        $("input[name='reviewAmtTax']").val(data.reviewAmtTax);

                        $("input[name='guaranteeAmt']").attr("title",data.guaranteeAmtTxt);
                        $("input[name='reviewAmt']").attr("title",data.reviewAmtTxt);
                        $("input[name='guaranteeAmtTax']").attr("title",data.guaranteeAmtTaxTxt);
                        $("input[name='reviewAmtTax']").attr("title",data.reviewAmtTaxTxt);
                    }else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                    LoadingAnimate.stop();
                    alert(top.getMessage("ERROR_SERVER"), 0);
                }
            });
        }else{
            // $("input[name='guaranteeAmt']").val("");
            // $("input[name='reviewAmt']").val("");
            // $("input[name='guaranteeAmtTax']").val("");
            // $("input[name='reviewAmtTax']").val("");
            //
            // $("input[name='guaranteeAmt']").removeAttr("title");
            // $("input[name='reviewAmt']").removeAttr("title");
            // $("input[name='guaranteeAmtTax']").removeAttr("title");
            // $("input[name='reviewAmtTax']").removeAttr("title");
        }

    }

    var _closeFeeChare = function () {
        var feeCollectType = $("select[name='feeChargeType'] option:selected").val();
        if(typeof(feeCollectType) == 'undefined' || feeCollectType == '1') {
            myclose_click();
        }else{
            window.location.href=webPath+"/mfBusFeeCollect/getListPage";
        }
    }
    function _selectBondBankAcc(){
        selectCusBankAccDialog(getBoodBankAccInfoArtDialog,"选择保证金账号","04");
    };
    function getBoodBankAccInfoArtDialog(accountInfo){
        var accountNo = accountInfo.accountNo;
        var space = " ";
        var formatAccountNo = accountNo.substring(0,4)+space+accountNo.substring(4,8)+space+accountNo.substring(8,12)+space+accountNo.substring(12,16)+space+accountNo.substring(16);
        $("input[name='bondAccount']").val(accountNo);
        $("input[name='bondBank']").val(accountInfo.bank);
        $("input[name='bondAccName']").val(accountInfo.accountName);
        $("input[name='bondAccId']").val(accountInfo.id);//银行卡Id
    };
	return {
		init : _init,
		submitForm : _submitForm,
		setFeeMain :_setFeeMain,
        getFeiCha :_getFeiCha,
        selectBankAcc:_selectBankAcc,
        selectBondBankAcc:_selectBondBankAcc,
        selectInvoiceInfog:_selectInvoiceInfog,
        getToTalAmt:_getToTalAmt,
        calcFeeAmt:_calcFeeAmt,
        closeFeeChare:_closeFeeChare,
        updateForm:_updateForm,
        getProjectSize:_getProjectSize
	};
}(window, jQuery);
