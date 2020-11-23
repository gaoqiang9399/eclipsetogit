<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>查询</title>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form  method="post" id="queryCusInfo" name="operform" action="${webPath}/MfCusInfoDw/updateNameAjax">
                    <dhcc:bootstarpTag property="formqueryCusInfo" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="查询" action="查询" onclick="MfCusInfoDw.queryAndDw();"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
<script type="text/javascript" src="${webPath}/component/cusInfoDw/js/MfCusInfoDw.js"></script>
</body>
</html>