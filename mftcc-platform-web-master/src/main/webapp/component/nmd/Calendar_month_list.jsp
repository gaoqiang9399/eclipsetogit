<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="app.base.*,app.component.common.*,app.component.entity.*,app.component.nmd.entity.WorkCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	List<WorkCalendar> list = (ArrayList<WorkCalendar>) request.getAttribute("list");
%>
<%!String year;
	String month;%>

<%
	month = request.getParameter("month");
	year = request.getParameter("year");
	String wait; //是否显示待办
	if((String)request.getAttribute("wait")!=null){
		wait=(String)request.getAttribute("wait");
	}
	else{
		wait="";
	}
%>

<html>
	<head>
		<title>日程填报</title>
		<script Language="javascript"> 
			<!-- 
			function changeMonth() 
			{ 
			var mm="${pageContext.request.webPath}/workCalendar/monthlist?month="+document.sm.elements[0].selectedIndex+"&year=" 
			
			+<%=year%>+"&wait="+<%=wait %>; 
			
			window.open(mm,"_self"); 
			
			} 
			-->
	</script>

	</head>

	<%!String days[];%>

	<%
		days = new String[42];

		for (int i = 0; i < 42; i++)

		{

			days[i] = "";

		}
	%>

	<%
		Calendar thisMonth = Calendar.getInstance();
		int today = thisMonth.get(Calendar.DAY_OF_MONTH);
		System.out.println("today==========" + today);
		if (month != null && (!"null".equals(month)))

        {
            thisMonth.set(Calendar.MONTH, Integer.parseInt(month));
        }

		if (year != null && (!"null".equals(year)))

        {
            thisMonth.set(Calendar.YEAR, Integer.parseInt(year));
        }

		year = String.valueOf(thisMonth.get(Calendar.YEAR));
		//System.out.println("year==========" + year);
		month = String.valueOf(thisMonth.get(Calendar.MONTH));
		int mon = Integer.parseInt(month) + 1;
		thisMonth.setFirstDayOfWeek(Calendar.SUNDAY);
		thisMonth.set(Calendar.DAY_OF_MONTH, 1);
		int firstIndex = thisMonth.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println("firstIndex======" + firstIndex);
		int maxIndex = thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		String tmonth = "";
		String ttoday = "";
		if (mon < 10) {
			tmonth = "0" + String.valueOf(mon);
		} else {
			tmonth = String.valueOf(mon);
		}
		if(today<10){
		ttoday = "0" + String.valueOf(today);
		} else {
			ttoday = String.valueOf(today);
		}
		String daynow = year + tmonth + ttoday; //当前日期
		System.out.println("daynow==========" + daynow);
		thisMonth.setTime(DateUtil.parseDate(daynow));
		int dayOfWeek = thisMonth.get(Calendar.DAY_OF_WEEK);
		int day =DateUtil.getDaysOfMonth(daynow); //一个月多少天
		//System.out.println("days===="+day);
		String monthday=year+tmonth+"01";//每个月第一天
		String xingqi=DateUtil.getWeekDay(monthday);//每个月第一天是星期几
		//System.out.println("xingqi===="+xingqi);
		int line=DateUtil.line(xingqi,day);
		//System.out.println("-------line---"+line);
		thisMonth.set(Calendar.DAY_OF_YEAR, thisMonth
				.get(Calendar.DAY_OF_YEAR)
				- dayOfWeek + 1);
		String firstday = DateUtil.getDateTime(thisMonth.getTime());//一周中最第一天
		thisMonth.set(Calendar.DAY_OF_YEAR, thisMonth
				.get(Calendar.DAY_OF_YEAR) + 6);
		String lastday = DateUtil.getDateTime(thisMonth.getTime());
		//System.out.println("11111<<<----" + firstday);
		//System.out.println("11111----" + lastday);
		for (int i = 0; i < maxIndex; i++)

		{

			days[firstIndex + i] = String.valueOf(i + 1);

		}
	%>
	<%
		String brno = User.getRegNo(request);
	%>
	<script type="text/javascript">
	function list(v){
	var m;
	if(v.length==1){
	v="0"+v;
	}
	if("<%=mon %>".length==1){
		m="0"+<%=mon %>;
	}else{
		m=<%=mon %>;
	}
var date=<%=year%>+""+m+""+v;
var brno="<%=brno%>";
var status="1";
window.open("${pageContext.request.webPath}/workCalendar/input?make_date="+date+"&make_man="+brno+"&end_sts="+status+"&flag=1"+"&calendar.event_date="+date,"window","status:no;help:no;border:thin;statusbar:no,left=200,top=30,resizable=yes,width=1000,height=520");
//self.location.href = self.location;
//window.open("${webPath}/calendar/input","_self");
}
	</script>
	<body class="body_bg" >
	<div class="right_bg">
