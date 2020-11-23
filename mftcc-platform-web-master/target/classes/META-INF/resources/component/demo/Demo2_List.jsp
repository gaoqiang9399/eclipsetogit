<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/demo2/findByPageAjax",//列表数据查询的url
			    	tableId:"demo0003",//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
			    	myFilter:false
			    });
			 });
		</script>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >用户信息列表</strong>
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
					<dhcc:button value="新增" action="新增" onclick="${webPath}/demo2/input"></dhcc:button>
				<!--我的筛选按钮-->
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input placeholder="智能搜索" id="filter_in_input"  class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search"  class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<!--待定是否放置自定义table标签?-->
			<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
		</div>
	</body>	
</html>
