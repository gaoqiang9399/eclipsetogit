<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	
</script>
<!--评级 授信审批历史 -->
 	<div class="row clearfix approval-hist" id="creditPactApproval"  style="display: none;">
		<div class="list-table">
		   <div class="title">
				 <span><i class="i i-xing blockDian"></i>授信合同审批历史</span>
				 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#creditPactApprovalDiv">
					<i class='i i-close-up'></i>
					<i class='i i-open-down'></i>
				</button>
		   </div>
		   <div class="content margin_left_15 collapse in " id="creditPactApprovalDiv">
				<div class="approval-process">
					<div id="modeler5" class="modeler">
						<ul id="wj-modeler5" class="wj-modeler" isApp = "false">
						</ul>
					</div>
				</div>
		   </div>
		</div>
	</div>