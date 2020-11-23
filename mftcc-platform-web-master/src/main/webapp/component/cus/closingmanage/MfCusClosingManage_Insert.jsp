<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusClosingManage.js"></script>
		<script type="text/javascript" src="${webPath}/component/nmd/js/NmdWay.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			$(function() {
				$(".scroll-content").mCustomScrollbar({//滚动条的生成
					advanced: {
						theme: "minimal-dark",
						updateOnContentResize: true
					}
				});
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
						<!-- <div class="form-title">法院信息 mf_cus_court_info</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCusClosingManageForm" theme="simple" name="operform" action="${webPath}/mfCusClosingManage/insertAjax">
							<dhcc:bootstarpTag property="formcusClosingManageBase" mode="query"/>
						</form>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>要件上传</span>
								<iframe id="mfBusCompensatoryUploadIframe" frameborder=0 width="100%" src="${webPath}/component/compensatory/mfBusClosingUpload.jsp?query=${query }&closingId=${id}"></iframe>
							</div>
						</div>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="MfCusClosingManage.insertAjax('#MfCusClosingManageForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
		</div>
	</body>
</html>
