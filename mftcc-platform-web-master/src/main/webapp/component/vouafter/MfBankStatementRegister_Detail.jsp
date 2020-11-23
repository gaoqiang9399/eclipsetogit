<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript">
            var id = '${id}';
            var refundId = '${refundId}';
            var relNo = '${relNo}';// 要件业务编号
            var nodeNo = '${nodNo}';//
            var refundQueryFile = '${refundQueryFile}';//
            var temBizNo1 = '${refundId}';
            var temParm1 = '${temParm1}';
            var applySts = '${mfBankStatementRegister.applySts}';//
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-xs-10 col-xs-offset-1" style="margin-top:20px;">
					<div class="arch_sort" style="border: 0px;">
						<div class="dynamic-block" title="银行结算单" name="MfBankStatementRegisterAction" data-sort="14" data-tablename="mf_vou_after_track">
							<div class="list-table">
								<div class="title">
									<span class="formName"><i class="i i-xing blockDian"></i>银行结算单</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfBankStatementRegisterAction">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div disable="true" class="content collapse in" id="MfBankStatementRegisterAction" style="margin-top: 10px;">
									<form method="post" id="MfBankStatementRegisterForm" theme="simple" name="operform" action="${webPath}/mfBankStatementRegister/updateAjax">
										<dhcc:propertySeeTag property="formBankStatementRegisterdetail" mode="query" />
									</form>
								</div>
							</div>
						</div>
					</div>
					<c:if test = "${mfBankStatementRegister.applySts != 0}">
						<div class="arch_sort">
							<div id="BankStatementRegisterApproveHis" class="row clearfix" style="display: none;">
								<div class="col-xs-12 column info-block">
									<div id="spInfo-block" class="approval-hist">
										<div class="list-table">
											<div class="title">
												<span><i class="i i-xing blockDian"></i>银行结算单审批历史</span>
												<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
													<i class="i i-close-up"></i><i class="i i-open-down"></i>
												</button>
											</div>
											<div class="content margin_left_15 collapse in " id="spInfo-div">
												<div class="approval-process">
													<div id="modeler1" class="modeler">
														<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
														</ul>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
						</div>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<c:if test = "${from != null and from == '1'}">
					<div id="approvalBtn" class="formRowCenter " style="display:block;">
						<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="backList();"></dhcc:thirdButton>
					</div>
				</c:if>
	   		</div>
	   	</div>
		<script type="text/javascript">
            $(function(){
                $(".scroll-content").mCustomScrollbar({
                    advanced:{
                        //滚动条根据内容实时变化
                        updateOnContentResize:true
                    }
                });
                if(applySts != 0){
                    showApproveHis();
                }
            });
            //展示审批历史
            function showApproveHis(){
                //获得审批历史信息
                showWkfFlowVertical($("#wj-modeler2"),id,"","bankStatementRegister");
                $("#BankStatementRegisterApproveHis").show();
            }
		</script>
	</body>
</html>