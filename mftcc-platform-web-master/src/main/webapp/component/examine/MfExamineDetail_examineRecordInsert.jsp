<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript" src="${webPath}/component/include/calcUtil.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/examine/js/MfExamineDetail_examineRecordInsert.js"></script>
</head>
<style type="text/css">
    .mCSB_container {
        min-height: 100%;
    }
    .text-primary input {
        color: #428bca !important;
        font-weight: bold;
        -webkit-animation:shine 1.5s linear 0.5s infinite; animation:shine 1.5s linear 0.5s infinite;
    }
    .text-danger input {
        color: #a94442 !important;
        font-weight: bold;
        -webkit-animation:shine 1.5s linear 0.5s infinite; animation:shine 1.5s linear 0.5s infinite;
    }

    @-webkit-keyframes shine{0%{opacity:0.5} 100%{opacity: 1}}
    @keyframes shine{0%{opacity:0.5} 100%{opacity: 1}}
</style>
<%--<script type="text/javascript">
    $(function() {
        MfBusFincApp_examineTaskInsert.init();
    });
</script>--%>
<body class="overflowHidden bg-white">
<div class="container form-container" id="normal">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form id="insertForExamineDetailForm" method="post" theme="simple" name="operform" action="${webPath}/MfExamineDetailController/insertForExamineDetailAjax">
                    <dhcc:bootstarpTag property="formapply0007_query" mode="query" />
                </form>
            </div>
        </div>

    </div>

    <div class="formRowCenter">
        <dhcc:thirdButton value="提交" action="提交"
                          onclick="insertForExamineDetailForm('#insertForExamineDetailForm','1');"></dhcc:thirdButton>
        <dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
                          onclick="myclose_click();"></dhcc:thirdButton>
    </div>
    <div style="display: none;" id="fincUse-div"></div>
</div>
</body>
</html>