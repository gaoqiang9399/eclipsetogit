<%@ page language="java" import="java.util.*,com.dhcc.wxtld.core.base.*,com.dhcc.wxtld.core.domain.*,com.dhcc.wechat.entity.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	WxFormData2 datashow0001 = new WxFormData2();
	WxFormDataService2 wf2 = new WxFormDataService2();
	datashow0001 = wf2.getFormData("datashow0001");
	Map<String, Object> dataMap = new HashMap<String, Object>();
	WxDemo demo = new WxDemo();
	demo.setDemo1("手机分期付款");
	demo.setDemo2("0-10,000.00");
	demo.setDemo3("0-24");
	demo.setDemo4("0.8%");
	demo.setDemo5("个人信息、淘宝、京东、支付宝");
	dataMap.put("demo", demo);
	List<WxDemo> list = new ArrayList<WxDemo>();
	for(int i= 0;i<5;i++){
		list.add(demo);
	}
	dataMap.put("list", list);
	wf2.getObjectValue(datashow0001, dataMap);
	request.setAttribute("datashow0001",datashow0001);
	
	
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
</head>
<body>
<wx:wxformTag2 property="datashow0001"/>
</body>
</html>
