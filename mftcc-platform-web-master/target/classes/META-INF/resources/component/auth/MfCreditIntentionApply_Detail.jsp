<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>

	</head>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCreditIntentionApply_Insert.js'></script>
	<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCredit.js'></script>
	<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
	<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
	<script type="text/javascript">
		var cusNo = '${mfCreditIntentionApply.cusNo}';
		var creditId = '${mfCreditIntentionApply.creditId}';
		var oaApproveFlag ='${oaApproveFlag}';
		var linshiFlag ='${linshiFlag}';
        $(function(){
            mfCreditIntentionApplyInsert.init();
            if(oaApproveFlag!='1'){
                $(".list-table").hide();
			}else{
                $(".list-table").show();
			}
			if(linshiFlag==1){
                showWkfFlowVertical($("#wj-modeler-primary"),creditId,"50","","1");
                $("#primary-spInfo-block").show()
                $("#primary-spInfo-block").find(".list-table").show()
			}else{
                $("#primary-spInfo-block").hide()
                $("#primary-spInfo-block").find(".list-table").hide()
			}
        });
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
					<div class="bootstarpTag">
						<!-- <div class="form-title"></div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfCreditIntentionApplyForm" theme="simple" name="operform" action="${webPath}/mfCreditIntentionApply/updateAjax">
							<dhcc:bootstarpTag property="formCreditIntentionBase" mode="query"/>
						</form>
					</div>
					<div class="list-table">
					<div class="title">
						<span>oa审批意见</span>
					</div>
					<div id="receList">
						<dhcc:tableTag property="tablecreditoadetails" paginate="mfCreditOaApproveDetailsList" head="true"></dhcc:tableTag>
					</div>
					</div>
						<div id="primary-spInfo-block" class="approval-hist" style="display: none">
							<div class="list-table">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>临时额度审批历史</span>
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

			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="mfCreditIntentionApplyInsert.detailReturn();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	</body>
</html>