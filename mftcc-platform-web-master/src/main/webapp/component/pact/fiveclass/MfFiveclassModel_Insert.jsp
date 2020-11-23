<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>五级分类模型详情</title>
		<script type="text/javascript" src='${webPath}/component/pact/js/MfFiveclassModel_Detail.js'></script>
		<script type="text/javascript">
			var path = "${webPath}";
		</script>
	</head>
	<body class=" overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form_title">五级分类模型配置</div>
					 	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post"  name="operform" id="operform" action="${webPath}/mfFiveclassModel/insertAjax">
							<dhcc:bootstarpTag property="formfiveclass0001" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				 <dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdate('#operform')"></dhcc:thirdButton>
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myback();"></dhcc:thirdButton>
			</div>
			</div>
	</body>
</html>