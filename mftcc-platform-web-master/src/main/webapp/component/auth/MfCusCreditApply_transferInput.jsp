<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>

<html>
	<head>
		<title>新增转贷授信申请表单</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
				<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
				 	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfCusCreditApply/insertTransferAjax">
							<dhcc:bootstarpTag property="formcreditapply0001" mode="query"/>
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
					<dhcc:thirdButton value="保存" action="保存" typeclass="saveButton" onclick="MfCusCreditApply_transferInput.ajaxInsert('#operform')"></dhcc:thirdButton>
					<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="closeMyDialog();"></dhcc:thirdButton>
				</div>
		</div>
		
</div>


	</body>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_transferInput.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInput.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
	<script type="text/javascript">
		var index = 0;
		var creditAppId='${creditAppId}';
		var creditModel='${creditModel}';//授信模式 1客户授信 2立项授信
		var $form=$("#operform");
		var cusNo='${cusNo}';
		var baseType='${baseType}';
		var cusType='${cusType}';
		var scNo='${scNo}';
		var docParm = "cusNo="+cusNo+"&relNo="+creditAppId+"&scNo="+scNo;//查询文档信息的url的参数
		var mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
		var creditFlag='${creditFlag}';//是否授信标识0未授信1已授信
		var termFlag='${termFlag}';//如果已授信，当前日期是否在授信期限内
		
		$(function(){
			MfCusCreditApply_transferInput.init();
		});
		function closeMyDialog(){
			window.location.href = webPath+"/mfCusCreditApply/getCusCreditApplyListPage";
		}
	</script>
</html>
