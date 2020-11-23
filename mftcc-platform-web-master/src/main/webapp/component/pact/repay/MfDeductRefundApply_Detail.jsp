<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form method="post" id="MfDeductRefundApplyForm" theme="simple" name="operform" action="${webPath}/mfDeductRefundApply/updateAjax">
							<dhcc:bootstarpTag property="formdeductrefundapplydetail" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
	   		<div class="formRowCenter">
	   			<c:if test='${mfDeductRefundApply.appSts=="0"}'>
	   				<dhcc:thirdButton value="保存" action="保存" onclick="MfDeductRefundApplyInsert.insertDeductRefundApply('#MfDeductRefundApplyForm');"></dhcc:thirdButton>
	   			</c:if>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/pact/repay/js/MfDeductRefundApplyInsert.js?v=${cssJsVersion}"></script>
	<script type="text/javascript">
	$(function(){
		top.LoadingAnimate.stop();	
		MfDeductRefundApplyInsert.initDetail();
	});
	</script>
</html>