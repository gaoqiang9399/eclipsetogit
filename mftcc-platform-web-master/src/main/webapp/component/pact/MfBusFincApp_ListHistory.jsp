<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		var pactId = '${pactId}';
		var cusNo = '${cusNo}';
		var cusBaseType = '${cusBaseType}';
		var selectType = '${selectType}';
		var ajaxData = '${ajaxData}';
			$(function(){
				var table = "tablefincSelectHistory";
				var url = webPath+"/mfBusFincApp/getListHistory";
			    myCustomScrollbar({
					obj : "#content",//页面内容绑定的id
					url : url,//列表数据查询的url
					tableId : table,//列表数据查询的table编号
					tableType : "tableTag",//table所需解析标签的种类
					pageSize : 30,//加载默认行数(不填为系统默认行数)
					data:{pactId:pactId,cusNo:cusNo}//指定参数
				});
			 });
		function getHistoryDetails(obj,url){
			top.window.openBigForm(url,"历史记录详情",function(){
			});
		}	
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
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
</body>

</html>
