<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<script type="text/javascript" src="${webPath}/component/examine/js/MfExamineHis_List4risk.js"></script>	
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfExamineHis/getExamList4RiskPageAjax",//列表数据查询的url
			    	tableId:"tableexamfinclist4Risk",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			function getDetailPage(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.LoadingAnimate.start();		
				window.location.href=url;
				event.stopPropagation();
			}
			function trClick(url){		
				clearTimeout(timeFunc);
				timeFunc=setTimeout(function(){
					if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
					top.LoadingAnimate.start();		
					window.location.href=url;
				}, 300);
			}
		</script>
	</head>
	<body>
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
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
