<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String examHisId = request.getParameter("examHisId");
	String filename = request.getParameter("filename");
	String returnUrl = request.getParameter("returnUrl");
	String templateType = request.getParameter("templateType");
	String examOpNo = request.getParameter("examOpNo");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	String templateId = request.getParameter("templateId");
	String cusNo = request.getParameter("cusNo");
	String docuSaveFlag = request.getParameter("docuSaveFlag");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>模板保存后修改模板信息</title>
		<script type="text/javascript">
			var examHisId = '<%=examHisId%>';
			var filename = '<%=filename%>';
			var returnUrl = '<%=returnUrl%>';
			var templateType = '<%=templateType%>';
			var examOpNo = '<%=examOpNo%>';
			var beginDate = '<%=beginDate%>';
			var endDate = '<%=endDate%>';
			var templateId = '<%=templateId%>';
			var cusNo = '<%=cusNo%>';
			var docuSaveFlag ="1";
			returnUrl = returnUrl+"&examHisId="+examHisId+"&templateType="+templateType+"&examOpNo="+examOpNo+"&beginDate="+beginDate+"&endDate="+endDate+"&cusNo="+cusNo+"&templateId="+templateId+"&docuTemplate="+filename+"&docuSaveFlag="+docuSaveFlag;
			$(function(){
				var w=window.open("","_self","");
				w.location=returnUrl.replace(/\|and\|/g,"&");//解决某些浏览器不能执行window.open问题
				/*  $.ajax({
						url: webPath+"/mfExamineHis/updateDocuTemplateAjax",
						type:"post",
						data:{"examHisId":examHisId,"docuTemplate":filename},
						async: false,
						dataType:"json",
						error:function(){alert('error')},
						success:function(data){
							var w=window.open("","_self","");
							w.location=returnUrl.replace(/\|and\|/g,"&");//解决某些浏览器不能执行window.open问题
						}
					}); */
			});
		</script>
	</head>
	<body class="body_bg">

	</body>
</html>