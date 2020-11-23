<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/component/collateral/js/WkfReceTranViewPoint.js"></script>
		<script type="text/javascript">
			var receTranAppId='${dataMap.receTranAppId}';
			var appId='${appId}';
			$(function(){
				WkfReceTranViewPoint.init();
			});
		</script>
</head>
 
<body  class="overflowHidden bg-white">
		<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfBusCollateralRel/getApproveCollateralInfo?cusNo=${dataMap.cusNo}&appId=${dataMap.appId}&pactId=${dataMap.pactId}&receTranAppId=${dataMap.receTranAppId }&showable=showable" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="receTranApprovForm">
						<dhcc:bootstarpTag property="formrecetranappro0001" mode="query" />
					</form>		
				</div>
			</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="WkfReceTranViewPoint.getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="WkfReceTranViewPoint.doSubmit('#receTranApprovForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="WkfReceTranViewPoint.approvalBack();"></dhcc:thirdButton>
		</div>
	</div>	
	<input name="taskId" id="taskId" type="hidden" value=${taskId} />
	<input name="activityType" id="activityType" type="hidden" value=${activityType} />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=${designateType} />
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>