<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	FileSaver fs = new FileSaver(request, response);
	String requestFileName = request.getParameter("fileName");
	String err = "";
				
	if (requestFileName != null && requestFileName.length() > 0) {
		String fileName = requestFileName ;
		fs.saveToFile(request.getSession().getServletContext().getRealPath("component/pss/batchPrint/batchDocTemp/") + "/" + fileName);
		
	} else {
		err = "<script>alert('未获得文件名称');</script>";
	}
	fs.close();
%>
