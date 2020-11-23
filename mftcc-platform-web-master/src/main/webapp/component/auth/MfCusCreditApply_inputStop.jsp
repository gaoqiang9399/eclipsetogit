<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信终止</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
            <div class="scroll-content">
                <div class="col-md-10 col-md-offset-1 column margin_top_20">
                    <div class="bootstarpTag">
                        <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
                        <form  method="post" id="MfCusCreditApplyStopForm" theme="simple" name="operform" action="${webPath}/mfCusCreditApply/insertStopAjax">
                            <dhcc:bootstarpTag property="formcreditStopAdd" mode="query"/>
                        </form>
                    </div>
                </div>
		    </div>
            <div class="formRowCenter">
                <!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
                <dhcc:thirdButton value="保存" action="保存" onclick="MfCusCreditApply_inputStop.ajaxSave('#MfCusCreditApplyStopForm')"></dhcc:thirdButton>
                <dhcc:thirdButton value="关闭" action="关闭" typeclass="myclose cancel" onclick="myclose_click();"></dhcc:thirdButton>
            </div>
        </div>
	</body>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_inputStop.js'></script>
		<script type="text/javascript">
			$(function(){
                MfCusCreditApply_inputStop.init();
			});
		</script>
</html>
