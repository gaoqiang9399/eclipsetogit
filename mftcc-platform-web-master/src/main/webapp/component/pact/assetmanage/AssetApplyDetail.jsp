<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>申请详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" /> 
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/finance/js/cw_common.js"></script>
		<script type="text/javascript">
		//var operable = '${operable}';
		var wkfAppId = '${mfAssetManage.wkfAppId}';
		var cusNo = '${mfAssetManage.cusNo}';
		var assetId = '${mfAssetManage.assetId}';
	</script>
	</head>
	<body class="bodybg-gray" style="background: white;">
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<div class="row clearfix hide" id="showWkf">
						<div class="app-process">
							<div id="modeler1" class="modeler">
								<ul id="wj-modeler1" class="wj-modeler" isApp="true">
								</ul>
							</div>
						</div>
					</div>
					<!-- 申请业务流程提示信息 -->
					<div class="row clearfix btn bg-danger next-div hide">
						<div class="col-xs-8 column text-center">
							<div class="block-next"></div>
						</div>
					</div> 
					
					<!--头部主要信息 -->
					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img padding_top_20">
								<div style="position: relative;">
									<button type="button" class="btn btn-font-app padding_left_15 font-app-div">
										<i class="i i-applyinfo font-icon"></i>
										<div class="font-text-left">资产信息</div>
									</button>
								</div>
							</div>
						<!--概要信息 -->
						<div class="col-xs-9 column head-content">
							<div class="margin_bottom_5 clearfix">
								<div class="bus-more head-title pull-left" title="${finBusApply.cusName}">
								<s:if test="">
									依法收贷申请
								</s:if>
								<!-- <s:else>
									<span class="unregistered">未登记</span>
								</s:else> -->
								</div>
							</div>
							<div>
								<table class="finpersonbaseinfo">
									<tr>
										<td>
											<p class="cont-title">贷款笔数</p>
											<p>
											<c:if test="${mfAssetManage.loanNum ne null}">
												<span id="appAmt" class="content-span">${mfAssetManage.loanNum}</span>
												<span class="unit-span margin_right_25">笔</span>
											</c:if>
											<c:if test="${mfAssetManage.loanNum eq null}">
												<span class="unregistered">未登记</span>
											</c:if>
											</p>
										</td>
										<td>
											<p class="cont-title">贷款总额</p>
											<p>
											<c:if test="${mfAssetManage.loanAmt ne null}">
												<span id="term" class="content-span"></i>${mfAssetManage.loanAmt}</span>
												<span class="unit-span margin_right_25">元</span>
											</c:if>
											<c:if test="${mfAssetManage.loanAmt eq null}">
												<span class="unregistered">未登记</span>
											</c:if>	
											</p>
										</td>
										<td>
											<p class="cont-title">罚息金额</p>
											<p>
											<c:if test="${mfAssetManage.penaltyAmount ne null}">
												<span id="fincRate" class="content-span">${mfAssetManage.penaltyAmount }</span>
												<span class="unit-span">元</span>
											</c:if>
											<c:if test="${mfAssetManage.penaltyAmount eq null}">
												<span class="unregistered">未登记</span>
											</c:if>
											</p>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<c:if test="${mfAssetManage.wkfAppId eq null}">
					<div class="row clearfix btn-opt-group">
						<div class="col-xs-12 column">
							<div class="btn-group pull-right">
								<dhcc:pmsTag pmsId="apply-new-btn">
									<button id="zhifu" class="btn btn-opt" onclick="AssetApplyDetail.submitProgress();">
										<i class="i i-fundorg"></i><span>提交审批</span>
									</button>
								</dhcc:pmsTag>
							</div>
						</div>
					</div>
					</c:if>
					<div class="row clearfix" id="spInfo-block">
						<div class="col-xs-12 column">
							<div class="form-table">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>申请信息</span>
									<button class="btn btn-link pull-right formAdd-btn"
										data-toggle="collapse" data-target="#spInfo-div">
										<i class='i i-close-up'></i> <i class='i i-open-down'></i>
									</button>
								</div>
								<div class="content margin_top_15 collapse in " id="spInfo-div">
									<form method="post" theme="simple" name="operform" id="pact">
										<dhcc:propertySeeTag property="formassetmanageinfo" mode="query" />
									</form>
								</div>
								<!--可编辑状态-->
								<%-- <div class="content" id="applyDetailInfo">
									<form action=""  id="htmlStr" >
											${dataMap.htmlStr}
									</form>
								</div> --%>
								<!-- 审批历史-->
								<c:if test="${mfAssetManage.wkfAppId != null and mfAssetManage.wkfAppId != ''}">
									<div class="arch_sort">
										<div id="leaveApproveHis" class="row clearfix"
											style="display: none;">
											<div class="col-xs-12 column info-block">
												<div id="spInfo-block" class="approval-hist">
													<div class="list-table">
														<div class="title">
															<span><i class="i i-xing blockDian"></i>依法收贷审批历史</span>
															<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div1">
																<i class="i i-close-up"></i><i class="i i-open-down"></i>
															</button>
														</div>
														<div class="content margin_left_15 collapse in "
															id="spInfo-div1">
															<div class="approval-process">
																<div id="modeler1" class="modeler">
																	<ul id="wj-modeler2" class="wj-modeler" isApp="false">
																	</ul>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
					<!-- 0000 -->
				</div>
			</div>
		<!-- 申请附属信息 -->
		<div class="col-md-4 column block-right">
			<div class="bg-white block-right-div">
				<!--客户信息视图块  -->
				<jsp:include page="/component/cus/MfCusCustomer_AbstractInfo.jsp?cusNo=${mfAssetManage.cusNo}"/>
			</div>
		</div>
	</div>		
	</body>
<script type="text/javascript" src="${webPath}/component/pact/assetmanage/js/AssetApplyDetail.js"></script>
<script type="text/javascript">
	$(function() {
		AssetApplyDetail.init();
	});
</script>	
</html>
