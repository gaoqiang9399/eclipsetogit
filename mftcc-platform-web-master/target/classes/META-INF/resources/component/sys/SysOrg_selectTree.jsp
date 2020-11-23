<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base target="_self">
<title>通过角色选择</title>

<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${webPath}/dwr/util.js'></script>
<script type='text/javascript'
	src='${webPath}/dwr/interface/SysOrgDwr.js'></script>
<script src="${webPath}/component/sys/js/sysOrgSelect.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="${webPath}/component/sys/css/sysOrg.css" />
</head>
<body class="overflowHidden">
<dhcc:markPoint markPointName="SysOrg_treeList"/>
	<script type="text/javascript">
		var ajaxData = '${ajaxData}';
		ajaxData = eval("(" + ajaxData + ")");
		window.onload=function(){
			$(".sys-org-tree").height(window.innerHeight);
		}
	</script>
	<div class="sys-org-body">
		<div class="sys-org-info" style="position: absolute;" >
			<ul id="sysOrgTree" class="ztree"></ul>
		</div>
	</div>
</body>
</html>