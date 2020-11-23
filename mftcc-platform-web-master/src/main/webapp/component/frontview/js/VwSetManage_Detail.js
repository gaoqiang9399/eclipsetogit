;
var VwSetManageDetail = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		$("[name=wechatStr]").parent().append("&nbsp;&nbsp;<span id='showWXJust' onclick='uploadWXImg()'></span>&nbsp;&nbsp;<a onclick='showWXImg()'>查看图片</a>&nbsp;&nbsp;<a onclick='uploadWXImg()'>上传图片</a>");
		$("[name=qqGroupStr]").parent().append("&nbsp;&nbsp;<span id='showQQJust' onclick='uploadQQImg()'></span>&nbsp;&nbsp;<a onclick='showQQImg()'>查看图片</a>&nbsp;&nbsp;<a onclick='uploadQQImg()'>上传图片</a>");
		$("[name=wechatStr]").hide();
		$("[name=qqGroupStr]").hide();
		$("body").show();
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);
//$(".scroll-content").mCustomScrollbar({
//	advanced:{
//		theme:"minimal-dark",
//		updateOnContentResize:true
//	}
//});
myCustomScrollbarForForm({
	obj:".scroll-content",
	advanced : {
		theme : "minimal-dark",
		updateOnContentResize : true
	}
});
$(".cancel").bind("click", function(event){
	window.location.href="/factor/component/frontview/VwEntrance.jsp";
});
var readerWX = new FileReader();
function wxImgChange(e) {
//     var size = e.target.files[0].size;
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$("[name=wxType]").val(arr[1]);
		if(filePath.length>40){
			filePath="..."+filePath.substring(filePath.length-40, filePath.length);
		}
		$("#showWXJust").text(filePath);
	    readerWX.onload = (function (file) {
	        return function (event) {
	        	var img64Str=event.target.result;
	            var strs= new Array(); //定义一数组
				strs=img64Str.split(","); //字符分割
				var imgMsg = strs[1];
				$("[name=wechatStr]").val(imgMsg);
	        };
	    })(e.target.files[0]);
	    readerWX.readAsDataURL(e.target.files[0]);
	}
}
var readerQQ = new FileReader();
function qqImgChange(e){
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$("[name=qqType]").val(arr[1]);
		if(filePath.length>40){
			filePath="..."+filePath.substring(filePath.length-40, filePath.length);
		}
		$("#showQQJust").text(filePath);
		readerQQ.onload = (function (file) {
	        return function (event) {
	        	var img64Str=event.target.result;
	            var strs= new Array(); //定义一数组
				strs=img64Str.split(","); //字符分割
				var imgMsg = strs[1];
				$("[name=qqGroupStr]").val(imgMsg);
	        };
	    })(e.target.files[0]);
	    readerQQ.readAsDataURL(e.target.files[0]);
	}
}

function myUpdateAjax(dom){//新增方法
	var url = $(dom).attr("action");
	var dataParam = JSON.stringify($(dom).serializeArray());
	LoadingAnimate.start();
	$.ajax({
		url:url,
		data : {
			ajaxData : dataParam
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			LoadingAnimate.stop(); 
			if (data.flag == "success") {
				top.updateFlag=true;
				window.top.alert(data.msg, 1);
				window.location.href=webPath+'/vwSetManage/getById';
			} else {
				window.top.alert(data.msg, 0);
			}
		},
		error : function() {
			LoadingAnimate.stop(); 
			alert(top.getMessage("ERROR_REQUEST_URL", url));
		}
	});
}

function uploadWXImg(){
	$("#wxFile").click();
}
function uploadQQImg(){
	$("#qqFile").click();
}
function showWXImg(){
	var fileStr=readerWX.result;
	showImg('WX',fileStr);
}
function showQQImg(){
	var fileStr=readerQQ.result;
	showImg('QQ',fileStr);
}
function showImg(type,fileStr){
	if(fileStr==null||fileStr==''){
		var str="";
		LoadingAnimate.start();
		$.ajax({
			url:webPath+'/vwSetManage/getImgBase64',
			data:'type='+type,
			dataType:'json',
			success:function(data){
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
					str=data.str;
					if(data.str==null||data.str==''){
						window.top.alert("请上传图片后再试！",0);
					}else{
						layerShowImg(type,str);
						LoadingAnimate.stop(); 
					}
				}else{
					window.top.alert(data.msg, 0);
				}
			}
		})
	}else{
		layerShowImg(type,fileStr);
	}
}
function layerShowImg(type,str){
	$("#template").attr("src",str);
	if(type=="WX"){
		$("#template").attr("alt","微信二维码");
	}else{
		$("#template").attr("alt","QQ群二维码");
	}
	$("#template").click();
}