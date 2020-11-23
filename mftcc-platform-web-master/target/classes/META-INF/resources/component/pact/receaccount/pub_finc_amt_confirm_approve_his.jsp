<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	$(function(){
		//审批信息模块
        showWkfFlowVertical($("#wj-modeler"),confirmId,"","");
	});

</script>
<!--  融资额度审批历史 -->
<div id="spInfo-block" class="approval-hist">
	<div class="list-table">
	   <div class="title">
			 <span><i class="i i-xing blockDian"></i>融资额度审批历史</span>
			 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
	   </div>
	   <div class="content margin_left_15 collapse in " id="spInfo-div">
			<div class="approval-process">
				<div id="modeler1" class="modeler">
					<ul id="wj-modeler" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
	   </div>
	</div>
</div>
	
