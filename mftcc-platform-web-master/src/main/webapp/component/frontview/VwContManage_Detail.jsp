<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<script type="text/javascript" >
    var factorWebUrl='${factorWebUrl}';
    var folder='${folder}';
</script>
<script src="${webPath}/component/risk/js/layer.js"></script>
<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/umeditor-dev/themes/default/_css/umeditor.css"/>
<script type="text/javascript" src='${webPath}/UIplug/umeditor-dev/vw.umeditor.config.js'></script>
<script type="text/javascript" src='${webPath}/UIplug/umeditor-dev/editor_api.js'></script>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/viewer.min.css" />
<script type="text/javascript" src="${webPath}/component/frontview/viewer/js/viewer-jquery.min.js"></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
<!DOCTYPE html>
<html>
	<head>
	</head>
<body class="bg-white" style="display: none;">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" style="background-color: white;">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="updateForm" theme="simple" name="operform" action="${webPath}/vwContManage/updateAjax" enctype="multipart/form-data">
                        <dhcc:bootstarpTag property="formvwcont0001" mode="query" />
                        <script id="content"  type="text/plain" style="width:100%;height:390px;" name="content">${vwContManage.content}</script>
                        <input style="display: none;" value="上传图片" id="file" type="file" onchange="imgChange(event)" accept=".jpg,.bmp,.png" name="upload"/>
                        <input style="display: none;" value="上传图片" id="file1" type="file" onchange="imgChange1(event)" accept=".jpg,.bmp,.png" name="upload1"/>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="myUpdateAjax('#updateForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
<ul id="viewer" style="display: none;">
    <li><img src="" alt="" id="template"></li>
</ul>
</body>
<script src="${webPath}/component/frontview/js/VwContManage_Detail.js"></script>
<script type="text/javascript">
$(function(){
    $("#viewer").viewer();
	VwContManageDetail.init();
})
</script>
</html>