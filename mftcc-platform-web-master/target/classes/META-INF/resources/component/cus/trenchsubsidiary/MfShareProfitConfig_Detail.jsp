<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="MfShareProfitConfigForm" theme="simple" name="operform" action="${webPath}/mfShareProfitConfig/updateAjax">
							<dhcc:bootstarpTag property="formMfShareProfitConfigDetail" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	   	<script type="text/javascript" 	src="${webPath}/component/cus/trenchsubsidiary/js/MfShareProfitConfig_Detail.js"></script>
		<script type="text/javascript" >
			$(function(){
				MfShareProfitConfig_Detail.init();
			});
		</script>
	</body>
</html>