<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>新增</title>
    <script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
    <script type="text/javascript" src="${webPath}/component/cus/js/MfCusGetNotCusInfo.js"></script>
    <script type="text/javascript">
        var cusmugno="";
        cusmugno='${cusmngno}';
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <!--
        两列表单使用 col-md-8 col-md-offset-2
        四列表单使用 col-md-10 col-md-offset-1
         -->
        <div class="col-md-8 col-md-offset-2 margin_top_20">
            <div class="bootstarpTag">
                <%--<div class="form-title">借阅申请表</div>--%>
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="MfArchiveBorrowForm" theme="simple" name="operform" action="${webPath}/mfCusCustomer/cusslipAjax">
                    <dhcc:bootstarpTag property="formcusslip" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
        <dhcc:thirdButton value="转单移交" action="转单移交" onclick="MfCusGetNotCusInfo.cusslip('#MfArchiveBorrowForm');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
