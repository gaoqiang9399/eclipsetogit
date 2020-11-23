<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusAssureCompany_cusInput.js"></script>
<script type="text/javascript">
    var idTypeMap = ${idTypeMap};
	$(function() {
		MfCusAssureCompany_cusInput.init();
	});
</script>
</head>
<body class="overflowHidden bg-white">
	<input id="ajaxData" name="ajaxData" type="hidden" value='${ajaxData}' />
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 margin_top_20">
				<div class="bootstarpTag">
					<!-- <div class="form-title">担保公司</div> -->
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="MfCusAssureCompanyForm" theme="simple" name="operform" action="${webPath}/mfCusAssureCompany/insertAjax">
						<dhcc:bootstarpTag property="formassurecompany0002" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusAssureCompany_cusInput.ajaxSave('#MfCusAssureCompanyForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