<div class="right_w">
		<FORM name="sm" method="post" action="Calendar_month_list.jsp">

			<%--			&nbsp;&nbsp;&nbsp;<%=year%>年&nbsp;&nbsp;<%=Integer.parseInt(month) + 1%>月--%>
			 <!--  
					<td width=28%>
						<input type=text name="year" value=<%=year%> size=4 maxlength=4>
					</td>

					<td>
						年
					</td>

					<td width=28%>
						<input type=submit value="提交">
					</td>
						</tr>
			</table>
                    -->
			<table id="tab1" align="center" width="100%"  class="searchstyle">
					<tr align="center">
						<td colspan="7" align="center"><br>
						<table align="right" width="70%"><tr><td>
							<div id="navRght">
								<span align="center"> <select name="month" size="1"
										onchange="changeMonth()">
										<option value="1">
											一月
										</option>
										<option value="2">
											二月
										</option>
										<option value="3">
											三月
										</option>
										<option value="4">
											四月
										</option>
										<option value="5">
											五月
										</option>
										<option value="6">
											六月
										</option>
										<option value="7">
											七月
										</option>
										<option value="8">
											八月
										</option>
										<option value="9">
											九月
										</option>
										<option value="10">
											十月
										</option>
										<option value="11">
											十一月
										</option>
										<option value="12">
											十二月
										</option>
									</select> </span>
								<span class="hbmenu"><a
									href="${pageContext.request.webPath}/workCalendar/monthlist?wait=<%=wait %>"
									style='font-weight: 600; color: #f00;font-size: 22'><img
											src="component/nmd/images/calendarm.gif" align="absmiddle">
										月</a> </span> |

								<span class="hbmenu"><a
									href="${pageContext.request.webPath}/workCalendar/weeklist?firstday=<%=firstday%>&lastday=<%=lastday%>&riqi=<%=daynow%>&wait=<%=wait %>"><img
											src="component/nmd/images/calendarw.gif" align="absmiddle">
										周</a> </span> |

								<span class="hbmenu"><a
									href="${pageContext.request.webPath}/workCalendar/daylist?firstday=<%=firstday%>&lastday=<%=lastday%>&riqi=<%=daynow%>&wait=<%=wait %>"><img
											src="component/nmd/images/calendard.gif" align="absmiddle">
										日</a> </span> |
								<input type='checkbox' id='QTV' onclick='javascript:wait();'>
								<label for='QTV'>
									显示待办
								</label>
							</div></td></tr></table>
							<br>
							<br>
						</td>
					</tr>
					</table>
					<table id="tab" align="center" width="100%"  border= "0"   cellpadding= "0 "   cellspacing= "1" class="bartable">
					<tr class="bartop">

						<th width="11%" height="1%" >
							<font color="red">日</font>

						</th>

						<th width="11%" height="1%" >
							一
						</th>

						<th width="11%" height="1%" >
							二
						</th>

						<th width="11%" height="1%" >
							三
						</th>

						<th width="11%" height="1%" >
							四
						</th>

						<th width="11%" height="1%" >
							五
						</th>

						<th width="11%" height="1%" >
							<font color="red">六</font>
						</th>
					</tr>
				
				<tbody id="tab">
					<%
						for (int j = 0; j < line; j++) {
					%>

					<tr>

						<%
							for (int i = j * 7; i < (j + 1) * 7; i++) {
							//	thisMonth.set(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(days[i])));
								Calendar cal = Calendar.getInstance();
								cal.set(Integer.parseInt(year),Integer.parseInt(month),3);
								int indexVal = cal.get(Calendar.DAY_OF_WEEK);
								System.out.println("indexVal==========" + indexVal);
						%>

						<td class="TDstyle100" VALIGN="top" ALIGN="center" height="13%">
							<%
								if ((i - firstIndex + 1) == today) {
							%>
							<font color="red" align="center" size="5"><%=days[i]%></font>
							<%
								} else{
							%>
							<font align="center" size="5"><%=days[i]%></font>
							<%
								}
										if (days[i].length() > 0) {
							%>
							<a href="javascript:list('<%=days[i]%>')"><img
									src="component/nmd/images/docadd.gif" border="0" align="absmiddle">
							</a>
							<br>
						<div align="right" style="position:relative;height:44;width:120;overflow:auto;">  <font>
							<%
								for (WorkCalendar workCalendar : list) {
												if (workCalendar.getMakeDate().substring(6, 8)
														.equals(days[i])
														&& workCalendar.getMakeDate().substring(4,
																6).equals(String.valueOf(tmonth))) {
							%>
							<div align="left" id="div1">
								<font><a
									href="javascript:calend('<%=workCalendar.getCalendarNo()%>');">
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
									<%=workCalendar.getTitle()%></a>
								</font>
							</div>
							<%
								}%>
								
								<%
												if (days[i].length() == 1) {
												String gday="0"+days[i];
													if (workCalendar.getMakeDate().substring(6, 8)
															.equals(gday)
															&& workCalendar.getMakeDate()
																	.substring(4, 6).equals(
																			String.valueOf(tmonth))) {
							%><div align="left">
								<a
									href="javascript:calend('<%=workCalendar.getCalendarNo()%>');">
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
									<%=workCalendar.getTitle()%></a><div>
								
							
							<%
								}
												}%>
												<%
											}
										%></font>
												</div><%}
							%>
						</td>

						<%
							}
						%>
					</tr>

					<%
						}
					%>
				</tbody>
			</table>



		</FORM>

		<script Language="JavaScript"> 
		 var wait=document.all("QTV");
         var w="<%=wait %>";
         if(w==1){
         wait.checked=true;
         }
