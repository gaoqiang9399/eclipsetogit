<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
    var ifBizManger='';
    var query = '';
	$(function() {
		$.ajax({
			url:webPath+"/mfLawsuit/getMfLitigationExpenseList?assetId="+caseId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				$("#mfLitigationExpenseApplyList").empty().html(html);
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
<!--费用登记信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>费用登记</span>
				<button class="btn btn-link formAdd-btn" onclick="MfLawsuitFollow_ShowList.insertFollow();" title="费用登记"><i class="i i-jia3"></i></button>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfLitigationExpenseApplyList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfLitigationExpenseApplyList">
				<dhcc:tableTag property="tablecostreglist" paginate="mfLitigationExpenseApplyList" head="true" ></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>