<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/umeditor-dev/themes/default/_css/umeditor.css"/>
		<style type="text/css">
		 	.rows .input-box input[ type="radio"]{
				width: 3%;
	   			height: 14px;
		 	}
		 	.edui-container{
		 		margin-top:-20px;
		 	}
		</style>
	</head>	
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="OaNoticeInsert" theme="simple" name="operform" action="${webPath}/mfOaNotice/updateAjax">
						<dhcc:bootstarpTag property="formoanotice0003" mode="query" />
						<script id="noticeContent"  type="text/plain"style="width:100%;height:390px;"></script>
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="保存" action="保存" typeclass ="saveAjax"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/oa/notice/js/MfOaNoticeInsert1.js"></script>
<script type="text/javascript" src='${webPath}/UIplug/umeditor-dev/umeditor.config.js'></script>
<script type="text/javascript" src='${webPath}/UIplug/umeditor-dev/editor_api.js'></script>
<script type="text/javascript">
	$(function() {
    	OaNoticeInsert.init();  
	});	
</script>
</html>