<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var creditAppId = '${param.creditAppId}';
	var proTableId = '${param.formId}';
	$(function() {
		$.ajax({
			url:webPath+"/mfCusPorductCredit/getMfCusCreditProListAjax?creditAppId="+creditAppId+"&tableId="+proTableId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.ifHasList == "1"){
					var html = data.htmlStr;
					$("#mfCusCreditProList").empty().html(html);
				}else{
					$("#cusCreditListDiv").remove();
				}
			}
		});
	});
</script>
<!--收款计划信息 -->
<div class="row clearfix" id="cusCreditListDiv">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>授信产品列表</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfCusCreditProList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfCusCreditProList">
				<dhcc:tableTag property="tablecuscreditProList" paginate="mfCusPorductCreditList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

