<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<style>
		.bigForm_content_form .from_btn input{ margin-right:90px;}
		</style>
		<script type='text/javascript'>
	    (function($){
	        $(window).load(function(){
	            $(".bigform_content").mCustomScrollbar();
	        });
	    })(jQuery);
		</script>
	</head>
	<body class="body_bg" style="overflow:hidden; height:96%;">
		<div class="bigform_content" style="padding-top:20px;" align="center">
			<div class="bigForm_content_form content_form_new">
				<form  method="post" theme="simple" name="operform" action="">
					<dhcc:formTag property="formeval4002" mode="query" />
				</form>	
			</div>
		</div>
	</body>
</html>