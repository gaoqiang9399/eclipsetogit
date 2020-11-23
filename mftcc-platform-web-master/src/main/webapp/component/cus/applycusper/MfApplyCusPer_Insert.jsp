<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">客户权限申请</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfAppluCusPerForm" theme="simple" name="operform" action="${webPath}/mfApplyCusPer/insertAjax">
							<dhcc:bootstarpTag property="formapplycusper" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交" action="提交" onclick="mfApplyCusPer.insertSave('#MfAppluCusPerForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfApplyCusPer.myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/applycusper/js/MfApplyCusPer.js"></script>
	</body>
</html>
