<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body>
		<div class="bigform_content">
			<form method="post" theme="simple" name="operform" action="${webPath}/wkfModeRole/insert">
				<dhcc:bigFormTag property="formwkf4012" mode="query"/>
				<div class="formRow">
	    			<dhcc:thirdButton value="提交" action="提交" commit="true"></dhcc:thirdButton>
	    			<dhcc:thirdButton value="ajax提交" action="ajax提交" onclick="ajaxInsert(this.form)"></dhcc:thirdButton>
	    		</div>
			</form>	
		</div>
	</body>
</html>
