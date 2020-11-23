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
		//System.out.println("saveSingleFile.jsp");
		//System.out.println(request.getSession().getServletContext().getRealPath("component/batchprint/batchDocTemp/") + "" + fileName);
		fs.saveToFile(request.getSession().getServletContext().getRealPath("component/batchprint/batchDocTemp/") + "" + fileName);
		
	} else {
		err = "<script>alert('未获得文件名称');</script>";
	}
	fs.close();
%>
