<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	var pactId = '${param.pactId}';
	var planTableId = '${param.formId}';
	$(function() {
        pubMfRepayPlanMergeList.init();
	});
</script>
<!--收款计划信息 -->
<div class="row clearfix hidden" id="mfRepayPlanMergeList-block">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>合并收款计划</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfRepayPlanMergeList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfRepayPlanMergeList">
				<dhcc:tableTag property="tablerepayplan0003" paginate="mfRepayPlanMergeList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

