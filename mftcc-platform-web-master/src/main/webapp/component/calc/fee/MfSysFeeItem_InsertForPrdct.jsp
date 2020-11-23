<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/calc/fee/js/MfSysFeeItemForPrdct.js'> </script>
	</head>
	<script type="text/javascript">
		$(function(){
			MfSysFeeItemForPrdct.init();
		});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
					<div class="col-md-8 col-md-offset-2 column margin_top_20">
						<div class="bootstarpTag">
	            			<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="feeItemInsertForm" theme="simple" name="operform" action="${webPath}/mfSysFeeItem/insertForPrdctAjax">
								<dhcc:bootstarpTag property="formfeeitem0002" mode="query"/>
							</form>	
						</div>
					</div>
			</div>	
		
			<div class="formRowCenter">
				 <dhcc:thirdButton value="保存" action="保存" onclick="MfSysFeeItemForPrdct.ajaxInsert('#feeItemInsertForm','insert');"></dhcc:thirdButton> 
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
