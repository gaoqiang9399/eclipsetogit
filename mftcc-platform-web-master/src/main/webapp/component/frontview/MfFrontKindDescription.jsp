<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<script src="${webPath}/component/risk/js/layer.js"></script>
		<link type="text/css" rel="stylesheet" href="UIplug/umeditor-dev/themes/default/_css/umeditor.css"/>
<script type="text/javascript" src='UIplug/umeditor-dev/umeditor.config.js'></script>
<script type="text/javascript" src='UIplug/umeditor-dev/editor_api.js'></script>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/viewer.min.css" />
<script type="text/javascript" src="${webPath}/component/frontview/viewer/js/viewer-jquery.min.js"></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
<!DOCTYPE html>
<html>
	<head>
	</head>
<body class="bg-white" >
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" style="background-color: white;">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="updateForm" theme="simple" name="operform" action="${webPath}/mfFrontKind/updateContentAjax">
						<dhcc:bootstarpTag property="formfrontkinddescription0001" mode="query" />
						<script id="content"  type="text/plain" style="width:100%;height:390px;" name="content"></script>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="updateContentAjax('#updateForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="关闭" typeclass="cancel" onclick="MfFrontKindDescription.myclose();"></dhcc:thirdButton>
		</div>
		<form  style="display: none;" enctype="multipart/form-data" id="fileForm" action="${webPath}/vwBannerManage/upload" method="post">
			<input type="file" id="imgTest" type="file" onchange="imgChange(event)" accept=".jpg,.bmp,.png" name="upload">
			<input type="text" name="fileName" id="fileName">
		</form>
	</div>
	<ul id="viewer" style="display: none;">
    	<li><img src="" alt="产品图标" id="template"></li>
	</ul>
</body>
<script>
$(function () {
  	$("#viewer").viewer();
});
 </script>
<script src="${webPath}/component/frontview/js/MfFrontKindDescription.js"></script>
<script type="text/javascript">
$(function(){
	MfFrontKindDescription.init();
});
</script>
</html>