<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
	String authFlag = (String) request.getAttribute("authFlag");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfBusCollateral_Detail.css" />
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfCollateralClass_InsertDetail.css" />
		<link rel="stylesheet" href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/realEstateAssessment.js"></script>
		<style type="text/css">
			.cover {
				cursor: default;
			}
			.list-table .list-add{
				color: #32b5cb
			}
		</style>
	
		<script type="text/javascript">
		var	cusNo = '${pledgeBaseInfo.cusNo}';
		var pledgeNo ='${pledgeBaseInfo.pledgeNo}';
		var classId = '${pledgeBaseInfo.classId}';
		var appId = '${appId}';
		var pactId = '${pactId}';
		var scNo = '${scNo}';	
		var docParm = "cusNo="+cusNo+"&relNo="+cusNo+"&scNo="+scNo;
		var webPath = '${webPath}';
		var collateralNo = '${pledgeBaseInfo.pledgeNo}';
		var collateralName='${pledgeBaseInfo.pledgeName}';
		var collateralMethod='${pledgeBaseInfo.pledgeMethod}';
		var dataMap = <%=request.getAttribute("dataMap")%>;
		var collateralTableList = dataMap.collateralTableList;
		var keepStatus='${pledgeBaseInfo.keepStatus}';
		var keepId = '${keepId}';
		var aloneFlag = true;
		var dataDocParm="";
		if(appId!=""){
            dataDocParm ={
                relNo:appId,
                docType:"collateralDoc",
                docTypeName:"担保资料",
                docSplitName:"担保资料",
                query:''
            };
		}else{
            dataDocParm ={
                relNo:cusNo,
                docType:"collateralDoc",
                docTypeName:"担保资料",
                docSplitName:"担保资料",
                query:''
            };
		}

		var busSts='${busSts}';//bug修改:押品根据业务状态 出库的可用与不可用状态。
		</script>
	</head>
