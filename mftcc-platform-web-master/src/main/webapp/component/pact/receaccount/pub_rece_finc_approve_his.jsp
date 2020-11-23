<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincMainId = '${param.fincMainId}';
	var fincSts = '${param.fincSts}';
	var fincProcessId = '${param.fincProcessId}';
	$(function() {
		//判断放款审批历史模块的显隐（fincSts:0-申请 1-未提交 2-流程中 3-退回 4-审批通过 ）
		if (fincSts != '0' && fincSts != '1' && fincSts != '' && fincProcessId != '') {
			showWkfFlowVertical($("#wj-modelerrece"), fincMainId, "","");
		} else {
			$("#receFincSpInfo-block").remove();
		}
	});
</script>
<!--  放款审批历史 -->
<div class="row clearfix approval-hist" id="receFincSpInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>放款审批历史</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receFincSpInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="receFincSpInfo-div">
				<div class="approval-process">
					<div id="modelerrecefinc" class="modeler">
						<ul id="wj-modelerrece" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

