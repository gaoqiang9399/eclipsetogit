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
			    	url:webPath+"/mfCusCreditSearch/findByPageAjax",//列表数据查询的url
			    	tableId:"tableMfCusCreditSearch",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 function getMfCusCreditInfo(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	top.openBigForm(url,"客戶授信详情", function(){});
			 }
		</script>
	</head>
	<body class="overflowHidden">
		
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div top-title">客户授信查询</div>
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
				"optCode" : "creditSum",
				"optName" : "授信额度",
				"parm" : [],
				"dicType" : "num"
			},{
				"optCode" : "authBal",
				"optName" : "可用额度",
				"parm" : [],
				"dicType" : "num"
			} ,{
				"optCode" : "beginDate",
				"optName" : "授信开始日期",
				"parm" : [],
				"dicType" : "date"
			},{
				"optCode" : "endDate",
				"optName" : "授信截止日期",
				"parm" : [],
				"dicType" : "date"
			},{
				"optName" : "授信期限",
				"parm" : [],
				"optCode" : "creditTerm",
				"dicType" : "num"
			},{
				"optCode" : "creditSts",
				"optName" : "授信状态",
					"parm" : [ {
						"optName" : "已授信",
						"optCode" : "1"
					}, {
						"optName" : "未授信",
						"optCode" : "0"
					} ],
				"dicType" : "y_n"
			}   ];
		</script>
	</body>	
</html>