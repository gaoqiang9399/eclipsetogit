;
var MfFrontKindDescription = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		//替换上传图片占位
		$("[name=kindIconPath]").hide();
		$("[name=kindIconPath]").parent().append("&nbsp;&nbsp;<span id='showJust' onclick='uploadImg()'></span>&nbsp;&nbsp;<a onclick='showImg()'>查看图片</a>&nbsp;&nbsp;<a onclick='uploadImg()'>上传图片</a>");
		$("[name=fileName]").val($("[name=kindIconPath]").val());//无论生成还是更新图片的名字不会变，只有后缀会变。
		var kindNoVal=$("[name='kindNo']").val();
		$.ajax({
			url:webPath+'/mfFrontKind/getContentByKindNoAjax',
			dataType:'json',
			data:'kindNo='+kindNoVal,
			type:'post',
			success:function(data){
				if (data.flag == "success") {
					ue.setContent(data.str);
				}else{
					window.top.alert("读取内容失败！",0);
				}
			}
		});
	};
	var _myclose=function(){
		myclose_click();//统一关闭表单
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		myclose:_myclose
	};
}(window, jQuery);
//滚动条设置
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
var ue = UM.getEditor('content', {
	width : "100%"
});


function updateContentAjax(dom){//新增方法
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
			var flag=true;
			if (data.flag == "success") {
				if($("#imgTest").val()!=null&&$("#imgTest").val()!=""){//说明想重新上传文件
					$("[name=fileName]").val(data.fileName);//新增信息时先确定了文件名
					flag=submitImg();
				}
				LoadingAnimate.stop(); 
				if(flag){
//					top.updateFlag=true;
					window.top.alert(data.msg, 1);
					myclose_click();//统一关闭表单
				}else{
					window.top.alert("图片上传失败！", 1);
				}
			} else {
				LoadingAnimate.stop(); 
				window.top.alert(data.msg, 0);
			}
		},
		error : function() {
			LoadingAnimate.stop(); 
			alert(top.getMessage("ERROR_REQUEST_URL", url));
		}
	});
}
//上传相关
//var reader = new FileReader();//低版本浏览器暂不支持此js对象-_-
function imgChange(e) {
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$("[name=fileType]").val(arr[1]);
		if(filePath.length>40){
			filePath="..."+filePath.substring(filePath.length-40, filePath.length);
		}
		$("#showJust").text(filePath);
	}else{//弹出的选择文件对话框选择了取消
		$("#showJust").text("");
		$("[name=fileType]").val("");
	}
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
	});
	return flag;
}
//弹出选择文件窗口推荐图片像素为1920x300,否则可能影响显示效果
function uploadImg(){
	window.top.alert("推荐图片像素为300x300,否则可能影响显示效果",3,function(){
		$("#imgTest").click();
	}); 
}
//展示图片
function showImg(){
	var src='';
	if($("#imgTest")==null||$("#imgTest").val()==''){//查看原来上传的图片
		if($("[name=kindIconPath]").val()!=null&&$("[name=kindIconPath]").val()!=''){
			src=webPath+"/vwBannerManage/viewImage?uploadFileName="+$("[name=kindIconPath]").val();
		}else{
			window.top.alert("您还未上传图片！", 1);
			return false;
		}
	}else{//重新上传了图片
		LoadingAnimate.start();	
		$("#fileForm").ajaxSubmit({
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
		});
	}
	viewerClick(src);
}
function viewerClick(str){
	$("#template").attr("src",str);
	$("#template").click();
}