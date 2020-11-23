<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincChidId = '${param.fincChidId}';
	var fincSts = '${param.fincSts}';
	var fincProcessId = '${param.fincProcessId}';
	$(function() {
	    debugger;		//判断放款审批历史模块的显隐（fincSts:0-申请 1-未提交 2-流程中 3-退回 4-审批通过 ）
		if (fincSts != '0' && fincSts != '1' && fincSts != '' && fincProcessId != '') {
			showWkfFlowVertical($("#wj-modeler4"), fincChidId, "3","putout_approval");
		} else {
			$("#fincSpInfo-block").remove();
		}
	});
</script>
<!--  放款审批历史 -->
<div class="row clearfix approval-hist" id="fincSpInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#fincSpInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="fincSpInfo-div">
				<div class="approval-process">
					<div id="modelerfinc" class="modeler">
						<ul id="wj-modeler4" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

