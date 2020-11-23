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
			    	url:webPath+"/parmCorpInfo/findByPageAjax",//列表数据查询的url
			    	tableId:"tableparam0003",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			  function getDetail(url){
			 	window.top.openBigForm(url,'租赁公司信息详情');
			 }
			  function getUpdatePage(url){
			 	window.top.openBigForm(url,'租赁公司信息修改');
			 }
		</script>
	</head>
	<body>
	<dhcc:markPoint markPointName="ParmCorpInfo_List"/>
	<div>
		<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >租赁公司信息</strong>
				<!--我的筛选按钮-->
				<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
				<dhcc:thirdButton value="新增"  action="ParmCorpInfo001"onclick="window.top.openBigForm('${webPath}/parmCorpInfo/input','租赁公司信息新增')"></dhcc:thirdButton>
					<!--我的筛选按钮-->
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input id="filter_in_input" placeholder="智能搜索" class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search" class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;"></div>
	</body>	
</html>
