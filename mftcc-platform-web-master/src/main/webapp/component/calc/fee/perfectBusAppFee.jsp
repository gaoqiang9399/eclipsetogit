<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		
	</head>
	<script type="text/javascript" src='${webPath}/component/calc/fee/js/perfectBusAppFee.js'> </script>
	<script type="text/javascript">
		var appId = '${appId}';
		var fincId = '${fincId}';
		var itemNo = '${itemNo}';
		$(function(){
			perfectBusAppFee.init();
		});
	</script>
	
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
	            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				
					<form  method="post" id="busFeeForm" theme="simple" name="operform" action="${webPath}/mfBusAppFee/savePerfectBusAppFeeAjax">
						<dhcc:bootstarpTag property="formbusfee0001" mode="query"/>
					</form>
					
					</div>
				</div>
			</div>	
		
		<div class="formRowCenter">
   			<dhcc:thirdButton value="保存" action="保存" onclick="perfectBusAppFee.ajaxUpdateThis('#busFeeForm');"></dhcc:thirdButton>
   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
   		</div>
   	</div>
	</body>
</html>