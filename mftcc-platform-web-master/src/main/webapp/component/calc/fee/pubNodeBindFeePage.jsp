<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="${webPath}/component/include/table_head.js"></script>	
<script type="text/javascript" src="${webPath}/component/include/filter.js"></script>
<script type="text/javascript" src="${webPath}/component/include/myCustomScrollbar.js"></script>
<script type="text/javascript" src="${webPath}/component/calc/fee/js/pubNodeBindFeePage.js"></script>
<script type="text/javascript" src='${webPath}/component/app/js/guaranteeApply_feeCollect.js'></script>
<!-- 必要参数
	var feeParm = 'nodeNo=' + nodeNo + '&appId=' + appId_pl + '&feePower=' + feePower_pl;
	
	nodeNo ：节点编号（参数为空时查询业务下所有费用）
	appId ：申请编号
	 -->
<div class="busfeeInfo showOrHide hidden">
	<div class="list-table">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>费用标准</span>
			<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#busfee-list">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
		</div>
		<div class="content_table collapse in" id="busfee-list">
		</div>
	</div>
</div>