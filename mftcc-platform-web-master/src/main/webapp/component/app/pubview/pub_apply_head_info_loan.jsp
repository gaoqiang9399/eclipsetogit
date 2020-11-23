<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	var appId= '${param.appId}';
	var busModel= '${busModel}';
	var appIdOld= '';
	var cusBaseType= '${param.cusBaseType}';
    var primaryAppId = '${param.primaryAppId}';
    var appSts= '${param.appSts}';
    var applyProcessId= '${param.applyProcessId}';
    var primaryApplyProcessId= '${param.primaryApplyProcessId}';
    var reconsiderId = '${reconsiderId}';// 否决复议审批业务编号("reconsider" + appId)
    var OPEN_APPROVE_HIS = '${OPEN_APPROVE_HIS}';// 是否展开审批历史
	$(function(){
		pubApplyHeadInfo.init();
        pubInitEvalInfo.initEval(cusNo,'1',appId,'4','1','cusCreditEvalRating-span','cusCreditBusEvalRating-button');
		if(primaryAppId != ""){
            showWkfFlowVertical1();
		}
	});

    //初始化审批历史按钮
    function showWkfFlowVertical1(){
        $.ajax({
            async: false,
            url:webPath+"/mfBusApplyHis/getByAppId",
            type:'post',
            data:{appId:primaryAppId},
            dataType:'json',
            success:function(data){
                var flag = data.flag;
                if(flag == "success"){
                    $("#applyJsyApproveHis").removeClass("btn-lightgray");// 去掉灰色样式
                    $("#applyJsyApproveHis").addClass("btn-forestgreen");// 添加绿色样式
                }
            }
        });
    }


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
				客户共有
				<span class="moreCnt_apply">申请中项目<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList('apply');"></a> 笔,</span>
				<span class="moreCnt_finc"> 在保项目<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('pact');"></a> 笔 </span>
				<span class="moreCnt_finc_finish">, 已完成担保<a class="moreCnt more-finc-finish-count pointer" onclick="getMultiBusList('fincFinish');">${dataMap.moreFincFinishCount}</a> 笔 </span>
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
		<!--信息查看入口 1   查看风险检查按钮-->
		<div class="margin_bottom_10">
			<dhcc:pmsTag pmsId="apply-risk-result">
				<input id="risklevel" type="hidden" value="-1"/>
				<button class="btn apply-risk-level btn-view" type="button" onclick="busRisk();">
					<i class="i i-risk"></i><span></span>
				</button>
			</dhcc:pmsTag>
			<dhcc:pmsTag pmsId="survey-report">
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
			<dhcc:pmsTag pmsId="cus-eval-credit-rating">
				<button id="cusCreditBusEvalRating-button" class="btn btn-lightgray btn-view " title="详审评级" type="button" onclick="getEvalDetailResult('0');">
					<i class="i i-eval1"></i><span id="cusCreditEvalRating-span">详审评级</span>
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
			<c:if test='${param.cusBaseType=="1"}'>
				<dhcc:pmsTag pmsId="cus-antiFraud-report">
					<button class="btn btn-view btn-forestgreen" type="button" onclick="getCusAntiFraudReport();">
						<i class="i i-ren2"></i><span>反欺诈报告</span>
					</button>
				</dhcc:pmsTag>
			</c:if>
			<dhcc:pmsTag pmsId="cus-risk-review">
				<button class="btn btn-lightgray btn-view"  id="riskReview" type="button">
					<i class="i i-eval1"></i><span>风险审查</span>
				</button>
			</dhcc:pmsTag>
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
			<dhcc:pmsTag pmsId="cus_risk_management_query">
				<button id ="riskQueryManagement" class="btn btn-forestgreen btn-view" type="button" onclick="MfBusApply_DynaDetail.getRiskManagement();">
					<i class="i i-fangdajing"></i><span>关联人联网查询</span>
				</button>
			</dhcc:pmsTag>
            <dhcc:pmsTag pmsId="apply-esign-history">
                <button id ="applyEsignHistory" class="btn btn-dodgerblue btn-view" type="button" onclick="applyEsignHistory();">
                    <i class="i i-fangdajing"></i><span>电签记录查询</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="apply-old-bus-info">
                <button id ="applyOldBusInfo" class="btn btn-dodgerblue btn-view" type="button" onclick="applyOldBusInfo();" style="display: none;">
                    <i class="i i-fangdajing"></i><span>原业务信息</span>
                </button>
            </dhcc:pmsTag>
			<!-- 风控查询 （第三方数据）-->
			<%-- <dhcc:pmsTag pmsId="cus_risk_query">
				<button id ="riskQuery" class="btn btn-dodgerblue btn-view" type="button" onclick="MfBusApply_DynaDetail.getRiskReport();">
					<i class="i i-fangdajing"></i><span>风控查询</span>
				</button>
			</dhcc:pmsTag>--%>
			<!-- 审批视角可见 -->
				<dhcc:pmsTag pmsId="approval-analysis-table">
					<button id ="analysisTable" class="btn btn-dodgerblue btn-view" type="button" onclick="MfBusApply_DynaDetail.analysisTable();" style="display: none;">
						<i class="i i-compareVS"></i><span>历史数据对比</span>
					</button>
				</dhcc:pmsTag>
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
				</tr>
			</table>
		</div>
		<div class="btn-special">
			
		</div>
	</div>
</div>
	
