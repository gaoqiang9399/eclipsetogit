<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var cusNo = '${cusNo}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfBusFincApp/findOverdueByPageAjax?cusNo=" + cusNo,//列表数据查询的url
			    	tableId:"tableTrenchOverdueList",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize : 30,//加载默认行数(不填为系统默认行数)
					topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });
			 function getDetailRepayPlan(obj,url){
				 if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
			 	window.location.href=url;
			 }
			 function getDetailCus(obj,url){
				 if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
			 	window.location.href=url;
			 }
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=项目名称/客户名称"/>
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
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		filter_dic = [
			  {"optCode":"pactEndDate","optName":"合同结束日期","dicType":"date"},
			  {"optCode":"minEndDate","optName":"还款到期日期","dicType":"date"},
			  {"optCode":"overDays","optName":"逾期天数","dicType":"num"},
			  {"optCode":"repayPrcp","optName":"逾期本金","dicType":"num"}
			  ];
	</script>
</html>
