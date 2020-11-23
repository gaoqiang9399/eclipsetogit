<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.mftcc.util.PropertiesUtil"%>
<%
    String pageOfficePath = PropertiesUtil.getWebServiceProperty("newPageofficeURL");
    String pageOfficePort = PropertiesUtil.getWebServiceProperty("newPageofficePort");
    String pageOfficeVersion = PropertiesUtil.getPageOfficeConfigProperty("office_version");

    /*
     * 由前端发起的，不应单独配置URL，避免跨域问题、不同访问方式（域名/IP/VPN）不兼容。
     * 由于pageOffice不同端口时可以打开（无session验证），目前定义一个特殊的端口来使用（不配置URL的前提下）。
     * LiuYF 20190912
    */
    if (pageOfficePath == null || pageOfficePath.equals("")) {
        if (pageOfficePort == null || pageOfficePort.equals("")) {
            pageOfficePort = "8722";
        }
        pageOfficePath = request.getScheme() + "://" + request.getServerName() + ":" + pageOfficePort;
    }
    if(pageOfficeVersion == null){
        pageOfficeVersion = "0";
    }
    request.setAttribute("pageOfficePath", pageOfficePath);
    request.setAttribute("pageOfficeVersion", pageOfficeVersion);
%>

<script type="text/javascript">
    var pageOfficePath = "${pageOfficePath}";
    var pageOfficeVersion = "${pageOfficeVersion}";
</script>
