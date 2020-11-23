<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<script type="text/javascript" src="${webPath}/component/pact/feerefund/js/MfBusFeeCollect_List.js?v=${cssJsVersion}"></script>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfBusFeeCollect/findByPageAjax", //列表数据查询的url
				tableId : "tablefeeCollectList", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div top-title">收费管理</div>
					<div class="search-div">
						<button type="button" class="btn btn-primary" onclick="MfBusFeeCollect_List.feeCollectInput();">新增</button>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=合同号/客户名称"/>
					</div>
				</div>
			</div>
				<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
