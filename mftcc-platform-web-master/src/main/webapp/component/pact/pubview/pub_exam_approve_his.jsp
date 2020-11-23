<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var examHisId= '${param.examHisId}';
	$(function(){
		//贷后检查审批流程 
		if(examHisId != null && examHisId != ""){
			showWkfFlow($("#wj-modeler1"),examHisId);
			showWkfFlowVertical($("#wj-modeler-examine"),examHisId,"7","exam_approval");
		}else{
			$("#examine-spInfo-block").remove();
		}
	});

</script>
<!--  贷后检查审批历史 -->
<div class="row clearfix approval-hist" id="examine-spInfo-block">
	<div class="list-table">
	   <div class="title">
			 <span><i class="i i-xing blockDian"></i>贷后检查审批历史</span>
			 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
	   </div>
	   <div class="content margin_left_15 collapse in " id="spInfo-div">
			<div class="approval-process">
				<div id="modeler4" class="modeler">
					<ul id="wj-modeler-examine" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
	   </div>
	</div>
</div>
	
