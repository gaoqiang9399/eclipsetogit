;
var VwContManageDetail = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		$("[name=fileName]").hide();
		$("[name=fileName]").parent().append("&nbsp;&nbsp;<span id='showJust' onclick='uploadImg()'></span>&nbsp;&nbsp;<div style='float:right;'><a onclick='showImg()'>查看</a>&nbsp;&nbsp;<a onclick='uploadImg()'>上传</a>&nbsp;&nbsp;<a onclick='uploadCancel()'>取消</a></div>");
		$("[name=outLinkImg]").hide();
		$("[name=outLinkImg]").parent().append("&nbsp;&nbsp;<span id='showJust1' onclick='uploadImg1()'></span>&nbsp;&nbsp;<div style='float:right;'><a onclick='showImg1()'>查看</a>&nbsp;&nbsp;<a onclick='uploadImg1()'>上传</a>&nbsp;&nbsp;<a onclick='uploadCancel1()'>取消</a></div>");
		$("#showJust").text($("[name=fileName]").val());//初始值
		$("#showJust").parent().css("line-height","34px");
		$("#showJust1").text($("[name=outLinkImg]").val());//初始值
		$("#showJust1").parent().css("line-height","34px");
		$.ajax({
			url:webPath+'/vwContManage/getAllMenu',
			dataType:'json',
			type:'post',
			success:function(data){
				var list=data.list;
				for(var i=0;i<list.length;i++){
					$("[name=block_1]").append("<option value=\""+list[i].id+"\">"+list[i].name+"</option>");
				}
				$("[name=block_1]").val($("[name=block]").val());
			}
		})
		var id=$("[name=id]").val();
		$.ajax({
			url:webPath+'/vwContManage/getContentById',
			dataType:'json',
			data:'id='+id,
			type:'post',
			success:function(data){
				if (data.flag == "success") {
					ue.addListener("ready", function () {  
			               // editor准备好之后才可以使用  
						ue.setContent(data.str);  
			  
			       });  
				}else{
					window.top.alert("读取内容失败！",0);
				}
			}
		});
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
	 $(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
});
var ue = UM.getEditor('content', {
	width : "100%"
});
//var reader = new FileReader();
function imgChange(e) {
//     var size = e.target.files[0].size;
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$("[name=fileType]").val(arr[1]);
		if(filePath.length>40){
			filePath="..."+filePath.substring(filePath.length-40, filePath.length);
		}
		$("#showJust").text(filePath);
		$("#showJust").attr("title",e.target.value);
//	    reader.onload = (function (file) {
//	        return function (event) {
//	        	var img64Str=event.target.result;
//	            var strs= new Array(); //定义一数组
//				strs=img64Str.split(","); //字符分割
//				var imgMsg = strs[1];
//				$("[name=base64]").val(imgMsg);
//	        };
//	    })(e.target.files[0]);
//	    reader.readAsDataURL(e.target.files[0]);
	}else{//弹出的选择文件对话框选择了取消
		$("#showJust").text($("[name=fileName]").val());//初始值
		$("#showJust").attr("title","");
		$("[name=fileType]").val("");
	}
}
//var reader1 = new FileReader();
function imgChange1(e){
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$("[name=fileType1]").val(arr[1]);
		if(filePath.length>40){
			filePath="..."+filePath.substring(filePath.length-40, filePath.length);
		}
		$("#showJust1").text(filePath);
		$("#showJust1").attr("title",e.target.value);
//		reader1.onload = (function (file) {
//	        return function (event) {
//	        	var img64Str=event.target.result;
//	            var strs= new Array(); //定义一数组
//				strs=img64Str.split(","); //字符分割
//				var imgMsg = strs[1];
//				$("[name=base641]").val(imgMsg);
//	        };
//	    })(e.target.files[0]);
//	    reader1.readAsDataURL(e.target.files[0]);
	}else{//弹出的选择文件对话框选择了取消
		$("#showJust1").text($("[name=fileName]").val());//初始值
		$("#showJust1").attr("title","");
		$("[name=fileType1]").val("");
	}
}

function myUpdateAjax(dom){//新增方法
	var url = $(dom).attr("action");
	LoadingAnimate.start();
	$("#updateForm").ajaxSubmit({
		url:url,
		success : function(data) {
			LoadingAnimate.stop(); 
			if (data.flag == "success") {
				top.updateFlag=true;
				window.top.alert(data.msg, 1);
				 $(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
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

function uploadImg(){
	window.top.alert("推荐图片像素为230x60,否则可能影响显示效果",3,function(){
		$("#file").click();
	});
}
function uploadImg1(){
	window.top.alert("推荐图片像素为230x60,否则可能影响显示效果",3,function(){
		$("#file1").click();
	});
}
function showImg(){
	var src='';
	if($("#file")==null||$("#file").val()==''){//查看原来上传的图片
		if($("[name=fileName]").val()!=null&&$("[name=fileName]").val()!=''){
			//src=webPath+"/vwBannerManage/viewImage?uploadFileName="+$("[name=fileName]").val();
            src=factorWebUrl+'/vwImage/viewImage?filePath='+$('[name=fileName]').val();
		}else{
			window.top.alert("您还未上传图片！", 1);
			return false;
		}
	}else{//重新上传了图片
		$("#file1").attr("disabled",true);
		LoadingAnimate.start();	
		$("#updateForm").ajaxSubmit({
			url:webPath+'/vwBannerManage/uploadShowImg',
			async: false,
			success:function(data){
				LoadingAnimate.stop(); 
				if(data.flag=="success"){
					src=data.base64;
				}else{
					window.top.alert("图片上传失败", 0);
				}
			},error:function(){
				LoadingAnimate.stop(); 
			}
		})
		$("#file1").attr("disabled",false);
	}
	$("#template").attr("src",src);
	$("#template").attr("alt","展示图片");
	$("#template").click();
}
function showImg1(){
	var src='';
	if($("#file1")==null||$("#file1").val()==''){//查看原来上传的图片
		if($("[name=outLinkImg]").val()!=null&&$("[name=outLinkImg]").val()!=''){
			//src=webPath+"/vwBannerManage/viewImage?uploadFileName="+$("[name=outLinkImg]").val();
			src=factorWebUrl+'/vwImage/viewImage?filePath='+$('[name=outLinkImg]').val();
		}else{
			window.top.alert("您还未上传图片！", 1);
			return false;
		}
	}else{//重新上传了图片
		$("#file").attr("disabled",true);
		$("#file1").attr("name","upload");
		LoadingAnimate.start();	
		$("#updateForm").ajaxSubmit({
			url:webPath+'/vwBannerManage/uploadShowImg',
			async: false,
			success:function(data){
				LoadingAnimate.stop(); 
				if(data.flag=="success"){
					src=data.base64;
				}
			},error:function(){
				LoadingAnimate.stop(); 
			}
		})
		$("#file1").attr("name","upload1");
		$("#file").attr("disabled",false);
	}
	$("#template").attr("src",src);
	$("#template").attr("alt","外部链接图片");
	$("#template").click();
}
function uploadCancel(){
	$("#file").val("");
	$("#showJust").text($("[name=fileName]").val());//初始值
	$("#showJust").attr("title","");
	$("[name=fileType]").val("");
}
function uploadCancel1(){
	$("#file1").val("");
	$("#showJust1").text($("[name=outLinkImg]").val());//初始值
	$("#showJust1").attr("title","");
	$("[name=fileType1]").val("");
}
