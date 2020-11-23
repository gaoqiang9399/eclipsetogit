<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>融资租赁系统</title>
</head>
  <frameset id="mainFramestId"  cols="203,10,*" frameborder="no" border="0" framespacing="0">
    <frame src="left.jsp" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" />
	<frame src="nav.jsp"  name="navFrame" scrolling="no" noresize="noresize" id="navFrame" />
    <frameset rows="30,*" frameborder="no" border="0" framespacing="0" name="mainFrame" id="mainFrame">
		<frame src="location.jsp" name="locFrame" scrolling="no" noresize="noresize" id="locFrame" />
		<frame src="welcome.jsp" frameborder="0" name="rightFrame" id="rightFrame" scrolling="auto"/>
	</frameset>
  </frameset>
<noframes><body>
</body>
</noframes>
</html>
