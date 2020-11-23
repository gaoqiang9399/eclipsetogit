<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<%
	String fileSize = PropertiesUtil.getUploadProperty("fileSize");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfBusCollateral_Detail.css" />
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfCollateralClass_InsertDetail.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>


		<!-- 加载要件 开始-->
		<script type="text/javascript"
				src="${webPath}/component/doc/webUpload/webuploader/webuploader.js"></script>
		<link id="main-detail" rel="stylesheet"
			  href="${webPath}/themes/factor/css/main-detail${skinSuffix}.css"/>
		<link rel="stylesheet"
			  href="${webPath}/component/doc/webUpload/webuploader/webuploader.css"/>
		<script type="text/javascript"
				src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
        <c:choose>
            <c:when test="${docShowType == '1'}">
                <script type="text/javascript"
                        src="${webPath}/component/doc/webUpload/js/uploadListForMainPage.js"></script>
            </c:when>
            <c:otherwise>
                <script type="text/javascript"
                        src="${webPath}/component/doc/webUpload/js/uploadForMainPage.js"></script>
            </c:otherwise>
        </c:choose>
		<link rel="stylesheet"
			  href="${webPath}/component/doc/webUpload/css/upload.css?v=${cssJsVersion}"/>
		<link rel="stylesheet"
			  href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}"/>
		<script type="text/javascript"
				src="${webPath}/UIplug/ViewImg/viewerForList.js?v=${cssJsVersion}"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/ViewImg/viewer.css"
			  type="text/css"/>
		<script type="text/javascript"
				src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var fileSize =<%=fileSize%>;
			if (fileSize == null) {
				fileSize = 100 * 1024 * 1024;//如果配置文件中没有配置upload.fileSize属性，默认大小是20M
			}

			function getFilterValArr() {
				var s = docParm.split("&");
				var ParamData = new Object();
				for (var i = 0; i < s.length; i++) {
					var kv = s[i].split("=");
					ParamData[kv[0]] = kv[1];
				}
				return JSON.stringify(ParamData);
			}
			var ifEditFlag = '${ifEditFlag}';
		</script>
		<!-- 加载要件 开始 -->
		<style type="text/css">
			.cover {
				cursor: default;
			}
			.infoTilte{
				margin-left: 14px;
				margin-bottom: 20px;
				margin-top:0px;
			}
			.form-margin{
				margin-top: 20px;
    			margin-left: 4px;
			}
			.set-disabled{
				color:#aaa;
			}
			.button-bac32B5CB{
				background-color:#32B5CB
			}
			.button-bac32B5CB:hover{
			    color: #fff;
			    background-color: #018FA7;
			}
			.button-bacFCB865{
				background-color:#FCB865
			}
			.button-bacFCB865:hover{
				background-color : #E9AA64;
				color : #fff;
			}
			.button-bacE14A47{
				background-color:#E14A47
			}
			.button-bacE14A47:hover{
				color : #fff;
				background-color:#C9302C
			}
			.button-his{
				margin-top: 20px;
			}
			.info-collect{
				margin-top: -30px;
			}	
		</style>
    <script type="text/javascript">
        var appId = '${appId}';
        var aloneFlag = true;
        var dataDocParm={
            relNo:appId,
            docType:"19",
            docTypeName: "反担保资料",
            docSplitNoArr : "[{'docSplitNo':20042315503804210},{'docSplitNo':20042315505645611}]",
            docSplitName: "原始凭证",
            query:''
        };
    </script>
	</head>
