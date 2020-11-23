<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
String appId = (String)request.getParameter("appId");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/collateral/js/EvalInfo_inputForFlow.js"></script>
	</head>
	<script type="text/javascript">
		var appId = '${appId}';
		var busPleId = '${busPleId}';
		$(function() {
			EvalInfo_inputForFlow.init();
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
			            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="evalInfoInsert" theme="simple" name="operform" action="${webPath}/evalInfo/insertBussAjax">
								<dhcc:bootstarpTag property="formdlevalinfo0002" mode="query"/>
						</form>	
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="EvalInfo_inputForFlow.insertEvalInfoForBuss('#evalInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
