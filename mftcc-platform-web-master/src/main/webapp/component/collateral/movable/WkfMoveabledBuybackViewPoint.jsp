<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/component/collateral/js/WkfMoveabledBuybackViewPoint.js"></script>
		<script type="text/javascript">
			var appId='${appId}';
			var backId='${backId}';
			$(function(){
				WkfMoveabledBuybackViewPoint.init();
			});
		</script>
</head>
 
<body  class="overFlowHidden bg-white">
		<div class="container form-container">
		<div id="infoDiv" style="display:block;height:calc(100% - 60px);">
			<iframe src="${webPath}/mfBusCollateralRel/getApproveCollateralInfo?cusNo=${dataMap.cusNo"/>&appId=<s:property value="dataMap.appId"/>&pactId=<s:property value="dataMap.pactId}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="moveBuybackApprovForm">
						<dhcc:bootstarpTag property="formbuybackappro0001" mode="query" />
					</form>		
				</div>
				<div id="goodsDetailListdiv" class="bigform_content col_content" style="display:none">
						<div class="title"><h5 >货物明细</h5></div>
							<div id="goodsDetailList" class="table_content padding_0">
								<dhcc:tableTag paginate="goodsDetailList" property="tabledlpledgebaseinfobill0006" head="true" />
							</div> 
					</div>
			</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="WkfMoveabledBuybackViewPoint.getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="WkfMoveabledBuybackViewPoint.doSubmit('#moveBuybackApprovForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="WkfMoveabledBuybackViewPoint.approvalBack();"></dhcc:thirdButton>
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