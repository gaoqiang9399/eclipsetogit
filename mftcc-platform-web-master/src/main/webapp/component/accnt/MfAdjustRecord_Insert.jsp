<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body>
		<div class="bigform_content">
		<div class="content_table">
			<form method="post" theme="simple" name="operform" action="<%=webPath %>/MfAdjustRecord/insertAjax">
				<dhcc:bigFormTag property="formaccntadjust0002" mode="query"/>
				<div class="formRow">
	    			<dhcc:thirdButton value="ajax提交" action="ajax提交" onclick="ajaxInsert(this.form,insertCallBack)"></dhcc:thirdButton>
	    		</div>
			</form>
		</div>
		</div>
	<script type="text/javascript">
		function insertCallBack(){
			var transferId = $("input[name='transferId']").val();
			//window.parent.cloesBigForm('MfAccntTransferAction_getById.action?transferId='+transferId);
			$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.$("#iframepage").attr('src',webPath + '/mfAccntTransfer/getById?transferId='+transferId);
	    	//$(top.window.document).find("#bigFormShow").find("#bigFormShowiframe").remove();
	    	myclose();
		};
	</script>
	</body>
</html>
