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
	<script type="text/javascript" src="<%=webPath %>/component/pss/import/js/PssBills_ImportExcel.js"></script>
	<title>进销存单据导入</title>
</head>
	<script type="text/javascript">
		var downloadTmplate =  '${dataMap.downloadTmplate}';
		var serverUrl = '${dataMap.serverUrl}';
		var downloadErrorUrl = '${dataMap.downloadErrorUrl}';
		
		$(function() {
			if(downloadTmplate == undefined || downloadTmplate == ''){
				alert("downloadTmplate参数设置不对", 0);
				return;
			}else if(serverUrl == undefined || serverUrl == ''){
				alert("serverUrl参数设置不对", 0);
				return;
			}else if(downloadErrorUrl == undefined || downloadErrorUrl == ''){
				alert("downloadErrorUrl参数设置不对", 0);
				return;
			}
			PssBills_ImportExcel.init();
		});
	</script>
<body>
	<div class="mod-toolbar-top">
		<div class="left mod-crumb">
			<span class="cur">Excel导入</span>
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
					<a href='${dataMap.downloadTmplate}' class="link">下载模版</a>
					<div class="step-btns">
						<a href="#" class="ui-btn ui-btn-sp" rel="step2">下一步</a>
					</div>
				</div>
				<div id="import-step2" class="step-item" style="display: none;">
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
					<a href="#" class="link" id="a_step3">上一步</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>