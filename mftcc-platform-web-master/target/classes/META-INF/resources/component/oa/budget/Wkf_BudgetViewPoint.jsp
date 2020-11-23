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
			var budgetId = '${budgetId}';
			var nodeId = '${dataMap.nodeId}';
// 			var termType = '${mfBusApply.termType}';
			$(function(){
				if('manager'!=nodeId){
					$('#edit-form table tbody').find('tr:eq(3)').addClass('hidden');
				}
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
			});
			 
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("select[name=opinionType]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess(webPath+"/mfOaBudgetMst/commitProcessAjax?opinionType"+opinionType+"&budgetId="+ budgetId, obj,'sp');
				}
			}
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="approvalDiv" class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formbudget0001" mode="query" />
						<!-- 预算列表 -->
						<div class="feeInfo showOrHide">
							<div class="list-table margin_0">
							<div class="title">
								<span>
								<i class="i i-xing blockDian"></i>
								预算列表</span>
								<!-- <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#busfee-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button> -->
							</div>
							<%-- <span>费用标准</span> --%>
							<div class="content_table collapse in" id="budgetamt-div">
								<dhcc:tableTag property="tablebudget0002" paginate="mfOaBudgetDetailList" head="true"></dhcc:tableTag>
							</div>
							</div>
						</div>
					</form>	
				</div>
			</div>
		</div>
		<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId}/>
	<input name="activityType" id="activityType" type="hidden" value=${activityType}/>
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext}/>
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=${designateType}/>
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>