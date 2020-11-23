;
var VwLinkManageDetail = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		$("[name=linkFile]").hide();
		$("[name=linkFile]").parent().append("&nbsp;&nbsp;<span id='showJust' onclick='uploadImg()'></span>&nbsp;&nbsp;<div style='float:right;'><a onclick='showImg()'>查看</a>&nbsp;&nbsp;<a onclick='uploadImg()'>上传</a>&nbsp;&nbsp;<a onclick='uploadCancel()'>取消</a></div>");
		$("#showJust").text($("[name=linkFile]").val());//初始值
		$("#showJust").parent().css("line-height","34px");
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
	$(top.window.document).find("#showDialog").remove();
});
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
	}else{//弹出的选择文件对话框选择了取消
		$("#showJust").text($("[name=linkFile]").val());//初始值
		$("#showJust").attr("title","");
		$("[name=fileType]").val("");
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
		$("#imgTest").click();
	});
}
//展示图片
function showImg(){
	var src='';
	if($("#imgTest")==null||$("#imgTest").val()==''){//查看原来上传的图片
		if($("[name=linkFile]").val()!=null&&$("[name=linkFile]").val()!=''){
			src=factorWebUrl+"/vwImage/viewImage?filePath="+$("[name=linkFile]").val();
		}else{
			window.top.alert("您还未上传图片！", 1);
			return false;
		}
	}else{//重新上传了图片
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
	}
	viewerClick(src);
}
function viewerClick(str){
	$("#template").attr("src",str);
	$("#template").click();
}
function uploadCancel(){
	$("#imgTest").val("");
	$("#showJust").text($("[name=linkFile]").val());//初始值
	$("#showJust").attr("title","");
	$("[name=fileType]").val("");
}
