<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/component/include/common.jsp"%>

<script type="text/javascript">
	var appId = '${param.appId}';
	var pactId = '${param.pactId}';
	$(function() {
		pubMfGpsDetailInfo.init();
	});
</script>
<!-- GPS详情信息-->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>GPS详情信息</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#gpsDetailInfo">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="gpsDetailInfo" name="gpsDetailInfo">
				<form method="post" theme="simple" id="gpsDetailForm" name="operform" action="${webPath}/mfBusGpsReg/updateAjaxByOne">
					<dhcc:propertySeeTag property="formcusBusGpsRegDetail" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div>
