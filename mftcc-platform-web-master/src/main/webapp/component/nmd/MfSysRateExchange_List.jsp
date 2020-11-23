<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<script type="text/javascript" >
			$(function(){
			    initPage();
			 });
			 function initPage(){
				 myCustomScrollbar({
				    	obj:"#content",//页面内容绑定的id
				    	url:webPath+"/mfSysRateExchange/findByPageAjax",//列表数据查询的url
				    	tableId:"tableexchangerate0001",//列表数据查询的table编号
				    	tableType:"thirdTableTag",//table所需解析标签的种类
				    	pageSize:20,//加载默认行数(不填为系统默认行数)
				    	callback:function(){
				    	}//方法执行完回调函数（取完数据做处理的时候）
				    })
			 }
		</script>
	</head>
	<body style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
			<div  class="btn-div">
						<button type="button" class="btn btn-info pull-left"
							onclick="finForm_input('${webPath}/mfSysRateExchange/input');">新增</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=base">基础配置</a></li>
							<li class="active">汇率管理</li>
						</ol>
			</div>
				<!-- <div class="search-div">
					
					<div class="col-xs-3 column znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i> <input type="text"
								class="form-control" id="filter_in_input" placeholder="打印标题/凭证前缀">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
				</div> -->
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=币种"/>
						<!-- end -->
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
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
	<script type="text/javascript">
	function finForm_input(url){//新增弹框
		top.addFlag = false;
		top.createShowDialog(url,"新 增汇率",'90','90',function(){
			if(top.addFlag){
				 updateTableData();//重新加载列表数据
	   		}
		});
	}
	function getByIdThis(url){//详情弹框
		top.detailFlag = false;
		top.createShowDialog(url,"设置汇率",'70','60',function(){
			if(top.detailFlag){
				 updateTableData();//重新加载列表数据
	   		}
		});
	}
	</script>	
</html>