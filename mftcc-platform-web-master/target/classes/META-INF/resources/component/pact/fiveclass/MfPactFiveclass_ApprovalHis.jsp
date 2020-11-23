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
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript">
		var appNo = '${appNo}';
		$(function(){
			/* showWkfFlow($("#wj-modeler2"),appNo); */
			showWkfFlowVertical($("#wj-modeler2"),appNo,"6","fiveclass_approval");
		});
													
			
		</script>
	</head>
<body style="overflow-y:auto;">
    <div class="layout">
		<div id="freewall" style="margin: 8px;height: auto;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:2px; width:100%; height: 100%;background-color:#FFFFFF;' data-handle=".handle">																							
					<div class="block-info approval-hist">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
						   </div>
							<div class="content margin_left_15 collapse in " id="spInfo-div">
			 					<div class="row clearfix">
						<div class="content margin_left_15 ">
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
</html>