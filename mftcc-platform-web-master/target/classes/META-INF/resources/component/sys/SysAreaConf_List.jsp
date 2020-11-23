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
			    	url:webPath+"/sysAreaConf/findByPageAjax",//列表数据查询的url
			    	tableId:"tablesys1001",//列表数据查询的table编号
			    	tableType:"thirdTableTag"//table所需解析标签的种类
			    });
			 });
		</script>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >大区配置</strong>
				<div class="search-group">
					<!--自定义查询输入框-->
					<input class="filter_in_input"  type="text"/>
					<dhcc:thirdButton value="新增" action="SysAreaConf001" onclick="${webPath}/sysAreaConf/input"></dhcc:thirdButton>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
		</div>
	</body>	
</html>
