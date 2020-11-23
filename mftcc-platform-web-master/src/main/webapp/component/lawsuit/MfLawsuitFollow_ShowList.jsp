<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="${webPath}/component/lawsuit/js/MfLawsuitFollow_ShowList.js"></script>
<script type="text/javascript">
	$(function() {
		$.ajax({
			url:webPath+"/mfLawsuitFollow/getMfLawsuitFollowList?caseId="+caseId,
			type:'post',
			dataType:'json',
			success:function(data){
				var html = data.htmlStr;
				$("#mfLawsuitFollowList").empty().html(html);
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
<!--案件跟进 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>案件跟进</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfLawsuitFollowList"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfLawsuitFollowList">
				<dhcc:tableTag property="tablelawsuitfollow0001" paginate="mfLawsuitFollowList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>