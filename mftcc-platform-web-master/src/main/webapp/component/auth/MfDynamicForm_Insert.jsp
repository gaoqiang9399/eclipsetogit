<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增授信申请动态表单</title>
		
		<script type="text/javascript" src='${webPath}/component/auth/js/MfDynamicForm_Insert.js'></script>
		<script type="text/javascript">
			var path = "${webPath}";
			$(function(){
				mfDynamicFormInsert.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
				 	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfDynamicForm/insertAjax">
							<dhcc:bootstarpTag property="formcredit0002" mode="query"/>
						</form>
						</div>
					</div>
				</div>
			
			<div class="formRowCenter" style="margin: 0px;0px;">
				  <dhcc:thirdButton value="保存" action="保存" onclick="mfDynamicFormInsert.ajaxInsert('#operform')"></dhcc:thirdButton>
				   <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfDynamicFormInsert.close();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
