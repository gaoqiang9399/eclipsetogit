<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var chargeId = '${param.chargeId}';
    var chargeSts = '${param.chargeSts}';
	$(function() {
		//判断审批历史模块的显隐
        if (chargeSts != '0' &&chargeId!='') {
			showWkfFlowVertical($("#wj-modelerrece"), chargeId, "","charge-fee");
		} else {
			$("#receChargeFeeInfo-block").remove();
		}
	});
</script>
<!--  放款审批历史 -->
<div class="row clearfix approval-hist" id="receChargeFeeInfo-block">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>缴款通知书审批历史</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#receFincSpInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="receFincSpInfo-div">
				<div class="approval-process">
					<div id="modelerrecefinc" class="modeler">
						<ul id="wj-modelerrece" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

