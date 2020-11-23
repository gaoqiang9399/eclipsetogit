<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		    
			<script type="text/javascript"  src='${webPath}/component/cus/intangibleassets/js/MfCusIntangibleAssets.js?v=${cssJsVersion}'> </script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="cusIntangibleAssetsInsert" theme="simple" name="operform" action="${webPath}/mfCusIntangibleAssets/insertAjax">
							<dhcc:bootstarpTag property="cusIntangibleAssetsBase" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="MfCusIntangibleAssets.saveCusIntangibleAssets('#cusIntangibleAssetsInsert');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
