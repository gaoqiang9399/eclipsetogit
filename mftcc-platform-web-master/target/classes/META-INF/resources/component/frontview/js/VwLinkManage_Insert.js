;
var VwLinkManageInsert = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);
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
	window.top.alert("推荐图片像素为230x60,否则可能影响显示效果",3,function(){
		$("#imgTest").click();
	});
});
function imgChange(e) {
//     var size = e.target.files[0].size;
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$("[name=fileType]").val(arr[1]);
		if(filePath.length>50){
			filePath="..."+filePath.substring(filePath.length-50, filePath.length);
		}
		$("[name=showJust]").val(filePath);
		$("[name=showJust]").attr("title",e.target.value);
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
		
	}else{
		$("[name=showJust]").val("");
		$("[name=fileType]").val("");
		$("[name=showJust]").attr("title","");
	}
}
function myInsertAjax(dom){//新增方法
	var url = $(dom).attr("action");
	LoadingAnimate.start();
	$("#insertForm").ajaxSubmit({
		url:url,
		success : function(data) {
			LoadingAnimate.stop(); 
			if (data.flag == "success") {
				top.addFlag=true;
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
function showImg(){
	if($("#imgTest").val()!=null&&$("#imgTest").val()!=""){//说明想上传文件
		var src="";
		LoadingAnimate.start();	
		$("#insertForm").ajaxSubmit({
			url:webPath+'/vwBannerManage/uploadShowImg',
			 async: false,
			success:function(data){
				LoadingAnimate.stop(); 
				if(data.flag=="success"){
					src=data.base64;
				}else{
					window.top.alert(data.msg, 1);
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