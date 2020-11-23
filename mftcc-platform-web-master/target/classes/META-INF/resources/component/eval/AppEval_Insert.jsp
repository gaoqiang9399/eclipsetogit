<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body>
		<div class="bigform_content">
			<div class="bigForm_content_form">
			<form  method="post" theme="simple" name="operform" action="${webPath}/appEval/insert">
				<dhcc:formTag property="formeval1003" mode="query"/>
				<div class="from_btn">
	    			<dhcc:thirdButton value="发起" action="发起" commit="true"></dhcc:thirdButton>
	    			<dhcc:thirdButton value="ajax发起" action="ajax发起" onclick="ajaxInsert(this.form)"></dhcc:thirdButton>
	    		</div>
			</form>	
			</div>
		</div>
	</body>
</html>
