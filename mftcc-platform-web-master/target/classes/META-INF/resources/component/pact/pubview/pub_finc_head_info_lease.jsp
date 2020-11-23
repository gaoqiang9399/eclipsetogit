<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<script type="text/javascript">
	var appId= '${param.appId}';
	var pactId= '${param.pactId}';
	var fincId= '${param.fincId}';
	var examEntrance = 'finc';
	$(function(){
		pubFincHeadInfo.init();
	});

</script>
<!--头部主要信息 cus-bo-head-info -->
<div class="row clearfix head-info">
	<!--头像 -->
	<div class="col-xs-3 column text-center head-img ">
		<div>
			<button type="button" class="btn btn-font-pact font-pact-div padding_left_15 ">
				<i class="i i-pact font-icon"></i>
				<div class="font-text-left">借据信息</div>
			</button>
		</div>
		<div class="btn btn-link" onclick="getKindInfo();"></div>
	</div>
	<!--概要信息 -->
	<div class="col-xs-9 column head-content">
		<div class="clearfix">
			<div class="multi-bus pull-right">
				客户共有<span class="moreCnt_apply">申请中业务<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList('apply');"></a> 笔,</span>
				<span class="moreCnt_pact">在履行合同<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList('pact');"></a> 笔</span>
				<span class="moreCnt_finc">, 在履行借据<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('finc');"></a> 笔 </span>
				<span class="moreCnt_assure">, 为他人担保<a class="moreCnt more-assure-count pointer" onclick="getMultiBusList('assure');"></a> 笔 </span>
			</div>
		</div>
		<div class="margin_bottom_5 clearfix">
			<div>
				<div class="bus-more head-title pull-left"></div>
			</div>
		</div>
		<div class="margin_bottom_10">
			<!--法律诉讼0-->
			<dhcc:pmsTag pmsId="loan-law-suit">
				<button id="has-lawsuit" class="btn btn-lightgray btn-view" type="button" onclick="getLawsuitList();">
					<i class="i i-falv"></i><span>法律诉讼</span>
				</button>
			</dhcc:pmsTag>
			<!-- 五级分类 -->
			<dhcc:pmsTag pmsId="loan-five-class">
				<button id="fiveclass" class="btn pact-fiveclass btn-view" type="button" onclick="fiveclassView();">
					<i class="i i-fenlei"></i><span id="fiveclass-i">五级分类</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="examine-result">
			<button id="examineResult" class="btn btn-lightgray btn-view" type="button" onclick="BusExamine.examineDetailResult();">
				<i class="i i-fangdajing" ></i><span id="examineResultTitle">保后检查</span>
			</button>
			</dhcc:pmsTag>
			<button id="recallbase" class="btn btn-danger btn-view hide" type="button" onclick="getRecallInfo();">
				<i class="i i-time"></i><span id="recallspan">待催收</span>
			</button>
			<dhcc:pmsTag pmsId="loan-approve-report">
				<button class="btn btn-view btn-dodgerblue" type="button" onclick="openCustomerCerReport();">
					<i class="i i-ren2"></i><span>认证报告</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-compensatory-info">
				<button class="btn btn-view btn-lightgray" type="button" onclick="MfBusCompensatory.getCompensatoryInfo();">
					<i class="i i-ren2"></i><span>代偿信息</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-recourse-info">
				<button class="btn btn-view btn-lightgray" type="button" onclick="MfBusRecourseApplyDetail.getRecourseConfirmInfo();">
					<i class="i i-ren2"></i><span>追偿信息</span>
				</button>
			</dhcc:pmsTag>
			<!-- 风控查询 （第三方数据）-->
			<dhcc:pmsTag pmsId="cus_risk_query">
				<button id ="riskQuery" class="btn btn-dodgerblue btn-view" type="button" onclick="getRiskReport();">
					<i class="i i-fangdajing"></i><span>风控查询</span>
				</button>
		   </dhcc:pmsTag>
		</div>
		<!--信息查看入口 -->
		<div>
			<table>
				<tr>
					<td>
						<p class="cont-title">借据金额</p>
						<p>
							<span id="fincAmt" class="content-span"></span><span class="unit-span margin_right_25">万</span>
						</p>
					</td>
					<td>
						<p class="cont-title">借据余额</p>
						<p>
							<span id="pactBal" class="content-span"></span><span class="unit-span margin_right_25">万</span>
						</p>
					</td>
					<td>
						<p class="cont-title">逾期天数</p>
						<p>
							<span id="overDays" class="content-span"></span><span class="unit-span">天</span>
						</p>
					</td>
				</tr>
			</table>
		</div>
		<div class="btn-special"></div>
	</div>
</div>
	
