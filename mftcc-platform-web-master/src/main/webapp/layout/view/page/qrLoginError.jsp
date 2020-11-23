<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
	<%@ include file="/component/include/webPath.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息</title>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<style type="text/css">
html {
	margin: 0;
	padding: 0;
	border: 0;
	-webkit-text-size-adjust: 100%;
	-ms-text-size-adjust: 100%;
	font-size: 12px;
}

body {
/* 	font-family: "微软雅黑"; */
	font-size: 12px;
	line-height: 24px;
	background-color: #f3f3f3;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	color: #6f6f6f;
	width: 100%;
	height: 100%;
}

#wrapbc {
	position: absolute;
	width: 530px;
	height: 300px;
	top: 50%;
	left: 50%;
	margin-left: -265px;
	margin-top: -150px;
}

.picbc {
	width: 100px;
	height: 100px;
	line-height: 100px;
	border-radius: 60px;
	border: 4px solid red;
	text-align: center;
	vertical-align: middle;
	color: red;
	font-size: 90px;
	margin-left: auto;
	margin-right: auto;
}

.textbc {
	width: 530px;
	height: 90px;
}

.textbc h1 {
	font-size: 30px;
	color: #2273b4;
	text-align: center;
}
</style>
</head>
<body>
	<div id="wrapbc">
		<div class="picbc">！</div>
		<div class="textbc">
			<h1 id="errorMsg">扫码失败,请重试!</h1>
		</div>
	</div>
	<script type="text/javascript">
		<s:if test="hasActionErrors()">
	                var actionmsg ="";
	            <s:iterator value="actionErrors">
	            	actionmsg = actionmsg+"<s:property escape="false"/>" + " ";
	    		 </s:iterator>
	      if(actionmsg!=""){
                $("#errorMsg").text(actionmsg);
           }
           </s:if>
	</script>
</body>
</html>

