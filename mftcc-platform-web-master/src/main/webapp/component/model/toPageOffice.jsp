<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	String type = request.getParameter("type");
	String relNo = request.getParameter("relNo");
	String cifno = request.getParameter("cifno");
	String modelid = request.getParameter("modelid");
	String filename = request.getParameter("filename");
	String pactno = request.getParameter("pactno");
	String appno = request.getParameter("appno");
	String loanNo = request.getParameter("loanNo");
	String traceNo = request.getParameter("traceNo");
	String returnUrl = request.getParameter("returnUrl");
	String filepath = request.getParameter("filepath");
	String saveUrl = request.getParameter("saveUrl");
	String modelNo = request.getParameter("modelNo");
	String generatePhase = request.getParameter("generatePhase");
	String urlParm = request.getParameter("urlParm");
	String templateNo = request.getParameter("templateNo");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>调用pageoffice公共页面</title>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var type = '<%=type%>';
			var filepath = '<%=filepath%>';
			var saveUrl = '<%=saveUrl%>';
			var returnUrl = '<%=returnUrl%>';
			var urlParm = '<%=urlParm%>';
			var filename = '<%=filename%>';
			var templateNo = '<%=templateNo%>';
			var datatmp = "";
			var saveUrl = webPath+"/component/model/saveModelInfo.jsp?relNo=<%=relNo%>&filename=<%=filename%>&urlParm="+encodeURIComponent(urlParm)+"&templateNo="+templateNo+"&modelNo="+'<%=modelNo%>';
			returnUrl=returnUrl+"?"+urlParm;
			$(function(){
				var poCntJson = {
						filePath : filepath,
						fileName : "<%=filename%>",
						appId : "<%=appno%>",
						pactId : "<%=pactno%>",
						fincId : "<%=loanNo%>",
						templateNo :templateNo,
						cusNo : "<%=cifno%>",
						saveUrl :saveUrl,
						saveBtn : "1",
						//returnUrl : returnUrl,
						fileType : 0
				};
				if(type == "detail"){
					poCntJson.saveBtn = "0";// 取消保存按钮
				}
				mfPageOffice.openPageOffice(poCntJson);
			})
		</script>
	</head>
	<body class="body_bg">
		
	</body>
</html>