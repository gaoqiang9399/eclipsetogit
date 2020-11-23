<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>新增</title>
    <script type="text/javascript" src="${webPath}/component/cus/js/MfCusDesignatedRecipient_Insert.js?v=${cssJsVersion}"></script>
    <script type="text/javascript">
        var cusNo = '${cusNo}';
        $(function () {
            MfCusDesignatedRecipient_Insert.init();
        });

        function insertCallBack() {
            top.addFlag = true;
            top.formOrTable = "table";
            myclose_click();
        };
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form method="post" id="designatedRecipient" theme="simple" name="operform" action="${webPath}/mfCusDesignatedRecipient/insertAjax">
                    <dhcc:bootstarpTag property="formData" mode="query"/>

                    <div style="display: none">
                        <input type='text' name='tmpHidden'/>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#designatedRecipient');"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
