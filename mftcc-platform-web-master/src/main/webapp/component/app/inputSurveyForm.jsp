<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>准入</title>
<script type="text/javascript" src='${webPath}/component/app/js/inputSurveyForm.js'></script>
<script type="text/javascript">
			var appId = '${appId}';
			var wkfAppId = '${wkfAppId}';
			var taskId = '${taskId}';
			var docParm = "cusNo=${mfBusApply.cusNo}&relNo=${mfBusApply.cusNo}&appId=${mfBusApply.appId}";// 查询文档信息的url的参数
			$(function(){
				survey.init();
			});
			
			//新增保存
			function insertAppAndCus(obj){
				survey.insertAppAndCus(obj);
			}
			
		</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="assureCusInsertForm" theme="simple"
						name="operform" action="${webPath}/mfAssureApply/insertSurveyAjax">
						<dhcc:bootstarpTag property="formapplyzh00001" mode="query" />
					</form>
				</div>
				<div class="row clearfix">
					<div class="col-xs-12 column" >
						<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" id="type" value="1"></input>
		<div class="formRowCenter">
			<dhcc:thirdButton value="完成调查" action="完成调查"
				onclick="insertAppAndCus('#assureCusInsertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
				onclick="myclose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
