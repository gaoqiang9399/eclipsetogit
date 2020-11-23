<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<script type="text/javascript">
	var appId= '${param.appId}';
	$(function(){
		pubApplyHeadInfo.init();
	});

</script>
<!--头部主要信息 cus-bo-head-info -->
<div class="row clearfix head-info">
	<!--头像 -->
	<div class="col-xs-3 column text-center head-img padding_top_20">
		<div style="position:relative;">
			<button type="button" class="btn btn-font-app padding_left_15 font-app-div">
				<i class="i i-applyinfo font-icon"></i>
				<div class="font-text-left">申请信息</div>
			</button>
		</div>
		<div class="btn btn-link" onclick="getKindInfo();"></div>
	</div>
	<!--概要信息 -->
	<div class="col-xs-9 column head-content">
		<div class="clearfix">
			<div class="multi-bus pull-right">
				客户共有<span>申请中业务<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList('apply');"></a> 笔,</span>
				<span>在履行合同<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList('pact');"></a> 笔</span>
				<span>, 在履行借据<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('finc');"></a> 笔 </span>
			</div>
		</div>
		<div class="margin_bottom_5 clearfix">
			<div >
				<div class="bus-more head-title pull-left"></div>
			</div>
		</div>
		<!--信息查看入口 1   查看风险检查按钮-->
		<div class="margin_bottom_10">
			<dhcc:pmsTag pmsId="apply-risk-result">
				<button class="btn apply-risk-level btn-view" type="button" onclick="busRisk();">
					<i class="i i-risk"></i><span></span>
				</button>
			</dhcc:pmsTag>
			<button class="btn btn-lightgray btn-view" id="surveyReport" type="button">
				<i class="i i-eval1"></i>尽调报告
			</button>
		</div>
		<div>
			<table>
				<tr>
					<td>
						<p class="cont-title">申请金额</p>
						<p><span id="appAmt" class="content-span"></span><span class="unit-span margin_right_25">万</span> </p>
					</td>
					<td>
					</td>
					<td>
						<p class="cont-title">申请期限</p>
						<p><span id="term" class="content-span"></span><span class="unit-span margin_right_25"></span></p>
					</td>
				</tr>
			</table>
		</div>
		<div class="btn-special">
			
		</div>
	</div>
</div>
	
