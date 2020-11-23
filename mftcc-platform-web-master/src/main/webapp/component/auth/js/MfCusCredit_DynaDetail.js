$(function() {
    /**滚动条**/
    top.infIntegrity = null;
    $("body").mCustomScrollbar({
        advanced:{
            //滚动条根据内容实时变化
            updateOnContentResize:true
        },
        callbacks: {
            //正在滚动的时候执行回调函数
            whileScrolling: function(){
                if ($(".changeval").length>0) {
                    $(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
                }
            }
        }
    });
    MfCusCredit.init();
    // 初始化授信风险拦截信息
    initRiskLevel();
    //初始化授信冻结/解冻按钮
    initFrozenThawButton();
    // 初始化授信调整，临额调整按钮
    initAdjustTemporaryBtn();
    // 初始化授信终止，授信失效按钮
    initCreditStopInvalidBtn();
    // 获取用信历史信息
    getMfCusCreditUseHisInfo();
    // 产品授信明细信息
    getMfCusCreditProductDetailListInfo();
    //初始化尽调报告
    surveyReport_init();

    if(templateCreditId!="credit24"){
        $("#creditDebtBtn").hide();
    }
    top.LoadingAnimate.stop();
});
// 尽调报告按钮初始化
 function surveyReport_init() {
    // 查询当前是否已保存尽调报告
    $.ajax({
        url : webPath+"/mfTuningReport/getByAppIdAjax?appId=" + creditAppId,
        data : {},
        type : 'post',
        dataType : 'json',
        async : true,
        success : function(data) {
            if (data.uploadSize == 1) {// 保存过尽调报告
                $("#surveyReport").removeClass("btn-lightgray");// 去掉灰色样式
                $("#surveyReport").addClass("btn-forestgreen");// 添加绿色样式
                surveyReport_bindClick(creditAppId);
            }
        }
    });
};
// 尽调报告绑定click事件
 function surveyReport_bindClick(creditAppId) {
    $("#surveyReport").bind("click", function() {
        top.openBigForm(webPath + "/mfTuningReport/getReportDtail?appId=" + creditAppId + "&type=1&creditAppId=" + creditAppId + "&creditModel=" + creditModel, "尽调报告", function () {
        });
    });
};
// 产品授信明细信息
function getMfCusCreditProductDetailListInfo(){
    $.ajax({
        url:webPath+'/mfCusCreditApply/getMfCusPorductCreditListAjax',
        type:'post',
        data:{
            creditAppId:creditAppId
        },
        async: false,
        dataType:"json",
        success:function(data){
            if(data.flag == "success"){
                if(data.ifShowPorductCreditFlag == "1"){
                    $("#mfCusProductCreditInfo").html(data.mfCusCreditUseHisListHtml);
                    $("#productCreditInfo").show();
                    $("#productCreditInfo #tablist").show();
                }
            }
        },error:function(){
            LoadingAnimate.stop();
        }
    });
}
// 获取用信历史信息
function getMfCusCreditUseHisInfo(){
    $.ajax({
        url:webPath+'/mfCusCreditApply/getMfCusCreditUseHisInfoAjax',
        type:'post',
        data:{
            wkfAppId:wkfAppId,
            cusNo:cusNo,
            creditModel:creditModel,
            pactId:pactId
        },
        async: false,
        dataType:"json",
        success:function(data){
            if(data.flag == "success"){
                if(data.ifShowUseHisFlag == "1"){
                    $("#mfCusCreditUseHisDiv").html(data.mfCusCreditUseHisListHtml);
                    $("#mfCusCreditUseHis").show();
                    $("#mfCusCreditUseHis #tablist").show();
                }
            }
        },error:function(){
            LoadingAnimate.stop();
        }
    });
}
// 初始化授信状态
function initCreditSts(){
    $.ajax({
        url:webPath+'/mfCusCreditApply/getCreditAppStsAjax',
        type:'post',
        data:{creditAppId:creditAppId},
        async: false,
        dataType:"json",
        success:function(data){
            if(data.flag == "success"){
                $("#creditStsName").text(data.creditStsName);
            }
        },error:function(){
            LoadingAnimate.stop();
        }
    });
}
// 初始化授信终止，授信失效按钮
function initCreditStopInvalidBtn(){
    $.ajax({
        url:webPath+'/mfCusCreditApply/getCreditStopInvalidFlag',
        type:'post',
        data:{creditAppId:creditAppId},
        async: false,
        dataType:"json",
        success:function(data){
            if(data.flag == "success"){
                if(data.ifInvalid !="1"){
                    $("#cusCreditInvalid").removeClass("btn-opt");
                    $("#cusCreditInvalid").addClass("btn-opt-dont");
                    $("#cusCreditInvalid").attr("disabled",'false');
                }else{
                    $("#cusCreditInvalid").removeClass("btn-opt-dont");// 去掉灰色样式
                    $("#cusCreditInvalid").addClass("btn-opt");// 添加蓝色
                    $("#cusCreditInvalid").removeAttr("disabled");;// 添加蓝色
                }
                if(data.ifStop !="1"){
                    $("#cusCreditStop").removeClass("btn-opt");
                    $("#cusCreditStop").addClass("btn-opt-dont");
                    $("#cusCreditStop").attr("disabled",'false');
                }else{
                    $("#cusCreditStop").removeClass("btn-opt-dont");// 去掉灰色样式
                    $("#cusCreditStop").addClass("btn-opt");// 添加蓝色
                    $("#cusCreditStop").removeAttr("disabled");;// 添加蓝色
                }
            }
        },error:function(){
            LoadingAnimate.stop();
        }
    });
}
// 初始化授信调整，临额调整按钮
function initAdjustTemporaryBtn(){
    $.ajax({
        url:webPath+'/mfCusCreditApply/getCreditAdjustFlag',
        type:'post',
        data:{creditAppId:creditAppId},
        async: false,
        dataType:"json",
        success:function(data){
            if(data.flag == "success"){
                if(data.ifTemporary !="1"){
                    $("#creditTemporaryApply").removeClass("btn-opt");
                    $("#creditTemporaryApply").addClass("btn-opt-dont");
                    $("#creditTemporaryApply").attr("disabled",'false');
                }else{
                    $("#creditTemporaryApply").removeClass("btn-opt-dont");// 去掉灰色样式
                    $("#creditTemporaryApply").addClass("btn-opt");// 添加蓝色
                    $("#creditTemporaryApply").removeAttr("disabled");// 添加蓝色
                }
                if(data.ifAdjust !="1"){
                    $("#creditApply").removeClass("btn-opt");
                    $("#creditApply").addClass("btn-opt-dont");
                    $("#creditApply").attr("disabled",'false');
                }else{
                    $("#creditApply").removeClass("btn-opt-dont");// 去掉灰色样式
                    $("#creditApply").addClass("btn-opt");// 添加蓝色
                    $("#creditApply").removeAttr("disabled");// 添加蓝色
                }
            }
        },error:function(){
            LoadingAnimate.stop();
        }
    });
}

//重写dblUpdateVal，支持单字段编辑同时更新相关字段
function dblUpdateVal(key,data){

}
function getCusInfo(cusNo){
    if (typeof (baseType) != "undefined" && baseType != null && baseType != "") {
        switch (baseType) {
            case "1"://企业客户
                window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&busEntrance=cus_credit"+"&creditAppId="+creditAppId;
                break;
            case "2"://个人客户
                window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&busEntrance=cus_credit"+"&creditAppId="+creditAppId;
                break;
            case "3"://核心企业
                url = "/mfCusCoreCompany/getCoreCompanyView?coreCompanyUid=" + cusNo + "&busEntrance=cus_core_company&baseType=" + baseType;
                window.location.href = webPath + url;
                break;
            case "4"://渠道商
                url = "/mfBusTrench/getTrenchView?cusNo=" + cusNo + "&busEntrance=cus_trench&baseType=" + baseType;
                window.location.href = webPath + url;
                break;
            case "5"://资金机构
                url = "/mfBusAgencies/getAgenciesView?cusNo=" + cusNo + "&busEntrance=cus_agencies&baseType=" + baseType;
                window.location.href = webPath + url;
                break;
            case "6"://仓储机构
                url = "/mfCusWarehouseOrg/getWarehouseOrgView?cusNo=" + cusNo + "&busEntrance=cus_warehouse_org&baseType=" + baseType;
                window.location.href = webPath + url;
                break;
            case "9"://担保公司
                url = "/mfCusAssureCompany/assureCompanyView?cusNo=" + cusNo + "&busEntrance=cus_assure&baseType=" + baseType;
                window.location.href = webPath + url;
                break;
            case "A"://集团客户
                url = "/mfCusGroup/cusGroupView?groupNo=" + cusNo + "&busEntrance=cus_group&baseType=" + baseType;
                window.location.href = webPath + url;
                break;
            case "B"://個人渠道
                url = "/mfBusTrench/getTrenchView?cusNo=" + cusNo + "&busEntrance=cus_trench&baseType=" + cusBaseType;
                window.location.href = webPath + url;
                break;
            case "C"://合作机构
                url = "/mfCusCooperativeAgency/cooperativeAgencyView?orgaNo=" + cusNo + "&busEntrance=cus_coopAgency&baseType=" + baseType;
                window.location.href = webPath + url;
                break;
            default:
                top.LoadingAnimate.stop();
                alert("该客户类型暂不支持，开发中。。。", 3);
                break;
        }

    } else {
        top.LoadingAnimate.stop();
        alert("客户类型不存在", 0);
    }
}

//单独提交业务流程
function doCommitProcess(){
    $.ajax({
        url:webPath+"/mfCusCreditApply/commitBusProcessAjax?wkfAppId="+wkfAppId,
        success:function(data){
            if(data.flag=="success"){
                alert(data.msg,1);
                getNextBusPoint();
                $("#wj-modeler1").empty();
                showWkfFlow($("#wj-modeler1"),wkfAppId);
            }else{
                alert(data.msg,0);
            }
        }
    });
}
//提交审批
function processSubmitAjax(){
    if(top.creditFlag){
        wkfCallBack();
        alert(top.getMessage("CONFIRM_OPERATION","提交到审批"),2,function(){
            LoadingAnimate.start();
            $.ajax({
                url : webPath+"/mfCusCreditApply/processSubmitAjax",
                type : 'post',
                dataType : 'json',
                data : {
                    cusNo : cusNo,
                    wkfAppId : wkfAppId
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        if (data.node == "processaudit") {
                            window.top.alert(data.msg, 3);
                            //实时更新授信状态
                            $.ajax({
                                url : webPath+"/mfCusCreditApply/getCreditStsInfo",
                                data : {
                                    wkfAppId : wkfAppId
                                },
                                type : 'post',
                                dataType : 'json',
                                success : function(data) {
                                    var status = data.status;
                                    var creditSum = data.creditSum;
                                    var applySum = data.applySum;
                                    getCreditSts(status,creditSum,applySum);
                                },
                                error : function() {
                                    alert(data.msg, 0);
                                }
                            });
                            getNextBusPoint();
                            $("#wj-modeler1").empty();
                            showWkfFlow($("#wj-modeler1"),wkfAppId);
                        } else {
                            getNextBusPoint();
                            $("#wj-modeler1").empty();
                            showWkfFlow($("#wj-modeler1"),wkfAppId);
                        }
                    } else {
                        alert(data.msg);
                    }
                }
            });
        });
    }
}

