<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String allianceId = (String)request.getParameter("allianceId");
%>
<script type="application/javascript" src="${webPath}/component/alliance/js/MfBusAlliance_takeup_his.js"></script>
<script type="text/javascript">
    var allianceId = '<%=allianceId%>';
    var allianceTakeupHisFormId= '${param.formId}';
    $(function() {
        takeupHis.init();
    });
</script>
<div class="row clearfix" id="putout-his-block">
	<div class="dynamic-block" title="额度变更历史" name="takeupHisDiv">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>额度变更历史</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#takeupHis">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="takeupHis" name="takeupHis"></div>
		</div>
	</div>
</div>

	
								