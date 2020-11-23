<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<script type="text/javascript" src="${webPath}/component/hzey/js/MfBusIcloudManage.js"></script>
		<title>修改表单</title>
		<script type="text/javascript">
			function ajaxIcloudSave(formid){
				MfbusIcloudManage.ajaxIcloudSave(formid);
			}
			function getIcloudPwd(){
				MfbusIcloudManage.getIcloudPwdAjax();
			}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">icloud账户管理表</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusIcloudManageForm" theme="simple" name="operform" action="${webPath}/mfBusIcloudManage/updateAjaxByOne">
							<dhcc:bootstarpTag property="formhzeyupdate0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxIcloudSave('#MfBusIcloudManageForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
