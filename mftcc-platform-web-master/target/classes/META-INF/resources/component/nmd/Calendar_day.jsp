<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="app.component.nmd.entity.WorkCalendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="app.base.*"%>
<%
List<WorkCalendar> list1=(ArrayList<WorkCalendar>)request.getAttribute("list");
String daytime=request.getParameter("riqi").substring(0, 8);
String makeman=User.getRegNo(request);
String firstday=(String)request.getParameter("firstday");
String lastday=(String)request.getParameter("lastday");
String status="1";
String wait=request.getParameter("wait");//是否显示待办
System.out.println("----->>>>>>>>>>>"+wait);
List<String> timelist=new ArrayList<String>();//时间的集合；
timelist.add("08:00");
timelist.add("09:00");
timelist.add("10:00");
timelist.add("11:00");
timelist.add("12:00");
timelist.add("13:00");
timelist.add("14:00");
timelist.add("15:00");
timelist.add("16:00");
timelist.add("17:00");
timelist.add("18:00");
timelist.add("19:00");
timelist.add("20:00");
timelist.add("21:00");
timelist.add("22:00");
timelist.add("23:00");

 %>
<html>
  <head>
      <title> </title>
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Cache-Control" content="no-cache">
        <meta http-equiv="Expires" content="0">
  </head>
<body leftmargin="0" topmargin="0" onload="check();">
<div class="right_bg">
<div class="right_w">
    <form method="post" theme="simple" validate="true" name="cms_form" action="${pageContext.request.webPath}/calendar/findByPage">
    <table id="tab1" align="center" width="100%"  border= "1 "   cellpadding= "0 "   cellspacing= "0 "  >
    <thead>
         <tr align="center" bordercolor="#FF0000"> 
               <td  align="center"><br><table align="right" width="70%"><tr><td>
								<div id="navRght">
									<span class="hbmenu"><a
										href="${pageContext.request.webPath}/workCalendar/monthlist?wait=<%=wait %>"
										><img
												src="component/nmd/images/calendarm.gif" align="absmiddle"> 月</a> </span> |
									
									<span class="hbmenu"><a
										href="${pageContext.request.webPath}/workCalendar/weeklist?firstday=<%=firstday %>&lastday=<%=lastday %>&riqi=<%=daytime %>&wait=<%=wait %>"><img
												src="component/nmd/images/calendarw.gif" align="absmiddle"> 周</a> </span> |
												 
									<span class="hbmenu"><a
										href="${pageContext.request.webPath}/workCalendar/daylist?firstday=<%=firstday %>&lastday=<%=lastday %>&riqi=<%=daytime %>&wait=<%=wait %>"
										style='font-weight: 600; color: #f00;font-size: 22'><img
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
    <table class="bartable"  align="center" width="100%"  border= "1 "   cellpadding= "0 "   cellspacing= "1 "  id="tab">
        <tr align="center" class="bartop">
                <th width="10%" >
						计划时间
					</th>
			 <th >
						计划
					</th>	
            </tr>
        <tbody id="tab">
        <%
        for(int i=0;i<timelist.size();i++){
         %>
         <tr >
              <td><%=timelist.get(i) %><br> <a href="javascript:add('<%=daytime %>','<%=makeman %>','<%=status %>');">添加计划</a></td>
              <td><div align="left" style="position:relative;height:44;width:120;overflow:auto;">
              <%
              for(WorkCalendar workCalendar : list1){
              if(workCalendar.getEventTime().substring(0,2).equals(timelist.get(i).substring(0,2))){
               %>
                 <a href="javascript:calend('<%=workCalendar.getCalendarNo() %>');">
                 <%
                 if("1".equals(workCalendar.getEndSts())){
                  %>
                 <img src="${pageContext.request.webPath}/component/nmd/images/page.gif" align="absmiddle">
                 <%
                 }else{
                  %>
                  <img src="${pageContext.request.webPath}/component/nmd/images/page2.gif" align="absmiddle">
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
      window.open("${pageContext.request.webPath}/workCalendar/input?make_date="+a+"&make_man="+b+"&end_sts="+c+"&flag=1"+"&event_date="+a,"window","status:no;help:no;border:thin;statusbar:no,left=200,top=30,resizable=yes,width=1000,height=420");
       self.location.href = self.location;
      }
      function calend(a){
      alert(a);
     /// document.all("calendar.calendar_no").value=a;
       //document.operform.action = 'CalendarAction_getById.action';
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
           window.open("${pageContext.request.webPath}/workCalendar/daylist?firstday=<%=firstday %>&lastday=<%=lastday %>&riqi=<%=daytime %>&wait="+z,"_self");
         }
</script>
</html>
