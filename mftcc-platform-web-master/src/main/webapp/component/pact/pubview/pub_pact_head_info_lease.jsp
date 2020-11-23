<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<script type="text/javascript">
	var appId= '${param.appId}';
	var pactId= '${param.pactId}';
	$(function(){
		pubPactHeadInfo.init();
	});

</script>
<!--头部主要信息 cus-bo-head-info -->
<div class="row clearfix head-info">
	<!--头像 -->
	<div class="col-xs-3 column text-center head-img ">
		<div>
			<button type="button" class="btn btn-font-pact font-pact-div padding_left_15 ">
				<i class="i i-pact font-icon"></i>
				<div class="font-text-left">合同信息</div>
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
                <span class="moreCnt_finc_finish">, 已结清担保<a class="moreCnt more-finc-finish-count pointer"
                                                            onclick="getMultiBusList('fincFinish');"></a> 笔 </span>
			</div>
		</div>
		<div class="margin_bottom_5 clearfix">
			<div >
				<div class="bus-more head-title pull-left"></div>
			</div>
		</div>
		<!--信息查看入口 -->
		<div>
			<dhcc:pmsTag pmsId="pact-survey-report">
			<button class="btn btn-lightgray btn-view" id="surveyReport" type="button">
				<i class="i i-eval1"></i>尽调报告
			</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="sign-approve-report">
				<button class="btn btn-view btn-dodgerblue" type="button" onclick="openCustomerCerReport();">
					<i class="i i-ren2"></i><span>认证报告</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="loan-check-car-list">
				<button class="btn btn-view" type="button" id="carCheckFrom" onclick="MfBusPact_DynaDetail.getCarCheckFormList();">
					<i class="i i-ren2"></i><span>复验验车单</span>
				</button>
			</dhcc:pmsTag>
			<%--<div id="putoutSts-div" style="display:none;margin-left: 80%;margin-top: -13%;">
				<div id ="putoutSts-i" class="i i-warehouse cus-type-font">
					<div id="putoutSts" class="type-name-div">放款中</div>
				</div>
			</div>--%>
			<table>
				<tr>
					<td>
						<p class="cont-title">合同金额</p>
						<p><span id="pactAmt" class="content-span"></span><span class="unit-span margin_right_25">万</span> </p>
					</td>
					<td>
						<p class="cont-title">合同期限</p>
						<p><span id="term" class="content-span"></span><span class="unit-span margin_right_25"></span></p>
					</td>
					<%--<td>
						<p class="cont-title">合同利率</p>
						<p><span id="fincRate" class="content-span"></span><span class="unit-span"></span></p>
					</td>--%>
				</tr>
			</table>
		</div>
		<div class="btn-special">
			
		</div>
	</div>
</div>
	
