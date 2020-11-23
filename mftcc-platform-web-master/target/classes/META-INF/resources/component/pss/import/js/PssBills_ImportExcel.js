;
var PssBills_ImportExcel = function(window, $){
	var _init = function() {
		$('#import-wrap .step-btns a[rel]').bind('click', function(e) {
			var step = $(this).attr('rel').substr(4, 1) - 1;
			if (step < 2) {
				$('#import-wrap .step-item').eq(step).show().siblings().hide();
				$('#import-steps >li >span').removeClass('current');
				$('#import-steps >li >span').eq(step).addClass('current');
			}
			if (step == 1) {
				$('#import-btn').find('div:eq(1)').css('top', '-18px');
				uploadInstance.refresh();
			}
			e.preventDefault();
		});

		$('#a_step3').bind('click', function(e) {
			$('#import-wrap .step-item').eq(1).show().siblings().hide();
			$('#import-steps2 >li >span').removeClass('current');
			$('#import-steps2 >li >span').eq(1).addClass('current');
			e.preventDefault();
		});

		var uploadInstance = WebUploader.create({
			// 选完文件后，是否自动上传。
			auto : false,
			// swf文件路径
			swf : '/factor/component/doc/webUpload/webuploader/Uploader.swf',
			// 文件接收服务端。
			server: serverUrl,
			//参数
			//formData:{"pledgeNo":'${pledgeNo}'},
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : '#import-btn',
			fileVal : 'vch',
			fileSizeLimit : 10 * 1024 * 1024,
			fileNumLimit : 1,
			resize : false,
			duplicate : true,
			contentType : false,
			accept : {
				title : 'excel',
				extensions : 'xls,xlsx',
				mimeTypes : 'application/xls,application/xlsx'
			}
		});
//		uploadInstance.options.server = serverUrl;
		
		uploadInstance.on('beforeFileQueued', function(file) {
			var files = this.getFiles();
			for ( var i = 0; i < files.length; i++) {
				this.removeFile(files[i].id, true);
			}
		});
		// 当有文件添加进来的时候
		uploadInstance.on('fileQueued', function(file) {
			$('#file-path').val(file.name);
		});
		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		uploadInstance.on('uploadSuccess', function(file, response) {
			$('#import-wrap .step-item').eq(2).show().siblings().hide();
			$('#import-steps >li >span').removeClass('current');
			$('#import-steps >li >span').eq(2).addClass('current');
			//根据服务器返回数据进行处理
			if (response === " ") {
			} else {
				if('error'==response.flag){
					var downloadError = '';
					if(response.result != undefined && response.result != null && response.result != ""){
						downloadError = '<a href="' + downloadErrorUrl + '?errorFileUrl=' + response.result + '" class="link">下载错误数据文件</a>';
					}
					$('#import-result').html(response.msg + ',' + downloadError);
				}else{
					$('#import-result').html('');
					/* top.addFlag = true;
					top.htmlStrFlag = true;
					top.htmlString = response.htmlStr;
					top.tableName="pledge_base_info_bill";
					top.pledgeNo='${pledgeNo}'; */
				}
			}

		});
		// 文件上传失败，显示上传出错。
		uploadInstance.on('uploadError', function(file, reason) {
			alert('上传失败，请重试！', 1);
			$('#file-path').val('');
		});

		$('#btn-import').on('click', function(e) {
			e.preventDefault();
			if (!$('#file-path').val()) {
				alert('请选择要上传的文件！', 2);
				return;
			}
			uploadInstance.option('formData', {
				voucherReSeq : $('#voucherReSeq').attr('checked') ? 1 : 0
			});
			uploadInstance.upload();
		});
		
	};
	
	return {
		init : _init
	};
}(window, jQuery);