<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body>
		
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<dhcc:tableTag property="tablecuswarehouse0001" paginate="mfCusWarehouseList" head="true"></dhcc:tableTag>
		</div>
	</body>	
</html>
