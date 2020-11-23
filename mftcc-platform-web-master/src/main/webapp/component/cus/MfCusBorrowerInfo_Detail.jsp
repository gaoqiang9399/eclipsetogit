<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>查询</title>
</head>
<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusBorrowerInfo_Insert.js'> </script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
<script type="text/javascript">
    var pageView = '';
    var cusNo;
    var cusBaseType = '${cusBaseType}';
    var relNo = '${relNo}';
    $(function(){
        cusNo=$("input[name=cusNo]").val();
        MfCusBorrowerInfo_Insert.init();
    });
    function selectBorrower(){
        MfCusBorrowerInfo_Insert.selectBorrower();
    }
    function updateMarital() {
        MfCusBorrowerInfo_Insert.updateMarital();
    }
</script>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">


        <!--
        两列表单使用 col-md-8 col-md-offset-2
        四列表单使用 col-md-10 col-md-offset-1
         -->
        <div class="col-md-10 col-md-offset-1 margin_top_20">
            <div class="bootstarpTag">
                <!-- <div class="form-title">外访-保证人信息</div> -->
                <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                <form  method="post" id="MfCusBorrowerInfoForm" theme="simple" name="operform" action="${webPath}/mfCusBorrowerInfo/updateAjax">
                    <dhcc:bootstarpTag property="formcusBorrowerBase" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->

        <dhcc:thirdButton value="保存" action="保存" onclick="MfCusBorrowerInfo_Insert.ajaxSave('#MfCusBorrowerInfoForm')"></dhcc:thirdButton>
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
