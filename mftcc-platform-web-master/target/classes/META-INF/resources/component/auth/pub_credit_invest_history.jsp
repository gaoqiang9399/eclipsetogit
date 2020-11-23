<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var creditAppId = '${param.creditAppId}';
	$(function() {
        MfCusCredit.investHistoryInit();
	});
</script>
<!--还款历史信息 -->
<div class="row clearfix hidden" id="investHistory-block">
	<div class="col-xs-12 column">
		<div class="list-table-replan base-info" id="investHistoryList">
			<div class="title">
				<span> <i class="i i-xing blockDian"></i> 尽调报告历史
				</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#investHistoryList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfInvestHistoryList">
				<dhcc:tableTag property="tableinvesthistory" paginate="mfRepayHistoryList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>


