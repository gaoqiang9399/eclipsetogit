<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusKindTableListPage.js?v=${cssJsVersion}'> </script>
<script type="text/javascript">
	var cusTableList = ${ajaxData};
	var cusNo = '${cusNo}';
	var appId = '${appId}';
	var busSubmitCnt = '1';
	var dataValidate = true;
	var query = '${query}';
	var pageView = "cusView";//客户视角
	var effectFlag = "0";// 0-客户信息未生效，直接返回，客户信息可以直接修改, 2-客户状态为销户，不允许修改数据
    var formEditFlag = '${query}'; //表单单子段可编辑的标志
</script>
</head>

<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix">
					<div class="col-md-10 col-md-offset-1 column margin_top_20">
						<div class="block-add" style="display: none;"></div>
					</div>
				</div>
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="提交" action="提交" typeclass ="insertAjax" onclick="MfCusKindTableListPage.submitAjax();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function(){
		MfCusDyForm.init(cusTableList);
		MfCusKindTableListPage.init();
	})
</script>
</body>
</html>