<body class="overflowHidden">
    <div class="container">
		<div class="row clearfix">	
			<!--申请主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<!--头部主要信息 -->
					<div class="row clearfix head-info">
						<div class="col-xs-3 column text-center head-img">
							<div>
							
							
								<button type="button" class="btn btn-font-pledge font-pledge-div padding_left_15">
									<i class="i i-pledge font-icon"></i>
									<div id="test" class="font-text-left">押品信息</div>
								</button>
							</div>
							<div class="btn btn-link" onclick="javascript:void(0);">${pledgeBaseInfo.pledgeShowNo}</div>
						</div>
						<div class="col-xs-9 column head-content">
							<div class="row clearfix">
							<div class="col-xs-10 column">
							<div class="margin_bottom_5">
								<c:if test="${fn:length(pledgeBaseInfo.pledgeName)>18}">  
									<div class="bus head-title pull-left" title="${pledgeBaseInfo.pledgeName}">${pledgeBaseInfo.pledgeName.substring(0,18)}... </div>
								</c:if>  
							 	<c:if test="${fn:length(pledgeBaseInfo.pledgeName)<=18}">  
									<div class="bus head-title pull-left">${pledgeBaseInfo.pledgeName}</div>
							  	</c:if>
							</div>

							<div class="margin_bottom_10">
								<dhcc:pmsTag pmsId="assets-custodial-info">
								<button class="btn btn-view cus-tag btn-lightgray" type="button" onclick="keepInfo();">
									<i class="i i-ren2"></i><span>保管信息</span>
								</button>
								</dhcc:pmsTag>
							</div>

							<div>
								<table>
									<tr>
										<td>
											<p class="cont-title">${pledgeBaseInfoHead.headFistName}</p>
											<p><span id='envalueAmt' class="content-span">${pledgeBaseInfoHead.headFistValue}</span><span class="unit-span margin_right_25">${pledgeBaseInfoHead.headFistUnit}</span></p>
										</td>
										<td>
											 <p class="cont-title">${pledgeBaseInfoHead.headSecName}</p>
											<p><span id='receiptsAmount' class="content-span">${pledgeBaseInfoHead.headSecValue}</span><span class="unit-span margin_right_25">${pledgeBaseInfoHead.headSecUnit}</span></p>
										</td>
										<%-- <td>
											 <p class="cont-title">${pledgeBaseInfoHead.headThirdName}</p>
											<p><span id='receiptsAmount' class="unit-span margin_right_25">${pledgeBaseInfoHead.headThirdValue}</span><span class="unit-span margin_right_25">${pledgeBaseInfoHead.headThirdUnit}</span></p>
										</td> --%>
										<td>
										</td>
									</tr>
								</table>
							</div>
							</div>
							<!--押品类型图标-->
							<%-- <div class="col-xs-2 column">
								<div class="i i-warehouse class-type">
									<div id='keepStatusName' class="classtype-name">${keepStatusName}</div>
								</div>
							</div> --%>
							<div class="col-xs-2 column">
								<div class="i i-warehouse cus-type-font">
									<div class="type-name-div">${keepStatusName}</div>
								</div>
							</div>
						</div>
					</div>
					</div>
					<%--审批页面查看详情时不展示操作按钮--%>
					<c:if test='${busEntrance!="keep_approve"}'>
						<div class="row clearfix btn-opt-group">
							<div class="col-xs-12 column">
								<div class="btn-group pull-right">
									<button id ="instockBtnAgain" class="btn btn-opt" onclick="inStockKeepInfoAgain();" type="button">
										<i class="i i-ruku"></i><span>补货入库</span>
									</button>
									<button id ="instockBtn" class="btn btn-opt" onclick="inStockKeepInfo();" type="button">
										<i class="i i-ruku"></i><span>入库</span>
									</button>
									<button id ="outstockBtn" class="btn btn-opt" onclick="outStockKeepInfo();" type="button">
										<i class="i i-chuku"></i><span>出库</span>
									</button>
								</div>
							</div>
						</div>
					</c:if>
					<!-- 暂时去掉押品详情中这个操作是，统一在担保详情中处理。勿删
					<div class="row clearfix btn-opt-group">
						<div class="col-xs-12 column">
							<div class="btn-group pull-right">
								<button id ="instockBtn" class="btn btn-opt" onclick="inStockKeepInfo();" type="button">
									<i class="i i-ruku"></i><span>入库</span>
								</button>
								<button id ="checkBtn"class="btn btn-opt" onclick="addChkInfo();" type="button">
									<i class="i i-plejiancha"></i><span>检查</span>
								</button>
								<button id ="outstockBtn" class="btn btn-opt" onclick="outStockKeepInfo();" type="button">
									<i class="i i-chuku"></i><span>出库</span>
								</button>
								<button id ="handleBtn" class="btn btn-opt" onclick="handleKeepInfo();" type="button">
									<i class="i i-plechuzhi"></i><span>处置</span>
								</button>
								<button class="btn btn-opt" onclick="collateralWarning();" type="button">
									<i class="i i-wenjian4"></i><span>预警</span>
								</button>
							</div>
						</div>
					</div> -->
			
					<!-押品其他信息-->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="block-add" style="display: none;">
							</div>
						</div>
					</div>
					<!--上传文件-->
					<div class="row clearfix">
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12" >
							<div id="rotate-body"></div>
						</div>
					</div> 
					<!-- 资产保全历史纪录 -->
					<dhcc:pmsTag pmsId="assets-assets-preservation-record">
					<jsp:include page="/component/assetspreservation/MfAssetspreservation_Record.jsp?pledgeNo=${pledgeBaseInfo.pledgeNo}"/>
					</dhcc:pmsTag>
					<div id="inOutStockApproveHis" class="row clearfix" style="display: none;">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block" class="approval-hist">
								<div class="list-table">
								   <div class="title">
										 <span><i class="i i-xing blockDian"></i>出入库审批历史</span>
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
				</div>
			  </div>
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<c:if test='${pledgeBaseInfo.cusNo!="" && pledgeBaseInfo.cusNo!=null}'>
						<jsp:include page="/component/cus/MfCusCustomer_AbstractInfo.jsp?cusNo=${pledgeBaseInfo.cusNo}"/>
					</c:if>
					<c:if test='${pledgeBaseInfo.refFlag=="1"}'>
						<jsp:include page="/component/app/MfBusApply_AbstractInfo.jsp?cusNo=${pledgeBaseInfo.cusNo}&appId=${appId}&pleId=${pledgeBaseInfo.pledgeNo}"/>
					</c:if>
					<!--信息变更记录-->
					<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${pledgeBaseInfo.cusNo}"/>
					<!--历史业务统计-->
					<jsp:include page="/component/pact/MfBusPact_Statistics.jsp?cusNo=${pledgeBaseInfo.cusNo}"/>
					<!--客户跟踪-->
					<jsp:include page="/component/cus/custracing/MfCusTrack_pub.jsp?cusNo=${pledgeBaseInfo.cusNo}"/>
					
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfCollateralClass_InsertDetail.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfBusCollateral_Detail.js"></script>
</html>