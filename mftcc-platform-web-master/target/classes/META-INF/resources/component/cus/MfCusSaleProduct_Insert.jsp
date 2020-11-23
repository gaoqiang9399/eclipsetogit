<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusSaleProject_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js"></script>
		<script type="text/javascript">
			var cusNo;
            var pageView = '';
            $(function() {
                cusNo=$("input[name=cusNo]").val();
                MfCusSaleProject_Insert.init();
            });
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">销售产品</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusSaleProductForm" theme="simple" name="operform" action="${webPath}/mfCusSaleProduct/insertAjax">
							<dhcc:bootstarpTag property="formcussaleproductBase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
				<%--<dhcc:pmsTag pmsId="cus-saveAndAdd">--%>
					<%--<dhcc:thirdButton value="保存并新增" action="保存并新增" onclick="MfCusSaleProject_Insert.ajaxSaveAndAdd('#MfCusSaleProductForm')"></dhcc:thirdButton>--%>
				<%--</dhcc:pmsTag>--%>
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusSaleProject_Insert.ajaxSave('#MfCusSaleProductForm')"></dhcc:thirdButton>
				<dhcc:pmsTag pmsId="return-page">
					<dhcc:thirdButton value="返回上级页面" action="返回上级页面" typeclass="cancel" onclick="MfCusDyForm.updateCusFormStas();"></dhcc:thirdButton>
				</dhcc:pmsTag>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
