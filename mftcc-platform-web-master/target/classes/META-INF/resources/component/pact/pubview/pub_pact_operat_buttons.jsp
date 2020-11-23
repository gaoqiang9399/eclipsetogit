<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<script type="text/javascript">
	var pactSts= '${param.pactSts}';
	var advanceLoanId= '${param.advanceLoanId}';
	var nodeNo= '${param.nodeNo}';
	$(function(){
        if(pactSts=="4" || pactSts=="6"){
            $('.btn-file-archive').removeClass('hidden');
            if(pactSts=="4"){
                $("#certiInfo").attr("disabled",false);
                $("#certiInfo").removeClass("btn-opt-dont");
                $("#certiInfo").addClass("btn-opt");
            }
        }
        if(nodeNo !="certidInfo_reg" || advanceLoanId != ''){
            $("#MfBusPact_DynaDetail_advance_button").attr("disabled","disabled").addClass("btn-opt-dont");
        }
	});
</script>
<!--信息登记操作入口 -->
<div class="row clearfix btn-opt-group">
	<div class="col-xs-12 column">
		<div class="btn-group pull-right">
			<dhcc:pmsTag pmsId="thirdPay-Button">
				<button class="btn btn-opt hidden" id="thirdPayButton" onclick="MfBusPact_DynaDetail.thirdPay();" type="button" >
					<i class="i i-guidang"></i><span>三方放款</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pact-disagree">
				<button class="btn btn-opt" onclick="inputDisagreeBuss.inputDisagree()" type="button" id="MfBusPact_DynaDetail_disagree_button">
					<i class="i i-x"></i><span >终止业务</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="advance_loan">
				<button class="btn btn-opt" onclick="MfBusAdvanceLoan.openAdvanceLoan()" type="button" id="MfBusPact_DynaDetail_advance_button">
					<i class="i i-xing"></i><span >提前放款</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pact-finish-loan">
				<button class="btn btn-opt" onclick="MfBusPact_DynaDetail.finishLoan('fee_collect_confirm')" type="button" id="MfBusPact_DynaDetail_disagree_button">
					<i class="i i-x"></i><span >结束放款</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pact-disagree2">
				<!-- 放款及放款之前都允许否决, 以权限控制此功能是否开放 -->
				<button class="btn btn-opt" onclick="inputDisagreeBuss.inputDisagreeFinc()" type="button" id="MfBusPact_DynaDetail_disagree2_button">
					<i class="i i-x"></i><span >拒绝放款</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="pact-pre-end">
				<button class="btn btn-opt" onclick="inputDisagreeBuss.inputPactPreEnd()" type="button" id="MfBusPact_DynaDetail_pactEnd_button">
					<i class="i i-x"></i><span >提前解约</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="sign-file-print">
				<button class="btn btn-opt" onclick="MfBusPact_DynaDetail.filePrint();" type="button">
					<i class="i i-x"></i><span>文件打印</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-file-filing-btn">
				<button class="btn btn-opt btn-file-archive hidden" onclick="fileArchive();" type="button">
					<i class="i i-guidang"></i><span>文件归档</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="repay-plan-trial-btn">
				<button class="btn btn-opt" id="repayPlanTrial" onclick="MfBusPact_DynaDetail.repayPlanTrial();" type="button">
					<i class="i i-guidang"></i><span>还款计划试算</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-check-btn">
				<button class="btn btn-opt btn-lightgray" id="loanAfterExamine"
						onclick="BusExamine.loanAfterExamine();" type="button">
					<i class="i i-fangdajing"></i><span>贷后检查</span>
				</button>
			</dhcc:pmsTag>
		</div>
	</div>
</div>

