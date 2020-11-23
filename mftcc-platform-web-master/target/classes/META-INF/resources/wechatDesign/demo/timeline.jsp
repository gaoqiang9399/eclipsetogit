<%@ page language="java" import="java.util.*,com.dhcc.wxtld.core.base.*,com.dhcc.wxtld.core.domain.*,com.dhcc.wechat.entity.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	WxFormData2 timeline0001 = new WxFormData2();
	WxFormDataService2 wf2 = new WxFormDataService2();
	timeline0001 = wf2.getFormData("timeline0001");
	Map<String, Object> dataMap = new HashMap<String, Object>();
	WxDemo demo = new WxDemo();
	demo.setDemo1("2016-03-06");
	demo.setDemo6("15:13:14");
	demo.setDemo2("32,000元");
	demo.setDemo3("30,000元");
	demo.setDemo4("2,000元");
	demo.setDemo5("0元");
	List<WxDemo> list = new ArrayList<WxDemo>();
	for(int i= 0;i<5;i++){
		list.add(demo);
	}
	dataMap.put("list", list);
	wf2.getObjectValue(timeline0001, dataMap);
	request.setAttribute("timeline0001",timeline0001);
	
	
%>

<!DOCTYPE html>
<html>
<head>
<link type="image/x-icon" rel=
"shortcut icon" href="wechatDesign/img/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>标签解析测试页面</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/wechatDesign/weblib/font-awesome-4.7.0/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/wechatDesign/weblib/jQueryWeUI/lib/weui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>/wechatDesign/weblib/jQueryWeUI/css/jquery-weui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>/wechatDesign/css/weui.extend.css" />
<script src="<%=basePath %>/wechatDesign/js/jquery-1.11.0.js" type="text/javascript"></script>
<script src="<%=basePath %>/wechatDesign/weblib/jQueryWeUI/js/jquery-weui.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath %>/wechatDesign/js/wxForm.js" type="text/javascript" charset="utf-8"></script>
<style>
#wrapper{-webkit-overflow-scrolling:touch;overflow:auto;}
</style>
</head>
<body>
<wx:wxformTag2 property="timeline0001"/>
<script src="<%=basePath %>/wechatDesign/weblib/jQueryWeUI/lib/fastclick.js"></script>
<script>
</script>
</body>
</html>
