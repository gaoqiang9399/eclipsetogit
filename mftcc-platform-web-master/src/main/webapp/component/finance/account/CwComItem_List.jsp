<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		 <style type="text/css">
		 	.backS1{}
		 	.backS2{margin-right: 15px;}
		 	.backS3{margin-right: 30px;}
		 	.backS4{margin-right: 45px;}
		 	.backS5{margin-right: 55px;}
		 	.backS6{margin-right: 65px;}
		 	.backS7{margin-right: 75px;}
		 	.backS8{margin-right: 85px;}
		 	.backS9{margin-right: 95px;}
		 	.backS10{margin-right: 105px;}
		 </style>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwComItem/findByPageAjax",//列表数据查询的url
			    	tableId:"tableaccount0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		/* 	$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwComItem/findByPageAjax?accType=${accType}",//列表数据查询的url
			    	tableId:"tableaccount0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			  */

		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary pull-left" onclick="addAccountkemu()">新增一级科目</button>
					<ol class="breadcrumb pull-left">  
								<li><a href="${webPath}/cwParamset/cwParamEntrance">设置</a></li>
								<li class="active">科目管理 </li>
							</ol>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
					<!-- begin -->
						<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=科目名称/科目编号"/>
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
	
	<script type="text/javascript" src="${webPath}/component/finance/account/js/CwComItem_List.js"></script>

	<script type="text/javascript">
	filter_dic =[
	{"optCode":"accType",
	"optName":"科目类型",
	"parm":[{
	"optName":"资产类","optCode":"1"},
	{"optName":"负债类","optCode":"2"},
	{"optName":"共同类","optCode":"3"},
	{"optName":"所有者权益类","optCode":"4"},
	{"optName":"成本类","optCode":"5"},
	{"optName":"损益类","optCode":"6"},
	{"optName":"表外类","optCode":"7"}
	],
	"dicType":"y_n"
	}];
	</script>
</html>
