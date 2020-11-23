<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${webPath}component/doc/webUpload/webuploader/webuploader.css" />
	<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
	<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/common.css" />
	<style type="text/css">
* {
				-webkit-box-sizing: content-box;
				-moz-box-sizing: content-box;
				box-sizing: content-box;
			}
	</style>
	<script type="text/javascript" src="${webPath}component/doc/webUpload/webuploader/webuploader.js"></script>
<title>凭证导入</title>
</head>
<script type="text/javascript">
	$(function() {
		
		var uploadInstance = WebUploader.create({
			// 选完文件后，是否自动上传。
			auto : false,
			// swf文件路径
			swf : '/factor/component/doc/webUpload/webuploader/Uploader.swf',
			// 文件接收服务端。
			server: '${webPath}/mfMoveableCompensationConfirm/importPledgeBillPlateAjax',
			//参数
			formData:{"pledgeNo":'${pledgeNo}'},
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
			//根据服务器返回数据进行处理
			if (response === " ") {
			} else {
				if('error'==response.flag){
					//$('#import-result').html(response.msg);
					window.top.alert(response.msg, 0);
				}else{
					parent.dialog.get('pledgeBillDialog').close(response);
				}
			}

		});
		// 文件上传失败，显示上传出错。
		uploadInstance.on('uploadError', function(file, reason) {
			alert(top.getMessage("FAILED_UPLOAD_FILE"),0);
			$('#file-path').val('');
		});

		$('#btn-import').on('click', function(e) {
			e.preventDefault();
			if (!$('#file-path').val()) {
				alert(top.getMessage("ERROR_UPLOAD_FILE_TYPE","xls,xlsx"),0);
				return;
			}
			uploadInstance.option('formData', {
				voucherReSeq : $('#voucherReSeq').attr('checked') ? 1 : 0
			});
			uploadInstance.upload();
		});
		
	});
</script>
<body>
	<div class="wrapper">

		<div class="mod-inner">
			<div id="import-wrap" class="cf">
				<div id="import-step2" class="step-item" >
					<div class="ctn file-import-ctn">
						<span class="tit">请选择要导入的Excel文件：</span> <input type="text" name="file-path" id="file-path" class="ui-input" readonly="" autocomplete="false">
						<span id="import-btn-wrap"><span id="import-btn">浏览</span></span>
					</div>
					<div class="step-btns">
						<a href="#" class="ui-btn ui-btn-sp" id="btn-import">导入</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>