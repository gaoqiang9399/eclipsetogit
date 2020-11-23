<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
	</head>
	<script type="text/javascript" src="${webPath}/component/app/js/inputTwoReportForm.js"></script>
	<script type="text/javascript">	
		var cusNo='${cusNo}';
		var appId = '${appId}';
		var fincUse = $.parseJSON('${jsonStr}');
		var kindNo = '${mfBusApply.kindNo}';
		$(function() {
			busApplyFormInfo.init();
		});
		function updateForApplyForm(obj){
			busApplyFormInfo.updateApplyForm(obj);
		}	
		function clossWindow(){
			busApplyFormInfo.cancle();
		}
		function changeCapital(){
			busApplyFormInfo.changeCapital();
		}
		function checkIfTrue(){
			busApplyFormInfo.checkIfTrue();
		}
	</script>

<body class="overflowHidden bg-white">
	<div class="container form-container" id="normal">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="updateForApplyForm" method="post" theme="simple" name="operform" action="${webPath}/mfLoanApply/updateApplyInfo">
					    <dhcc:bootstarpTag property="formapply0001" mode="query" />  
					</form>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="updateForApplyForm('#updateForApplyForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="clossWindow();"></dhcc:thirdButton>
		</div>
<!-- 		<div style="display: none;" id="fincUse-div"></div> -->
	</div>
</body>
</html>