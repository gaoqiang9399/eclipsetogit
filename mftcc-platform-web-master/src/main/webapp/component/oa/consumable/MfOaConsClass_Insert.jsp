<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="OaConsClassInsert" theme="simple"
					name="operform" action="${webPath}/mfOaConsClass/insertAjax">
					<dhcc:bootstarpTag property="formconsclass0002" mode="query" />
				</form>
				<input id="page_flag" type="hidden" value="new_class_page"/>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="提交" typeclass="insertAjax"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
	</div>
	</div>
</body>
<script type="text/javascript"
	src="${webPath}/component/oa/consumable/js/MfOaConsClassInsert.js"></script>
<script type="text/javascript">
	$(function() {
		OaConsClassInsert.init();
	});
</script>
</html>

