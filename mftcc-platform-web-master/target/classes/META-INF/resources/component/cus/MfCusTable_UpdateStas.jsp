<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>

<!DOCTYPE html>
<html>
	<head>
	<title>新增</title>
	<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusTable_UpdateStas.css" />
	<script type="text/javascript" src='${webPath}/component/cus/js/MfCusTable_UpdateStas.js'> </script>	
	<script type="text/javascript">
	var cusTableList = '${ajaxData}';
	cusTableList = JSON.parse(cusTableList);
	//var selCount = 0;
	//var unSelCount = 0;
	var countt = 0;
	var cusNo;
	var pageView = '${dataMap.pageView}';
	var relNo = '${relNo}';
	var isMustHtm1 = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>已选</span>";
	var isMustHtm2 = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>未选</span>";
	</script>			
	</head>
	
		<body class="bg-white overflowHidden">
			<div class="container form-container">
				<div class="scroll-content">
					<div class="row clearfix" style="margin-top: 10px;">
						<div class="col-xs-12 column color_theme" id="yes">已选</div>
						<div class="col-xs-12 column" id="rotate-body"></div>
						<div class="col-xs-12 column color_theme" id="no">未选</div>
						<div class="col-xs-12 column" id="rotate-body-no"></div>
					</div> 
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="确认" action="保存"
					onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</body>
</html>



