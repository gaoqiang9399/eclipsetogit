<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/component/include/common.jsp"%>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${webPath}/component/doc/webUpload/webuploader/webuploader.css" />
	<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
	<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/common.css" />
	<style type="text/css">
* {
				-webkit-box-sizing: content-box;
				-moz-box-sizing: content-box;
				box-sizing: content-box;
			}
	</style>
	<script type="text/javascript" src="${webPath}/component/doc/webUpload/webuploader/webuploader.js"></script>
<title>凭证导入</title>
</head>
<script type="text/javascript">
	$(function() {
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
			server: '${webPath}/cwVchPlateMst/importVchPlateAjax',
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
			$('#import-wrap .step-item').eq(2).show().siblings().hide();
			$('#import-steps >li >span').removeClass('current');
			$('#import-steps >li >span').eq(2).addClass('current');
			//根据服务器返回数据进行处理
			if (response === " ") {
			} else {
				if('error'==response.flag){
					$('#import-result').html(response.msg);
				}else{
					if('success'==response.flag){
						alert(response.msg, 1);
					}
					$('#import-result').html('');
				}
			}

		});
		// 文件上传失败，显示上传出错。
		uploadInstance.on('uploadError', function(file, reason) {
		//	alert('上传失败，请重试！', 1);
			alert(top.getMessage("FAILED_UPLOAD_FILE", ''), 1);
			
			$('#file-path').val('');
		});

		$('#btn-import').on('click', function(e) {
			e.preventDefault();
			if (!$('#file-path').val()) {
				alert(top.getMessage("FIRST_SELECT_FIELD","要上传的文件"), 2);
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
	<div class="mod-toolbar-top">
		<div class="left mod-crumb">
			<a href="${webPath}/cwVoucherMst/getListPage">凭证查询</a>&gt; <span class="cur">Excel导入</span>
		</div>
	</div>
	<div class="wrapper">

		<div class="mod-inner">
			<ul class="mod-steps" id="import-steps">
				<li><span class="current">1.下载模版</span>&gt;</li>
				<li><span class="">2.导入Excel</span>&gt;</li>
				<li><span>3.导入完毕</span></li>
			</ul>
			<div id="import-wrap" class="cf">
				<div id="import-step1" class="step-item" style="">
					<div class="ctn">
						<h3 class="tit">温馨提示：</h3>
						<p>请下载统一的模版，并按相应的格式在Excel软件中填写您的业务数据，然后再导入到系统中。</p>
					</div>
					<a href="${webPath}/cwVchPlateMst/printVchTemplate" class="link">下载模版</a>
					<div class="step-btns">
						<a href="#" class="ui-btn ui-btn-sp" rel="step2">下一步</a>
					</div>
				</div>

				<div id="import-step2" class="step-item" style="display: none;">
<!-- 					<input type="checkbox" id="voucherReSeq" checked="checked" name="voucherReSeq"> <label for="voucherReSeq">凭证重新编号</label> -->
					<div class="ctn file-import-ctn">
						<span class="tit">请选择要导入的Excel文件：</span> <input type="text" name="file-path" id="file-path" class="ui-input" readonly="" autocomplete="false">
						<span id="import-btn-wrap"><span id="import-btn">浏览</span></span>
					</div>
					<div class="step-btns">
						<a href="#" class="ui-btn" rel="step1">上一步</a> <a href="#" class="ui-btn ui-btn-sp" id="btn-import">导入</a>
					</div>
				</div>

				<div id="import-step3" class="step-item" style="display: none;">
					<div class="ctn file-import-ctn" id="import-result"></div>

					<a href="${webPath}/cwVoucherMst/getListPage" class="link">返回凭证列表</a> <a href="#" class="link" id="a_step3">上一步</a>
				</div>

			</div>
		</div>
	</div>
</body>
</html>