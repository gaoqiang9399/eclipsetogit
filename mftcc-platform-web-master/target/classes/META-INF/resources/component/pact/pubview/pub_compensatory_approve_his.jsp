<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var compensatoryId = '${compensatoryId}';
	var compensatoryappSts = '${compensatoryappSts}';
	var approveProcessId = '${approveProcessId}';
	$(function() {
		//判断代偿审批块的显隐 (申请状态 0 未提交 1申请 2 审批 3 通过 4否决 5确认)
		if (compensatoryappSts != '0' && compensatoryappSts != '1' && compensatoryappSts != '' && approveProcessId != '') {
			showWkfFlowVertical($("#wj-modeler6"), compensatoryId, "51","");
		} else {
			$("#compensatoryInfo-block").remove();
		}
	});
</script>
<!--  放款审批历史 -->
<div class="row clearfix approval-hist" id="compensatoryInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>代偿审批历史</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#compensatorySpInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="compensatorySpInfo-div">
				<div class="approval-process">
					<div id="modelerfinc" class="modeler">
						<ul id="wj-modeler6" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

