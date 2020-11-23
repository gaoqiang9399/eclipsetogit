<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<script type="text/javascript" src="${webPath}/component/pact/feerefund/js/MfBusFeeCollect_Insert.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/feerefund/js/MfBusFeeCollect_List.js"></script>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript">
        $(function(){
            MfBusFeeCollect_Insert.init();
            MfBusFeeCollect_Insert.selectPact();
        });
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusFeeCollectForm" theme="simple" name="operform" action="${webPath}/mfBusFeeCollect/insertAjax">
							<dhcc:bootstarpTag property="formfeeCollectBase" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusFeeCollect_Insert.ajaxSave('#MfBusFeeCollectForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="MfBusFeeCollect_Insert.myclose_reload();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
