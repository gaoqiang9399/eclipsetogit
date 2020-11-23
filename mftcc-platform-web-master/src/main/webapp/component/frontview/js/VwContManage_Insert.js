;
var VwContManageInsert = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		$.ajax({
			url:webPath+'/vwContManage/getAllMenu',
			dataType:'json',
			type:'post',
			success:function(data){
			var list=data.list;
				for(var i=0;i<list.length;i++){
					var menuType=list[i].menuType;
					var menuTypeName='';
					if(menuType=='0'){
                        menuTypeName='--内容';
					}
                    if(menuType=='1'){
                        menuTypeName='--列表';
                    }
					$("[name=block]").append("<option value=\""+list[i].id+"\">"+list[i].name+menuTypeName+"</option>");
				}
			}
		})
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
	$("#imgTest").click();
});
$("[name=showJust1]").bind("click", function(event){
	$("#imgTest1").click();
});
var ue = UM.getEditor('content', {
		width : "100%"
    });
//var reader = new FileReader();//低版本浏览器不支持此js对象-_-
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
//var reader1 = new FileReader();
function imgChange1(e) {
//     var size = e.target.files[0].size;
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$("[name=fileType1]").val(arr[1]);
		if(filePath.length>50){
			filePath="..."+filePath.substring(filePath.length-50, filePath.length);
		}
		$("[name=showJust1]").val(filePath);
		$("[name=showJust1]").attr("title",e.target.value);
//	    reader1.onload = (function (file) {
//	        return function (event) {
//	        	var img64Str=event.target.result;
//	            var strs= new Array(); //定义一数组
//				strs=img64Str.split(","); //字符分割
//				var imgMsg = strs[1];
//				$("[name=base641]").val(imgMsg);
//	        };
//	    })(e.target.files[0]);
//	    reader1.readAsDataURL(e.target.files[0]);
	}else{
		$("[name=showJust1]").val("");
		$("[name=fileType1]").val("");
		$("[name=showJust1]").attr("title","");
	}
}
function myInsertAjax(dom){//新增方法
	//检查是否需要提示 菜单内容不是列表，会覆盖原来的数据
	$.ajax({
		url:webPath+'/vwContManage/checkCountAjax?block='+$("[name=block]").val(),
		dataType:'json',
		type:'POST',
		success:function(data){
			if(data.flag=="success"){
				if(data.data=="Y"){//会覆盖
					window.top.alert("此次新增的内容会覆盖之前新增的内容，确定要新增吗？", 2,function(){
						var url = $(dom).attr("action");
						var dataParam = JSON.stringify($(dom).serializeArray());
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
					});
				}else{//直接新增
					var url = $(dom).attr("action");
					var dataParam = JSON.stringify($(dom).serializeArray());
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
			}else{
				window.top.alert("系统异常", 0);
			}
		}
	})
}
function showImg(){//查看第一个图片
	if($("#imgTest").val()!=null&&$("#imgTest").val()!=""){//说明想上传文件
		//把第二个图片设置为disabled，不提交
		$("#imgTest1").attr("disabled",true);
		var src="";
		LoadingAnimate.start();	
		$("#insertForm").ajaxSubmit({
			url:webPath+'/vwBannerManage/uploadShowImg',
			success:function(data){
				LoadingAnimate.stop(); 
				if(data.flag=="success"){
					src=data.base64;
					$("#template").attr("src",src);
					$("#template").attr("alt","展示图片");
					$("#template").click();
				}else{
					window.top.alert(data.msg, 1);
				}
			},error:function(){
				$("#imgTest1").attr("disabled",false);
				LoadingAnimate.stop(); 
			}
		})
		$("#imgTest1").attr("disabled",false);
	}else{
		window.top.alert("请上传图片后再试！", 1);
	}
}
function showImg1(){
	if($("#imgTest1").val()!=null&&$("#imgTest1").val()!=""){//说明想上传文件
		//把第一个图片设置为disabled，不提交,第二个图片的name值换为action对应的name值。
		$("#imgTest").attr("disabled",true);
		$("#imgTest1").attr("name","upload");
		/////////////////////////////////////////////////////
		var src="";
		LoadingAnimate.start();	
		$("#insertForm").ajaxSubmit({
			url:webPath+'/vwUpload/upload?folder='+folder,
			success:function(data){
				LoadingAnimate.stop(); 
				$("#imgTest1").attr("disabled",false);
				if(data.flag=="success"){
					src=data.base64;
					$("#template").attr("src",src);
					$("#template").attr("alt","外部链接图片");
					$("#template").click();
				}else{
					window.top.alert(data.msg, 1);
				}
			},error:function(){
				$("#imgTest1").attr("disabled",false);
				LoadingAnimate.stop(); 
			}
		})
		//////////////////还原////////////////
		$("#imgTest").attr("disabled",false);
		$("#imgTest1").attr("name","upload1");
		////////////////////////////////////
	}else{
		window.top.alert("请上传图片后再试！", 1);
	}
}