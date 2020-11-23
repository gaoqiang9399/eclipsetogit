<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
<script type="text/javascript"
	src='${webPath}/component/pact/js/confirmPutoutForm.js'></script>
	<script type="text/javascript">
		var wkfAppId = "<%=wkfAppId%>";
		var taskId = "<%=taskId%>";
		var appId = "<%=appId%>";
		var isNewBank = "0";
		$(function(data){
			fincappInfo.init();
		});
		
		function selectBankAcc(){
			var cusNo = $("input[name='cusNo']").val();
			selectBankAccDialog(getBankAccInfoArtDialog,cusNo);
		}
		function insertZHfincappAjax(obj){
			fincappInfo.insertZHfincapp(obj);
		}
	</script>

</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
<!-- 					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div> -->
					<form method="post" id="fincAppInsertForm" theme="simple"
						name="operform" action="${webPath}/mfLoanApply/updateProcessAjax">
						<dhcc:bootstarpTag property="formfincapp0004" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<input type="hidden" id="type" value="1"></input>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="确认"
				onclick="insertZHfincappAjax('#fincAppInsertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
				onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
