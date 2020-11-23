<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var creditAppId = '${param.creditAppId}';
	var proTableId = '${param.formId}';
	$(function() {
		$.ajax({
			url:webPath+"/workFlowDyForm/getCallCenterTelVisitListAppId?creditAppId="+creditAppId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					var html = data.tableHtml;
					$("#mfCallCenterTelVisitList").empty().html(html);
				}else{
					$("#mfCallCenterTelVisitList").remove();
				}
			}
		});
	});
	
	
	function  getCallCenterTelVisitDetailPage(url){
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}	
		top.parent.openBigForm(url, '电话回访记录详情', function() {
			
		});
	};
</script>
<!--收款计划信息 -->
<div class="row clearfix" id="mfCallCenterTelVisitListDiv">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>电话回访记录列表</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfCallCenterTelVisitList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfCallCenterTelVisitList">
				<dhcc:tableTag property="mfCallCenterTelVisitList" paginate="mfCallCenterTelVisitList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

