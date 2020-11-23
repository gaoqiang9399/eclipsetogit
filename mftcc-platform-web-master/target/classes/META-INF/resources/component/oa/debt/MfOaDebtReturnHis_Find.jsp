<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<script type="text/javascript" src="${webPath}/component/include/idCheck.js?v=${cssJsVersion}"></script>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：本页信息不可修改。</div>
				<form method="post" id="OaDebtDetail" theme="simple" name="operform" action="${webPath}/mfOaDebt/updateAjax">
					<dhcc:bootstarpTag property="formreturndebt00003" mode="query" />
				</form>
			</div>
		</div>
	</div>
	</div>
	<%-- <div class="formRowCenter">
		<dhcc:thirdButton value="还款" action="提交" typeclass ="updateAjax"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
	</div> --%>
</body>
</html>