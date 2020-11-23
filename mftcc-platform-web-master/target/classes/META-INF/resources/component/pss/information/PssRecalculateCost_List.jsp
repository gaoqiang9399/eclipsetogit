<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>成本重算单</title>
		<script type="text/javascript" src="<%=webPath %>/component/pss/information/js/PssRecalculateCost_List.js"></script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="formRowCenter">
				<dhcc:thirdButton value="重算成本" action="重算成本" onclick="pssRecalculateCost_List.recalculateCost();"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="放弃" action="放弃" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>