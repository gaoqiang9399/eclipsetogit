<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
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
						<!-- <div class="form-title">融资方案</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfFinancingOptionsForm" theme="simple" name="operform" action="${webPath }/mfFinancingOptions/updateAjax">
							<dhcc:bootstarpTag property="formfinancingoptionsdetail" mode="query"/>
						</form>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
								<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>	
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfFinancingOptions_Detail.ajaxSave('#MfFinancingOptionsForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	   	<script type="text/javascript" 	src="${webPath}/component/financingoptions/js/MfFinancingOptions_Detail.js"></script>
		<script type="text/javascript">
			var kindNo = ${kindNo};
			var financingOptionsId = '${financingOptionsId}';
			var financingTerm = ${financingTerm};
			var labelMap = ${labelMap};
			var downPaymentRatio = ${downPaymentRatio};
			var kindAdvantageMap = ${kindAdvantageMap};
			var entryCriteriaMap = ${entryCriteriaMap};
			var applicationProcessMap = ${applicationProcessMap};
			var aloneFlag = true;
			var dataDocParm={
					relNo:financingOptionsId,
					docType:'goodsPic',
					docTypeName:"",
					docSplitName:"产品图片",
					query:''
				};
			$(function(){
				MfFinancingOptions_Detail.init();
			});
		</script>
	</body>
</html>