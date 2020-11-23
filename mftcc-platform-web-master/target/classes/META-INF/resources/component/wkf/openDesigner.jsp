<%@ page language="java" pageEncoding="UTF-8"%>
<%
 String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <title>打开流程设计器</title>
  </head>
  <body>
    <script language="javascript" type="text/javascript">
		window.open('${webPath}/workflow/WFDLDesigner.jsp',"_blank");
	</script>
  </body>
</html>
