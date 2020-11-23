<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="app.component.report.entity.MfCountByCus"%>
<% 
	List<MfCountByCus> list = new ArrayList<MfCountByCus>();
	list = (List)request.getAttribute("list");
	Gson gson = new Gson(); 
	String json = gson.toJson(list);
	String reportUrl = String.valueOf(request.getAttribute("reportUrl"));
	String repPath = "/report_new/report/rbc/designJavabean.jsp?reporttype=D&uid=ebc0130aa2c0280f130ace6e67ff8821";
 %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html> 
	<head>
		<title>列表</title>
	</head>
	<style>
		table{border-collapse: collapse;background:#ffffff;}
		.dynamic{border:1px solid #000;width:90%;}
		.dynamic .rtitle td{text-align:center;vertical-align:middle;mso-pattern:auto;font-size:14pt;font-weight:700;font-style:normal;font-family:"Microsoft YaHei UI","sans-serif";border-bottom:2px solid #cdcdcd;height: 23.25pt;}
		
		.dynamic .rhead td{ text-align:left;vertical-align:middle;font-size:12pt;font-weight:400;font-style:normal;font-family:"Microsoft YaHei UI","sans-serif";border-bottom:1px solid #bfbfbf;overflow:hidden;}
		
		.dynamic tbody td{height: 26.45pt;color: rgb(0, 0, 0);font-family: "Microsoft YaHei UI";font-size: 10pt;font-weight: 400;border-bottom:1px solid #bfbfbf;text-decoration: none;text-line-through: none;padding-left:3px;padding-right:6px;}
		tfoot td{ text-align:left;vertical-align:middle;padding-left:3px;overflow:hidden;}
	</style>
	 <script src="../lib/jquery.min.js"></script>
	<script type="text/javascript">
		function search(){
			ifmreport.getSearch("1","","");
		}
	</script>
	<body class="body_bg">
	
	<table style="width: 100%; height: 100%; table-layout: fixed;" border="0" cellspacing="0" cellpadding="0">
<tbody>
<tr><td style="overflow: auto;">
<table width="100%" height="100%" style="table-layout: fixed;"><tbody><tr><td><div style="-ms-overflow-x: auto;">
<textarea rows="1" cols="1" id="list" style="display:none"><%=json %></textarea>
</div></td></tr></tbody></table>
</td></tr> 
<tr><td valign="top" height="100%"><div style="width:100%;height:100%;z-index:-1;"><iframe id="ifmreport" name="ifmreport" src="<%=repPath %>" frameborder="0" style="width: 100%; height: 100%;"></iframe></div></td></tr>
</tbody></table>
	</body>
</html>