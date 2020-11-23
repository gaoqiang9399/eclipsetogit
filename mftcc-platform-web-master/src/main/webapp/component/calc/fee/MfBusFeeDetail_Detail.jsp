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
						<div class="form-title">查看详情</div>
					<form  method="post" theme="simple" name="operform" action="">
						<dhcc:formTag property="formbusfeedetail0001" mode="query" />
					</form>
				</div>
				</div>	
			</div>
		</div>
	</body>
</html>