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
<script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js"></script>
<div class="row clearfix btn-opt-group">
	<div class="col-xs-12 column">
		<div class="btn-group pull-right">
			<dhcc:pmsTag pmsId="cus-eval-btn-fk">
				<button class="btn btn-opt" onclick="cusEval.getInitatEcalApp();"
						type="button">
					<i class="i i-eval1"></i><span>发起评级</span>
				</button>
			</dhcc:pmsTag>
		</div>
	</div>
</div>
	
