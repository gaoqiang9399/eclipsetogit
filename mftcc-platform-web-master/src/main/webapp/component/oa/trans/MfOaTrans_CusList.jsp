<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body class="overflowHidden multi-select-list">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称"/>
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
	<div class="formRowCenter">
		<dhcc:thirdButton value="确认" action="确认" typeclass ="insertAjax" onclick="OaTransCusList.selectCusInfo();"></dhcc:thirdButton>
		<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="OaTransCusList.closeDialog();"></dhcc:thirdButton>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript" src="${webPath}/component/oa/trans/js/MfOaTransCusList.js"></script>
<script type="text/javascript" >
	var cusMngNo = '${cusMngNo}';
	$(function(){
		OaTransCusList.init();
	});
</script>
</html>
