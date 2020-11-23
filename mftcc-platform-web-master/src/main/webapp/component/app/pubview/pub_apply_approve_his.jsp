<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId= '${param.appId}';
	var primaryAppId = '${param.primaryAppId}';
	var appSts= '${param.appSts}';
	var applyProcessId= '${param.applyProcessId}';
	var primaryApplyProcessId= '${param.primaryApplyProcessId}';
	var reconsiderId = '${reconsiderId}';// 否决复议审批业务编号("reconsider" + appId)
	var OPEN_APPROVE_HIS = '${OPEN_APPROVE_HIS}';// 是否展开审批历史
    var OPEN_APPROVE_HIS_RECONSIDE = '${OPEN_APPROVE_HIS_RECONSIDE}';
	$(function(){
	    if(OPEN_APPROVE_HIS_RECONSIDE == '1'){
			$("#spInfo-block-reconside").remove();
            if(appSts != '0'&&applyProcessId!=''){
                pubBusApproveHis.init();
            }else {
                $("#spInfo-block").remove();
            }
		}else {
            $("#spInfo-block-reconside").remove();
            //审批信息模块
            if (reconsiderId != '') {
                reconsiderApproveHis.init(reconsiderId);
            } else if (appSts != '0' && applyProcessId != '') {
                pubBusApproveHis.init();
            } else {
                $("#spInfo-block").remove();
            }
        }
		//业务初选审批历史
		if(primaryApplyProcessId!=''){
			pubPrimaryBusApproveHis.init(primaryAppId);
		}else{
			$("#primary-spInfo-block").remove();
		}
		if (OPEN_APPROVE_HIS=='1'){
		    $(".ideaOpt").click();
		}

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
<!--  业务初选审批历史 -->
<div id="primary-spInfo-block" class="approval-hist">
	<div class="list-table">
	   <div class="title">
			 <span><i class="i i-xing blockDian"></i>初选审批历史</span>
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
<!--  业务审批历史 -->
<div id="spInfo-block" class="approval-hist">
	<div class="list-table">
	   <div class="title">
			 <span><i class="i i-xing blockDian"></i>业务审批历史</span>
			 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
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

<div id="spInfo-block-reconside" class="approval-hist" style="width: 100%">
	<div class="list-table">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>复议审批历史</span>
			<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div-reconside">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
		</div>
		<div class="content margin_left_15 collapse in " id="spInfo-div-reconside">
			<div class="approval-process">
				<div id="modeler1" class="modeler">
					<ul id="wj-modeler3" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

	
