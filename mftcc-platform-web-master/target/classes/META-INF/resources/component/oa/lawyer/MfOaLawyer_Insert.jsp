<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<html>
<head>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container" id="normal">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 column margin_top_20">
            <div class="bootstarpTag">
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form id="insertLawyerForm" method="post" theme="simple" name="operform"
                      action="<%=webPath %>/mfOaLawyer/insertAjax">
                    <dhcc:bootstarpTag property="formlawyer0002" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="保存" action="保存"
                          onclick="MfOaLawyer_Insert.ajaxSave('#insertLawyerForm')"></dhcc:thirdButton>
        <dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfOaLawyer_Insert.myclose();"></dhcc:thirdButton>
    </div>
    <div style="display: none;" id="fincUse-div"></div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/lawyer/js/MfOaLawyer_Insert.js"></script>
<script type="text/javascript">
    MfOaLawyer_Insert.path = "${webPath}";
    var processId = '${processId}';
    $(function () {
        MfOaLawyer_Insert.init();
    });

</script>
</html>