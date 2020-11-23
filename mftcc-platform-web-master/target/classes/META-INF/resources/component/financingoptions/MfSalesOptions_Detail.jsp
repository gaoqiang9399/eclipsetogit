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
						<!-- <div class="form-title">销售方案</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfSalesOptionsForm" theme="simple" name="operform" action="${webPath }/mfSalesOptions/updateAjax">
							<dhcc:bootstarpTag property="formsalesoptionsdetail" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfSalesOptions_Detail.ajaxSave('#MfSalesOptionsForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	   	<script type="text/javascript" 	src="${webPath}/component/financingoptions/js/MfSalesOptions_Detail.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript">
			var mfCarBrandMap = ${mfCarBrandMap};
			var kindNo = ${kindNo};
			var mfCarSeriesMap = ${mfCarSeriesMap};
			var mfCarModelMap = ${mfCarModelMap};
			$(function(){
				MfSalesOptions_Detail.init();
			});
		</script>
	</body>
</html>