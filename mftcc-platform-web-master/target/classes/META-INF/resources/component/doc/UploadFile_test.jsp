<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String contextPath=request.getContextPath();
%>
<!DOCTYPE html>
<html>
	<head>
		<title>单独上传例子</title>
		<script type="text/javascript" src="${webPath}/themes/login/js/jquery-2.1.1.min.js" ></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
		<script type="text/javascript">
			function changeCifIdPic(obj){
				var fileName=obj.value;
				var array=fileName.split("\\");
				fileName=array[array.length-1];
				document.getElementById("uploadFileName").value=fileName;
				document.getElementById("primaryKey").value="00";
					$("#upload").ajaxSubmit(function(data){
						if(data.indexOf("false;")>-1){
			  			var m=data.split(";")[1];
			  			alert("上传失败，"+m);
					}else{
						document.getElementById('QRCodePicShow').src="${webPath}/uploadFile/viewImage?srcPath="+data;
					}
			     });
			}
			function upLoadFile(id){
				var s="#"+id;
				$(s).click();
			}
	</script>
	</head>
	<body>
		<form action="${webPath}/uploadFile/upload" id="upload" method="post" enctype="multipart/form-data">
		<input type="hidden" id="uploadFileName" name="uploadFileName">
		<input type="hidden" id="primaryKey" name="primaryKey">
		<img id="QRCodePicShow" name="QRCodePicShow">
		<div id="testDiv">
		<input type="file" id="upload" name="upload" onchange="">
		<input type="button" id="upload1" name="upload1" value= "上传" onclick="changeCifIdPic(this)">
		</div>
		</form>
	</body>
</html>