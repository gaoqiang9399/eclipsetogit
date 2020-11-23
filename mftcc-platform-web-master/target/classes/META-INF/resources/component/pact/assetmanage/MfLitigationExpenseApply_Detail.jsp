<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" /> 
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript" src="${webPath}/component/pact/assetmanage/js/MfLitigationExpenseApply_Insert.js"></script>
		<script type="text/javascript">

		var wkfAppId = '${mfLitigationExpenseApply.wkfAppId}';
		var cusNo = '${mfLitigationExpenseApply.cusNo}';
		var litigationId = '${mfLitigationExpenseApply.litigationId}';
	</script>
	</head>
	<body class="bodybg-gray" style="background: white;">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="form-table">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>申请信息</span>
				</div>
				<div class="content margin_top_15 collapse in " id="spInfo-div">
					<form method="post" theme="simple" name="operform" id="pact">
						<dhcc:propertySeeTag property="formlitigationexpenseapplyinfo" mode="query" />
					</form>
				</div>
				<!-- 审批历史-->
				<c:if test="${mfLitigationExpenseApply.wkfAppId != null and mfLitigationExpenseApply.wkfAppId != ''}">
				<div class="title">
					<span><i class="i i-xing blockDian"></i>审批历史</span>
				</div>
				<div class="approval-process">
					<div id="modeler1" class="modeler">
						<ul id="wj-modeler2" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
				</c:if>
			</div>
			
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfLitigationExpenseApply_Insert.myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript">
	var wkfAppId = '${mfLitigationExpenseApply.wkfAppId}';
	$(function() {
		$(".scroll-content").mCustomScrollbar({//滚动条的生成
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//展示审批历史
		//获得审批历史信息
		showWkfFlowVertical($("#wj-modeler2"), wkfAppId, "37","legalcost_apply_approval");
		$("#leaveApproveHis").show();
	});
</script>	
</html>
