<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
		<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js" ></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/jAlert.js" ></script>
		<script type="text/javascript" src="${webPath}/component/wkf/js/wkfTypeConfig.js" ></script>
		<link rel="stylesheet" href="${webPath}/component/wkf/css/wkfTypeConfig.css" />
	</head>
	<body>
		<div class="wkf-mian-list">
			<ul id="wkfTypeTree" class="ztree wkfTypeTree"></ul>
			<a id="wkf-type-add" class="wkf-type-add">+</a>
		</div>
		<div class="wkf-sub-list">
				<div class="header">
					<ul>
						<li>
							<span class="menu-no">菜单号</span>
							<span class="menu-name">菜单名称</span>
							<span class="menu-url">菜单地址</span>
							<span class="menu-js">调用函数</span>
							<span class="menu-type">类型</span>
							<span class="menu-sts">状态</span>
							<span class="menu-remark">备注</span>
							<span class="menu-opt">操作</span>
						</li>
					</ul>
				</div>
			<ul id="wkfVpTree" class="ztree wkfVpTree"></ul>
		</div>
		<div class="wkf-body-form">
			<div class="wkf-type-form">
				<table>
					<tr>
						<td colspan="2">
								新增审批类型
						</td>
					</tr>
					<tr>
						<td>类型编号</td>
						<td><input type="text" id="vpNo"></td>
					</tr>
					<tr>
						<td>类型名称</td>
						<td><input type="text" id="vpName"></td>
					</tr>
					<tr>
						<td colspan="2">
								<button type="button" id="vpAdd">添加</button>
								<button type="button" id="vpCancle">取消</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>	
</html>
