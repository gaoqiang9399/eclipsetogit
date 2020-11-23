<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div id="itemsDiv" class="margin_top_25">
				
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="确认" action="确认" onclick="MfKindNodeList.selectConfirm();"></dhcc:thirdButton>
		</div>
	</div>
</body>

<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindNodeList.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var itemList = '${ajaxData}';
    itemList = JSON.parse(itemList);
    $(function(){
    	MfKindNodeList.init();
    });
   </script>
</html>
