<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript" src='${webPath}/component/pact/protect/js/WkfAssetViewApprove.js'> </script>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var ajaxCollClassData='${ajaxCollClassData}';
			var ajaxOverduePactData='${ajaxOverduePactData}';
			var cusData='${cusData}';
			ajaxCollClassData=JSON.parse(ajaxCollClassData);
			ajaxOverduePactData=JSON.parse(ajaxOverduePactData);
			cusData=JSON.parse(cusData); 
			var protectId = '${protectId}';
			var basePath="${webPath}";
			var cusNo="${cusNo}";
			var cusName='${mfAssetProtectRecord.cusName}';
		 	var appId="${appId}";
		 	var pactId="${pactId}";
		 	var aloneFlag = true;
			var dataDocParm={
				relNo:protectId,
				docType:"assetProtectDoc",
				docTypeName:"资产资料",
				docSplitName:"资产资料",
				query:'query'
			};
			$(function(){
				WkfAssetViewApprove.init();
			});
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfBusPact/getById?pactId=${pactId }&protectId=${protectId }&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="assetApproveForm">
						<dhcc:bootstarpTag property="formassetapprove" mode="query" />
					</form>
					<form method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="${webPath}/mfAssetProtectRecord/insert">
						<dhcc:bootstarpTag property="formassetbaseinfo" mode="query"/>
					</form>
				</div>
			</div>
			<div class="col-xs-10 col-md-offset-1 column" >
	 				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	 		</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="WkfAssetViewApprove.getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="WkfAssetViewApprove.doSubmit('#assetApproveForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="WkfAssetViewApprove.approvalBack();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId} />
	<input name="activityType" id="activityType" type="hidden" value=${activityType} />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value="${designateType }"/>
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>