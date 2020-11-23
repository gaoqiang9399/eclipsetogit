<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>尽职调查</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/app/js/confirmedLoan.js"></script>
	
	<script type="text/javascript">	
		var docParm = "cusNo=${mfBusPact.cusNo}&relNo=${mfBusPact.appId}&scNo=${scNo}";// 查询文档信息的url的参数
		var cusNo='${cusNo}';
		var appId = '${mfBusFincApp.appId}';
		var pactId = '${mfBusFincApp.pactId}';
		var fincId = '${mfBusFincApp.fincId}';
		var kindNo = '${mfBusPact.kindNo}';

		$(function() {
			confirmedLoan.appId = "${appId}"; 
			confirmedLoan.init();	
			bindVouTypeByKindNo($("input[name=vouType]"), '');
		});	
		
	</script>

<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="confirmedLoan" theme="simple" name="operform" action="${webPath}/mfLoanApplyActionAjax/insertFundConfirmInfo">
							<dhcc:bootstarpTag property="formfincapp0004" mode="query" />
						<form >
					</div>
					<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="提交" action="提交" typeclass ="insertAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
