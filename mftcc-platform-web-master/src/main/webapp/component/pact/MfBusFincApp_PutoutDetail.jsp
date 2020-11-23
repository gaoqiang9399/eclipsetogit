<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var cusNo='${mfBusFincApp.cusNo}';
			var appId='${mfBusFincApp.appId}';
			var pactId='${mfBusFincApp.pactId}';
			var pactNo='${mfBusFincApp.pactNo}';
			var isNewBank = "0";
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="fincAppInsertForm" theme="simple" name="cms_form" action="${webPath}/mfBusFincApp/insertAjax">
						<dhcc:bootstarpTag property="formfincapp0001" mode="query"/>
					</form>
				</div>
				<%-- <%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) --> --%>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$("i.i-rili").remove();
		$("font[color=#FF0000]").remove();
	});
</script>	
</html>
