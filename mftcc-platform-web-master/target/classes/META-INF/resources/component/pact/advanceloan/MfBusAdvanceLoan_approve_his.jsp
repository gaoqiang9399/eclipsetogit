<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var advanceLoanId = '${param.advanceLoanId}';
    var advanceLoanSts = '${param.advanceLoanSts}';
	$(function() {
		//判断审批历史模块的显隐
        if (advanceLoanSts != '0' &&advanceLoanId!='') {
			showWkfFlowVertical($("#advanceLoanInfo-modelerrece"), advanceLoanId, "","advance-loan");
		} else {
			$("#advanceLoanInfo-block").hide();
		}
	});
</script>
<!--  提前放款审批历史 -->
<div class="row clearfix approval-hist" id="advanceLoanInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>提前放款审批历史</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#advanceLoanInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="advanceLoanInfo-div">
				<div class="approval-process">
					<div id="modelerrecefinc" class="modeler">
						<ul id="advanceLoanInfo-modelerrece" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

