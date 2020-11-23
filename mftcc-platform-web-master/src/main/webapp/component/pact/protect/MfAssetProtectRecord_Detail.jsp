<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>资产保全详情-查询</title>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/component/pact/protect/js/MfAssetHandleInfo_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/pact/protect/js/MfAssetProtectRecord_Insert.js"></script>
		<link rel="stylesheet" href="${webPath}/component/lawsuit/css/MfLawsuit_Detail.css?v=${cssJsVersion}"></link>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript">
			var protectId = '${protectId}';
			var handleFlag = '${handleFlag}';
			var handleType='${handleType}';
			var aloneFlag = true;
			var dataDocParm={
					relNo:protectId,
					docType:"assetProtectDoc",
					docTypeName:"资产资料",
					docSplitName:"资产资料",
					query:''
				};
			$(function() {
				MfAssetHandleInfoInsert.init();
				showWkfFlowVertical($("#wj-modeler4"),protectId,"15","assets_protect_approval");
			});
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content" style="padding:10px,10px,10px,0px;">
			<div class="row clearfix head-info">
				<div class="col-md-3 text-center head-img padding_top_20">
					<div style="position:relative;">
						<button type="button" class="btn btn-font-pact font-pact-div padding_left_15">
							<i class="i i-lawsuit font-icon"></i>
							<div class="font-text-left" style="margin-top:4px">资产保全</div>
						</button>
					</div>
				</div>
				<div class="col-md-6 head-content">
					<div class="bus head-title" title="${mfAssetProtectRecord.assetNo}">${mfAssetProtectRecord.assetName}</div>
					<div class="margin_top_10">
						<table>
							<tr>
								<td>
									<p class="cont-title">资产类型</p>
									<p><span class="content-span font_size_18">${mfAssetProtectRecord.assetTypeName}</span></p>
								</td>
								<%--<td>--%>
									<%--<p class="cont-title">资产价值</p>--%>
									<%--<p><span class="content-span font_size_18">${mfAssetProtectRecord.assetAmtStr}</span><span class="unit-span margin_right_25">元</span> </p>--%>
								<%--</td>--%>
								<%--<td>--%>
									<%--<p class="cont-title">资产所有权人名称</p>--%>
									<%--<p><span class="content-span font_size_18">${mfAssetProtectRecord.cretificateName}</span></p>--%>
								<%--</td>--%>
							</tr>
						</table>
					</div>
				</div>
				<div class="col-md-3">
					<div class="i i-warehouse seal-font">
						<div class="seal-name-div">${mfAssetProtectRecord.assetStateName}</div>
					</div>
				</div>
			</div>
			<!--资产保全详情 -->
			<div class="row clearfix">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>资产信息</span>
						<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#suitDetail"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
					</div>
					<div id="suitDetail" class="content collapse in">
						<div id="assetProtect-form" class="row clearfix">
							<div class="col-md-12 col-md-offset-0 column margin_top_20">
								<div class="bootstarpTag">
									<form id="edit-form" theme="simple" name="operform" action="">
										<dhcc:propertySeeTag property="formassetbaseinfo" mode="query" ifBizManger="3"/>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--资产资料 -->
			<div class="row clearfix">
				<div class="col-xs-12 column" >
					<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
				</div>
			</div>
			<!-- 资产保全审批历史 -->
			<c:if test="${mfAssetProtectRecord.assetState == 2 || mfAssetProtectRecord.assetState == 3 || mfAssetProtectRecord.assetState == 4}">
			<div class="row clearfix approval-hist" id="spInfo-block">
				<div class="list-table">
				   <div class="title">
						 <span><i class="i i-xing blockDian"></i>资产保全审批历史</span>
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
			<div id="suitFollowDiv" class="row clearfix">
				<div class="list-table">
					<!--资产处置 新增-->
					<div id="assetHandleTitle" style="display: none;"class="title">
						<span><i class="i i-xing blockDian"></i>资产处置</span>
						<button id="addAssetHandle" class="btn btn-link formAdd-btn" onclick="MfAssetHandleInfoInsert.addAssetHandle();" title="资产处置"><i class="i i-jia3"></i></button>
						<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#suitTrack"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
					</div>
					<div id="suitTrack" class="content collapse in">
						<!--资产处置登记 -->
						<div id="follow-form" class="row clearfix" style="display: none;">
							<div class="col-md-10 col-md-offset-1 column margin_top_20">
								<div class="bootstarpTag">
									<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
									<form method="post" id="MfAssetHandleInfoForm" theme="simple" name="operform" action="${webPath}/mfAssetHandleInfo/insertAjax">
										<dhcc:bootstarpTag property="formassethandle" mode="query" />
									</form>
								</div>
								<div id="saveButton" class="formRowCenter background-border-none position-fixed">
									<dhcc:thirdButton value="保存" action="保存" onclick="MfAssetHandleInfoInsert.saveAssetHandleAjax('#MfAssetHandleInfoForm')"></dhcc:thirdButton>
									<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfAssetHandleInfoInsert.cancelAssetHandle();"></dhcc:thirdButton>
								</div>
							</div>
						</div>
						<!--资产处置详情-->
						<div id="detail-form" class="row clearfix" style="display: none;">
							<div class="col-md-12 col-md-offset-0 column margin_top_20">
								<div class="bootstarpTag">
									<form id="assetHandleDetailInfoForm" theme="simple" name="operform" action="">
										<dhcc:propertySeeTag property="formassethandle" mode="query" />
									</form>
								</div>
							</div>
						</div>
						<!--提交资产保全审批 -->
						<div id="submitApprove" class="row clearfix" style="display: none;">
							<div class="col-md-10 col-md-offset-1 column margin_top_20">
								<div id="saveButton" class="formRowCenter background-border-none position-fixed">
									<dhcc:thirdButton value="提交" action="提交" onclick="MfAssetProtectRecordInsert.submitAjax()"></dhcc:thirdButton>
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