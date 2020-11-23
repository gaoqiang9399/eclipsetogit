<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="body_bg" style="overflow:hidden; height:96%;">
		<div class="bigform_content" style="padding-top:20px;" align="center">
			<div class="bigForm_content_form content_form_new" style="padding-left:20px">
				<form  method="post" theme="simple" name="operform" action="${webPath}/evalSceneBizRel/update">
					<dhcc:formTag property="formeval4002" mode="query"/>
					<div class="formRow">
		    			<dhcc:thirdButton value="保存" action="EvalSceneBizRel002" commit="true"></dhcc:thirdButton>
	     			</div>
				</form>	
			</div>
		</div>
	</body>
</html>
