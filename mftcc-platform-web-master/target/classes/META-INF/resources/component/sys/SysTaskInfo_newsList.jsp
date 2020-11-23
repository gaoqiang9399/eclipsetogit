<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var taskSts="${dataMap.taskSts}";
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/sysTaskInfo/findNewsListPageAjax",//列表数据查询的url
			    	tableId:"tablesysNewsList001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{"taskSts":taskSts}//指定参数 (过滤掉已经封挡的数据)
			    });
			 });
			 
			 function showNews(taskId){
			 	var parmMap=getParams(taskId);
			 	
			 	window.top.showDialog(webPath+"/sysTaskInfo/getNewsDetail?taskId="+parmMap.taskId+"&ifNext=0","消息查看","80","50");
			 }
			 
			 function getParams(parms){
			 	var result={};
			 	if(parms.length>0){
			 	var parmStr=parms.split("?");//获取参数字符串
			 	var parmAry=parmStr[1].split("&");//获取每个参数字符串
			 	var len=parmAry.length;
			 	if(len>0){
			 		var subAry=null;
			 		
			 		for(var i=0;i<len;i++){
			 			subAry=parmAry[i].split("=");
			 			result[subAry[0]]=subAry[1];
			 		}
			 	}
			 	
			 	}
			 	return  result;
			 }
 
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=消息内容/消息标题"/>
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
