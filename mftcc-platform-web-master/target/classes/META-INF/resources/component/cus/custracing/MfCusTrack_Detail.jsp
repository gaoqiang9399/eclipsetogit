<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<style type="text/css">
		.content_form{
			border:none;
			padding-top: 10px;
		}
		</style>
	</head>
	<body>
		<div class="bigform_content">
			<div class="content_form">
			<form  method="post" theme="simple" name="operform" action="${webPath}/mfCusTrack/updateAjax">
				<dhcc:formTag property="formcustracing0002" mode="query"/>
				<div class="formRow">
	    			<dhcc:thirdButton value="ajax提交" action="ajax提交" onclick="ajaxInsert(this.form,insertCallBack)"></dhcc:thirdButton>
	    		</div>
			</form>	
			</div>
		</div>
	<script type="text/javascript">
	$(function(){
		$("select[name=trackType]").find("option[value='9']").remove();
		//var cusNo = '${cusId}';
		//$("input[name=cusNo]").val(cusNo);
	});
		function insertCallBack(data){
			//alert(data);
			var cusNo = $("input[name=cusNo]").val();
	    	$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.$("#iframepage").attr('src','${webPath}/mfCusTrack/getListPage?cusId='+cusNo);
	    	//$(top.window.document).find("#bigFormShow").find("#bigFormShowiframe").remove();
	    	myclose();

		};
		
		</script>	
	</body>
</html>
