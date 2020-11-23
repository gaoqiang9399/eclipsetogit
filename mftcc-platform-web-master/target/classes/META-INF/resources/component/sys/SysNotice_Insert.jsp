<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<style type="text/css">
			textarea[name=content] {
				height: 240px;
			}
		</style>
		<script type="text/javascript">
			function ajaxInsertAndPush(obj,callback){
				obj.action = webPath+"/sysNotice/insertAndPushAjax";
<%-- 				obj.action = "${webPath}/sysNotice/Ajax_insertAndPushAjax"; --%>
				ajaxInsert(obj,callback);
			}
			function closeShowDialogView(){
				window.top.showDialogView("","");
/* 				window.top.showDialogView("","iframepage"); */
			}
		</script>
	</head>
	<body style="overflow: hidden;">
		<div class="bigform_content">
			<form method="post" theme="simple" name="operform" action="${webPath}/sysNotice/insertAjax">
				<dhcc:bigFormTag property="formsys8002" mode="query"/>
				<div class="formRow" style="margin: 0 auto;text-align: center;">
	    			<%-- <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsert(this.form,closeShowDialogView)" ></dhcc:thirdButton> --%>
	    			<dhcc:thirdButton value="推送" action="推送" onclick="ajaxInsertAndPush(this.form,closeShowDialogView)" ></dhcc:thirdButton>
	    		</div>
			</form>	
		</div>
	</body>
</html>
