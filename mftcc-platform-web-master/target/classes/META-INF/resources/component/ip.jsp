<%@page import="cn.mftcc.util.DateUtil"%>
<%@page import="cn.mftcc.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	out.print("现在时间：" + DateUtil.getDateTime());
	out.print("username：" + PropertiesUtil.getMsgConfigProperty("username"));
	System.out.println("impl：" + PropertiesUtil.getMsgConfigProperty("impl"));
	System.out.println("cloud.ip：" + PropertiesUtil.getCloudProperty("cloud.ip"));
	
	out.print("<br/>");
	
	String realIP = request.getHeader("x-forwarded-for");
	if (realIP != null && realIP.length() != 0) {
		out.print("你的IP地址是：" + realIP);
		System.out.println("你的IP地址是：" + realIP);
	} else {
		String ip = request.getRemoteAddr();
		out.print("你的IP地址是：" + ip);
		System.out.println("你的IP地址是：" + ip);
	}
%>
</body>
</html>