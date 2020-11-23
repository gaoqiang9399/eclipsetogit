<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<link rel="stylesheet" href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css" />
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript">
		var webPath = "${webPath}";
		var id = '${id}';
		var applySts = '${mfRiskLevelAdjust.applySts}';
		var operateType = '${mfRiskLevelAdjust.operateType}';
		var aloneFlag = true;
		var dataDocParm = {//页面使用上传附件组件
			relNo : id,
			docType : "messageDoc",
			docTypeName : "附件资料",
			docSplitName : "附件资料",
			query : 'query'
		};
</script>
</head>
<body class="bg-white" style="height: 100%;">
	<div class="scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;">
		<div class="col-xs-10 col-xs-offset-1" style="margin-top:20px;">
			<div class="arch_sort" style="border: 0px;">
				<div class="dynamic-block" title="风险处置" name="MfRiskLevelAdjustAction" data-sort="14" data-tablename="mf_risk_level_adjust">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>风险处置</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfRiskLevelAdjustAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div disable="true" class="content collapse in" id="MfRiskLevelAdjustAction" style="margin-top: 10px;">
							<form method="post" id="MfRiskLevelAdjustActionForm" theme="simple" name="operform" action="${webPath}/mfRiskLevelAdjust/insertAjax">
								<dhcc:propertySeeTag property="formriskleveladjustdetail" mode="query" />
							</form>
						</div>
						<!--上传文件-->
						<div class="row clearfix">
							<div class="col-xs-12 col-md-offset-0 column">
								<%@include
									file="/component/doc/webUpload/uploadutil.jsp"%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:if test = "${mfRiskLevelAdjust.applySts != '0' and mfRiskLevelAdjust.applySts != ''}">
			<div class="arch_sort">
				<div id="riskLevelAdjustApproveHis" class="row clearfix" style="display: none;">
					<div class="col-xs-12 column info-block">
						<div id="spInfo-block">
							<div class="form-table">
							   <div class="title">
									 <span><i class="i i-xing blockDian"></i>风险处置审批历史</span>
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
			</div>
			</c:if>
		</div>
		<div style="height: 10px;"></div>
	</div>
	<c:if test = "${entryFlag != null and entryFlag == '1'}">
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<c:if test = "${mfRiskLevelAdjust.applySts == '0'}">
				<dhcc:thirdButton value="提交申请" action="提交申请" onclick="MfRiskLevelAdjust_Detail.ajaxSave()"></dhcc:thirdButton>
			</c:if>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</c:if>
</div>
<script type="text/javascript" src="${webPath}/component/risk/risklevelmanage/js/MfRiskLevelAdjust_Detail.js"></script>
<script type="text/javascript"
		src="${webPath}/component/risk/risklevelmanage/js/MfRiskLevelAdjust_Insert.js"></script>
<script type="text/javascript">
		var comeFrom =  '${comeFrom}';
		var mfBusFincAppListSize =  '${mfBusFincAppListSize}';
		var webPath = '${webPath}';
		$(function(){
			MfRiskLevelAdjust_Detail.init();
		});	
</script>
</body>
</html>