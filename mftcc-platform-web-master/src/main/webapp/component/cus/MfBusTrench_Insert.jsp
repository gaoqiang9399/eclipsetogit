<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	</head>
<body class="bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" theme="simple" name="operform" action="${webPath}/mfBusTrench/insertAjax" id="insertForm">
						<dhcc:bootstarpTag property="formtrench0002" mode="query"/>
					</form>	
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="myInsertAjax('#insertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script src="${webPath}/component/cus/js/MfBusTrench.js"></script>
<script type="text/javascript">
	$(function(){
		MfBusTrench.insertInit();
	});
	$("form").delegate("[name=cusType]","change",function(){
		window.location.href=webPath+"/mfBusTrench/input?typeNo="+$(this).val();
	})
</script>
</html>
