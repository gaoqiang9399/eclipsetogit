<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		 <script type="text/javascript" src="${webPath}/component/collateral/js/MfReceivablesDisputedAppDetail.js"></script>
	</head>
	<body class="bg-white" >
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" style="background-color: white;">
				<div class="bootstarpTag">
<!-- 					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div> -->
					<form  method="post" id="updateForm" theme="simple" name="operform" action="">
						<dhcc:bootstarpTag property="formrecedisputed0003" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<!--  <div class="formRowCenter">
			<dhcc:thirdButton value="确定" action="保存" onclick="MfReceivablesDisputedAppDetail.myclose(this);"></dhcc:thirdButton>
			<dhcc:thirdButton value="取消" action="关闭" typeclass="cancel" onclick="MfReceivablesDisputedAppDetail.myclose(this);"></dhcc:thirdButton>
		</div> -->
	</div>
</body>
	<script>
		$(function(){
			MfReceivablesDisputedAppDetail.init();
		});

	</script>
</html>