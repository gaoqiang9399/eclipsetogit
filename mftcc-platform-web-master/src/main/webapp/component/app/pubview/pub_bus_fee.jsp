<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	$(function() {
		pubBusAppFee.init();
	});
</script>
<!--  费用标准 -->
<div class="row clearfix" id="bus-fee-block">
	<div class="dynamic-block" title="费用标准" name="busfee">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#busfee">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="busfee" name="busfee"></div>
		</div>
	</div>
</div>
