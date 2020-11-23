<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	$(function() {
        pubBusAppRepayPlan.init();
	});
</script>
<!--  费用标准 -->
<div class="row clearfix" id="busrepayplan-block">
	<div class="dynamic-block" title="还款计划" name="busrepayplan">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#busrepayplan">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="busrepayplan" name="busrepayplan"></div>
		</div>
	</div>
</div>
