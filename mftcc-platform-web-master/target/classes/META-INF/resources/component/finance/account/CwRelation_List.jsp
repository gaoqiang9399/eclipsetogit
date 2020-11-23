<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%-- <%@ include file="/component/include/pub_view.jsp"%> --%>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwRelation/findByPageAjax?txType="+'${txType}',//列表数据查询的url
			    	tableId:"tablecwrelation0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body class="bg-white">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
				<!-- 	<button type="button" class="btn btn-primary" onclick="addCwkeRelation()">新增</button>
					<button type="button" class="btn btn-primary" onclick="returnToFuzhu()">返回</button> -->
					<button type="button" class="btn btn-primary pull-left" onclick="addCwkeRelation()">新增</button>
					<ol class="breadcrumb pull-left">
						<li><a href="${webPath}/cwFication/getListPage">辅助核算</a></li>
						<li class="active">配置科目</li>
					</ol>
					
				</div>
				<div class="search-div">
					<div class="col-xs-9 column mysearch-div" id="pills">
						<div id="filter_list" class="pull-left">
							<ul class="ztree" id="my_filter" style="-moz-user-select: none;">
							</ul>
						</div>
						
					</div>
					<div class="col-xs-3 column znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
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
	</div>
	<div style="display:none">
		<span id="txType">${txType}</span>
	</div>
</body>   
<script type="text/javascript">
		function addCwkeRelation(){
			var txType = $("#txType").text();
			window.parent.openBigForm("${webPath}/cwRelation/input?txType="+txType,"新增科目与辅助核算关系",function(){
				myclose_click();
				updateTableData();//重新加载列表数据
			});
		
		}
		//getRelationById,getcwRelationbyId
		function getRelationById(url){
		
			window.parent.openBigForm(webPath+"/"+url,"科目与辅助核算关系详情",function(){
				myclose_click();
				updateTableData();//重新加载列表数据
			});
		}
		//
		function returnToFuzhu(){
			window.location.href = "${webPath}/cwFication/getListPage";
		}
	</script>
</html>
