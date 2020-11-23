<%@ page language="java" import="java.util.*,java.io.*,com.zhuozhengsoft.pageoffice.*" pageEncoding="utf-8"%>
<%
	FileSaver fs=new FileSaver(request,response);
	String path=request.getParameter("savePath");
	String fileName=request.getParameter("saveFileName");
	//拼接路径
	String realPathFile = path+fileName;
	//文件目录不存在就创建
	File file = new File(realPathFile);
 	if (!file.getParentFile().exists()) {
       file.getParentFile().mkdirs();  
    } 
	fs.saveToFile(realPathFile);
	//设置保存返回值
	fs.setCustomSaveResult("ok");
	fs.close();
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>pageoffice保存页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	</script>
  </head>
  <body>
  </body>
</html>
