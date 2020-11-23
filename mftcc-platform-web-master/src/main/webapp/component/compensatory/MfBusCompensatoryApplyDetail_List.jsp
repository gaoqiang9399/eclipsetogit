<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src='${webPath}/component/compensatory/js/MfBusCompensatoryApply_Detail.js'></script>
		<script type="text/javascript">
			var compensatoryId = '${compensatoryId}';
			$(function(){
				MfBusCompensatoryApplyDetail.init();
			});
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">代偿申请表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="mfBusCompensatoryApplyForm" theme="simple" name="operform">
							<dhcc:bootstarpTag property="formcompensatoryConfirm0001" mode="query"/>
						</form>
					</div>
					<!-- 要件信息 -->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>要件上传</span>
								<iframe id="mfBusCompensatoryUploadIframe" frameborder=0 width="100%" src="${webPath}/component/compensatory/mfBusCompensatoryUpload.jsp?query=${query }&compensatoryId=${compensatoryId}"></iframe>
							</div>
						</div>
					</div>
					<%--<div class="bigform_content col_content">--%>
						<%--<!-- <div class="title"><h5 >检查指标子项</h5>--%>
						<%--</div> -->--%>
						<%--<div id="mfBusCompensatoryApplyList" class="table_content">--%>
							<%--<dhcc:tableTag paginate="mfBusCompensatoryApplyDetailList" property="tablecompensatoryDetail0002" head="true" />--%>
						<%--</div>--%>
					<%--</div>--%>
				</div>
	   		</div>	
	   	</div>
	</body>
</html>