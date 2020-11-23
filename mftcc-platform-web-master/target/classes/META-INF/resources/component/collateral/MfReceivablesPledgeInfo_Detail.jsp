<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form  method="post" id="MfReceivablesPledgeInfoForm" theme="simple" name="operform" action="${webPath}/mfReceivablesPledgeInfo/insertAjax">
							<dhcc:bootstarpTag property="formreceple0001" mode="query"/>
						</form>
					</div>
				</div>
				<div class="col-md-8 col-md-offset-2 margin_top_20 approval-hist" id="spInfo-block">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
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
	</body>
	<script type="text/javascript" src="${webPath}/component/collateral/js/MfReceivablesPledgeInfo.js"></script>
	<script type="text/javascript">
		var busModel='${mfBusApply.busModel}';
		var receivablesPledgeId='${mfReceivablesPledgeInfo.receivablesPledgeId}';
		var receTranAppId='${mfReceivablesTransferInfo.receTranAppId}';
		$(function(){
			MfReceivablesPledgeInfo.init();
			if(busModel=="13"){//保理业务展示转让审批历史
				//showWkfFlow($("#wj-modeler2"),receTranAppId);
				showWkfFlowVertical($("#wj-modeler2"),receTranAppId,"10","recetrans_approval");
			}else{
				$("#spInfo-block").hide();
			}
			/*
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			*/
			/*
			$(".scroll-content").mCustomScrollbar({
				advanced : {
					updateOnContentResize : true
				}
			});
			*/
		});
	</script>
</html>