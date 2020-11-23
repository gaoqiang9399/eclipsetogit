<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src="${webPath}/component/vouafter/js/MfHandAmtManage_Insert.js"></script>
		<script type="text/javascript" >
            var relNo = '${id}';// 要件业务编号
            var aloneFlag = true;
            var dataDocParm={
                relNo:relNo,
                docType:'handAmtManage',
                docTypeName:"",
                docSplitName:"手续费管理",
                query:""
            };
            $(function(){
                MfHandAmtManage_Insert.init();
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
			<div class="col-md-8 col-md-offset-2 margin_top_20">
				<div class="bootstarpTag">
					<!-- <div class="form-title">工程担保保后跟踪</div> -->
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="MfHandAmtManageForm" theme="simple" name="operform" action="${webPath}/mfHandAmtManage/updateAjax">
						<dhcc:bootstarpTag property="formHandAmtManagebase" mode="query"/>
					</form>
				</div>
				<div class="row clearfix">
					<div class="col-xs-12 column">
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
			<dhcc:thirdButton value="保存" action="保存" onclick="MfHandAmtManage_Insert.ajaxSave('#MfHandAmtManageForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
	</body>
</html>