<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="${webPath}/component/cus/js/MfBusGpsReg_tabList.js"></script>
<script type="text/javascript">
	var appId = '${param.appId}';
	$(function() {
		pubMfGpsListInfo.init();
	});
</script>
<!-- GPS详情信息-->
<div class="row clearfix">
	<div class="col-xs-12 column">
	<div class="dynamic-block" title="GPS列表" name="MfCusFinMainAction" data-sort="1"
				    data-tablename="mf_bus_gps_reg">
		<div class="list-table" id="gpsListDiv">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>GPS列表</span>
				<button class="btn btn-link formAdd-btn" onclick="MfBusGpsReg_tabList.addGpsFormInfo();" title="新增"><i class="i i-jia3"></i></button>
				<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#gpsListInfo">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content margin_left_15 collapse in" id="gpsListInfo">
			</div>
		</div>
		</div>
	</div>	
</div>
