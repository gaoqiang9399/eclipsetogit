<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.workflow.pvm.internal.task.WFProxy" %>
<%@page import="com.dhcc.workflow.Format" %>
<%@page import="com.dhcc.workflow.WF" %>
<%@page import="com.dhcc.workflow.pvm.internal.task.WFProxyProcess"%>
<%@page import="app.util.DateUtil"%>
<%
	String contextPath = request.getContextPath();
	String imgDir = webPath + "/creditapp/js/datepopcalendar/image/";
%>

<script type="text/javascript" src='${webPath}/include/uior_val.js'> </script>
<script type="text/javascript" src='${webPath}/include/xcqi_cal.js'> </script>
<script type='text/javascript' src='${webPath}/creditapp/js/datepopcalendar/selectdate.js'></script>
<script type='text/javascript' src='${webPath}/creditapp/js/datepopcalendar/dateVerdict.js'></script>
<script type="text/javascript" src="${webPath}/creditapp/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${webPath}/creditapp/js/pop.js"></script>
<script type="text/javascript" src="${webPath}/creditapp/js/idvalidate.js"></script>
<script type='text/javascript' src='${webPath}/include/base.js'></script>
<script type='text/javascript' src='${webPath}/include/idCheck.js'></script>
<link rel="stylesheet" href="${webPath}/include/screencore.css" type="text/css" />
<script type='text/javascript' src='${webPath}/include/tablelistshowdiv.js'></script>
<script type='text/javascript' src='${webPath}/include/improveGrid.js'></script>
<link rel="stylesheet" href="${webPath}/creditapp/pageframe/css1/<%=request.getSession().getAttribute("color") %>.css" type="text/css" />

<% 
	Object object = request.getAttribute(WF.PARAM_OBJECT_NAME);
	WFProxy proxy = null;
	boolean isSaved = true;
	if( object == null || !(object instanceof WFProxy) ) {
		isSaved = false;
		proxy = new WFProxy();
	} else {
		proxy = (WFProxy) object;
	}
	String processkeys = "";
	Set<WFProxyProcess> set = proxy.getProcessKeys();
	if(set != null){
		Iterator<WFProxyProcess> it = set.iterator();
		while(it.hasNext()){
			WFProxyProcess pp = it.next();
			processkeys += "," + pp.getProcessKey();
		}
	}
	if(processkeys != ""){
		processkeys = processkeys.substring(1);
	}
%>

<html>
	<head>
		<title>转授权/代理用户设置</title>
		<script language="javascript">
		
		function selectUsers() {
			var result = window.showModalDialog("<%=webPath %>/wkf/WkfSystem/selectUser",'选择被代理人','dialogWidth:500px;dialogHeight:400px;resizable=no;scrollbars=no;status:no;help:no;');
			if( result != null && result != "undefined" && result != "" ) {
				var index = result.indexOf(":");
				document.proxyForm.userId.value=result.substr(0, index);
				document.proxyForm.userName.value=result.substr((index+1));
			}
		}
		
		function selectProxyUsers() {
			var result = window.showModalDialog("<%=webPath %>/wkf/WkfSystem/selectUser",'选择代理人','dialogWidth:500px;dialogHeight:400px;status:no;');
			if( result != null && result != "undefined" && result != "" ) {
				var index = result.indexOf(":");
				document.proxyForm.proxyUser.value=result.substr(0, index);
				document.proxyForm.proxyUserName.value=result.substr((index+1));
			}
		}
		
		function selectProcess() {
			var result = window.showModalDialog("<%=webPath %>/wkf/WkfSystem/selectBusiness",'选择代理任务','dialogWidth:500px;dialogHeight:400px;status:no;');
			if( result != null && result != "undefined" && result != "" ) {
				document.proxyForm.process.value = result;
			}
		}
</script>
	</head>
	<body class="body_bg">
		<div class="right_bg">
			<div class="right_w">
				<!--con -->
				<div class="from_bg">
					<form id="${webPath}/system/updateProxy" name="proxyForm" action="${webPath}/wkf/System/updateProxy" method="post">
						<!--table -->
						<table id="" width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w" title="">
							<tr>
								<td  colspan="4" align="center" class="bartitle" >转授权/代理用户设置</td>
							</tr>
							<tr>
								<input type="hidden" name="proxyId" value="<%=proxy.getId() %>" />
								<td width="10%" align="right" class="tdlable" >被代理人&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="被代理人" name="userId" value="<%=proxy.getUser() %>" onclick="selectUsers()"  dataType="0" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
									<input type="button" value="选择" onclick="selectUsers()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
								</td>
								<td width="10%" align="right" class="tdlable" >被代理人姓名&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="被代理人姓名" name="userName" value="<%=proxy.getUserName() %>"  dataType="0" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
								</td>
							</tr>
							<tr>
								<td width="10%" align="right" class="tdlable" >代理人&nbsp</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="代理人" name="proxyUser" value="<%=proxy.getProxyUser() %>" onclick="selectProxyUsers()" dataType="0" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
									<input type="button" value="选择" onclick="selectProxyUsers()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
								</td>
								<td width="10%" align="right" class="tdlable" >代理人姓名&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="代理人姓名" name="proxyUserName" value="<%=proxy.getProxyUserName() %>"  dataType="0" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
								</td>
							</tr>
							<tr>
								<td width="10%" align="right" class="tdlable" >开始时间&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="开始时间" name="startTime" value="<%=DateUtil.dateToStr(proxy.getStartTime()) %>" onclick="selectCalendar(startTime,'<%=imgDir %>');return false;" dataType="6" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="datelogo BOTTOM_LINE"></td>
								<td width="10%" align="right" class="tdlable" >结束时间&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="结束时间" name="endTime" value="<%=DateUtil.dateToStr(proxy.getEndTime()) %>" onclick="selectCalendar(endTime, '<%=imgDir %>')" dataType="6" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="datelogo BOTTOM_LINE"></td>
							</tr>
							<tr>
								<td width="10%" align="right" class="tdlable" >代理业务&nbsp</td>
								<td width="90%" align="left" class="tdvalue" colspan="3"><input type="text" title="代理业务" name="process" value="<%=processkeys %>" onclick="selectProcess()" dataType="0" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" size="40">
									<input type="button" value="选择" onclick="selectProcess()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right" class="tdlable" >状态&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" >
									<select name="state" title="状态" mustinput="1" onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
										<option value="enabled" <%=("enabled".equals(proxy.getState())?"selected":"") %> >启用</option>
										<option value="disabled" <%=("disabled".equals(proxy.getState())?"selected":"") %>>停用</option>
									</select>
								</td>
								<td width="10%" align="right" class="tdlable" >&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" >&nbsp;</td>
							</tr>
						</table>
						<!--table end -->
						<div class="from_btn">
						  	<input type="submit" value="保存" onclick="return submitJsMethod(this.form, '')" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
							<input type="button" value="返回" onclick="javascript:window.location='${webPath}/system/listProxy'" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
					 	 </div>
					</form>
				</div>
				<!--con end-->
			</div>
 
		</div>
	</body>
</html>
