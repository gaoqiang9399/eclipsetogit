<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="MfMoveableAccountCheckInfoForm" theme="simple" name="operform" action="${webPath}/mfMoveableAccountCheckInfo/insertAjax">
							<dhcc:bootstarpTag property="formaccountcheck0001" mode="query"/>
						</form>
					</div>
					<!--上传文件-->
					<div class="row clearfix">
						<!-- <div class="title"><h5>对账单资料</h5></div> -->
						<div class="col-xs-12 column" style=" width: 109%;margin-left: -4%;margin-top: 0%;">
							<%@include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfMoveableAccountCheckInfo.ajaxAccountCheckSave('#MfMoveableAccountCheckInfoForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfMoveableAccountCheckInfo.closeDialog('close');"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/movable/js/MfMoveableAccountCheckInfo.js"></script>
	<script type="text/javascript">
		var accountCheckId=$("input[name=accountCheckId]").val();
		var aloneFlag = true;
		var dataDocParm={
			relNo:accountCheckId,
			docType:"accountCheckDoc",
			docTypeName:"",
			docSplitName:"对账单资料",
			query:''
		};
		$(function(){
			MfMoveableAccountCheckInfo.init();
		});
	</script>
</html>
