<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<script src="${webPath}/component/risk/js/layer.js"></script>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
			<div  class="btn-div">
						<button type="button" class="btn btn-primary pull-left"
							onclick="finForm_input('${webPath}/vwContManage/input');">新增</button>
<!-- 						<ol class="breadcrumb pull-left"> -->
<!-- 						<li><a -->
<%-- 								href="${webPath}/component/oa/MfOaEntrance.jsp?entranceNo=6">办公</a></li> --%>
<!-- 							<li><a -->
<%-- 								href="${webPath}/component/frontview/VwEntrance.jsp">前端网站管理</a></li> --%>
<!-- 							<li class="active">菜单内容管理</li> -->
<!-- 						</ol> -->
			</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=标题/描述/所属模块"/>
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
	</body>
<script src="${webPath}/component/frontview/js/VwContManage_List.js"></script>
<script type="text/javascript" >
$(function(){
   VwContManageList.init();
 });
</script>
</html>
