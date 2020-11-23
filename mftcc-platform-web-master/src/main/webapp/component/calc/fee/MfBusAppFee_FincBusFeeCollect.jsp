<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src='${webPath}/component/calc/fee/js/MfBusAppFee_FincBusAppFee.js'> </script>
		<script type="text/javascript" >
			var appId = '${appId}';
			var fincId = '${fincId}';
			$(function(){
				MfBusAppFee_FincBusAppFee.init();
				MfBusAppFee_FincBusAppFee.initFincBusFeeCollect();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
					</div>
					<div class="bigform_content col_content">
						<div id="fincBusAppFeeList" class="table_content">
							<dhcc:tableTag paginate="mfBusAppFeeList" property="tablebusFincFeeCollect" head="true" />
						</div>
					</div>
				</div>
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="title">
						<span>费用计划</span>
					</div>
					<div class="bigform_content col_content">
						<div id="fincBusAppFeeList" class="table_content">
							<dhcc:tableTag property="tablebusFeePlanList" paginate="mfBusFeePlanList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</div>
			</div>	
		
		<div class="formRowCenter">
   			<dhcc:thirdButton value="保存" action="保存" onclick="MfBusAppFee_FincBusAppFee.ajaxInsertFincBusFeeCollect();"></dhcc:thirdButton>
   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
   		</div>
   	</div>
	</body>
</html>
