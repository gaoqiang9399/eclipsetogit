<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>

		<script type="text/javascript" src="${webPath}/themes/login/js/jquery-2.1.1.min.js" ></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
	</head>
	<script type="text/javascript">
	var headImgPtah;
	var headImg = '${headImg}';
	var ifUploadHead = '${ifUploadHead}';
	var fileName = "";
	var filePath = "/cusHeadImg/";
	$(function(){
		var data = headImg;
		if(ifUploadHead!="1"){
			data = "themes/factor/images/"+headImg;
		}
		$("input[name=upload]").attr({"accept":"image/*"});
		data = encodeURIComponent(encodeURIComponent(data));
		document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?fileName=user2.jpg&srcPath="+data;
		$("form[id=UploadFileAction_upload]").attr({"enctype":"multipart/form-data"});
		$("form[id=UploadFileAction_upload]").attr({"name":"upload"});
		$("form[id=UploadFileAction_upload]").attr({"id":"upload"});
		$("input[name=upload]").attr({"id":"upload"});
	});
	//保存头像信息，上传头像及保存文件信息到客户信息登记表中
	function uploadHeadImg(obj){
		fileName=$("input[name=upload]").val();
		if(fileName==""){
			window.top.alert(top.getMessage("ERROR_UPLOAD_FILE_TYPE",""),0);
			return;
		}
		var array=fileName.split("\\");
		fileName=array[array.length-1];
		$("input[name=uploadFileName]").val(fileName);
		$("input[name=primaryKey]").val("00");
		$("input[name=filePath]").val(filePath);
		var url = $("#operform").attr("action");
		var formData = new FormData($('#operform')[0]);
		formData.append('formNote', "upload");
		$.ajax({
			url:url,
			data:formData,
			dataType:"json",
			type:"post",
			cache: false,//上传文件无需缓存 
		    processData: false,//用于对data参数进行序列化处理 这里必须false
               contentType: false, //必须 
			success:function(data){
				if(data.flag == "success"){
					$.ajax({
						url:webPath+"/mfCusCustomer/submitUploadHeadImg",
						data:{cusNo:'${cusNo}',headImg:fileName},
						type:'post',
						dataType:'json',
						success:function(data){
							if(data.flag == "success"){
								  myclose_click();
							}
						},error:function(){
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
				}else{
					window.top.alert("上传失败!",0);
				}
			},
			error:function(){
				window.top.alert("上传失败!",0);
			}
		});	
	}
	//验证文件格式及预览
	function checkFileEnd(obj){
		var filePathTmp = '/tmp/';
		fileName=$("input[name=upload]").val();
		var fileNameEnd = fileName.toLowerCase().substr(fileName.lastIndexOf("."));
		if(fileNameEnd==".jpg"||fileNameEnd==".png"||fileNameEnd==".gif"||fileNameEnd==".jpeg"){
			fileName=$("input[name=upload]").val();
			var array=fileName.split("\\");
			fileName=array[array.length-1];
			$("input[name=uploadFileName]").val(fileName);
			$("input[name=primaryKey]").val("00");
			$("input[name=filePath]").val(filePathTmp);
				$("#operform").ajaxSubmit(function(data){
					if(data.flag.indexOf("false;")>-1){
			  			var m=data.split(";")[1];
			  			window.top.alert(top.getMessage("FAILED_UPLOAD_FILE",m),0);
					}else{
						fileName = encodeURIComponent(encodeURIComponent(fileName));
						document.getElementById('headImgShow').src=webPath+"/uploadFile/viewImage?fileName="+fileName;
					}
		     });
			return true;
		}else{
			window.top.alert(top.getMessage("ERROR_UPLOAD_FILE_TYPE","jpg、jpeg、gif、png格式的"),0);
			$("input[name=upload]").val("");
			return false;
		}
	}
	//取消
	function myclosePop(){
	 	myclose();
	 	delFile();
	}
	//删除文件
	function delFile(){
		var srcPath = "/tmp/";
		$.ajax({
			url:webPath+"/uploadFile/delFile",
			data:{srcPath:srcPath},
			type:'post',
			dataType:'json',
			success:function(data){
				
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
			        <div id = "headimgdiv" style="height:170px; text-align: center;">
				            <div class="head-img1" id = "head_img">
								<img id="headImgShow" name="headImgShow" style="height: 132px; width: 132px; border-radius: 50%;">
							</div>
					</div>
					<div class="bootstarpTag">
						<div class="form-tips">说明：请上传jpg、jpeg、gif、png格式的文件。</div>
						<form  method="post" theme="simple" name="operform" id="operform" action="${webPath}/uploadFile/upload">
							<dhcc:bootstarpTag property="formcusuploadhead0001" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="uploadHeadImg(this.form);"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclosePop();"></dhcc:thirdButton>
	   		</div>
   			
   		</div>
	</body>
</html>
