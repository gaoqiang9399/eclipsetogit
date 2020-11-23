<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/webPath.jsp"%>
<%
	String relNo = request.getParameter("relNo");
	String filename = request.getParameter("filename");
	String returnUrl = request.getParameter("returnUrl");
	String filesavepath = request.getParameter("docFilePath");
	String saveFileName = request.getParameter("docFileName");
	String modelNo = request.getParameter("modelNo");
	String generatePhase = request.getParameter("generatePhase");
	String urlParm = request.getParameter("urlParm");
	String templateNo = request.getParameter("templateNo");
	String waterId = request.getParameter("waterId");
	String pageOffice = request.getParameter("pageOffice");
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title>文档保存后记录文档保存信息</title>
		<script type="text/javascript" src="${webPath}/component/demo/js/jquery-1.11.2.min.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="json2.js"></script>
		<script type="text/javascript">
			var relNo = '<%=relNo%>';
			var filename = '<%=filename%>';
			var returnUrl = '<%=returnUrl%>';
			var filesavepath = '<%=filesavepath%>';
			var modelNo = '<%=modelNo%>';
			var generatePhase = '<%=generatePhase%>';
			var saveFileName="<%=saveFileName%>";
			var urlParm = '<%=urlParm%>';
			var templateNo = '<%=templateNo%>';
			var waterId = '<%=waterId%>';
			var pageOffice = '<%=pageOffice%>';
			returnUrl=returnUrl+"?"+urlParm+"&modelNo="+modelNo+"&generatePhase"+generatePhase;
			//验证串
			var userId = '${param.userId}';
			$(function(){
				 $.ajax({
					 	url:webPath+"/mfTemplateModelRel/insertTemplateModelRel",
						type:"post",
						data:{"relNo":relNo,"modelNo":modelNo,"filename":filename,"filesavepath":filesavepath,"saveFileName":saveFileName,"templateNo":templateNo,"pageOffice":pageOffice,"waterId":waterId,"userId":userId},
						async: false,
						dataType:"json",
						 error: function (XMLHttpRequest, textStatus, errorThrown) {
			                    // 状态码
			                    //alert(XMLHttpRequest.status);
			                    // 状态
			                    //alert(XMLHttpRequest.readyState);
			                    // 错误信息   
			                    //alert(textStatus);
			                    //alert(JSON.stringify(errorThrown));
			                    alert("保存失败");
			             },
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