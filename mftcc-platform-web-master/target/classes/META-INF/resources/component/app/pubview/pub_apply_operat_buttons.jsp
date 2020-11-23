<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
	<!--信息登记操作入口 -->
<script type="text/javascript">
    var appId= '${param.appId}';
    var primaryAppId = '${param.primaryAppId}';
    var appSts= '${param.appSts}';
    var applyProcessId= '${param.applyProcessId}';
    var primaryApplyProcessId= '${param.primaryApplyProcessId}';
    var reconsiderId = '${reconsiderId}';// 否决复议审批业务编号("reconsider" + appId)
    var OPEN_APPROVE_HIS = '${OPEN_APPROVE_HIS}';// 是否展开审批历史
</script>
<div class="row clearfix btn-opt-group">
	<div class="col-xs-12 column">
		<div class="btn-group pull-right">
<!-- 								<button class="btn btn-opt" onclick="addedService();" type="button"> -->
<!-- 									<i class="i i-lianwang"></i><span>联网核查</span> -->
<!-- 								</button> -->
			<dhcc:pmsTag pmsId="apply-eval">
				<button class="btn btn-opt" onclick="MfBusApply_DynaDetail.eval()" type="button" id="MfBusApply_DynaDetail_eval_button">
					<i class="i i-x"></i><span >房屋评估</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply-car-eval">
				<button class="btn btn-opt" onclick="MfBusApply_DynaDetail.carEval()" type="button" id="MfBusApply_DynaDetail_carEval_button">
					<i class="i i-x"></i><span>车辆评估</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply_car_gps">
				<button class="btn btn-opt" onclick="MfBusApply_DynaDetail.carGps()" type="button" id="MfBusApply_DynaDetail_carGps_button">
					<i class="i i-x"></i><span>车辆GPS查询</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply-house-eval">
				<button class="btn btn-opt" onclick="MfBusApply_DynaDetail.houseEval()" type="button" id="MfBusApply_DynaDetail_houseEval_button">
					<i class="i i-x"></i><span>房屋评估</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply-disagree">
				<button class="btn btn-opt" onclick="inputDisagreeBuss.inputDisagree()" type="button" id="MfBusApply_DynaDetail_disagree_button">
					<i class="i i-x"></i><span>终止业务</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cus-eval-debts-btn">
				<button class="btn btn-opt" id="debtEvalApply" onclick="cusEval.getInitatEcalApp('${param.appId}','4','1');"
						type="button">
					<i class="i i-eval1"></i><span>详审评级</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply-che300-eval">
			<button class="btn btn-opt" onclick="MfBusApply_DynaDetail.che300Eval();" type="button">
				<i class="i i-x"></i><span>商用车询价</span>
			</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply-jxl-pot">
			<button class="btn btn-opt" onclick="MfBusApply_DynaDetail.honeypot();" type="button">
				<i class="i i-x"></i><span>蜜罐报告</span>
			</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply-jxl-bee">
			<button class="btn btn-opt" onclick="MfBusApply_DynaDetail.honeybee();" type="button">
				<i class="i i-x"></i><span>蜜蜂报告</span>
			</button>
			</dhcc:pmsTag>



			<button class="btn btn-opt" onclick="MfBusApply_DynaDetail.filePrint();" type="button">
				<i class="i i-x"></i><span >文件打印</span>
			</button>
		</div>
	</div>
</div>
	
