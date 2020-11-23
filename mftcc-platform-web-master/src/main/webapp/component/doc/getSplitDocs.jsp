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
	<link rel="stylesheet" href="${webPath}/component/doc/css/DocBizSceConfig.css" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		
			$(document).ready(function() {
			
			});
	</script>
  </head>
  
  <body>
  
    <table>
    	<s:iterator value="docBizSceConfigs" id="docBizSceConfigs">
    		<tr>
    			<td>
    				<c:if test="${#docBizSceConfigs.scNo==null}">
    				<input name="docBizSceConfig.docSplitNo" type="checkbox" value="${#docBizSceConfigs.docSplitNo"/>" /><a><label><s:property value="#docBizSceConfigs.docSplitName} </a></label> 
    				</c:if>
    				<c:if>
    				<input name="docBizSceConfig.docSplitNo" type="checkbox" value="${#docBizSceConfigs.docSplitNo"/>"  checked/><a><label><s:property value="#docBizSceConfigs.docSplitName} </a></label> 
    				</c:if>
    				
    			</td>
    		</tr>
    	</c:forEach>		
    </table>
  </body>
</html>
