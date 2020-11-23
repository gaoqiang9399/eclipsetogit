<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/app/js/inputCreditInquiry.js"></script>
	
	<script type="text/javascript">	
		var cusNo='${mfBusApply.cusNo}';
		var appId = '${appId}';
		var nodeName = '${nodeName}';

		$(function(){
			
			inputCreditInquiry.init();
			//只展示无需产品号
			bindVouTypeByKindNo($("input[name=vouType]"), '');
		});
		function commit(obj){
			inputCreditInquiry.docommit(obj);
		}

	</script>

<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="updateForApplyForm" theme="simple" name="operform" action="${webPath}/mfLoanApplyActionAjax/updateCreditInquiry">
							<dhcc:bootstarpTag property="formapply0001" mode="query" /> 
						</form>
					</div>
			 	 <%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick ="commit('#updateForApplyForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
