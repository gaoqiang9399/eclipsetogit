<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title> 
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<c:if test="${opNo == regNo}">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						</c:if>
						<form method="post" id="MfFundPlanForm" theme="simple" name="operform" action="${webPath}/mfFundPlan/updateAjax">
							<dhcc:bootstarpTag property="formfundplan0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
				<c:if test="${opNo == regNo}">
	   				<dhcc:thirdButton value="保存" action="保存" onclick="MfFundPlan_Detail.updateAjax('#MfFundPlanForm')"></dhcc:thirdButton>
				</c:if>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/tcph/js/MfFundPlan_Detail.js"></script>
	<script type="text/javascript">
		var opNo = '${opNo}';
		var regNo = '${regNo}';
		var resultMap = ${resultMap};
		$(function(){
			MfFundPlan_Detail.init();
		});
	</script>
</html>
