<%@ page language="java"
	import="java.util.*,com.dhcc.wxtld.core.base.*,com.dhcc.wxtld.core.domain.*,com.dhcc.wechat.entity.*"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="wx" uri="/WEB-INF/tld/weChat.tld"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<link rel="stylesheet" href="${webPath}/component/interfaces/appinterface/common/jqueryWeUI-1.01/weui.css"/>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>要件上传</title>
<style type="text/css">
	.sub_uploader_title{
		font-size:16px;
	}
	.big_title{
	    background-color: #F7F7F7;
	    color: #38adff;
	    width: 100%;
	    line-height: 40px;
	    height: 40px;
	    padding-left: 15px;
	}
	.padding_class{
		padding: 5px 15px;
	}
	.background_white{
		background-color: white;
		width: 100%;
	}
	.uploader__input-box:before, .weui-uploader__input-box:after{
		background-color: #38adff;
	}
	.weui-uploader__input-box:before, .weui-uploader__input-box:after{
		background-color: #38adff;
	}
	.weui-uploader__input-box{
		border: 1px dashed #38adff;
	}
	.weui-uploader__bd{
		border-bottom: 1px dashed #F7f7f7;
		margin-left: 15px;
		padding-left: 0px !important;
	}
	.weui-uploader__hd{
		padding-top: 20px;
	}
	.big_title_out{
		  padding-top:0px  !important;
	}
</style>
<!-- <script type="text/javascript"
	src="/factor/component/doc/webUpload/js/jquery-1.11.0.js"></script> -->
