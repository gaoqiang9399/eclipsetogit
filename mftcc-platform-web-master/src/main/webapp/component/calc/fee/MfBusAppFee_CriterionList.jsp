<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>	
		<script type="text/javascript" src="${webPath}/component/calc/fee/js/MfBusAppFee_CriterionList.js"></script>
		<script type="text/javascript" >
			var appId = '${appId}';
			$(function(){
				MfBusAppFee_CriterionList.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bigform_content col_content">
						<div id="feeCriterionList" class="table_content">
							<dhcc:thirdTableTag property="tablefeeCriterionList" paginate="mfBusAppFeeList" head="true"></dhcc:thirdTableTag>
						</div>
					</div>
				</div>
			</div>	
		
   	</div>
	</body>
</html>
