<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		 <script type="text/javascript" src='${webPath}/component/eval/js/MfEvalIndexSub.js'> </script>
		 <script type="text/javascript">
			$(function(){
				MfEvalIndexSub.init();
			});
		</script>
	</head>
	<body class="body_bg overFlowHidden">
	 	<div class="mf_content" >
			<div class="content-box">
			<p class="tip"><span>说明：</span>带*号的为必填项信息，请填写完整。</p>
				<div class="tab-content">
					<form  method="post" id="evalSubUpdForm" theme="simple" name="operform" action="${webPath}/mfEvalIndexSub/updateAjax">
						<dhcc:bootstarpTag property="formevalsub0002" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick="MfEvalIndexSub.ajaxInsertOrUpdate('#evalSubUpdForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="MfEvalIndexSub.close();"></dhcc:thirdButton>
		</div>
</body>
</html>