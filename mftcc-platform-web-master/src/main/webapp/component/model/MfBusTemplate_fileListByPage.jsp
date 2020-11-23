<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><%=request.getAttribute("title")%>
    </title>
    <link rel="stylesheet" type="text/css"
          href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
    <script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileList.js"></script>
    <script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileListByPage.js"></script>
    <script type="text/javascript" src="${webPath}/component/model/js/qrcode.min.js"></script>

    <script type="text/javascript">
        var cusNo = '${cusNo}';
        var appId = '${appId}';
        var pactId = '${pactId}';
        var fincId = '${fincId}';
        var nodeNo = '';//文件打印
        var temBizNo =  appId ;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
        var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&fincId=' + fincId;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
        var querySaveFlag_pl = "0";
        var approvalNodeNo = "${approvalNodeNo}";
        //二维码标识 0-禁用 1-启用
        var qrCodeShowFlag = '${qrCodeShowFlag}';
        var followPactNoShowSts = '${followPactNoShowSts}';
        $(function () {
            MfBusTemplate_fileListByPage.init();
            if (qrCodeShowFlag == "0") {
                $(".qrCode").remove();
            }
        });
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont scroll-content" >
    <%@ include file="/component/model/templateIncludePage.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
        <c:if test="${mfBusFollowPactList != null}">
            <div class="list-table">
                <div class="title">
                    <span><i class="i i-xing blockDian"></i>从合同文档</span>
                    <button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse"
                            data-target="#followPact">
                        <i class="i i-close-up"></i>
                        <i class="i i-open-down"></i>
                    </button>
                </div>
                <div id="followPact" class="content collapse in" aria-expanded="true">
                    <dhcc:tableTag paginate="mfBusFollowPactList" property="tablemfBusFollowPactNo"
                                   head="true"/>
                </div>
            </div>
        </c:if>
    </div>

</div>
<div id="qrcode"></div>
</body>
</html>