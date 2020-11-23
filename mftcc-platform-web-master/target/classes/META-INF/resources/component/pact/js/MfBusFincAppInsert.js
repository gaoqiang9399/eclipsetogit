function checkIfTrue(){
	var putoutAmt = $("[name=putoutAmt]").val();
    var pactAmt;
    var pactString;
	if($("[name=usableFincAmt]").length > 0){					
		pactAmt =  $("[name=usableFincAmt]").val();
		pactString = "可支用金额";
	}
	putoutAmt = putoutAmt.replace(/,/g, "");
	pactAmt = pactAmt.replace(/,/g, "");
	if(parseFloat(pactAmt)<parseFloat(putoutAmt)){
		$("input[name=putoutAmt]").val("");
		window.top.alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"放款金额" , "timeTwo": pactString}), 3);
		return false;
	}
	if(parseFloat(putoutAmt)<=0){
		$("input[name=putoutAmt]").val("");
		window.top.alert(top.getMessage("NOT_SMALL_TIME",{"timeOne":"放款金额" , "timeTwo": "0.00"}), 3);
        return false;
	}
    return true;

};
//此方法是应用于选择的借据结束日期不能小于合同结束日期
	function checkTimeMax(){
	fPopUpCalendarDlg({max: intstEndDateShow,min: intstBeginDateShow,choose:checkIntstEndDate});
	}


function checkIntstEndDate(){
	var intstEndDate=$("input[name=intstEndDateShow]").val();
	intstEndDate=intstEndDate.replace(/-/g,"");
	if(parseInt(intstEndDate)>parseInt(endDate)){
		window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"借据结束日期","timeTwo":"合同结束日期"}), 0);
		$("input[name=intstEndDateShow]").val("");
		return false;
	}
    var intstBeginDate=$("input[name=intstBeginDate]").val();
	if(intstBeginDate != '' && typeof (intstBeginDate) != 'undefined'){
        intstBeginDate=intstBeginDate.replace(/-/g,"");
        if(parseInt(intstBeginDate)>parseInt(intstEndDate)){
            window.top.alert(top.getMessage("NOT_SMALL_TIME", {"timeOne":"借据结束日期","timeTwo":"借据开始日期"}), 0);
            $("input[name=intstEndDateShow]").val("");
            return false;
        }
	}
	//校验借据结束日期是否在借据期限范围之内
	validateFincTerm(intstEndDate);
};

function checkIntstBeginDate(){
    var intstBeginDate=$("input[name=intstBeginDate]").val();
    intstBeginDate=intstBeginDate.replace(/-/g,"");
    var intstEndDate=$("input[name=intstEndDateShow]").val();
    if(intstEndDate != '' && typeof (intstEndDate) != 'undefined'){
        intstEndDate=intstEndDate.replace(/-/g,"");
        if(parseInt(intstBeginDate)>parseInt(intstEndDate)){
            window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"借据开始日期","timeTwo":"借据结束日期"}), 0);
            $("input[name=intstBeginDate]").val("");
            return false;
        }
    }
    //校验借据结束日期是否在借据期限范围之内
    validateFincTerm(intstEndDate);
};


function mfBusFincAppFPopUpCalendarDlg() {
    fPopUpCalendarDlg({
        min: beginDateAccount,
        max: new Date().toLocaleDateString(),
        choose: checkIntstBeginDateAndCountIntstEndDate
    })
}


function fPopUpCalendarDlgByPactDate() {

    pactBeginDate = pactBeginDate.replace(/-/g, "");
    var pactBeginDateNew = pactBeginDate.substring(0, 4) + "/" + pactBeginDate.substring(4, 6) + "/" + pactBeginDate.substring(6, 8);

    fPopUpCalendarDlg({
        min: pactBeginDateNew,
        max: new Date().toLocaleDateString(),
        choose: checkIntstBeginDateAndCountIntstEndDate
    })
};

