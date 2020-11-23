<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfRiskLevelManageForm" theme="simple" name="operform" action="${webPath }/mfRiskLevelManage/insertAjax.action">
							<dhcc:bootstarpTag property="formrisklevelmanagebase" mode="query"/>
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
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfRiskLevelManage_Insert.ajaxSave('#MfRiskLevelManageForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
		<script type="text/javascript"
		src="${webPath}/component/risk/risklevelmanage/js/MfRiskLevelManage_Insert.js"></script>
	<script type="text/javascript">
		var webPath = "${webPath}";
		var managePlan =  ${managePlan};
		var aloneFlag = true;
		var id = '${id}';
		var dataDocParm={
			relNo:id,
			docType:"messageDoc",
			docTypeName:"附件资料",
			docSplitName:"附件资料",
			query:''
		};
		$(function() {
			MfRiskLevelManage_Insert.init();
		});
	</script>
	</body>
</html>