// 删除文件
function delFile() {
    var srcPath = "/tmp/";
    $.ajax({
        url : webPath+"/uploadFile/delFile",
        data : {
            srcPath : srcPath
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {

        },
        error : function() {
//			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
        }
    });
};

//单字段编辑的保存回调方法。
function oneCallback(data,disVal) {
    var name = data[0].name;
    var value = data[0].value;
    var $_form = this;
    var formAction = $_form.attr("action");
    if (formAction === webPath+"/mfCusCreditApply/updateAjaxByOne") {
        if(name=="creditSum"){
            var creditSumStr = CalcUtil.formatMoney(CalcUtil.divide(value.replace(/,/g,""),10000),6);
            $("#creditSum").text(creditSumStr);
            $("#authBal").text(creditSumStr);
        }else if(name=="creditTerm"){
            $("#creditTerm").text(value + "个月");
        }
    }
}


function adjCreditApply(creditModel) {
    var showType = "1";
    top.openBigForm(webPath + "/mfCusCreditApply/creditInitInput?showType=" + showType + "&cusNo=" + cusNo + "&creditAppId=" + creditAppId + "&creditModel=" + creditModel, "授信申请", function () {
        if (top.creditSave == "1") {
            top.creditSave = "0";
            window.location.href = webPath + "/mfCusCreditApply/getCusCreditView?&cusNo=" + top.cusNo + "&creditAppId=" + top.creditAppId + "&busEntrance=credit" + "&entrance=credit";

        }
    });
}

function creditFrozeThaw(operaType){
    var url = webPath+"/mfCreditIntentionApply/frozenThawInput?operaType=" + operaType + "&cusNo=" + cusNo+"&creditAppId="+creditAppId;
    var title = "";
    if(operaType==1){
        title ="授信冻结";
    }else if (operaType==2){
        title ="授信解冻";
    }
    top.frozeThawFlag = false;
    top.ifApprove = false;
    top.openBigForm(url, title, function () {
        if (top.frozeThawFlag) {
            //初始化授信冻结/解冻按钮
            initFrozenThawButton();
            // 初始化授信调整，临额调整按钮
            initAdjustTemporaryBtn();
            // 初始化授信终止，授信失效按钮
            initCreditStopInvalidBtn();
            // 授信流程显示初始化
            MfCusCredit.init();
            // 初始化授信状态
            initCreditSts();
        }
    });
}
function cusCreditStop(){
    var url = webPath+"/mfCusCreditApply/inputForCreditStop?wkfAppId="+wkfAppId;
    var title = "授信终止";
    top.ifCreditStopFlag = false;
    top.openBigForm(url, title, function () {
        if (top.ifCreditStopFlag) {
            //初始化授信冻结/解冻按钮
            initFrozenThawButton();
            // 初始化授信调整，临额调整按钮
            initAdjustTemporaryBtn();
            // 初始化授信终止，授信失效按钮
            initCreditStopInvalidBtn();
            // 授信流程显示初始化
            MfCusCredit.init();
            // 初始化授信状态
            initCreditSts();
        }
    });
}
function cusCreditInvalid(){
    var url = webPath+"/mfCusCreditApply/inputForCreditInvalid?wkfAppId="+wkfAppId;
    var title = "授信失效";
    top.ifCreditInvalidFlag = false;
    top.openBigForm(url, title, function () {
        if (top.ifCreditInvalidFlag) {
            //初始化授信冻结/解冻按钮
            initFrozenThawButton();
            // 初始化授信调整，临额调整按钮
            initAdjustTemporaryBtn();
            // 初始化授信终止，授信失效按钮
            initCreditStopInvalidBtn();
            // 授信流程显示初始化
            MfCusCredit.init();
            // 初始化授信状态
            initCreditSts();
        }
    });
}
function listShowDetail(obj,url){
    if(url.substr(0,1)=="/"){
        url =webPath + url;
    }else{
        url =webPath + "/" + url;
    }
    top.openBigForm(url, "详情展示", function () {

    });
};

function wkfCallBack(){
    if(top.adjCreditFlag){
        window.location.href=webPath+"/mfCusCreditApply/getCusCreditView?&cusNo="+cusNo+"&creditAppId="+top.creditAppId+"&busEntrance=credit";
    }
};

//用章申请
function useChapterInsert() {
    if(isValid == '0'){
        window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"该产品授信已失效,","content2":"发起用章申请"}), 0);
        return;
    }
    var firstKindNo = chapterkindNo;
    // 客户新增业务前置校验（是否开户、是否存在保证金收取中的放款申请）
    var params = {
        "cusNo" : cusNo
    };
    // 判断该客户是否完善了基本信息
    $.ajax({
        url : webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+ cusNo,
        success : function(data) {
            if (data.fullFlag == '1') {// 全部都填写了
                // 准入拦截
                var parmData = {'nodeNo':'apply', 'relNo': cusNo, 'cusNo': cusNo};
                $.ajax({
                    url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
                    type : "post",
                    data : {ajaxData: JSON.stringify(parmData)},
                    dataType : "json",
                    success : function(data) {
                        if (data.exsitRefused == true) {// 存在业务拒绝
                            top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){});
                        }else if(data.exsitFX == true){//存在风险项
                            alert(top.getMessage("CONFIRM_DETAIL_OPERATION",{"content":"该客户存在风险项","operation":"新增业务"}), 2, function() {
                                window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
                            });
                        }else {
                            //传appId时是为了在业务新增页面取消时返回到原来的页面
                            window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
                        }
                    },
                    error : function() {
                    }
                });
            } else if (data.fullFlag == '0') {
                alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.infoName),0);
            }
        }
    });
}

