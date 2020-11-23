<%@page import="cn.mftcc.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cusNo = (String)request.getParameter("cusNo");
	String appId = (String)request.getParameter("appId");
	String busEntrance = (String)request.getParameter("busEntrance");
	String fincMainId = (String)request.getParameter("fincMainId");
%>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusReceBaseInfo_AbstractInfo.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    MfBusReceBaseInfo_AbstractInfo.cusNo = '<%=cusNo%>';
    MfBusReceBaseInfo_AbstractInfo.appId = '<%=appId%>';
    MfBusReceBaseInfo_AbstractInfo.busEntrance = '<%=busEntrance%>';
    MfBusReceBaseInfo_AbstractInfo.fincMainId = '<%=fincMainId%>';
$(function(){
	MfBusReceBaseInfo_AbstractInfo.init();
});
</script>

<!-- 应收账款摘要信息 -->
<!-- 应收账款信息未登记 -->
<div id="noReceInfo" class="row clearfix no-content hide">
	<div class="col-xs-3 col-md-3 column">
		<button type="button" class="btn btn-font-none no-link padding_left_15">
			<i class="i i-pledge font-icon" id="titleLi_receivables"></i>
			<div class="font-text" id="title_receivables">应收账款</div>
		</button>
	</div>
	<div class="col-xs-9 col-md-9 column no-content-tip">
		<span id="noReceInfoSpan" style="display:none;">暂无应收账款</span>
		<button id="addReceInfo" class="btn btn-link content-title" title="添加应收账款" onclick="MfBusReceBaseInfo_AbstractInfo.addReceInfo();" style="display:none;font-size: 14;">添加应收账款</button>
	</div>
</div>
<!-- 展示应收账款信息 -->
<div id="receInfo" class="row clearfix has-content hide">
	<div class="row clearfix cont-row">
	</div>
</div>

<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>	


	
								