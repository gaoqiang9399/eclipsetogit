<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/sys/js/MfSysSkinUser_List.js?v=${cssJsVersion}"></script>
		<link rel="stylesheet" href="${webPath}/component/sys/css/MfSysSkinUser_List.css?v=${cssJsVersion}">
		<script type="text/javascript" >
            var skinList = '${skinArray}';
            skinList = JSON.parse(skinList);
			$(function(){
				MfSysSkinUser_List.init();
		 	});
		</script>
	</head>
	<body class="overflowHidden bg-white">
	<div class="container">
		<div class="row clearfix skin-item">


		</div>
	</div>
	</body>	
</html>
