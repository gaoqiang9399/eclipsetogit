<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/model/js/MfTemplateTagBase_Insert.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript">
		var ajaxData = '${ajaxData}';
		ajaxData = JSON.parse(ajaxData);
        var bookData = '${bookData}';
        bookData = JSON.parse(bookData);
		$(function(){
			MfTemplateTagBase_Insert.init();
		});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="mf_content container form-container" style="height: 600px;">
			<div class="content-box scroll-content">
				<div class="tab-content col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfTemplateTagBase/insertAjax">
							<dhcc:bootstarpTag property="formtemplatetagebase0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="MfTemplateTagBase_Insert.insertTemplateTagBase('#operform');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
