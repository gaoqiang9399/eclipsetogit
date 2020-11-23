<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
</head>
<body class="bg-white" style="height: 100%;">
	<c:choose>
		<c:when test="${entryFlag != null and entryFlag == '1'}">
			<div class="scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;">
		</c:when>
		<c:otherwise>
			<div class="scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;padding:0px;">
		</c:otherwise>
	</c:choose>
		<div class="col-xs-10 col-xs-offset-1" style="margin-top:20px;">
			<div class="arch_sort" style="border: 0px;">
				<div class="dynamic-block" title="${showName }申请" name="MfAssetsDisposalAction" data-sort="14" data-tablename="mf_oa_dimission">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>${showName }申请</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfAssetsDisposalAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div disable="true" class="content collapse in" id="MfAssetsDisposalAction" style="margin-top: 10px;">
							<form method="post" id="MfAssetsDisposalActionForm" theme="simple" name="operform" action="${webPath}/mfAssetsDisposal/insertAjax">
								<dhcc:propertySeeTag property="formAssetsDisposalDetail" mode="query" />
							</form>
						</div>
						<!--资产资料 -->
						<div class="row clearfix">
							<div class="col-xs-12 column" >
								<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:if test = "${mfAssetsDisposal.applySts != 0}">
			<div class="arch_sort">
					<div class="col-xs-12 column info-block">
						<div id="spInfo-block" class="approval-hist">
							<div class="list-table">
							   <div class="title">
									 <span><i class="i i-xing blockDian"></i>${showName }申请审批历史</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
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
			</c:if>
		</div>
		<div style="height: 10px;"></div>
	</div>
	<c:if test = "${entryFlag != null and entryFlag == '1'}">
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</c:if>
</div>
<script type="text/javascript" src="${webPath}/component/assetsmanage/js/MfAssetsDisposal_Detail.js"></script>
<script type="text/javascript">
		var handleType  = "${handleType }";
		var disposalId = '${disposalId}';
		var assetsManageId = '${assetsManageId}';
		var showName = '${showName}';
		var applySts = '${mfAssetsDisposal.applySts}';
		var aloneFlag = true;
		var dataDocParm={
				relNo:assetsManageId,
				docType:"assetsDisposalDoc",
				docTypeName:"资产资料",
				docSplitName:"资产资料",
				query:'query'
			};
		$(function(){
			MfAssetsDisposal_Detail.init();
		});	

</script>
</body>
</html>