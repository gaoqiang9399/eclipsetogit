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
					<div class="bootstarpTag fourColumn">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="education_form" theme="simple" name="operform" action="${webPath}/mfOaArchivesEducation/insertAjax">
							<dhcc:bootstarpTag property="formarchiveeducation0001" mode="query" />
						</form>
					</div>
				</div>
				</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass="education_insertAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
	<script type="text/javascript" 	src="${webPath}/component/oa/archive/js/MfOaDrchivesEducation_insert.js"></script>
	<script type="text/javascript">
		$(function(){
			OaArchivesEducation.init();
		})
		$(document.body).height($(window).height());
	</script>
</html>