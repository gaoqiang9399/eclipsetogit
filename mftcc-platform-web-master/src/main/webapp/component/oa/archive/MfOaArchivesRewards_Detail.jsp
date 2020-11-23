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
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="reward_form" theme="simple" name="operform" action="${webPath}/mfOaArchivesRewards/insertAjax">
							<dhcc:bootstarpTag property="formarchivereward0001" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" typeclass="reward_insertAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
	<script type="text/javascript" 	src="${webPath}/component/oa/archive/js/MfOaDrchivesReward_insert.js"></script>
	<script type="text/javascript">
		$(function(){
			OaArchivesReward.init();
		})
		$(document.body).height($(window).height());
	</script>
</html>