<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<script type="text/javascript">
	var appId= '${param.appId}';
	var cusBaseType= '${param.cusBaseType}';
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
				客户共有<span class="moreCnt_apply">申请中业务<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList('apply');"></a> 笔,</span>
				<span class="moreCnt_pact">在履行合同<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList('pact');"></a> 笔</span>
				<span class="moreCnt_finc">, 在履行借据<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('finc');"></a> 笔 </span>
				<span class="moreCnt_assure">, 为他人担保<a class="moreCnt more-assure-count pointer" onclick="getMultiBusList('assure');"></a> 笔 </span>
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
					<input id="risklevel" type="hidden" value="-1"/>
				</button>
			</dhcc:pmsTag>
			<%-- <dhcc:pmsTag pmsId="survey-report">
				<button class="btn btn-lightgray btn-view" id="surveyReport" type="button">
					<i class="i i-eval1"></i>尽调报告
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply-approve-report">
				<button class="btn btn-view btn-dodgerblue" type="button" onclick="openCustomerCerReport();">
					<i class="i i-ren2"></i><span>认证报告</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="apply-operator-report">
				<button class="btn btn-view btn-dodgerblue" type="button" onclick="openCustomerOperReport();">
					<i class="i i-ren2"></i><span>运营商报告</span>
				</button>
			</dhcc:pmsTag>
			<s:if test='%{param.cusBaseType=="1"}'>
				<dhcc:pmsTag pmsId="cus-antiFraud-report">
					<button class="btn btn-view btn-forestgreen" type="button" onclick="getCusAntiFraudReport();">
						<i class="i i-ren2"></i><span>反欺诈报告</span>
					</button>
				</dhcc:pmsTag>
			</s:if>
			<!-- 征信查询-->
			<dhcc:pmsTag pmsId="apply_credit_query">
				<button id ="creditQuery" class="btn btn-lightgray btn-view" type="button" style="display:none" onclick="MfCreditQueryRecordInfo.openCreditQuery();">
					<i class="i i-ren2"></i><span>征信查询</span>
				</button>
			</dhcc:pmsTag>
			<!-- 法执情况-->
			<dhcc:pmsTag pmsId="apply-law-enforcement">
				<button id ="lawEnforcementQuery" class="btn btn-lightgray btn-view" type="button" onclick="MfThirdMftccHighcourt.openLawEorcement();">
					<i class="i i-ren2"></i><span>法执情况</span>
				</button>
			</dhcc:pmsTag>
			<!-- 风控查询 （第三方数据）-->
			<dhcc:pmsTag pmsId="cus_risk_query">
				<button id ="riskQuery" class="btn btn-dodgerblue btn-view" type="button" onclick="MfBusApply_DynaDetail.getRiskReport();">
					<i class="i i-fangdajing"></i><span>风控查询</span>
				</button>
			</dhcc:pmsTag>
			<!-- 风控查询 （网智天元）预审批-->
			<dhcc:pmsTag pmsId="examination-approval-report">
				<button class="btn btn-lightgray btn-view" id="examinationReport" type="button">
					<i class="i i-fangdajing"></i>预审批报告
				</button>
			</dhcc:pmsTag>
			<!-- 风控查询 （网智天元）秒批报告-->
			<dhcc:pmsTag pmsId="second-approval-report">
				<button class="btn btn-lightgray btn-view" id="secondReport" type="button">
					<i class="i i-fangdajing"></i>秒批报告
				</button>
			</dhcc:pmsTag> --%>
<!-- 			<!-- 风控查询 （网智天元）车辆实时位置查询-->
<%-- 			<dhcc:pmsTag pmsId="vehicle-real-time-location"> --%>
<!-- 				<button class="btn btn-dodgerblue btn-view" id="vehicleLocation" type="button" onclick="MfBusApply_DynaDetail.vehicleRealTimeLocation();"> -->
<!-- 					<i class="i i-fangdajing"></i>车辆实时位置 -->
<!-- 				</button> -->
<%-- 			</dhcc:pmsTag> --%>
		</div>
		<div>
			<table>
				<tr>
					<td>
						<p class="cont-title">申请金额</p>
						<p><span id="appAmt" class="content-span"></span><span class="unit-span margin_right_25">万</span> </p>
					</td>
					<td>
						<p class="cont-title">申请期限</p>
						<p><span id="term" class="content-span"></span><span class="unit-span margin_right_25"></span></p>
					</td>
					<td>
						<p class="cont-title">申请利率</p>
						<p><span id="fincRate" class="content-span"></span><span class="unit-span"></span></p>
					</td>
				</tr>
			</table>
		</div>
		<div class="btn-special">
			
		</div>
	</div>
</div>
	
