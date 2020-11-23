<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../include/pub.jsp"%>
<%
request.setCharacterEncoding("UTF-8");
//String tname = new String(request.getParameter("tname").getBytes(),"UTF-8");
String tname = request.getParameter("tname");
String tcode = request.getParameter("tcode");
String pcode = request.getParameter("pcode");
String lvl = request.getParameter("lvl");
String url = request.getParameter("url");
if(tname==null){
	tname = "";
}
boolean isAdd = false;
if("".equals(tname)){
	isAdd = true;
}
%>
<html>
<head>
<title><%if(isAdd){ %>菜单新增<%}else { %>菜单编辑<%} %></title>
<script type='text/javascript' src='${webPath}/creditapp/sys/js/jquery.js'></script>
<script	type="text/javascript">
function returnVal(){
	var tname = $("#tname").val();
	var tcode = $("#tcode").val();
	var pcode = $("#pcode").val();
	var lvl = $("#lvl").val();
	var url = $("#url").val();
	if(tname=="" || tcode=="" || pcode=="" || lvl==""){
		alert(top.getMessage("NOT_FORM_EMPTY", "菜单要素"));
		return;
	}
	var reg = /[\d]{1}/;
	if(!reg.test(lvl)){
		alert("请正确输入菜单等级!");
		return;
	}<%if(isAdd){ %>
	$.post(webPath+"/sysMenu/insertMenu",{tcode:tcode,tname:encodeURI(tname),pcode:pcode,lvl:lvl,url:url,rdm:Math.random()},function(data){
		if($.trim(data)!="success"){
			alert($.trim(data));
			return;
		}else{
			alert("新增菜单成功!");
			if(lvl=="3"){
				var result = "<li class='doc-last' id='"+tcode+"'><span class='file' title='"+tcode+"' lvl='"+lvl+"'"+" pid='"+pcode+"'"+" url='"+url+"'>"+tname+"</span></li><li class='line-last'></li>";
			}else if(lvl=="2"){
				var result = "<li class='folder-close-last' id='"+tcode+"'><img src='${webPath}/creditapp/sys/images/spacer.gif'/><span class='file' title='"+tcode+"' lvl='"+lvl+"'"+" pid='"+pcode+"'"+" url='"+url+"'>"+tname+"</span><ul><li class='line-last'></li></ul></li>";
			}
			closeWin(result);
		}
	});<% }else { %>
	$.post(webPath+"/sysMenu/updateMenu",{tcode:tcode,tname:encodeURI(tname),pcode:pcode,lvl:lvl,url:url,rdm:Math.random()},function(data){
		if($.trim(data)!="success"){
			alert($.trim(data));
			return;
		}else{
			alert("修改菜单成功!");
			closeWin(tname);
		}
	});<% } %>
}
function closeWin(val){
	window.returnValue = val;
	window.close();
}
</script>

<style type="text/css">
input{ vertical-align:middle; background:#fff; border:1px solid #7F9DB9; font-size:12px; padding:0px 0px; color:#333333; height:21px;line-height:21px; margin:0px 5px;}
button{ margin:0px 3px;_margin-bottom:10px;}
td {
	margin: 0;
	padding: 0;
	border: 0;
	font-weight: inherit;
	font-style: inherit;
	vertical-align: middle;
	text-decoration:none;
	font-size:100%;
}
</style>

</head>

<body style="text-align:center;">
<div style="width:100%; text-align: center;">
	<table width='100%' border='0' cellspacing='0' cellpadding='0'>
		<tr bgcolor='#9CBAF7'>
		<td colspan='2' align="center"><font size='3px'><%if(isAdd){ %>菜单新增<%}else { %>菜单编辑<%} %></font></td>
		<tr><td colspan='2' align='center'>
			<table id='cr' width='90%'>
				<tr><td colspan='2'>&nbsp;&nbsp;</td></tr>
				<tr><td align='right'>菜单编号：</td><td><input type="text" id='tcode' name="tcode" value="<%=tcode %>" <%if(!isAdd){ %>readonly="readonly"<%} %>/></td>
				<tr><td align='right'>父菜单编号：</td><td><input type="text" id='pcode' name="pcode" value="<%=pcode %>" readonly="readonly"/></td>
				<tr><td align='right'>菜单名称：</td><td><input type="text" id='tname' name="tname" value="<%=tname %>"/></td>
				<tr><td align='right'>菜单级别：</td><td><input type="text" id='lvl' name="lvl" value="<%=lvl %>" readonly="readonly"/></td>
				<tr><td align='right'>菜单URL：</td><td><input type="text" id='url' name="url" value="<%=url %>" size="35"/></td>
				</tr><tr><td colspan='2'>&nbsp;&nbsp;</td></tr>
				<tr><td align='center' colspan='2'>
					<input type='button' onclick="returnVal();" value='确定'/>&nbsp;&nbsp;
					<input type='button' onclick="closeWin('fail');" value='取消'/>
				</td></tr>
			</table>
		</td></tr>
	</table>
</div>
</body>
</html>
