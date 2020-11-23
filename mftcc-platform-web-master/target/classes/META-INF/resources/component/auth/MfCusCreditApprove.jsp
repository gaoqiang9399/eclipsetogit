<%@ page language="java" import="java.util.*,net.sf.json.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信审批</title>
		<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
		<script type="text/javascript" src="${webPath}/component/app/makepolicymeeting/js/WkfMakePolicyMeetingViewPoint.js"></script>
		<script type="text/javascript">
		$(document.body).height($(window).height());
		//vpNo 为后台配置的菜单的编号  viewPointData 为要显示菜单
		var basePath = "${webPath}";
		var index = 0;  //动态增加产品计数使用
		var creditApproveId = "${creditApproveId}";
		var creditType = "${creditType}";
		var mfCusPorductCreditList = eval("(" + '${json}' + ")").mfCusPorductCreditList;
		var mfSysKinds = eval("(" + '${json}' + ")").mfSysKinds;
		var cusType='${cusType}';
		var baseType='${baseType}';
		var creditAppId='${creditAppId}';
		var appId =creditAppId;
		var cusNo='${cusNo}';
		var relId=appId;
		var relNo=appId;
        var busModel = '${busModel}';
		var tempType="REPORT";//尽调报告
        var nodeNo='${nodeNo}';
        var CREDIT_END_DATE_REDUCE ='${cusCreditAddPro}';// 授信结束日自动减一天
        var processId='${processId}';
		$(function(){
			MfCusCreditApprove.init();
			//加载尽调报告模板
			//MfCusCreditTemplate.template_init();
			$("[name='approveRemark']").attr("style","height:200px");
		});
		var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
		<%-- var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
        PageMessageSend.onPageLoad(userId); 
		dwr.engine.setActiveReverseAjax(true);
	 	dwr.engine.setNotifyServerOnPageUnload(true); --%>
	</script>
	</head>
	<body  class="overflowHidden bg-white">
			<div class="container form-container">
			<c:choose>
				<c:when test="${busModel == '12' and nodeNo == 'reviewMeet'}">
					<div id="infoDiv" style="display:block;height:100%;">
				</c:when>
				<c:otherwise>
					<div id="infoDiv" style="height:100%;width:75%;float: left;">
				</c:otherwise>
			</c:choose>
            <c:if test="${baseType == '1' or baseType == '2'}">
                <iframe src="${webPath}/mfCusCreditApply/getCusCreditView?cusNo=${cusNo}&busEntrance=credit&entrance=credit&creditAppId=${creditAppId}&nodeNo=${nodeNo}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
            </c:if>
            <c:if test="${baseType == '3'}">
                <iframe src="${webPath}/mfCusCoreCompany/getCoreCompanyView?coreCompanyUid=${cusNo}&busEntrance=cus_core_company&baseType=${baseType}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
            </c:if>
            <c:if test="${baseType == '4' or baseType == 'B'}">
                <iframe src="${webPath}/mfBusTrench/getTrenchView?cusNo=${cusNo}&busEntrance=cus_trench&baseType=${baseType}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
            </c:if>
            <c:if test="${baseType == '5'}">
                <iframe src="${webPath}/mfBusAgencies/getAgenciesView?cusNo=${cusNo}&busEntrance=cus_agencies&baseType=${baseType}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
            </c:if>
            <c:if test="${baseType == '6'}">
                <iframe src="${webPath}/mfCusWarehouseOrg/getWarehouseOrgView?cusNo=${cusNo}&busEntrance=cus_warehouse_org&baseType=${baseType}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
            </c:if>
            <c:if test="${baseType == '9'}">
                <iframe src="${webPath}/mfCusAssureCompany/assureCompanyView?cusNo=${cusNo}&busEntrance=cus_assure&baseType=${baseType}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
            </c:if>
            <c:if test="${baseType == 'A'}">
                <iframe src="${webPath}/mfCusGroup/cusGroupView?groupNo=${cusNo}&busEntrance=cus_group&baseType=${baseType}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
            </c:if>
            <c:if test="${baseType == 'C'}">
                <iframe src="${webPath}/mfCusCooperativeAgency/cooperativeAgencyView?orgaNo=${cusNo}&busEntrance=cus_coopAgency&baseType=${baseType}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
            </c:if>
        </div>
			<c:choose>
				<c:when test="${busModel == '12' and nodeNo == 'reviewMeet'}">
					<div id="approvalDiv" class="scroll-content" style="display:none;">
				</c:when>
				<c:otherwise>
					<div id="approvalDiv" class="scroll-content" style="width: 25%;float: right;">
				</c:otherwise>
			</c:choose>
					<div style="text-align: center;font-size: 16px;">
						<a href="#formButton" style="padding:0px 25px 0px 25px;">授信审批</a>
						<a href="#fileButton" id="buttonFile" style="padding:0px 25px 0px 25px;display: none;">电子文档</a>
						<a href="#docButton" id="buttonDoc" style="padding:0px 25px 0px 25px;display: none;">调查资料</a>
					</div>
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn" id="formButton">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="creditApprovForm" theme="simple">
						<dhcc:bootstarpTag property="formcreditapprinfo0001" mode="query"/>
						<input type="hidden" name="kindNames">
						<input type="hidden" name="creditAmts">
					</form>
				</div>
				<%@ include file="/component/include/approval_biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<c:choose>
			<c:when test="${busModel == '12' and nodeNo == 'reviewMeet'}">
				<div id="approvalBtn" class="formRowCenter " style="display:block;">
					<dhcc:thirdButton value="审批" action="审批" onclick="MfCusCreditApprove.getApprovaPage();"></dhcc:thirdButton>
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
				</div>
				<div id="submitBtn" class="formRowCenter" style="display:none;">
					<dhcc:thirdButton value="提交" action="提交" onclick="MfCusCreditApprove.doSubmit('#creditApprovForm');"></dhcc:thirdButton>
					<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfCusCreditApprove.approvalBack();"></dhcc:thirdButton>
				</div>
			</c:when>
			<c:otherwise>
				<div id="submitBtn" class="formRowCenter">
					<dhcc:thirdButton value="提交" action="提交" onclick="MfCusCreditApprove.doSubmit('#creditApprovForm');"></dhcc:thirdButton>
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId} />
	<input name="activityType" id="activityType" type="hidden" value=${activityType} />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=${designateType} />
	</body>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApprove.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply_Insert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditAdjustApplyInsert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditTemplate.js'></script>
	<script type='text/javascript' src='dwr/engine.js'></script>  
	<script type='text/javascript' src='dwr/util.js'></script>  
	<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
	<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
    <script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js?v=${cssJsVersion}"></script>
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