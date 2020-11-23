var BusImportExcel = function(window,$){
	var _init = function(){
		$("body").mCustomScrollbar({
			advanced:{ 
				updateOnBrowserResize:true 
			},
			autoHideScrollbar: true
		});
		var $list = $("#thelist");
		//当文件被加入队列之前触 此事件的handler返回值为false，则此文件不会被添加进入队列
		uploader.on('beforeFileQueued', function(file) {
			$("#showMsg").empty();
			$("input[name=uploadFile]").val(file.name);
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
		    	alert("上传成功");
                _renovateBank();
		    	//alert("6666");
		       // DIALOG.confirm("上传成功，是否继续上传","2",_inputBank(),"");
		    }else if(data.flag=="error"){
		    	LoadingAnimate.stop();
		    	top.isUpload = false; //上传成标志
		    	alert(data.result);
		    	_inputBank();
		    }
		});
		//当文件上传出错时触发
		uploader.on('uploadError', function(file) {
			alert(data.result);
		    $('#'+file.id ).find('p.state').text('上传出错');
		});
		//不管成功或者失败，文件上传完成时触发	
	/*	uploader.on('uploadComplete', function(file) {
		    $( '#'+file.id ).find('.progress').fadeOut();
		});*/
	};
	
	//保存表单时保存上传的excel文件中的数据到数据库中
	var _uploadAndSubmitData = function(finDataVal,cusNo,finRptType,finRptDate,cusName) {
    	var url =webPath+"/importexcel/mfCusImportExcel/checkImportExcel";
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

    var   _renovateBank  =   function  (){
        window.location.href=webPath+"/mfBusFincApp/getListPageBank";
    }


	return{
		init:_init,
		uploadAndSubmitData:_uploadAndSubmitData,
        inputBank:_inputBank,
        renovateBank:_renovateBank,
        exportExcel:_exportExcel
	};
}(window,jQuery);