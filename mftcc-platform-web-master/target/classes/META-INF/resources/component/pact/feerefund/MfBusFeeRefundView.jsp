<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var feeId = '${feeId}';
			var scNo = '${scNo}';
			var query = '${query}';
            var aloneFlag = true;
            var dataDocParm={
                relNo:feeId,
                docType:scNo,
                docTypeName:"调查资料",
                docSplitName:"调查资料",
                query:query,
            };
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
				var opinionType = $("[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess(webPath+"/mfBusFeeRefund/submitUpdateAjax?opinionType="+opinionType+"&feeId="+feeId+"&appNo="+feeId,obj,'feeRefundSP');
				}
			}
		</script>
</head>
 
<body class="overflowHidden bg-white" style="height: 100%;">
	<div class="container form-container">
		<div id="approvalDiv" class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form"  >
						<dhcc:bootstarpTag property="formFeeRefund" mode="query" />
					</form>	
				</div>
				<div class="row clearfix">
					<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
				</div>
			</div>
		</div>
		<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value="${taskId }" />
</body>
</html>