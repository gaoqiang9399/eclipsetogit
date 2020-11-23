<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fiveclassId= '${param.fiveclassId}';
	var fiveFlag= '${param.fiveFlag}';
	$(function(){
		if(fiveFlag=="fiveFlag"){//bug修改：显示五级分类审批流程
			showWkfFlow($("#wj-modeler1"),fiveclassId);
			showWkfFlowVertical($("#wj-modeler5"),fiveclassId,"6","fiveclass_approval");
		}else{
			$("#fiveClass-block").remove();
		}
	});

</script>
<!--  五级分类审批历史 -->
<div class="row clearfix approval-hist" id="fiveClass-block">
	<div class="list-table">
	   <div class="title">
			 <span><i class="i i-xing blockDian"></i>五级分类审批历史</span>
			 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
	   </div>
	   <div class="content margin_left_15 collapse in " id="spInfo-div">
			<div class="approval-process">
				<div id="modeler5" class="modeler">
					<ul id="wj-modeler5" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
	   </div>
	</div>
</div>
	
