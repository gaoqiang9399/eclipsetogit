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
var str="data:image/png;base64,";
$(function(){
	var code='${param.code}';
	$.ajax({
		url:webPath+"/accountPay/getBase64Img',
		data:'code='+code,
		dataType:'json',
		success:function(data){
			str+=data.str;
			$("#img").html("<img src=\""+str+"\" style='width:600px'>");
		}
	})
	//自定义滚动条
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				theme:"minimal-dark",
// 				updateOnContentResize:true
// 			}
// 		});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
})
</script>
<body class=" overflowHidden body_bg">
<div id="img" class="mf_content" align="center" style="height:563px">
</div>
</body>
</html>