<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/plugin/viewer.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/component/archives/plugin/viewer.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/model/js/templateIncludePage.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileListByPage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
<script type="text/javascript" src="${webPath}/component/archives/js/ArchiveInfoMain_Insert.js"></script>
<script type="text/javascript">
	var archiveMainNo='${archiveMainNo}';
	var cusNo='${cusNo}';
	var appId='${appId}';
	var pactId='${pactId}';
	var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId ;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
    $(function () {
		$.ajax({
			url:"${webPath}/archiveInfoMain/getVoucherReceiveAjax?archiveMainNo="+archiveMainNo,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("#bizConfigs").html(data.bizConfigs);
					$("#bizConfigs td[mytitle]:contains('...')").initMytitle();
					$("#template_div").show();
					$(".docType").each(function(){
						$(this).html("<img src = '"+webPath+"/component/doc/webUpload/image/word.png' height='20px' width='20px' style='margin-top:10px;'>");
					});
				} else {
					window.top.alert(data.msg, 0);
				}
			}, error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
			}, complete:function(){
				window.top.loaded();
			}
		});
	})

	function closeWindow(){
		myclose_click();
	};
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content" id="quanxuan">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div name="stampTemplateInfo" title="文档信息" class="dynamic-block list-table" >
				<div id="template_div" class="template_div" style="display: none;">
					<div class="list-table">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>电子文档</span>
							<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#bizConfigs">
								<i class="i i-close-up"></i>
								<i class="i i-open-down"></i>
							</button>
							<%--<button type="button" class=" btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.downloadSelTemplateZip();">打包下载</button>--%>
							<%--<button type="button" class="btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.checkAll();">全选</button>--%>
						</div>
						<div id="bizConfigs" class="content collapse in" aria-expanded="true"></div>
						<div id="qrcode"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="closeWindow();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>
