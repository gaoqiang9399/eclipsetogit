<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="app.component.sys.entity.*,java.util.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object themeObj = session==null?null:session.getAttribute("color");
	String theme = themeObj==null?"Cred":((String)themeObj);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="themes/css/main.css" type="text/css" rel="stylesheet" />
<link href="themes/theme_<%=theme %>/Css/sysUI_<%=theme %>.css" type="text/css" rel="stylesheet" />
<link href="themes/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<script src="js/jquery-1.8.0.min.js" language="javascript" type="text/javascript" ></script>
<script src="js/jquery.easyui.min.js" language="javascript" type="text/javascript"></script>
<script src="<%=basePath %>UIplug/jqueryUI/js/jquery.ui.widget.js" language="javascript" type="text/javascript"></script>
<script src="<%=basePath %>UIplug/jqueryUI/js/jquery.ui.accordion.js" language="javascript" type="text/javascript"></script>
<script src="<%=basePath %>UIplug/zTree/jquery.ztree.core-3.5.min.js" language="javascript" type="text/javascript"></script>
<title>二级菜单列表</title>
</head>
<body style="margin:10px 3px 0 7px;" class="body_bg1" >
<div class="entry_accordion">

<!--  一二级菜单联动示例-------------->
<%
Map<String,String> map;
String id = null;
String menuNo = null;
if(session==null || session.getAttribute("menuTreeMap")==null){
	map = null;
}else {
	map = (Map<String,String>)session.getAttribute("menuTreeMap");
	id = request.getParameter("id");
	menuNo = ((app.component.sys.entity.SysMenu)((java.util.List)session.getAttribute("sysMenuLev1List")).get(0)).getMenuNo();
}
%>
<%=(map==null || map.values()==null)?("<script language='javascript'>alert('异常登录状态,请重新登录!');window.top.location.href='"+request.getContextPath()+"';</script>"):(id==null|| "".equals(id))?((String)map.get(menuNo)):map.get(id) %>
  </div>
</body>
<script language="javascript" type="text/javascript">
		//手风琴上下伸缩效果
		$("#accordion").accordion({  
	       	collapsible:true, 
	        active:0,
			autoHeight:false,
			fillSpace:true
	    });
		
		$(".menudiv").click(function(){
			$(".menudivDown").attr("class","menudiv");
			$(this).attr("class","menudivDown");
		});
		function goMenuUrl(obj){
			$("li.current").removeClass("current");
			$(obj).addClass("current");
			parent.document.getElementById("rightFrame").src = $(obj).attr("url");
			parent.document.getElementById("locFrame").contentWindow.document.getElementById("secondMenu").innerText = $(obj).parent("ul").parent("div").prev("a").text();
			parent.document.getElementById("locFrame").contentWindow.document.getElementById("thirdMenu").innerText = $(obj).text();
		}
</script>
</html>