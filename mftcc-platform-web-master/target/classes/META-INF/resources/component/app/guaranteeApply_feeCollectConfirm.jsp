<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>收费</title>
<script type="text/javascript" src='${webPath}/component/app/js/guaranteeApply_fee.js'></script>
<script type="text/javascript" src='${webPath}/component/app/js/guaranteeApply_feeCollectConfirm.js'></script>
<script type="text/javascript">
	var cusNo = '${mfBusApply.cusNo}';
	var appId = '${appId}';
	var pactId = '${mfBusApply.pactId}';
	var feePower = '1';//改
	$(function() {
		//只展示无需产品号
		bindVouTypeByKindNo($("input[name=vouType]"), '');
		
		guaranteeApply_fee.init();
		guaranteeApply_feeCollectConfirm.init();
		
	});
</script>
</head>
<body class="overflowHidden bg-white">

	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  id="operform" name="operform" method="post" theme="simple" action="${webPath}/guaranteeApply/feeCollectConfirmSubmitAjax">
						<dhcc:bootstarpTag property="formappzhlf0004" mode="query" />
					</form>
				</div>
				<div class="bigform_content col_content">
					<!-- <div class="title"><h5 >检查指标子项</h5>
					</div> -->
					<div class="title"><h5 >财务收费</h5>
					</div>
					<div id="depositOutList" class="table_content">
						<dhcc:tableTag paginate="mfBusFincAppList" property="tableDepositOutList" head="true" />
					</div>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>

		<div class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="guaranteeApply_fee.submitForm('#operform');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
		</div>
	</div>

</body>
</html>
