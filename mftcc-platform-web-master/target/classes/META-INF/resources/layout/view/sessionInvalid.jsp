<%@page import="app.component.sys.entity.SysMenu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%@ page import="java.util.*" %>


<!--  一二级菜单联动示例-------------->
<%
Map map;
String id = null;
String menuNo = null;
if(session==null || session.getAttribute("menuTreeMap")==null){
	map = null;
}else {
	map = (Map)session.getAttribute("menuTreeMap");
	id = request.getParameter("id");
    List<SysMenu> sysMenuList = (List<SysMenu>)session.getAttribute("sysMenuLev1List");
    if(sysMenuList!=null&&sysMenuList.size()>0) {
        menuNo = (sysMenuList.get(0)).getMenuNo();
    }
}
%>
<%=(map==null || map.values()==null)?("<html><body><script language='javascript'>alert('异常登录状态,请重新登录!');window.top.location.href='"+webPath+"'</script></body></html>"):"11111111111111"/* (id==null||id.equals(""))?((String)map.get(menuNo)):map.get(id) */ %>