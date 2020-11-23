<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="family_form" theme="simple" name="operform" action="${webPath}/mfOaArchivesFamily/insertAjax">
							<dhcc:bootstarpTag property="formarchivefamily0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass="family_insertAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
	<script type="text/javascript" 	src="${webPath}/component/oa/archive/js/MfOaDrchivesFamily_insert.js"></script>
	<script type="text/javascript">
		$(function(){
			OaArchivesFamily.init();
		})
		$(document.body).height($(window).height());
	</script>
</html>