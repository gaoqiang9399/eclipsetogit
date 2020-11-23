<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<script src="${webPath}/component/risk/js/layer.js"></script>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/viewer.min.css" />
<script type="text/javascript" src="${webPath}/component/frontview/viewer/js/viewer-jquery.min.js"></script>
<!DOCTYPE html>
<html>
	<head>
	</head>
<body class="bg-white" style="background-color: #f4f4f4;display: none;">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" style="background-color: white;">
				<div class="bootstarpTag">
					<div class="form-title">网站基本信息设置</div>
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="updateForm" theme="simple" name="operform" action="${webPath}/vwSetManage/updateAjax">
						<dhcc:bootstarpTag property="formfrontview0001" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<input style="display: none;" type="file" value="上传图片" id="wxFile" type="file" onchange="wxImgChange(event)" accept=".jpg,.bmp,.png">
		<input style="display: none;" type="file" value="上传图片" id="qqFile" type="file" onchange="qqImgChange(event)" accept=".jpg,.bmp,.png">
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="myUpdateAjax('#updateForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
<ul id="viewer" style="display: none;">
    <li><img src="" alt="banner图" id="template"></li>
</ul>
</body>
<script src="${webPath}/component/frontview/js/VwSetManage_Detail.js"></script>
<script>
$(function () {
  	$("#viewer").viewer();
});
 </script>
<script type="text/javascript">
$(function(){
	VwSetManageDetail.init();
})
</script>
</html>