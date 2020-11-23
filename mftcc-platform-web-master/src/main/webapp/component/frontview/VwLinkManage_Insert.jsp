<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<script src="${webPath}/component/risk/js/layer.js"></script>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/viewer.min.css" />
<script type="text/javascript" src="${webPath}/component/frontview/viewer/js/viewer-jquery.min.js"></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
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
					<form method="post" id="insertForm" theme="simple" name="operform" action="${webPath}/vwLinkManage/insertAjax" enctype="multipart/form-data">
						<dhcc:bootstarpTag property="formvwlink0002" mode="query" />
						<input style="display: none;" type="file" value="上传图片" id="imgTest" type="file" onchange="imgChange(event)" accept=".jpg,.bmp,.png" name="upload">
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="myInsertAjax('#insertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
<ul id="viewer" style="display: none;">
    <li><img src="" alt="图片" id="template"></li>
</ul>
</body>
<script>
$(function () {
  	$("#viewer").viewer();
});
 </script>
<script src="${webPath}/component/frontview/js/VwLinkManage_Insert.js"></script>
<script type="text/javascript">
$(function(){
	VwLinkManageInsert.init();
})
</script>
</html>
