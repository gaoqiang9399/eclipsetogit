<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var pactId = '${param.pactId}';
	var pactSts = '${param.pactSts}';
	var pactProcessId = '${param.pactProcessId}';
	$(function() {
		//判断合同审批历史模块的显隐（pactSts:0-未补录 1-未提交 2-流程中 3-退回 4-审批通过 5-否决 6-已完结）
		if (pactSts != '0' && pactSts != '1' && pactProcessId != '') {
			showWkfFlowVertical($("#wj-modeler3"), pactId, "4","contract_approval");
		} else {
			$("#pactSpInfo-block").remove();
		}
	});
</script>
<!--  合同审批历史 -->
<div class="row clearfix approval-hist" id="pactSpInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#pactSpInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="pactSpInfo-div">
				<div class="approval-process">
					<div id="modeler1" class="modeler">
						<ul id="wj-modeler3" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

