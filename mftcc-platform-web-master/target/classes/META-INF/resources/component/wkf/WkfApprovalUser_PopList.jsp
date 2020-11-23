<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String taskId = request.getParameter("taskId");
String parNames = request.getParameter("parNames");
String popNames = request.getParameter("popNames");
%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<script type="text/javascript">
	window.name = "curWindow";
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/wkfApprovalUser/findByPagePop4Ajax?parNames="+'<%=popNames%>'+"&popNames="+'<%=parNames%>'+"&taskId="+'<%=taskId%>',//列表数据查询的url
			tableId : "tablewkf0005",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true
		//是否有我的筛选
		});
	});
</script>
	<body class="bodybg-gray">
	<input type="hidden" name="wkfUserName">
		<div>
			<div style="vertical-align: bottom; display: block;" class="tabCont">
				<strong>流程任务列表</strong>
				
				<div class="search-group">
					<!--我的筛选选中后的显示块-->
					<div class="search-lable" id="pills">
						<!--我的筛选按钮-->
						<div class="filter-btn-group">
							<!--自定义查询输入框-->
							<input id="filter_in_input" placeholder="智能搜索" class="filter_in_input" type="text" />
							<div class="filter-sub-group">
								<button id="filter_btn_search" class="filter_btn_search" type="button">
									<i class="i i-fangdajing"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div id="content" class="table_content" style="height: auto;">
				<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
			</div>
			<dhcc:formTag property="formwkf0033" mode="query" />
		</div>
	</body>
	
</html>
