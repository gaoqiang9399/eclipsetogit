<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>补货入库审批</title>
		<script type="text/javascript" src="${webPath}/component/collateral/js/WkfKeepInfoViewPoint.js"></script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/KeepInfo_instock_Insert.js"></script>
		<script type="text/javascript">
			var keepId='${keepInfo.keepId}';
			var collateralId='${keepInfo.collateralId}';
			var cusNo='${cusNo}';
			var appId='${appId}';
			var fincId='${fincId}';
			var calcIntstFlag='${calcIntstFlag}';
			var nodeNo = '${nodeNo}';
			$(function(){
				WkfKeepInfoViewPoint.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">   
		<div class="container form-container">
			<div id="infoDiv" style="display:block;height:100%;">
				<c:if test="${aloneFlag=='1'}">
					<iframe src="${webPath}/pledgeBaseInfo/getById?pledgeNo=${keepInfo.collateralId}&busEntrance=keep_approve" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
				</c:if>
				<c:if test="${aloneFlag!='1'}">
					<iframe src="${webPath}/mfBusCollateralRel/getCollateralInfo?fincId=${fincId}&appId=${appId}&cusNo=${cusNo}&relId=${appId}&busEntrance=finc&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
				</c:if>
			</div>
			<div id="approvalDiv" class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="keepInfoApproveForm" theme="simple" name="operform" action="${webPath}/keepInfo/submitUpdateAjax">
							<dhcc:bootstarpTag property="formKeepInfoApprove" mode="query"/>
						</form>
					</div>
					<div id="goodsDetailListdiv" class="bigform_content col_content" style="display:none">
						<div class="title"><h5>货物明细</h5></div>
						<div id="goodsDetailList" class="table_content padding_0">
							<dhcc:tableTag paginate="goodsDetailList" property="tabledlpledgebaseinfobill0005" head="true" />
						</div>
					</div>
					 <%@ include file="/component/include/biz_node_plugins.jsp"%> 
				</div>
	   		</div>
			<div id="approvalBtn" class="formRowCenter " style="display:block;">
				<dhcc:thirdButton value="审批" action="审批" onclick="WkfKeepInfoViewPoint.getApprovaPage();"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
			</div>
			<div id="submitBtn" class="formRowCenter" style="display:none;">
				<dhcc:thirdButton value="提交" action="提交" onclick="WkfKeepInfoViewPoint.doSubmitAjax('#keepInfoApproveForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="WkfKeepInfoViewPoint.approvalBack();"></dhcc:thirdButton>
			</div>
		</div>
		<input name="taskId" id="taskId" type="hidden" value=${taskId} />
		<input name="activityType" id="activityType" type="hidden" value=${activityType} />
		<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value=${designateType} />
	</body>
</html>
