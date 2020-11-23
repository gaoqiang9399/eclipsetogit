<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<body class="overflowAuto body_bg">
		<div class="bigform_content">
		<div class="content_table">
			<form method="post" theme="simple" name="operform" action="<%=webPath %>/mfAccntTransferAction/insertAjax">
				<dhcc:bigFormTag property="formaccnttrans0002" mode="query"/>
				<div class="formRowCenter">
 	    			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsert(this.form,insertCallBack)"></dhcc:thirdButton>
	    		</div>
			</form>	
			</div>
		</div>
		<script type="text/javascript">
		function insertCallBack(data){
			//alert(data);
			var pactId = $(".content_table").find("input[name=pactId]").val();
	    	$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.$("#iframepage").attr('src','MfAccntTransferAction_getListPage.action?pactId='+pactId);
	    	//$(top.window.document).find("#bigFormShow").find("#bigFormShowiframe").remove();
	    	myclose();

		};
		
		</script>
	</body>
</html>
