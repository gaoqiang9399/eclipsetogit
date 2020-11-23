<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/bussNodePmsBiz.jsp" %>
<%
    String cellDatas = (String) request.getAttribute("cellDatas");
    String blockDatas = (String) request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css"/>
    <link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css"/>
    <script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
    <script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
    <script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
    <script type="text/javascript" src="${webPath}/component/app/js/pub_apply_view_closure.js"></script>
    <script type="text/javascript" src="${webPath}/component/pact/js/pub_pact_view_closure.js"></script>
    <script type="text/javascript" src='${webPath}/component/compensatory/js/MfBusCompensatory.js'></script>
    <script type="text/javascript" src='${webPath}/component/recourse/js/MfBusRecourseApplyDetail.js'></script>
    <!-- 同盾认证报告 -->
    <!-- <script type="text/javascript" charset="utf-8" src="//cdnjs.tongdun.cn/preloan/tdreport.1.4.min.js?r=" + (newDate()).getTime()></script> -->

    <link rel="stylesheet" href="${webPath}/component/pact/css/MfBusPact_Detail.css?v=${cssJsVersion}"/>
    <script type="text/javascript">
        var pactId, appId, wkfAppId, cusNo, pactSts, fincSts, fincId, pleId;
        var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
        var headImg = '${headImg}';
        var ifUploadHead = '${ifUploadHead}';
        var busModel = '${busModel}';
        var coreCusNo = '${coreCusNo}';
        var fundCusNo = '${fundCusNo}';
        var wareHouseCusNo = '${wareHouseCusNo}';
        var pactModelNo = '${mfBusPact.kindNo}';
        var mfSysKindExt1 = '${mfSysKindExt1}';
        var mfSysKindExt3 = '${mfSysKindExt3}';
        pactId = '${mfBusPact.pactId}';
        var pactIdForExamine = pactId;
        var pactNo = '${mfBusPact.pactNo}';
        var cusNo = '${mfBusPact.cusNo}';
        var appId = '${mfBusPact.appId}';
        var canRepay = '${canRepay}';
        var wkfAppId = '${mfBusFincApp.wkfRepayId}';
        var pactSts = '${mfBusPact.pactSts}';//合同状态值参考BizPubParm中的字段PACT_STS**
        var fincSts = '${mfBusFincApp.fincSts}';
        var fincId = '${mfBusFincApp.fincId}';
        var fincShowId = '${mfBusFincApp.fincShowId}';
        var wkfRepayId = '${mfBusFincApp.wkfRepayId}';
        var pleId = '${mfBusPledge.pleId}';
        var term = '${mfBusPact.term}';
        var termType = '${mfBusPact.termType}';
        var hisTaskList =
        ${json}.hisTaskList;
        var cusNoTmp = "";
        var webPath = '${webPath}';
        var query = '${query}';
        var queryFile = '${queryFile}';
        var operable = '${operable}';
        var queryExtensionFile = '${queryExtensionFile}';
        var extenSts = '${extenSts}';
        var extensionId = '${extensionId}';//展期申请ID
        var docParm = "query=" + queryFile + "&cusNo=" + cusNo + "&relNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&pactModelNo=" + pactModelNo + "&fincId=" + fincId + "&extenSts=" + extenSts;//查询文档信息的url的参数
        //展期要件列表所使用的
        var docExtensionParmNew = "query=" + queryExtensionFile + "&cusNo=" + cusNo + "&relNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&pactModelNo=" + pactModelNo + "&fincId=" + fincId + "&extenSts=" + extenSts + "&extensionId=" + extensionId;//查询文档信息的url的参数
        //这个是展期新增要件用的
        if (extenSts == "1") {
            var queryFileExtesion = '${queryFileExtesion}';
            var extensionApplyId = '${extensionApplyId}';
            var docExtensionParm = "query=" + queryFileExtesion + "&extensionApplyId=" + extensionApplyId;
        }

        var hasLawsuit = '${mfBusPact.hasLawsuit}';
        var fiveclassId = "";
        var recParam = <%=request.getAttribute("recParam")%>;
        var hasRecallFlag = recParam.hasRecallFlag;
        var recallingFlag = recParam.recallingFlag;
        var entrance = "business";
        var operateflag = '${operateflag}';
        var tailRepayFlag = '${tailRepayFlag}';
        var ifShowRepayHistory = '${ifShowRepayHistory}';
        var busEntrance = "${busEntrance}";//业务入口
        var cmpdRateType = '${cmpdRateType}';//复利利息是否收取：0-不收取、1-收取
        var compensatoryId = "";  //代偿业务关联id
        var recourseId = "";  //追偿业务关联id
        var PROJECT_ID = "${PROJECT_ID}";  // 项目标识
        var refundFee = "${refundFee}";  // 退费状态
        var extenLiXiOperateFlag = "${extenLiXiOperateFlag}";// 展期前利息收取按钮是否能够操作 0能收取 1不能收取
        var projectName = '${projectName}';

        var mfBusEditorRepayplanFlag = '${mfBusEditorRepayplanFlag}';   //还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑
        var isPreRepayment = '0'; // 0没有做过提前划款  1做过提前还款
        var examEntrance = 'finc';//贷后检查入口标识（借据）

        $(function () {
            //代偿
            MfBusCompensatory.mfBusCompensatoryConfirmInit();
            //追偿
            MfBusRecourseApplyDetail.getRecourseStatus();

        });

    </script>

</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix">
        <!--合同主信息 -->
        <div class="col-md-12 column block-left">
            <div class="bg-white block-left-div">
                <!--头部信息 -->
                <c:forEach items="${ dataMap.headView}" var="item">
                    <jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
                </c:forEach>

                <!--业务主信息-->
                <c:forEach items="${ dataMap.bodyView}" var="item">
                    <c:if test="${item.pmsBizNo==''}">
                        <jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
                    </c:if>
                    <c:if test="${item.pmsBizNo!=''}">
                        <dhcc:pmsTag pmsId="${item.pmsBizNo}">
                            <jsp:include page="${item.blockUrl}" flush="true"></jsp:include>
                        </dhcc:pmsTag>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <!--合同 附属信息 -->
        <%--<div class="col-md-4 column block-right">
            <div class="bg-white block-right-div">
                <c:forEach items="${ dataMap.afftView}" var="item">
                    <c:if test="${item.pmsBizNo==''}">
                        <jsp:include page="${item.blockUrl }" flush="true"></jsp:include>
                    </c:if>
                    <c:if test="${item.pmsBizNo!=''}">
                        <dhcc:pmsTag pmsId="${item.pmsBizNo}">
                            <jsp:include page="${item.blockUrl}" flush="true"></jsp:include>
                         </dhcc:pmsTag>
                    </c:if>
                </c:forEach>
            </div>
        </div>--%>
    </div>
</div>

</body>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFinc_DynaDetail.js"></script>

<script type="text/javascript" src="${webPath}/component/collateral/js/collateral.js"></script>
<script type="text/javascript" src="${webPath}/component/examine/js/BusExamine.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/extension/js/MfBusExtensionCommon.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfPrint.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/FeeCollect.js"></script>
<script type="text/javascript">

    //临时放这里，2017年9月19日 lzs 还款打印回执
    /* 	function doTaoDaPrintByAppId(url){
            url = url+"&busType=hk&"+docParm;//将方式设置为还款
            top.addFlag = false;
            top.htmlStrFlag = false;
            top.createShowDialog(url,"选择打印方式",'50','50',function(){
            });
        } */
</script>
</html>