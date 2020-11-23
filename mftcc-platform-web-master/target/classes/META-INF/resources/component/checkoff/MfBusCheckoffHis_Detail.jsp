<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<script type="text/javascript" src="${webPath}/component/checkoff/js/MfBusCheckoffs_List.js"></script>
		<title>详情</title>
	
	</head>
	<body class="overflowHidden bg-white">

	   	<div class="container form-container">
			<div class="scroll-content">
		<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusCheckoffHisForm" theme="simple" name="operform" action="${webPath}/mfBusCheckoffHis/submitUpdateAjax">
							<dhcc:bootstarpTag property="formcheckoffhisadd" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="提交" action="提交" onclick="checkOffs.checkOffArroveSave('#MfBusCheckoffHisForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
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
			//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback:WkfApprove.opinionTypeChange
				}); 
	</script>
</html>