<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String pactNo = (String) request.getAttribute("pactNo");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
				var pactNo='<%=pactNo%>';
			    myCustomScrollbar({
					obj : "#content", //页面内容绑定的id
					url : webPath+"/mfBusAddAmtRecord/findByPageAjax?pactNo="+pactNo, //列表数据查询的url
					tableId : "tableaddamtrecord0001", //列表数据查询的table编号
					tableType : "thirdTableTag", //table所需解析标签的种类
					pageSize:15 //加载默认行数(不填为系统默认行数)
					//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
