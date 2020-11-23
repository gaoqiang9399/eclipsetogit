<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
	String wkfAppId = (String) request.getParameter("wkfAppId");
	String taskId = (String) request.getParameter("taskId");
	String appId = (String) request.getParameter("appId");
%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript">
	var docParm = "cusNo=${mfBusFincAppChild.cusNo}&relNo=${mfBusFincAppChild.appId}";// 查询文档信息的url的参数
	var cusNo = '${mfBusFincAppChild.cusNo}';
	var appId = '${mfBusPact.appId}';
	var pactId = '${mfBusPact.pactId}';
	var pactNo = '${mfBusPact.pactNo}';
	var processId = '${mfBusPact.fincProcessId}';
	var ajaxData = '${ajaxData}';
	var PROJECT_ID = '${PROJECT_ID}';
	var isCusDoc = "cusDoc";
	var id = '${id}';
	var cusNoFund = cusNo;
    var docParm = 'relNo=' + id + '&scNo=history_record';
	ajaxData = JSON.parse(ajaxData);
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="fincAppInsertForm" theme="simple"
						name="cms_form" action="${webPath}/mfBusFincApp/bankAccUpdateAjax">
						<dhcc:bootstarpTag property="formfincapp0005" mode="query" />
					</form>
				</div>
				<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
			</div>
				
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="更新" action="更新"
				onclick="MfBusFincAppInsert.bankAccUpdateAjax('#fincAppInsertForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
				onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
<script type="text/javascript"
	src="${webPath}/component/pact/js/MfBusFincAppInsert.js"></script>
<script type="text/javascript">
	$(function() {
		MfBusFincAppInsert.init();
	});
</script>
</html>
