<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
		<script type="text/javascript">
		var menuBtn=[],menuUrl=[];
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData")%>'+")");
		var vpNo = '3';
		var pactId,appId,wkfAppId,cusNo,pactSts,fincSts,fincId,pleId;
		var examHisId = '${examHisId}';
		var examHis = '${examHis}';//==examHis贷后检查审批页面跳转过来，合同审批历史和放款审批历史不展示
		var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var busModel = '${busModel}';
		var coreCusNo = '${coreCusNo}';
		var fundCusNo = '${fundCusNo}';
		var wareHouseCusNo = '${wareHouseCusNo}';
		var pactModelNo = '${mfBusPact.kindNo}';
		var pactId = '${mfBusPact.pactId}';
		var cusNo = '${mfBusPact.cusNo}';
		var appId = '${mfBusPact.appId}';
		var wkfAppId = '${mfBusPact.wkfAppId}';
		var pactSts = '${mfBusPact.pactSts}';//合同状态值参考BizPubParm中的字段PACT_STS**
		var fincSts = '${mfBusFincApp.fincSts}';
		var fincId = '${mfBusFincApp.fincId}';
		var fincChidId = '${fincChidId}';
		var wkfRepayId = '${mfBusFincApp.wkfRepayId}';
		var pleId = '${mfBusPledge.pleId}';
		var term = '${mfBusPact.term}';
		var termType = '${mfBusPact.termType}';
		var hisTaskList = eval("(" + '${json}' + ")").hisTaskList;
		var cusNoTmp = "";
		var webPath = '${webPath}';
		var query = '${query}';
		var operateflag='${operateflag}';
        var operable = '${operable}';
		var docParm = "query=query"+"&cusNo="+cusNo+"&relNo="+cusNo+"&appId="+appId+"&pactId="+pactId;//查询文档信息的url的参数
		var hasLawsuit = '${mfBusPact.hasLawsuit}';
		var fiveclassId = "";
		var recParam = <%=request.getAttribute("recParam")%>;
		var hasRecallFlag = recParam.hasRecallFlag;
		var recallingFlag = recParam.recallingFlag;
		var entrance = "business";
		//审批子流程开启标志
		var approvalSubFlag = "0";
		//bug修改：显示五级分类流程标识
		var fiveFlag='${dataMap.fiveFlag}';
		var getfiveclassId="${dataMap.fiveclassId}";//从后台传来的id
		var fincProcessId ="${mfBusPact.fincProcessId}";//放款审批流程编号
		var pactProcessId ="${mfBusPact.pactProcessId}";//放款审批流程编号
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<!--合同主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<div class="row clearfix">
						<div class="app-process">
							<div id="modeler1" class="modeler">
								<ul id="wj-modeler1" class="wj-modeler" isApp = "true">
								</ul>
							</div>
						</div>
					</div>
					<c:if test='${operable=="operable"}'> 
						<div  class="row clearfix btn bg-danger next-div hide">
							<div class="col-xs-12 column text-center">
								<div class="block-next"></div>
							</div>
						</div>
					</c:if>
					<!--业务主要信息 -->
					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img">
							<div>
								<button type="button" class="btn btn-font-pact font-pact-div padding_left_15">
									<i class="i i-pact font-icon"></i>
									<div class="font-text-left">合同信息</div>
								</button>
							</div>
							<c:if test="${fn:length(mfBusPact.kindName) > 8}">
								<div class="btn btn-link" onclick="getKindInfo();"
									title="${mfBusPact.kindName}">
									${fn:substring(mfBusPact.kindName,0,8)}
									...
								</div>
							</c:if>
							<c:if test="${fn:length(mfBusPact.kindName) <= 8}">
								<div class="btn btn-link" onclick="getKindInfo();">${mfBusPact.kindName}</div>
							</c:if>
						</div>
						<!--概要信息 -->
						<div class="col-xs-9 column head-content">
							<div class="margin_bottom_5 clearfix">
								<div>
									<c:if test="${dataMap.moreCount>1}">
									<div class="bus-more head-title pull-left" title="${mfBusPact.appName}">${mfBusPact.appName}</div>
										<div class="multi-bus pull-left">
											客户共有 <a class="moreCnt more-apply-count pointer"  onclick="getMultiBusList();">${dataMap.moreCount}</a> 笔在履行业务
										</div>
									</c:if>
									<c:if test="${dataMap.moreCount<1}">
										<div class="bus head-title pull-left" title="${mfBusPact.appName}">${mfBusPact.appName}</div>
									</c:if>
								</div>
							</div>
							<!--信息查看入口 -->
							<div>
								<button class="btn btn-lightgray btn-view" id="surveyReport" type="button">
									<i class="i i-eval1"></i>尽调报告
								</button>

								<table>
									<tr>
										<td>
											<p class="cont-title">合同金额</p>
											<p><span class="content-span">${mfBusPact.fincAmt}</span><span class="unit-span margin_right_25">万</span> </p>
										</td>
										<td>
											<p class="cont-title">合同期限</p>
											<p><span id="term" class="content-span">${mfBusPact.term}</span><span class="unit-span margin_right_25"><c:if test='${mfBusApply.termType=="1"}'>个月</c:if><c:if test='${mfBusApply.termType!="1"}'>天</c:if></span> </p>
										</td>
										<td>
											<c:if test='${dataMap.guaranteeType=="loan"}'>
												<p class="cont-title">合同利率</p>
												<p><span class="content-span">
												<fmt:formatNumber minFractionDigits="2" type="number" value="${mfBusPact.fincRate}"/>
												</span> <span class="unit-span">${rateUnit}</span></p>
											</c:if>
										</td>
									</tr>
								</table>
							</div>
							<div class="btn-special">
								<c:if test='${wareHouseCusNo!=null && wareHouseCusNo!="" && wareHouseCusNo!="0"}'>
									<span  class="relate-corp" data-view='cuswarehouse'>
										<i class="i i-cangKu"></i><span>由仓储机构<a href="javascript:void(0);" onclick="getInfoForView('103','${wareHouseCusNo}','仓储机构');">${mfBusApply.cusNameWarehouse}</a>保管货物 </span>
									</span>
								</c:if>
								<c:if test='${coreCusNo!=null && coreCusNo!="" && coreCusNo!="0"}'>
									<span class="relate-corp" data-view='cuscore'>
										<i class="i i-qiYe"></i><span>由核心企业 <a href="javascript:void(0);"  onclick="getInfoForView('108','${coreCusNo}','核心企业');">${mfBusApply.cusNameCore}</a> 推荐</span>
									</span>
								</c:if>
								<c:if test='${fundCusNo!=null && fundCusNo!="" && fundCusNo !="0"}'>
									<span class="relate-corp" data-view='fundorg' >
										<i class="i i-fundorg"></i><span>由资金机构 <a href="javascript:void(0);" onclick="getInfoForView('109','${fundCusNo}','资金机构 ');">${mfBusApply.cusNameFund}</a> 放款</span>
									</span>
								</c:if>
							</div>
						</div>
					</div>
					<c:if test='${operable=="operable"}'> 
						<!--信息登记操作入口 -->
						<div class="row clearfix btn-opt-group">
							<div class="col-xs-12 column">
								<div class="btn-group pull-right">
								<dhcc:pmsTag pmsId="sign-file-print">
									<button class="btn btn-opt" onclick="MfBusPact_SignDetail.filePrint();" type="button">
										<i class="i i-x"></i><span>文件打印</span>
									</button>
								</dhcc:pmsTag>
								<dhcc:pmsTag pmsId="loan-file-filing-btn">
									<c:if test='${mfBusPact.pactSts=="4" || mfBusPact.pactSts=="6"}'> 
										<button class="btn btn-opt" onclick="fileArchive();" type="button">
											<i class="i i-guidang"></i><span>文件归档</span>
										</button>
	 								</c:if> 
	 							</dhcc:pmsTag>
								</div>
							</div>
						</div>
					</c:if>
					<!--合同表单信息 2-->
					<%-- <dhcc:pmsTag pmsId="sign-detail-query"> --%>
						<div class="row clearfix">
							<div class="col-xs-12 column">
								<div class="form-table base-info">
									<c:if test='${cusBaseFlag!="0"}'>
										<div class="content" id="pactDetailInfo">
											<form method="post" theme="simple" name="operform" action="${webPath}/mfBusPact/updateAjaxByOne">
												<dhcc:propertySeeTag property="formpact0004" mode="query" />
											</form>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					<%-- </dhcc:pmsTag> --%>
					<div class="row clearfix">
						<div class="block-new-block"></div>
					</div>
					<!-- 合同信息插件 2 -->
					<dhcc:pmsTag pmsId="sign-detail-upload">
						<div class="row clearfix">
							<div class="col-xs-12 column" >
	 							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	 						</div>
						</div>
					</dhcc:pmsTag>
					<!-- 审批历史 pact-->
				
				<%-- <dhcc:pmsTag pmsId="sign-pact-approve-history"> --%>
				
					<div class="row clearfix approval-hist" id="pactSpInfo-block">
						<div class="col-xs-12 column">
							<div class="list-table">
							   <div class="title">
								 <span><i class="i i-xing blockDian"></i>合同审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#pactSpInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							   </div>
							   <div class="content margin_left_15 collapse in " id="pactSpInfo-div">
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
				<%-- </dhcc:pmsTag> --%>
					<%-- <dhcc:pmsTag pmsId="sign-fincSpInfo-block"> --%>
						<div class="row clearfix approval-hist" id="fincSpInfo-block">
							<div class="col-xs-12 column">
								<div class="list-table">
								   <div class="title">
									 <span><i class="i i-xing blockDian"></i>放款审批历史</span>
									 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#fincSpInfo-div">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
								   </div>
								   <div class="content margin_left_15 collapse in " id="fincSpInfo-div">
										<div class="approval-process">
											<div id="modeler1" class="modeler">
												<ul id="wj-modeler3" class="wj-modeler" isApp = "false">
												</ul>
											</div>
										</div>
								   </div>
								</div>
							</div>
						</div>
					<%-- </dhcc:pmsTag> --%>
					<!--贷后检查审批历史 -->
					<c:if test='${examHis=="examHis"}'> 
				 	<div class="row clearfix approval-hist" id="spInfo-block">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>贷后检查审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
						   </div>
						   <div class="content margin_left_15 collapse in " id="spInfo-div">
								<div class="approval-process">
									<div id="modeler4" class="modeler">
										<ul id="wj-modeler4" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
						   </div>
						</div>
					</div>
					</c:if>
					<!--贷后检查审批历史 -->
					<c:if test='${dataMap.fiveFlag =="fiveFlag"}'> 
				 	<div class="row clearfix approval-hist" id="fiveClass-block">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>五级分类审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
						   </div>
						   <div class="content margin_left_15 collapse in " id="spInfo-div">
								<div class="approval-process">
									<div id="modeler5" class="modeler">
										<ul id="wj-modeler5" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
						   </div>
						</div>
					</div>
					</c:if>
				</div>
			</div>
			<!--合同 附属信息 -->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<!--客户视图  1 -->
					<jsp:include page="/component/cus/MfCusCustomer_AbstractInfo.jsp"/>
					<!-- 应收帐款1 担保-->
					<jsp:include  page="/component/collateral/MfBusCollateralRel_AbstractInfo.jsp?relId=${mfBusPact.appId}&busModel=${busModel}&operable=${operable}&cusNo=${mfBusPact.cusNo}"/>
					<!--信息变更记录1-->
					<dhcc:pmsTag pmsId="mf-message-change-record">
						<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${mfBusPact.cusNo}"/>
					</dhcc:pmsTag>
					<!--历史业务统计1-->
					<dhcc:pmsTag pmsId="mf-history-service">
						<jsp:include page="/component/pact/MfBusPact_Statistics.jsp?cusNo=${mfBusPact.cusNo}"/>
					</dhcc:pmsTag>
					<!--客户跟踪1-->
					<dhcc:pmsTag pmsId="mf-cus-follow">
						<jsp:include page="/component/cus/custracing/MfCusTrack_pub.jsp?cusNo=${mfBusPact.cusNo}"/>
					</dhcc:pmsTag>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPact_SignDetail.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/collateral.js"></script>
</html>