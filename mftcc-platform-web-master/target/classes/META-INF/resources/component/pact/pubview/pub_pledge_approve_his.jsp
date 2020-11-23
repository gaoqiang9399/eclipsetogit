<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var pactId = '${param.pactId}';
	var pactSts = '${param.pactSts}';
	var approvalId = '${param.approvalId}';
	$(function() {		
		//判断抵质押审批历史模块的显隐
		if(approvalId!=''){
			showWkfFlowVertical($("#wj-modeler36"), approvalId, "36","");
		}else{
			$("#pledgeSpInfo-block").remove();
		}
	});
</script>
<!--  抵质押审批历史 -->
<div class="row clearfix approval-hist" id="pledgeSpInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>抵质押审批历史</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#pledgeSpInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="pledgeSpInfo-div">
				<div class="approval-process">
					<div id="modeler1" class="modeler">
						<ul id="wj-modeler36" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

