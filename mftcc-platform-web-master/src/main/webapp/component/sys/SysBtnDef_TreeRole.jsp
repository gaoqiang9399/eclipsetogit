<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script src="${webPath}UIplug/zTree/jquery.ztree.all-3.5.min.js"
	type="text/javascript"></script>
<script src="${webPath}component/sys/js/sysBtnDefRole.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="${webPath}UIplug/zTree/metroStyle/metroStyle.css" />
<link rel="stylesheet"
	href="${webPath}component/sys/css/sysBtnDefRole.css" />
<script type="text/javascript">
	/* $(function(){
	 }); */
</script>
</head>
<body>
	<div class="sys-btn-body">
		<div class="sys-top">
			<button type="button" id="refurbish">刷新按钮缓存</button>
		</div>
		<div class="sys-btn-lv lv1">
			<div class="sys-btn-head">
				<h3>组件</h3>
			</div>
			<div class="sys-btn-tree">
				<ul id="treeLv1" class="ztree"></ul>
			</div>
		</div>
		<div class="sys-btn-lv lv2">
			<div class="sys-btn-head">
				<h3>功能</h3>
			</div>
			<div class="sys-btn-tree">
				<ul id="treeLv2" class="ztree"></ul>
			</div>
		</div>
		<div class="sys-btn-lv lv3">
			<div class="sys-btn-head">
				<h3>按钮</h3>
				<input type="checkbox" id="selectAllBtn" class="selectAllBtn"/>
			</div>
			<div class="sys-btn-tree">
				<ul id="treeLv3" class="ztree"></ul>
			</div>
			<div class="sys-btn-button">
				<input type="hidden" id="funUpId"/>
				<input type="hidden" id="btnUpId"/>
				<input type="hidden" id="funNo"/>
				<input type="hidden" id="roleNo" value="${roleNo"/>}
				<button id="saveRoleBtn" type="button" >保存</button>
			</div>
		</div>
	</div>
</body>
</html>
