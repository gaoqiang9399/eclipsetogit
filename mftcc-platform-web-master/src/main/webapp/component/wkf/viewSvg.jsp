<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript">
<!--
var isGoBack = true;
-->
</script>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>查看流程</title>
		<script type="text/javascript">
			 function isSVG(){  
				 //IE不支持navigator.mimeTypes属性   
				 if (navigator.mimeTypes != null && navigator.mimeTypes.length > 0){    
					 //如果不是IE,判断此游览器中是否有支持SVG的插件     
					 return !!navigator.mimeTypes["image/svg+xml"];  
				  } else {   
				   //如果是IE则判断是否安装了ADOBE的SVG的插件    
				   try {  
				       var asv = new ActiveXObject( "Adobe.SVGCtl" );  
				    } catch (err){  
				        return false;  
				    }  
					return true;   
					} 
				} 
			function hasSvg(){
				if (!isSVG()){ 
					if(confirm("您还未安装 Adobe SVG Viewer 插件，点击确定安装后再刷新页面重试！如还未解决，请使用ie7及以上版本浏览器！")){
						window.location.href= "${webPath}/workflow/adobe-svg-viewer.exe" ;  
					} 
				}   
			}
		</script>
	</head>
	<script type="text/javascript">
	</script>
	<body class="body_bg">
	<div><font color="gray" size="2">【查看流程】需要adobe SVG插件的支持,如果无法显示,请点击<em><a href='${webPath}/workflow/adobe-svg-viewer.exe'><span style='text-decoration:underline;'>Adobe SVG Viewer</span></a></em>下载并安装</font></div>
	<% String processInstanceId=request.getParameter("processInstanceId"); %>
	<embed src="${webPath}/ProcessToSVG/viewSvg?processInstanceId=<%=processInstanceId %>"
		width="100%" height="95%"
		type="image/svg+xml" id="svgmapctrl" 
		pluginspage="http://www.adobe.com/svg/viewer/install/">
</body>
</html>