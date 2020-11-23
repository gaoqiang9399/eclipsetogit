<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>编辑</title>
    <script type="text/javascript" src="${webPath}/component/cus/intangibleassets/js/MfCusIntangibleAssets.js"></script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form  method="post" id="cusIntangibleAssetsEdit" theme="simple" name="operform" action="${webPath}/mfCusIntangibleAssets/updateAjax">
                    <dhcc:bootstarpTag property="formcusIntangibleAssetsBase" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存" onclick="MfCusIntangibleAssets.saveCusIntangibleAssets('#cusIntangibleAssetsEdit');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>

