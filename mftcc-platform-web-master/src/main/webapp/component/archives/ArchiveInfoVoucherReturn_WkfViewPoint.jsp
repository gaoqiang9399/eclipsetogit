<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript">
	var id = '${archiveInfoVoucherReturn.id}';
	$(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})
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
			commitProcess(webPath+"/archiveInfoVoucherReturn/submitUpdateAjax?id="+id+"&primaryAppId="+id+"&appNo="+id+"&opinionType="+opinionType+"&approvalOpinion="+approvalOpinion,obj,'applySP');
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
				<div class="form-title">凭证处置</div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="stampCreditForm" theme="simple" name="operform" action="${webPath}/archiveInfoVoucherReturn/submitUpdateAjax">
					<dhcc:bootstarpTag property="formarchivevoucherapproval" mode="query"/>
				</form>
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