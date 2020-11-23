<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<!-- 中汇鑫德-收费 -->

<html>
<head>
<title>收费</title>
<script type="text/javascript" src='${webPath}/component/app/js/guaranteeApply_feeForReceFinc.js'></script>
<script type="text/javascript">
	var cusNo = '${mfBusApply.cusNo}';
	var appId = '${appId}';
	var pactId = '${mfBusApply.pactId}';
	var fincMainId = '${fincMainId}';
	var feePower = '3';//费用权限字段 3：收'
	$(function() {
		//只展示无需产品号
		bindVouTypeByKindNo($("input[name=vouType]"), '');
		
		guaranteeApply_feeForReceFinc.init();
		
	});
</script>
</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="operform" name="operform" method="post" theme="simple" action="${webPath}/guaranteeApply/receFincFeeSubmitAjax">
						<dhcc:bootstarpTag property="formappzhlf0004" mode="query" />
					</form>
				</div>
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>账款列表</span>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#accountList">
							<i class="i i-close-up"></i>
							<i class="i i-open-down"></i>
						</button>
					</div>
					<div id="accountList" class="content collapse in" aria-expanded="true">
						<dhcc:tableTag property="tablereceFincApprovalBaseList" paginate="mfBusReceTransferList" head="true"></dhcc:tableTag>
					</div>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>

		<div class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="guaranteeApply_feeForReceFinc.submitForm('#operform');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>

</body>
</html>
