<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form method="post" id="MfPreRepayApplyForm" theme="simple" name="operform" action="${webPath}/mfPreRepayApply/updateAjax">
							<dhcc:bootstarpTag property="formprerepayapplydetail" mode="query"/>
						</form>
					</div>
					<div id="spInfo-block" class="approval-hist">
						<div class="list-table">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>审批历史</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
							<div class="content margin_left_15 collapse in " id="spInfo-div">
								<div class="approval-process">
									<div id="modeler1" class="modeler">
										<ul id="wj-modeler" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
	   		</div>
	   		<div class="formRowCenter">
	   			<c:if test='${mfPreRepayApply.preRepayAppSts=="0"}'>
	   				<dhcc:thirdButton value="保存" action="保存" onclick="MfPreRepayApplyInsert.insertPreRepayApply('#MfPreRepayApplyForm');"></dhcc:thirdButton>
	   			</c:if>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
   		</div>
	</body>
	<script type="text/javascript" src="${webPath}/component/pact/repay/js/MfPreRepayApplyInsert.js?v=${cssJsVersion}"></script>
	<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
	<script type="text/javascript">
		var preRepayAppId = '${mfPreRepayApply.preRepayAppId}';
	$(function(){
		MfPreRepayApplyInsert.initDetail(preRepayAppId);
	});
	</script>
</html>