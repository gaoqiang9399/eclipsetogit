<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusPersonLiabilities.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
	<script type="text/javascript">
	var cusNo;
    var pageView = '';
	$(function() {
        cusNo=$("input[name=cusNo]").val();
		MfCusPersonLiabilities.init();
	});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfCusPersonLiabilitiesForm" theme="simple" name="operform" action="${webPath}/mfCusPersonLiabilities/insertAjax">
							<dhcc:bootstarpTag property="formcusliabilitiesBase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#MfCusPersonLiabilitiesForm')"></dhcc:thirdButton>
				<dhcc:pmsTag pmsId="return-page">
					<dhcc:thirdButton value="返回上级页面" action="返回上级页面" typeclass="cancel" onclick="MfCusDyForm.updateCusFormStas();"></dhcc:thirdButton>
				</dhcc:pmsTag>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
