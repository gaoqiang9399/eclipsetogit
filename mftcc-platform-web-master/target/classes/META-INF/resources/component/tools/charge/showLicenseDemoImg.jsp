  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/component/include/pub_view.jsp"%>
<title>Insert title here</title>
<style type="text/css">

</style>
</head>
<script type="text/javascript">
$(function(){
	//自定义滚动条
		$(".aaa").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:false
			}
		});
})
</script>
<body class=" overflowHidden body_bg">
<div id="img" class="aaa" align="center" style="height:800px">
<img src="${webPath}/component/tools/image/licenseImgDemo.jpg" style="width:600px">
</div>
</body>
</html>