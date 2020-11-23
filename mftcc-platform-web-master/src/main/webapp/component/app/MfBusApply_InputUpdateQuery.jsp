<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/component/include/common.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
    <script type="text/javascript"
            src="${webPath}/component/app/js/MfBusApply_InputQuery.js?v=${cssJsVersion}"></script>
    <script type="text/javascript"
            src="${webPath}/component/app/js/MfBusApply_applyInput.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/include/showBusinessCount.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/nmd/js/parLoanuse.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
    <script type="text/javascript" src="${webPath}/component/app/js/pub_apply_view_closure.js"></script>
    <script type="text/javascript" src="${webPath}/component/include/calcUtil.js?v=${cssJsVersion}"></script>
</head>
<style type="text/css">
    .mCSB_container {
        min-height: 100%;
    }
</style>
<script type="text/javascript">
    var cusNo = '${cusNo}';
    var appId = '${appId}';//传递参数是为了在新增业务页面取消时，返回到原来的页面
    var fromPage = '${dataMap.fromPage}';
    var firstKindNo = '${kindNo}';
    var wkfAppId = '${wkfAppId}';
    var nodeNo = '${nodeNo}';
    var maxAmt = null;
    var minAmt = null;
    var minTerm = null;
    var maxTerm = null;
    var minFincRate = null;
    var maxFincRate = null;
    var creditAmt = null;
    var kindCreditAmt = null;
    var oldKindNo = firstKindNo;
    var busModel = '${busModel}';
    var ajaxData = '${ajaxData}';
    ajaxData = JSON.parse(ajaxData);
    var rateTypeMap = ajaxData.rateType;
    var data = ajaxData.data;
    var updateType = '${dataMap.updateType}';
    var processId = '${processId}';
    var breedInitFlag=0;
    var agenicesInitFlag=0;
    $(function () {
        myCustomScrollbarForForm({
            obj: ".scroll-content",
            advanced: {
                updateOnContentResize: true
            }
        });
        if (data.feeShowFlag == "1") {
            $('#busfee-list table').find("colgroup").remove();
            $('.busfeeInfo').removeClass('hidden');
        }
        maxAmt = data.maxAmt;
        minAmt = data.minAmt;
        minTerm = data.minTerm;
        maxTerm = data.maxTerm;
        termType = data.termType;
        minFincRate = data.minFincRate;
        maxFincRate = data.maxFincRate;
        if (data.creditflag == "success") {
            creditAmt = data.creditAmt;
            if (data.kindCreditflag == "success") {
                kindCreditAmt = data.kindCreditAmt;
            }
        }
        /*bindVouTypeByKindNo($("#insertForApplyForm").find(
            '[name=vouType]'), firstKindNo);*/
        //贷款投向选择组件
        $("select[name=fincUse]").popupSelection({
            searchOn: true,//启用搜索
            inline: true,
            items: data.fincUse,
            multiple: false,
            changeCallback: cleanFincUseSmShow
            //单选
        });
        // 是否隐藏 复利利率上浮字段
        if (data.cmpdRateType == "0") {//隐藏
            $('input[name=cmpdFloat]').parents("td").hide();// 字段td
            $('input[name=cmpdFloat]').parents("td").prev("td")
                .hide();// 标签td
            $("input[name=cmpdFloat]").attr("mustinput", "");

        }
        //不允许修改产品和客户
        $("select[name=kindNo]").val(firstKindNo);
        $("select[name=kindNo]").attr("disabled", "disabled");
        $("input[name=cusName]").attr("readonly", true);
        $("input[name=cusName]").attr("onclick", "");
        $("input[name=cusName]").parent().find("i").remove();
        //初始化加载
        $(".anchor-ctrl select").change(function () {
            handleAnchorFun(this);
        });
        $(".anchor-ctrl select").each(function () {
            handleAnchorFun(this);
        });
        //信贷.房抵贷申请确认环节，超出产品配置金额处理
        var appConfirmAmt = '${appConfirmAmt}';
        if (maxAmt != null && maxAmt != "" && appConfirmAmt != "") {
            var maxAmtNum = new Number(maxAmt);
            var applyAmt = new Number(appConfirmAmt);
            if (parseFloat(applyAmt) > parseFloat(maxAmtNum)) {
                $("input[name=appAmt]").val(0);
                alert("超出最大申请金额请手动输入", 0);
            }
        }
        $("[name='loanKind']").attr("disabled", "disabled");

        var isRelCredit = $("[name='isRelCredit']").val();
        if("1" == isRelCredit){
            let creditAppId = $("[name='creditAppId']").val();
            //初始化合作银行
            MfBusApply_applyInput.agenciesInit(creditAppId);
            let agenciesId = $("[name='agenciesId']").val();
            MfBusApply_applyInput.breedInit(agenciesId,creditAppId);
        }
        MfBusApply_applyInput.breedInit1();
    });

</script>
<body class="overflowHidden bg-white">
<div class="container form-container" id="normal">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <s:if test='%{query=="cusbody"}'>
                    <div class="form-title"></div>
                </s:if>

                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form id="insertForApplyForm" method="post" theme="simple"
                      name="operform"
                      action="${webPath}/mfBusApply/insertForApplyAjax_query">
                    <dhcc:bootstarpTag property="formapply0007_query" mode="query"/>
                </form>
            </div>
            <div id="coborrNumName" class="row clearfix">
                <%@ include file="/component/app/MfBusCoborrList.jsp" %>
            </div>
            <%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
        </div>
    </div>
    <div class="formRowCenter">
        <c:if test="${nodeNo =='apply'}">
            <dhcc:thirdButton value="暂存" action="暂存"
                              onclick="insertForApply('#insertForApplyForm','0');"></dhcc:thirdButton>
        </c:if>
        <dhcc:thirdButton value="提交" action="提交"
                          onclick="insertForApply('#insertForApplyForm','1');"></dhcc:thirdButton>
        <dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
                          onclick="cancelApply();"></dhcc:thirdButton>
    </div>
    <div style="display: none;" id="fincUse-div"></div>
</div>
</body>
</html>