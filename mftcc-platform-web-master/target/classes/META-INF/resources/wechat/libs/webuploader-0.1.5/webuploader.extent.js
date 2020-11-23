function mobileUpload(uploadname,uploadurl) {
	var percentages = {}, imageOrderKey = [], imageBrowser;
	var uploadBtnId = uploadname+"_btn";
	var uploadListId = uploadname+"_list";
	$(function() {
		var uploader = WebUploader.create({
			// 是否自动上传
			auto : true,
			server : uploadurl,
			// 指定按钮
			pick : {
				"id" : "#"+uploadBtnId
			},
			// 开起分片上传
			chunked : false,
			fileVal : "upload",
			// 文件类型
			accept : {
				title : 'Images',
				extensions : 'gif,jpg,jpeg,bmp,png',
				mimeTypes : 'image/*'
			},
			allowMagnify : false
		});
		uploader.on('ready', function() {
			window.uploader = uploader;
		});
		function addFile(uploader, file, ret) {
			var $ui = $("#"+uploadListId);
			var $li = $('<li id="'
					+ file.id
					+ '" class="weui_uploader_file weui_uploader_status" style="background-image:url('
					+ ret + ')"></li>');
			var $progress = $('<div class="weui_uploader_status_content">0%</div>');
			$li.append($progress);
			$ui.append($li);
			imageOrderKey[file.id] = false;
			imageOrderKey.push(file.id);
		}
		// 负责view的销毁
		function removeFile(file) {
			var $li = $('#' + file.id);
			delete percentages[file.id];
			$li.off().remove();
		}

		function uploadError(file) {
			var $li = $('#' + file.id);
			$li.on("click", function() {
				$.modal({
					title : "文件上传",
					text : "是否尝试重传文件！",
					buttons : [ {
						text : "尝试重传",
						onClick : function() {
							uploader.retry(file);
						}
					}, {
						text : "取消",
						className : "default",
						onClick : function() {
							uploader.removeFile(file);
						}
					}, ]
				});
			});
		}

		// 当有文件添加进来的时候
		uploader.on('fileQueued', function(file) {
			percentages[file.id] = [ file.size, 0 ];
			console.log("有文件添加进来");
			uploader.makeThumb(file, function(error, ret) {
				if (error) {
					console.log("预览错误");
				} else {
					addFile(uploader, file, ret);
				}
			});
		});
		// 当有文件移除队列的时候
		uploader.on('fileDequeued', function(file) {
			removeFile(file);
		});
		// 某个文件开始上传前触发，一个文件只会触发一次。
		uploader.on('uploadStart', function(file) {
			console.log('正在上传' + file.name);
		});
		uploader.on('uploadProgress', function(file, percentage) {
			percentages[file.id][1] = percentage;
			$("#"+uploadListId).find("#" + file.id).find(
					".weui_uploader_status_content").text(
					Math.round(percentage * 100) + '%');
			console.log(file.name + "上传进度" + percentage);
		});
		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		uploader
				.on(
						'uploadSuccess',
						function(file, response) {
							if (response.flag == "true"
									|| response.flag == true) {
								$("#"+uploadListId).find("#" + file.id)
										.removeClass("weui_uploader_status");
								$("#"+uploadListId).find("#" + file.id).find(
										".weui_uploader_status_content")
										.remove();
								var imageItems = [];
								imageOrderKey[file.id] = response.path;
								for ( var i = 0; i < imageOrderKey.length; i++) {
									if (imageOrderKey[imageOrderKey[i]]) {
										imageItems
												.push("../../"
														+ imageOrderKey[imageOrderKey[i]]);
									}
								}
								imageBrowser = $.photoBrowser({
									items : imageItems
								});
								$("#"+uploadListId).find("#" + file.id).off()
										.bind("click", function() {
											console.log($(this).index());
											imageBrowser.open($(this).index());
										});
								console.log("上传成功！", file, response);
							} else {
								$("#"+uploadListId).find("#" + file.id).find(
										".weui_uploader_status_content")
										.remove();
								$("#"+uploadListId)
										.find("#" + file.id)
										.append(
												'<div class="weui_uploader_status_content"><i class="weui_icon_warn"></i></div>');
								uploadError(file);
								$.alert(response.msg);
								imageOrderKey[file.id] = false;
								console.log("上传失败！", file, response);
							}
						});
		uploader.on('uploadError', function(file, response) {
			$("#"+uploadListId).find("#" + file.id).find(
					".weui_uploader_status_content").html(
					'<i class="weui_icon_warn"></i>');
			console.log("上传失败！", file, response);
			imageOrderKey[file.id] = false;
		});
		// 文件上传完成时触发，不论是否成功
		uploader.on('uploadComplete', function(file) {

			console.log("上传完成！", file);
		});
		uploader.onError = function(code) {
			if (code == "F_DUPLICATE") {
				$.alert('Error: ' + "文件已存在！");
			} else {
				alert('Error: ' + code);
			}
		};
	});
}
