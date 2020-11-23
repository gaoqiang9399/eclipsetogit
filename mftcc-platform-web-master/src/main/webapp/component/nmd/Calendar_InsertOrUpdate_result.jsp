<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/dict.tld" prefix="dict" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String flag=request.getParameter("flag");
if(flag==null){
flag="";
}
System.out.println("---------flag"+flag);
%>
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <html>
    <head>
    <base target="_self">
     <link rel="stylesheet" href="${webPath}/creditapp/css/style.css" type="text/css"/>
     <script src="${webPath}/creditapp/js/jquery-1.1.3.1.pack.js" type="text/javascript"></script>
     <script src="${webPath}/creditapp/js/jquery.tabs.pack.js" type="text/javascript"></script>
     <script type="text/javascript">$(function() {$('#container-10').tabs();});</script>
     <link rel="stylesheet" href="${webPath}/creditapp/css/jquery.tabs.css" type="text/css" media="print, projection, screen">
     <link rel="stylesheet" href="${webPath}/creditapp/css/jquery.tabs-ie.css" type="text/css" media="projection, screen">
    </head>
<body leftmargin="0" topmargin="0">
<div id="man_zone">
<div id="fragment-25" class="tabs-container" />
<form name="operform" method="post" theme="simple" validate="true">
    <table class="tableStyle04" id="tab1" align="center">
      <tr><td align="right">��־���</td><td align="left">${calendar.calendar_no }</td><td></td><td></td></tr>
      <tr><td align="right">��������</td><td align="left">${calendar.make_date }</td><td></td><td></td></tr>
      <tr><td align="right">������</td><td align="left">${calendar.make_man }</td><td></td><td></td></tr>
      <tr><td align="right">����</td><td align="left">${calendar.title }</td><td></td><td></td></tr>
      <tr><td align="right">�¼�����</td><td align="left">
      <dict:translate name="calendar.event_type" ddname="EVENT_TYPE" value='${calendar.event_type }'/>
      </td><td></td><td></td></tr>
      <tr><td align="right">�¼�����</td><td align="left">${calendar.event_date }</td><td></td><td></td></tr>
      <tr><td align="right">�¼�ʱ��</td><td align="left">${calendar.event_time }</td><td></td><td></td></tr>
      <tr><td align="right">�¼�˵��</td><td align="left">${calendar.event_desc }</td><td></td><td></td></tr>
      <tr><td align="right">��������</td><td align="left">
      <c:if test="${calendar.warn_setup ==1 }">
      ������
      </c:if>
      <c:if test="${calendar.warn_setup ==2 }">
      ��������
      </c:if>
      </td><td></td><td></td></tr>
      <tr><td align="right">��������</td><td align="left">${calendar.warn_date}</td><td></td><td></td></tr>
      <tr><td align="right">����ʱ��</td><td align="left">${calendar.warn_time}</td><td></td><td></td></tr>
      <tr><td align="right">������ֹ</td><td align="left">${calendar.warn_stop}</td><td></td><td></td></tr>
      <tr><td align="right">���״̬</td><td align="left">
      <c:if test="${calendar.end_sts ==1 }">
      δ���
      </c:if>
      <c:if test="${calendar.end_sts ==2 }">
      ���
      </c:if>
      </td><td></td><td></td></tr>
      <tr><td align="right">��ע</td><td align="left">${calendar.filler }</td><td></td><td></td></tr>
     
      </table>
</form>
<div align="center" width="100%">
<%
if("1".equals(flag)){
 %>
 <input type="button" value="����" onclick="javascript:self.close();" class="comeback_input">
 <%
 }else if("2".equals(flag)){
  %>
<input type="button" value="����" onclick="javascript:history.back();" class="comeback_input">
<%
}else{
 %>
 <input type="button" value="����" onclick="func_back();" class="comeback_input">
 <%
 }
  %>
<form name="openform" action="#"  method="post">
</form>
</div>
</div>
</div>
</body>
<script language="javascript">
function func_back(){
    document.openform.action ="${webPath}/workCalendar/findByPage"
    document.openform.submit();
}
</script>
</html>
