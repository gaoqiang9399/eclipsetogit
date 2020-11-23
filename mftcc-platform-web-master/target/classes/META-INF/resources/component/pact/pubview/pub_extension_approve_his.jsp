<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	/* var extensionApplyId = '${param.extensionApplyId}';
	var extenAppSts = '${param.extenAppSts}';
	$(function() {
		//判断放款审批历史模块的显隐（fincSts:0-申请 1-未提交 2-流程中 3-退回 4-审批通过 ）
		if (extenAppSts != '0' && extenAppSts != '1' && extenAppSts != '') {
			showWkfFlowVertical($("#extension-wj-modeler"), extensionApplyId, "3");
		} else {
			$("#extenSpInfo-block").remove();
		}
	}); */
</script>
<!--  放款审批历史 -->
<div class="row clearfix approval-hist" id="extenSpInfo-block" style="display:none">
	<div class="col-xs-12 column">
		<div class="list-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>展期审批历史</span>
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#extenSpInfo-div">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in " id="extenSpInfo-div">
				<div class="approval-process">
					<div id="extension-modeler" class="modeler">
						<ul id="extension-wj-modeler" class="wj-modeler" isApp="false">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

