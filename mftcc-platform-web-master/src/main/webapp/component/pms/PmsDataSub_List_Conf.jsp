<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pms/js/pms_data_sub.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pms/css/pms_data_sub.css" />
	</head>
	
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >数据权限子表</strong>
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
					<%-- <div class="search-lable" id="pills">
						<dhcc:thirdButton value="保存"  action=""onclick=""></dhcc:thirdButton>
						<input type="button" value="保存" onclick="saveDataRangSub();">
					</div> --%>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<ul style="-moz-user-select: none;" class="ulThead">
				<li>
					<!-- <span class="col1">勾选</span> -->
					<span class="col2">权限类型</span>
					<span class="col3">指定字段</span>
					<!-- <span class="col4">是否启用指定字段</span> -->
				</li>
			</ul>
			<ul id="pmsTree" class="ulTable ztree" style="background:#fff;"></ul>
		</div>
		<input type="hidden" id="funNo" value='${funNo}'/>
		<div class="formRowCenter" style="margin-top:40px;">
		    <dhcc:thirdButton value="保存" action="保存" typeclass="insertAjax" onclick="saveDataRangSub();"></dhcc:thirdButton>
		    <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	    </div>
	</body>	
</html>
