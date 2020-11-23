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
			var appId = '${mfBusApply.appId}';
			var termType = '${mfBusApply.termType}';
			$(function(){
				//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					searchOn: false, //启用搜索
					inline: true, //下拉模式
					multiple: false //单选
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
// 				if(termType=="1"){
// 					$("input[name=term]").parent().parent().prev().append("&nbsp;(个月)");
// 				}else{
// 					$("input[name=term]").parent().parent().prev().append("&nbsp;(天)");
// 				}
				$("#approvalDiv").css("display","block"); 
				$("#submitBtn").css("display","block"); 
			});
			 //审批提交
			function doSubmit(obj){
// 				var opinionType = $("select[name=opinionType]").val();
				var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					alert();
					//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
					commitProcess(webPath+"/mfOaCounttrans/discussAjax?appNo="+appId+"&opinionType="+opinionType,obj,'applySP');
				}
				
			}
			 
			 function updatePactEndDate(){
				var beginDate =  $("input[name=beginDate]").val();
				var term = $("input[name=term]").val();
				var termType = $("select[name=termType]").val();
				var intTerm = parseInt(term);
				if(1==termType){ //融资期限类型为月 
					var d = new Date(beginDate);
					d.setMonth(d.getMonth()+intTerm);
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
					$("input[name=endDate]").val(str);
				}else{ //融资期限类型为日 
					var d = new Date(beginDate);
				 	d.setDate(d.getDate()+intTerm);
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
					$("input[name=endDate]").val(str);
				}
			};
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formchangemoney0004" mode="query" />
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