<script>
	var _config;
	var phone;
	var appId = "";
	var cusNo = "${cusNo}";
	var scNo = "${scNo}";
	$("input[name=scNo]").val("image");
	var pactId = "";
	var isUploading= false;
	var hasLoacation = false;
	$(function() {
		getSignature();
		dd.config({
			corpId : _config.corpId,
			timeStamp : _config.timeStamp,
			nonceStr : _config.nonceStr,
			signature : _config.signature,
			agentId : _config.agentid,
			jsApiList : [ 'runtime.info', 'biz.user.get',
					'runtime.permission.requestAuthCode', 'biz.contact.choose',
					'biz.navigation.setRight', 'device.notification.alert',
					'device.notification.prompt', 'biz.ding.post',
					'biz.util.openLink','biz.util.uploadImageFromCamera','device.geolocation.get'
					]
		});
		dd.error(function(error){
			  alert('dd error: ' + JSON.stringify(error));
		});
		getPhoneNode();
		dd.ready(function() {
			dd.device.geolocation.get({
			    targetAccuracy : 20,
			    coordinate : 1,
			    withReGeocode : true,
			    onSuccess : function(result) {
			    	$("input[name=address]").val(result.address);
			    	$("input[name=longitude]").val(result.address);
			    	$("input[name=latitude]").val(result.address);
			    	hasLoacation = true;
			    },
			    onFail : function(err) {}
			});
		});
	})
	/* 	function setRight(){
			dd.biz.navigation.setRight({
			    show: true,//控制按钮显示， true 显示， false 隐藏， 默认true
			    control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
			    text: '进件',//控制显示文本，空字符串表示显示默认文本
			    onSuccess : function(result) {
			       window.location.href='http://wx.mftcc.cn/factor/MfAppCusCustomerAction_input.action'
			    },
			    onFail : function(err) {}
			});	
		} */
	function getSignature() {
		$.ajax({
			url : webPath+"/dingInterface/getDingJSConfigAjax?htmlUrl="
					+ encodeURIComponent(location.href.split('#')[0]),
			type : "get",
			dataType : 'json',
			async : false,
			success : function(data) {
				_config = data.data;
			},
			error : function(returnJSON) {
				alert("连接失败，请刷新页面重试");
			}
		})
	}
	function getPhoneNode() {
		var ajaxdata = {
			appId : appId,
			cusNo : cusNo,
			scNo : scNo,
			pactId : pactId,
		};
		var ajaxdataS = JSON.stringify(ajaxdata);
		$.ajax({
			url : webPath+"/dingUploadFile/getCusNeedDocs",
			type : "post",
			data : {
				paramJson : ajaxdataS
			},
			dataType : 'json',
			async : false,
			success : function(data) {
				console.log(data);
				var list = data.data;
				list = $.parseJSON(list);
			 	$imageList = $("#image_list");
			 	
			 	//上一个子类型信息
			 	var lastDisInfo = {};
			 	var lastSplitNo = '-1';
				var hasLast = false;
				for ( var i = 0; i < list.length; i++) {
					var e = list[i];
					//添加头部显示
					$bigTitle = $("<div></div>").addClass("weui-uploader__hd big_title_out").html('<div class="big_title"><div class="weui-uploader__title">'+e.docTypeName+'</div></div>');
					$imageList.append($bigTitle);
					//添加照片
					var ImageList = e.imgs;
					for ( var j = 0; j < ImageList.length; j++) {
						var img = ImageList[j];
						var cusNo = img.cusNo;
						var relNo = img.relNo;
						var docSplitNo = img.docSplitNo;
						var docType = img.docType;
						var scNo = img.scNo;
						var docNo = img.docNo;
						var docBizNo = img.docBizNo;
						if(docSplitNo == lastSplitNo){//这个类型跟上一个一样，则加到上一个中
							var imgCompressUrl = webPath+'/dingUploadFile/viewCompressImage?docNo='+docNo+'&docBizNo='+docBizNo;
							$imagLi = $('<li></li>').addClass('weui-uploader__file show_file').attr({
									'style':'background-image: url('+imgCompressUrl+')',
									'data-docNo' : docNo,
									'data-docBizNo': docBizNo
								});
							$lastUI.append($imagLi);
						}else{//如果不一样，创建新的结构，并为上一个添加添加按钮
							//添加子标题
							$susTitle = $("<div></div>").addClass("weui-uploader__hd padding_class").html('<p class="weui-uploader__title sub_uploader_title">'+img.docSplitName+'</p>');
							$imageList.append($susTitle);
							$imgDiv = $("<div></div>").addClass("weui-uploader__bd padding_class");
							$imgUl =  $('<ul></ul>').addClass('weui-uploader__files').attr("id",docSplitNo);
							//如果有图片则加到UI中，没有的话等下一个转换时添加添加按钮
							if(typeof(img.docNo)!="undefined"){
								var imgCompressUrl = webPath+'/dingUploadFile/viewCompressImage?docNo='+docNo+'&docBizNo='+docBizNo;
								$imagLi = $('<li></li>').addClass('weui-uploader__file show_file').attr({
									'style':'background-image: url('+imgCompressUrl+')',
									'data-docNo' : docNo,
									'data-docBizNo': docBizNo
								});
								$imgUl.append($imagLi);
							}
							$imgDiv.append($imgUl);
							$imageList.append($imgDiv);
							//为上一个添加添加按钮
							var paramJSON = JSON.stringify(lastDisInfo);
							$imgAddDiv = $("<div></div>").addClass("weui-uploader__input-box").
							html('<input data-param='+paramJSON+'  class="weui-uploader__input" type="text" readonly accept="image/*" multiple="">');
							if(hasLast){
								$lastUI.parent().append($imgAddDiv);
							}
							hasLast =true;
							$lastUI = $imgUl;
							//更改上一个不同节点信息
							lastDisInfo.docSplitNo = docSplitNo;
							lastDisInfo.cusNo = cusNo;
							lastDisInfo.relNo = relNo;
							lastDisInfo.docType = docType;
							lastDisInfo.docBizNo = docBizNo;
						}
						lastSplitNo = docSplitNo;
						
						}
					}
				//为最后一个节点添加添加按钮
				var paramJSON = JSON.stringify(lastDisInfo);
				$imgAddDiv = $("<div></div>").addClass("weui-uploader__input-box").
					html('<input data-param='+paramJSON+'  class="weui-uploader__input" type="text" readonly  accept="image/*" multiple="">');
				$lastUI.append($imgAddDiv);
				
				//为所有图片添加预览事件
				$(".show_file").click(function(){
					var docNo = $(this).attr('data-docNo' );
					var docBizNo = $(this).attr('data-docBizNo' );
					var url = "${webPath}dingUploadFile/viewImage?docNo="+docNo+"&docBizNo="+docBizNo;
					dd.biz.util.previewImage({
					    urls: [url],//图片地址列表
					    current: url,//当前显示的图片链接
					    onSuccess : function(result) {
					        /**/
					    },
					    onFail : function(err) {}
					})
				});
				//为所有添加按钮绑定事件
				$(".weui-uploader__input").unbind().click(function(){
					if(isUploading){
						return;
					}else{
						isUploading = true;
						paramJSON = $(this).attr("data-param");
						paramObj = $.parseJSON(paramJSON);
						//把点击的值赋给表单
						for ( var i in paramObj) {
							$("input[name="+i+"]").val(paramObj[i]);
						}
						//图片上传
						 dd.biz.util.uploadImageFromCamera({
							compression: false, //(是否压缩，默认为true)
							onSuccess: function(result) { 
								var docSplitNo = $("input[name=docSplitNo]").val();
								var docBizNo = $("input[name=docBizNo]").val();
								var docNo = "";
								var imgCompressUrl = result[0];
								$("input[name=photourl]").val( result[0]);
								var imgFileName = result[0].split("/");
								imgFileName = imgFileName[imgFileName.length-1];
								//uploadContentType = imgUrl.split(".")[imgUrl.length];
							
								var uploadFileName = imgFileName;
								$("input[name=uploadContentType]").val("image");
								$("input[name=uploadFileName]").val(uploadFileName);
								//var formdata = new FormData(document.getElementById("photoInfoForm"));
								
								$.ajax({
									url : webPath+"/dingUploadFile/uploadFileAjax",
									type : "post",
									data : $( '#photoInfoForm').serialize(),
									dataType : 'json',
									async : false,
									success : function(data) {
										$imagLi = $('<li></li>').addClass('weui-uploader__file show_file').attr({
											'style':'background-image: url('+imgCompressUrl+')',
											'data-docNo' : docNo,
											'data-docBizNo': docBizNo
										});
										$("#"+docSplitNo).append($imagLi);
										
										$imagLi.click(function(){
											var docNo = $(this).attr('data-docNo' );
											var docBizNo = $(this).attr('data-docBizNo' );
											// var url = "${webPath}docUpload/viewImage?docNo="+docNo+"&docBizNo="+docBizNo;
											var url =  result[0];
											dd.biz.util.previewImage({
											    urls: [url],//图片地址列表
											    current: url,//当前显示的图片链接
											    onSuccess : function(result) {
											        /**/
											    },
											    onFail : function(err) {}
											})
										});
									}
								});
					 			isUploading = false;
							},
							onFail: function(err) {
								alert(JSON.stringify(err));
								isUploading = false;
							}
						}); 
					}
					
				});
			},
			error : function(returnJSON) {
				alert("连接失败，请刷新页面重试");
			}
		})
	}
</script>
</head>

<body>
	<input type="hidden" id="phone" />
	<input type="hidden" id="code" />
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell" style="padding:0px">
			<div class="weui-cell__bd">
				<div class="weui-uploader" id = "image_list">
		
				</div>
			</div>
		</div>
	</div>
	
	<form id="photoInfoForm">
		<input type="hidden" name="docSplitNo"/>
		<input type="hidden" name="cusNo"/>
		<input type="hidden" name="relNo"/>
		<input type="hidden" name="docType"/>
		<input type="hidden" name="docBizNo"/>
		<input type="hidden" name="uploadFileName"/>
		<input type="hidden" name="uploadContentType"/>
		<input type="hidden" name="photourl"/>
		<input type="hidden" name="scNo"/>
		<input type="hidden" name="address"/>
		<input type="hidden" name="longitude"/>
		<input type="hidden" name="latitude"/>
	</form>
</body>

</html>