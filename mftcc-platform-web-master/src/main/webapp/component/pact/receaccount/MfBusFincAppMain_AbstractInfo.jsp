<%@page import="cn.mftcc.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String fincMainId = (String)request.getParameter("fincMainId");
	String appId = (String)request.getParameter("appId");
	String pactId = (String)request.getParameter("pactId");
	String busEntrance = (String)request.getParameter("busEntrance");
%>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/js/MfBusFincAppMain_AbstractInfo.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    MfBusFincAppMain_AbstractInfo.fincMainId = '<%=fincMainId%>';
    MfBusFincAppMain_AbstractInfo.appId = '<%=appId%>';
    MfBusFincAppMain_AbstractInfo.pactId = '<%=pactId%>';
    MfBusFincAppMain_AbstractInfo.busEntrance = '<%=busEntrance%>';
$(function(){
	MfBusFincAppMain_AbstractInfo.init();
});
</script>
<!-- 展示应收账款融资信息 -->
<div id="receFincMainInfo" class="row clearfix has-content">
	<div class="row clearfix cont-row">
		<div class="col-xs-3 col-md-3 column padding_top_10">
			<button type="button" class="btn btn-font-pact padding_left_15" onclick="MfBusFincAppMain_AbstractInfo.getFincMainDetail();">
				<i class="i i-pact font-icon"></i>
				<div class="font-text">融资信息</div>
			</button>
		</div>
		<div class="col-xs-9 col-md-9 column" >
			<div class="row clearfix">
				<div class="col-xs-10 col-md-10 column margin_top_20">
					<button type="button" id="apply-name" class="btn btn-link content-title"  title="融资信息">
					</button>
				</div>
				<div class="col-xs-2 col-md-2 column" style="margin-top: -5px;">
					<button type="button" class="btn btn-font-qiehuan" onclick="MfBusFincAppMain_AbstractInfo.getFincMainDetail();"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
				</div>
			</div>
			<div class="row clearfix pledge-rece" id="fincMainInfo">
				<div>
					<table><tbody><tr>
						<td>
							<p class="ptitle">融资金额</p>
							<p class="pvalue">
								<span class="span-value" id="fincAmt"></span>
								<span>&nbsp;万</span>
							</p>
						</td>
						<td>
							<p class="ptitle">融资余额</p>
							<p class="pvalue">
								<span class="span-value" id="fincBal"></span>
								<span>&nbsp;万</span>
							</p>
						</td>
					</tr></tbody></table>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>