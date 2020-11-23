<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%@page import="app.component.common.BizPubParm"%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
	<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
	<link id="MfSysKindConfig" rel="stylesheet" type="text/css" href="${webPath}/component/prdct/css/MfSysKindConfig${skinSuffix}.css?v=${cssJsVersion}">
<title>移动端设置</title>
</head>
<body class="overflowHidden">
<div class="container set-div">

		<div class="row clearfix margin_top_5 nav-content">
			<div class="col-md-12 column kind-list">

				<div class="list-item bg-white " onclick="skipToSetting()">
					<div class="block-header">
						<i class="i i-chanpin"></i><span class="kind-name">移动端设置</span>
					</div>
					<div class="block-body" title="移动端设置">移动端设置</div>
				</div>

				<div class="list-item bg-white " onclick="">
					<div class="block-header">
						<i class="i i-chanpin"></i><span class="kind-name">设备管理</span>
					</div>
					<div class="block-body" title="设备管理">设备管理</div>
				</div>

				<div class="list-item bg-white " onclick="skipToRolePmsSetting()">
					<div class="block-header">
						<i class="i i-chanpin"></i><span class="kind-name">角色权限配置</span>
					</div>
					<div class="block-body" title="角色权限配置">角色权限配置</div>
				</div>

                <div class="list-item bg-white " onclick="skipToCusFormConfigSetting()">
                    <div class="block-header">
                        <i class="i i-chanpin"></i><span class="kind-name">移动端客户表单配置</span>
                    </div>
                    <div class="block-body" title="移动端客户表单配置">移动端客户表单配置</div>
                </div>

			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
    $(".nav-content").mCustomScrollbar({
        advanced: {
            updateOnContentResize: true,
        }
    });
    function  skipToSetting() {
        window.location.href = '${appPath}'+"/mfAppLoginConfig/input";
    }
    function  skipToRolePmsSetting() {
        window.location.href = '${appPath}'+"/sysRole/input";
    }
    function  skipToCusFormConfigSetting() {
        window.location.href = '${appPath}'+"/mfCusType/input";
    }

</script>
</html>
