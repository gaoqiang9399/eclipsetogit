<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ include file="/component/include/webPath.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="js/d3.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" />
		<link rel="stylesheet" href="css/relation.css" />
		<script type="text/javascript">
			var basepath = '${webPath}';
		</script>
	</head>
	<body>
		<div class="mao-screen-area" id="screenArea" >
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
		<script type="text/javascript" src="js/relation.js" ></script>
	</body>
</html>