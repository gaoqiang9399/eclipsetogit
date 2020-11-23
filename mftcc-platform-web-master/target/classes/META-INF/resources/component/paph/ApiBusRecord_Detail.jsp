<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/viewer.min.css" />
<script type="text/javascript" src="${webPath}/component/frontview/viewer/js/viewer-jquery.min.js"></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">外部接口请求业务数据存储表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="ApiBusRecordForm" theme="simple" name="operform" action="${webPath}/apiBusRecord/updateAjax">
							<dhcc:bootstarpTag property="formpaph0001" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	</body>
	<script type="text/javascript">
$(function(){
	$("#viewer").viewer();
})
</script>
<script src="${webPath}/component/paph/js/ApiBusRecord_Detail.js"></script>
<script type="text/javascript">
var webPath="${webPath}";
$(function(){
	VwBannerManageDetail.init();
})
</script>
</html>