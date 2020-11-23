<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String templateNo = request.getParameter("templateNo");
	String filename = request.getParameter("filename");
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>模板保存后修改模板信息</title>
		<script type="text/javascript" src="${webPath}/component/demo/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript">
			var templateNo = '<%=templateNo%>';
			var filename = '<%=filename%>';
			var id = '${param.id}';
			var waterId = '${param.waterId}';
			var pageOffice = '${param.pageOffice}';
			var opName = '${param.opName}';
			var orgNo = '${param.orgNo}';
			var orgName = '${param.orgName}';
			var userId = '${param.userId}';
			$(function(){
				 $.ajax({
					 url:webPath+"/mfSysTemplate/updateTemplateNameAjax",
						type:"post",
						data:{"orgNo":orgNo,"orgName":orgName,"userId":userId,"opName":opName,"templateNo":templateNo,"templateNameEn":filename,"pageOffice":pageOffice,"waterId":waterId,"modelId":id},
						async: false,
						dataType:"json",
						error:function(){alert('error')},
						success:function(data){
							window.external.close();//关闭pageOfficeLink窗口
						}
					});
			})
		</script>
	</head>
	<body class="body_bg">

	</body>
</html>