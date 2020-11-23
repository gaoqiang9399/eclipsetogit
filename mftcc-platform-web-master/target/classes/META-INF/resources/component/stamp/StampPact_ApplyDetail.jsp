<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/stamp/js/MfStampPact_input.js"></script>
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/templateIncludePage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileListByPage.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    var stampBaseId = '${mfStampPact.id}';
    var appId  = '${mfStampPact.appId}';
    var cusNo  = '${mfStampPact.cusNo}';
    var queryType  = '${queryType}';
    var temParm = 'appId=' + appId+"&creditAppId="+ '${mfStampPact.creditAppId}'+"&cusNo="+ cusNo ;
    $(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})
        templateIncludePage.init();
        $("#mfBusPactExtendList").find("table").show();
	})
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<form method="post" id="stampCreditForm" theme="simple" name="operform" action="${webPath}/mfStampPact/submitUpdateAjax">
					<dhcc:bootstarpTag property="formstampApplyDetail" mode="query"/>
				</form>
			</div>
			<div class="list-table" id="mfBusPactExtendListDiv">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>非系统生成相关合同</span>
					<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusPactExtendList">
						<i class="i i-close-up"></i><i class="i i-open-down"></i>
					</button>
				</div>
				<div class="content collapse in" id="mfBusPactExtendList" name="mfBusPactExtendList">
					${tableHtml}
				</div>
			</div>
			<div name="stampTemplateInfo" title="文档信息" class="dynamic-block list-table" >
				<div id="template_div" class="template_div" style="display: none;">
					<div class="list-table">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>电子文档</span>
							<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#bizConfigs">
								<i class="i i-close-up"></i>
								<i class="i i-open-down"></i>
							</button>
							<button type="button" class=" btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.downloadSelTemplateZip();">打包下载</button>
							<button type="button" class="btn btn-link pull-right  download-btn" onclick="MfBusTemplate_fileListByPage.checkAll();">全选</button>
						</div>
						<div id="bizConfigs" class="content collapse in" aria-expanded="true"></div>
						<div id="qrcode"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>

