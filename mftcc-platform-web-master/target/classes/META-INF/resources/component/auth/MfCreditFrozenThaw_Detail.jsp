<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
	<script type="text/javascript">
        var cusNo = '${mfCreditFrozenThaw.cusNo}';
        var operaId = '${mfCreditFrozenThaw.operaId}';
        $(function(){
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    theme : "minimal-dark",
                    updateOnContentResize : true
                }
            });
            showWkfFlowVertical($("#wj-modeler-primary"),operaId,"50","","1");
            $("#primary-spInfo-block").show()
            $("#primary-spInfo-block").find(".list-table").show()
        });
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-8 col-md-offset-2 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title"></div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCreditFrozenThawForm" theme="simple" name="operform" action="${webPath}/mfCreditFrozenThaw/updateAjax">
							<dhcc:bootstarpTag property="formfrzoenBase" mode="query"/>
						</form>
					</div>
					<div id="primary-spInfo-block" class="approval-hist" style="display: none">
						<div class="list-table">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>审批历史</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#primary-spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
							<div class="content margin_left_15 collapse in " id="primary-spInfo-div">
								<div class="approval-process">
									<div id="modeler1" class="modeler">
										<ul id="wj-modeler-primary" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
		</div>
	   	</div>
	</body>
</html>