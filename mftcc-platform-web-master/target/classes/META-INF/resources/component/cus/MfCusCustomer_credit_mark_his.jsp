<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
    var appId= '${creditAppId}';
    $(function(){
        $.ajax({
            url : webPath + "/mfMakePolicyMeeting/getLastedByMakePolicyMeetingAppId",
            data : {
                appId:appId
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if (data.flag == "success") {
                    let ifShowMakePolicyMeeting = data.ifShowMakePolicyMeeting;
                    if(ifShowMakePolicyMeeting == "1"){
                        //获得审批信息
                        showWkfFlowVertical($("#wj-modeler-makePolicyMeeting"),data.mfMakePolicyMeeting.makePolicyMeetingAppId,"05","make_policy_meeting_approval");
                        $("#makePolicyMeeting-block").show();
                    }
                }
            },
            error : function() {
            }
        });
    });

</script>
<!--  决策会审批历史 -->
<div id="makePolicyMeeting-block" class="approval-hist" style="display: none;">
	<div class="list-table">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>决策会审批历史</span>
			<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#makePolicyMeeting-div">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
		</div>
		<div class="content margin_left_15 collapse in " id="makePolicyMeeting-div">
			<div class="approval-process">
				<div id="modeler-makePolicyMeeting" class="modeler">
					<ul id="wj-modeler-makePolicyMeeting" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>