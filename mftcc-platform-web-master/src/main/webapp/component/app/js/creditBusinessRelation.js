;
var creditBusinessRelation = function(window,$){
    var _init = function(){
        var kindNo = $("[name='kindNo']").val();
        //授信项目选择
        $("input[name=creditProjectId]").popupSelection({
            searchOn : true, // false-不启用搜索，true-启用搜索
            inline : true, // true-内联,false-弹出
            ajaxUrl : webPath+"/mfCusCreditContract/getCreditPactListFinishedAjax?creditModel=2&kindNo="+ kindNo,
            multiple : false, // false-单选,true-复选
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name=creditProjectName]"));
                _getCreditPactInfo($("input[name=creditProjectId]").val());
            },
        });
/*
        if($("input[name=creditPactId]").is(':visible')){
            //授信合同选择
            $("input[name=creditPactId]").popupSelection({
                searchOn : true, // false-不启用搜索，true-启用搜索
                inline : true, // true-内联,false-弹出
                ajaxUrl : webPath+"/mfCusCreditContract/getCreditPactListFinishedAjax?creditModel=1&cusNo="+cusNo,
                multiple : false, // false-单选,true-复选
                changeCallback:function(elem){//回调方法
                    $("input[name=creditPactNo]").val($("input[name=creditPactId]").parents("td").find(".pops-value").html());
                    $.ajax({
                        url : webPath+"/mfCusCreditContract/getCreditPactInfoByPactAjax",
                        data : {id:$("input[name=creditPactId]").val()},
                        type : "post",
                        dataType : "json",
                        success : function(data) {
                            //LoadingAnimate.stop();
                            if (data.flag == "success") {
                                var mfCusCreditContract = data.mfCusCreditContract;
                                $("input[name=creditPactAmt]").val(mfCusCreditContract.authBal);
                            }
                        },
                        error : function(data) {
                            //loadingAnimate.stop();
                            window.top.alert(top.getMessage("ERROR_INSERT"), 0);
                        }
                    });
                },
            });
        }
*/
        flag=0;
    };
    var flag=0;//是否加载过资金机构选择组件0未加载过1加载过
    var _getCreditPactInfo = function(id){
        $.ajax({
            url : webPath+"/mfCusCreditContract/getCreditPactInfoByPactAjax",
            data : {id:id},
            type : "post",
            dataType : "json",
            success : function(data) {
                //LoadingAnimate.stop();
                if (data.flag == "success") {
                    var mfCusCreditContract = data.mfCusCreditContract;
                    if(data.ifExistAdjustCrediting == "1"){
                        alert("立项项目（" + mfCusCreditContract.projectName + "）额度正在调整中！",3);
                    }else{
                        var creditAppId = mfCusCreditContract.creditAppId;
                        $("input[name=creditAppId]").val(creditAppId)
                        $("input[name=creditProjectNo]").val(mfCusCreditContract.projectNo);
                        $("input[name=creditProjectName]").val(mfCusCreditContract.projectName);
                        $("input[name=projectMain]").val(mfCusCreditContract.cusName);
                        $("input[name=projectAmt]").val(mfCusCreditContract.creditSum);
                        $("input[name=bussRiskType]").val(mfCusCreditContract.projectType);
                        if(flag==0){
                            $("input[name=cusNoFund]").popupSelection({//资金机构选择
                                searchOn : true, // false-不启用搜索，true-启用搜索
                                inline : true, // true-内联,false-弹出
                                ajaxUrl : webPath+"/mfUserPermission/getCustomerInfoByCusTypeAjax?cusType=403",
                                multiple : false, // false-单选,true-复选
                                changeCallback:function(elem){//回调方法
                                    BASE.removePlaceholder($("input[name=cusNameFund]"));
                                    $("input[name=cusNameFund]").val($("input[name=cusNoFund]").parents("td").find(".pops-value").html());
                                    _getCusNoFundProjectPactInfo($("input[name=creditAppId]").val(),$("input[name=cusNoFund]").val());
                                },
                            });
                            flag=1;
                        }else{

                            $.ajax({
                                url : webPath+"/mfUserPermission/getCustomerInfoByCusTypeAjax?cusType=403",
                                type : "post",
                                dataType : "json",
                                success : function(data) {
                                    $("input[name=popscusNoFund]").popupSelection("updateItems",data.items);
                                }
                            });
                        }
                    }


                }
            },
            error : function(data) {
                //loadingAnimate.stop();
                window.top.alert(top.getMessage("ERROR_INSERT"), 0);
            }
        });
    };
    //获得资金机构项目信息
    var _getCusNoFundProjectPactInfo = function(creditAppId,cusNoFund){
        $.ajax({
            url : webPath+"/mfCusCreditChildContract/getCusNoFundProjectPactInfo",
            data : {creditAppId:creditAppId,cusNoFund:cusNoFund},
            type : "post",
            dataType : "json",
            success : function(data) {
                //LoadingAnimate.stop();
                var authBal = 0.00;
                if (data.flag == "success") {
                    var mfCusCreditChildContract = data.mfCusCreditChildContract;
                    authBal = mfCusCreditChildContract.authBal;
                }
                $("input[name=fundProjectAmt]").val(CalcUtil.formatMoney(authBal,2));
            },
            error : function(data) {
                //loadingAnimate.stop();
                window.top.alert(top.getMessage("ERROR_INSERT"), 0);
            }
        });
    };

    //验证申请金额和申请期限是否符合产品设置的申请金额和期限的范围
    var _checkAppAmt =function(obj) {
        var name = $(obj).attr("name");
        var title = $(obj).attr("title").split("(")[0];
        //申请金额
        if (name == "appAmt") {
            if (maxAmt != null && minAmt != null && maxAmt != ""
                && minAmt != "") {
                maxAmtNum = new Number(maxAmt);
                minAmtNum = new Number(minAmt);
                var appAmt = $(obj).val();
                appAmt = appAmt.replace(/,/g, "");
                if (parseFloat(appAmt) < parseFloat(minAmtNum)
                    || parseFloat(appAmt) > parseFloat(maxAmtNum)) {//判断申请金额是否在产品设置范围内
                    $(obj).val(null);
                    alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                        "info" : "产品设置",
                        "field" : title,
                        "value1" : fmoney(minAmtNum, 2),
                        "value2" : fmoney(maxAmtNum, 2)
                    }), 0);
                } else {
                    _checkCusNoFundCreditAmtAjax(parseFloat(appAmt));
                };
            };
        } else if (name == "fincRate") {//检测融资利率
            if (minFincRate != null && maxFincRate != null && minFincRate != ""
                && maxFincRate != "") {
                var maxFincRateNum = new Number(maxFincRate);
                var minFincRateNum = new Number(minFincRate);
                var fincRate = $(obj).val();
                if (parseFloat(fincRate) < parseFloat(minFincRateNum)
                    || parseFloat(fincRate) > parseFloat(maxFincRateNum)) {//判断申请金额是否在产品设置范围内
                    $(obj).val(null);
                    alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE", {
                        "info" : "产品设置",
                        "field" : title,
                        "value1" : minFincRateNum,
                        "value2" : maxFincRateNum
                    }), 0);
                }
            }
        }
    };
    //验证申请金额是否大于资金机构总额度和资金机构的项目额度
    var _checkCusNoFundCreditAmtAjax = function(appAmt){
        var cusNoFund = $("input[name=cusNoFund]").val();
        var creditPactId = $("input[name=creditPactId]").val();
        var creditProjectId = $("input[name=creditProjectId]").val();
        $.ajax({
            url : webPath+"/mfCusCreditContract/checkCreditPactAmtAjax",
            data : {creditPactId:creditPactId,creditProjectId:creditProjectId,cusNoFund:cusNoFund,appAmt:appAmt},
            type : "post",
            dataType : "json",
            success : function(data) {
                if (data.checkBool == false) {
                    $("input[name=appAmt]").val(null);
                    window.top.alert(data.msg, 0);
                }else{

                }
            },
            error : function(data) {
                window.top.alert(top.getMessage("ERROR_INSERT"), 0);
            }
        });
    };
    return{
        init:_init,
        checkCusNoFundCreditAmtAjax:_checkCusNoFundCreditAmtAjax,
        checkAppAmt:_checkAppAmt
    }
}(window,jQuery);