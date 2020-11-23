<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<link rel="stylesheet"
	href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css" />
<link rel="stylesheet"
	href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript"
	src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript"
	src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript">
	var leaveNo = '${mfOaLeave.leaveNo}';
	var from = "leave";
	var detailFlag = '${detailFlag}';
	var aloneFlag = true;
	var dataDocParm = {//页面使用上传附件组件
		relNo : leaveNo,
		docType : "messageDoc",
		docTypeName : "附件资料",
		docSplitName : "附件资料",
		query : 'query'
	};
</script>
</head>
<body class="bg-white" style="height: 100%;">
	<c:choose>
		<c:when test="${entryFlag != null and entryFlag == '1'}">
			<div class=" scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;">
		</c:when>
		<c:otherwise>
			<div class=" scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;padding:0px;">
		</c:otherwise>
	</c:choose>
		<div class="col-xs-10 col-xs-offset-1" style="margin-top: 20px;">
			<div class="arch_sort">
				<div class="row clearfix">
					<div class="col-xs-12 column info-block">
						<div id="spInfo-block">
							<div class="form-table">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>请假详情</span>
									<button class="btn btn-link pull-right formAdd-btn"
										data-toggle="collapse" data-target="#spInfo-div"></button>
								</div>
								<div id="base_div" disable="true" class="content collapse in">
									<form method="post" id="MfOaLeaveForm"
										theme="simple" name="operform"
										action="${webPath}/mfOaLeave/insertAjax">
										<dhcc:propertySeeTag property="formoaleave0001"
											mode="query" />
									</form>
								</div>
								<!--上传文件-->
								<div class="row clearfix">
									<div class="col-xs-12 col-md-offset-0 column">
										<%@include
											file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${mfOaLeave.leaveSts != null and mfOaLeave.leaveSts != '0'}">
				<div class="arch_sort">
					<div id="leaveApproveHis" class="row clearfix"
						style="display: none;">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block" class="approval-hist">
								<div class="list-table">
									<div class="title">
										<span><i class="i i-xing blockDian"></i>请假申请审批历史</span>
										<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
											<i class="i i-close-up"></i><i class="i i-open-down"></i>
										</button>
									</div>
									<div class="content margin_left_15 collapse in "
										id="spInfo-div">
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
		<div style="height: 10px;"></div>
	</div>
	<c:if test="${entryFlag != null and entryFlag == '1'}">
		<div id="approvalBtn" class="formRowCenter " style="display: block;">
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</c:if>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		showApproveHis(); 
	});
	//展示审批历史
	function showApproveHis() {
		//获得审批历史信息
		showWkfFlowVertical($("#wj-modeler2"), leaveNo, "27","leave_approval");
		$("#leaveApproveHis").show();
	}
</script>
</html>