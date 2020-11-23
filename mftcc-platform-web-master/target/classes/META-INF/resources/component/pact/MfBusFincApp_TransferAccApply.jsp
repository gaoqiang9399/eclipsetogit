<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
	String wkfAppId = (String)request.getParameter("wkfAppId");
	String taskId = (String)request.getParameter("taskId");
	String appId = (String)request.getParameter("appId");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var cusNo='${mfBusPact.cusNo}';
			var appId='${mfBusPact.appId}';
			var pactId='${mfBusPact.pactId}';
			var pactNo='${mfBusPact.pactNo}';
			var processId ='${mfBusPact.fincProcessId}'; 
			var ajaxData = '${ajaxData}';
    		ajaxData = JSON.parse(ajaxData);
    		var transferFlag = '${mfBusFincApp.transferFlag}';
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="fincAppInsertForm" theme="simple" name="cms_form" action="${webPath}/mfBusFincApp/insertTransferAccAjax">
						<dhcc:bootstarpTag property="formfincapp0005" mode="query"/>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="转账" action="转账" onclick="MfBusFincAppInsert.ajaxInsertTransferAcc('#fincAppInsertForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincAppInsert.js"></script>
<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
<script type="text/javascript">
	$(function() {
		if(transferFlag=="2"){//转账失败的情况下
			$("[name=bankAccId]").removeAttr("readonly");
			$("[name=collectAccId]").removeAttr("readonly");
		}
		MfBusFincAppInsert.init();
		
	});
</script>	
</html>
