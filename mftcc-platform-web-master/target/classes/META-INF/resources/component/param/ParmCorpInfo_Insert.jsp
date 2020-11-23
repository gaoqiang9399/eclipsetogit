<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
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
		<script type="text/javascript">
			function pageDataCallback(data) {
				$("input[name=belongOrgName]").val(data.brName);
				$("input[name=belongOrgNo]").val(data.brNo);
			}
		</script>
	</head>
	<body class="body_bg" style="overflow:hidden; height:96%;">
		<div class="bigform_content" style="padding-top:20px;" align="center">
			<div class="bigForm_content_form content_form_new">
			<form method="post" theme="simple" name="operform" action="${webPath}/parmCorpInfo/insert">
				<dhcc:formTag property="formparam0004" mode="query"/>
				<div class="formRow">
	    			<dhcc:thirdButton value="保存" action="ParmCorpInfo004" commit="true"></dhcc:thirdButton>
     		</div>
			</form>	
			</div>
		</div>
	</body>
</html>
