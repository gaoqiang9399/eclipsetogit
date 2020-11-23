<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="${webPath}">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="${webPath}/component/doc/css/DocBizSceConfig.css" />
	<script type="text/javascript">
		
			$(document).ready(function() {
			$("a").click(function() {
				var docDimmsFrame = $("#docDimms",window.parent.document);
				var scNo = '${sceDocTypeRel.scNo}';
				var docType = $(this).attr("id");
				var docDimmsSrc = "${webPath}/docBizSceConfig/findDocDimms";
				docDimmsSrc += "?docBizSceConfig.scNo="+ scNo+"&docBizSceConfig.docType="+docType;
				//console.log(docDimmsSrc);
				docDimmsFrame.attr("src",docDimmsSrc)
				$(this).css("color","yellow");
				$("a").not(this).css("color","black");
			}) ;
		});
	</script>
  </head>
  
  <body>
  
    <table>
    	<c:forEach items="${ sceDocTypeRels}" id="sceDocTypeRels">
    		<tr>
    			<td>
    				<c:if test="${sceDocTypeRels.scNo==null}">
    				<input name="sceDocTypeRels" type="checkbox" value="${sceDocTypeRels.docType"/>" /><a id="${ sceDocTypeRels.docType}"><label>${sceDocTypeRels.docTypeName} </a></label> 
    				</c:if>
    				<c:if  test="${sceDocTypeRels.scNo!=null}">
    				<input name="sceDocTypeRels" type="checkbox" value="${sceDocTypeRels.docType"/>"  checked/><a id="${ sceDocTypeRels.docType}"><label>${sceDocTypeRels.docTypeName} <a></label> 
    				</c:if>
    			</td>
    		</tr>
    	</c:forEach>		
    </table>
  </body>
</html>
