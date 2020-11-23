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
			$("input:radio").change(function () {
				var dimm1 = $(this).val();
				var scNo = '${docBizSceConfig.scNo}';
				var docType = '${docBizSceConfig.docType}';
				var splitDocsFrame = $("#splitDocs",window.parent.document);
				var splitDocsSrc = "${webPath}/docBizSceConfig/findSplitDocs";
				splitDocsSrc += "?docBizSceConfig.scNo="+scNo+"&docBizSceConfig.docType="+docType+"&docBizSceConfig.dime1="+dimm1;
				//console.log(splitDocsSrc);
				
				//$("input + label").css("color","yellow");
				splitDocsFrame.attr("src",splitDocsSrc);
			});
		});
		
	</script>
	<style type="text/css">
		body{
			background-color: #EBEBEB;
		}
	</style>
  </head>
  
  <body>
  
    <table>
    	<s:iterator value="docDimms" id="docDimm">
    		<tr>
    			<td>
    				<input type="radio" name="docDimm" value="${#docDimm.dimmNo"/>"  ><label><s:property value="#docDimm.dimmName}</label> 
    				
    			</td>
    		</tr>
    	</c:forEach>		
    </table>
  </body>
</html>
