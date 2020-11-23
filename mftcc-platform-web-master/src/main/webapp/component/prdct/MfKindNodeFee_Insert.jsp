<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfKindNodeFeeForm" theme="simple" name="operform" action="<%=webPath %>/mfKindNodeFee/insertAjax">
							<dhcc:bootstarpTag property="formnodefee0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfKindNodeFeeInsert.insertNodeFee('#MfKindNodeFeeForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindNodeFeeInsert.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
// 	var ajaxData = '${ajaxData}';
// 	ajaxData = JSON.parse(ajaxData);
	$(function() {
		MfKindNodeFeeInsert.init();
	});
</script>
</html>
