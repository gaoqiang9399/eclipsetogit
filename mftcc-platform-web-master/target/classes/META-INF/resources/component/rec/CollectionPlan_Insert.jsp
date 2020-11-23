<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<!-- <script type="text/javascript">
		var kindList = (eval("(" + '${json}' + ")")).kindList;
		$(function(){
				init("insert");
		});
		</script> -->
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-title">新增催收方案</div>
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="collectionInsertForm" theme="simple" name="operform" action="${webPath}/collectionPlan/insertAjax">
							<dhcc:bootstarpTag property="formdlcollectionplan0002" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="collectionPlanSave('#collectionInsertForm','insert');"></dhcc:thirdButton>
				 <dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="cancelInsert();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
