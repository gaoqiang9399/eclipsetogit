<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript">
	var borrowId = '${archiveInfoBorrow.borrowId}';
	$(function () {
		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		})

		//判断审批历史模块的显隐
		if (borrowId!='') {
			showWkfFlowVertical($("#wj-modelerrece"), borrowId, "","");
		} else {
			$("#receChargeFeeInfo-block").remove();
		}
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
			commitProcess(webPath+"/archiveInfoBorrow/submitUpdateAjax?borrowId="+borrowId+"&primaryAppId="+borrowId+"&appNo="+borrowId+"&opinionType="+opinionType+"&approvalOpinion="+approvalOpinion,obj,'applySP');
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
				<div class="form-title"></div>
				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				<form method="post" id="stampCreditForm" theme="simple" name="operform" action="${webPath}/archiveInfoBorrow/submitUpdateAjax">
					<dhcc:bootstarpTag property="formarchiveborrowapproval" mode="query"/>
				</form>
			</div>
		</div>

		<div class="clearfix approval-hist col-md-10 col-md-offset-1" id="receChargeFeeInfo-block">
			<div class="col-xs-12 column">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>借阅申请审批历史</span>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receFincSpInfo-div">
							<i class='i i-close-up'></i> <i class='i i-open-down'></i>
						</button>
					</div>
					<div class="content margin_left_15 collapse in " id="receFincSpInfo-div">
						<div class="approval-process">
							<div id="modelerrecefinc" class="modeler">
								<ul id="wj-modelerrece" class="wj-modeler" isApp="false">
								</ul>
							</div>
						</div>
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