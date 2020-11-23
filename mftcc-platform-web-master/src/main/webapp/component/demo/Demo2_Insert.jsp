<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
<body onselectstart="return false">
    <div class="layout">
		<div class='cell' style='top:0px; left:0px; width:406px; height: 251px; background-color:#EBEBEB' data-handle=".handle">
			<div class="info">
				<form  method="post" theme="simple" name="operform" action="${webPath}/demo2/insert">
						<dhcc:propertySeeTag property="formdemo0008" mode="query"/>
				</form>
			</div>
		</div>
    </div>
</body>
</html>