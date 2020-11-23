<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/pmsDataSub/findByPageAjax",//列表数据查询的url
			    	tableId:"tablepms2001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >数据权限子表</strong>
				<!--我的筛选按钮-->
				<div class="filter-btn-group">
					<!--自定义查询输入框-->
					<input placeholder="智能搜索"  class="filter_in_input" type="text" />
					<div class="filter-sub-group">
						<button  class="filter_btn_search" type="button" ><i class="fa fa-search"></i></button>
						<button id="fiter_ctrl_btn" class="filter_btn_myFilter" type="button"  ><i class="fa fa-caret-down"></i></button>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
	</body>	
</html>
