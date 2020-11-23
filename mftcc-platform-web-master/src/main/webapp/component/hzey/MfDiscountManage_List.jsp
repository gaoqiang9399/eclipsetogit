<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/hzey/js/MfDiscountManage.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfDiscountManage/getAllCusByPageAjax", //列表数据查询的url
				tableId : "tablecustomerdiscount0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					
					<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="MfDiscountManage.applyInsertList();">批量新增</button>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">优惠券查询</span>
					</div>
					<div class="col-md-2">
					</div>
				</div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称"/>
				</div>
					
					
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
