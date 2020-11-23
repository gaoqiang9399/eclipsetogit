<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>新增风险拦截配置</title>
		<script type="text/javascript" >
		var pageStr = '${pageStr}';
		</script>
		<script type="text/javascript" src="${webPath}/component/risk/js/RiskPreventSceGen_Insert.js"></script>
		<script type="text/javascript" >
		$(function(){
			
			RiskPreventSceGenInsert.init();		
		});
</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="riskSceInsertForm" theme="simple" name="operform" action="${webPath}/riskPreventSceGen/insertAjax">
						<dhcc:bootstarpTag property="formrisk0020" mode="query"/>
					</form>
				</div>
			</div>	
		</div>
		<div class="formRowCenter">
   			<dhcc:thirdButton value="保存" action="保存" onclick="RiskPreventSceGenInsert.ajaxInsert('#riskSceInsertForm')"></dhcc:thirdButton>
   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose()"></dhcc:thirdButton>
   		</div>
   	</div>
</body>
</html>