<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!--评级 授信审批历史 -->
 	<div class="row clearfix approval-hist hide" id="evalApprovalHis-block">
		<div class="list-table">
		   <div class="title">
				 <span><i class="i i-xing blockDian"></i>审批历史</span>
				 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#evalApprovalSpInfo-div">
					<i class='i i-close-up'></i>
					<i class='i i-open-down'></i>
				</button>
		   </div>
		   <div class="content margin_left_15 collapse in " id="evalApprovalSpInfo-div">
				<div class="approval-process">
					<div id="evalApprovalHisModeler-div" class="modeler">
						<ul id="evalApprovalHisModeler" class="wj-modeler" isApp = "false">
						</ul>
					</div>
				</div>
		   </div>
		</div>
	</div>