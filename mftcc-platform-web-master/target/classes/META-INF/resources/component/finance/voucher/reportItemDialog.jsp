<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>报表项列表</title>
	<link rel="stylesheet" href="${webPath}/themes/view/css/tableFour.css" />
	<!-- 科目树 -->
	<style type="text/css">
		/* * {
			    -webkit-box-sizing: content-box;
			    -moz-box-sizing: content-box;
			    box-sizing: content-box;
		} */
		body{background-color:#fff;}

		/* #searchDiv {
		    padding: 5px 15px;
		    min-width: auto;
			 -webkit-box-sizing: border-box;
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		}
		#searchDiv input{
			 -webkit-box-sizing: border-box;
		    -moz-box-sizing: border-box;
		    box-sizing: border-box;
		} */
		
		.btn-info{
			margin: 5px 15px;
		}
	</style>
	<script type="text/javascript">
	//var reportTypeId = '${param.reportTypeId}';
/* 	$(function(){
		//加载列表
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/cwReportItem/findByPageAjax",//列表数据查询的url
	    	tableId:"tablereportitem0001",//列表数据查询的table编号
	    	tableType:"tableTag",//table所需解析标签的种类
//		    	myFilter:false, //是否有我的筛选(列表列动态切换)
	    	pageSize:10,//加载默认行数(不填为系统默认行数)
	    	data:{reportTypeId:reportTypeId},//指定参数
	    	ownHeight:true,
	    	callback:function(){
	    		
	    	}//方法执行完回调函数（取完数据做处理的时候）
	    });
	}); */
	
	//获取 查询条件（方法名固定写法）
	/* function getFilterValArr(){ 
		return JSON.stringify($('#assistForm').serializeArray());
	} */
	
	function ajaxGetItemNo(event, ajaxUrl) {
		var obj = {};
		obj.reportItemId = $(event).children('td').eq(0).text();
		obj.reportName = $(event).children('td').eq(1).text();
		top.cwBackData = obj;//获取摘要返回父页面
		$(top.window.document).find("#showDialog .close").click();//关闭此页面
		//给单击进来的文本框赋值
	};
	function chooseGetItemNo(event, ajaxUrl) {
		var tr=$(event).parents("tr")[0];
		var obj = {};
		obj.reportItemId = $(tr).children('td').eq(0).text();
		obj.reportName = $(tr).children('td').eq(1).text();
		top.cwBackData = obj;//获取摘要返回父页面
		$(top.window.document).find("#showDialog .close").click();//关闭此页面
		//给单击进来的文本框赋值
	};
	</script>

<script type="text/javascript" >
		var reportTypeId = '${param.reportTypeId}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwReportItem/findByPageAjax?reportTypeId="+reportTypeId,//列表数据查询的url
			    	tableId:"tablereportitem0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:10//加载默认行数(不填为系统默认行数)
			    });
			 });

		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder="/>
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