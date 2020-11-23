<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>详情</title>
	<script type="text/javascript" src='${webPath}/component/recourse/js/MfBusRecourseConfirm_Detail.js'></script>
	<script>
		$(function(){
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    theme : "minimal-dark",
                    updateOnContentResize : true
                }
            });
		});
	</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">追偿确认表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfBusRecourseConfirmForm" theme="simple" name="operform">
							<dhcc:bootstarpTag property="formrecourseConfirm0001" mode="query"/>
						<form>
					</div>
					<div class="list-table" id="mfBusCompensatoryDocListDiv">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>资料清单</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusCompensatoryDocList">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="mfBusCompensatoryDocList" name="mfBusCompensatoryDocList">
							<dhcc:tableTag property="tablecompensatorydocregister" paginate="mfBusCompensatoryDocList" head="true"></dhcc:tableTag>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>追偿资料</span>
								<iframe id="mfBusRecourseApplyIframe" frameborder=0 width="100%" src="${webPath}/component/compensatory/mfBusRecourseUpload.jsp?query=${query }&recourseId=${recourseId}"></iframe>
							</div>
						</div>
					</div>
				</div>
	   		</div>
	   	</div>
	</body>
</html>