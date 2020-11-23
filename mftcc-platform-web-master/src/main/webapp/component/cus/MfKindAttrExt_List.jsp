<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfKindAttrExt.js"></script>
		<script type="text/javascript" >
			$(function(){
                MfKindAttrExt.init();
			 });
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div  class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary pull-left" onclick="MfKindAttrExt.kindExtForm_input('${webPath}/mfKindAttrExt/input');">新增</button>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">非常规产品查询</span>
					</div>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=产品名称/登记时间"/>
				</div>
			</div>
		</div>
	</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
	</body>
</html>
