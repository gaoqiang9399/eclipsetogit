<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/viewer.min.css" />
<script type="text/javascript" src="${webPath}/component/frontview/viewer/js/viewer-jquery.min.js"></script>

<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
<script type="text/javascript" >
    var factorWebUrl='${factorWebUrl}';
</script>
<!DOCTYPE html>
<html>
	<head>
	</head>
<body class="bg-white" style="display: none;">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="updateForm" theme="simple" name="operform" enctype="multipart/form-data" action="${webPath}/vwBannerManage/updateAjax">
						<dhcc:bootstarpTag property="formvwbanner0002" mode="query" />
						<div style="display: none;">
							<input type="file" id="imgTest" type="file" onchange="imgChange(event)" accept=".jpg,.bmp,.png" name="upload">
							<input type="text" name="fileName" id="fileName">

						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="myUpdateAjax('#updateForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
<ul id="viewer" style="display: none;">
    <li><img src="" alt="banner图" id="template"></li>
</ul>
</body>
<script type="text/javascript">
$(function(){
	$("#viewer").viewer();
})
</script>
<script src="${webPath}/component/frontview/js/VwBannerManage_Detail.js"></script>
<script type="text/javascript">
var webPath="${webPath}";
$(function(){
	VwBannerManageDetail.init();
})
</script>
</html>
