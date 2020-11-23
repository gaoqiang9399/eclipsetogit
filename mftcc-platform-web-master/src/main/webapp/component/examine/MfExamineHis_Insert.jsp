<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var templateList = "";
			var templateType = "";
			var pactId = '${pactId}';
			var cusNo = '${cusNo}';
			var appId = '${appId}';
			var examHisId ='${examHisId}';
			var templateId = '${templateId}';
			var docuSaveFlag = '${docuSaveFlag}';
			var docuTemplateNew = '${docuTemplate}';
			var saveUrl = "${webPath}component/examine/updateExamHis.jsp";
		</script>
		<script type="text/javascript" src='${webPath}/component/examine/js/MfExamineHis.js'> </script> 
		<script type="text/javascript">
			$(function(){
				init();
			})
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="ecamHisInsertForm" theme="simple" name="operform" action="${webPath}/mfExamineHis/insertAjax">
							<dhcc:bootstarpTag property="formexamhis001" mode="query"/>
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   		<dhcc:thirdButton value="提交" action="提交" onclick="ajaxInsertThis('#ecamHisInsertForm');"></dhcc:thirdButton>
	   		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
	<script type="text/javascript">
	$(document.body).height($(window).height());
</script>
</html>
