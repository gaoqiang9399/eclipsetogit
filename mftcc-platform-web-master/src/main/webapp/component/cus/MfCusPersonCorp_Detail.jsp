<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>新增</title>
</head>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusPersonCorp.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
<script type="text/javascript" src="${webPath}/component/nmd/js/NmdWay.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    var projectName = '${projectName}';
    var cusNo;
    var pageView = '';
    $(function() {
        cusNo=$("input[name=cusNo]").val();
        MfCusPersonCorp.init();
    });
</script>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 colum margin_top_20">
			<div class="bootstarpTag fourColumn">
				<div class="form-title"></div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form  method="post" id="MfCusPersonCorpForm" theme="simple" name="operform" action="${webPath}/mfCusPersonCorp/updateAjax">
					<dhcc:bootstarpTag property="formcusPersonCorpBase" mode="query"/>
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="保存" action="保存" onclick="MfCusPersonCorp.insertCusPersonCorpBase('#MfCusPersonCorpForm')"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
