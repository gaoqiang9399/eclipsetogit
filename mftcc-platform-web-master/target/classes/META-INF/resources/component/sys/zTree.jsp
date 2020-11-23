<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@ include file="../include/pub.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html>
  <head>
    
    <title>zTree simpleData UTF-8</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${webPath}/creditapp/sys/css/demo.css">
	<link rel="stylesheet" type="text/css" href="${webPath}/creditapp/sys/css/zTreeStyle/zTreeStyle.css">
	<link rel="stylesheet" type="text/css" href="${webPath}/creditapp/sys/css/zTreeStyle.css">
	<script type="text/javascript" src="${webPath}/creditapp/sys/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript">
	function lkconfirm(lk){
		alert("请确定是否要执行此操作，按“取消”表示不进行此操作！",2,function(){
			location.href = lk;
		});
 	/* flag=window.confirm("请确定是否要执行此操作，按“取消”表示不进行此操作！");
  	if(flag){
  		location.href = lk;
  	} */
}
	var zNodes= <%=request.getAttribute("treeVal")%>;
	
		function treeOnClick(event,treeId,treeNode) 
		{
			if(!treeNode.isParent)
			{	
				alert(treeNode.name);
				lkconfirm(webPath+"/sysRequireTable/insertNew?tableinfo="+treeNode.name);
			}
			
		}
		

		var setting={
				data:{
					  simpleData:{
								  enable:true
							}
					},
					
				callback:{
						onClick:treeOnClick
					}
			};
		
		
		$(function()
		{
			$.fn.zTree.init($("#tree"),setting,zNodes);
			
		});
	</script>

  </head>
  
  <body>
    <div align="center">
		<ul id="tree" class="ztree" style="text-align: left"></ul>
	</div>
	<br>
  </body>
  <script type="text/javascript">
  		var width = document.body.offsetWidth;
  		var height = document.body.offsetHeight;
		document.getElementById("tree").style.width=width+"px";
		document.getElementById("tree").style.height=height+"px";
  </script>
</html>
