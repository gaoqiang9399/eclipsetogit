<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	var pactId = '${param.pactId}';
	var pactId = '${param.pactId}';
	var feePlanTableId = '${param.formId}';
	$(function() {
		pubMfBusFeePlanList.init();
	});
</script>
<!--还款历史信息 -->
<div class="row clearfix hidden" id="mfBusFeePlanList-block">
	<div class="col-xs-12 column">
		<div class="list-table-replan base-info" id="busFeePlanList">
			<div class="title">
				<span> <i class="i i-xing blockDian"></i> 费用计划
				</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusFeePlanList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfBusFeePlanList">
				<dhcc:tableTag property="tablebusFeePlanList" paginate="mfBusFeePlanList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>


