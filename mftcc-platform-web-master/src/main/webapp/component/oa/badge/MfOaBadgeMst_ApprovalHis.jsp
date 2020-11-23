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
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		
		<script type="text/javascript">
		var menuBtn=[];
		var vpNo="9";
<%-- 		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");				 --%>
		var badgeNo='${badgeNo}';
		$(function(){
			$(".scroll-content").mCustomScrollbar({
				advanced : {
					updateOnContentResize : true
				}
			});				
			showWkfFlow($("#wj-modeler2"),badgeNo);
		});
	
		</script>
	</head>
	
<body style="overflow-y:auto;">
    <div class="layout">
		<div id="freewall" style="margin: 8px;height: auto;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:2px; width:100%; height: 100%;background-color:#FFFFFF;' data-handle=".handle">																							
					<div class="block-info">
						<div class="form-table">
				   			<div class="title">
<!-- 								 <span><i class="i i-xing blockDian"></i>审批历史</span> -->
<!-- 								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div"> -->
<!-- 									<i class='i i-close-up'></i> -->
<!-- 									<i class='i i-open-down'></i> -->
<!-- 								</button> -->
							 </div>
							<div class="content margin_left_15 collapse in " id="spInfo-div">
<%-- 								<iframe src='tech/wkf/processDetail.jsp?appNo=${leaveNo}' marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" id = "processDetailIframe"></iframe> --%>
<!-- 								<ul id="wfTree" class="ztree"> -->
<!-- 			 					</ul> -->
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
</body>
</html>