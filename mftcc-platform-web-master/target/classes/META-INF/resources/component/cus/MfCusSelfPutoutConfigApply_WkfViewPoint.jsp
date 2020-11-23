<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var id='${id}';
			$(function(){
				//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback:WkfApprove.opinionTypeChange
				});
				
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced:{
						updateOnContentResize:true
					}
				});

			});
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
				//没有选择审批意见默认同意
				if(opinionType == undefined || opinionType == ''){
					opinionType = "1";
				}
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
					commitProcess("${webPath}/mfCusSelfPutoutConfigApply/submitUpdateAjax?id="+id+"&appNo="+id+"&opinionType="+opinionType,obj,'applySP');
				}
				
			}
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="approvalDiv" class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="edit-form">
						<dhcc:bootstarpTag property="formcommon" mode="query" />
					</form>	
				</div>
			</div>
		</div>
		<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="approvalBack();"></dhcc:thirdButton>
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