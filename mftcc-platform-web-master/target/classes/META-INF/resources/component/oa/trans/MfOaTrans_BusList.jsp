<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body class="overflowHidden">
	<div class="container">		
		<div id="content" class="table_content"  style="height: calc(100% - 60px);">
			<dhcc:tableTag property="tablepacttrans00001" paginate="cusAndAppList" head="true"></dhcc:tableTag>	
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认" typeclass ="insertAjax" onclick="OaTransBusList.selectBusInfo();"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="OaTransBusList.closeDialog();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/trans/js/MfOaTransBusList.js"></script>
<script type="text/javascript" >
	$(function(){
		OaTransBusList.init();
	});
</script>
</html>
