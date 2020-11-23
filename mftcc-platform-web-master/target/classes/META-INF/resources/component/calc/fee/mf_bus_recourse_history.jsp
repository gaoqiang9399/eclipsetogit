<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincId = '${param.fincId}';
	var recourseTableId = '${param.formId}';
	$(function() {
		MfBusRecourseApplyDetail.pubMfBusRecourseList();
	});
</script>
<!--追偿历史信息 -->
<div class="row clearfix hidden" id="mfRecocurseHistoryList-block">
	<div class="col-xs-12 column">
		<div class="list-table-replan base-info" id="recourseHistoryList">
			<div class="title">
				<span> <i class="i i-xing blockDian"></i> 追偿历史
				</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfRecourseHistoryList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfRecourseHistoryList">
				<dhcc:tableTag property="tableMfBusRecourseHistoryDetail" paginate="mfRecourseHistoryList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>