<body class="overflowHidden">
    <div class="container">
		<div class="row clearfix">	
			<!--申请主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
				
					<!--头部主要信息 -->
					<c:if test="${mfBusCollateralRel.collateralType== 'account'}">
						<div class="row clearfix head-info">
							<div class="col-xs-3 column text-center head-img">
								<div>
									<button type="button" class="btn btn-font-pledge font-pledge-div padding_left_15">
										<i class="i i-rece font-icon"></i>
										<div class="font-text-left">应收账款</div>
									</button>
								</div>
								<div>${mfBusCollateralRel.vouTypeName}</div>
							</div>
							<div class="col-xs-9 column head-content">
							<div class="row clearfix">
								<!--信息查看入口 -->
								<div class="col-xs-10 column button-his">
									<div class="margin_bottom_10">
										<!-- 应收账款 -->
										<c:if test="${mfBusApply.busModel==5||mfBusApply.busModel==1}">
											<dhcc:pmsTag pmsId="pledge-prove">
												<button class="btn btn-view  btn-lightgray" type="button" id= "recePledge" onclick="MfBusCollateralRelDetail.getRecePledgeInfo();">
													<i class="i i-ruku"></i><span>质押证明</span>
												</button>
											</dhcc:pmsTag>
										</c:if>
										<!-- 保理 -->
										<c:if test="${mfBusApply.busModel==13}">
											<dhcc:pmsTag pmsId="pledge-prove">
												<button class="btn btn-view  btn-lightgray" type="button" id= "recePledge" onclick="MfBusCollateralRelDetail.getReceTranInfo();">
													<i class="i i-ruku"></i><span>转让证明</span>
												</button>
											</dhcc:pmsTag>
										</c:if>
										<c:if test="${mfBusApply.busModel==5||mfBusApply.busModel==13}">
											<dhcc:pmsTag pmsId="pledge-rebate-history">
												<button class="btn btn-view btn-lightgray" type="button" id="rebateHistory" onclick="MfBusCollateralRelDetail.getRebateHistory();">
													<i class="i i-zherang1"></i><span>折让历史</span>
												</button>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="pledge-disputed-history">
												<button class="btn btn-view btn-lightgray" type="button" id="disputedHistory" onclick="MfBusCollateralRelDetail.getDisputedHistory();">
													<i class="i i-rebate"></i><span>争议记录</span>
												</button>
											</dhcc:pmsTag>
										</c:if>
										<!-- 动产质押 -->
										<c:if test="${mfBusApply.busModel==1}">
											<dhcc:pmsTag pmsId="pledge-check-inventory">
												<button class="btn btn-view btn-lightgray" type="button" id= "checkInventory" onclick="MfMoveableCommon.getCheckInventoryInfo();">
													<i class="i i-ruku"></i><span>核库信息</span>
												</button>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="pledge-modify-history">
												<button class="btn btn-view  btn-lightgray " type="button" id="modifyHistory" onclick="MfBusCollateralRelDetail.getModifyHistory();">
													<i class="i i-rebate"></i><span>价格变动</span>
												</button>
											</dhcc:pmsTag>
										</c:if>
										<%--<c:if test="${mfBusApply.busModel==13}">--%>
										<%--<dhcc:pmsTag pmsId="pledge-repo-affirm-info">--%>
											<%--<button class="btn btn-view  btn-lightgray" type="button" id="repoAffirmInfo" onclick="MfBusCollateralRelDetail.getRepoAffirmInfo();">--%>
												<%--<i class="i i-fanzhuanrang"></i><span>反转让确认信息</span>--%>
											<%--</button>--%>
										<%--</dhcc:pmsTag>--%>
										<%--</c:if>--%>
									</div>
								</div>
								<div class="col-xs-2 column">
									<div class="i i-warehouse cus-type-font">
										<div class="type-name-div">${mfBusCollateralRel.collaStsName}</div>
									</div>
								</div>
							</div>
							<div class="row clearfix">
							<div class="col-xs-12 column info-collect">
									<table>
										<tr>
											
											<td>
												<p class="cont-title">转让总额</p>
												<p><span id='envalueAmt' class="content-span">
													<c:if test='${mfBusCollateralRel.receTransAmt == null}'>0.00</c:if>
													<c:if test='${mfBusCollateralRel.receTransAmt != null}'><fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusCollateralRel.receTransAmt}" /></c:if>
												</span></p>
											</td>
											<td>
												<p class="cont-title">转让余额</p>
												<p><span id='pledgeNum' class="content-span">
													<c:if test='${mfBusCollateralRel.receTransAmt == null}'>0.00</c:if>
													<c:if test='${mfBusCollateralRel.receTransAmt != null}'><fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusCollateralRel.receTransAmt}" /></c:if>
												</span></p>
												
												</p>
											</td>
											<td>
												 <p class="cont-title">账款到期日(最早)</p>
												<p><span id='receiptsAmount' class="content-span">											
												${mfBusCollateralRel.receEndDate}
												</span></p>
											</td>
											<td>
											</td>
										</tr>
									</table>
							</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${mfBusCollateralRel.collateralType != 'account'}">
						<div class="row clearfix head-info">
							<div class="col-xs-3 column text-center head-img">
								<div>
									<button type="button" class="btn btn-font-pledge font-pledge-div padding_left_15">
										<i class="i i-pledge font-icon"></i>
										<div class="font-text-left">反担保信息</div>
									</button>
								</div>
								<div>${mfBusCollateralRel.vouTypeName}</div>
							</div>
							<div class="col-xs-9 column head-content">
							<div class="row clearfix">
								<!--信息查看入口 -->
								<div class="col-xs-10 column button-his">
									<div class="margin_bottom_5 clearfix">
										<div >
											<div class="bus-more head-title pull-left">${mfBusApply.cusName}</div>
										</div>
									</div>
									<div class="margin_bottom_10">
										<!-- 应收账款 -->
										<c:if test="${mfBusApply.busModel==5||mfBusApply.busModel==1}">
											<dhcc:pmsTag pmsId="pledge-prove">
												<button class="btn btn-view  btn-lightgray" type="button" id= "recePledge" onclick="MfBusCollateralRelDetail.getRecePledgeInfo();">
													<i class="i i-ruku"></i><span>质押证明</span>
												</button>
											</dhcc:pmsTag>
										</c:if>
										<!-- 保理 -->
										<c:if test="${mfBusApply.busModel==13}">
											<dhcc:pmsTag pmsId="pledge-prove">
												<button class="btn btn-view  btn-lightgray" type="button" id= "recePledge" onclick="MfBusCollateralRelDetail.getReceTranInfo();">
													<i class="i i-ruku"></i><span>转让证明</span>
												</button>
											</dhcc:pmsTag>
										</c:if>
										<c:if test="${mfBusApply.busModel==5||mfBusApply.busModel==13}">
											<dhcc:pmsTag pmsId="pledge-rebate-history">
												<button class="btn btn-view btn-lightgray" type="button" id="rebateHistory" onclick="MfBusCollateralRelDetail.getRebateHistory();">
													<i class="i i-zherang1"></i><span>折让历史</span>
												</button>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="pledge-disputed-history">
												<button class="btn btn-view btn-lightgray" type="button" id="disputedHistory" onclick="MfBusCollateralRelDetail.getDisputedHistory();">
													<i class="i i-rebate"></i><span>争议记录</span>
												</button>
											</dhcc:pmsTag>
										</c:if>
										<!-- 动产质押 -->
										<c:if test="${mfBusApply.busModel==1}">
											<dhcc:pmsTag pmsId="pledge-check-inventory">
												<button class="btn btn-view btn-lightgray" type="button" id= "checkInventory" onclick="MfMoveableCommon.getCheckInventoryInfo();">
													<i class="i i-ruku"></i><span>核库信息</span>
												</button>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="pledge-modify-history">
												<button class="btn btn-view  btn-lightgray " type="button" id="modifyHistory" onclick="MfBusCollateralRelDetail.getModifyHistory();">
													<i class="i i-rebate"></i><span>价格变动</span>
												</button>
											</dhcc:pmsTag>
										</c:if>
										<%--<c:if test="${mfBusApply.busModel==13}">--%>
										<%--<dhcc:pmsTag pmsId="pledge-repo-affirm-info">--%>
											<%--<button class="btn btn-view  btn-lightgray" type="button" id="repoAffirmInfo" onclick="MfBusCollateralRelDetail.getRepoAffirmInfo();">--%>
												<%--<i class="i i-fanzhuanrang"></i><span>反转让确认信息</span>--%>
											<%--</button>--%>
										<%--</dhcc:pmsTag>--%>
										<%--</c:if>--%>
									</div>
								</div>
								<div class="col-xs-2 column">
									<div class="i i-warehouse cus-type-font">
										<div class="type-name-div">${mfBusCollateralRel.collaStsName}</div>
									</div>
								</div>
							</div>
							<div class="row clearfix">
							<div class="col-xs-12 column info-collect">
									<table>
										<tr>
											<td>
												<p class="cont-title">反担保数量</p>
												<p><span id='pledgeNum' class="content-span">
													${mfBusCollateralRel.pledgeNum}
												</span></p>
											</td>
											<dhcc:pmsTag pmsId="pledge-info-amt-rate">
											<td>
												<p class="cont-title">反担保总额</p>
												<p><span id='envalueAmt' class="content-span">
													<c:if test='${mfBusCollateralRel.collateralAmt == null}'>0.00</c:if>
													<c:if test='${mfBusCollateralRel.collateralAmt != null}'><fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusCollateralRel.collateralAmt}" /></c:if>
												</span><span class="unit-span margin_right_25">万</span></p>
											</td>
											<td>
												 <p class="cont-title">反担保比例</p>
												<p><span id='receiptsAmount' class="content-span">
												<c:if test='${mfBusCollateralRel.collateralRate == null}'>0.00</c:if>
												
												<c:if test='${mfBusCollateralRel.collateralRate != null}'><fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusCollateralRel.collateralRate}" /></c:if>
												</span><span class="unit-span margin_right_25">%</span></p>
											</td> 
											</dhcc:pmsTag>
											<td>
											</td>
										</tr>
									</table>
							</div>
								</div>
							</div>
						</div>
					</c:if>
					<%-- <c:if test='${busEntrance=="1" || busEntrance=="2" || busEntrance=="3"|| entrance=="credit"}'> --%>
						<div class="row clearfix btn-opt-group" id="btn-group-div">
							<div class="col-xs-12 column">
								<div class="btn-group pull-right">
									<dhcc:pmsTag pmsId="add-pledge-info">
										<c:if test="${ifEditFlag == '1'}">
										<button class="btn btn-opt" id ="addCollateralInfo" onclick="MfBusCollateralRelDetail.addCollateralInfo();" type="button">
												<i class="i i-jia1"></i><span>新增</span>
										</button>
										</c:if>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="add-pledge-rel">
										<button class="btn btn-opt" id ="addPledgeRel" onclick="MfBusCollateralRelDetail.agenciesPledgeRelList();" type="button">
											<i class="i i-jia1"></i><span>关联合作银行</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="pledge-batch-inStock">
										<button id="batchInStock" class="btn btn-opt" onclick="MfBusCollateralRelDetail.inStockCollateralBatch();" type="button">
											<i class="i i-ruku"></i><span>入库</span>
										</button>
									</dhcc:pmsTag>
									<!-- 动产质押业务模式 -->
										<dhcc:pmsTag pmsId="pledge-transfer-apply">
											<button id="transferApply" class="btn btn-opt" onclick="MfMoveableCommon.transferApplyInput();" type="button">
												<i class="i i-yiku"></i><span>移库</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-claim-goods-apply">
											<button id="claimGoodsApply" class="btn btn-opt" onclick="MfMoveableCommon.claimGoodsApplyInput();" type="button">
												<i class="i i-tihuo"></i><span>提货</span>
											</button>
										</dhcc:pmsTag>
										<!-- 提前出库 等同于提货 -->
										<dhcc:pmsTag pmsId="pledge-ahead-delivery">
											<button id="claimGoodsApply" class="btn btn-opt" onclick="MfMoveableCommon.aheadDeliveryApplyInput();" type="button">
												<i class="i i-tihuo"></i><span>提前出库</span>
											</button>
										</dhcc:pmsTag>
										
										<dhcc:pmsTag pmsId="pledge-claim-goods-affirm">
											<button id="claimGoodsAffirm" class="btn btn-opt" style="display:none" onclick="MfMoveableCommon.claimGoodsAffirm();" type="button">
												<i class="i i-tihuo"></i><span>提货确认</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-moveable-modify">
											<button id="moveableModify" class="btn btn-opt" onclick="MfMoveableCommon.moveableModify();" type="button">
											<i class="i i-tiaojia"></i><span>调价</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-moveable-compentstate">
											<button id="moveableCompentstate" class="btn btn-opt" onclick="MfMoveableCommon.moveableCompentstate();" type="button">
											<i class="i i-buhuo"></i><span>跌价补偿</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-moveable-compentstate-confirm">
											<button id="moveableCompentstateConfirm" class="btn btn-opt" style="display:none" onclick="MfMoveableCommon.moveableCompentstateConfirm();" type="button">
											<i class="i i-buhuo"></i><span>跌价补偿确认</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-patrol-inventory">
											<button id="patrolInventory" class="btn btn-opt" style="" onclick="MfMoveableCommon.patrolInventoryApplyInput();" type="button">
												<i class="i i-xunku"></i><span>巡库</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-account-check">
											<button id="accountCheck" class="btn btn-opt" style="" onclick="MfMoveableCommon.accountCheckInput();" type="button">
												<i class="i i-duizhang"></i><span>对账</span>
											</button>
										</dhcc:pmsTag>
									<!-- 保兑仓业务模式 -->
									<c:if test="${mfBusApply.busModel==4}">
										<dhcc:pmsTag pmsId="pledge-moveable-buy-back">
											<button id="moveableBuyBack" class="btn btn-opt" onclick="MfBusCollateralRelDetail.moveableBuyBack();" type="button">
												<i class="i i-rebate"></i><span>回购申请</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-moveable-buy-back-confirm">
											<button id="moveableBuyBackConfirm" class="btn btn-opt" style="display:none" onclick="MfBusCollateralRelDetail.moveableBuyBackConfirm();" type="button">
												<i class="i i-rebate"></i><span>回购确认</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<!-- 应收账款融资和保理 -->
									<c:if test="${mfBusApply.busModel==13||mfBusApply.busModel==5}">
										<dhcc:pmsTag pmsId="pledge-rebate-apply">
											<button id="rebatePledge" class="btn btn-opt" onclick="MfBusCollateralRelDetail.rebatePledge();" type="button">
												<i class="i i-zherang1"></i><span>折让</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-rebate-affirm">
											<button id="rebateAffirm" class="btn btn-opt" style="display: none;" onclick="MfBusCollateralRelDetail.rebateAffirm();" type="button">
												<i class="i i-zherang1"></i><span>折让确认</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-disputed-deal-apply">
											<button id="disputedDealPledge" class="btn btn-opt" onclick="MfBusCollateralRelDetail.disputedDealPledge();" type="button">
												<i class="i i-rebate"></i><span>争议处理</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-relieve-disputed">
											<button id="relieveDisputed" class="btn btn-opt" style="display: none;" onclick="MfBusCollateralRelDetail.relieveDisputed();" type="button">
												<i class="i i-rebate"></i><span>争议解除</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<%--<!-- 保理业务 -->--%>
									<%--<c:if test="${mfBusApply.busModel==13}">--%>
										<%--<dhcc:pmsTag pmsId="pledge-buy-back-apply">--%>
											<%--<button id ="buyBackPledge" class="btn btn-opt" onclick="MfBusCollateralRelDetail.buyBackPledge();" type="button">--%>
												<%--<i class="i i-fanzhuanrang"></i><span>反转让</span>--%>
											<%--</button>--%>
										<%--</dhcc:pmsTag>--%>
										<%--<dhcc:pmsTag pmsId="pledge-rece-repo-affirm">--%>
											<%--<button id ="receRepoAffirm" class="btn btn-opt" style="display: none;" onclick="MfBusCollateralRelDetail.receRepoAffirm();" type="button">--%>
												<%--<i class="i i-fanzhuanrang"></i><span>反转让确认</span>--%>
											<%--</button>--%>
										<%--</dhcc:pmsTag>--%>
									<%--</c:if>--%>
									<dhcc:pmsTag pmsId="pledge-batch-out-stock">
										<button id="batchOutStock" class="btn btn-opt" onclick="MfBusCollateralRelDetail.outStockCollateralBatch();" type="button">
											<i class="i i-chuku"></i><span>出库</span>
										</button>
									</dhcc:pmsTag>
									<div class="btn-group hidden-lg hidden-md hidden-sm">
										<button type="button" class="btn btn-opt dropdown-toggle"  data-toggle="dropdown">
											更多<span class="caret"></span>
										</button>
									</div>
								</div>
							</div>
						</div>
					<%-- </c:if> --%>
					<!--押品信息-->
					<div class="row clearfix"  id = "pledgeBaseInfo">
						<div class="col-xs-12 column info-block">
							<div class="block-add" style="display: none;"></div>
						</div>
					</div>
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
					<%--<div class="row clearfix">
						<div class="col-xs-12 column" >
 							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
 						</div>
					</div>--%>
				</div>
			</div>
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<jsp:include page="/component/cus/MfCusCustomer_AbstractInfo.jsp?cusNo=${cusNo}"/>
					<c:choose>
						<c:when test="${busEntrance == 'credit' || busEntrance== 'cus_credit' || busEntrance == 'cus_core_company' || busEntrance == 'bus_trench'}">
							<jsp:include page="/component/auth/MfCusCredit_AbstractInfo.jsp?cusNo=${cusNo}&creditAppId=${jumpCreditAppId}"/>
						</c:when>
						<c:when test="${busEntrance == 'rece_finc'}">
							<jsp:include page="/component/pact/receaccount/MfBusFincAppMain_AbstractInfo.jsp?appId=${appId}&pactId=${pactId}&fincMainId=${fincMainId}&busEntrance=${busEntrance}"/>
						</c:when>
						<c:otherwise>
							<jsp:include page="/component/app/MfBusApply_AbstractInfo.jsp?frompage=collateral&appId=${appId}"/>
						</c:otherwise>
					</c:choose>
					<c:if test="${mfBusApply.busModel=='13'}">
						<jsp:include page="/component/pact/receaccount/MfBusReceBaseInfo_AbstractInfo.jsp"/>
					</c:if>
					<c:if test="${mfBusApply.busModel=='34'}">
						<jsp:include page="/component/collateral/leases/MfBusCollateralRel_leases_AbstractInfo.jsp"/>
					</c:if>
					
					<!--信息变更记录-->
					<dhcc:pmsTag pmsId="mf-message-change-record">
						<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
    var projectName = '${projectName}';
