<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String id = request.getParameter("id");
	String filename = request.getParameter("filename");
	String returnUrl = request.getParameter("returnUrl");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>模板保存后修改模板信息</title>
		<script type="text/javascript">
			var id = '<%=id%>';
			var filename = '<%=filename%>';
			var returnUrl = '<%=returnUrl%>';
			$(function(){
				 $.ajax({
					 	url:webPath+"/mfTemplateModelConfig/updateTemplateModelConfigAjax",
						type:"post",
						data:{"id":id,"templateNameEn":filename},
						async: false,
						dataType:"json",
						error:function(){alert('error')},
						success:function(data){
							var w=window.open("","_self","");
							w.location=returnUrl.replace(/\|and\|/g,"&");//解决某些浏览器不能执行window.open问题
						}
					});
			})
		</script>
	</head>
	<body class="body_bg">

	</body>
</html>