$(function() {
    //合并还款计划时到期结束日期限制编辑
    var multipleLoanPlanMerge = $("#multipleLoanPlanMerge").val();//合并还款计划
    if (multipleLoanPlanMerge == 1) {
        $("#endDate").attr("disabled", "disabled");
    }

});
var repayPlan_List = function(window, $) {
    var termNum = 0;//修改当前期数
    var repayPrcpDefault = 0;//用与存放应还本金初始值
    var planEndDateDefault = 0;//用与存放结束日期初始值
    var _init = function () {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                theme: "minimal-dark",
                updateOnContentResize: true
            }
        });
    };
    var _showEndDateInputValue = function (obj) {
        //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        var mfBusEditorRepayplanFlag = $("#mfBusEditorRepayplanFlag").val();
        if (mfBusEditorRepayplanFlag == 0) {//不能编辑
            return false;
        }
        var termNumArr = $(obj).attr("id").split("_");
        termNum = termNumArr[1];//获得当前期数
        $("#planEndDate_" + termNum).show();
        $("#spanPlanEndDate_" + termNum).hide();
        planEndDateDefault = $("#planEndDate_" + termNum).val();
    };
    var _showRepayPrcpInputValue = function (obj) {
        //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        var mfBusEditorRepayplanFlag = $("#mfBusEditorRepayplanFlag").val();
        if (mfBusEditorRepayplanFlag == 0) {//不能编辑
            return false;
        }

        var termNumArr = $(obj).attr("id").split("_");
        termNum = termNumArr[1];//获得当前期数
        $("#repayPrcp_" + termNum).show();
        $("#repayPrcp_" + termNum).focus();//获取焦点
        $("#spanRepayPrcp_" + termNum).hide();
        repayPrcpDefault = $("#repayPrcp_" + termNum).val();
    };
    var _showRepayIntsInputValue = function (obj) {
        //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        var mfBusEditorRepayplanFlag = $("#mfBusEditorRepayplanFlag").val();
        if (mfBusEditorRepayplanFlag == 0) {//不能编辑
            return false;
        }

        var termNumArr = $(obj).attr("id").split("_");
        termNum = termNumArr[1];//获得当前期数
        $("#repayIntst_" + termNum).show();
        $("#repayIntst_" + termNum).focus();//获取焦点
        $("#spanRepayIntst_" + termNum).hide();
    };

    var _showEndDateInputValueNew = function (obj) {
        //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        var mfBusEditorRepayplanFlag = $("#mfBusEditorRepayplanFlag").val();
        if (mfBusEditorRepayplanFlag == 0) {//不能编辑
            return false;
        }

        var termNumArr = $(obj).attr("id").split("_");
        termNum = termNumArr[1];//获得当前期数
        var outFlag = $("#outFlag_" + termNum).val();
        if (outFlag != '0') {
            return false;
        }
        $("#planEndDate_" + termNum).show();
        $("#spanPlanEndDate_" + termNum).hide();
        planEndDateDefault = $("#planEndDate_" + termNum).val();
    };
    var _showRepayPrcpInputValueNew = function (obj) {
        //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        var mfBusEditorRepayplanFlag = $("#mfBusEditorRepayplanFlag").val();
        if (mfBusEditorRepayplanFlag == 0) {//不能编辑
            return false;
        }

        var termNumArr = $(obj).attr("id").split("_");
        termNum = termNumArr[1];//获得当前期数
        var outFlag = $("#outFlag_" + termNum).val();
        if (outFlag != '0') {
            return false;
        }
        $("#repayPrcp_" + termNum).show();
        $("#repayPrcp_" + termNum).focus();//获取焦点
        $("#spanRepayPrcp_" + termNum).hide();
        repayPrcpDefault = $("#repayPrcp_" + termNum).val();
    };
    var _showRepayIntsInputValueNew = function (obj) {
        //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        var mfBusEditorRepayplanFlag = $("#mfBusEditorRepayplanFlag").val();
        if (mfBusEditorRepayplanFlag == 0) {//不能编辑
            return false;
        }

        var termNumArr = $(obj).attr("id").split("_");
        termNum = termNumArr[1];//获得当前期数
        var outFlag = $("#outFlag_" + termNum).val();
        if (outFlag != '0') {
            return false;
        }
        $("#repayIntst_" + termNum).show();
        $("#repayIntst_" + termNum).focus();//获取焦点
        $("#spanRepayIntst_" + termNum).hide();
    };
    //修改本金事件
    var _changeRepayPrcpValue = function (obj) {
        var repayPrcp = $("#repayPrcp_" + termNum).val();//获取修改后的本金
        if (repayPrcp == "" || repayPrcp == undefined) {
            alert(top.getMessage("FAILED_OPERATION", "应还本金不能为空"), 2);
            $("#repayPrcp_" + termNum).val(repayPrcpDefault);
            $("#repayPrcp_" + termNum).hide();
            $("#spanRepayPrcp_" + termNum).show();
            return false;
        }
        if (repayPrcp.indexOf('.') != -1 && repayPrcp.length - repayPrcp.indexOf('.') > 3) {
            alert("应还本金的小数位数不可大于两位", 3);
            $("#repayPrcp_" + termNum).val(repayPrcpDefault);
            $("#repayPrcp_" + termNum).hide();
            $("#spanRepayPrcp_" + termNum).show();
            return false;
        }
        var planListSize = $("#planListSize").val();//获取还款计划期数
        var loanAmt = $("#putoutAmtHidden").val();//借据金额
        var fincRate = $("#fincRate").val();//年利率
        var preTermNum = termNum - 1;//上期期号
        var intstModify = $("#input_hide_intst_modify").val();//利息修改状态
        var preRepayPrcpBalAfters = $("#repayPrcpBalAfter_" + preTermNum).val();//获取上期期末本金余额
        //处理修改第一期的本金超过本金余额
        if (termNum == "1") {
            var repayPrcpBalAfters = $("#repayPrcpBalAfter_" + termNum).val();
            preRepayPrcpBalAfters = CalcUtil.add(repayPrcpDefault, repayPrcpBalAfters);
        }
        if (parseInt(repayPrcp) > parseInt(preRepayPrcpBalAfters)) {
            //还原应还本金默认值
            $("#repayPrcp_" + termNum).val(repayPrcpDefault);
            $("#repayPrcp_" + termNum).hide();
            $("#spanRepayPrcp_" + termNum).show();
            alert(top.getMessage("FAILED_OPERATION", "应还本金不能大于" + preRepayPrcpBalAfters), 2);
            return false;
        } else if (parseInt(repayPrcp) < 0) {
            $("#repayPrcp_" + termNum).val(repayPrcpDefault);
            $("#repayPrcp_" + termNum).hide();
            $("#spanRepayPrcp_" + termNum).show();
            alert(top.getMessage("FAILED_OPERATION", "应还本金不能是负数"), 2);
            return false;
        }

        $("#spanRepayPrcp_" + termNum).html(repayPrcp);
        $("#repayPrcp_" + termNum).hide();
        $("#spanRepayPrcp_" + termNum).show();

        var multipleLoanPlanMerge = $("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
        var interestCollectType = $("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
        var repayDateSet = $("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日
        var yearDays = $("#yearDays").val();//计息天数
        var normCalcType = $("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息
        var secondNormCalcType = $("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
        var returnPlanPoint = $("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
        var returnPlanRound = $("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
        var instCalcBase = $("#instCalcBase").val();//还款利息计算基数：1-贷款金额、2-贷款余额
        var preInstCollectType = $("#preInstCollectType").val();
        var fincId = $("#fincId").val();//借据号
        if (preInstCollectType == null) {
            preInstCollectType = "";
        }
        var repayDateDef = $("#repayDateDef").val();
        if (repayDateDef == null) {
            repayDateDef = "";
        }

        //获取还款计划列表
        var planObjs = getPlanObjs();
        var repayPlanList = JSON.stringify(planObjs);
        var rulesNo = $("#rulesNo").val();
        //请求后台应还本金接口
        $.ajax({
            url: webPath + '/mfRepayPlan/changePlanByPrcpAjax',
            data: {
                "repayPlanList": repayPlanList,
                "appId": appId,
                "fincId": fincId,
                "planListSize": planListSize,
                "modTermNum": termNum,
                "loanAmt": loanAmt,
                "fincRate": fincRate,
                "intstModify": intstModify,
                "multipleLoanPlanMerge": multipleLoanPlanMerge,
                "interestCollectType": interestCollectType,
                "repayDateSet": repayDateSet,
                "yearDays": yearDays,
                "normCalcType": normCalcType,
                "secondNormCalcType": secondNormCalcType,
                "returnPlanPoint": returnPlanPoint,
                "returnPlanRound": returnPlanRound,
                "instCalcBase": instCalcBase,
                "preInstCollectType": preInstCollectType,
                "repayDateDef": repayDateDef,
                "rulesNo": rulesNo
            },
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            },
            success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    var mfBusFincAppChild = data.mfBusFincAppChild;
                    var repayPlanList = data.repayPlanList;
                    var intstModify = data.intstModify;
                    getPlanListHtml(repayPlanList);
                    $("#input_hide_intst_modify").val(intstModify);//设置修改状态
                    $("#planListSize").val(repayPlanList.length);
                    $("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
                    $("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息
                    //最后一期限制编辑
                    var planListSize = repayPlanList.length;
                    var lastPlanEndRate = "#tdPlanEndDate_" + planListSize;//最后一期结束日期
                    var lastRepayPrcp = "#tdRepayPrcp_" + planListSize;//最后一期应还本金
                    var lastRepayIntst = "#tdRepayIntst_" + planListSize;//最后一期应还利息
                    $(lastPlanEndRate).removeAttr('ondblclick');
                    $(lastRepayPrcp).removeAttr('ondblclick');
//					$(lastRepayIntst).removeAttr('ondblclick');
                } else {
                    window.top.alert(data.msg, 0);
                }
            }, error: function (data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };
    //修改利息事件
    var _changeRepayIntstValue = function (obj) {
        var repayIntst = $("#repayIntst_" + termNum).val();//获取修改后的利息
        if (repayIntst == "" || repayIntst == undefined) {
            $("#repayIntst_" + termNum).val(repayIntst);
            $("#repayIntst_" + termNum).hide();
            $("#spanRepayIntst_" + termNum).show();
            alert(top.getMessage("FAILED_OPERATION", "应还利息不能不能为空"), 2);
            return false;
        }
        var planListSize = $("#planListSize").val();//获取还款计划期数
        var loanAmt = $("#putoutAmtHidden").val();//借据金额
        var fincRate = $("#fincRate").val();//年利率

        if (parseInt(repayIntst) < 0) {
            $("#repayIntst_" + termNum).val(repayIntst);
            $("#repayIntst_" + termNum).hide();
            $("#spanRepayIntst_" + termNum).show();
            alert(top.getMessage("FAILED_OPERATION", "应还利息不能是负数"), 2);
            return false;
        }

        $("#spanRepayIntst_" + termNum).html($("#repayIntst_" + termNum).val());
        $("#repayIntst_" + termNum).hide();
        $("#spanRepayIntst_" + termNum).show();

        var multipleLoanPlanMerge = $("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
        var interestCollectType = $("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
        var repayDateSet = $("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日
        var yearDays = $("#yearDays").val();//计息天数
        var normCalcType = $("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息
        var secondNormCalcType = $("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
        var returnPlanPoint = $("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
        var returnPlanRound = $("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
        var instCalcBase = $("#instCalcBase").val();//还款利息计算基数：1-按借据金额、2-借据余额
        var preInstCollectType = $("#preInstCollectType").val();
        var fincId=$("#fincId").val();//借据号
        if (preInstCollectType == null) {
            preInstCollectType = "";
        }
        var repayDateDef = $("#repayDateDef").val();
        if (repayDateDef == null) {
            repayDateDef = "";
        }

        //获取还款计划列表
        var planObjs = getPlanObjs();
        var repayPlanList = JSON.stringify(planObjs);
        var rulesNo = $("#rulesNo").val();
        //请求后台应还利息接口
        $.ajax({
            url: webPath + '/mfRepayPlan/changePlanByIntstAjax',
            data: {
                "repayPlanList": repayPlanList,
                "fincId": fincId,
                "appId": appId,
                "planListSize": planListSize,
                "modTermNum": termNum,
                "loanAmt": loanAmt,
                "fincRate": fincRate,
                "multipleLoanPlanMerge": multipleLoanPlanMerge,
                "interestCollectType": interestCollectType,
                "repayDateSet": repayDateSet,
                "yearDays": yearDays,
                "normCalcType": normCalcType,
                "secondNormCalcType": secondNormCalcType,
                "returnPlanPoint": returnPlanPoint,
                "returnPlanRound": returnPlanRound,
                "instCalcBase": instCalcBase,
                "preInstCollectType": preInstCollectType,
                "repayDateDef": repayDateDef,
                "rulesNo": rulesNo
            },
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            },
            success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    var mfBusFincAppChild = data.mfBusFincAppChild;
                    var repayPlanList = data.repayPlanList;
                    getPlanListHtml(repayPlanList);
                    $("#planListSize").val(repayPlanList.length);
                    $("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
                    $("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息
                    $("#input_hide_intst_modify").val(1);//设置修改状态
                    //最后一期限制编辑
                    var planListSize = repayPlanList.length;
                    var lastPlanEndRate = "#tdPlanEndDate_" + planListSize;//最后一期结束日期
                    var lastRepayPrcp = "#tdRepayPrcp_" + planListSize;//最后一期应还本金
                    var lastRepayIntst = "#tdRepayIntst_" + planListSize;//最后一期应还利息
                    $(lastPlanEndRate).removeAttr('ondblclick');
                    $(lastRepayPrcp).removeAttr('ondblclick');
//					$(lastRepayIntst).removeAttr('ondblclick');
                } else {
                    window.top.alert(data.msg, 0);
                }
            }, error: function (data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };
    //修改结束日期事件
    var _changeEndDateValue = function () {
        var planEndDate = $("#planEndDate_" + termNum).val();//获取修改后的结束日期
        planEndDate = planEndDate.replace(/-/g, "");

        var planListSize = $("#planListSize").val();//获取还款计划期数
        var loanAmt = $("#putoutAmtHidden").val();//借据金额
        var fincRate = $("#fincRate").val();//年利率
        var intstModify = $("#input_hide_intst_modify").val();//利息修改状态
        var prePlanEndDate = $("#planBeginDate_" + termNum).val();//获取本期开始日期
        prePlanEndDate = prePlanEndDate.replace(/-/g, "");
        var lastPlanEndDate = $("#planEndDate_" + planListSize).val();//获取起息结束日期
        lastPlanEndDate = lastPlanEndDate.replace(/-/g, "");

        //获取修改的是否是当前期

        var currentNum = $("#currentNum_" + termNum).val();
        if (parseInt(planEndDate) < parseInt(prePlanEndDate)) {
            //还原应还本金默认值
            $("#planEndDate_" + termNum).val(prePlanEndDate);
            $("#planEndDate_" + termNum).hide();
            $("#spanPlanEndDate_" + termNum).show();
            alert(top.getMessage("FAILED_OPERATION", "计划还款日期不能小于计划开始日期"), 2);
            return false;
        } else if (parseInt(planEndDate) == parseInt(prePlanEndDate)) {
            //还原应还本金默认值
            $("#planEndDate_" + termNum).val(prePlanEndDate);
            $("#planEndDate_" + termNum).hide();
            $("#spanPlanEndDate_" + termNum).show();
            alert(top.getMessage("FAILED_OPERATION", "计划还款日期不能等于于计划开始日期"), 2);
            return false;
        } else if (parseInt(planEndDate) > parseInt(lastPlanEndDate)) {
            //还原应还本金默认值
            $("#planEndDate_" + termNum).val(prePlanEndDate);
            $("#planEndDate_" + termNum).hide();
            $("#spanPlanEndDate_" + termNum).show();
            alert(top.getMessage("FAILED_OPERATION", "计划还款日期不能大于合同结束日期"), 2);
            return false;
        } else if (currentNum == '0') {

            if (parseInt(planEndDate) < parseInt(lastReturnDate)) {
                //还原日期默认值
                $("#planEndDate_" + termNum).val(prePlanEndDate);
                $("#planEndDate_" + termNum).hide();
                $("#spanPlanEndDate_" + termNum).show();
                alert(top.getMessage("FAILED_OPERATION", "计划还款日期不能小于上次还款日期"), 2);
                return false;

            }
        }

        $("#spanPlanEndDate_" + termNum).html($("#planEndDate_" + termNum).val());
        $("#planEndDate_" + termNum).hide();
        $("#spanPlanEndDate_" + termNum).show();

        var multipleLoanPlanMerge = $("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
        var interestCollectType = $("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
        var repayDateSet = $("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日
        var yearDays = $("#yearDays").val();//计息天数
        var normCalcType = $("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息
        var secondNormCalcType = $("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
        var returnPlanPoint = $("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
        var returnPlanRound = $("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
        var instCalcBase = $("#instCalcBase").val();//还款利息计算基数：1-借据金额、2-借据余额
        var preInstCollectType = $("#preInstCollectType").val();
        var fincId=$("#fincId").val();//借据号
        if (preInstCollectType == null) {
            preInstCollectType = "";
        }
        var repayDateDef = $("#repayDateDef").val();
        if (repayDateDef == null) {
            repayDateDef = "";
        }

        //获取还款计划列表
        var planObjs = getPlanObjs();
        var repayPlanList = JSON.stringify(planObjs);
        var rulesNo = $("#rulesNo").val();
        //请求后台结束日期接口
        $.ajax({
            url: webPath + '/mfRepayPlan/changePlanByEndDateAjax',
            data: {
                "repayPlanList": repayPlanList,
                "fincId": fincId,
                "appId": appId,
                "planListSize": planListSize,
                "modTermNum": termNum,
                "loanAmt": loanAmt,
                "fincRate": fincRate,
                "intstModify": intstModify,
                "multipleLoanPlanMerge": multipleLoanPlanMerge,
                "interestCollectType": interestCollectType,
                "repayDateSet": repayDateSet,
                "yearDays": yearDays,
                "normCalcType": normCalcType,
                "secondNormCalcType": secondNormCalcType,
                "returnPlanPoint": returnPlanPoint,
                "returnPlanRound": returnPlanRound,
                "instCalcBase": instCalcBase,
                "preInstCollectType": preInstCollectType,
                "repayDateDef": repayDateDef,
                "rulesNo": rulesNo
            },
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            },
            success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    var mfBusFincAppChild = data.mfBusFincAppChild;
                    var repayPlanList = data.repayPlanList;
                    getPlanListHtml(repayPlanList);
                    $("#input_hide_intst_modify").val(0);//设置修改状态
                    $("#planListSize").val(repayPlanList.length);
                    $("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
                    $("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息
                    //最后一期限制编辑
                    var planListSize = repayPlanList.length;
                    var lastPlanEndRate = "#tdPlanEndDate_" + planListSize;//最后一期结束日期
                    var lastRepayPrcp = "#tdRepayPrcp_" + planListSize;//最后一期应还本金
                    var lastRepayIntst = "#tdRepayIntst_" + planListSize;//最后一期应还利息
                    $(lastPlanEndRate).removeAttr('ondblclick');
                    $(lastRepayPrcp).removeAttr('ondblclick');
//					$(lastRepayIntst).removeAttr('ondblclick');
                } else {
                    window.top.alert(data.msg, 0);
                }
            }, error: function (data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    };
    //校验后保存还款计划
    var _repayPlanChackAjax = function (obj) {
        //金额校验
        if(!_getFeiCha()){
            return false;
        }
        if(Number($("input[name=bankRate]").val()) == 0){
            window.top.alert("确认银行利率是为0？",2,function(){
                _repayPlanAjax(obj); //点击确定执行
            },function(){
                return;//点击取消执行
            });
        }else {
            _repayPlanAjax(obj);
        }
    };

    //保存还款计划
    var _repayPlanAjax = function (obj) {

        var flag = submitJsMethod($("#repayPlanInsertForm").get(0), '');
        if(flag){
            var dataParam2 = JSON.stringify($("#repayPlanInsertForm").serializeArray());
            var beginDate = $("input[name=beginDate]").val();
            var endDate = $("select[name=endDate]").val();
            //借新还旧业务出账时是否自动解清上笔贷款 1是，0否
            var autoSettledAccount=$("select[name=autoSettledAccount]").val();
            //3为借新还旧
            var loanKind = $("input[name=loanKind]").val();
            if (beginDate == "" || endDate == ""){
                alert("发放日期或到期日期不能为空!");
                return;
            }
            //银行实际放款金额
            var putoutAmtRealFormat = $("input[name=putoutAmtRealFormat]").val();
            if (loanKind == '3') {
                if (autoSettledAccount == "" || autoSettledAccount == ""){
                    alert("是否自动结清上笔贷款必选！");
                    return;
                }
            }

            var msg = _pdIsNotNull();
            if (msg == "pass") {
                //连连使用放款复核真实打款，控制按钮只能点击一次
                $(obj).attr("disabled", "disabled");
                //阳光银行需求：生成还款计划之前要检查押品是否入库，调用准入接口
                // 准入拦截
                var parmData = {'nodeNo': 'review_finc', 'relNo': appId, 'appId': appId};
                $.ajax({
                    url: webPath + "/riskForApp/getNodeRiskDataForBeginAjax",
                    type: "post",
                    data: {ajaxData: JSON.stringify(parmData)},
                    dataType: "json",
                    success: function (data) {
                        if (data.exsitRefused == true) {// 存在业务拒绝
                            top.window.openBigForm(webPath + '/riskForApp/preventList?relNo=' + appId, '风险拦截信息', function () {
                            });
                        } else {//执行原来的代码
                            var beginDate = $("input[name=beginDate]").val();
                            var endDate = $("select[name=endDate]").val();
                            var dataParam = JSON.stringify($("#repayPlanForm").serializeArray());
                            var planListSize = $("#planListSize").val();
                            var repayFeeSum = $("#repay_fee_sum").val();//提前收取的费用总和
                            var repayIntstSum = $("#repay_intst_sum").val();//提前收取的利息总和
                            var multipleLoanPlanMerge = $("#multipleLoanPlanMerge").val();//多次放款是否合并还款计划
                            var interestCollectType = $("#interestCollectType").val();//上收息参数  利息收息方式：1-上收息 2-下收息
                            var feeCollectWay = $("#feeCollectWay").val();//费用收取方式：1 上收费 2 下收费
                            var putOutChargeIntstFlag = $("#putOutChargeIntstFlag").val();//0 表示不是放款时收取  1 表示 是固定还款日 且是放款时收取
                            //放款时收取利息 （存在固定还款日 ）
                            var putOutChargeIntst = $("#putout_charge_Intst_hidden").val();
                            //放款时收取的 一次性费用
                            var putOutChargeFee = $("#put_out_feesum").val();
                            var rulesNo = $("#rulesNo").val();
                            //获取借款编码的值
                            var borrowCode = $("#borrowCode").val();

                            $.ajax({
                                url: webPath + '/mfRepayPlan/repayPlanAjax',
                                data: {
                                    "planListData": dataParam,
                                    "appId": appId,
                                    "fincId": fincId,
                                    "beginDate": beginDate,
                                    "endDate": endDate,
                                    "planListSize": planListSize,
                                    "repayFeeSum": repayFeeSum,
                                    "repayIntstSum": repayIntstSum,
                                    "multipleLoanPlanMerge": multipleLoanPlanMerge,
                                    "interestCollectType": interestCollectType,
                                    "rulesNo": rulesNo,
                                    "putOutChargeIntstFlag": putOutChargeIntstFlag,
                                    "putOutChargeIntst": putOutChargeIntst,
                                    "putOutChargeFee": putOutChargeFee,
                                    "feeCollectWay": feeCollectWay,
                                    "borrowCode": borrowCode,
                                    "autoSettledAccount": autoSettledAccount,
                                    'nodeNoOld': nodeNo,
                                    "putoutAmtRealFormat":putoutAmtRealFormat,
                                    // bankPactNo:bankPactNo,
                                    // bankFicNo:bankFicNo,
                                    // assurePactNo:assurePactNo,
                                    // bankRate:bankRate,
                                    ajaxData:dataParam2
                                },
                                type: "POST",
                                dataType: "json",
                                beforeSend: function () {
                                },
                                success: function (data) {
                                    LoadingAnimate.stop();
                                    alert(data.msg, 3, function () {//生成还款计划成功之后要提示一下，再执行原来的方法
                                        top.flag = true;
                                        top.fincId = fincId;// 回调时取不到当前页面的值
                                        if (data.flag == 'success') {
                                            top.putoutReviewFlag = true;
                                            top.tableHtml = data.tableHtml;
                                            myclose_click();
                                        } else {
                                            top.putoutSaveFlag = true;// 刷新放款历史
                                        }

                                    });
                                    $(obj).removeAttr("disabled");
                                }, error: function (data) {
                                    LoadingAnimate.stop();
                                    alert(top.getMessage("FAILED_OPERATION", " "), 0);
                                    $(obj).removeAttr("disabled");
                                }
                            });
                        }
                    },
                    error: function () {
                        $(obj).removeAttr("disabled");
                    }
                })
            }
        };
        }


//保存还款计划
    var _noPutoutAjax = function (obj) {
            window.top.alert("确定放弃此次放款申请，返回放款通知书？", 2, function () {
                $.ajax({
                    url: webPath + '/mfBusFincApp/noPutoutAjax',
                    data: {
                        "appId": appId,
                        "fincId": fincId
                    },
                    type: "POST",
                    dataType: "json",
                    beforeSend: function () {
                    },
                    success: function (data) {
                        LoadingAnimate.stop();
                        alert(data.msg, 3, function () {
                            top.flag = true;
                            top.fincId = fincId;// 回调时取不到当前页面的值
                            if (data.flag == 'success') {
                                top.putoutReviewFlag = true;
                                top.tableHtml = data.tableHtml;
                            } else {
                                top.putoutSaveFlag = true;// 刷新放款历史
                            }
                            myclose_click()
                        });
                        $(obj).removeAttr("disabled");
                    }, error: function (data) {
                        LoadingAnimate.stop();
                        alert(top.getMessage("FAILED_OPERATION", " "), 0);
                        $(obj).removeAttr("disabled");
                    }
                });
        });


    };
    var _doEditorPrePlanAjax = function (obj) {
        var dataParam = JSON.stringify($("#repayPlanForm").serializeArray());
        var planListSize = $("#planListSize").val();
        LoadingAnimate.start();
        $.ajax({
            url: webPath + '/mfRepayPlan/doEditorPrePlanAjax',
            data: {"planListData": dataParam, "fincId": fincId, "planListSize": planListSize},
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            },
            success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    window.top.alert("提前还款操作成功且完成还款计划的自定义", 3);
                    myclose_click();
                } else {
                    alert(data.msg, 0);
                }

            }, error: function (data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }

        })
    }


    //保存还款计划
    var _thirdPayRepayPlanAjax = function (obj) {
        //散发使用放款复核真实打款，控制按钮只能点击一次
        $(obj).attr("disabled", "disabled");
        //执行原来的代码
        var fincId = $("#fincId").val();//借据号
        var beginDate = $("#beginDate").val();
        var endDate = $("#endDate").val();
        var dataParam = JSON.stringify($("#repayPlanForm").serializeArray());
        var planListSize = $("#planListSize").val();
        var repayFeeSum = $("#repay_fee_sum").val();//提前收取的费用总和
        var repayIntstSum = $("#repay_intst_sum").val();//提前收取的利息总和
        var putoutAmt = $("#putoutAmtHidden").val();
        var putoutAmtReal = $("#putoutAmtRealHidden").val();
//		var multipleLoanPlanMerge=$("#multipleLoanPlanMerge").val();//多次放款是否合并还款计划 
//		var interestCollectType=$("#interestCollectType").val();//上收息参数  利息收息方式：1-上收息 2-下收息
//		var feeCollectWay=$("#feeCollectWay").val();//费用收取方式：1 上收费 2 下收费
//		var putOutChargeIntstFlag=$("#putOutChargeIntstFlag").val();//0 表示不是放款时收取  1 表示 是固定还款日 且是放款时收取
        //放款时收取利息 （存在固定还款日 ）
        var putOutChargeIntst = $("#putout_charge_Intst_hidden").val();
        //放款时收取的 一次性费用
        var putOutChargeFee = $("#put_out_feesum").val();
        var rulesNo = $("#rulesNo").val();
        $.ajax({
            url: webPath + '/mfRepayPlan/thirdPayRepayPlanAjax',
            data: {
                "planListData": dataParam,
                "fincId": fincId,
                "beginDate": beginDate,
                "endDate": endDate,
                "planListSize": planListSize,
                "repayFeeSum": repayFeeSum,
                "repayIntstSum": repayIntstSum,
                "putoutAmt": putoutAmt,
                "putoutAmtReal": putoutAmtReal,
                "rulesNo": rulesNo,
                "putOutChargeIntst": putOutChargeIntst,
                "putOutChargeFee": putOutChargeFee
            },
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            },
            success: function (data) {
                LoadingAnimate.stop();
                alert(data.msg, 3, function () {//生成还款计划成功之后要提示一下，再执行原来的方法
                    top.flag = true;
                    top.putoutReviewFlag = true;
                    top.tableHtml = data.tableHtml;
                    myclose_click();
                });
                $(obj).removeAttr("disabled");
            }, error: function (data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
                $(obj).removeAttr("disabled");
            }
        });
    };


    function getPlanObjs() {
        var planObjs = new Array();
        $("#repayPlan_tab tr").each(function () {
            var planObj = new Object();
            var termNumArr = $(this).attr("id").split("_");
            var index = termNumArr[1];//获得当前行
            planObj.termNum = $("#termNum_" + index).val();//期数
            planObj.planBeginDate = $("#planBeginDate_" + index).val();//开始日期
            planObj.planEndDate = $("#planEndDate_" + index).val();//结束日期
            planObj.repayDate = $("#repayDate_" + index).val();//还款日期
            planObj.repayPrcp = $("#repayPrcp_" + index).val();//应还本金
            // planObj.repayIntst = $("#repayIntst_" + index).val();//应还利息
            // planObj.feeSum = $("#feeSum_" + index).val();//费用总额
            // planObj.repayPrcpIntstSum = $("#repayPrcpIntstSum_" + index).val();//应还总额
            planObj.repayPrcpBalAfter = $("#repayPrcpBalAfter_" + index).val();//期末本金余额
            planObj.outFlag = $("#outFlag_" + index).val();//还款状态
            planObj.currentNum = $("#currentNum_" + index).val();//是否是当期
            planObjs.push(planObj);
        });
        return planObjs;
    };
    //检查借款编码是否唯一
    var _checkBorrowCode = function (obj) {
        var borrowCode = $("input[name='borrowCode']").val();
        if(typeof (borrowCode)!= 'undefined' && borrowCode != ''){
            $.ajax({
                async:false,
                url:webPath+"/mfBusFincApp/validateBorrowCode",
                data:{borrowCode:borrowCode},
                success:function(data){
                    if(data.flag == "success"){
                        if(data.exitFlag == 'true'){
                            alert(data.msg, 3);
                            $("input[name='borrowCode']").val('');
                        }
                    }
                },
                error:function () {
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });
        }
    };

    //判断借款编码是否为空
    var _pdIsNotNull=function(){
        var msg = "";
        var borrowCode = $("#borrowCode").val();
        if(borrowCodeType=='1'){
            if(borrowCode!=""){
                msg="pass";
            }else{
                alert(top.getMessage("EXIST_INFORMATION_EVAL","该借款编码"),0);
            }
        }else{
            msg="pass";
        }
        return msg;
    };
    //修改调整期数时调用的方法
    var _getPlanListByPeriodAjax = function (obj) {
        func_uior_valType(obj);
        var periodAdjust = $("input[name='periodAdjust']").val();
        if(periodAdjust*1<=0){
            return;
        }
        if (periodAdjust * 1 > 360) {
            window.top.alert("调整的最大期数不可超过360", 0);
            return;
        }
        var beginDate=$("input[name=beginDate]").val();//获取改变的发放日期
        var endDate=$("select[name=endDate]").val();//获取到期日期
        var multipleLoanPlanMerge=$("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
        var interestCollectType=$("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
        var repayDateSet=$("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日
        var yearDays=$("#yearDays").val();//计息天数
        var normCalcType=$("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息
        var secondNormCalcType=$("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
        var returnPlanPoint=$("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
        var returnPlanRound=$("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
        var instCalcBase=$("#instCalcBase").val();//还款利息计算基数：1-借据金额、2-借据余额
        var preInstCollectType=$("#preInstCollectType").val();
        if(preInstCollectType==null){
            preInstCollectType="";
        }
        var repayDateDef=$("#repayDateDef").val();
        if(repayDateDef==null){
            repayDateDef="";
        }
        var rulesNo=$("#rulesNo").val();
        $.ajax({
            url:webPath+'/mfRepayPlan/getPlanListByPeriodAjax',
            data:{"appId":appId,"beginDate":beginDate,"endDate":endDate,"multipleLoanPlanMerge":multipleLoanPlanMerge,"interestCollectType":interestCollectType,"repayDateSet":repayDateSet,"yearDays":yearDays,"normCalcType":normCalcType,"secondNormCalcType":secondNormCalcType,"returnPlanPoint":returnPlanPoint,"returnPlanRound":returnPlanRound,"instCalcBase":instCalcBase,"preInstCollectType":preInstCollectType,"repayDateDef":repayDateDef,"rulesNo":rulesNo,"periodAdjust":periodAdjust},
            type:"POST",
            dataType:"json",
            beforeSend:function(){
            },
            success:function(data){
                if(data.flag=="success"){
                    if (data.outMaxPeriodAdjust == "1") {
                        window.top.alert("调整期数超过最大天数调整时,按实际最大天数调整", 3);
                    }
                    var mfBusFincAppChild=data.mfBusFincAppChild;
                    var repayPlanList=data.repayPlanList;
                    getPlanListHtml(repayPlanList);
                    $("#planListSize").val(repayPlanList.length);
                    $("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
                    $("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息

                    $("#span_putout_amt_real_format").html(mfBusFincAppChild.putoutAmtRealFormat);//实际发放的金额
                    $("#putoutAmtRealHidden").html(mfBusFincAppChild.putoutAmtReal);//放款时收取利息
                    $("#span_charge_interest_sum").html("&nbsp;(扣除费用利息"+mfBusFincAppChild.chargeFeeAndInterSumFormat+"元)");//提前收取的费用
                    //最后一期限制编辑
                    var planListSize=repayPlanList.length;
                    var lastPlanEndRate="#tdPlanEndDate_"+planListSize;//最后一期结束日期
                    var lastRepayPrcp="#tdRepayPrcp_"+planListSize;//最后一期应还本金
                    var lastRepayIntst="#tdRepayIntst_"+planListSize;//最后一期应还利息
                    $(lastPlanEndRate).removeAttr('ondblclick');
                    $(lastRepayPrcp).removeAttr('ondblclick');
//				$(lastRepayIntst).removeAttr('ondblclick');
                }else {
                    window.top.alert(data.msg, 0);
                }
                LoadingAnimate.stop();
                top.flag=true;
                top.putoutReviewFlag=true;
            },error:function(data){
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION"," "),0);
            }
        });
    };
    /**
     * 发放日期事件
     * @param obj
     * @private
     */
    var _fPopUpCalendarDlgByPactDate = function(obj) {
        fPopUpCalendarDlg({min:beginDate,choose:getChangePlanListBybeginDate})
    };
    /**
     * 放款金额事件
     * @param obj
     * @private
     */
    var _putoutAmtChange = function(obj) {
       var putoutAmt =$(obj).val();
        $("#putoutAmtRealHidden").html(putoutAmt);
        $("#putoutAmtHidden").html(putoutAmt);
        $("#putoutAmtRealHidden").val(putoutAmt);
        $("#putoutAmtHidden").val(putoutAmt);
    };
    //放款解除
    var _putOutCancel = function (obj) {
        var dataParam = JSON.stringify($("#repayPlanForm").serializeArray());
        var planListSize = $("#planListSize").val();
        LoadingAnimate.start();
        $.ajax({
            url: webPath + '/mfBusFincApp/putOutCancelAjax',
            data: { "fincId": fincId},
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            },
            success: function (data) {
                LoadingAnimate.stop();
                if (data.flag == "success") {
                    window.top.alert("解除放款成功", 3);
                    myclose_click();
                } else {
                    alert(data.msg, 0);
                }

            }, error: function (data) {
                LoadingAnimate.stop();
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }

        })
    };

    //判断银行发放金额不能大于委托合同金额
    var _getFeiCha = function () {
        var ruleFlag = true;
        var putoutAmtRealFormat = $("input[name=putoutAmtRealFormat]").val().replace(/,/g, "");
        var usableFincAmt = $("input[name=usableFincAmt]").val().replace(/,/g, "");
        var putoutAmtFormat = $("input[name=putoutAmtFormat]").val().replace(/,/g, "");
        if(Number(putoutAmtRealFormat) > Number(usableFincAmt)+Number(putoutAmtFormat)){
            alert("银行发放金额不能大于可支用金额。",3);
            $("input[name=putoutAmtRealFormat]").val(0);
            ruleFlag = false;
        }
        return ruleFlag;
    };

    return {
        init: _init,
        changeRepayPrcpValue: _changeRepayPrcpValue,
        changeRepayIntstValue: _changeRepayIntstValue,
        changeEndDateValue: _changeEndDateValue,
        repayPlanAjax: _repayPlanAjax,
        thirdPayRepayPlanAjax: _thirdPayRepayPlanAjax,
        showEndDateInputValue: _showEndDateInputValue,
        showRepayPrcpInputValue: _showRepayPrcpInputValue,
        showRepayIntsInputValue: _showRepayIntsInputValue,
        doEditorPrePlanAjax: _doEditorPrePlanAjax,
        showEndDateInputValueNew: _showEndDateInputValueNew,
        showRepayPrcpInputValueNew: _showRepayPrcpInputValueNew,
        showRepayIntsInputValueNew: _showRepayIntsInputValueNew,
        checkBorrowCode:_checkBorrowCode,
        getPlanListByPeriodAjax:_getPlanListByPeriodAjax,
        fPopUpCalendarDlgByPactDate:_fPopUpCalendarDlgByPactDate,
        pdIsNotNull:_pdIsNotNull,
        putoutAmtChange:_putoutAmtChange,
        putOutCancel:_putOutCancel,
        noPutoutAjax:_noPutoutAjax,
        getFeiCha:_getFeiCha,
        repayPlanChackAjax:_repayPlanChackAjax
    };
}(window, jQuery);
//还款计划开始日期修改
function getChangePlanListBybeginDate(){
    // debugger;
    var beginDate = $("input[name=beginDate]").val();//获取改变的发放日期
    var endDate = $("select[name=endDate]").val();//获取到期日期
    var putoutAmtRealFormat = $("input[name=putoutAmtRealFormat]").val();//获取实际放款金额
    var beginDateHidden = $("#beginDateHidden").val();
    var intstBeginDate = beginDate.replace(/-/g, "");
    var intstEndDate = endDate.replace(/-/g, "");
    putoutAmtRealFormat = putoutAmtRealFormat.replace(/,/g, "");
    if (parseInt(intstBeginDate) > parseInt(intstEndDate)) {
        alert(top.getMessage("FAILED_OPERATION", "计划发放日期不能大于到期结束日期"), 0);
        $("input[name=beginDate]").val(beginDateHidden);
        return false;
    }
    var multipleLoanPlanMerge = $("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
    var interestCollectType = $("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
    var feeCollectWay = $("#feeCollectWay").val();//费用收取方式：1 上收费 2 下收费
    var repayDateSet = $("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日
    var yearDays = $("#yearDays").val();//计息天数
    var normCalcType = $("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息
    var secondNormCalcType = $("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
    var returnPlanPoint = $("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
    var returnPlanRound = $("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
    var instCalcBase = $("#instCalcBase").val();//还款利息计算基数：1-贷款金额、2-待款余额
    var preInstCollectType = $("#preInstCollectType").val();////预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期
    var fincId = $("#fincId").val();//借据号
    if (preInstCollectType == null) {
        preInstCollectType = "";
    }
    var repayDateDef = $("#repayDateDef").val();
    if (repayDateDef == null) {
        repayDateDef = "";
    }
    var rulesNo = $("#rulesNo").val();
    $.ajax({
        url: webPath + '/mfRepayPlan/getPlanListByBeginDateAjax',
        data: {
            "appId": appId,
            "putoutAmt": putoutAmtRealFormat,
            "fincId": fincId,
            "beginDate": beginDate,
            "endDate": endDate,
            "beginDateHidden": beginDateHidden,
            "multipleLoanPlanMerge": multipleLoanPlanMerge,
            "interestCollectType": interestCollectType,
            "repayDateSet": repayDateSet,
            "yearDays": yearDays,
            "normCalcType": normCalcType,
            "secondNormCalcType": secondNormCalcType,
            "returnPlanPoint": returnPlanPoint,
            "returnPlanRound": returnPlanRound,
            "instCalcBase": instCalcBase,
            "preInstCollectType": preInstCollectType,
            "repayDateDef": repayDateDef,
            "rulesNo": rulesNo
        },
        type: "POST",
        dataType: "json",
        beforeSend: function () {
        },
        success: function (data) {
            LoadingAnimate.stop();
            if (data.flag == "success") {
                var mfBusFincAppChild = data.mfBusFincAppChild;
                var repayPlanList = data.repayPlanList;
                var delayDateMap = data.delayDateMap;

                changeMfFincAppValue(delayDateMap);
                getPlanListHtml(repayPlanList);
                $("#planListSize").val(repayPlanList.length);
                $("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
                $("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息

                $("#span_putout_amt_real_format").html(mfBusFincAppChild.putoutAmtRealFormat);//实际发放的金额
                $("#putoutAmtRealHidden").html(mfBusFincAppChild.putoutAmtReal);//放款时收取利息
                $("#putoutAmtHidden").html(mfBusFincAppChild.putoutAmtReal);//放款时收取利息
                $("#putoutAmtRealHidden").val(mfBusFincAppChild.putoutAmtReal);//放款时收取利息
                $("#putoutAmtHidden").val(mfBusFincAppChild.putoutAmtReal);//放款时收取利息

                $("#span_charge_interest_sum").html("&nbsp;(扣除费用利息" + mfBusFincAppChild.chargeFeeAndInterSumFormat + "元)");//提前收取的费用

                $("#span_putout_charge_Intst_format").html(mfBusFincAppChild.putOutChargeIntstFormat);//放款时收取的利息
                $("#putout_charge_Intst_hidden").val(mfBusFincAppChild.putOutChargeIntst);//放款时收取的利息
                //最后一期限制编辑
                var planListSize = repayPlanList.length;
                var lastPlanEndRate = "#tdPlanEndDate_" + planListSize;//最后一期结束日期
                var lastRepayPrcp = "#tdRepayPrcp_" + planListSize;//最后一期应还本金
                var lastRepayIntst = "#tdRepayIntst_" + planListSize;//最后一期应还利息
                $(lastPlanEndRate).removeAttr('ondblclick');
                $(lastRepayPrcp).removeAttr('ondblclick');
//				$(lastRepayIntst).removeAttr('ondblclick');
            } else {
                window.top.alert(data.msg, 0);
            }

            top.flag = true;
            top.putoutReviewFlag = true;
        }, error: function (data) {
            LoadingAnimate.stop();
            alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
}
function getChangePlanListByEndDate(){
    var beginDate = $("input[name=beginDate]").val();//获取改变的发放日期
    var putoutAmtRealFormat = $("input[name=putoutAmtRealFormat]").val();//获取实际放款金额
    putoutAmtRealFormat = putoutAmtRealFormat.replace(/,/g, "");
    var endDate = $("select[name=endDate]").val();//获取到期日期
    var multipleLoanPlanMerge = $("#multipleLoanPlanMerge").val();//多次放款还款计划是否合并  1-启用、0-禁用
    var interestCollectType = $("#interestCollectType").val();//利息收息方式：1-上收息 2-下收息
    var repayDateSet = $("#repayDateSet").val();//还款日设置：1-贷款发放日 2-月末 3-固定还款日
    var yearDays = $("#yearDays").val();//计息天数
    var normCalcType = $("#normCalcType").val();//利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息
    var secondNormCalcType = $("#secondNormCalcType").val();//不足期计息类型   1-按月计息|2-按实际天数计息
    var returnPlanPoint = $("#returnPlanPoint").val();//保留小数位数 	2-两位|1-一位|0-不保留
    var returnPlanRound = $("#returnPlanRound").val();//还款计划舍入方式 2-四舍五入
    var instCalcBase = $("#instCalcBase").val();//还款利息计算基数：1-借据金额、2-借据余额
    var preInstCollectType = $("#preInstCollectType").val();
    if (preInstCollectType == null) {
        preInstCollectType = "";
    }
    var repayDateDef = $("#repayDateDef").val();
    if (repayDateDef == null) {
        repayDateDef = "";
    }
    var rulesNo = $("#rulesNo").val();
    $.ajax({
        url: webPath + '/mfRepayPlan/getPlanListByEndDateAjax',
        data: {
            "appId": appId,
            "beginDate": beginDate,
            "putoutAmt": putoutAmtRealFormat,
            "endDate": endDate,
            "multipleLoanPlanMerge": multipleLoanPlanMerge,
            "interestCollectType": interestCollectType,
            "repayDateSet": repayDateSet,
            "yearDays": yearDays,
            "normCalcType": normCalcType,
            "secondNormCalcType": secondNormCalcType,
            "returnPlanPoint": returnPlanPoint,
            "returnPlanRound": returnPlanRound,
            "instCalcBase": instCalcBase,
            "preInstCollectType": preInstCollectType,
            "repayDateDef": repayDateDef,
            "rulesNo": rulesNo,
            "fincId": fincId
        },
        type: "POST",
        dataType: "json",
        beforeSend: function () {
        },
        success: function (data) {
            if (data.flag == "success") {
                var mfBusFincAppChild = data.mfBusFincAppChild;
                var repayPlanList = data.repayPlanList;
                getPlanListHtml(repayPlanList);
                $("#planListSize").val(repayPlanList.length);
                $("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
                $("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息

                $("#span_putout_amt_real_format").html(mfBusFincAppChild.putoutAmtRealFormat);//实际发放的金额
                $("#putoutAmtRealHidden").html(mfBusFincAppChild.putoutAmtReal);//放款时收取利息
                $("#span_charge_interest_sum").html("&nbsp;(扣除费用利息" + mfBusFincAppChild.chargeFeeAndInterSumFormat + "元)");//提前收取的费用
                //最后一期限制编辑
                var planListSize = repayPlanList.length;
                var lastPlanEndRate = "#tdPlanEndDate_" + planListSize;//最后一期结束日期
                var lastRepayPrcp = "#tdRepayPrcp_" + planListSize;//最后一期应还本金
                var lastRepayIntst = "#tdRepayIntst_" + planListSize;//最后一期应还利息
                $(lastPlanEndRate).removeAttr('ondblclick');
                $(lastRepayPrcp).removeAttr('ondblclick');
//				$(lastRepayIntst).removeAttr('ondblclick');
            } else {
                window.top.alert(data.msg, 0);
            }
            LoadingAnimate.stop();
            top.flag = true;
            top.putoutReviewFlag = true;
        }, error: function (data) {
            LoadingAnimate.stop();
            alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
}

/**
 * 修改预先支付利息收取方式获取还款计划
 */
function changePreInstCollectTypeAjax() {


    var preInstCollectType = $("[name=preInstCollectType]").val();


    $.ajax({
        url: webPath + '/mfRepayPlan/changePreInstCollectTypeAjax',
        data: {"appId": appId, "preInstCollectType": preInstCollectType},
        type: "POST",
        dataType: "json",
        beforeSend: function () {
        },
        success: function (data) {
            if (data.flag == "success") {
                var mfBusFincAppChild = data.mfBusFincAppChild;
                var repayPlanList = data.repayPlanList;
                getPlanListHtml(repayPlanList);
                $("#planListSize").val(repayPlanList.length);
                $("#repay_fee_sum").val(mfBusFincAppChild.chargeFeeSum);//提前收取的费用
                $("#repay_intst_sum").val(mfBusFincAppChild.chargeInterestSum);//提前收取的利息

                $("#span_putout_amt_real_format").html(mfBusFincAppChild.putoutAmtRealFormat);//实际发放的金额
                $("#putoutAmtRealHidden").html(mfBusFincAppChild.putoutAmtReal);//放款时收取利息
                $("#span_charge_interest_sum").html("&nbsp;(扣除费用利息" + mfBusFincAppChild.chargeFeeAndInterSumFormat + "元)");//提前收取的费用
                //最后一期限制编辑
                var planListSize = repayPlanList.length;
                var lastPlanEndRate = "#tdPlanEndDate_" + planListSize;//最后一期结束日期
                var lastRepayPrcp = "#tdRepayPrcp_" + planListSize;//最后一期应还本金
                var lastRepayIntst = "#tdRepayIntst_" + planListSize;//最后一期应还利息
                $(lastPlanEndRate).removeAttr('ondblclick');
                $(lastRepayPrcp).removeAttr('ondblclick');

            } else {
                window.top.alert(data.msg, 0);
            }
            LoadingAnimate.stop();
            top.flag = true;
            top.putoutReviewFlag = true;
        }, error: function (data) {
            LoadingAnimate.stop();
            alert(top.getMessage("FAILED_OPERATION", " "), 0);
        }
    });
}



//封装还款计划列表HTML
function getPlanListHtml(obj){
    $("#repayPlan_tab").html("");
    $.each(obj, function (i, repayPlan) {


        if (repayPlan.currentNum == '0') {

            $("#repayPlan_tab").append(
                "<tr id='tr_" + repayPlan.termNum + "'>" +

                "<td align='center'>" +
                "<span class='tab_span' id='spanTermNum_" + repayPlan.termNum + "'>" + repayPlan.termNum + "</span>" +
                "<input type='text' name='termNum" + repayPlan.termNum + "' id='termNum_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.termNum + "'>" +
                "</td>" +

                "<td align='center' hidden>" +
                "<span class='tab_span' id='spanPlanBeginDate_" + repayPlan.termNum + "'>" + repayPlan.planBeginDateFormat + "</span>" +
                "<input type='text' name='planBeginDate" + repayPlan.termNum + "' id='planBeginDate_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.planBeginDateFormat + "' datatype='6'>" +
                "</td>" +

                "<td align='center' hidden>" +
                "<span class='tab_span' id='spanExpiryIntstDate_" + repayPlan.termNum + "'>" + repayPlan.expiryIntstDateFormat + "</span>" +
                "<input type='text' name='expiryIntstDate" + repayPlan.termNum + "' id='expiryIntstDate_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.expiryIntstDateFormat + "' datatype='6' mustinput='1' maxlength='10'>" +
                "</td>" +

                "<td align='center' id='tdPlanEndDate_" + repayPlan.termNum + "' ondblclick='repayPlan_List.showEndDateInputValueNew(this)'>" +
                "<span class='tab_span' id='spanPlanEndDate_" + repayPlan.termNum + "'>" + repayPlan.planEndDateFormat + "</span>" +
                "<input type='text' name='planEndDate" + repayPlan.termNum + "' id='planEndDate_" + repayPlan.termNum + "' datatype='6' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.planEndDateFormat + "' onfocus='fPopUpCalendarDlg({max:lastReturnDate, choose:repayPlan_List.changeEndDateValue});'  onmousedown='enterKey()' onkeydown='enterKey();' datatype='6' mustinput='1' maxlength='10'>" +
                "</td>" +

                "<td  id='tdRepayPrcp_" + repayPlan.termNum + "'  ondblclick='repayPlan_List.showRepayPrcpInputValueNew(this)'>" +
                "<span class='tab_span money_right' id='spanRepayPrcp_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpFormat + "</span>" +
                "<input type='text' name='repayPrcp" + repayPlan.termNum + "' id='repayPrcp_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.repayPrcp + "'   onblur='repayPlan_List.changeRepayPrcpValue(this);' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">" +
                "</td>" +

                "<td hidden id='tdRepayIntst_" + repayPlan.termNum + "' ondblclick='repayPlan_List.showRepayIntsInputValueNew(this)'>" +
                "<span class='tab_span money_right' id='spanRepayIntst_" + repayPlan.termNum + "'>" + repayPlan.repayIntstFormat + "</span>" +
                "<input type='text' name='repayIntst" + repayPlan.termNum + "' id='repayIntst_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.repayIntst + "'   onblur='repayPlan_List.changeRepayIntstValue(this);' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">" +
                "</td>" +
                "<td  hidden>" +
                "<span class='tab_span money_right' id='spanFeeSum_" + repayPlan.termNum + "'>" + repayPlan.feeSumFormat + "</span>" +
                "<input type='text' name='feeSum" + repayPlan.termNum + "' id='feeSum_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.feeSum + "'>" +
                "</td>" +
                "<td hidden>" +
                "<span class='tab_span money_right' id='spanRepayPrcpIntstSum_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpIntstSumFormat + "</span>" +
                "<input type='text' name='repayPrcpIntstSum" + repayPlan.termNum + "' id='repayPrcpIntstSum_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.repayPrcpIntstSum + "'>" +
                "</td>" +
                "<td >" +
                "<span class='tab_span money_right' id='spanRrepayPrcpBalAfter_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpBalAfterFormat + "</span>" +
                "<input type='text' name='repayPrcpBalAfter" + repayPlan.termNum + "' id='repayPrcpBalAfter_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.repayPrcpBalAfter + "' >" +
                "</td>" +
                "<td align='center' style='display:none'>" +
                "<input type='text' name='repayIntstOrig" + repayPlan.termNum + "' id='repayIntstOrig_" + repayPlan.termNum + "'  value='" + repayPlan.repayIntstOrig + "'>" +
                "</td>" +
                "<td align='center' style='display:none'>" +
                "<input type='text' name='outFlag" + repayPlan.termNum + "' id='outFlag_" + repayPlan.termNum + "'  value='" + repayPlan.outFlag + "'>" +
                "</td>" +

                "<td align='center' style='display:none'>" +
                "<input type='text' name='currentNum" + repayPlan.termNum + "' id='currentNum_" + repayPlan.termNum + "'  value='" + repayPlan.currentNum + "'>" +
                "</td>" +

                "<td align='center' style='display:none'>" +
                "<input type='text' name='repayDate" + repayPlan.termNum + "' id='repayDate_" + repayPlan.termNum + "'  value='" + repayPlan.repayDate + "'>" +
                "</td>" +

                "</tr>"
            );


        } else {

            $("#repayPlan_tab").append(
                "<tr id='tr_" + repayPlan.termNum + "'>" +

                "<td align='center'>" +
                "<span class='tab_span' id='spanTermNum_" + repayPlan.termNum + "'>" + repayPlan.termNum + "</span>" +
                "<input type='text' name='termNum" + repayPlan.termNum + "' id='termNum_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.termNum + "'>" +
                "</td>" +
                "<td align='center' hidden>" +
                "<span class='tab_span' id='spanPlanBeginDate_" + repayPlan.termNum + "'>" + repayPlan.planBeginDateFormat + "</span>" +
                "<input type='text' name='planBeginDate" + repayPlan.termNum + "' id='planBeginDate_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.planBeginDateFormat + "' datatype='6'>" +
                "</td>" +

                "<td align='center' hidden>" +
                "<span class='tab_span' id='spanExpiryIntstDate_" + repayPlan.termNum + "'>" + repayPlan.expiryIntstDateFormat + "</span>" +
                "<input type='text' name='expiryIntstDate" + repayPlan.termNum + "' id='expiryIntstDate_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.expiryIntstDateFormat + "' datatype='6' mustinput='1' maxlength='10'>" +
                "</td>" +

                "<td align='center' id='tdPlanEndDate_" + repayPlan.termNum + "' ondblclick='repayPlan_List.showEndDateInputValueNew(this)'>" +
                "<span class='tab_span' id='spanPlanEndDate_" + repayPlan.termNum + "'>" + repayPlan.planEndDateFormat + "</span>" +
                "<input type='text' name='planEndDate" + repayPlan.termNum + "' id='planEndDate_" + repayPlan.termNum + "' datatype='6' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.planEndDateFormat + "' onfocus='fPopUpCalendarDlg({max:lastReturnDate, choose:repayPlan_List.changeEndDateValue});'  onmousedown='enterKey()' onkeydown='enterKey();' datatype='6' mustinput='1' maxlength='10'>" +
                "</td>" +

                "<td  id='tdRepayPrcp_" + repayPlan.termNum + "'  ondblclick='repayPlan_List.showRepayPrcpInputValueNew(this)'>" +
                "<span class='tab_span money_right' id='spanRepayPrcp_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpFormat + "</span>" +
                "<input type='text' name='repayPrcp" + repayPlan.termNum + "' id='repayPrcp_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.repayPrcp + "'   onblur='repayPlan_List.changeRepayPrcpValue(this);' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">" +
                "</td>" +

                "<td hidden id='tdRepayIntst_" + repayPlan.termNum + "' ondblclick='repayPlan_List.showRepayIntsInputValueNew(this)'>" +
                "<span class='tab_span money_right' id='spanRepayIntst_" + repayPlan.termNum + "'>" + repayPlan.repayIntstFormat + "</span>" +
                "<input type='text' name='repayIntst" + repayPlan.termNum + "' id='repayIntst_" + repayPlan.termNum + "' class='repayInput datelogo BOTTOM_LINE' value='" + repayPlan.repayIntst + "'   onblur='repayPlan_List.changeRepayIntstValue(this);' onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">" +
                "</td>" +
                "<td hidden >" +
                "<span class='tab_span money_right' id='spanFeeSum_" + repayPlan.termNum + "'>" + repayPlan.feeSumFormat + "</span>" +
                "<input type='text' name='feeSum" + repayPlan.termNum + "' id='feeSum_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.feeSum + "'>" +
                "</td>" +
                "<td  hidden>" +
                "<span class='tab_span money_right' id='spanRepayPrcpIntstSum_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpIntstSumFormat + "</span>" +
                "<input type='text' name='repayPrcpIntstSum" + repayPlan.termNum + "' id='repayPrcpIntstSum_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.repayPrcpIntstSum + "'>" +
                "</td>" +
                "<td >" +
                "<span class='tab_span money_right' id='spanRrepayPrcpBalAfter_" + repayPlan.termNum + "'>" + repayPlan.repayPrcpBalAfterFormat + "</span>" +
                "<input type='text' name='repayPrcpBalAfter" + repayPlan.termNum + "' id='repayPrcpBalAfter_" + repayPlan.termNum + "' class='repayInput' value='" + repayPlan.repayPrcpBalAfter + "' >" +
                "</td>" +
                "<td align='center' style='display:none'>" +
                "<input type='text' name='repayIntstOrig" + repayPlan.termNum + "' id='repayIntstOrig_" + repayPlan.termNum + "'  value='" + repayPlan.repayIntstOrig + "'>" +
                "</td>" +
                "<td align='center' style='display:none'>" +
                "<input type='text' name='outFlag" + repayPlan.termNum + "' id='outFlag_" + repayPlan.termNum + "'  value='" + repayPlan.outFlag + "'>" +
                "</td>" +
                "<td align='center' style='display:none'>" +
                "<input type='text' name='currentNum" + repayPlan.termNum + "' id='currentNum_" + repayPlan.termNum + "'  value='" + repayPlan.currentNum + "'>" +
                "</td>" +

                "<td align='center' style='display:none'>" +
                "<input type='text' name='repayDate" + repayPlan.termNum + "' id='repayDate_" + repayPlan.termNum + "'  value='" + repayPlan.repayDate + "'>" +
                "</td>" +

                "</tr>"
            );


        }
    });
}
function changeMfFincAppValue(delayDateMap){
    var endIndex = 0;
    if (delayDateMap != null&&delayDateMap != ""&&JSON.stringify(delayDateMap) != "{}") {
        $("select[name=endDate]").html("");
        $.each(delayDateMap, function (key, value) {
            if(endIndex<31){
                $("select[name=endDate]").append(
                    "<option value='" + value + "'>" + value + "</option>"
                );
                endIndex=endIndex+1;
            }

        });
        if(delayDateMap.delayDate!=null){
            $("select[name=endDate]").val(delayDateMap.delayDate);
        }else{
            $("select[name=endDate]").val(delayDateMap.intstEndDate);

        }

        $("select[name=endDate]").attr("readOnly",true);
    }else{
        $("select[name=endDate]").html("");
        $.each(delayDateMapOld, function (key, value) {
            if(endIndex==0){
                $("select[name=endDate]").append(
                    "<option value='" + value + "'>" + value + "</option>"
                );
                endIndex=1;
            }
        });
        $("select[name=endDate]").attr("readOnly",true);
    }
}

//发放日期日历小控件的事件
function selectriliRepayPlan(obj) {
    $("[name=beginDate]").next().click;
    selectriliRepayPlanNew(obj, "beginDate", beginDate, today, getChangePlanListBybeginDate);
};

function selectriliRepayPlanNew(obj, name, mintime, maxtime, chooseCallback) {
    var opts = {};
    if (!name) {
        opts.elem = $(obj).parent().parent().find("input")[0];
    } else {
        opts.elem = $(obj).parent().parent().find("input[name=" + name + "]")[0];
    }
    if (mintime) {
        opts.min = mintime;
    }
    if (maxtime) {
        opts.max = maxtime;
    }
    if (typeof chooseCallback === "function") {
        opts.choose = chooseCallback;
    }
    fPopUpCalendarDlg(opts);
};