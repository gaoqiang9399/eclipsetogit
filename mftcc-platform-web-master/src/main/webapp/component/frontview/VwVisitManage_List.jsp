<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
					obj : "#content", //页面内容绑定的id
					url : webPath+"/vwVisitManage/findByPageAjax", //列表数据查询的url
					tableId : "tablevwVisitManage_ML", //列表数据查询的table编号
					tableType : "thirdTableTag", //table所需解析标签的种类
					pageSize:30 //加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div  class="btn-div">

				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=联系人/联系电话/公司名称"/>
				</div>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;">
			</div>
		</div>
	</div>
	</body>
</html>
