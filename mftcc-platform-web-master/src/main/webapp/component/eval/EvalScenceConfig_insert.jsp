<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		 <script type="text/javascript" src='${webPath}/component/eval/js/EvalScenceConfig_comm.js'> </script>
		<style type="text/css">
			 .input-box textarea {
				line-height: 35px;
				font-size: 14px;
				position: relative;
			} 
		</style>
		<script type="text/javascript">
		var CUSTYPE = JSON.parse('${dataMap.cusTypeArray}');
		var TRADE = JSON.parse('${dataMap.wayClassItems}');
		var PROJSIZE = JSON.parse('${dataMap.projSizeItems}');
		var gradeType = JSON.parse('${dataMap.gradeType}');
		$(function(){
			initContent();
			initData();
		});
		</script>
	</head>
	<body class="overflowHidden bg-white">
	 	<div class="container form-container" >
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="evalSceInsertAndUpdForm" theme="simple" name="operform" action="${webPath}/evalScenceConfig/insertAjax">
							<dhcc:bootstarpTag property="formeval0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsert('#evalSceInsertAndUpdForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
