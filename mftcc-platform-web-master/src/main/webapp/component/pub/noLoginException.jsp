<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
	<title>会话失效</title>
	<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
	<script type="text/javascript">
	   	function funcExit(actionmsg){		
		 	window.top.alert(actionmsg,4,locationHref,locationHref);
		}
		function locationHref(){
			<%-- window.top.location.href='${webPath}entryLease.jsp'; --%>
			window.top.location.href = "<%=request.getContextPath() %>/logout.action";
		}
	</script>
	<%-- <s:if test="hasActionMessages()">
		<script type="text/javascript">
			var actionmsg = "";
		</script>
		<s:iterator value="actionMessages">
			<script type="text/javascript">
				actionmsg = actionmsg+"<s:property escape="false"/>"+"<br />";
		    </script>
		</s:iterator> --%>
		<script type="text/javascript">
			if($("#hasMessages").length>0) {
	    	var actionmsg ="";
	    	
	    	var errorMsgs = '${errorMsgs}';
			if(errorMsgs!==undefined&&errorMsgs!=null&&errorMsgs!=""){
				var errorMsgsArray =  eval('(' + errorMsgs + ')');
				$.each(errorMsgsArray,function(index,msg){
					if(actionmsg===undefined){
						actionmsg=msg;
					}else{
						actionmsg+=msg;
					}
				});
			}
			if (actionmsg != "") {
				funcExit(actionmsg);
			}
		</script>
	<%-- </s:if> --%>
</head>
<body>

</body>
</html>
