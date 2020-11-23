<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		 <script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		 <script type="text/javascript" src='${webPath}/component/eval/js/MfEvalIndexSub.js'> </script>
		 <script type="text/javascript">
			$(function(){
				MfEvalIndexSub.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
	 	<div class="mf_content container form-container" >
			<div class="content-box scroll-content">
				<div class="tab-content col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="evalSubInsertForm" theme="simple" name="operform" action="${webPath}/mfEvalIndexSub/insertSubAjax">
							<dhcc:bootstarpTag property="formevalsub0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="MfEvalIndexSub.ajaxInsertOrUpdate('#evalSubInsertForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
</body>
</html>
