<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript">
			var basepath = '${webPath}';
			var cusNo='${dataMap.cusNo}';
			var baseType='${dataMap.baseType}';//客户类型
		</script>
		<script type="text/javascript" src="${webPath}/component/cus/relation/js/d3.js" ></script>
		<script type="text/javascript" src="${webPath}/component/cus/relation/js/relationForView.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${webPath}/component/cus/relation/css/relation.css" />
		
	</head>
	<body>
	
		<div class="row  bg-white">
		</div>
		<div class="mao-screen-area" id="screenArea">
			<div id="main" class="mao-main"></div>
			<div class="mao-toolbar">
				<ul>
					
					<li onclick="maoScale(1)"><i class="fa fa-plus"></i></li>
					<li onclick="maoScale(2)"><i class="fa fa-minus"></i></li>
				</ul>
				<ul>
					<!-- <li onclick="changeScreen(this)"><i class="fa fa-expand"></i></li> -->
					<li onclick="maoRefresh()"><i class="fa fa-refresh"></i></li>
					<li onclick="saveImg()"><i class="fa fa-save"></i></li>
				</ul>
			</div>
		</div>

		
	</body>
</html>