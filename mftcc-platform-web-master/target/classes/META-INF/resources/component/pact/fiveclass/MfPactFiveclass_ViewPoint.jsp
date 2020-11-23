<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	<script type="text/javascript">
		var fiveclassId = '${fiveclassId}';
		$(function(){
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					updateOnContentResize:true
// 				}
// 			});
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
			var opinionType = $("[name=opinionType]").val();
			var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				commitProcess(webPath+"/mfPactFiveclass/submitUpdate?opinionType="+opinionType+"&appNo="+fiveclassId+"&fiveclassId="+fiveclassId,obj,'fiveSP');
			}
		}
	</script>
</head>
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfBusPact/getById?pactId=${mfPactFiveclass.pactId }&operable=&fiveFlag=fiveFlag&fiveclassId=${fiveclassId}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formfiveclassapp0001" mode="query" />
					</form>	
				</div>
			</div>
			<div class="row clearfix" style="padding:10px 10px;line-height: 3.125em;">
				<div class="col-md-10 col-md-offset-1 column">
					<div class="row clearfix padding_top_15 padding_left_40">
						<div class="col-md-12 column">
							<div class="col-md-6 column">
								<span>项目名称：</span>
								<span class="margin_top_15">${mfBusPact.appName}</span>
							</div>
							<div class="col-md-6 column">
								<span>合同编号：</span>
								<span class="margin_top_15">${mfBusPact.pactNo}</span>
							</div>
					   </div>	
					   <div class="col-md-12 column">
					   		<div class="col-md-6 column">
								<span>客户名称：</span>
								<span class="margin_top_15">${mfCusCustomer.cusName}</span>
							</div>
					   		<div class="col-md-6 column">
								<span>合同金额(元)：</span>
								<span class="margin_top_15">${mfBusPact.fincAmt}</span>
							</div>
					   </div>
					   <div class="col-md-12 column">
					   		<div class="col-md-6 column">
								<span>产品名称：</span>
								<span class="margin_top_15">${mfBusPact.kindName}</span>
							</div>
					   		<div class="col-md-6 column">
								<span>逾期情况(天)：</span>
								<span class="margin_top_15">
									<c:if test="${empty mfPactFiveclass.overDate  || mfPactFiveclass.overDate == 0 }">未逾期</c:if>
									<c:if test="${!empty mfPactFiveclass.overDate  && mfPactFiveclass.overDate != 0 }">${mfPactFiveclass.overDate}</c:if>
								</span>
							</div>
					   </div>
					   <div class="col-md-12 column">
					   		<div class="col-md-6 column">
								<span>还款方式：</span>
								<span class="margin_top_15">
									${repayTypeStr}
								</span>
							</div>
					   		<div class="col-md-6 column">
								<span>担保方式：</span>
								<span class="margin_top_15">
									${vouTypeStr}
								</span>
							</div>
					   </div>
					   <div class="col-md-12 column">
					   		<div class="col-md-6 column">
								<span>当前五级分类：</span>
								<span class="margin_top_15">
									<span class="business-span">
										${fiveclassStr}
									</span> 
								</span>
							</div>
							<div class="col-md-6 column">
								<span>系统初分：</span>
								<span class="margin_top_15">
									<span class="business-span">
										${systemFiveclassStr}
									</span>
								</span>
							</div>
					   </div>
					</div>
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
	<input name="transition" type="hidden" value="${transition}"/>
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value="${designateType }" />
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>