<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
	<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<script>
	
</script>

<body style="background: #FFFFFF">

	<div class="container" style="text-align: left; background: #FFFFFF">
		<form class="form-horizontal" role="form" action="${webPath}/mfToolsSendMessage/input">
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
				<lable> 短信记录  </lable>
					<input type="submit" value="新短信">
				</div>
			</div>
		</form>
	</div>

	<div class="block-info" id="sendMessage-div">
		<div>
			<div class="list-table">
				<div class="content margin_left_15">
					<dhcc:tableTag paginate="mfToolsSendMessageList"
						property="tabletools0001" head="true" />
				</div>
			</div>
		</div>
</body>
</html>