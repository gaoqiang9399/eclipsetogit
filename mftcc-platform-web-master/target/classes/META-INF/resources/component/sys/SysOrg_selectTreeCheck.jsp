<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base target="_self">
<title>选择机构多选</title>
<style type="text/css">
	#sysOrgTree.ztree li span.button{
		opacity: 0.5;
		margin-top: 0px;
	}
	#sysOrgTree {
		height: 355px;
		overflow: auto;
	}
	.button-div{
	    position: absolute;
	    margin-left: 42%;
	    margin-top: 100%;
	}
</style>
</head>
<%-- <body class="overflowHidden">
<dhcc:markPoint markPointName="SysOrg_treeList"/>
	<div class="sys-org-body">
		<div class="sys-org-info" style="position: absolute;" >
			<ul id="sysOrgTree" class="ztree"></ul>
		</div>
	</div>
</body> --%>
<body class="bg-white">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-12 column sys-org-body" id="ztreeDiv">
				<div class="sys-org-info" style="position: absolute;width:100%">
					<ul id="sysOrgTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="row clearfix button-div">
			<div class="col-xs-12 column">
				<div style="text-align: center; margin-top: 5px;">
					<button id="selectOrgConfirm" type="button"
						style="border: none; background: #009db7; color: white; height: 30px; width: 60px; cursor: pointer;"
					>确认</button>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- 滚动轴 -->
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.min.css" />
<!--zTree  -->
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />

<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
<script type='text/javascript' src='${webPath}/dwr/util.js'></script>
<script type='text/javascript'
	src='${webPath}/dwr/interface/SysOrgDwr.js'></script>
<script src="${webPath}/component/sys/js/sysOrgSelectCheck.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="${webPath}/component/sys/css/sysOrg.css" />
<script type="text/javascript">
		var ajaxData = '${ajaxData}';
		ajaxData = eval("(" + ajaxData + ")");
		window.onload=function(){
			//$(".sys-org-tree").height(window.innerHeight);
			bindOrgConfirm();
		}
	</script>
</html>