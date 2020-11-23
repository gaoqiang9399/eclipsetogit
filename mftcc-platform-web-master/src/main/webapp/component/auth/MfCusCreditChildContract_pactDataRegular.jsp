<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditChildContract_Insert.js'></script>
		<script  type="text/javascript">
			var currBrNo = '${currBrNo}';
			var cusNo = '${cusNo}';
			var creditAppId = '${creditAppId}';
			var appId = '${creditAppId}';
			var relNo = '${creditAppId}';
			var wkfAppId = '${wkfAppId}';
			//var sysUserList = JSON.parse('${sysUserList}');
			$(function(){
				MfCusCreditChildContract_Insert.initPactDataUpload();				
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">授信子合同信息表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusCreditChildContractForm" theme="simple" name="operform" action="/mfCusCreditChildContract/submitPactDataRegularAjax.action">
							<dhcc:bootstarpTag property="formcreditChildPactAdd" mode="query"/>
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusCreditChildContract_Insert.submitPactDataRegularAjax('#MfCusCreditChildContractForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
