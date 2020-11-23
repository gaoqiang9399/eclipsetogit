<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<html>
	<head>
		<title>转授权/代理用户设置</title>
		<script type="text/javascript" src="<%=webPath %>/creditapp/js/datepopcalendar/selectdate.js"></script>
		<script language="javascript">
		
		function selectUsers() {
			var result = window.showModalDialog("${webPath}/wkf/WkfSystem/selectUser",'选择被代理人','dialogWidth:500px;dialogHeight:400px;resizable=no;scrollbars=no;status:no;help:no;');
			if( result != null && result != "undefined" && result != "" ) {
				var index = result.indexOf(":");
				document.proxyForm.userId.value=result.substr(0, index);
				document.proxyForm.userName.value=result.substr((index+1));
			}
		}
		
		function selectProxyUsers() {
			var result = window.showModalDialog("${webPath}/wkf/WkfSystem/selectUser",'选择代理人','dialogWidth:500px;dialogHeight:400px;status:no;');
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
					<form id="${webPath}/system/addProxy" name="proxyForm" action="${webPath}/wkf/System/addProxy" method="post">
						<!--table -->
						<table id="" width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w" title="">
							<tr>
								<td  colspan="4" align="center" class="bartitle" >转授权/代理用户设置</td>
							</tr>
							<tr>
								<td width="10%" align="right" class="tdlable" >被代理人&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="被代理人" name="userId"  onclick="selectUsers()"  dataType="0" mustinput="1" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
									<input type="button" value="选择" onclick="selectUsers()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
								</td>
								<td width="10%" align="right" class="tdlable" >被代理人姓名&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="被代理人姓名" name="userName"   dataType="0" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
								</td>
							</tr>
							<tr>
								<td width="10%" align="right" class="tdlable" >代理人&nbsp</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="代理人" name="proxyUser"" onclick="selectProxyUsers()" dataType="0" mustinput="1" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
									<input type="button" value="选择" onclick="selectProxyUsers()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
								</td>
								<td width="10%" align="right" class="tdlable" >代理人姓名&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="代理人姓名" name="proxyUserName"   dataType="0" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">
								</td>
							</tr>
							<tr>
								<td width="10%" align="right" class="tdlable" >开始时间&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="开始时间" name="startTime" onclick="selectCalendar(startTime,'<%=imgDir %>');return false;" dataType="6" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="datelogo"></td>
								<td width="10%" align="right" class="tdlable" >结束时间&nbsp;</td>
								<td width="40%" align="left" class="tdvalue" ><input type="text" title="结束时间" name="endTime"  onclick="selectCalendar(endTime,'<%=imgDir %>')" dataType="6" mustinput="0" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" class="datelogo"></td>
							</tr>
							<tr>
								<td width="10%" align="right" class="tdlable" >代理业务&nbsp</td>
								<td width="90%" align="left" class="tdvalue" colspan="3"><input type="text" title="代理业务" name="process" onclick="selectProcess()" dataType="0" mustinput="" readonly onBlur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" size="40">
									<input type="button" value="选择" onclick="selectProcess()" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
								</td>
							</tr>
						</table>
						<!--table end -->
						<div class="from_btn">
						  	<input type="submit" value="保存" onclick="return submitJsMethod(this.form, '')" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
							<input type="button" value="返回" onclick="javascript:window.location='${webPath}/wkf/System/listProxy'" onmousemove='this.className="btn_80d"'onmouseout='this.className="btn_80"'class="btn_80"/>
					 	 </div>
					</form>
				</div>
				<!--con end-->
			</div>
 
		</div>
	</body>
</html>
