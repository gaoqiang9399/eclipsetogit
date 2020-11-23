<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var appId = '${mfBusApply.appId}';
			var termType = '${mfBusApply.termType}';
			$(function(){
				var idIndex = $("#busfee-div").find("thead th[name=id]").index();
				$("#busfee-div").find("thead th[name=id]").hide();
				
				var rateScaleIndex = $("#busfee-div").find("thead th[name=rateScale]").index();
				//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					inline: true, //下拉模式
					multiple: false //单选
				});
				$("#busfee-div").find("tbody tr").each(function(index){
					$thisTr = $(this);
					$thisTr.find("td").eq(idIndex).hide();
					var $rateScaleTd = $thisTr.find("td").eq(rateScaleIndex); 
					var rateScale = $rateScaleTd.html().trim();
					$rateScaleTd.html('<input value="'+rateScale+'">');
				});
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				if(termType=="1"){
					$("input[name=term]").parent().parent().prev().append("&nbsp;(个月)");
				}else{
					$("input[name=term]").parent().parent().prev().append("&nbsp;(天)");
				}
			});
			
			 //审批页面
			 function getApprovaPage(){
			 	$("#infoDiv").css("display","none"); 
			 	$("#approvalBtn").css("display","none"); 
			 	$("#approvalDiv").css("display","block"); 
			 	$("#submitBtn").css("display","block"); 
			 }
			 //返回详情页面
			 function approvalBack(){
			 	$("#infoDiv").css("display","block"); 
			 	$("#approvalBtn").css("display","block"); 
			 	$("#approvalDiv").css("display","none"); 
			 	$("#submitBtn").css("display","none");
			 }
			 
			 //审批提交
			function doSubmit(obj){
// 				var opinionType = $("select[name=opinionType]").val();
				var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
					commitProcess(webPath+"/mfOaCounttrans/discussAjax?appId="+appId+"&opinionType="+opinionType,obj,'applySP');
				}
			}
			
			
			//手动调用审批页面
			getApprovaPage();
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfBusApply/getSummary?appId=${mfBusApply.appId}&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
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
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="approvalBack();"></dhcc:thirdButton>
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