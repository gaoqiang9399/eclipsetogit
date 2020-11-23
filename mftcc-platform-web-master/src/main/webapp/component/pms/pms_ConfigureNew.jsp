<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>角色权限配置页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="角色权限配置页面">
<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		
<%--滚动条js 和鼠标滚动事件js--%>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css">
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<%--滚动样式--%>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css"/>
 <link rel="stylesheet" href="${webPath}/themes/factor/css/BS-factor.css?v=${cssJsVersion}" />
<link rel="stylesheet" href="${webPath}/component/pms/css/pms_configure.css">
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css">

<script type="text/javascript" src="${webPath}/component/pms/js/pmsBiz.js"></script>
<link rel="stylesheet" href="${webPath}/component/pms/zTree_v3/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.css" />
<link rel="stylesheet" href="${webPath}/component/pms/css/pmsBizOpt.css" />
<link rel="stylesheet" href="${webPath}/themes/factor/css/factor.css?v=${cssJsVersion}" />
<script type="text/javascript" src='${webPath}/component/include/closePopUpBox.js'> </script>
 <!--加载动画js  -->
<script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>

<script type="text/javascript">
	var dataMap;
	var roleName = '${sysRole.roleName}';
	var roleNo = '${sysRole.roleNo}';
	var roleType = '${sysRole.roleType}';
	var roleLev = '${sysRole.roleLev}';
	var opNoType = '${opNoType}';
</script>
<script type="text/javascript" src="${webPath}/component/pms/js/configureNew.js"></script>
</head>
	<body class="overflowHidden">
		<div class="container" style="height: 100%;">
			<div class="row clearfix pms_form_div">
				<form id="saveSysRoleForm" action="">
					<label for="filterName"><span style="font-family: Verdana, Arial, Helvetica, AppleGothic, sans-serif,FontAwesome;font-size: 14px;">角色名称</span><font color="red">*</font>：</label>
					<input id = "roleName" name = "roleName" type="text" style="color: rgba(0, 0, 0, 0.6);height: 24px;line-height: 22px;width: 190px;border: 1px solid #000000;"></input>
					<!-- <div id = "roleNameShow" style="display:none;"></div> -->
					<input id = "roleNo" name = "roleNo" type="hidden"></input>
					<input id = "roleType" name = "roleType" type="hidden"></input>
					<input id = "roleLev" name = "roleLev" type="hidden"></input>
					<input id = "flag" name = "flag" type="hidden"></input>
				</form>
			</div>
			<div class="row clearfix pms_configure_body">
				<div class="pms_configure_div col-md-6 column">
					<div class="pms_configure_sub">
						<div class="pms_configure_title">
							<span>入口权限配置</span>
						</div>
						<div class="pms_configure_tree" style="display: none;">
							<ul id="treeEntr" class="ztree"></ul>
						</div>
					</div>
				</div>
				<div class="pms_configure_div col-md-6 column">
					<div class="pms_configure_sub">
						<div class="pms_configure_title">
							<span>功能权限配置</span>
						</div>
						<div class="pms_configure_tree" style="display: none;">
							<!-- <ul id="treeView" class="ztree"></ul> -->
							<ul id="pmsBizTree" class="ztree"></ul>
						</div>
					</div>
				</div>
				<div class="formRowCenter" style="margin-bottom: 0%;">
	   				<dhcc:thirdButton value="保存" action="保存" typeclass="ajaxInsert" onclick="saveSysRolePms('add')"></dhcc:thirdButton>
	   				<dhcc:thirdButton value="保存" action="保存" typeclass="ajaxEdit" onclick="saveSysRolePms('edit')"></dhcc:thirdButton>
	   			 	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick=" myclose_click();"></dhcc:thirdButton>
	   			</div>
			</div>
		</div>
	</body>
</html>
