<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	var pactId = '${param.pactId}';
	var repayFeeHisTableId = '${param.formId}';
	$(function() {
		pubMfRepayFeeHistoryList.init();
	});
</script>
<!--收费历史信息 -->
<div class="row clearfix hidden" id="mfRepayFeeHistoryList-block">
	<div class="col-xs-12 column">
		<div class="list-table-replan base-info" id="repayFeeHistoryList">
			<div class="title">
				<span> <i class="i i-xing blockDian"></i> ${param.blockName}</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfRepayFeeHistoryList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfRepayFeeHistoryList">
				<dhcc:tableTag property="tableMfBusFeePlanHistoryDetail0001" paginate="mfRepayFeeHistoryList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>


