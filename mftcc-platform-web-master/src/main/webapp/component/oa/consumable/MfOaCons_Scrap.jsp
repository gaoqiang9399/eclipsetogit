<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>盘点</title>
<link type="text/css" rel="stylesheet"
	href="UIplug/umeditor-dev/themes/default/_css/umeditor.css" />
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="OaConsScrap" theme="simple"
							name="operform" action="${webPath}/mfOaCons/updateAjax">
							<dhcc:bootstarpTag property="formconsumable0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="提交" typeclass="updateAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
			</div>
		</div>
</body>
<script type="text/javascript"
	src="${webPath}/component/oa/consumable/js/MfOaConsScrap.js"></script>
<script type="text/javascript">
	$(function() {
		OaConsScrap.init();
	});
</script>
</html>
