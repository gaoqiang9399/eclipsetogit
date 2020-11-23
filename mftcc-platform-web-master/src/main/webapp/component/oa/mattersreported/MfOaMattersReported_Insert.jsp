<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
		<script type="text/javascript" src="${webPath}/component/oa/mattersreported/js/MfOaMattersReported_Insert.js"></script>
		<script type="text/javascript" >
			MfOaMattersReported_Insert.path = "${webPath}";
			var mattersReportedId = '${mattersReportedId}';
			var processId='${processId}';
			var resultMap=${resultMap};
			var aloneFlag = true;
			var dataDocParm={
				relNo:mattersReportedId,
				docType:"messageDoc",
				docTypeName:"附件资料",
				docSplitName:"附件资料",
				query:''
			};
			$(function(){
				MfOaMattersReported_Insert.init();
			 });
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">内容通讯消息表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfOaMattersReportedForm" theme="simple" name="operform" action="${webPath}/mfOaMattersReported/insertAjax">
							<dhcc:bootstarpTag property="formmattersreported0002" mode="query"/>
						</form>
					</div>
				</div>
				<!--上传文件-->
				<div class="row clearfix">
					<div class="col-xs-10 col-md-offset-1 column">
						<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交" action="提交" onclick="MfOaMattersReported_Insert.ajaxSave('#MfOaMattersReportedForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfOaMattersReported_Insert.myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
		
	</body>
</html>
