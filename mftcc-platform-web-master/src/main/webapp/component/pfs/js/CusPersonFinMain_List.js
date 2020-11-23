var cusPersonFinMainList = function(window,$){
	var _init = function(){
		$("body").mCustomScrollbar({
			advanced:{ 
				updateOnBrowserResize:true 
			},
			autoHideScrollbar: true
		});
		//处理上传过的财务报表列表
		
		//上传操作	
		var uploader = WebUploader.create({
			auto : true,
			// swf文件路径
			swf : webPath+'/UIplug/webuploader/js/Uploader.swf',
			// 文件接收服务端。
			server : webPath+'/cusFinUploadFiles/uploadReportAjax?cusNo='+cusNo+"&cusType=2",
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
		var $list = $("#thelist");
		//当文件被加入队列之前触 此事件的handler返回值为false，则此文件不会被添加进入队列
		uploader.on('beforeFileQueued', function(file) {
			$("#showMsg").empty();
			$("input[name=uploadFile]").val(file.name);
		});
		//当文件被加入队列以后触发
		uploader.on('fileQueued', function(file) {
			/*var fileId = file.id;
			 //$("input[name=uploadFile]").attr("id",fileId);
			 $("input[name=uploadFile]").val(file.name);
			 $list.append('<a class="delFile" style="float: right; margin-top: -27px;padding-left: 335px;cursor: pointer;">删除</a>');
		    //删除文件操作
		    $(".delFile").click(function(){
		    	var showMsg = $("#showMsg").html();
		    	$("#showMsg").empty();
		    	var fileName = $("input[name=finData]").val();
		    	if(showMsg != ""){
		    		$list.find("a").remove();
		    		$("input[name=uploadFile]").val("");
		    		uploader.removeFile(fileId);
		    	}else{
		    		$.ajax({
						type:"post",
						url:webPath+"/cusFinUploadFiles/deletUploadFileAjax",
						async:false,
						data:{ajaxData:fileName},
						success:function(data){
							if(data.flag=="success"){
								alert(data.msg,1);
								$list.find("a").remove();
					    		$("input[name=uploadFile]").val("");
					    		uploader.removeFile(fileId);
							}else if(data.flag=="error"){
								alert(data.msg,0);
							}
						},error:function(){
							alert(top.getMessage("FAILED_DELETE"),0);
						}
					});
		    	}
		    });*/
		});
		//当文件被移除队列后触发
		uploader.on('fileDequeued', function(file) {
			$("#showInfo-div").empty();
			$("#"+file.id).remove();
		});
		//上传过程中触发，携带上传进度
		uploader.on('uploadProgress', function(file,percentage) {
			LoadingAnimate.start();
		    var $li = $( '#'+file.id ),
		        $percent = $li.find('.progress .progress-bar');
		    // 避免重复创建
		    if (!$percent.length ) {
		        $percent = $('<div class="progress progress-striped active">' +
		          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
		          '</div>' +
		        '</div>').appendTo( $li ).find('.progress-bar');
		    }
		    $percent.css( 'width', percentage * 100 + '%' );
		});
		//当文件上传成功时触发
		uploader.on('uploadSuccess', function(file,data) {
			LoadingAnimate.stop();
		    if(data.flag=="success"){
		    	top.isUpload = true;  //财务报表上传成标志
		    	top.infIntegrity = data.infIntegrity;
		    	//alert(top.getMessage("SUCCEED_UPLOAD"),1);
		    	$(".submitdata").addClass("success");
		    	var fileName = data.fileName;
		    	$("input[name=finData]").val(fileName);
		    	$('#'+file.id ).data("fileName",fileName);
		    	var finRptType = data.finRptType;
		    	$("input[name=finRptType]").val(finRptType);
		    	var finRptDate = data.finRptDate;
		    	$("input[name=finRptDate]").val(finRptDate);
		    	var cusName = data.cusName;
		    	$("input[name=cusName]").val(cusName);
		    	var relationCorpName = data.relationCorpName;
		    	$("input[name=relationCorpName]").val(relationCorpName);
		    	var relationCorpNo = data.corpNo;
		    	$("input[name=relationCorpNo]").val(relationCorpNo);
		    	var teplateType = data.teplateType;  //上传的模板类型
		    	//保存数据成功打开报表详情页面
		    	var url = webPath+"/cusFinMain/inputReportView?finRptType="+finRptType+"&finRptDate="+finRptDate
		    				+"&cusNo="+cusNo+"&accRule=1"+"&teplateType="+teplateType+"&relationCorpNo="+relationCorpNo+"&relationCorpName="+relationCorpName+"&uploadFlag=1";
		    	cusPersonFinMainList.reportView(url);
		    }else if(data.flag=="error"){
		    	LoadingAnimate.stop();
		    	top.isUpload = false; //财务报表上传成标志
		    	$("#showMsg").empty();
		    	$("#showMsg").html(data.resMsg);
		    	uploader.removeFile(file.id);
		    	$('#'+file.id).find('p.state').text('上传出错');
		    }
		});
		//当文件上传出错时触发
		uploader.on('uploadError', function(file) {
		    $('#'+file.id ).find('p.state').text('上传出错');
		});
		//不管成功或者失败，文件上传完成时触发	
		uploader.on('uploadComplete', function(file) {
		    $( '#'+file.id ).find('.progress').fadeOut();
		});
	};
	
	//保存表单时保存上传的excel文件中的数据到数据库中
	var _uploadAndSubmitData = function(finDataVal,cusNo,finRptType,finRptDate,cusName) {
    	var url =webPath+"/cusFinMain/insertFormMoreExcelAjax";
         $.ajax({
         	type:"post",
         	url:url,
         	async:false,
         	data:{
         		allPath:finDataVal,
         		cusNo:cusNo,
         		finRptType:finRptType,
         		finRptDate:finRptDate,
         		cusName:cusName
         	},
         	success:function(data){
         		if(data.flag=="success"){
         			top.addFlag = true;
         			 //触发回调函数
					 $(top.window.document).find("#showDialog .close").click();
         		}else if(data.flag=="error"){
         			alert(data.msg,0);
         		}
         	}
         });
    };
	
	//新增
	var _newFinMain = function(){
		top.addFlag = false;
		window.top.window.showDialog(webPath+'/cusFinMain/input?cusNo='+cusNo,'编辑报表',70,80,function(){
			if(top.addFlag){
				window.location.reload();
			}
		});
	};
	
	//编辑
	var _getFinMain = function(obj,url){
		top.updateFlag = false;
		window.top.window.showDialog(url,'编辑报表',70,80,function(){
			if(top.updateFlag){
				window.location.reload();
			}
		});
	};
	
	//财务报表预览填写
	var _reportView = function (viewUrl){
		var obj = $(top.window.document).find("body");
		obj.find("#bigFormShowiframe").attr("src","");
		obj.find("#bigFormShowiframe").attr("src",viewUrl);

	};
	
	//数据确认
	var _confirmFinMain = function(obj,url){
		LoadingAnimate.start();
		var parm = "?"+url.split("?")[1];
		$.ajax({
			type:"post",
			url:webPath+"/cusFinMain/checkFinDataAjax"+parm,
			dataType:"json",
			success:function(data){
				if(data.flag=="success"){
					if(data.checkFlag == "success"){
						alert(top.getMessage("CONFIRM_OPERATION","数据确认"),2,function(){
							LoadingAnimate.start();
							cusPersonFinMainList.doCofrimData(url);
						});
					}else{
						LoadingAnimate.stop();
                        alert(data.msg,2,function(){
                            LoadingAnimate.start();
                            cusPersonFinMainList.doCofrimData(url);
                        });
					}
				}else if(data.flag=="error"){
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_FINC_VERIFY"),0);
				}
			},error:function(){
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("ERROR_FINC_VERIFY"),0);
			}
		});
	};
	
	//执行确认操作
	var _doCofrimData = function(url){
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag=="success"){
					top.isUpload = true;
					$(".color_theme").css("color","gray");
					window.location.reload();
				}else if(data.flag=="error"){
					window.top.alert(data.msg,0);
				}
			},error:function(){
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("ERROR_FINC_VERIFY"),0);
			}
		});
	};
	
	//导入
	var _importFinMain = function(){
		top.addFlag = false;
		window.top.window.showDialog(webPath+'/cusFinUploadFiles/toUpload?cusNo='+cusNo,'批量导入报表',45,45,function(){
			if(top.addFlag){
				window.location.reload();
			}
		});
	};
	
	//下载模板
	var _exportExcel = function(downType){
		LoadingAnimate.start();
		$.ajax({
			url:webPath+"/cusFinMain/exportPersonExcelAjax",
			data:{
				downType:downType,
				cusNo:cusNo
			},
			dataType:"json",
			type:"POST",
			success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					if(data.exportFlag == "success"){
						window.top.location.href = encodeURI(webPath+"/docUpLoad/getFileDownload_new.action?path="+data.path+"&cusNo="+cusNo+"&downType="+downType);
					}else{
						alert(data.msg,0);
					}
				}else{
					alert(data.msg,0);
				}
			},error:function(){
				LoadingAnimate.stop();
				alert(data.msg,0);
			}
		});
	};
	
	var _uploadFinReport = function(){
		$("#picker").find("input.webuploader-element-invisible").click();
	}
	
	return{
		init:_init,
		newFinMain:_newFinMain,
		getFinMain:_getFinMain,
		reportView:_reportView,
		confirmFinMain:_confirmFinMain,
		importFinMain:_importFinMain,
		exportExcel:_exportExcel,
		uploadAndSubmitData:_uploadAndSubmitData,
		doCofrimData:_doCofrimData,
		uploadFinReport:_uploadFinReport
	};
}(window,jQuery);