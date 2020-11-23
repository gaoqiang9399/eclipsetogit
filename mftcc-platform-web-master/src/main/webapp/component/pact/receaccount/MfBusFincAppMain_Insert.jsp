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
            var busModel='${mfBusPact.busModel}';
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
			<div class="col-md-12 column margin_top_20" style="padding:0px 50px;">
				<div class="bootstarpTag fourColumn">
					<div class="form-title">融资申请</div>
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="receFincAppInsertForm" theme="simple" name="cms_form" action="${webPath}/mfBusFincAppMain/insertAjax">
						<dhcc:bootstarpTag property="formrecefincappbase" mode="query"/>
					</form>
				</div>
				<div class="list-table">
					<div class="title">
						<span>账款列表</span>
					</div>
					<div id="receList">
						<dhcc:tableTag property="tablereceFincAppBaseList" paginate="mfBusReceTransferList" head="true"></dhcc:tableTag>
					</div>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusFincAppMain_Insert.ajaxInsert('#receFincAppInsertForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfBusFincAppMain_Insert.cancelBack();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusFincAppMain_Insert.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
<script type="text/javascript">

	$(function() {
        MfBusFincAppMain_Insert.init();
	});
</script>	
</html>
