<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="app.component.nmd.entity.WorkCalendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="app.base.*"%>
<%@page import="app.component.nmd.entity.ValueBean"%>
<%
List<WorkCalendar> list1=(ArrayList<WorkCalendar>)request.getAttribute("list");
List<ValueBean> weeklist=(ArrayList<ValueBean>)request.getAttribute("weeklist");
String firstday=(String)request.getAttribute("firstday");
String lastday=(String)request.getAttribute("lastday");
Integer fday=Integer.parseInt(firstday);
Integer lday=Integer.parseInt(lastday);
String daytime=request.getParameter("riqi").substring(0, 8);
String makeman=User.getRegNo(request);
String status="1";
String wait=request.getParameter("wait");//是否显示待办
 %>
<html>
  <head>
      <title> </title>
  </head>
<body leftmargin="0" topmargin="0" onload="check();">
	<div class="right_bg">
<div class="right_w">
    <form method="post" theme="simple" validate="true" name="cms_form" action="${pageContext.request.webPath}/workCalendar/findByPage">
    <table  id="tab1" align="center" width="100%"  border= "1 "   cellpadding= "0 "   cellspacing= "1 ">
          <thead>
         <tr align="center" > 
         <td  align="center"><br><table align="right" width="70%">
               <tr><td>
								<div id="navRght">
									<span class="hbmenu"><a
										href="${pageContext.request.webPath}/workCalendar/monthlist?wait=<%=wait %>"
										><img
												src="component/nmd/images/calendarm.gif" align="absmiddle"> 月</a> </span> |
									
									<span class="hbmenu"><a
										href="${pageContext.request.webPath}/workCalendar/weeklist?firstday=<%=firstday %>&lastday=<%=lastday %>&riqi=<%=daytime %>&wait=<%=wait %>" 
										style='font-weight: 600; color: #f00;font-size: 22'><img
												src="component/nmd/images/calendarw.gif" align="absmiddle"> 周</a> </span> |
												 
									<span class="hbmenu"><a
										href="${pageContext.request.webPath}/workCalendar/daylist?firstday=<%=firstday %>&lastday=<%=lastday %>&riqi=<%=daytime %>&wait=<%=wait %>"><img
												src="component/nmd/images/calendard.gif" align="absmiddle"> 日</a> </span> |
								<input type='checkbox' id='QTV' onclick='javascript:wait();'>
								<label for='QTV'>
									显示待办
								</label>
								</div>
							</td>
            </tr></table>
        </thead>
    </table>
    <table class="bartable" id="tab" align="center" width="100%"  border= "1 "   cellpadding= "0 "   cellspacing= "1" >

        <tr  class="bartop">
        <td width="10%">
						计划时间
					</td>
			 <td >
						计划
					</td>		
            </tr>
        <tbody>
            <%
            for(ValueBean week  : weeklist){
            %>
            <tr>
            <td class="TDstyle01"><%=week.getName1() %><br>
             <%=week.getName2() %>
            <br><a href="javascript:add('<%=daytime %>','<%=makeman %>','<%=status %>');">添加计划</a></td>
              <td class="TDstyle01">
              <div align="left" style="position:relative;height:44;width:120;overflow:auto;">
              <%
              for(WorkCalendar workCalendar : list1){
              if(workCalendar.getMakeDate().equals(week.getName1())){
               %>
               <a href="javascript:calend('<%=workCalendar.getCalendarNo() %>');">
               <%
               if("1".equals(workCalendar.getEndSts())){
                %>
               <img src="component/nmd/images/page.gif" align="absmiddle">
               <%
               }else{
              %>
              <img src="component/nmd/images/page2.gif" align="absmiddle">
              <%
              }
               %>
                <%=workCalendar.getTitle() %>
             </a><br>
               <%
               }
               }
                %></div>
             </td>
               </tr>
            <% 
            }
            %>
             
              </tbody>
      </table>
    </form>
    </div>
    <form name="operform" action="#" method="post">
    <input type="hidden" name="calendar.calendar_no"/>
    <input type="hidden" name="flag" value="1"/>
     </form>
</body>
<script type="text/javascript">
var s="<%=request.getParameter("wait")%>";
	if(s=='1'){
	document.all("QTV").checked=true;
	}
      function add(a,b,c){
     window.open("${pageContext.request.webPath}/workCalendar/input","window","status:no;help:no;border:thin;statusbar:no,left=200,top=30,resizable=yes,width=1000,height=420");
     self.location.href = self.location;
      }
      function calend(a){
     // alert(a);
     /// document.all("calendar.calendar_no").value=a;
       //document.operform.action = '${webPath}/calendar/getById';
    //  document.operform.submit();
       window.showModalDialog("${pageContext.request.webPath}/workCalendar/getById?calendar_no="+a+"&flag=1","_self","dialogWidth=950;dialogHeight=600;status:no;center:yes;help:no;minimize:no;maximize:no;border:thin;statusbar:no");
      }
       function check(){
        var wait=document.all("QTV");
         var w="<%=wait %>";
         if(w==1){
         wait.checked=true;
         }
         }
          function wait(){
      var wait=document.all("QTV");
      var z
          if (wait.checked)
          {
           wait.checked=true;
           z="1";
          }
        else
          {
           wait.checked=false;
           z="0";
           }
           window.open("${pageContext.request.webPath}/workCalendar/weeklist?firstday=<%=firstday %>&lastday=<%=lastday %>&riqi=<%=daytime %>&wait="+z,"_self");
         }
</script>
</html>
