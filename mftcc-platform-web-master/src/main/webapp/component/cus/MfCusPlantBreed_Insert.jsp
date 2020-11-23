<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusPlantBreed_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var  cusNo;
            var pageView = '';
            $(function() {
                cusNo=$("input[name=cusNo]").val();
                MfCusPlantBreed_Insert.init();
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
						<!-- <div class="form-title">种植养殖信息</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusPlantBreedForm" theme="simple" name="operform" action="${webPath}/mfCusPlantBreed/insertAjax">
							<dhcc:bootstarpTag property="formcusplantbreedBase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
				<dhcc:pmsTag pmsId="cus-saveAndAdd">
					<dhcc:thirdButton value="保存并新增" action="保存并新增" onclick="MfCusPlantBreed_Insert.ajaxSaveAndAdd('#MfCusPlantBreedForm')"></dhcc:thirdButton>
				</dhcc:pmsTag>
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusPlantBreed_Insert.ajaxSave('#MfCusPlantBreedForm')"></dhcc:thirdButton>
				<dhcc:pmsTag pmsId="return-page">
					<dhcc:thirdButton value="返回上级页面" action="返回上级页面" typeclass="cancel" onclick="MfCusDyForm.updateCusFormStas();"></dhcc:thirdButton>
				</dhcc:pmsTag>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
