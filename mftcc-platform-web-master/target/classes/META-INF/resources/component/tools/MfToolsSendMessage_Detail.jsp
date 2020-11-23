<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<script type="text/javascript">
		$(function (){
			var textreaDiv = $("textarea[name=sendMsg]").parent();
			textreaDiv.append("<span style='position:absolute;top:19%;left:76%;color:#32b5cb' class=\"divc\">微金时代</span>");
		});  
	
	</script>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<form method="post" theme="simple" name="operform" action="${webPath}/mfCusKeyMan/updateAjax">
						<dhcc:bootstarpTag property="formtools00002" mode="query" />
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" onclick=""></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
	</div>
</body>
</html>