<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.mftcc.util.PropertiesUtil"%>
<%
String webPath = PropertiesUtil.getWebServiceProperty("webPath");
String servicemanagePath = PropertiesUtil.getWebServiceProperty("servicemanagePath");
String requestUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
if(webPath!=null&&!"".equals(webPath)){
	if("/".equals(webPath.substring(0, 1))){
		webPath = requestUrl + webPath;
	}else{
		webPath = requestUrl+"/"+webPath;
	}
}else{
	webPath = requestUrl;
}
request.setAttribute("webPath", webPath);
request.setAttribute("servicemanagePath", servicemanagePath);
%>

<script type="text/javascript">
var webPath = "${webPath}";
</script>