var s="<%=request.getParameter("wait")%>";
	if(s=='1'){
	document.all("QTV").checked=true;
	}

document.sm.month.options.selectedIndex=<%=month%>; 


 function calend(a){
     // alert(a);
     /// document.all("calendar.calendar_no").value=a;
       //document.operform.action = '${webPath}/calendar/getById';
    //  document.operform.submit();
    	
       window.showModalDialog("${pageContext.request.webPath}/workCalendar/getById?calendar_no="+a+"&view=view&flag=1","_self","dialogWidth=950;dialogHeight=600;status:no;center:yes;help:no;minimize:no;maximize:no;border:thin;statusbar:no");
        //self.location.href = '${webPath}/calendar/monthlist';
          //window.open("${webPath}/calendar/monthlist","_self");
          //window.open("${webPath}/calendar/monthlist")
        //window.setTimeout("on_load()", 3000);
        
      }
      function wait(){
      		var wait=document.all("QTV");
     		var z
		      if (wait.checked){
		           wait.checked=true;
		           z="1";
		      }else{
		           wait.checked=false;
		           z="0";
		      }
	     	 window.open("${pageContext.request.webPath}/workCalendar/monthlist/?wait="+z,"_self");
      }
         function check(){
        
         var wait=document.all("QTV");
         var w="<%=wait %>";
         if(w==1){
         wait.checked=true;
         }
         }
    </script>

	</body>

</html>
