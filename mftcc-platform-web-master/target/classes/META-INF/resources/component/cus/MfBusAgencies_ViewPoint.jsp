<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>资金机构审批页面</title>
		<script type="text/javascript">
			var wkfAppId = '${wkfAppId}';
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content" style="height:100%">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag" style="height:auto">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="insertForm" theme="simple" name="operform" action="${webPath}/mfBusAgencies/approveMfBusAgenciesModifyAjax">
							<dhcc:bootstarpTag property="formmfbusagenciesdetail0003" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="提交" action="提交" typeclass="save" onclick="approveModify('#insertForm');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
	   		</div>
   		</div>
   		<input name="taskId" id="taskId" type="hidden" value=${taskId} />
		<input name="activityType" id="activityType" type="hidden" value=${activityType} />
		<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value=${designateType} />
	</body>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
	<script src="${webPath}/component/cus/js/MfBusAgencies.js"></script>
	<script type="text/javascript">
		$(function(){
			MfBusAgencies.viewPointInit();
		});
		
	</script>
</html>