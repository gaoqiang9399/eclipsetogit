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
	src='${webPath}/component/pact/js/putOutConfirmForm.js'></script>
	<script type="text/javascript">
		var wkfAppId = "<%=wkfAppId%>";
		var taskId = "<%=taskId%>";
		var appId='<%=appId%>';
		var cusNo='${cusNo}';
		var scNo ='${scNo}';//客户要件场景
		var docParm = "cusNo="+cusNo+"&relNo="+appId+"&scNo="+scNo;//查询文档信息的url的参数
		var isNewBank = "0";
		$(function(data){
			fincappInfo.init();
		});
		
		function selectBankAcc(){
			var cusNo = $("input[name='cusNo']").val();
			selectBankAccDialog(getBankAccInfoArtDialog,cusNo);
		}
		function getBankAccInfoArtDialog(accountInfo){
			var accountNo = accountInfo.accountNo;
			var space = " ";
			var formatAccountNo = accountNo.substring(0,4)+space+accountNo.substring(4,8)+space+accountNo.substring(8,12)+space+accountNo.substring(12,16)+space+accountNo.substring(16);
			$("input[name='incomAccount']").val(formatAccountNo);
			$("input[name='incomBank']").val(accountInfo.bank);
			$("input[name='incomName']").val(accountInfo.accountName);
			$("input[name='incomAccountName']").val(accountInfo.accountName);
		};
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
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="fincAppInsertForm" theme="simple"
						name="operform" action="${webPath}/mfLoanApply/insertPutoutAjax">
						<dhcc:bootstarpTag property="formfincapp0006" mode="query" />
					</form>
				</div>
			</div>
			<div class="row clearfix">
					<div class="col-md-8 col-md-offset-2 column margin_top_20" >
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
			</div>
		</div>
		<input type="hidden" id="type" value="1"></input>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存"
				onclick="insertZHfincappAjax('#fincAppInsertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
				onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
