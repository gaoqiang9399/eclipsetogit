<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>



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
				var sceDocTypeFrame = $("#sceDocType",window.parent.document);
				var splitDocsFrame = $("#splitDocs",window.parent.document);
				var docDimmsFrame = $("#docDimms",window.parent.document);
				var sceDocTypeSrc = "${webPath}/docBizSceConfig/findSceDocTypeRels";
				sceDocTypeSrc += "?sceDocTypeRel.scNo="+ $(this).attr("id");
				sceDocTypeFrame.attr("src",sceDocTypeSrc);
				docDimmsFrame.attr("src","");
				splitDocsFrame.attr("src","");
				$(this).css("color","yellow");
				$("a").not(this).css("color","black");
			}) ;
		});
	</script>
	<style type="text/css">
		body{
			background-color: #EBEBEB;
		}
	</style>
  </head>
  <body>
    <table cellpadding="20px" cellspacing="20" border="1" bordercolor="black">
    	<c:forEach items="${scenceList }" var="scences">
    		<tr>
    			<td>
    				<a id="${scences.scNo }"><label>${scences.scName}</label></a>
    			</td>
    		</tr>
    	</c:forEach>		
    </table>
  </body>
</html>