//授信处模板打印
function creditTemplatePrint(){
    top.window.openBigForm(webPath+"/mfSysTemplate/toCreditTemplateList?templateType=12&tableId=tablecreditTemplate&"+docParm ,'模板打印', myclose);
}

//初始化授信冻结解冻
function initFrozenThawButton(){
    $.ajax({
        url:webPath+'/mfCusCreditApply/getFrozenThawFlag',
        type:'post',
        data:{cusNo:cusNo,creditAppId:creditAppId},
        async: false,
        dataType:"json",
        success:function(data){
            if(data.flag == "success"){
                var frozeFlag =data.frozeFlag;
                var thawFlag =data.thawFlag;
                if(frozeFlag == "0"){
                    $("#creditFrozen").removeClass("btn-opt");// 去掉灰色样式
                    $("#creditFrozen").addClass("btn-opt-dont");// 添加蓝色
                    $("#creditFrozen").attr("disabled",'false');
                }else{
                    $("#creditFrozen").removeClass("btn-opt-dont");// 去掉灰色样式
                    $("#creditFrozen").addClass("btn-opt");// 添加蓝色
                    $("#creditFrozen").removeAttr("disabled");;// 添加蓝色
                }
                if(thawFlag == "0"){
                    $("#creditThaw").removeClass("btn-opt");// 去掉灰色样式
                    $("#creditThaw").addClass("btn-opt-dont");// 添加蓝色
                    $("#creditThaw").attr("disabled",'false');
                }else{
                    $("#creditThaw").removeClass("btn-opt-dont");// 去掉灰色样式
                    $("#creditThaw").addClass("btn-opt");// 添加蓝色
                    $("#creditThaw").removeAttr("disabled");
                }
                //initFrozenThaw();
            }
        },error:function(){
            LoadingAnimate.stop();
        }
    });
};


