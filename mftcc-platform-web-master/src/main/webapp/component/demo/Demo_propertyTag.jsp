<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/anchor.js"></script>
		<script type="text/javascript">
			function dblSuccessCallback(dataname){
				if(dataname=="demoWork"){
					handleAnchorFun($(".fieldReal [name='"+dataname+"']"));
				}
			}
		</script>
	</head>
	<body class="layout layoutTag">
	<dhcc:markPoint markPointName="Demo_propertyTag"/>
		<div id="tts" class='cell' style=' height:auto; background-color:#e8edf6' data-handle=".handle">
			<form  method="post" theme="simple" name="operform" action="${webPath}/demo/updateAjaxByOne">
				<div class="info">
					<dhcc:propertySeeTag property="formdemo0001" mode="query"/>
					<a href="#" class="more font-small bold">更多<i class="i i-jiantoua"></i></a>
				</div>
			</form>	
		</div>
	</body>
</html>