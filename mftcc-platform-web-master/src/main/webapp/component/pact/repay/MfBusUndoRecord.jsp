<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>还款撤销信息</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusUndoRecordApplyForm" theme="simple" name="operform" action="${webPath}/mfBusUndoRecordApply/insertAjax.action">
							<dhcc:bootstarpTag property="formundorecordapply" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="还款撤销" action="还款撤销" onclick="MfBusUndoRecord.insertUndoRecordApply('#MfBusUndoRecordApplyForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/pact/repay/js/MfBusUndoRecord.js"></script>
	<script type="text/javascript">
	    var repayId='${repayId}';//还款历史Id
	    var fincId='${fincId}';
		$(function(){
			MfBusUndoRecord.init();
		});
	</script>
</html>
