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
		var changeId = '${changeId}';
		var applySts = '${mfCusInfoChange.applySts}';
		var query = "${query}";
        var cusNo = '${mfCusInfoChange.cusNo}';
		var aloneFlag = true;
		var dataDocParm = {//页面使用上传附件组件
			relNo : changeId,
			docType : "messageDoc",
			docTypeName : "附件资料",
			docSplitName : "附件资料",
			query : query
		};
        var docParm = "&cusNo="+cusNo+"&changeId="+changeId;//查询文档信息的url的参数
</script>
</head>
<body class="bg-white" style="height: 100%;">
	<div class="scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;">
		<div class="col-xs-10 col-xs-offset-1" style="margin-top:20px;">
			<div class="arch_sort" style="border: 0px;">
				<div class="dynamic-block" title="客户信息变更" name="MfCusInfoChangeAction" data-sort="14" data-tablename="mf_risk_level_adjust">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>客户信息变更申请</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfCusInfoChangeAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div disable="true" class="content collapse in" id="MfCusInfoChangeAction" style="margin-top: 10px;">
							<form method="post" id="MfCusInfoChangeActionForm" theme="simple" name="operform" action="${webPath}/mfCusInfoChange/updateAjaxByOne">
								${htmlStrCorp }
							</form>
						</div>
						<div style="display: table" class="col-md-12 col-md-offset-0 margin_top_10" id="test">
							<div class="row clearfix">
								<div class="col-xs-12 column">
									<div class="list-table-replan">
										<div class="content margin_left_15 collapse in" id="tablechangeInfo0001">
											<dhcc:tableTag property="tablechangeInfo0001" paginate="tablechangeInfo0001" head="true"></dhcc:tableTag>
										</div>
									</div>
								</div>
							</div>
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
			<c:if test = "${mfCusInfoChange.applySts != 0}">
			<div class="arch_sort">
				<div id="cusInfoChangeApproveHis" class="row clearfix" style="display: none;">
					<div class="col-xs-12 column info-block">
						<div id="spInfo-block">
							<div class="form-table">
							   <div class="title">
									 <span><i class="i i-xing blockDian"></i>客户信息变更审批历史</span>
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
			<c:if test = "${mfCusInfoChange.applySts == 0}">
				<dhcc:thirdButton value="提交申请" action="提交申请" onclick="MfCusInfoChange_Detail.ajaxSave()"></dhcc:thirdButton>
			</c:if>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</c:if>
</div>
<script type="text/javascript" src="${webPath}/component/cus/infochange/js/MfCusInfoChange_Detail.js"></script>
<script type="text/javascript">
		$(function(){
			MfCusInfoChange_Detail.init();
		});	
</script>
</body>
</html>