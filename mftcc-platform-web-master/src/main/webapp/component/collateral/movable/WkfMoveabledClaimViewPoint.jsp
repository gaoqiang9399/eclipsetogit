<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
JSONObject otMap = null;
if(request.getAttribute("otMap")!=null){
	otMap = JSONObject.fromObject(request.getAttribute("otMap"));
}
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <style type="text/css">
  .footer_loader{
  z-index:1;
  }
  </style>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/component/collateral/js/WkfMoveabledClaimViewPoint.js"></script>
		<script type="text/javascript">
			var claimId='${claimId}';
			var appId='${appId}';
			var busPleId='${mfMoveableClaimGoodsApply.busPleId}';
			var nodeNo = '${nodeNo}';
			var isQuote="0";
			$(function(){
				WkfMoveabledClaimViewPoint.init();
			});
		</script>
</head>
 
<body  class="overFlowHidden bg-white">
		<div class="container form-container">
		<div id="infoDiv" style="display:block;height:calc(100% - 60px);">
			<iframe src="${webPath}/mfBusCollateralRel/getApproveCollateralInfo?cusNo=${cusNo}&appId=${appId}&pactId=${pactId}"  marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="moveClaimApprovForm">
						<dhcc:bootstarpTag property="formclaimappro0001" mode="query" />
					</form>		
				</div>
				<div id="goodsDetailListdiv" class="bigform_content col_content">
					<div class="title"><h5 >货物明细</h5></div>
						<div id="goodsDetailList" class="table_content">
							<dhcc:tableTag paginate="pledgeBaseInfoBillList" property="tablepledgeBillApproveList" head="true" />
						</div> 
						
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%>
			</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="WkfMoveabledClaimViewPoint.getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="WkfMoveabledClaimViewPoint.doSubmit('#moveClaimApprovForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="WkfMoveabledClaimViewPoint.approvalBack();"></dhcc:thirdButton>
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