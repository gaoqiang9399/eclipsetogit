<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>
<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
		<link rel="stylesheet" href="${webPath}/component/wkf/css/wkf_approval.css" />
		<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
		<%-- <script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script> --%>
<%-- 			<script type="text/javascript" src="${webPath}/tech/wkf/js/wjProcessDetail.js"></script> --%>
<%-- 		<script type="text/javascript" src="${webPath}/component/include/wkfApproveIdea.js"></script> --%>
	
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
			
		<script type="text/javascript">
		var menuBtn=[];
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData")%>'+")");
		var vpNo = '3';
		var pactId,appId,wkfAppId,cusNo,pactSts,fincSts,fincId,pleId;
		var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var busModel = '${busModel}';
		var coreCusNo = '${coreCusNo}';
		var wareHouseCusNo = '${wareHouseCusNo}';
		var pactModelNo = '${pactModelNo}';
		var pactId = '${mfBusPact.pactId}';
		var	cusNo = '${mfBusPact.cusNo}';
		var	appId = '${mfBusPact.appId}';
		var	wkfAppId = '${mfBusPact.wkfAppId}';
		var	pactSts = '${mfBusPact.pactSts}';//合同状态值参考BizPubParm中的字段PACT_STS**
		var	fincSts = '${mfBusFincApp.fincSts}';
		var	fincId = '${mfBusFincApp.fincId}';
		var hisTaskList = eval("(" + '${json}' + ")").hisTaskList;
		var cusNoTmp = "";
		var webPath = '${webPath}';
		var basePath = "${webPath}";
		$(function(){
			$(".container").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true
				}
			});
		});
</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<!--合同主信息 -->
			<div class="col-xs-12 column block-left">
				<div class="bg-white block-left-div">
					<!--业务主要信息 -->
					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-2 column text-center head-img">
							<div style="position:relative;">
								<button type="button" class="btn btn-font-pact padding_left_15">
									<i class="i i-pact font-icon"></i>
								</button>
								<div class="font-text-left"style="top: 50%;left: 34%;">合同信息</div>
							</div>
							<c:if test="${fn:length(mfBusPact.kindName)>8 }">
								<div class="btn btn-link"
									title="${mfBusPact.kindName}">
									${fn:substring(mfBusPact.kindName,0,8)}...
								</div>
							</c:if>
							<c:if test="${fn:length(mfBusPact.kindName)<=8 }">
								<div class="btn btn-link" onclick="getKindInfo();">${mfBusPact.kindName}</div>
							</c:if>
						</div>
						<!--概要信息 -->
						<div class="col-xs-7 column head-content">
							<div></div>
							<div class="margin_bottom_5">
								<div class="head-title">${mfBusPact.appName}</div>
							</div>
							<!--信息查看入口 -->
							<div>
								<%-- <p>
									<span class="content-span"><i class="i i-rmb"></i>${mfBusPact.fincAmt}</span><span
										class="unit-span margin_right_25">万</span> <span
										class="content-span"><i class="i i-richengshezhi"></i>${mfBusPact.term}</span><span
										class="unit-span margin_right_25"><s:if
											test='%{mfBusApply.termType=="1"}'>月</s:if>
										<s:else>天</s:else></span> <span class="content-span"><i
										class="i i-tongji1"></i>${mfBusPact.fincRate}</span><span
										class="unit-span">%</span>
								</p> --%>
								<table>
									<tr>
										<td>
											<p class="cont-title">合同金额</p>
											<p><span class="content-span">${mfBusPact.fincAmt}</span><span class="unit-span margin_right_25">万</span> </p>
										</td>
										<td>
											<p class="cont-title">合同期限</p>
											<p><span class="content-span"></i>${mfBusPact.term}</span><span class="unit-span margin_right_25"><c:if test='${mfBusPact.termType=="1"}'>个月</c:if><c:if test='${mfBusPact.termType！="1"}'>天</c:if></span> </p>
										</td>
										<td>
											<p class="cont-title">年利率</p>
											<p><span class="content-span">${mfBusPact.fincRate}</span><span	class="unit-span">%</span></p>
										</td>
									</tr>
								</table>
							</div>
						</div>

						<div class="col-xs-2 column">
							<div class="btn btn-link block-view" data-view='cuswarehouse'
								style="display: none;">
								<i class="i i-4sdian"></i><span>仓储方</span>
							</div>
							<div class="btn btn-link block-view" data-view='cuscore'
								style="display: none;">
								<i class="i i-4sdian"></i><span>核心企业</span>
							</div>
							<div class="btn btn-link block-view" data-view='buyinfo'
								style="display: none;">
								<i class="i i-4sdian"></i><span>买方信息</span>
							</div>
						</div>
					</div>
					<!--合同表单信息 -->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="form-table base-info">
								<c:if test='${cusBaseFlag!="0"}'>
									<div class="content">
										<form method="post" theme="simple" name="operform"
											action="${webPath}/mfCusCorpBaseInfo/updateAjaxByOne">
											<dhcc:propertySeeTag property="formpact0004" mode="query" />
										</form>
									</div>
								</c:if>
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="block-new-block"></div>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="list-table base-info">
								<c:if test='${fn:length(mfRepayHistoryList)>0}' >
								   <div class="title">
									 <span>还款历史</span>
								   </div>
								   <div class="content margin_left_15">
									 <dhcc:tableTag property="tablerepayhis0001" paginate="mfRepayHistoryList" head="true"></dhcc:tableTag>
								   </div>
					            </c:if>
							</div>
						</div>
					</div>
						<div class="row clearfix">
						<div class="block-new-block"></div>
					</div>
					
					<div class="row clearfix approval-hist" >
						<div class="list-table">
						  	<div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
						   	</div>
							<div class="content margin_left_15 collapse in " id="spInfo-div">
<%-- 								<iframe src='${webPath}/tech/wkf/processDetail.jsp?appNo=${mfBusPact.pactId}&appWorkflowNo=${mfBusPact.pactProcessId}'  --%>
<!-- 									marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" id = "processDetailIframe"> -->
<!-- 								</iframe> -->
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
	</div>

</body>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPactApprov_Detail.js"></script>
</html>