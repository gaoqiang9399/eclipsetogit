<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/pledge/js/MfHighGuaranteeContract.js"></script>
		<script type="text/javascript" >
			$(function(){
                MfHighGuaranteeContract.selectCustomerList();
			});
		</script>
		<style type="text/css">
			.subBtn{
				color: #32b5cb;
				margin-left:62px;
				margin-top: -31px;
				background-color:#fff
			}
		</style>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">最高额担保合同表</div> -->
						<div class="form-title">登记最高额担保合同</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="mfHighGuaranteeContractForm" theme="simple" name="mfHighGuaranteeContractForm" action="${webPath}/mfHighGuaranteeContract/insertAjax">
							<dhcc:bootstarpTag property="formhighGrtContractInput" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfHighGuaranteeContract.insertSave('#mfHighGuaranteeContractForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfHighGuaranteeContract.myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
