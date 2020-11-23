<%@ page language="java" import="java.util.*,com.dhcc.wxtld.core.base.*,com.dhcc.wxtld.core.domain.*,com.dhcc.wechat.entity.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	WxFormData2 desc0001 = new WxFormData2();
	WxFormDataService2 wf2 = new WxFormDataService2();
	desc0001 = wf2.getFormData("desc0001");
	Map<String, Object> dataMap = new HashMap<String, Object>();
	WxDemo demo = new WxDemo();
	demo.setDemo1("22-60周岁");
	demo.setDemo2("郑州及周边郊县，包括新郑、中牟、荣阳、上街");
	demo.setDemo3("在现单位连续工作满6个月");
	demo.setDemo4("暂不接受个体商户，私营业主申请");
	demo.setDemo5("月打卡工资2000元以上，带有工资字样不接受工资转账");
	demo.setDemo6("信用良好，无不良逾期，不接受信用空白人士");
	dataMap.put("demo", demo);
	wf2.getObjectValue(desc0001, dataMap);
	request.setAttribute("desc0001",desc0001);
	
	
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
<wx:wxformTag2 property="desc0001"/>
</body>
</html>
