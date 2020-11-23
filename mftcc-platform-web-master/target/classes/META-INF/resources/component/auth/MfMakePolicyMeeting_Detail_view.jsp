<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="${webPath}/component/app/makepolicymeeting/js/MfMakingMeetingSummary_Detail.js"></script>
<script type="text/javascript">
	var creditAppId = '${param.creditAppId}';
    var appId = '${param.appId}';
    var formId = '${param.formId}';
    if(creditAppId!=null&&creditAppId!=""){
        appId = creditAppId;
	}
    $(function(){
        MfMakingMeetingSummary_Detail.init();
    });

</script>
<!-- 决策会申请信息-->
<div class="row clearfix" id="cusMakePolicyMeetingDiv">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>决策会申请信息</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#cusMakePolicyMeetingDetailInfo">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="cusMakePolicyMeetingDetailInfo" name="cusMakePolicyMeetingDetailInfo">
				<form method="post" theme="simple" id="cusMakePolicyMeetingDetailForm" name="operform">
					<dhcc:propertySeeTag property="formcusMakePolicyMeetingDetail" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div> 


