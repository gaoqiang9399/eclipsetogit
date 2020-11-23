<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
		
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfOaBecomeQualifiedForm" theme="simple" name="operform" action="${webPath}/mfOaBecomeQualified/insertAjax">
							<dhcc:bootstarpTag property="formbecomequalified0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交申请" action="提交申请" onclick="MfOaBecomeQualified_Insert.ajaxSave('#MfOaBecomeQualifiedForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="myclose cancel" onclick="MfOaBecomeQualified_Insert.myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
<script type="text/javascript" 	src="${webPath}/component/oa/becomequalified/js/MfOaBecomeQualified_Insert.js"></script>
<script type="text/javascript">
var processId='${processId}';
		$(function(){
			MfOaBecomeQualified_Insert.init();
		});
</script>
	</body>
</html>
