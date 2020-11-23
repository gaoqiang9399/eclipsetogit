<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<script type="text/javascript" src="${webPath}/component/include/myCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script src="${webPath}/component/cus/js/MfBusAgencies.js"></script>
		<script type="text/javascript">
			var wkfAppId = '${wkfAppId}';
			var approvalStatus = '${approvalStatus}';
			$(function() {
				if (approvalStatus != '01' && wkfAppId != '') {
					showWkfFlowVertical($("#wj-modeler3"), wkfAppId, "50","");
				} else {
					$("#agenciesSpInfo-block").remove();
				}
			});
		</script>
	</head>
<body class="bg-white">
	<div class="row clearfix approval-hist" id="agenciesSpInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="content margin_left_15 collapse in " id="agenciesSpInfo-div">
				<div class="approval-process">
					<div id="modeler1" class="modeler">
						<ul id="wj-modeler3" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="formCloseCenter" class="formRowCenter">
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="closeMfAgenciesDetailInput();"></dhcc:thirdButton>
	</div>
</div>
</body>
</html>