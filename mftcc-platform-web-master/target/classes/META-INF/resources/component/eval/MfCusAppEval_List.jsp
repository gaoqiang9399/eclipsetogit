<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
			    	url:webPath+"/mfCusAppEval/findByPageAjax",//列表数据查询的url
			    	tableId:"tableMfCusAppEval",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 function getAppEvalForCusNo(obj,url){
			 	top.openBigForm(url,"评估等级详情", function(){});
			 }
		</script>
	</head>
	<body class="overflowHidden">
		
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div top-title">客户评级查询</div>
					<div class="search-div" id="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
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
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
		<script type="text/javascript">
		
			filter_dic = [ {
				"optCode" : "cusType",
				"optName" : "客户类别",
				"parm" :${cusTypeJsonArray},
				"dicType" : "y_n"
			},{
				"optCode" : "approvalGrade",
				"optName" : "评估等级",
				"parm" :${evalLevelJsonArray},
				"dicType" : "y_n"
			},{
				"optCode" : "evalEndDate",
				"optName" : "评级到期日期",
				"parm" :[],
				"dicType" : "date"
			}  ];
		</script>
	</body>	
</html>