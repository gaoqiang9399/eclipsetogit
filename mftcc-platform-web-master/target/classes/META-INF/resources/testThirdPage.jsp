<%@ page session="false" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息</title>
<style type="text/css">
html {
	margin:0;
	padding:0;
	border:0;
	-webkit-text-size-adjust:100%;
	-ms-text-size-adjust:100%;
	font-size:12px;
}
body {
	font-size:12px;
	line-height: 24px;
	background-color:#f3f3f3;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	color:#6f6f6f;
	width:100%;
	height:100%;
}
#wrapbc {
	position:absolute;
	width:530px;
	height:300px;
	top:50%;
	left:50%;
	margin-left:-265px;
	margin-top:-150px;
}
.picbc {
	width:149px;
	height:184px;
	background-image:url(layout/view/page/imgs/pic.png);
	margin-left:auto;
	margin-right:auto;
}
.textbc {
	width:530px;
	height:90px;
}
.textbc h1 {
	font-size:30px;
	color:#2273b4;
	text-align:center;
}
.textbc h2 {
	font-size:16px;
	text-align:center;
	font-weight:normal;
}
.redbc {
	color:#dd3900;
}
</style>
</head>
<body>
<div id="wrapbc">
    <form method="post" enctype="multipart/form-data" action="${webPath}/testThird/testThirdAjax">
      请选择报文文件：
      <input name="file" type="file"/>
      <input type="submit" style="width:100px" value="提交"/>
    </form>
</div>
</body>
</html>
