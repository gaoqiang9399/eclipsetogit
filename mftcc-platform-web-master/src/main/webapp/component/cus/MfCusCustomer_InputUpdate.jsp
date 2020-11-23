<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/cus/js/MfCusCustomer_InputUpdate.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript" src='${webPath}/component/include/checkOperable.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript">
		var cusType="${cusType}";
		var cusNo="${cusNo}";
		var ajaxData = '${ajaxData}';
		var cusMngNo="${cusMngNo}";
		ajaxData = JSON.parse(ajaxData);
		$(function(){
			MfCusCustomer_InputUpdate.init();
		});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">客户登记</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="cusInsertForm" theme="simple" name="operform" action="${webPath}/mfCusCustomer/insertForBusAjax">
							<dhcc:bootstarpTag property="formcommon" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
	   		<input type="hidden" id="type" value="1"></input>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="查看详情" action="查看详情" typeclass="save" onclick="MfCusCustomer_InputUpdate.toCusDetailInfo('#cusInsertForm');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="MfCusCustomer_InputUpdate.cancelInsert();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
