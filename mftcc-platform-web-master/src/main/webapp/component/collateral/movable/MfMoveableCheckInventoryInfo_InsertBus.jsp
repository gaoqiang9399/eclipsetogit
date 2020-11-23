<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/collateral/movable/js/MfMoveableCheckInventoryInfoBus.js"></script>
	<script type="text/javascript">
		var appId='${appId}';
		$(function(){
			MfMoveableCheckInventoryInfoBus.init();
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<c:if test='${dataMap.vouType=="1"}'>
           			 		<div class="text-center">主担保方式为信用贷款，不需要进行核库</div>
	           			</c:if>
	           			<c:if  test='${dataMap.vouType!="1"}'>
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form  method="post" id="MfMoveableCheckInventoryInfoForm" theme="simple" name="operform" action="${webPath}/mfMoveableCheckInventoryInfo/insertBusAjax">
								<dhcc:bootstarpTag property="formcheckinv0002" mode="query"/>
							</form>
						</c:if>
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<c:if test='${dataMap.vouType!="1"}'>
	   				<dhcc:thirdButton value="保存" action="保存" onclick="MfMoveableCheckInventoryInfoBus.ajaxCheckInventorySave('#MfMoveableCheckInventoryInfoForm')"></dhcc:thirdButton>
	   			</c:if>
	   			<dhcc:thirdButton value="跳过" action="跳过" typeclass="cancel" onclick="MfMoveableCheckInventoryInfoBus.submitBussProcessAjax()"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	
</html>
