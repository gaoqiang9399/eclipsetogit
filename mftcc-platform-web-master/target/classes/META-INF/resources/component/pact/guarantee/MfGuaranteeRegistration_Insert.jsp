<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/umeditor-dev/themes/default/_css/umeditor.css"/>
		<script type="text/javascript" >
            var pactId = "${pactId}";
            var cusNo = "${cusNo}";
            var id = "${id}";
            var flag = "${flag}";
            var queryFile = "${queryFile}";
            var aloneFlag = true;
            var dataDocParm={
                relNo:id,
                docType:'guarantee',
                docTypeName:"",
                docSplitName:"保函要件",
                query:queryFile
            };
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
						<!-- <div class="form-title">保函登记</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfGuaranteeRegistrationForm" theme="simple" name="operform" action="${webPath}/mfGuaranteeRegistration/insertAjax">
							<dhcc:bootstarpTag property="formguaranteeAdd" mode="query"/>
						</form>
					</div>
					<c:if test="${flag=='update'or flag == 'detail'or flag == 'insert'or flag == 'edit'}">
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>
					</c:if>
				</div>
	   		</div>
			<div class="formRowCenter">
				<c:if test="${flag=='insert'}">
					<dhcc:thirdButton value="暂存" action="暂存" onclick="MfGuaranteeRegistration_Insert.ajaxSave('#MfGuaranteeRegistrationForm','1')"></dhcc:thirdButton>
				</c:if>
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
				<c:if test="${flag=='insert' or flag == 'edit'}">
					<dhcc:thirdButton value="保存" action="保存" onclick="MfGuaranteeRegistration_Insert.ajaxSave('#MfGuaranteeRegistrationForm','0')"></dhcc:thirdButton>
				</c:if>
				<c:if test="${flag == 'update'}">
					<dhcc:thirdButton value="确认收函" action="确认收函" onclick="MfGuaranteeRegistration_Insert.ajaxSave('#MfGuaranteeRegistrationForm','0')"></dhcc:thirdButton>
				</c:if>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
		</div>
	</body>
<script type="text/javascript" src="${webPath}/component/pact/guarantee/js/MfGuaranteeRegistration_Insert.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/umeditor-dev/umeditor.config.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/umeditor-dev/editor_api.js"></script>
<script type="text/javascript">
    $(function(){
        MfGuaranteeRegistration_Insert.init();
    });
	$(document.body).height($(window).height());
</script>
</html>
