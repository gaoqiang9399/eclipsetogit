<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
	String opNo = (String)request.getSession().getAttribute("opNo");
	String opName = (String)request.getSession().getAttribute("opName");
%>
<!DOCTYPE html>
<html>
<script type="text/javascript" src='${webPath}/component/sys/js/PwdForceUpdate.js'></script>
	<head>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="sysModifyPwd" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formsysModifyPwd" mode="query"/>
						</form>

					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="modifyPwd.ajaxUpdate('#sysModifyPwd');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="modifyPwd.sysQuit();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
	<script type="text/javascript">
	var opNo="<%=opNo%>";
	var opName="<%=opName%>";
	// 接收传参等
	$(function() {
		modifyPwd.init();
	});
</script>
</html>
