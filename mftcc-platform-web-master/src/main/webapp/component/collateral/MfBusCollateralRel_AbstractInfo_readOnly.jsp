<%@page import="cn.mftcc.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	String entrance = (String)request.getParameter("entrance");
	String creditAppId = (String)request.getParameter("creditAppId");
	busEntrance = StringUtil.KillNull(busEntrance);
	String vouType = (String)request.getParameter("vouType");
	vouType = StringUtil.KillNull(vouType);
	
%>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfBusCollateralRel_AbstractInfo_readOnly.js"></script>
<script type="text/javascript">
$(function(){
	MfBusCollateralRel_AbstractInfo_readOnly.cusNo = '<%=cusNo%>';
	if(MfBusCollateralRel_AbstractInfo_readOnly.cusNo == 'null' || MfBusCollateralRel_AbstractInfo_readOnly.cusNo == ""){
		MfBusCollateralRel_AbstractInfo_readOnly.cusNo = '${cusNo}';
	}
	MfBusCollateralRel_AbstractInfo_readOnly.creditAppId = '<%=creditAppId%>';
	if(MfBusCollateralRel_AbstractInfo_readOnly.creditAppId == 'null' || MfBusCollateralRel_AbstractInfo_readOnly.creditAppId == ""){
		MfBusCollateralRel_AbstractInfo_readOnly.creditAppId = '${creditAppId}';
	}
	MfBusCollateralRel_AbstractInfo_readOnly.relId = '<%=relId%>';
	if(MfBusCollateralRel_AbstractInfo_readOnly.relId == 'null' || MfBusCollateralRel_AbstractInfo_readOnly.relId == ""){
		MfBusCollateralRel_AbstractInfo_readOnly.relId = appId;
	}
	MfBusCollateralRel_AbstractInfo_readOnly.busModel = '<%=busModel%>';
	MfBusCollateralRel_AbstractInfo_readOnly.operable = '<%=operable%>';
	MfBusCollateralRel_AbstractInfo_readOnly.type = '<%=type%>';
	MfBusCollateralRel_AbstractInfo_readOnly.fincId = '<%=fincId%>';
	MfBusCollateralRel_AbstractInfo_readOnly.busEntrance = '<%=busEntrance%>';
	if(MfBusCollateralRel_AbstractInfo_readOnly.busEntrance == 'null' || MfBusCollateralRel_AbstractInfo_readOnly.busEntrance == ""){
		MfBusCollateralRel_AbstractInfo_readOnly.busEntrance = '${busEntrance}';
	}
	MfBusCollateralRel_AbstractInfo_readOnly.entrance = 'credit';
	if(MfBusCollateralRel_AbstractInfo_readOnly.entrance == 'null' || MfBusCollateralRel_AbstractInfo_readOnly.entrance == ""){
		MfBusCollateralRel_AbstractInfo_readOnly.entrance = '${entrance}';
	}
	MfBusCollateralRel_AbstractInfo_readOnly.vouType = '<%=vouType%>';
	MfBusCollateralRel_AbstractInfo_readOnly.init();
});
</script>

<!-- 押品摘要信息 -->
<!-- 押品信息未登记 -->
<div id="pleInfono" class="row clearfix no-content hide">
	<div class="col-xs-3 col-md-3 column">
		<button type="button" class="btn btn-font-none no-link padding_left_15">
			<i class="i i-pledge font-icon" id="titleLi"></i>
			<div class="font-text" id="title">反担保信息</div>
		</button>
	</div>
	<div class="col-xs-9 col-md-9 column no-content-tip">
		<span id="noPledge" style="display:none;">暂无反担保信息</span>
		<span id="creditPledge" style="display:none;">无反担保，不需要添加反担保信息</span>
		<button id="addCollateralInfo" class="btn btn-link content-title" title="添加反担保信息" onclick="MfBusCollateralRel_AbstractInfo_readOnly.addCollateralInfo();" style="display:none;font-size: 14;">添加反担保信息</button>
		<!-- <span id="noPledge" onclick="addCollateralInfo()">添加担保信息</span> -->
	</div>
</div>
<!-- 展示押品信息 -->
<div id="pleInfo" class="row clearfix has-content hide">
	<div class="row clearfix cont-row">
	</div>
</div>

<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>	


	
								