<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@page import="app.creditapp.nmd.entity.WorkCalendar"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/pagetag.tld" prefix="pageC"%>
<%@ taglib uri="/WEB-INF/tld/dict.tld" prefix="dict"%>
<%
	String flag = request.getParameter("flag");
	WorkCalendar workCalendar=(WorkCalendar)request.getAttribute("calendar");
	String eventime=workCalendar.getEvent_time();
	String warntime=workCalendar.getWarn_time();
	String name1="";
	String name2="";
	String name3="";
	String name4="";
	if(eventime!=null){
	String[] even=eventime.split(":");
	 name1=even[0];
	 name2=even[1];
	}
	if(warntime!=null){
	String[] even=warntime.split(":");
	 name3=even[0];
	 name4=even[1];
	}
	System.out.print("========"+name1+"======"+name2);
	if (flag == null) {
		flag = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<title></title>
		<base target="_self">
		<link rel="stylesheet"
			href="${webPath}/creditapp/css/style.css"
			type="text/css" />
		<script
			src="${webPath}/creditapp/js/jquery-1.1.3.1.pack.js"
			type="text/javascript"></script>
		<script
			src="${webPath}/creditapp/js/jquery.tabs.pack.js"
			type="text/javascript"></script>
		<script type="text/javascript">$(function() {$('#container-10').tabs();});</script>
		<link rel="stylesheet"
			href="${webPath}/creditapp/css/jquery.tabs.css"
			type="text/css" media="print, projection, screen">
		<link rel="stylesheet"
			href="${webPath}/creditapp/css/jquery.tabs-ie.css"
			type="text/css" media="projection, screen">
		<script src="${webPath}/creditapp/js/fpopdate.js"
			type="text/javascript"></script>
		<script src="${webPath}/creditapp/js/validate.js"
			type="text/javascript"></script>
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
	</head>

	<body leftmargin="0" topmargin="0" onload="time();">
		<div id="man_zone">
			<div id="container-10">
				<div>
					<ul class="tabs-nav">
						<li>
							<a href="#fragment-25"><span>日程填报</span>
							</a>
						</li>
					</ul>
				</div>
				<br>
				<div>
					<div id="fragment-25" class="tabs-container" />
						<form name="operform"
							action="WorkCalendarActionInsertOrUpdate.action" method="post"
							theme="simple" validate="true">
							<input type="hidden" name="calendar.calendar_no" value="${calendar.calendar_no }"/>
							<table class="tableStyle04" align="center">
								<tr>
									<td align="right" width="25%">
										记事日期
									</td>
									<td align="left">
										<input  size="10" maxlength="8" date="true" emptyok="false" alt="记事日期" name="calendar.make_date" value="${calendar.make_date }"/>
										<img style="CURSOR: hand"
											src="${webPath}/creditapp/pageframe/images/calendar.gif"
											width="16" height="16" alt="date" align="absMiddle"
											onClick="fPopUpCalendarDlg('calendar.make_date');return false">
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" width="25%">
										记事人
									</td>
									<td align="left">
										<input size="10" maxlength="10" emptyok="false" alt="记事人" name="make_man" value="${calendar.make_man }"/>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" width="25%">
										标题
									</td>
									<td align="left">
									<input  size="80" maxlength="80" emptyok="false" alt="标题" name="title" value="${calendar.title }"/>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" width="25%">
										事件类型
									</td>
									<td align="left">
										<input type="radio" name="calendar.event_type" value="1" checked="checked" />工作计划
										<input type="radio" name="calendar.event_type" value="2" />日程安排
										<input type="radio" name="calendar.event_type" value="3" />工作记事
										<input type="radio" name="calendar.event_type" value="4" />备忘录
										<input type="radio" name="calendar.event_type" value="5" />个人日程
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" width="25%">
										事件日期
									</td>
									<td align="left">
									<input size="10" maxlength="8" date="true" emptyok="false" alt="事件日期" name="event_date" value="${calendar.event_date }"/>
										<img style="CURSOR: hand"
											src="${webPath}/creditapp/pageframe/images/calendar.gif"
											width="16" height="16" alt="date" align="absMiddle"
											onClick="fPopUpCalendarDlg('calendar.event_date');return false">
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="text" name="name1" size="5" maxlength="5"
											emptyok="false" value="08" />
										:
										<input type="text" name="name2" size="5" maxlength="5"
											value="00" />
										时间按24小时制
									</td>
									<td></td>
									<td></td>
								</tr>
									<input type="hidden" name="calendar.event_time" value="${calendar.event_time }"/>
								<tr>
									<td align="right" width="25%">
										事件说明
									</td>
									<td align="left">
										<textarea rows="5" cols="80" maxlength="100" emptyok="false" alt="事件说明" name="calendar.event_desc"></textarea>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" width="25%">
										提醒设置
									</td>
									<td align="left">
									 <c:if test="${calendar.warn_setup==1 }">
                                   <input type="radio" name="calendar.warn_setup" value = "1" checked/>不提醒<br>
                                    <input type="radio" name="calendar.warn_setup" value = "2" />当天提醒
                                    </c:if>
                                    <c:if test="${calendar.warn_setup==2 }">
                                     <input type="radio" name="calendar.warn_setup" value = "1" />不提醒<br>
                                    <input type="radio" name="calendar.warn_setup" value = "2" checked/>当天提醒
                                    </c:if>
                                   <c:if test="${calendar.warn_setup!=2 && calendar.warn_setup!=1 }">
                                    <input type="radio" name="calendar.warn_setup" value = "1" checked/>不提醒<br>
                                    <input type="radio" name="calendar.warn_setup" value = "2" />当天提醒
                                    </c:if>
                                    </td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td align="right" width="25%">
										提醒日期
									</td>
									<td align="left">
										<input type="text" size="10" maxlength="8" date="true" alt="提醒日期" name="calendar.warn_date" value="${calendar.warn_date }" />
										<img style="CURSOR: hand"
											src="${webPath}/creditapp/images/calendar.gif"
											width="16" height="16" alt="date" align="absMiddle"
											onClick="fPopUpCalendarDlg('calendar.warn_date');return false">
										&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="text" name="name3" size="5" maxlength="5"/>
										:
										<input type="text" name="name4" size="5" maxlength="5" />
										时间按24小时制
									</td>
									<td></td>
									<td></td>
								</tr>
								<input type="hidden" name="calendar.warn_time" />
								<tr>
									<td align="right" width="25%">
										提醒终止
									</td>
									<td align="left">
									<input type="text" size="10" maxlength="8" date="true" alt="提醒终止" name="calendar.warn_stop" value="${calendar.warn_stop }" />
											<img style="CURSOR: hand"
											src="${webPath}/creditapp/images/calendar.gif"
											width="16" height="16" alt="date" align="absMiddle"
											onClick="fPopUpCalendarDlg('calendar.warn_stop');return false">
									</td>
									<td></td>
									<td></td>
								</tr>
								<input type="hidden" name="calendar.end_sts" />
								<tr>
									<td align="right" width="25%">
										备注
									</td>
									<td align="left">
										<textarea rows="3" cols="80" maxlength="100" alt="备注" name="calendar.filler"></textarea>
									</td>
									<td></td>
									<td>&nbsp;</td>
								</tr>
							</table>
							<div align="center" style="margin-top: 10px">
								<input type="button" name="save" value="保存"
									onclick="func_submit(this.form)" class="submit_input" />
								<%
									if ("1".equals(flag)) {
								%>
								<input type="button" name="back" value="返回"
									onclick="javascrip:self.close();" class="comeback_input" />
								<%
									} else {
								%>
								<input type="button" name="back" value="返回"
									onclick="javascrip:history.back();" class="comeback_input" />
								<%
									}
								%>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script language="javascript">
function func_submit(frm){
    if(!check_all(frm)){
        return false;
    }
    var temp=document.getElementsByName("calendar.warn_setup"); 
    var setup="";
	for (i=0;i<temp.length;i++){
     	if(temp[i].checked) 
      	{
     		setup= temp[i].value;
       	} 
	} 
	if(setup=="2"){
   		var date=document.all("calendar.warn_date").value;
   		if(date.length==0){
   			alert(top.getMessage("NOT_FORM_EMPTY", "提醒日期"));
   			return false;
   		}
   		var ti=document.all("name3").value;
   		if(ti.length==0){
   			alert(top.getMessage("NOT_FORM_EMPTY", "时间"));
   			return false;
   		}
    }
    if(document.all("name1").value.length>0){
    	if(document.all("name2").value.length==0){
   			document.all("name2").value="00";
    	}
    	if(document.all("name1").value.length==1){
    		document.all("name1").value="0"+document.all("name1").value;
    	}
     	document.all("calendar.event_time").value=document.all("name1").value+":"+document.all("name2").value;
    	//alert(document.all("calendar.event_time").value);
	}
	if(document.all("name3").value.length>0){
    	if(document.all("name4").value.length==0){
   			document.all("name4").value="00";
    	}
		document.all("calendar.warn_time").value=document.all("name3").value+":"+document.all("name4").value;
    	//alert(document.all("calendar.warn_time").value);
    }
  	frm.submit();
  	if("<%=flag %>"!=3){
  		alert("成功创建一条记录")
  	}
  	// self.close();
  	window.close();
}
function time(){
	if("<%=name1 %>".length!=0){
		document.all("name1").value="<%=name1 %>";
		document.all("name2").value="<%=name2 %>";
		document.all("name3").value="<%=name3 %>";
		document.all("name4").value="<%=name4 %>";
	}
}
</script>
</html>