<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincId = '${param.fincId}';
	var compensatoryTableId = '${param.formId}';
	$(function() {
		MfBusCompensatory.pubMfBusCompensatoryList();
	});
</script>
<!--代偿历史信息 -->
<div class="row clearfix hidden" id="mfCompensatoryHistoryList-block">
	<div class="col-xs-12 column">
		<div class="list-table-replan base-info" id="compensatoryHistoryList">
			<div class="title">
				<span> <i class="i i-xing blockDian"></i> 代偿历史
				</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfCompensatoryHistoryList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfCompensatoryHistoryList">
				<dhcc:tableTag property="tableCompensatoryHistoryDetail" paginate="mfCompensatoryHistoryList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>


