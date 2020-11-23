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
		var agenciesUid = '${agenciesUid}';
		var applySts = '${mfBusAgencies.applySts}';
</script>
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
				<div class="dynamic-block" title="机构审批申请" name="MfOaDimissionAction" data-sort="14" data-tablename="mf_oa_dimission">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>机构审批申请</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfOaDimissionAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div disable="true" class="content collapse in" id="MfOaDimissionAction" style="margin-top: 10px;">
							<form method="post" id="MfOaDimissionActionForm" theme="simple" name="operform" action="${webPath}/jKInstitutions/insertAjax">
								<dhcc:propertySeeTag property="formdimission0001" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<c:if test = "${mfOaDimission.applySts != 0}">
			<div class="arch_sort">
				<div id="dimissionApproveHis" class="row clearfix" style="display: none;">
					<div class="col-xs-12 column info-block">
						<div id="spInfo-block">
							<div class="form-table">
							   <div class="title">
									 <span><i class="i i-xing blockDian"></i>机构申请审批历史</span>
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
			<c:if test = "${mfOaDimission.applySts == 0}">
				<dhcc:thirdButton value="提交申请" action="提交申请" onclick="ajaxSave()"></dhcc:thirdButton>
			</c:if>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="backList();"></dhcc:thirdButton>
		</div>
	</c:if>
</div>
</body>
<script type="text/javascript">
		$(function(){
			$(".scroll-content").mCustomScrollbar({
				advanced:{
					//滚动条根据内容实时变化
					updateOnContentResize:true
				}
			});
			if(applySts != 0){
				showApproveHis();
			}
		});	
		function backList(){
			myclose_click();
		}
		//展示审批历史
	    function showApproveHis(){
			//获得审批历史信息
			showWkfFlowVertical($("#wj-modeler2"),dimissionId,"23","dimission_approval");
			$("#dimissionApproveHis").show();
		}
		function ajaxSave(){
			$.ajax({
				url : webPath+"/jKInstitutions/submitProcessAjax",
				data : {
					agenciesUid:agenciesUid
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
</script>
</html>