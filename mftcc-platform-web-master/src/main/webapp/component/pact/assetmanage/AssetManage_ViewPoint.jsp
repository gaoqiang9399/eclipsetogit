<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>审批</title>
		<script type="text/javascript">
			var assetId = '${mfAssetManage.assetId}';
			var taskId = '${taskId}';
			var nodeNo = '${nodeNo}';
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
					multiple: false //单选
				});				
			});
			 
			//审批提交
			function doSubmit(obj){
 				var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					
					if( typeof(nodeNo) != "undefined" && nodeNo == "204017664254"){
						commitProcess(webPath+"/mfAssetManage/updateLawForSubmit?assetId="+assetId+"&appNo="+taskId+"&opinionType="+opinionType,obj,'SP');
					}else{
						commitProcess(webPath+"/mfAssetManage/updateForSubmit?assetId="+assetId+"&appNo="+taskId+"&opinionType="+opinionType,obj,'SP');	
					}
					
				}
			}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">人力需求表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusWhitenameForm" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formapplyapproval" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#MfCusWhitenameForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
	   		</div>	
		</div>
		
		<input name="taskId" id="taskId" type="hidden" value=${taskId}/>
		<input name="activityType" id="activityType" type="hidden" value=${activityType} />
		<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
		<input name="transition" type="hidden" />
		<input name="nextUser" type="hidden" />
		<input name="designateType" type="hidden" value=${designateType} />
	</body>
</html>


