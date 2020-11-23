<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript">	
		var appId = '${appId}';
		var pactId = '${mfBusApply.pactId}';
		$(function() {
			FormFactor.init();
		});	
	</script>
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form  method="post" id="reservationReg" theme="simple" name="operform" action="${webPath}/mfBusCollateralRel/reservationRegAjax">
							<dhcc:bootstarpTag property="formcommon" mode="query" />
						</form>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass ="insertAjax" onclick="FormFactor.insertAjax('#reservationReg');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
