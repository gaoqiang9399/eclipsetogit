<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
    var planTableId = '${param.formId}';
	$(function() {
        pubMfRepayHistoryListForMain.init();
	});
</script>
<!--还款历史信息 -->
<div class="row clearfix" id="mfRepayHistoryList-block">
	<div class="col-xs-12 column">
		<div class="list-table-replan base-info" id="repayHistoryList">
			<div class="title">
				<span> <i class="i i-xing blockDian"></i> 还款历史
				</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfRepayHistoryList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfRepayHistoryList">

			</div>
		</div>
	</div>
</div>

