<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>审批</title>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusClosingManage.js"></script>
	<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js"></script>
    <link rel="stylesheet" href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}"/>
	<script type="text/javascript">
        var id = '${id}';// 要件业务编号
		var cusNo = '${cusNo}';
        var aloneFlag = false;
        /*var docParm = "cusNo="+id+"&relNo="+id;//查询文档信息的url的参数
        var dataDocParm = {
            relNo : id,
            docType : "messageDoc",
            docTypeName : "人工审核要件",
            docSplitName : "",
            query : "query",
        };*/
        $(function() {
            MfCusClosingManage.init();
        });
	</script>
	<script type="text/javascript"></script>
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
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form  method="post" theme="simple" name="operform" action="${webPath}/mfCusClosingManage/doSubmit"  id=MfCusClosingManageForm>
					<dhcc:bootstarpTag property="formcusClosingManageWkf" mode="query"/>
				</form>
				<div class="row clearfix">
					<div class="col-xs-12 column">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>要件上传</span>
							<iframe id="mfBusCompensatoryUploadIframe" frameborder=0 width="100%" src="${webPath}/component/compensatory/mfBusClosingUpload.jsp?query=${query }&closingId=${id}"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
		<dhcc:thirdButton value="保存" action="保存" onclick="MfCusClosingManage.doSubmit('#MfCusClosingManageForm')"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
