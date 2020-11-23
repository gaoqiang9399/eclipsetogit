<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-8 col-md-offset-2">
					<div class="bootstarpTag">
						<div class="form-title">应收账款争议审批历史表</div>
						<form  method="post" theme="simple" name="operform" action="">
							<dhcc:bootstarpTag property="formdispapprohis0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
	   	</div>
	</body>
</html>