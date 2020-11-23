<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
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
		<script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script>
<%-- 		<script type="text/javascript" src="${webPath}/component/include/wkfApproveIdea.js"></script> --%>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<script type="text/javascript">
		var menuBtn=[];
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
		var vpNo = '3';
		var pactId,appId,wkfAppId,cusNo,pactSts,fincSts,fincId,pleId;
		var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var busModel = '${busModel}';
		var coreCusNo = '${coreCusNo}';
		var wareHouseCusNo = '${wareHouseCusNo}';
		var pactModelNo = '${pactModelNo}';
		var fincId = '${fincId}';
		$(function(){
			pactId = '${mfBusPact.pactId}';
			cusNo = '${mfBusPact.cusNo}';
			appId = '${mfBusPact.appId}';
			wkfAppId = '${mfBusPact.wkfAppId}';
			pactSts = '${mfBusPact.pactSts}';//合同状态值参考BizPubParm中的字段PACT_STS**
			fincSts = '${mfBusFincApp.fincSts}';
			fincId = '${mfBusFincApp.fincId}';
			pactProcessId='${mfBusFincApp.pactProcessId}';
// 			getSPInfo();
			showWkfFlow($("#wj-modeler2"),"${fincId}");
			
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
<%-- 	<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script> --%>
<%-- <link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" /> --%>
<%-- <link rel="stylesheet" href="${webPath}/component/wkf/css/wkf_approval.css" /> --%>
<body style="overflow-y:auto;">
    <div class="layout">
		<div id="freewall" style="margin: 8px;height: auto;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:2px; width:100%; height: 100%;background-color:#FFFFFF;' data-handle=".handle">																							
					<div class="block-info approval-hist">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							   </div>
							<div class="content margin_left_15 collapse in " id="spInfo-div">
<%-- 								<iframe src='tech/wkf/processDetail.jsp?appNo=${fincId}&appWorkflowNo=${mfBusApply.fincProcessId}' --%>
<!-- 								 	marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" id = "processDetailIframe"> -->
<!-- 								 </iframe> -->
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