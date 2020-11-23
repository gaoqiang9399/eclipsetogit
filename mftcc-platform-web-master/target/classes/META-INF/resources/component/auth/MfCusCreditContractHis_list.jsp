<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var creditAppId = '${param.creditAppId}';
	var proTableId = '${param.formId}';
	$(function() {
		$.ajax({
			url:webPath+"/workFlowDyForm/getCreditContractHisAppId?creditAppId="+creditAppId,
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					var html = data.tableHtml;
					$("#mfCusCreditContractHisList").empty().html(html);
				}else{
					$("#mfCusCreditContractHisList").remove();
				}
			}
		});
	});
	
	
	function  getCusCreditContractHis(url){
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}	
		top.parent.openBigForm(url, '审批历史详情', function() {
			
		});
	}; 
</script>
<!--收款计划信息 -->
<div class="row clearfix" id="mfCusCreditContractHisListDiv">
	<div class="col-xs-12 column">
		<div class="list-table-replan">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>风控中心审批意见</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfCusCreditContractHisList">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="mfCusCreditContractHisList">
				<dhcc:tableTag property="tablecusCreditContractHisList" paginate="tablecusCreditContractHisList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>

