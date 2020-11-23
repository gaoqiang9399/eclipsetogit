;
var MfAssetManage_List = function(window, $) {
	var _init = function() {
		
		//上传操作	
		var uploader = WebUploader.create({
			auto : true,
			// swf文件路径
			swf : webPath+'/UIplug/webuploader/js/Uploader.swf',
			// 文件接收服务端。
			server : webPath+'/mfCusWhitename/uploadAjax',
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : '#picker',
			// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			fileVal : 'cusFinUpload',
			resize : false,
			fileNumLimit : 1,   //验证文件总数量, 超出则不允许加入队列
			accept : {
				title : 'Excl',
				extensions : 'xls,xlsx',
				mimeTypes : '.xls,.xlsx'
			}
		});
		//当文件上传成功时触发
		uploader.on('uploadSuccess', function(file,data) {
			LoadingAnimate.stop();
		    if(data.flag=="success"){
		    	if(!data.sign){
		    		alert(data.dataMsg,0);
		    	}else{
		    		alert(data.dataMsg,1);
		    	}
		    	window.updateTableData();
		    }else if(data.flag=="error"){
		    	alert(data.dataMsg,0);
		    }
		    uploader.removeFile(file);
		});
	};

	//申请新增
	 var _finForminput = function(){
		 if(applyFlag=='0'){
			 top.window.openBigForm(webPath+'/mfAssetManage/input?applyFlag='+applyFlag, '依法收贷申请',function(){
			 		window.updateTableData();
			 	});
		 }else{
			 top.window.openBigForm(webPath+'/mfAssetManage/input?applyFlag='+applyFlag, '诉讼申请',function(){
			 		window.updateTableData();
			 	});
		 }
	 	
	 };
	/* //详情页面
	 var _getDetailPage = function (obj,url){
			top.LoadingAnimate.start();		
			window.location.href=url;			
		};*/
	//查看详情   详情弹框
	//demo新增
		var _ajaxDelete = function(obj,urlArgs){
			//var id = url.split("?")[1].split("=")[1];
			var url = webPath+urlArgs;
				jQuery.ajax({
					url:url,
					data:{},
					type:"POST",
					dataType:"json",
					beforeSend:function(){ 
						LoadingAnimate.start();
					},success:function(data){
						if(data.flag == "success"){
							alert(data.msg,1);
							//回调处理
							window.updateTableData();
						}else if(data.flag == "error"){
							 alert(data.msg,0);
						}
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					},complete:function(){
						LoadingAnimate.stop();
					}
				});
			
		};
		 var _getByIdThis =function(obj,url){
		 	top.openBigForm(url,"查看详情",function(){
		 		//回调处理
		 		window.updateTableData();
			});
		 };
			
	return {
		init:_init,
		finForminput:_finForminput,
		getByIdThis:_getByIdThis,
		ajaxDelete:_ajaxDelete
	};
}(window, jQuery);
