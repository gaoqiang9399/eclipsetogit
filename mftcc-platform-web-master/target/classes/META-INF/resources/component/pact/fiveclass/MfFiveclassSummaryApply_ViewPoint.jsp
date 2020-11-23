<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	<link rel="stylesheet" href="${webPath}/component/pact/css/MfPactFiveclass_commonForBatch.css" />
	<script type="text/javascript" src='${webPath}/component/pact/js/MfPactFiveclass_commonForBatch.js'></script>
	<script type="text/javascript" src='${webPath}/component/pact/js/MfFiveclassSummaryApply_ViewPoint.js'></script>
	<script type="text/javascript">
		var applyId = '${applyId}';
		var ajaxData = ${ajaxData};
		$(function(){
			MfFiveclassSummaryApply_ViewPoint.init();
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
				var fiveclassList = [];
				$("#tab").find("tr").each(function(){
				    var tdArr = $(this).children();
				    var fiveclassId = tdArr.find("input[name='fiveclassId']").val();
				    var appName = tdArr.eq(0).text();
				    var nowChangeReason = tdArr.find("input[name='nowChangeReason']").val();
				    var changeReason = tdArr.find("input[name='changeReason']").val();
				    var nowFiveclass = tdArr.find("select[name='nowFiveclass']").val();
				    var fincShowId = tdArr.eq(1).text();
				    var putoutAmt = tdArr.eq(2).text();
				    var lastFiveclass = tdArr.eq(3).text();
				    var systemFiveclass = tdArr.eq(4).text();
					 var fiveclass = new Object();
						fiveclass.fiveclassId = fiveclassId;
						fiveclass.nowChangeReason = nowChangeReason;
						fiveclass.nowFiveclass = nowFiveclass;
						fiveclass.appName = appName;
						fiveclass.fincShowId = fincShowId;
						fiveclass.putoutAmt = putoutAmt;
						fiveclass.lastFiveclass = lastFiveclass;
						fiveclass.systemFiveclass = systemFiveclass;
						fiveclass.changeReason = changeReason;
						fiveclassList.push(fiveclass);

				 });
				 var jsonString = JSON.stringify(fiveclassList);
				 $("input[name=fiveclassContext]").val(jsonString);
				commitProcess(webPath+"/mfFiveclassSummaryApply/submitUpdate?opinionType="+opinionType+"&appNo="+applyId+"&applyId="+applyId,obj,'fiveSP');
			}
		}
	</script>
</head>
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="approvalDiv" class="scroll-content" ">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">五级分类认定汇总申请表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfFiveclassSummaryApplyForm" theme="simple" name="operform" action="${webPath}/mfFiveclassSummaryApply/doSubmit">
							<dhcc:bootstarpTag property="formfiveclassApplyViewPoint" mode="query"/>
						</form>
					</div>
					<div id="plan_content" class="table_content" style="width:100%;margin-left:-15px;">
				
					</div>
				</div>
	</div>
	<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#MfFiveclassSummaryApplyForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
	</div>
	<input name="taskId" id="taskId" type="hidden" value="${taskId}" />
	<input name="activityType" id="activityType" type="hidden" value="${activityType}" />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext}" />
	<input name="transition" type="hidden" value="${transition}"/>
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value="${designateType }" />
	</div>
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>