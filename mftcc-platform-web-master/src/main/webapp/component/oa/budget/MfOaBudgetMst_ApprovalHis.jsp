<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">

			.cover {
				cursor: default;
			}
			.wfTree_description {
			    width: 80px;
			}
		</style>
		<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
        <link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
		<script type="text/javascript" src="${webPath}/component/oa/leave/js/MfOaLeaveApprovalHis.js"></script>	
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>

		<script type="text/javascript">
		var menuBtn=[];
		var vpNo="9";
		var budgetId='${budgetId}';
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
			showWkfFlowVertical($("#wj-modeler2"),budgetId,"","");
		});
	
		</script>
	</head>
	
<body class="bg-white" style="height: 100%;">
<div class="scroll-content container form-container"  style="height: 100%;">
	<div class="col-xs-10 col-xs-offset-1" style="margin-top:20px;">
		<div class="arch_sort">
			<div id="MfOaBudgetMst_ApprovalHis" class="row clearfix">
				<div class="col-xs-12 column info-block">
					<div id="spInfo-block" class="approval-hist">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>预算申请审批历史</span>
						   </div>
						   <div class="content margin_left_15 collapse in " id="spInfo-div">
								<div class="approval-process">
									<div id="modeler1" class="modeler">
										<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
						   </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</body>
</html>