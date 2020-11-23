<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>资产管理详情-查询</title>
		<script type="text/javascript" src="${webPath}/component/assetsmanage/js/MfAssetsManage_Detail.js"></script>
		<script type="text/javascript">
			var assetsManageId = '${assetsManageId}';
			var assetsSts = '${mfAssetsManage.assetsSts}';
			var appStsShow = '${appStsShow}';
			var appSts = '${appSts}';
			var aloneFlag = true;
			var dataDocParm={
					relNo:assetsManageId,
					docType:"assetProtectDoc",
					docTypeName:"资产资料",
					docSplitName:"资产资料",
					query:''
				};
			$(function() {
				MfAssetsManage_Detail.init();
			});
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="row clearfix head-info"  style="padding:20px 20px 0px 20px;">
				<div class="col-md-3 text-center head-img padding_top_20">
					<div style="position:relative;">
						<button type="button" class="btn btn-font-pact font-pact-div padding_left_15">
							<i class="i i-lawsuit font-icon"></i>
							<div class="font-text-left" style="margin-top:4px">资产管理</div>
						</button>
					</div>
				</div>
				<div class="col-md-6 head-content">
					<div class="bus head-title" title="${mfAssetsManage.pledgeNo}">${mfAssetsManage.pledgeName}</div>
					<div class="margin_top_10">
						<table>
							<tr>
								<td>
									<p class="cont-title">资产类型</p>
									<p><span class="content-span font_size_18">${pledgeBaseInfo.classSecondName}</span></p>
								</td>
								<td>
									<p class="cont-title">评估价值</p>
									<p><span class="content-span font_size_18">${mfAssetsManage.assessAmtStr}</span><span class="unit-span margin_right_25">元</span> </p>
								</td>
								<td>
									<p class="cont-title">抵债金额</p>
									<p><span class="content-span font_size_18">${mfAssetsManage.debtAmtStr}</span><span class="unit-span margin_right_25">元</span> </p>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="col-md-3">
					<div class="i i-warehouse seal-font">
						<div class="seal-name-div">${mfAssetsManage.assetsStsShow }</div>
					</div>
				</div>
			</div>
			<!--资产管理详情 -->
			<div class="row clearfix" style="padding:0px 20px 0px 20px;">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>资产信息</span>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#suitDetail"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						<dhcc:pmsTag pmsId="assets-addDisposal-btn">
						<c:choose>
							<c:when test="${appSts != '1'}">
								<button class="btn btn-link pull-right formAdd-btn" name="assetsManage" onclick="MfAssetsDailyManage_Detail.addDisposal('1');" title="拍卖"><i class="i i-paimai">拍卖</i></button>
								<button class="btn btn-link pull-right formAdd-btn" name="assetsManage" onclick="MfAssetsDailyManage_Detail.addDisposal('2');" title="租赁"><i class="i i-lease">租赁</i></button>
							</c:when>
							<c:otherwise>
							<button class="btn btn-link pull-right formAdd-btn" name="assetsManage"  title="拍卖" disabled="disabled"><i class="i i-paimai">拍卖</i></button>
							<button class="btn btn-link pull-right formAdd-btn" name="assetsManage"  title="租赁" disabled="disabled"><i class="i i-lease">租赁</i></button>
							</c:otherwise>
						</c:choose>
						</dhcc:pmsTag>
					</div>
					<div id="suitDetail" class="content collapse in">
						<div id="assetProtect-form" class="row clearfix">
							<div class="col-md-12 col-md-offset-0 column margin_top_20">
								<div class="bootstarpTag">
									<form id="edit-form" theme="simple" name="operform" action="${webPath }/mfAssetsManage/updateAjaxByOne">
										${detailHtml}
									</form>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
			<!--资产资料 -->
			<div class="row clearfix" style="padding:0px 20px 0px 20px;">
				<div class="col-xs-12 column" >
					<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
				</div>
			</div>
			<!--资产日常管理 -->
			<dhcc:pmsTag pmsId="assets-daily-manage">
			<div class="row clearfix" style="padding:0px 20px 20px 20px;">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>资产日常管理</span>
						<button class="btn btn-link formAdd-btn" onclick="MfAssetsManage_Detail.insertDailyManage();" title="新增管理"><i class="i i-jia3"></i></button>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#manageDetail"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
					</div>
					<div id="dailyManageDetail" class="content collapse in">
						<div id="assetProtect-form" class="row clearfix">
							<div class="col-md-12 col-md-offset-0 column margin_top_20">
								<div id="dailyManage" class="table_content" style="height: auto;">
									${dailyManageHtml }
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</dhcc:pmsTag>
			<!--资产盘活 -->
			<dhcc:pmsTag pmsId="assets-lease">
			<div class="row clearfix" style="padding:0px 20px 20px 20px;">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>资产盘活</span>
						<button class="btn btn-link formAdd-btn" onclick="MfAssetsManage_Detail.chooseDisposal('2');" title=""><i class="i i-jia3"></i></button>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#manageDetail"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
					</div>
					<div id="leaseDetail" class="content collapse in">
						<div id="assetProtect-form" class="row clearfix">
							<div class="col-md-12 col-md-offset-0 column margin_top_20">
								<div id="content" class="table_content" style="height: auto;">
									${leaseHtml }
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</dhcc:pmsTag>
			<!--资产处置 -->
			<dhcc:pmsTag pmsId="assets-disposal">
			<div class="row clearfix" style="padding:0px 20px 20px 20px;">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>资产处置</span>
						<button class="btn btn-link formAdd-btn" onclick="MfAssetsManage_Detail.chooseDisposal('1');" title=""><i class="i i-jia3"></i></button>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#manageDetail"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
					</div>
					<div id="disposalDetail" class="content collapse in">
						<div id="assetProtect-form" class="row clearfix">
							<div class="col-md-12 col-md-offset-0 column margin_top_20">
								<div id="content" class="table_content" style="height: auto;">
									${auctionHtml }
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			</dhcc:pmsTag>
			<!--资产管理记录 -->
			<dhcc:pmsTag pmsId="assets-manage-record">
			<div class="row clearfix" style="padding:0px 20px 20px 20px;">
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>资产管理记录</span>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#manageDetail"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
					</div>
					<div id="manageDetail" class="content collapse in">
						<div id="assetProtect-form" class="row clearfix">
							<div class="col-md-12 col-md-offset-0 column margin_top_20">
								<div id="content" class="table_content" style="height: auto;">
									${allHtml }
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</dhcc:pmsTag>
	</div>
	<div id="approvalBtn" class="formRowCenter " style="display:block;">
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	</div>
</body>
</html>