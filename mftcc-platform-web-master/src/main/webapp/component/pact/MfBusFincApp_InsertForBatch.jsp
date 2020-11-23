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
			var endDate='${mfBusPact.endDate}';
			var cusNoFund='${mfBusPact.cusNoFund}';
			var intstEndDateShow='${intstEndDateShow}';
			var intstBeginDateShow='${intstBeginDateShow}';
			var pactBeginDate='${pactBeginDate}';
			var pactEndDate='${pactEndDate}';
			var wkfAppId='${mfBusPact.wkfAppId}';
			var fincMainId='${fincMainId}';
			var isNewBank = "0";
			var taskId="";
			var ajaxData = '${ajaxData}';
    		ajaxData = JSON.parse(ajaxData);
    		var processId ='${mfBusPact.fincProcessId}'; 
            var kindNo = "${kindNo}";
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="fincAppInsertForm" theme="simple" name="cms_form" action="${webPath}/mfBusFincApp/insertForBatchAjax">
						<dhcc:bootstarpTag property="formfincapp0006" mode="query"/>
					</form>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusFincAppInsert.ajaxInsertForBatch('#fincAppInsertForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincAppInsert.js"></script>
<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
<script type="text/javascript">

	$(function() {
		MfBusFincAppInsert.init();
	});
</script>	
</html>
