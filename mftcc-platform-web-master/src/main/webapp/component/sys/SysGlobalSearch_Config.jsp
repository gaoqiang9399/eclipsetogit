<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="../include/message.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>全局搜索配置</title>
		<style type="text/css">
			.config_content .config_form{
				width: 20%;
				float:left;
			}
			.config_content .config_table{
				width: 80%;
				float:left;
			}
			.table_content .ls_list tr {
			    line-height: 26px;
			}
			.table_content .ls_list td,.form_content table td{
				font-size: 12px;
			}
			.table_content .ls_list thead th {
			    background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
			    border: medium none;
			    color: #7e9abb;
			    font-size: 12px;
			    font-weight: bold;
			}
			.table_content .ls_list tr:nth-child(2n) {
			    background: #f7f8fc none repeat scroll 0 0;
			}
			.table_content .ls_list tr:nth-child(2n) {
			    background: #f7f8fc none repeat scroll 0 0;
			}
		</style>
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<script type="text/javascript">
			
			
		</script>
	</head>
	<body>
		<div class="config_content">
			<div class="config_form">
				<div class="form_content">
					<dhcc:bigFormTag property="formsys9901" mode="query"/>
					
				</div>
			</div>
			<div class="config_table">
				<div class="table_search" style="height: 100px;"> 
				
				</div>
				<div class="table_content">
					<dhcc:tableTag property="tablesys9901" paginate="sysGlobalSearchList" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
	</body>
</html>