<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
				<div class="font-text-left"  id = "btnTitle">合同信息</div>
			</button>
		</div>
		<div class="btn btn-link" onclick="getKindInfoNew('${param.appId}');"></div>
	</div>
	<!--概要信息 -->
	<div class="col-xs-9 column head-content">
		<div class="clearfix">
			<div class="multi-bus pull-right">
				客户共有<span class="moreCnt_apply">申请中项目<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList('apply');"></a> 笔,</span>
				<span class="moreCnt_finc"> 在保项目<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('pact');"></a> 笔 </span>
				<%--<span class="moreCnt_pact">,在保项目<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList('pact');"></a> 笔</span>--%>
				<%--<span class="moreCnt_repay">, 已还款<a class="moreCnt more-repay-count pointer" onclick="getMultiBusList('repay');"></a> 次 </span>--%>
				<%--				<span class="moreCnt_assure">, 为他人担保<a class="moreCnt more-assure-count pointer" onclick="getMultiBusList('assure');"></a> 笔 </span>--%>
				<span class="moreCnt_finc_finish">, 已完成<a class="moreCnt more-finc-finish-count pointer"
														  onclick="getMultiBusList('fincFinish');"></a> 笔 </span>
				<c:if test="${busModel != null && busModel == '12'}">
					<span class="moreCnt_early_warning">, 预警项目<a class="moreCnt more-early-warning-count pointer" onclick="getMultiBusList('earlyWarning');"></a> 笔 </span>
				</c:if>
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
				<i class="i i-eval1"></i>
				<c:choose>
					<c:when test="${busModel == '12'}">
						项目申报表
					</c:when>
					<c:otherwise>
						尽调报告
					</c:otherwise>
				</c:choose>
			</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="sign-approve-report">
				<button class="btn btn-view btn-dodgerblue" type="button" onclick="openCustomerCerReport();">
					<i class="i i-ren2"></i><span>认证报告</span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="cus-pact-risk-review">
				<button class="btn btn-lightgray btn-view"  id="riskReview" type="button">
					<i class="i i-eval1"></i><span>风险审查</span>
				</button>
			</dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-esign-history">
                <button id ="loanEsignHistory" class="btn btn-dodgerblue btn-view" type="button" onclick="loanEsignHistory();">
                    <i class="i i-fangdajing"></i><span>电签记录查询</span>
                </button>
            </dhcc:pmsTag>
			<!-- 贷后检查 -->
			<dhcc:pmsTag pmsId="examine-result">
				<button id="examineResult" class="btn btn-lightgray btn-view" type="button" onclick="BusExamine.examineDetailResult();">
					<i class="i i-fangdajing"></i><span>保后检查</span>
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
						<p><span id="tips" class="content-span" style="color: red"></span> </p>
					</td>
					<td>
						<p class="cont-title">合同期限</p>
						<p><span id="term" class="content-span"></span><span class="unit-span margin_right_25"></span></p>
					</td>
					<%--<td>--%>
						<%--<p class="cont-title">合同利率</p>--%>
						<%--<p><span id="fincRate" class="content-span"></span><span class="unit-span"></span></p>--%>
					<%--</td>--%>
				</tr>
			</table>
		</div>
		<div class="btn-special">
			
		</div>
	</div>
</div>
	
