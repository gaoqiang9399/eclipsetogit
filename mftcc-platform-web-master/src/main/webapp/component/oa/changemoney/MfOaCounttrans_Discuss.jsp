<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/oa/changemoney/ChangeMoney_Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/factor/WebRoot/component/include/closePopUpBox.js"></script>
		<script type="text/javascript">
			var badgeNo = '${mfOaCounttrans.badgeNo}';
			$(function(){
				//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					searchOn: false, //启用搜索
					inline: true, //下拉模式
					multiple: false //单选
				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				$("#approvalDiv").css("display","block"); 
				$("#submitBtn").css("display","block"); 
			});
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
					commitProcess(webPath+"/mfOaCounttrans/discussAjax?appNo="+badgeNo+"&opinionType="+opinionType,obj,'applySP');
				}
				
			}
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formchangemoney0003" mode="query" />
					</form>	
				</div>
			</div>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<!--<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="approvalBack();"></dhcc:thirdButton>-->
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value="${taskId}" />
	<input name="activityType" id="activityType" type="hidden" value="${activityType}" />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext}" />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value="${designateType}" />
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
		
</script>
</html>