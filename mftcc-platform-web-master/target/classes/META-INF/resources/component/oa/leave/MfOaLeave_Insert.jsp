<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/WkfApprove.js"></script>
		<script type="text/javascript" src="${webPath}/component/oa/leave/js/MfOaLeave.js"></script>
		<script type="text/javascript">
			OaLeave.path = "${webPath}";
			var leaveNo = "${leaveNo}";
			var query = '${query}';
			var aloneFlag = true;
			var dataDocParm = {
				relNo:leaveNo,
				docType:"messageDoc",
				docTypeName:"附件资料",
				docSplitName:"附件资料",
				query:''
			};
			$(function() {
				OaLeave.init();
			});
		</script>
	</head>
<body class="overflowHidden bg-white">
		<div class="container form-container">
		<div class="scroll-content ">
			<div class="col-md-10 col-md-offset-1 margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="OaLeaveInsert" theme="simple" name="operform" action="${webPath}/mfOaLeave/insertAjax">
						<dhcc:bootstarpTag property="formoaleave0001" mode="query"/>
					</form>
				</div>
			</div>
			<!--上传文件-->
			<div class="row clearfix">
				<div class="col-xs-10 col-md-offset-1 column">
					<%@include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
				</div>
			</div>
   		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="提交" typeclass="insertAjax"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"  onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
