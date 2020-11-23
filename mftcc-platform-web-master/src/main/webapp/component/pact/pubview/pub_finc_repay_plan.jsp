<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	var pactId = '${param.pactId}';
	var planTableId = '${param.formId}';
	$(function() {
		pubMfRepayPlanList.init();
	});
</script>
<!--收款计划信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfRepayPlanList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfRepayPlanList">
				<dhcc:tableTag property="tablerepayplan0003" paginate="mfRepayPlanList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

