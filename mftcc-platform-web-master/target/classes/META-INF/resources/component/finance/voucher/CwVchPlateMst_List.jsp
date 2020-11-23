<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<style type="text/css">
			#searchDiv {
			    padding: 5px 10px;
			}
		</style>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwVchPlateMst/findByPageAjax",//列表数据查询的url
			    	tableId:"tablevchplate0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			
			function ajaxGetPlateNo(obj, ajaxUrl){
				var plateNo = $(obj).find('input[name=plateNo]').val();
				top.cwBackData = plateNo;//获取摘要返回父页面
				$(top.window.document).find("#showDialog .close").click();//关闭此页面
			}
		</script>
	</head>
	<body>
		<!-- <div id="searchDiv" class="col-xs-12 znsearch-div" >
			<div class="col-xs-5"></div>
			<div class="col-xs-7">
				<div  class="input-group pull-right">
					<i class="i i-fangdajing"></i>
					<input type="text" class="form-control" id="search" name="search" placeholder="智能搜索">
					<span class="input-group-addon" onclick="getDataItem()">搜索</span>
				</div>
			</div>
		</div>
		页面显示区域
		<div id="content" class="table_content"  style="height: auto;">
		</div> -->
		
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=模版名称"/>
						<!-- end -->
				</div>
			</div>
		</div>
		
		<div class="row clearfix">
			<div class="col-md-12 column">
				<!--页面显示区域-->
				 <div id="content" class="table_content"  style="height: auto;">
					</div> 
			</div>
		</div>
	</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
</html>
