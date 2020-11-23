<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	$(function() {
		$.ajax({
			url:webPath+"/mfLawsuit/getExecutionRecoveryList?caseId="+caseId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				$("#executionRecoveryList").empty().html(html);
			}
		});
	});
</script>
<style type="text/css">
	tbody tr td{
		text-align: left !important;
	}
	thead tr th{
		text-align: left !important;
	}
</style>
<!--执行回收登记登记信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>执行回收情况</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#executionRecoveryList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="executionRecoveryList">
				<dhcc:tableTag property="tableExecutionRecoveryList" paginate="executionRecoveryList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>