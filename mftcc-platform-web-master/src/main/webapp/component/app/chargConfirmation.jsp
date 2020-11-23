<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>

<!DOCTYPE html>
<html>
<head>
    <title>收费确认</title>

    <script type="text/javascript">
        $(function() {
            ApplyChargConfirmation.init();
        });

    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="appChargConfirmation" theme="simple" name="operform" action="${webPath}/mfBusApply/verifyChargConfirmation">
                    <dhcc:bootstarpTag property="formapplyChargConfirmation" mode="query"/>
                </form>
            </div>
        </div>
    </div>

</div>
 <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存" onclick="ApplyChargConfirmation.doSubmit('#appChargConfirmation');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
 </div>

</body>
<script type="text/javascript" src="${webPath}/component/app/js/appChargConfirmation.js?v=${cssJsVersion}"></script>
</html>