// 获取授信风险检查信息
function getCreditRiskCheckResult(parm) {
    if (parm == '1') {
        top.openBigForm(webPath + "/appEval/getDetailResult?cusNo=" + cusNo + "&creditAppId=" + creditAppId + "&appSts=4&useType=3", "风险检查信息", function () {
        });
    }else{
        return false;
    }
};

//风险拦截
function cusRisk(){
    if(!(dialog.get('riskInfoDialog') == null)){
        dialog.get('riskInfoDialog').close();
    }
    top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){});
};

//初始化授信风险检查信息
var initRiskLevel = function(){
    var param = {};
    param.creditAppId = creditAppId;
    param.useType = "3";
    param = JSON.stringify(param);
    $.ajax({
        url : webPath+"/appEval/getCurrAppEvalDataAjax",
        data : {
            param : param,
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {
            LoadingAnimate.stop();
            if (data.flag == "success") {
                var appEval = data.appEval;
                var cusLevelName = data.cusLevelName;
                if(cusLevelName != null && cusLevelName != ""){
                    $("#creditRiskCheck-span").text(cusLevelName);
                    $("#creditRiskCheck-button").attr("onclick","getCreditRiskCheckResult('1');");
                    if(cusLevelName.indexOf("无风险") != -1){
                        $("#creditRiskCheck-button").attr("class","btn btn-view btn-forestgreen");
                    }else if(cusLevelName.indexOf("低风险") != -1){
                        $("#creditRiskCheck-button").attr("class","btn btn-view cus-middle");
                    }else if(cusLevelName.indexOf("业务拒绝") != -1 || cusLevelName.indexOf("高风险") != -1){
                        $("#creditRiskCheck-button").attr("class","btn btn-view btn-danger");
                    }
                }else{
                    $("#creditRiskCheck-span").text("未检查");
                    $("#creditRiskCheck-button").attr("onclick","getCreditRiskCheckResult('0');");
                    $("#creditRiskCheck-button").attr("class","btn btn-view btn-lightgray");//灰的
                }
            }
        },
        error : function() {
            LoadingAnimate.stop();
            alert(top.getMessage("ERROR_REQUEST_URL", "/appEval/getCurrAppEvalDataAjax"));
        }
    });
};
