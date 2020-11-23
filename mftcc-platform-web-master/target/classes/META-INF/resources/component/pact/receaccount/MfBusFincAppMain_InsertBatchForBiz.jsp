<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var cusNo='${mfBusPact.cusNo}';
			var appId='${mfBusPact.appId}';
			var pactId='${mfBusPact.pactId}';
			var pactNo='${mfBusPact.pactNo}';
			var fincMainId='${fincMainId}';
			var wkfAppId='${wkfAppId}';
			var confirmId='${confirmId}';
			var ajaxData = '${ajaxData}';
    		ajaxData = JSON.parse(ajaxData);
    		var processId ='${mfBusPact.fincProcessId}'; 
            var kindNo = "${kindNo}";
            var nodeNo='${nodeNo}';
		</script>
	</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-10 col-md-offset-1 column margin_top_20">
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="batchFincAppInsertForm" theme="simple" name="cms_form" action="${webPath}/mfBusFincAppMain/insertForBatchAjax">
						<dhcc:bootstarpTag property="formfincappbatchbase" mode="query"/>
					</form>
				</div>

				<div class="list-table">
					<div class="title">
						<span class="formName"> <i class="i i-xing blockDian"></i>借据明细</span>

						<button title="新增" onclick="MfBusFincAppMain_InsertBatchForBiz.fincAppInput();return false;" class="btn btn-link formAdd-btn">
							<i class="i i-jia3"></i>
						</button>
						<button data-target="#fincAppList" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">
							<i class="i i-close-up"></i><i class="i i-open-down"></i>
						</button>
					</div>
					<div id="fincAppList">
						<dhcc:tableTag property="tablefincAppBatchListBase" paginate="fincAppBatchList" head="true"></dhcc:tableTag>
					</div>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusFincAppMain_InsertBatchForBiz.ajaxInsert('#batchFincAppInsertForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusFincAppMain_InsertBatchForBiz.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
<script type="text/javascript">

	$(function() {
        MfBusFincAppMain_InsertBatchForBiz.init();
	});
</script>	
</html>
