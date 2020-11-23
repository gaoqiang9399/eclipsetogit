<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/frontview/js/VwProductManage_List.js"></script>
		<script type="text/javascript" >
			$(function(){
                VwProductManage_List.init()
			 });
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12">
				<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="VwProductManage_List.toApplyInsert();">产品新增</button>
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">前端产品</span>
					</div>
					<div class="col-md-2">
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=产品名称/申请对象"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content">
				</div>
			</div>
		</div>
	</div>
	</body>
</html>
