<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>房产评估</title>
    <script type="text/javascript" src="${webPath}/component/collateral/js/realEstateAssessment.js"></script>
    <link rel="stylesheet" href="${webPath}/component/sys/css/MfSysSkinUser_List.css?v=${cssJsVersion}">
    <script type="text/javascript">
        $(function() {
            $(".scroll-content").mCustomScrollbar({
                advanced: {
                    //滚动条根据内容实时变化
                    updateOnContentResize: true
                }
            });
        });
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form  method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="">
                    <dhcc:bootstarpTag property="formRealEstateEval" mode="query"/>
                </form>
                <div disable="true" class="content collapse in"  id="valuationResults" style="margin-top: 10px; display:block;">
                    <div class="form-tips" id="requesTitle" style="display:none">在线评估结果，请勿修改。</div>
                    <form method="post" id="onlineDlevalinfo0002" theme="simple" name="operform" >
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
