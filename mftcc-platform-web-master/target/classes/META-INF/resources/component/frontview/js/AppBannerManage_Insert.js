;
var AppBannerManageInsert = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		getMaxSort("[name=type]");
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
$("[name=showJust]").bind("click", function(event){
	window.top.alert("推荐图片像素为1024x406,否则可能影响显示效果", 3,function(){
		$("#imgTest").click();
	});
});
function imgChange(e) {
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$("[name=fileType]").val(arr[1]);
		if(filePath.length>50){
			filePath="..."+filePath.substring(filePath.length-50, filePath.length);
		}
		$("[name=showJust]").val(filePath);
	}else{//弹出的选择文件对话框选择了取消
		$("[name=showJust]").val("");
		$("[name=fileType]").val("");
	}
}
function myInsertAjax(dom){//新增方法
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
				var uploadFlag="";
				if($("#imgTest").val()!=null&&$("#imgTest").val()!=""){//说明想重新上传文件
					$("[name=fileName]").val(data.fileName);//新增信息时先确定了文件名
					uploadFlag=submitImg();
				}else{
					uploadFlag=true;
				}
				if(uploadFlag){
					top.addFlag=true;
					window.top.alert(data.msg, 1);
					$(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
				}else{
					window.top.alert("图片上传失败！", 1);
				}
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
function showImg(){
	if($("#imgTest").val()!=null&&$("#imgTest").val()!=""){//说明想上传文件
		var src="";
		LoadingAnimate.start();	
		$("#fileForm").ajaxSubmit({
			url:webPath+'/vwBannerManage/uploadShowImg',
			 async: false,
			success:function(data){
				LoadingAnimate.stop(); 
				if(data.flag=="success"){
					src=data.base64;
				}else{
					
				}
			},error:function(){
				LoadingAnimate.stop(); 
			}
		})
		$("#template").attr("src",src);
		$("#template").click();
	}else{
		window.top.alert("请上传图片后再试！", 1);
	}
}
function getMaxSort(dom){//1手机
	var type=$(dom).val();
	$.ajax({
		url:webPath+'/vwBannerManage/getMaxSort',
		dataType:'json',
		type:'post',
		data:'type='+type,
		success:function(data){
			if(data.flag=="error"){
			}else{
				$("[name=sort]").val(parseInt(data.maxSort)+1);
			}
		}
	})
}
//ajax提交图片
function submitImg(){
	var flag=false;
	$("#fileForm").ajaxSubmit({
			async: false,
			success:function(data){
				if (data.flag == "success") {
					flag=true;
				}
			}
	})
	return flag;
}
