<%@ page language="java" import="java.util.*,net.sf.json.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信临时额度审批</title>
		<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
		<script type="text/javascript">
		$(document.body).height($(window).height());
		//vpNo 为后台配置的菜单的编号  viewPointData 为要显示菜单
		var basePath = "${webPath}";
		var index = 0;  //动态增加产品计数使用
		var creditId = "${creditId}";
		var creditType = "${creditType}";
		var cusType='${cusType}';
		var baseType='${baseType}';
		var creditId='${creditId}';
		var appId =creditId;
		var cusNo='${cusNo}';
		var relId=appId;
		var relNo=appId;
		var tempType="REPORT";//尽调报告
		$(function(){
			MfCusCreditApprove_linshi.init();
		});
		var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
	</script>
	</head>
	<body  class="overflowHidden bg-white">
			<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfCusCustomer/getById?cusNo=${cusNo}&busEntrance=6" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="creditApprovForm" theme="simple">
						<dhcc:bootstarpTag property="formcreditapprinfo0001" mode="query"/>
						<input type="hidden" name="kindNames">
						<input type="hidden" name="creditAmts">
					</form>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="MfCusCreditApprove_linshi.getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="MfCusCreditApprove_linshi.doSubmit('#creditApprovForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCusCreditApprove.approvalBack();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId} />
	<input name="activityType" id="activityType" type="hidden" value=${activityType} />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=${designateType} />
	</body>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApprove_linshi.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditTemplate.js'></script>
	<script type='text/javascript' src='dwr/engine.js'></script>  
	<script type='text/javascript' src='dwr/util.js'></script>  
	<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
	<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
</html>