</script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfBusCollateralRel_Detail.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/ShowPledgeDetailInfo.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfMoveableCommon.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_EntList.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/notie/notie.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/realEstateAssessment.js"></script>
<script type="text/javascript">
		var wkfAppId,cusNo,scNo,fincId;
		var collateralType = '${collateralType}';
		if(collateralType ==''|| collateralType=='null' || collateralType==null){
			collateralType='pledge';
		}
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var creditAppId = '${creditAppId}';
		var jumpCreditAppId = '${jumpCreditAppId}';
		fincId = "${fincId}";
		cusNo = "${cusNo}";
		var baseType = '${baseType}';
		appId = "${appId}";
		wkfAppId = '${mfBusApply.wkfAppId}';
		scNo = '${scNo}';
		var relId = "${relId}";
		var webPath = '${webPath}';
		var opType = "<%=(String)request.getAttribute("opType")%>";
		var vouType ='${mfBusApply.vouType}';
		var entrance = '${entrance}';
		var appSts= '${mfBusApply.appSts}';
		var busCollateralId='${mfBusCollateralRel.busCollateralId}';
		var busPleId='${mfBusCollateralRel.busCollateralId}';
        var query = '${query}';
		var queryFile = '${queryFile}';
		var docParm ="query="+queryFile+"&cusNo="+cusNo+"&relNo="+appId+"&scNo="+scNo;//查询文档信息的url的参数
		var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&fincId=' + fincId ;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
		var busSts='${busSts}';
		var instockAllflag='${instockAllflag}';
		var outstockAllflag='${outstockAllflag}';
		var tranFlag='${tranFlag}';
		var collaSts='${mfBusCollateralRel.collaSts}';
		var repoFlag='${repoFlag}';
		var repoSts='${mfPleRepoApply.appSts}';
		var operable='${operable}';
		var pactSts= '${mfBusPact.pactSts}';
		var pactId= '${mfBusPact.pactId}';
		var fincSts= '${fincSts}';
		var busModel= '${busModel}';
		var evalFlag= '${evalFlag}';
		var busEntrance= '${busEntrance}';
		var inBatchSts='${mfBusCollateralRel.inBatchSts}';
		var outBatchSts='${mfBusCollateralRel.outBatchSts}';
		var keepId='${mfBusCollateralRel.keepId}';
		var busEndDate = '${busEndDate}';
		var authCycle = '${mfBusPact.authCycle}';
		var isAllFincEnd = '${isAllFincEnd}';
		var nodeNo = "pledge_reg";
		var temBizNo = appId;
		var querySaveFlag_pl = "0";
        var docShowType = "${docShowType}";
        var extensionApplyId = "${extensionApplyId}";
		$(function(){
			MfBusCollateralRelDetail.init();
		});

        // 单字段编辑的保存回调方法。
        function oneCallback(data,disVal) {
            var name = data[0].name;
            var value = data[0].value;
            if(name == "insType"){//
                BASE.oneRefreshTable(name,disVal);
            }else{
                BASE.oneRefreshTable(name,value);
            }
            MfBusCollateralRelDetail.updateCollateral();
            var $_form = this;
            var formAction = $_form.attr("action");
            if(formAction == webPath+"/certiInfo/updateAjaxByOne") {//权证单字段更新刷新押品基础表
                var collateralNo;
                for(j = 0; j < data.length; j++) {
					if("collateralId"==data[j].name){
                        collateralNo=data[j].value;
                        break;
					}
                }
                MfBusCollateralRelDetail.updateBaseCollateralInfo(collateralNo);
            }

        }
		</script>
		<script type="text/javascript">								
				//首次抵押余额 小于 等于 抵押物估值 在首次抵押余额判断
				var judgeOfValue = function(firstPle){									
					var extNumValueStr = $("span[class=inputcontent]").find("input[title='首次抵押余额']").val();											
					var pleOriginalValueStr = $("input[name=pleOriginalValue]").val();//抵押物估值
					
					var flag;
					if(pleOriginalValueStr != "" && extNumValueStr != ""){
						var	pleOriginalValue = parseFloat(pleOriginalValueStr.replace(/,/g,""));//替换掉逗号 并转换成float类型				
						var extNumValue = parseFloat(extNumValueStr.replace(/,/g,""));
						flag = pleOriginalValue < extNumValue?true:false;
					}
					if(flag){					
						window.top.alert("首次抵押余额不能大于抵押物估值！", 0);						
						$("input[title='首次抵押余额']").val("");					
						flag=false;	
					}									
				}
				//首次抵押余额 小于 等于 抵押物估值 在首次抵押余额判断
				var judgeOfValue2 = function(ele){
					var extNumValueStr = $("input[name=extNum03]").val();					
					var pleOriginalValueStr = $("span[class=inputcontent]").find("input[title='抵押物估值']").val();
					
					var flag;
					if(pleOriginalValueStr != "" && extNumValueStr != ""){
						var	pleOriginalValue = parseFloat(pleOriginalValueStr.replace(/,/g,""));//替换掉逗号 并转换成float类型				
						var extNumValue = parseFloat(extNumValueStr.replace(/,/g,""));
						flag = pleOriginalValue < extNumValue?true:false;
					}
					if(flag){					
						window.top.alert("首次抵押余额不能大于抵押物估值！", 0);						
						$("input[title='首次抵押余额']").val("");
						$("input[name=extNum03]").val("");
						$('input[name=extNum03]').parents("tr").eq(1).find('td').eq(0).find('.fieldShow').html("");				
						flag=false;	
					}									
				}
				
				//房屋坐落修改为抵押物地址，选择区域后填详细地址
				var selectAreaCallBack = function(areaInfo){
					$("input[name=pleAddress]").val(areaInfo.disName);
				};
		
		</script>
</html>