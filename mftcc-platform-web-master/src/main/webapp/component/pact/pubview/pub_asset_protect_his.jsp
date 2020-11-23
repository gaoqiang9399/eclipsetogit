<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	var protectId = '${protectId}';
	$(function(){
		//资产保全审批流程 
		if(protectId != null && protectId != ""){
			//showWkfFlow($("#wj-modeler1"),protectId);
			showWkfFlowVertical($("#wj-modeler-protect"),protectId,"15","assets_protect_approval");
		}else{
			$("#protect-spInfo-block").remove();
		}
	});
</script>
<!--  资产保全审批历史-->
<c:if test="${assetProtect=='assetProtect' && protectId != null && protectId != ''}">
<div class="row clearfix approval-hist" id="protect-spInfo-block">
	<div class="list-table">
	   <div class="title">
			 <span><i class="i i-xing blockDian"></i>资产保全审批历史</span>
			 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
	   </div>
	   <div class="content margin_left_15 collapse in " id="spInfo-div">
			<div class="approval-process">
				<div id="modeler4" class="modeler">
					<ul id="wj-modeler-protect" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
	   </div>
	</div>
</div>
</c:if>
