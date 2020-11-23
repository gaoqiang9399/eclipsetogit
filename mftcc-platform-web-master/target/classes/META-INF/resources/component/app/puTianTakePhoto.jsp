<%
%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<title>拍照</title>
		<script type="text/javascript">
			var relNo,docBizNo,cusNo,docSplitNo;
			<%--relNo = '${mfBusPact.pactId}';// 要件业务编号--%>
            docSplitNo='1000000156';
			relNo = 'CONT18041814171343031';
			docBizNo = '${mfBusPact.appId}';
			cusNo = '${mfBusPact.cusNo}';
			photoUploadIp = '192.168.2.20'; //普天上传的服务器IP
			photoUploadPort = '8090'; //普天上传的服务器端口
			var recordFile = ""; //录像文件名，根据是否有文件名来判断关闭录像并上传
			var isVice = false; //副摄像头是否开启
			$(function(){
				if(!!window.ActiveXObject || "ActiveXObject" in window){//判断是否是IE
					$(".photo-frame").html("");
					$(".photo-frame").append('<div style="text-align:center;" ><object classid="clsid:454C18E2-8B7D-43C6-8C17-B1825B49D7DE"'
							+' id="captrue"  width="65%" height="55%"></object></div>');
				}else{					
					$(".photo-frame").html("");
					$(".photo-frame").append('<p><img id="photo" src="" style="width:65%;height:55%" ></p>');					
				}				
				setTimeout(function(){
					getPhotoUtil().start1Camera();	
		    	 },600);
				
				//滚动条
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
			});			
			
		</script>
		<script type="text/javascript"	src="<%=webPath%>/component/app/js/PuTian.js?v=${cssJsVersion}"></script>
		<link type="text/css" rel="stylesheet" href="<%=webPath%>/component/app/css/puTianTakePhoto.css?v=${cssJsVersion}" />
	</head>
	<body class="overflowHidden bg-white">
		<%--<form enctype="multipart/form-data">
			<input type="file" name="file">
		</form>--%>

		<div class="container form-container">
			<div id="view-imgs" class="scroll-content">
                <div class='photo-frame' style="text-align:center;margin-top:12px;">
                </div>
				<div style="text-align:center;">
				 <table id="putian-tab" style="color: #009db7;" >
				 	<tr>
				 		<td>文件保存地址</td>
				 		<td colspan='2'>
				 			<div class="btn_text block-view">
				 				<input name="filePath" type="text" value="C:/" style="border:none;outline:none"/>
				 			</div>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td>开启摄像头</td>
				 		<td ><div style="text-align:center;">主摄像头<input type="radio" name="camera"  onclick="getPhotoUtil().start1Camera()" checked="checked"/></div></td>
				 		<td ><div style="text-align:center;">副摄像头<input type="radio" name="camera"  onclick="getPhotoUtil().start2Camera()" /></div></td>
				 	</tr>
				 	<tr>
				 		<td>拍照与录像</td>
				 		<td colspan='2'>
				 			<div class="btn block-view" name='savePhoto' onclick='getPhotoUtil().saveAsJPG()'>拍照
                                <%--<div style="display:none;">
                                    <input type="file" name="Photos" id="Photo"/>
                                </div>--%>
				 		</td>
				 	</tr>
				 	<tr>
			 			<td></td>							 		
				 		<td>
                            <div class="btn1 block-view" id='startRecord' onclick='getPhotoUtil().startRecord()'>开始录像
                            </div>
                <div class="btn1 block-view" id="record" style="display:none;background:#CFCFCF;"  onclick='getPhotoUtil().stopRecord()'>
                    <span id="TimeLenth"></span>
                    <span name='stopRecord' >停止</span>
                </div>
				 		</td>
				 		<td>
				 			<div class="btn2 block-view" onclick='$("#openVideo").click();'>+ 上传视频</div>
							<div style="display:none;">
							<input type="file" name="filename1" id="openVideo" onchange ="uploadFile('#openVideo','1000000156')"/>
							</div>
				 		</td>
					 </tr>
					 <tr>
				 		<%--<td>人脸对比</td>--%>
				 		<td >
				 			<div class="btn1 block-view" onclick='getPhotoUtil().facePhotoUpload()' >点击拍照</div>
				 		</td>
				 		<td >
				 			<div class="btn2 block-view" onclick='facePlusUpload()' >+ 上传照片</div>
				 			<div style="display:none;">							 				
						        <input type="file" name="filename2" id="openPhoto" onchange ="uploadFile('#openPhoto','face')"/>
				</div>
				 		</td>
				 	</tr>
				 	<tr>
				 		<td></td>
				 		 	<div  class="btn block-view" id="compareResult"  style="display:none;background:#CFCFCF;" >
				 		 		匹配结果：<input id="faceCompareResult" type="text" value="" style="border:none;width:60%;background:#CFCFCF;" readonly="readonly"/>
				 		 	</div>
				 		 </td>
				 	</tr>							 	
				 </table>
				<!-- <div class="btn block-view">录制时间&nbsp;&nbsp;<input id="TimeLenth" type="text" value="" readonly="readonly" size="15" style="height:20px;"/></div>
				<div class="btn block-view" name='stopRecord' onclick='getPhotoUtil().stopRecord()' >停止录屏</div><br/>
				<div class="btn block-view"  onclick="getPhotoUtil().stopCamera()" >关闭高拍仪</div>	 -->
				</div>										
			</div>
			<div class="formRowCenter">
	   		</div>						
		</div>	
	</body>
</html>


