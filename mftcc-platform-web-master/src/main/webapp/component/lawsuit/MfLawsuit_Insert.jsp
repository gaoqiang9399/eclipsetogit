<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="lawsuit" theme="simple" name="operform" action="${webPath}/mfLawsuit/insertAjax">
							<dhcc:bootstarpTag property="formlawsuit0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass ="insertAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
			</div>
   		</div>
	</body>
<script type="text/javascript" src="${webPath}/component/lawsuit/js/MfLawsuit_insert.js"></script>
<script type="text/javascript">
    var query = '${query}'; 
	$(function() {
    	lawsuitInsert.init();  
	});	
</script>
</html>
