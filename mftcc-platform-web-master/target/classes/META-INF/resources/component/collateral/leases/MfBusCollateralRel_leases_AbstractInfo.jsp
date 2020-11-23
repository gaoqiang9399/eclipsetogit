<%@page import="cn.mftcc.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${mfBusApply.busModel=='34'}">
<%
	String cusNo = (String)request.getParameter("cusNo");
	String relId = (String)request.getParameter("relId");
	String c = (String)request.getParameter("relNo");
	String busModel = (String)request.getParameter("busModel");
	String operable = (String)request.getParameter("operable");
	String type = (String)request.getParameter("type");
	String fincId = (String)request.getParameter("fincId");
	fincId = StringUtil.KillNull(fincId);
	String busEntrance = (String)request.getParameter("busEntrance");
	String creditAppId = (String)request.getParameter("creditAppId");
	busEntrance = StringUtil.KillNull(busEntrance);
	String vouType = (String)request.getParameter("vouType");
	vouType = StringUtil.KillNull(vouType);
	
%>
<script type="text/javascript" src="${webPath}/component/collateral/leases/js/MfBusCollateralRel_leases_AbstractInfo.js"></script>
<script type="text/javascript">
MfBusCollateralRel_leases_AbstractInfo.cusNo = '<%=cusNo%>';
MfBusCollateralRel_leases_AbstractInfo.relId = '<%=relId%>';
MfBusCollateralRel_leases_AbstractInfo.creditAppId = '<%=creditAppId%>';
if(MfBusCollateralRel_leases_AbstractInfo.relId == 'null' || MfBusCollateralRel_leases_AbstractInfo.relId == ""){
	MfBusCollateralRel_leases_AbstractInfo.relId = appId;
}
MfBusCollateralRel_leases_AbstractInfo.busModel = '<%=busModel%>';
MfBusCollateralRel_leases_AbstractInfo.operable = '<%=operable%>';
MfBusCollateralRel_leases_AbstractInfo.type = '<%=type%>';
MfBusCollateralRel_leases_AbstractInfo.fincId = '<%=fincId%>';
MfBusCollateralRel_leases_AbstractInfo.busEntrance = '<%=busEntrance%>';
MfBusCollateralRel_leases_AbstractInfo.vouType = '<%=vouType%>';
$(function(){
	MfBusCollateralRel_leases_AbstractInfo.init();
});
</script>

<!-- 租赁物摘要信息 -->
<!-- 租赁物信息未登记 -->
<div id="pleInfono_leases" class="row clearfix no-content hide">
	<div class="col-xs-3 col-md-3 column">
		<button type="button" class="btn btn-font-none no-link padding_left_15">
			<i class="i i-pledge font-icon" id="titleLi_leases"></i>
			<div class="font-text" id="title_leases">租赁物</div>
		</button>
	</div>
	<div class="col-xs-9 col-md-9 column no-content-tip">
		<span id="noPledge_leases" style="display:none;">暂无租赁物</span>
		<!-- <span id="creditPledge_leases" style="display:none;">信用贷款，不需要添加租赁物</span> -->
		<button id="addCollateralInfo_leases" class="btn btn-link content-title" title="添加租赁物" onclick="MfBusCollateralRel_leases_AbstractInfo.addCollateralInfo();" style="display:none;font-size: 14;">添加租赁物</button>
		<!-- <span id="noPledge" onclick="addCollateralInfo()">添加租赁物</span> -->
	</div>
</div>
<!-- 展示租赁物信息 -->
<div id="pleInfo_leases" class="row clearfix has-content hide">
	<div class="row clearfix cont-row">
	</div>
</div>

<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>	
</c:if>

	
								