<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfFeePactQueryList.js"></script>
		<script type="text/javascript" >
			var fincIdStr="";
			var assignType="";
			$(function(){
				MfFeePactQueryList.init();
			 });
			function getDetailPage(obj,url){		
				top.LoadingAnimate.start();	
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				window.location.href=url;	
				event.stopPropagation();
			}
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div" id="search-div">
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
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
</html>
