
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.BizPubParm"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String investigateScNo = BizPubParm.SCENCE_TYPE_DOC_INVESTIGATION;
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript">
		var pactId,fincId,pleId;
		var cusNo = '${mfBusApply.cusNo}';
		var appId = '${mfBusApply.appId}';
		var cusType = '${mfCusCustomer.cusType}';
		var menuBtn=[],menuUrl=[];
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData")%>'+")");
		var vpNo = '1';
		var appSts = '${mfBusApply.appSts}';//申请状态在BizPubParm.java中的APP_STS_***中
		var wkfAppId = '${mfBusApply.wkfAppId}';
		var investigateScNo ='<%=investigateScNo%>';
		var headImg = '${mfCusCustomer.headImg}';
		var ifUploadHead = '${mfCusCustomer.ifUploadHead}';
		var busModel = '${mfBusApply.busModel}';
		var cusNoTmp = "";
		var riskLevel = '${dataMap.riskLevel}';
		var modelNo = '${mfBusApply.kindNo}';
		var webPath = '${webPath}';
		var basePath = '${webPath}';
		var pleFlag = '${pleFlag}';
		var hisTaskObj = '${dataMap.hisTaskList}';
		hisTaskObj = JSON.parse(hisTaskObj);
		var query = '${query}';
		var operable = '${operable}';
		var docParm = "query=query" + "&cusNo=" + cusNo + "&relNo=" + cusNo
			+ "&appId=" + appId;//查询文档信息的url的参数
		var entrance = "business";
		var vouType = '${mfBusApply.vouType}';
		var applyProcessId = '${mfBusApply.applyProcessId}';
		var rateUnit = '${rateUnit}';
		var kindNam_8 = {};
        var projectName = '${projectName}';//项目名称
        var kindNo = '${mfBusApply.kindNo}';//产品编号
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<!--申请主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">

					<!--  业务视图 -->
					<div class="row clearfix">
						<div class="app-process">
							<div id="modeler1" class="modeler">
								<ul id="wj-modeler1" class="wj-modeler" isApp="true">
								</ul>
							</div>
						</div>
					</div>

					<c:if test='${operable=="operable"}'>
						<div class="row  btn clearfix bg-danger next-div hide">
							<div class="col-xs-12 column text-center">
								<div class="block-next"></div>
							</div>
						</div>
					</c:if>
					<!--头部主要信息 cus-bo-head-info -->

					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img padding_top_20">
							<div style="position: relative;">
								<button type="button" class="btn btn-font-app padding_left_15 font-app-div">
									<i class="i i-applyinfo font-icon"></i>
									<div class="font-text-left">申请信息</div>
								</button>
							</div>
							<c:choose>
								<c:when test="${fn:substring(mfBusApply.kindName, 0, 8)}">
									<div class="btn btn-link" onclick="getKindInfo();" title="${mfBusApply.kindName}">
										<s:property value="mfBusApply.kindName.substring(0,8)" />
										...
									</div> --%>
							    </c:when>

								<c:otherwise>
									<div class="btn btn-link" onclick="getKindInfo();">${mfBusApply.kindName}</div>
								</c:otherwise>
							</c:choose>
							<%-- 	<c:if test="${mfBusApply.kindName.length()>8}">  
						    	<div class="btn btn-link" onclick="getKindInfo();" title="${mfBusApply.kindName}"><s:property value="mfBusApply.kindName.substring(0,8)"/>... </div> 
						  	</c:if>  
						 	<c:else>  
								<div class="btn btn-link" onclick="getKindInfo();">${mfBusApply.kindName}</div>
						  	</c:else>  --%>
						</div>
						<!--概要信息 -->
						<div class="col-xs-9 column head-content">
							<div class="margin_bottom_5 clearfix">
								<div>
									<c:if test="${dataMap.moreCount>1}">
										<div class="bus-more head-title pull-left" title="${mfBusApply.appName}">${mfBusApply.appName}</div>
										<div class="multi-bus pull-left">
											客户共有 <a class="moreCnt more-apply-count pointer" onclick="getMultiBusList();">${dataMap.moreCount}</a> 笔在履行业务
										</div>
									</c:if>
									<s:else>
										<div class="bus head-title pull-left" title="${mfBusApply.appName}">${mfBusApply.appName}</div>
									</s:else>
								</div>
							</div>
							<!--信息查看入口 1   查看风险检查按钮-->
							<div class="margin_bottom_10">
								<dhcc:pmsTag pmsId="apply-risk-result">
									<button class="btn risklevel${dataMap.riskLevel} btn-view" type="button" onclick="busRisk();">
										<i class="i i-risk"></i><span>${dataMap.riskName}</span>
									</button>
								</dhcc:pmsTag>
								<button class="btn btn-lightgray btn-view" id="surveyReport" type="button">
									<i class="i i-eval1"></i>尽调报告
								</button>
							</div>
							<div>
								<table>
									<tr>
										<td>
											<p class="cont-title">申请金额</p>
											<p>
												<span id="appAmt" class="content-span">${mfBusApply.fincAmt}</span><span class="unit-span margin_right_25">万</span>
											</p>
										</td>
										<td>
											<p class="cont-title">申请期限</p>
											<p>
												<span id="term" class="content-span"></i>${mfBusApply.term}</span><span class="unit-span margin_right_25"><c:if test='${mfBusApply.termType=="1"}'>个月</c:if>
													<s:else>天</s:else></span>
											</p>
										</td>
										<td><c:if test='${dataMap.guaranteeType=="loan"}'>
												<p class="cont-title">申请利率</p>
												<p>
													<span id="fincRate" class="content-span"><s:property value="${formatDouble(mfBusApply.fincRate)}" /></span><span class="unit-span">${rateUnit}</span>
												</p>
											</c:if></td>
									</tr>
								</table>
							</div>
							<div class="btn-special">
								<c:if test='${dataMap.wareHouseCusNo!=null && dataMap.wareHouseCusNo!="" && dataMap.wareHouseCusNo!="0"}'>
									<span class="relate-corp" data-view='cuswarehouse'> <i class="i i-cangKu"></i><span>由仓储机构<a href="javascript:void(0);"
											onclick="getInfoForView('103','${dataMap.wareHouseCusNo}','仓储机构');">${mfBusApply.cusNameWarehouse}</a>保管货物
									</span>
									</span>
								</c:if>
								<c:if test='${dataMap.coreCusNo!=null && dataMap.coreCusNo!="" && dataMap.coreCusNo!="0"}'>
									<span class="relate-corp" data-view='cuscore'> <i class="i i-qiYe"></i><span>由核心企业 <a href="javascript:void(0);" onclick="getInfoForView('108','${dataMap.coreCusNo}','核心企业');">${mfBusApply.cusNameCore}</a>
											推荐
									</span>
									</span>
								</c:if>
								<c:if test='${dataMap.fundCusNo!=null && dataMap.fundCusNo!="" && dataMap.fundCusNo !="0"}'>
									<span class="relate-corp" data-view='fundorg'> <i class="i i-fundorg"></i><span>由资金机构 <a href="javascript:void(0);"
											onclick="getInfoForView('109','${dataMap.fundCusNo}','资金机构 ');">${mfBusApply.cusNameFund}</a> 放款
									</span>
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
									<!-- 								<button class="btn btn-opt" onclick="addedService();" type="button"> -->
									<!-- 									<i class="i i-lianwang"></i><span>联网核查</span> -->
									<!-- 								</button> -->
									<button class="btn btn-opt" onclick="MfBusApply_Detail.filePrint();" type="button">
										<i class="i i-x"></i><span>文件打印</span>
									</button>
								</div>
							</div>
						</div>
					</c:if>
					<!--申请表单信息 22-->
					<dhcc:pmsTag pmsId="apply-detail-info">
						<div class="row clearfix">
							<div class="col-xs-12 column">
								<div class="form-table base-info">
									<c:if test='${cusBaseFlag!="0"}'>
										<div class="content" id="applyDetailInfo">
											<form method="post" theme="simple" name="operform" action="${webPath}/mfBusApply/updateAjaxByOne">
												<dhcc:propertySeeTag property="formapply0006" mode="query" />
											</form>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</dhcc:pmsTag>
					<!--费用标准  -->
					<div class="row clearfix">
						<div class="block-new-block"></div>
					</div>
					<!--要件信息 22-->
					<dhcc:pmsTag pmsId="apply-data-upload">
						<div class="row clearfix">
							<div class="col-xs-12 column">
								<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
							</div>
						</div>
					</dhcc:pmsTag>
					<!--审批历史 22-->
					<dhcc:pmsTag pmsId="apply-approve-his">
						<div class="row clearfix" id="spInfo-block">
							<div class="form-table">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>业务审批历史</span>
									<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										<i class='i i-close-up'></i> <i class='i i-open-down'></i>
									</button>
								</div>
								<div class="content margin_left_15 collapse in " id="spInfo-div">
									<div class="approval-process">
										<div id="modeler1" class="modeler">
											<ul id="wj-modeler2" class="wj-modeler" isApp="false">
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</dhcc:pmsTag>

				</div>
			</div>
			<!-- 申请附属信息 -->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<!--客户信息视图块  -->
					<jsp:include page="/component/cus/MfCusCustomer_AbstractInfo.jsp?cusNo=${mfBusApply.cusNo}&operable=${operable}" />
					<!--应收账款，担保  -->
					<jsp:include
						page="/component/collateral/MfBusCollateralRel_AbstractInfo.jsp?relId=${mfBusApply.appId}&busModel=${mfBusApply.busModel}&cusNo=${mfBusApply.cusNo}&operable=${operable}&busEntrance=1" />
					<!--信息变更记录-->
					<dhcc:pmsTag pmsId="mf-message-change-record">
						<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${mfBusApply.cusNo}" />
					</dhcc:pmsTag>
					<!--历史业务统计-->
					<dhcc:pmsTag pmsId="mf-history-service">
						<jsp:include page="/component/pact/MfBusPact_Statistics.jsp?cusNo=${mfBusApply.cusNo}" />
					</dhcc:pmsTag>
					<!--客户跟踪33-->
					<dhcc:pmsTag pmsId="mf-cus-follow">
						<jsp:include page="/component/cus/custracing/MfCusTrack_pub.jsp?cusNo=${mfBusApply.cusNo}" />
					</dhcc:pmsTag>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_Detail.js"></script>
</html>