<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>

<!DOCTYPE html>
<html  style="height:100% !important;">
<head>
<title>详情</title>
<script type="text/javascript">
		var id = '${id}';
		var webPath = '${webPath}';
</script>
</head>
<body class="overflowHidden bg-white">   
		<div class="container form-container">
			<div id="infoDiv" style="height:100%;width:75%;float: left;">
				<iframe src="${webPath}/mfBankStatementRegister/getById?id=${id}&nodeNo=${nodeNo}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
			</div>
			<div id="approvalDiv" class="scroll-content" style="width: 25%;float: right;">
				<div class="col-md-10 col-md-offset-1 column margin_top_20" >
					<div class="bootstarpTag fourColumn" id="formButton">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form id="mfBankStatementRegisterForm">
							<dhcc:bootstarpTag property="formBankStatementRegisterApprove" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div id="submitBtn" class="formRowCenter">
				<dhcc:thirdButton value="提交" action="提交" onclick="WkfBankStatementRegisterViewPoint.doSubmitAjax('#mfBankStatementRegisterForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
			</div>
		</div>
		<input name="taskId" id="taskId" type="hidden" value="${taskId}" />
		<input name="activityType" id="activityType" type="hidden" value="${activityType}" />
		<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext}" />
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value="${designateType}" />
</body>
<script type="text/javascript" src="${webPath}/component/vouafter/js/WkfBankStatementRegisterViewPoint.js"></script>
<script type="text/javascript">
		$(function(){
			WkfBankStatementRegisterViewPoint.init();
		});	
</script>
<style>
	.block-left {
		padding-top: 10px;
		padding-right: 5px;
		padding-left: 2px;
	}
	.col-md-offset-1 {
		margin-left: 3%;
	}
	.bootstarpTag .table>tbody>tr>td, .bootstarpTag .table>tbody>tr>th, .bootstarpTag .table>tfoot>tr>td, .bootstarpTag .table>tfoot>tr>th, .bootstarpTag .table>thead>tr>td, .bootstarpTag .table>thead>tr>th {
		padding: 2px 2px;
		line-height: 1.42857143;
		vertical-align: middle;
		border-top: 1px solid #ddd;
	}
	.bootstarpTag.fourColumn .tdlable {
		width: 105px;
		min-width: 105px;
	}
	.mCustomScrollBox {
		background-color: #fff;
	}
	.mCSB_draggerRail {
		background: #fff;
		width: 9px;
		border-radius: 3px;
	}
	.upload-div .filelist {
		padding: 0px 0px 10px 30px;
	}
	.form-container {
		padding-bottom: 0px;
	}
	.scroll-content {
		border-top: 10px solid #F0F5FB;
		border-right: 9.5px solid #F0F5FB;
	}
</style>
</html>