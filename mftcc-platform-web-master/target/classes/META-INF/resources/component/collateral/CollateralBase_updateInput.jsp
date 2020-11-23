<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript" src="${webPath}/component/collateral/js/CollateralBase_updateInput.js"></script>
	<script type="text/javascript">
		$(function(){
			CollateralUpdate.init();
		});
		function updatetCollateralBase(obj){
			CollateralUpdate.updatetCollateralBase(obj);
		}
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
	            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				
					<form  method="post" id="pledgeBaseInfoUpdate" theme="simple" name="operform" action="${webPath}/mfBusCollateralRel/updatePledgeInfoAjax">
						<dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
					</form>
					<input type="hidden" name = "isQuote"/>
					<input type="hidden" name = "entrFlag"/>
				</div>
			</div>	
		</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="updatetCollateralBase('#pledgeBaseInfoUpdate');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="CollateralUpdate.closeMyOnly();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
