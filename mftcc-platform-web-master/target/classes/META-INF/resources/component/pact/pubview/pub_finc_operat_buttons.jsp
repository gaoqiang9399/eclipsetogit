<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!--信息登记操作入口 -->
<div class="row clearfix btn-opt-group">
    <div class="col-xs-12 column">
        <div class="btn-group pull-right" id="allButtonDiv"
             style="display: none">
            <!-- <button class="btn btn-opt" onclick="Collateral.editCollateral('pactDeatil');" type="button">
                <i class="i i-huankuan"></i><span>应收账款管理</span>
            </button> -->
            <dhcc:pmsTag pmsId="repay-plan-trial-btn">
                <button class="btn btn-opt-dont" id="repayTrial" onclick="repaymentTrial();"
                        type="button" disabled>
                    <i class="i i-huankuan"></i><span>还款试算</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-return-money">
                <button class="btn btn-opt-dont" id="repay" onclick="repayment();" type="button" disabled>
                    <c:if test="${busModel!='13'}">
                        <i class="i i-huankuan"></i><span>正常还款</span>
                    </c:if>
                    <c:if test="${busModel=='13'}">
                        <i class="i i-huankuan"></i><span>反转让</span>
                    </c:if>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-repayment-registration">
                <button class="btn btn-opt-dont" id="registration"
                        onclick="repaymentRegistration();" type="button" disabled>
                    <i class="i i-huankuan"></i><span>还款登记</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-opt-btn-buyer">
                <button class="btn btn-opt-dont" id="buyerRepay" onclick="buyerRepayment();"
                        type="button" disabled>
                    <i class="i i-huankuan"></i><span>买方付款</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-opt-btn-tail">
                <button class="btn btn-opt-dont" id="tailPayment" onclick="tailPayment();"
                        type="button" disabled>
                    <i class="i i-huankuan"></i><span>尾款解付</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-early-return-money">
                <button class="btn btn-opt-dont" id="prerepay"
                        onclick="preRepaymentJsp();" type="button" disabled>
                    <i class="i i-huankuan"></i><span>提前还款</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-fnc-fee-plan">
                <button class="btn btn-opt" id="fincFeePlan"
                        onclick="FeeCollect.doFincFeePlan();" type="button">
                    <i class="i i-huankuan"></i><span>生成费用计划</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-after-collect-fee">
                <button class="btn btn-opt" id="collectFee"
                        onclick="FeeCollect.doFincFeeCollect();" type="button">
                    <i class="i i-huankuan"></i><span>收费</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-opt-btn_refundfee">
                <button class="btn btn-opt-dont" id="refundFee"
                        onclick="mfBusRefundFeeJsp();" type="button" disabled>
                    <i class="i i-huankuan"></i><span>退费</span>
                </button>
            </dhcc:pmsTag>

            <%--<dhcc:pmsTag pmsId="loan-opt-btn-compensatory">--%>
                <%--<button class="btn btn-opt" id="compensatory"--%>
                        <%--onclick="MfBusCompensatory.getCompensatoryApply();" type="button">--%>
                    <%--<i class="i i-huankuan"></i><span>代偿申请</span>--%>
                <%--</button>--%>
                <%--<button class="btn btn-opt" id="compensatoryConfirm"--%>
                        <%--onclick="MfBusCompensatory.getCompensatoryConfirm();"--%>
                        <%--style="display: none;" type="button">--%>
                    <%--<i class="i i-huankuan"></i><span>代偿确认</span>--%>
                <%--</button>--%>
            <%--</dhcc:pmsTag>--%>
                <%--<button class="btn btn-opt" id="recourse"
                        onclick="MfBusRecourseApplyDetail.getRecourseApply();"
                        type="button">
                    <i class="i i-huankuan"></i><span>追偿申请</span>
                </button>--%>
                <button class="btn btn-opt" id="recourseConfirm"
                        onclick="MfBusRecourseApplyDetail.getRecourseConfirm();"
                        style="display: none;" type="button">
                    <i class="i i-huankuan"></i><span>追偿收回</span>
                </button>

            <dhcc:pmsTag pmsId="loan-opt-btn-register">
                <button class="btn btn-opt" id="recourse"
                        onclick="MfBusRecourseApplyDetail.getRecourseApplyRec();"
                        type="button">
                    <i class="i i-huankuan"></i><span>追偿登记</span>
                </button>
            </dhcc:pmsTag>

            <dhcc:pmsTag pmsId="loan-opt-btn_liXiRepay">
                <button class="btn btn-opt-dont" id="LiXiRepay"
                        onclick="LiXiRepaymentJsp();" type="button" disabled>
                    <i class="i i-huankuan"></i><span>展期前收息</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-check-btn">
                <button class="btn btn-opt" id="loanAfterExamine"
                        onclick="BusExamine.loanAfterExamine();" type="button">
                    <i class="i i-fangdajing"></i><span>贷后检查</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-collection-btn">
                <button class="btn btn-opt" id="recallRegist"
                        onclick="recallRegist();" type="button">
                    <i class="i i-time"></i><span>催收登记</span>
                </button>
            </dhcc:pmsTag>

            <dhcc:pmsTag pmsId="loan-five-class-btn">
                <button class="btn btn-opt" id="fiveclassUpdate"
                        style="display: none" onclick="fiveclassUpdate('五级分类');" type="button">
                    <i class="i i-fenlei"></i><span>五级分类</span>
                </button>
                <button class="btn btn-opt" id="fiveclassInsert"
                        onclick="fiveclassInsert('五级分类');" type="button">
                    <i class="i i-fenlei"></i><span>五级分类</span>
                </button>
            </dhcc:pmsTag>


            <dhcc:pmsTag pmsId="loan-file-print-btn">
                <button class="btn btn-opt" id="filePrint" onclick="filePrint();"
                        type="button">
                    <i class="i i-x"></i><span>文件打印</span>
                </button>
            </dhcc:pmsTag>
            <%--<dhcc:pmsTag pmsId="loan-file-filing-btn">
                <button class="btn btn-opt" id="fileArchive"
                        onclick="fileArchive();" type="button">
                    <i class="i i-guidang"></i><span>文件归档</span>
                </button>
            </dhcc:pmsTag>--%>
            <dhcc:pmsTag pmsId="loan-datadownload-btn">
                <button class="btn btn-opt" id="dataDownload"
                        onclick="dataDownload();" type="button">
                    <i class="i i-guidang"></i><span>资料下载</span>
                </button>
            </dhcc:pmsTag>
            <dhcc:pmsTag pmsId="loan-lawsuit-btn">
                <button class="btn btn-opt" onclick="lawsuit();" type="button"
                        id="lawsuitAdd">
                    <i class="i i-falv"></i><span>法律诉讼</span>
                </button>
            </dhcc:pmsTag>

            <dhcc:pmsTag pmsId="loan-extension-btn">
                <button class="btn btn-opt" type="button"
                        onclick="MfBusExtensionCommon.ExtensionApply();"
                        id="extensionApplyButton">
                    <i class="i i-bi1"></i><span>展期申请</span>
                </button>
            </dhcc:pmsTag>


            <dhcc:pmsTag pmsId="loan-extension-disagree-btn">
                <button class="btn btn-opt" type="button"
                        onclick="inputDisagreeBuss.extensionDisagreeBuss();"
                        id="extensionDisagreeButton">
                    <i class="i i-bi1"></i><span>终止本次展期</span>
                </button>
            </dhcc:pmsTag>


            <dhcc:pmsTag pmsId="risk-registration-btn">
                <button class="btn btn-opt" type="button"
                        onclick="riskRegistration();" id="riskRegistrationButton">
                    <i class="i i-bi1"></i><span>风险登记</span>
                </button>
            </dhcc:pmsTag>

            <div class="btn-group more-lawsuit" id="moreDiv">
                <button type="button" class="btn btn-opt  dropdown-toggle"
                        data-toggle="dropdown" id="moreButton">
                    更多<span class="caret"></span>
                </button>
                <ul class="dropdown-menu btn-opt pull-right" role="menu" id="moreUl">
                </ul>
            </div>
        </div>
    </div>
</div>

