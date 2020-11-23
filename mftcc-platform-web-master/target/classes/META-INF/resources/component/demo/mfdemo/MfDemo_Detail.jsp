<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						<form  method="post" id="update" theme="simple" name="operform" action="${webPath}/mfDemo/updateAjax">
							<dhcc:bootstarpTag property="formmfdemo0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
   			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfDemo.ajaxInsert('#update');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
	   	</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/demo/mfdemo/js/MfDemo.js"></script>
	<script type="text/javascript">
		// 接收传参等
		$(function() {
			MfDemo.init();
		});
	</script>
</html>