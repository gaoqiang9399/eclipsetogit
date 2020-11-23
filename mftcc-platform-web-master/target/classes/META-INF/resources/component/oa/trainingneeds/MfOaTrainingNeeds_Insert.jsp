<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<script type="text/javascript"
	src="${webPath}/component/include/WkfApprove.js"></script>
	<script type="text/javascript">
		var processId = '${processId}';
 		var trainingNeedsId = '${trainingNeedsId}';
		var aloneFlag = true;
		var resultMap=${resultMap};
		 /* var dataDocParm = {
			relNo : trainingNeedsId,
			docType : "messageDoc",
			docTypeName : "附件资料",
			docSplitName : "附件资料",
			query : ''
		};  */
		$(function() {
			MfOaTrainingNeeds_Insert.init();
		});
	</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="MfOaTrainingNeedsForm" theme="simple"
						name="operform"
						action="<%=webPath %>/mfOaTrainingNeeds/insertAjax">
						<dhcc:bootstarpTag property="formtrainingneeds0002" mode="query" />
					</form>
				</div>
			<%-- 	<!--上传文件-->
				<div class="row clearfix">
					<div class="col-xs-12 col-md-offset-0 column">
						<%@include
							file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div> --%>
			</div>
		</div>
		<div class="formRowCenter">
			<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
			<dhcc:thirdButton value="提交申请" action="提交申请"
				onclick="MfOaTrainingNeeds_Insert.ajaxSave('#MfOaTrainingNeedsForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel"
				onclick="MfOaTrainingNeeds_Insert.myclose();"></dhcc:thirdButton>
		</div>
	</div>
	<script type="text/javascript"
		src="${webPath}/component/oa/trainingneeds/js/MfOaTrainingNeeds_Insert.js"></script>
	
</body>
</html>
