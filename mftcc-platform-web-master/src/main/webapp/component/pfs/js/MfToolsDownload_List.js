var toolsDownloadList = function(window,$){
	var _init = function(){
		$("body").mCustomScrollbar({
			advanced:{ 
				updateOnBrowserResize:true 
			},
			autoHideScrollbar: true
		});
	};
		//下载工具
	var _downloadTool = function(obj,url){
		var serialNo=url.split("?")[1].split("=")[1];
		window.top.location.href = encodeURI(webPath+"/mfToolsDownload/downloadTool?serialNo="+serialNo);
//		$.ajax({
//			url:webPath+"/mfToolsDownload/downloadToolAjax",
//			data:{
//				serialNo:serialNo
//			},
//			dataType:"json",
//			type:"POST",
//			success:function(data){
//				if(data.flag == "success"){
//						window.top.location.href = encodeURI(webPath+"/docUpload/ToolDownload?path="+data.path);
//						//window.top.location.href = encodeURI("DocUploadAction_fileDownload_new.action?serialNo="+serialNo);
//					}else{
//						alert(data.msg,0);
//					}
//			},error:function(){
//				alert(data.msg,0);
//			}
//		});
	};
	
	var _uploadFinReport = function(){
		$("#picker").find("input.webuploader-element-invisible").click();
	}
	
	return {
		init:_init,
		downloadTool:_downloadTool
	};
}(window,jQuery);