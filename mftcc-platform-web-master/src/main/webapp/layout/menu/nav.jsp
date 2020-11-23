<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<link href="themes/css/main.css" rel="stylesheet"  type="text/css"/>
<link href="themes/theme_<%=theme %>/Css/sysUI_<%=theme %>.css" rel="stylesheet"  type="text/css"/>
<script src="<%=basePath %>layout/menu/js/jquery-1.8.0.min.js" language="javascript" type="text/javascript"></script>
<title>nav</title>
</head>


<body class="body_bg1" style="margin:0px;padding: 0px;">
<div>
	<div>
		<div id="imgid" class="leftarrow"></div>
	</div>
	<script language="javascript">
		var img=document.getElementById("imgid");
		var isclose=false;
		img.onclick=function(){
			if(isclose==true){
				//针对IE10这个设置cols无法repaint的BUG,只能这样了(设置rows可以repaint)
				var height = parent.document.getElementById("mainFramestId").clientHeight;
				parent.document.getElementById("mainFramestId").removeAttribute("cols");
				parent.document.getElementById("mainFramestId").setAttribute("rows", height + ",*");
				parent.document.getElementById("mainFramestId").removeAttribute("rows");
				parent.document.getElementById("mainFramestId").cols="203,8,*";
				
				img.className="leftarrow";
				isclose=false;
			}else{
				var height = parent.document.getElementById("mainFramestId").clientHeight;
				parent.document.getElementById("mainFramestId").removeAttribute("cols");
				parent.document.getElementById("mainFramestId").setAttribute("rows", height + ",*");
				parent.document.getElementById("mainFramestId").removeAttribute("rows");
				parent.document.getElementById("mainFramestId").cols="0,8,*";
				img.className="leftarrow_open";
				isclose=true;
			}
		}
	</script>
</div>
</body>
</html>