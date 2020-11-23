<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 【此页面供“借款客户”使用，即CusBaseType为1和2的类型】 -->

<!--头部主要信息 -->
<script type="text/javascript">
    $(function(){
        pubInitEvalInfo.initEval(cusNo,'1',creditAppId,'4','1','cusCreditDebtEvalRating-span','cusCreditDebtEvalRating-button');
    });
</script>
<div class="row clearfix head-info">
	<!--头像 -->
    <div class="col-xs-3 column text-center head-img ">
        <div>
            <button type="button" class="btn btn-font-pact font-pact-div padding_left_15 ">
                <i class="i i-pact font-icon"></i>
                <div class="font-text-left">授信信息</div>
            </button>
        </div>
        <div class="btn-link">${creditName}</div>
    </div>
	<!--概要信息 -->
	<div class="col-xs-9 column head-content">
		<div class="clearfix">
<!-- 			<div class="multi-bus pull-right"> -->
<%-- 				客户共有<span class="moreCnt_apply">申请中业务<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList('apply');">${dataMap.moreApplyCount}</a> 笔, --%>
<%-- 				</span> <span class="moreCnt_pact">在履行合同<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList('pact');">${dataMap.morePactCount}</a> 笔 --%>
<%-- 				</span> <span class="moreCnt_finc">, 在履行借据<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('finc');">${dataMap.moreFincCount}</a> 笔 --%>
<%-- 				</span> <span class="moreCnt_assure">, 为他人担保<a class="moreCnt more-assure-count pointer" onclick="getMultiBusList('assure');">${dataMap.moreAssureCount}</a> 笔 --%>
<!-- 				</span> -->
<!-- 			</div> -->
		</div>
		<div class="row clearfix">
			<div class="col-xs-10 column">
				<div class="margin_bottom_5">
					<span class="cus head-title">${mfCusCustomer.cusName}</span>
				</div>
				<!--信息查看入口 -->
				<div class="margin_bottom_10">
                    <dhcc:pmsTag pmsId="credit-risk-check">
                        <button id="creditRiskCheck-button" class="btn btn-lightgray btn-view " type="button" onclick="getCreditRiskCheckResult('0');">
                            <i class="i i-eval1"></i><span id="creditRiskCheck-span">未检查</span>
                        </button>
                    </dhcc:pmsTag>
                    <dhcc:pmsTag pmsId="cus-eval-debts-rating">
                        <button id="cusCreditDebtEvalRating-button" class="btn btn-lightgray btn-view " type="button" onclick="getEvalDetailResult('0');">
                            <i class="i i-eval1"></i><span id="cusCreditDebtEvalRating-span">详审评级</span>
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
                    <%--<dhcc:pmsTag pmsId="cus-auth-credit">--%>
                        <%--<button id="cusCredit-button" class="btn btn-lightgray btn-view" title="授信总额" type="button" onclick="">--%>
                            <%--<i class="i i-credit"></i><span class="creditBus">未授信</span>--%>
                        <%--</button>--%>
                    <%--</dhcc:pmsTag>--%>
					<dhcc:pmsTag pmsId="cus-risk-level">
                        <c:choose>
                            <c:when test="${dataMap.riskLevel > -1}">
                                <button class="btn risklevel${dataMap.riskLevel} btn-view" type="button" onclick="cusRisk();">
                                    <i class="i i-risk"></i><span>${dataMap.riskName}</span>
                                </button>
                            </c:when>
                            <%--<c:otherwise>--%>
                                <%--<button class="btn btn-lightgray btn-view" type="button">--%>
                                    <%--<i class="i i-risk"></i><span>客户准入</span>--%>
                                <%--</button>--%>
                            <%--</c:otherwise>--%>
                        </c:choose>
					</dhcc:pmsTag>
				</div>

			</div>
            <div class="col-xs-2 column">
                <div class="i i-warehouse cus-type-font">
                    <div class="type-name-div" id="creditStsName">${creditStsName}</div>
                </div>
            </div>
		</div>
        <div>
            <table>
                <tr>
                    <td>
                        <p class="cont-title">授信总额</p>
                        <p><span id="creditSum" class="content-span">${creditSum}</span><span class="unit-span margin_right_25">万</span> </p>
                    </td>
                    <td>
                        <p class="cont-title">授信余额</p>
                        <p><span id="authBal" class="content-span">${authBal}</span><span class="unit-span margin_right_25">万</span></p>
                    </td>
                    <td>
                        <p class="cont-title">授信期限</p>
                        <p><span id="creditTerm" class="content-span">${creditTermShow}</span><span class="unit-span margin_right_25"></span></p>
                    </td>
                </tr>
                <tr>
                    <td>
                        <p><span id="tips" class="content-span" style="color: red">${tips}</span> </p>
                    </td>
                </tr>
            </table>
        </div>
	</div>
</div>