//改变开始日期  通过开始日期与期限  得出借据的结束日期与借据的展示结束日期
//pactEndDateShowFlag：借据结束日期展示  1-显示结束日期 2-显示结束日期减一天,3-实际结束日期减一天，显示结束日期再减一天
function checkIntstBeginDateAndCountIntstEndDate() {

    var intstBeginDate = $("input[name=intstBeginDate]").val();
    intstBeginDate = intstBeginDate.replace(/-/g, "");  //开始日期

    var term = $("input[name=termMonth]").val();//期限
    //计算出借据的结束日期
    $.ajax({
        url: webPath + "/mfBusFincApp/getFincInststEndDateInfoMapAjax",
        data: {"intstBeginDate": intstBeginDate, "appId": appId, "pactId": pactId, "term": term},
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.flag == "success") {
                var intstEndDate = data.intstEndDate;
                var intstEndDateShow = data.intstEndDateShow;
                $("input[name=intstEndDateShow]").val(intstEndDateShow);
                $("input[name=intstEndDate]").val(intstEndDate);
            } else {
                $("input[name=intstBeginDate]").val("");
                window.top.alert(data.msg, 0);
            }
        }, error: function () {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
};


//改变结束日期  得期限
function changeIntstEndDateShowGetTermIntstEndDate() {

    var intstBeginDate = $("input[name=intstBeginDate]").val();
    intstBeginDate = intstBeginDate.replace(/-/g, "");  //开始日期


    var intstEndDateShow = $("input[name=intstEndDateShow]").val();
    if (intstEndDateShow != '' && typeof (intstEndDateShow) != 'undefined') {
        intstEndDateShow = intstEndDateShow.replace(/-/g, "");
        if (parseInt(intstBeginDate) > parseInt(intstEndDateShow)) {
            window.top.alert("借据结束日期不可大于借据开始日期", 0);
            $("input[name=intstEndDateShow]").val("");
            return false;
        }
    }
    $.ajax({
        url: webPath + "/mfBusFincApp/getTermByIntstEndDateAndIntstBeginDate",
        data: {
            "intstBeginDate": intstBeginDate,
            "appId": appId,
            "pactId": pactId,
            "intstEndDateShow": intstEndDateShow
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.flag == "success") {
                var intstEndDate = data.intstEndDate;
                var fincTerm = data.fincTerm;
                $("input[name=termMonth]").val(fincTerm);
                intstEndDate = intstEndDate.substring(0, 4) + "-" + intstEndDate.substring(4, 6) + "-" + intstEndDate.substring(6, 8);
                $("input[name=intstEndDate]").val(intstEndDate);
            } else {
                $("input[name=intstEndDateShow]").val("");
                window.top.alert(data.msg, 0);
            }
        }, error: function () {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
};


//修改期限
function changeTermIntstEndDate() {

    var intstBeginDate = $("input[name=intstBeginDate]").val();
    intstBeginDate = intstBeginDate.replace(/-/g, "");  //开始日期

    var term = $("input[name=termMonth]").val();//期限

    validateFincTermNew(term);
    //计算出借据的结束日期
    $.ajax({
        url: webPath + "/mfBusFincApp/getFincInststEndDateInfoMapAjax",
        data: {"intstBeginDate": intstBeginDate, "appId": appId, "pactId": pactId, "term": term},
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.flag == "success") {
                var intstEndDate = data.intstEndDate;
                var intstEndDateShow = data.intstEndDateShow;
                /*intstEndDate = intstEndDate.substring(0, 4) + "-" + intstEndDate.substring(4, 6) + "-" + intstEndDate.substring(6, 8);
                intstEndDateShow = intstEndDateShow.substring(0, 4) + "-" + intstEndDateShow.substring(4, 6) + "-" + intstEndDateShow.substring(6, 8);*/
                $("input[name=intstEndDateShow]").val(intstEndDateShow);
                $("input[name=intstEndDate]").val(intstEndDate);
            } else {
                $("input[name=termMonth]").val("");
                window.top.alert(data.msg, 0);
            }
        }, error: function () {
            window.top.alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
};

//校验期限是否正确
function validateFincTermNew(term) {
    jQuery.ajax({
        url: webPath + "/mfBusFincApp/validateFincTermNewAjax",
        data: {term: term, appId: appId},
        success: function (data) {
            if (data.flag == "success") {

            } else {
                window.top.alert(data.msg, 0);
                $("input[name=termMonth]").val("");
                return false;
            }
        }
    });
}

//校验借据结束日期是否在借据期限范围之内
function validateFincTerm(intstEndDate){
	jQuery.ajax({
		url:webPath+"/mfBusFincApp/validateFincTermAjax",
		data:{ajaxData:intstEndDate,appId:appId},
		success:function(data){
			if(data.flag == "success"){
			}else{
				window.top.alert(data.msg, 0);
				//$("input[name=intstEndDate]").val("");
                $("input[name=intstEndDateShow]").val("");
				return false;
			}
		}
	});
}

function checkRepayFixDate(obj){
	var name = $(obj).attr("name");
	var title = $(obj).attr("title").split("(")[0];
	//固定还款日
	if(name=="repayDateDef"){
		var repayDateDefVal = $(obj).val();
		if(parseFloat(repayDateDefVal)<parseFloat(1)||parseFloat(repayDateDefVal)>parseFloat(29)){
			$(obj).val(0);
			alert(title+"必须在"+new Number(1)+"到"+new Number(29)+"之间",0);
		}
	}
};

;
var MfBusFincAppInsert;
MfBusFincAppInsert = function (window, $) {
    var _init;
    _init = function () {
        //只展示无需带产品
        bindVouTypeByKindNo($("input[name=vouType]"), '');
        //滚动条
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
        //是否要走放款审批，走放款审批并且要指派第一个审批人员时，展示“指派审批人员”字段
        if (processId == null || processId == '') {
            $("[name=firstApprovalUserName]").parents("tr").remove();// 字段td
        }

        if ($("[name=putoutAmt]").length > 0) {
            $("[name='putoutAmt']").bind("change", function () {
                checkIfTrue();
            });
        }
      //  MfBusBankAccCom.bankAccInit();

        //还款方式处理
        var $repayObj = $("input[name=repayType]");
        if ($repayObj.is(':visible')) {
            $("input[name=repayType]").popupSelection({
                searchOn: false,//启用搜索
                inline: false,//下拉模式
                multiple: false,//多选选
                title: "还款方式",
                valueClass: "show-text",
                items: ajaxData.repayTypeMap,
                changeCallback: function (obj, elem) {
                }
            });
            if (typeof($repayObj.attr('readonly')) != 'undefined') {
                $repayObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
            }
        }

        var $repayDateSetObj = $("input[name=repayDateSet]");
        if ($repayDateSetObj.is(':visible')) {
            $("input[name=repayDateSet]").popupSelection({
                searchOn: false,//启用搜索
                inline: false,//下拉模式
                multiple: false,//多选选
                title: "还款日设置",
                valueClass: "show-text",
                items: ajaxData.repayDateSetMap,
                changeCallback: function (elem) {
                    var callBackVal = elem.data("values").val();//1_1：贷款发放日  2_2：月末 3_3：固定还款日
                    var callBackValArr = callBackVal.split("_");
                    if (callBackValArr[1] == "3") {
                        $("input[name=repayDateDef]").parents("td").show();// 字段td
                        $("input[name=repayDateDef]").parents("td").prev("td").show();// 标签td
                        $("input[name=repayDateDef]").removeAttr("readonly");
                    } else {
                        $("input[name=repayDateDef]").parents("td").hide();// 字段td
                        $("input[name=repayDateDef]").parents("td").prev("td").hide();// 标签td
                        $("input[name=repayDateDef]").attr("readonly", "readonly");
                    }
                }
            });

            if (typeof($repayDateSetObj.attr('readonly')) != 'undefined') {
                $repayDateSetObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
            }
            //初始化判断如果不是是固定还款日（3_3），则为repayDateDef为只读
            if ($repayDateSetObj.attr('value') != '3_3') {
                $("input[name=repayDateDef]").parents("td").hide();// 字段td
                $("input[name=repayDateDef]").parents("td").prev("td").hide();// 标签td
                $("input[name=repayDateDef]").attr("readonly", "readonly");
            }
        }
        //存出保证金账号
        $("input[name=depositOutAccountId]").popupSelection({//账号选择
            searchOn: true, // false-不启用搜索，true-启用搜索
            inline: true, // true-内联,false-弹出
            ajaxUrl: webPath + "/mfCusBankAccManage/getBankAccData?cusNo=" + cusNoFund,//+"&useType:9"
            //items:data.items,
            multiple: false, // false-单选,true-复选
        });
        //贷款投向选择组件
        $("select[name=fincUse]").popupSelection({
            searchOn : true,//启用搜索
            inline : true,
            items : fincUse,
            multiple : false,
            //单选
        });
    };

    //获取当前系统时间
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }


    //放款申请保存方法
    var _ajaxInsert = function (obj, temporaryStorage) {
        if (parseFloat($("[name=putoutAmt]").val()) <= 0) {
            alert("申请金额必须大于0！", 0);
            return;
        }
        if (!checkIfTrue()) {
            return;
        }
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            //规则验证
            var ruleCusNo = $("input[name=cusNo]").val();
            var rulePutoutAmt = $("input[name=putoutAmt]").val().replace(/,/g, "");
            var parmData = {
                'nodeNo': 'putout_apply',
                'relNo': ruleCusNo,
                'cusNo': ruleCusNo,
                'putoutAmt': rulePutoutAmt
            };
            $.ajax({
                url: webPath + "/riskForApp/getNodeRiskDataForBeginAjax",
                data: {ajaxData: JSON.stringify(parmData)},
                success: function (data) {
                    if (temporaryStorage == '1') {// 暂存
                        _validateAccIsModify(obj, temporaryStorage);
                    } else if (data.exsitRefused == true) {// 存在业务拒绝
                        top.window.openBigForm(webPath + '/riskForApp/preventList?relNo=' + ruleCusNo, '风险拦截信息', function () {
                        });
                    } else if (data.exsitFX == true) {//存在风险项
                        alert(top.getMessage("CONFIRM_DETAIL_OPERATION", {
                            "content": data.fxlist,
                            "operation": "放款申请"
                        }), 2, function () {
                            _validateAccIsModify(obj, temporaryStorage);
                        });
                    } else {
                        if (typeof(advInterestBal) != "undefined" && advInterestBal != "") {
                            var reason = "预收利息余额:" + advInterestBal + "元,预收担保余额:" + advGuaranteeBal + "元";
                            alert(top.getMessage("CONFIRM_OPERATION_REASON", {
                                "reason": reason,
                                "operation": '提交下一步'
                            }), 2, function () {
                                _validateAccIsModify(obj, temporaryStorage);
                            });
                        } else {
                            alert(top.getMessage("CONFIRM_OPERATION", "提交下一步"), 2, function () {
                                _validateAccIsModify(obj, temporaryStorage);
                            });
                        }
                    }
                }, error: function () {
                }
            });
            /*var dataParam = JSON.stringify($(obj).serializeArray());
            //判断放款审批环节的账号信息与合同签约阶段的是否一致，不一致的情况下，给出提醒
            jQuery.ajax({
                url:webPath+"/mfBusFincApp/validateAccIsModifyAjax",
                data:{ajaxData:dataParam,appId:appId},
                success:function(data){
                    if(data.flag == "success"){
                        saveMfBusFincAppInfo(obj,dataParam,wkfAppId,taskId,appId,isNewBank);
                    }else{
                        alert(top.getMessage("NOT_ACCNO_SAME",data.msg),2,function(){
                            saveMfBusFincAppInfo(obj,dataParam,wkfAppId,taskId,appId,isNewBank);
                        });
                    }
                }
            });*/

        }
    };

    var _checkBorrowCode = function (obj) {
        var borrowCode = $("input[name='borrowCode']").val();
        if (typeof (borrowCode) != 'undefined' && borrowCode != '') {
            $.ajax({
                async: false,
                url: webPath + "/mfBusFincApp/validateBorrowCode",
                data: {borrowCode: borrowCode},
                success: function (data) {
                    if (data.flag == "success") {
                        if (data.exitFlag == 'true') {
                            alert(data.msg, 3);
                            $("input[name='borrowCode']").val('');
                        }
                    }
                },
                error: function () {
                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                }
            });
        }
    };
    // 验证账号信息是否一致
    var _validateAccIsModify = function (obj, temporaryStorage) {
        //保存表单数据时移除下拉框是disabled的属性控制
        $(obj).find("select").each(function () {
            $(this).removeAttr("disabled");
        });
        var dataParam = JSON.stringify($(obj).serializeArray());
        //判断放款审批环节的账号信息与合同签约阶段的是否一致，不一致的情况下，给出提醒
        jQuery.ajax({
            url: webPath + "/mfBusFincApp/validateAccIsModifyAjax",
            data: {ajaxData: dataParam, appId: appId},
            success: function (data) {
                if (data.flag == "success") {
                    saveMfBusFincAppInfo(obj, dataParam, wkfAppId, taskId, appId, isNewBank, temporaryStorage);
                } else {
                    alert(top.getMessage("NOT_ACCNO_SAME", data.msg), 2, function () {
                        saveMfBusFincAppInfo(obj, dataParam, wkfAppId, taskId, appId, isNewBank, temporaryStorage);
                    });
                }
            }
        });
    };

    var saveMfBusFincAppInfo = function (obj, dataParam, wkfAppId, taskId, appId, isNewBank, temporaryStorage) {
        var url = $(obj).attr("action");
        var newAccountNo = $("input[name='incomAccount']").val();
        if (newAccountNo != '' && newAccountNo != undefined) {
            newAccountNo = newAccountNo.trim().replace(/\s/g, "");
        }
        $("input[name='incomAccount']").val(newAccountNo);
        if (typeof (fincMainId) == "undefined") {
            fincMainId = "";
        }

        jQuery.ajax({
            url: url,
            data: {
                ajaxData: dataParam,
                wkfAppId: wkfAppId,
                taskId: taskId,
                appId: appId,
                isNewBank: isNewBank,
                fincMainId: fincMainId,
                'temporaryStorage': temporaryStorage,
                'nodeNoOld':nodeNo
            },
            success: function (data) {
                if (data.flag == "success") {
                    $.each(data, function (name, value) {
                        setFormEleValue(name, value);//调用公共js文件的方法表单赋值
                    });
                    top.alert(data.msg, 3);
                    top.putoutSaveFlag = true;
                    top.flag = true;
                    top.getInfoFlag = true;
                    top.htmlStr = data.htmlStr;
                    top.showType = '3';
                    top.getInfoUrl = webPath + "/mfBusFincApp/getFincApp?fincId=" + data.fincId;
                    top.title = '放款申请';
                    top.name = data.fincId;
                    top.fincSts = data.fincSts;
                    myclose_click();
                } else {
                    alert(data.msg, 0);
                }
            }, error: function (data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };

    //转账申请
    var _ajaxInsertTransferAcc = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            alert(top.getMessage("CONFIRM_OPERATION", "转账"), 2, function () {
                jQuery.ajax({
                    url: url,
                    data: {ajaxData: dataParam, appId: appId},
                    type: "POST",
                    dataType: "json",
                    beforeSend: function () {
                    }, success: function (data) {
                        LoadingAnimate.stop();
                        if (data.flag == "success") {
                            myclose_click();
                        } else {
                            alert(data.msg, 0);
                        }
                    }, error: function (data) {
                        alert(top.getMessage("FAILED_OPERATION", " "), 0);
                    }
                });
            });

        }
    };
    //账号变更
    var _bankAccUpdateAjax = function (obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            alert(top.getMessage("CONFIRM_OPERATION", "账号变更"), 2, function () {
                jQuery.ajax({
                    url: url,
                    data: {ajaxData: dataParam, id: id},
                    type: "POST",
                    dataType: "json",
                    beforeSend: function () {
                    }, success: function (data) {
                        LoadingAnimate.stop();
                        if (data.flag == "success") {
                            myclose_click();
                        } else {
                            alert(data.msg, 0);
                        }
                    }, error: function (data) {
                        alert(top.getMessage("FAILED_OPERATION", " "), 0);
                    }
                });
            });

        }
    };
    //根据放款金额和担保比例自动计算存出保证金
    var _setDepositOutMarginAmt = function () {
        var putoutAmt = Number($("input[name=putoutAmt]").val().replace(/,/g, ""));
        var guaranteeRate = $("input[name=guaranteeRate]").val();
        guaranteeRate = guaranteeRate / 100;
        if (putoutAmt != "" && guaranteeRate != "") {
            $("input[name=depositOutMarginAmt]").val(guaranteeRate * putoutAmt);
        }
    };
    //获取收款银行账号
    var _getBankCusAccMange = function () {
        $("input[name=collectAccount]").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/cwCusBankAccManage/getbusCwBankCusAccMangeAjax?cusNo=" + cusNo, // 请求数据URL
            valueElem: "input[name='collectAccount']",//真实值选择器
            title: "选择机构",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=collectAccId]").val(tmpData.id);
                $("input[name=collectAccName]").val(tmpData.accountName);
                $("input[name=collectBank]").val(tmpData.bank);
            },
            tablehead: {//列表显示列配置
                "accountNo": "账号",
                "accountName": "账号名称",
                "bank": "开户行"
            },
            returnData: {//返回值配置
                disName: "accountNo",//显示值
                value: "id"//真实值
            }
        });
    };

    //获取付款银行账号
    var _getPayBankCusAccMange = function () {
        $("input[name=paymentAccount]").popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl: webPath + "/cwCusBankAccManage/getbusCwBankCusAccMangeAjax?cusNo=" + cusNo, // 请求数据URL
            valueElem: "input[name='paymentAccId']",//真实值选择器
            title: "选择机构",//标题
            changeCallback: function (elem) {//回调方法
                var tmpData = elem.data("selectData");
                $("input[name=popspaymentAccId]").val(tmpData.id);
                $("input[name=paymentAccName]").val(tmpData.accountName);
                $("input[name=paymentBank]").val(tmpData.bank);
            },
            tablehead: {//列表显示列配置
                "accountNo": "账号",
                "accountName": "账号名称",
                "bank": "开户行"
            },
            returnData: {//返回值配置
                disName: "accountNo",//显示值
                value: "id"//真实值
            }
        });
    };

    //批量放款申请明细出账信息保存方法
    var _ajaxInsertForBatch = function (obj) {
        if (parseFloat($("[name=putoutAmt]").val()) <= 0) {
            alert("申请金额必须大于0！", 0);
            return;
        }
        if (!checkIfTrue()) {
            return;
        }
        //保存表单数据时移除下拉框是disabled的属性控制
        $(obj).find("select").each(function () {
            $(this).removeAttr("disabled");
        });
        var dataParam = JSON.stringify($(obj).serializeArray());
        //判断放款审批环节的账号信息与合同签约阶段的是否一致，不一致的情况下，给出提醒
        jQuery.ajax({
            url: webPath + "/mfBusFincApp/validateAccIsModifyAjax",
            data: {ajaxData: dataParam, appId: appId},
            success: function (data) {
                if (data.flag == "success") {
                    saveMfBusFincAppInfoForBatch(obj, dataParam, wkfAppId, appId, isNewBank);
                } else {
                    alert(top.getMessage("NOT_ACCNO_SAME", data.msg), 2, function () {
                        saveMfBusFincAppInfoForBatch(obj, dataParam, wkfAppId, appId, isNewBank);
                    });
                }
            }
        });
    };

    var saveMfBusFincAppInfoForBatch = function (obj, dataParam, wkfAppId, appId, isNewBank) {
        var url = $(obj).attr("action");
        var newAccountNo = $("input[name='incomAccount']").val();
        if (newAccountNo != '' && newAccountNo != undefined) {
            newAccountNo = newAccountNo.trim().replace(/\s/g, "");
        }
        $("input[name='incomAccount']").val(newAccountNo);
        jQuery.ajax({
            url: url,
            data: {ajaxData: dataParam, wkfAppId: wkfAppId, appId: appId, isNewBank: isNewBank, fincMainId: fincMainId},
            success: function (data) {
                if (data.flag == "success") {
                    top.fincAppFlag = true;
                    top.htmlStr = data.htmlStr;
                    top.usableFincAmt = data.usableFincAmt;
                    myclose_click();
                } else {
                    alert(data.msg, 0);
                }
            }, error: function (data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };

    return {
        init: _init,
        ajaxInsert: _ajaxInsert,
        ajaxInsertTransferAcc: _ajaxInsertTransferAcc,
        bankAccUpdateAjax: _bankAccUpdateAjax,
        setDepositOutMarginAmt: _setDepositOutMarginAmt,
        validateAccIsModify: _validateAccIsModify,
        getBankCusAccMange: _getBankCusAccMange,
        getPayBankCusAccMange: _getPayBankCusAccMange,
        checkBorrowCode: _checkBorrowCode,
        ajaxInsertForBatch: _ajaxInsertForBatch,
    };
}(window, jQuery);