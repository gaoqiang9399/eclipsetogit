<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/templateIncludePage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/MfBusTemplate_fileListByPage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/stamp/js/MfStampPact_input.js"></script>
	<script type="text/javascript">
	var stampBaseId = '${mfStampPact.id}';
    var appId  = '${mfStampPact.appId}';
    var cusNo  = '${mfStampPact.cusNo}';
    var queryType  = '${queryType}';
    var temParm = 'appId=' + appId+"&creditAppId="+ '${mfStampPact.creditAppId}'+"&cusNo="+ cusNo ;
    var id = '${mfStampPact.id}';
	$(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})
        templateIncludePage.init();
		$("#mfBusPactExtendList").find("table").show();
	})

	//审批提交
	function doSubmit(obj){
		//移除disabled属性，为了取值
		// $("[name='realFlag']").removeAttr("disabled");
		// $("[name='finalApproveFlag']").removeAttr("disabled");
		var opinionType = $("[name=opinionType]").val();
		//没有选择审批意见默认同意
		if(opinionType == undefined || opinionType == ''){
			opinionType = "1";
		}
		var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			commitProcess(webPath+"/mfStampPact/submitUpdateAjax?id="+id+"&primaryAppId="+id+"&appNo="+id+"&opinionType="+opinionType+"&approvalOpinion="+approvalOpinion,obj,'applySP');
		}
	}


	function closeWindow(){
		myclose_click();
	};
</script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
	<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="bootstarpTag">
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="stampCreditForm" theme="simple" name="operform" action="${webPath}/mfStampPact/submitUpdateAjax">
					<dhcc:bootstarpTag property="formstamppactapproval" mode="query"/>
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
		<dhcc:thirdButton value="审批" action="审批" onclick="doSubmit('#stampCreditForm');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="取消" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
	</div>
</div>
<input name="taskId" id="taskId" type="hidden" value=${taskId} />
<input name="activityType" id="activityType" type="hidden" value=${activityType} />
<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
<input name="transition" type="hidden" />
<input name="nextUser" type="hidden" />
<input name="designateType" type="hidden" value=${designateType} />
</body>
</html>

+