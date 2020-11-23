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
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bigform_content col_content">
						<div id="fincBusAppFeeList" class="table_content">
							<dhcc:tableTag paginate="mfBusAppFeeList" property="tablebusFincfeeList" head="true" />
						</div>
						<div class="col-sm-offset-5 col-sm-5" style="margin-top:20px;">
							<button id="fincFeePlanTrial_button" type="button" class="btn btn-default" value="计算"
								style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;"
								onclick="MfBusAppFee_FincBusAppFee.ajaxInsertThis();" >计算</button>
						</div>
					</div>
					<div id="feePlanList" class="table_content" style="width:100%;margin-left:-15px;">
					
					</div>
				</div>
			</div>	
		
   	</div>
	</body>
</html>
