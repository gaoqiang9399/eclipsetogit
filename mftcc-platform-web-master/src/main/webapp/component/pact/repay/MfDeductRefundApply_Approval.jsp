<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	  	<script type="text/javascript" src="${webPath}/component/pact/repay/js/MfDeductRefundApplyInsert.js"></script>
		<script type="text/javascript">
			var cusNo = '${mfBusFincApp.cusNo}';
			var appId = '${mfDeductRefundApply.appId}';
			var pactId = '${mfDeductRefundApply.pactId}';
			var id = '${mfDeductRefundApply.id}';
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
					//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback:WkfApprove.opinionTypeChange
				}); 
			});
			
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("[name=opinionType]").val();
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess("${webPath}/mfDeductRefundApply/submitUpdateAjax?opinionType="+opinionType+"&id="+id,obj,'applySP');
				}
			}
		</script>
</head>
 
<body class="overflowHidden bg-white" style="height: 100%;">
	<div class="container form-container">
		<div id="approvalDiv" class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form" theme="simple" >
						<dhcc:bootstarpTag property="formdeductrefundapplyadd" mode="query" />
					</form>	
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value="${taskId }"/>
	<input name="activityType" id="activityType" type="hidden" value="${activityType }"/>
	<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext }"/>
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value="${designateType }"/> />
</body>
</html>