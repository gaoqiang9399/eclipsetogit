<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%-- 		<script type="text/javascript" src="${webPath}/component/include/wkfApproveIdea.js"></script> --%>
<%-- 		<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script> --%>
<%-- 		<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" /> --%>
<%-- 		<link rel="stylesheet" href="${webPath}/component/wkf/css/wkf_approval.css" /> --%>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<script type="text/javascript">
		var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
		var fincId = '${fincId}';
		$(function(){
			getSPInfo();
		    /* showWkfFlow($("#wj-modeler2"),fincId); */ 
			showWkfFlowVertical($("#wj-modeler2"),fincId,"4","contract_approval");
		});
													
		
		function getSPInfo(){
			$.ajax({
				type: "post",
				data:{appNo:"${fincId}"},
				dataType: 'json',
				url:webPath+"/wkfApprovalOpinion/getApplyApprovalOpinionList",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
				    console.log(data);
					Wkf_zTreeNodes=data.zTreeNodes;
					Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
				}
			});
		}
			
		
		

	</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container">
		<div class="content margin_left_15 margin_top_20">
<%-- 			<iframe src='tech/wkf/processDetail.jsp?appNo=<s:property value="fincId"/>&appWorkflowNo=<s:property value="mfBusApply.fincProcessId"/>' marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%"  id = "processDetailIframe"></iframe> --%>
<!-- 			<ul id="wfTree" class="ztree"> -->
<!-- 			</ul> -->
			<div class="approval-process">
				<div id="modeler1" class="modeler">
					<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>