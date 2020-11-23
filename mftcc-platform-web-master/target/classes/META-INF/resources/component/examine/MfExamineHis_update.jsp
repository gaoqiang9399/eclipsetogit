<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link rel="stylesheet" href="${webPath}/component/report/css/MfReport1.css" />
		<script type="text/javascript">
			var templateList = "";
			var examHisId = "";
			var templateType = '${templateType}';
			var pactId = '${pactId}';
			var cusNo = '${mfBusPact.cusNo}';
			var appId = '${mfBusPact.appId}';
			var examDetailFlag = '${examDetailFlag}';
			var saveUrl = "${webPath}component/examine/updateExamHis.jsp";
		</script>
		<script type="text/javascript" src='${webPath}/component/examine/js/MfExamineHis.js'> </script> 
		<script type="text/javascript">
			$(function(){
				init_update();
			})
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="ecamInfoForm" theme="simple" name="operform" action="${webPath}/mfExamineHis/insertAjax">
							<dhcc:bootstarpTag property="formexamFormTemp" mode="query"/>
						</form>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
				<div id="save">
					<dhcc:thirdButton value="提交" action="提交" onclick="submitAjax('#ecamInfoForm');"></dhcc:thirdButton>
					<dhcc:thirdButton value="保存" action="保存" onclick="saveFormTemplateAjax('#ecamInfoForm');"></dhcc:thirdButton>
		   			<dhcc:thirdButton value="上一步" action="上一步" onclick="openPrevForm();"></dhcc:thirdButton>
		   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
				</div>
				<div id="submit">
					<dhcc:thirdButton value="提交" action="提交" onclick="submitAjax('#ecamInfoForm');"></dhcc:thirdButton>
		   			<dhcc:thirdButton value="上一步" action="上一步" onclick="openPrevForm();"></dhcc:thirdButton>
		   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
				</div>
   			</div>
		</div>
	</body>
</html>
