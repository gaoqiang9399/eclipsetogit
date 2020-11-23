<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/vouafter/js/MfVouAfterTrack_InitBusInfo.js"></script>
		<script type="text/javascript" >
            $(function(){
                MfVouAfterTrack_InitBusInfo.init();
            });
		</script>
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
						<!-- <div class="form-title">工程担保保后跟踪</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfVouAfterTrackForm" theme="simple" name="operform" action="${webPath}/mfVouAfterTrack/insertAjax">
							<dhcc:bootstarpTag property="formvouafterinit" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
		</div>
	</body>
</html>
