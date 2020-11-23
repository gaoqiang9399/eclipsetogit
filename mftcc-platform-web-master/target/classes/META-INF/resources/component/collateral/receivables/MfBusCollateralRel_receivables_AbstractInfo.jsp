<%@page import="cn.mftcc.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${mfBusApply.busModel=='13'}">
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
<script type="text/javascript" src="${webPath}/component/collateral/receivables/js/MfBusCollateralRel_receivables_AbstractInfo.js"></script>
<script type="text/javascript">
MfBusCollateralRel_receivables_AbstractInfo.cusNo = '<%=cusNo%>';
MfBusCollateralRel_receivables_AbstractInfo.relId = '<%=relId%>';
MfBusCollateralRel_receivables_AbstractInfo.creditAppId = '<%=creditAppId%>';
if(MfBusCollateralRel_receivables_AbstractInfo.relId == 'null' || MfBusCollateralRel_receivables_AbstractInfo.relId == ""){
	MfBusCollateralRel_receivables_AbstractInfo.relId = appId;
}
MfBusCollateralRel_receivables_AbstractInfo.busModel = '<%=busModel%>';
MfBusCollateralRel_receivables_AbstractInfo.operable = '<%=operable%>';
MfBusCollateralRel_receivables_AbstractInfo.type = '<%=type%>';
MfBusCollateralRel_receivables_AbstractInfo.fincId = '<%=fincId%>';
MfBusCollateralRel_receivables_AbstractInfo.busEntrance = '<%=busEntrance%>';
MfBusCollateralRel_receivables_AbstractInfo.vouType = '<%=vouType%>';
$(function(){
	MfBusCollateralRel_receivables_AbstractInfo.init();
});
</script>

<!-- 应收账款摘要信息 -->
<!-- 应收账款信息未登记 -->
<div id="pleInfono_receivables" class="row clearfix no-content hide">
	<div class="col-xs-3 col-md-3 column">
		<button type="button" class="btn btn-font-none no-link padding_left_15">
			<i class="i i-pledge font-icon" id="titleLi_receivables"></i>
			<div class="font-text" id="title_receivables">应收账款</div>
		</button>
	</div>
	<div class="col-xs-9 col-md-9 column no-content-tip">
		<span id="noPledge_receivables" style="display:none;">暂无应收账款</span>
		<!-- <span id="creditPledge_receivables" style="display:none;">信用贷款，不需要添加应收账款</span> -->
		<button id="addCollateralInfo_receivables" class="btn btn-link content-title" title="添加应收账款" onclick="MfBusCollateralRel_receivables_AbstractInfo.addCollateralInfo();" style="display:none;font-size: 14;">添加应收账款</button>
		<!-- <span id="noPledge" onclick="addCollateralInfo()">添加应收账款</span> -->
	</div>
</div>
<!-- 展示应收账款信息 -->
<div id="pleInfo_receivables" class="row clearfix has-content hide">
	<div class="row clearfix cont-row">
	</div>
</div>

<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>	
</c:if>   

	
								