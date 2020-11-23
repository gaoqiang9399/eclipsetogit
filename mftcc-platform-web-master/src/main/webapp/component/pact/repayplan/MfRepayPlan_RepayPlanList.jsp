<%@ page language="java" import="java.util.*,com.google.gson.Gson" pageEncoding="UTF-8"
		 contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String appId = (String) request.getAttribute("appId");
	String fincId = (String) request.getAttribute("fincId");
	String pactId = (String) request.getAttribute("pactId");
	String cusNo = (String) request.getAttribute("cusNo");
	String repayJsonString = new Gson().toJson(request.getAttribute("repayPlanList"));
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	<link rel="stylesheet" href="${webPath}/component/pact/css/MfRepayPlan_RepayPlanList.css"/>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfRepayPlan_RepayPlanList.js"></script>
	<script type="text/javascript" src="${webPath}/component/pact/js/MfRepayPlan_RepayPlan.js"></script>
    <script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	<script type="text/javascript" src="${webPath}/UIplug/webuploader/js/webuploader.js"></script>
	<link rel="stylesheet" href="${webPath}/component/pfs/css/CusFincMainList.css?v=${cssJsVersion}"/>
	<link rel="stylesheet" href="${webPath}/UIplug/webuploader/css/webuploader.css?v=${cssJsVersion}">
	<style type="text/css">
		.webuploader-pick {
			width: 100% !important
		}

		.footer_loader {
			z-index: 50
		}
	</style>
	<script type="text/javascript">
        var appId = '<%=appId%>';
        var fincId = '<%=fincId%>';
        var pactId = '<%=pactId%>';
        var cusNo = '<%=cusNo%>';
        var feePower = "3";
        var putOutChargeIntstFlag = '${mfBusFincAppChild.putOutChargeIntstFlag}';
        var beginDate = '${mfBusPact.beginDate}';
        var borrowCodeType = '${borrowCodeType}';//借款编码开启状态
        var projectName = '${projectName}';
        var authCycle = '${mfBusPact.authCycle}';
        var intstBeginDateStar = '${ mfBusFincAppChild.intstBeginDate}';
        //收取前N期利息总额 （金额合计）
        var chargeInterestSum = '${ mfBusFincAppChild.chargeInterestSum}';
        //收取前N期费用总额
        var chargeFeeSum = '${mfBusFincAppChild.chargeFeeSum}';
        var nodeNo = '${nodeNo}';
        var lastReturnDate = '${lastReturnDate}';
        var delayDateMap = ${delayDateMap};
        var creditBeginDate = '${creditBeginDate}';
        var creditEndDate = '${creditEndDate}';
        var zgeSignDate = '${zgeSignDate}';
        $(function () {
            var repayPlanList = <%=repayJsonString%>;
            $("input[name=creditBeginDate]").val(creditBeginDate);
            $("input[name=creditEndDate]").val(creditEndDate);
            $("input[name=zgeSignDate]").val(zgeSignDate);
            $("#planListSize").val(repayPlanList.length);
            var planListSize = repayPlanList.length;
            var lastPlanEndRate = "#tdPlanEndDate_" + planListSize;//最后一期结束日期
            var lastRepayPrcp = "#tdRepayPrcp_" + planListSize;//最后一期应还本金
            var lastRepayIntst = "#tdRepayIntst_" + planListSize;//最后一期应还利息
            $(lastPlanEndRate).removeAttr('ondblclick');
            $(lastRepayPrcp).removeAttr('ondblclick');
            if (putOutChargeIntstFlag == "1") {//显示放款时收取利息
                $("#putoutChargeIntstFormat").show();
            }
            $("input[name=beginDate]").val(intstBeginDateStar);
            if (chargeInterestSum * 1 <= 0.00 && chargeFeeSum * 1 <= 0.00) {
                $("input[name=chargeFeeAndInterSumFormat]").parents("tr").hide();
            }
            //
            <!--还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑 -->
            var mfBusEditorRepayplanFlag = '${mfBusEditorRepayplanFlag}';
            if (mfBusEditorRepayplanFlag == 0) {
                $("input[name=periodAdjust]").parents('tr').hide();
            }
            repayPlan_List.init();
            changeMfFincAppValue(delayDateMap);//固定还款日顺延日期
            MfRepayPlan_RepayPlan.init();
        });

	</script>
</head>
<dhcc:pmsTag pmsId="repay-plan-template-download">
	<ul class="tab-ul" style="text-align:left;">
		<li onclick="MfRepayPlan_RepayPlan.exportExcel();">
			<span><font size="3">还款计划表模板下载</font>></span> <i class="i i-xiazai"></i>
		</li>
	</ul>
</dhcc:pmsTag>
<body class="overflowHidden bg-white">
<div class="scroll-content">
	<div class="container">
		<div class="row repay">
			<%--<div class="col-md-6 repayleft" id="pact_info">--%>
				<%--<dhcc:propertySeeTag property="formrepayplanlist0001" mode="query"/>--%>
			<%--</div>--%>
			<%--<div class="col-md-6 repayright">--%>
                <%--<dhcc:bootstarpTag property="formrepayplanlist0002" mode="query"/>--%>
			<%--</div>--%>
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="repayPlanInsertForm" theme="simple" name="cms_form" action="${webPath}/mfBusFincApp/insertAjax">
							<dhcc:bootstarpTag property="formrepayplanlist0002" mode="query"/>
						</form>
					</div>
				</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="list-table">
					<div class="content margin_left_15 collapse in" id="busfee"
						 name="busfee">
						<form id="repayPlanForm">
							<table id="repayPlan_tablist" width="100%" border="0" align="center"
								   cellspacing="1" class="ls_list" title="">
								<thead>
								<tr>
									<th scope="col" align="center" name="termNum" sorttype="0">期号</th>
									<th scope="col" align="center" name="planBeginDate" hidden sorttype="0">开始日</th>
									<th scope="col" align="center" name="expiryIntstDate" hidden sorttype="0">结息日</th>
									<th scope="col" align="center" name="planEndDate" sorttype="0">还款日期</th>
									<%--<th scope="col" align="center" name="" sorttype="0">还款日期</th>--%>
									<th scope="col" align="center" name="repayPrcp" sorttype="0">应还本金(元)</th>
									<th scope="col" align="center" name="repayIntst" sorttype="0" hidden>应还利息(元)</th>
									<th scope="col" align="center" name="feeSum" sorttype="0" hidden >费用总额(元)</th>
									<th scope="col" align="center" name="repayPrcpIntstSum" sorttype="0" hidden>应还总额(元)</th>
									<th scope="col" align="center" name="repayPrcpBal" sorttype="0">期末本金余额(元)</th>
								</tr>
								</thead>
								<tbody id="repayPlan_tab">
								<c:forEach items="${repayPlanList}" varStatus="i" var="repayPlan">
									<tr id="tr_${repayPlan.termNum}">
										<td align="center">
											<span class="tab_span"
												  id="spanTermNum_${repayPlan.termNum}">${repayPlan.termNum}</span>
											<input type="text" name="termNum${repayPlan.termNum}"
												   id="termNum_${repayPlan.termNum}" class="repayInput"
												   value="${repayPlan.termNum}">
										</td>
										<td align="center" hidden>
											<span class="tab_span"
												  id="spanPlanBeginDate_${repayPlan.termNum}">${repayPlan.planBeginDateFormat}</span>
											<input type="text" name="planBeginDate${repayPlan.termNum}"
												   id="planBeginDate_${repayPlan.termNum}" class="repayInput"
												   value="${repayPlan.planBeginDateFormat}" datatype="6">
										</td>


										<td align="center" hidden>
								   <span class="tab_span"
										 id="spanExpiryIntstDate_${repayPlan.termNum}">${repayPlan.expiryIntstDateFormat}</span>
											<input type="text" name="expiryIntstDate${repayPlan.termNum}"
												   id="expiryIntstDate_${repayPlan.termNum}"
												   class="repayInput datelogo BOTTOM_LINE"
												   value="${repayPlan.expiryIntstDateFormat}" datatype="6" mustinput="1"
												   maxlength="10">
										</td>


										<td align="center" id="tdPlanEndDate_${repayPlan.termNum}"
											ondblclick="repayPlan_List.showEndDateInputValue(this)">
											<span class="tab_span"
												  id="spanPlanEndDate_${repayPlan.termNum}">${repayPlan.planEndDateFormat}</span>
											<input type="text" name="planEndDate${repayPlan.termNum}"
												   id="planEndDate_${repayPlan.termNum}"
												   class="repayInput datelogo BOTTOM_LINE"
												   value="${repayPlan.planEndDateFormat}"
												   onfocus="fPopUpCalendarDlg({choose:repayPlan_List.changeEndDateValue});"
												   onmousedown="enterKey()" onkeydown="enterKey();" datatype="6"
												   mustinput="1" maxlength="10">
										</td>

										<td id="tdRepayPrcp_${repayPlan.termNum}"
											ondblclick="repayPlan_List.showRepayPrcpInputValue(this)">
											<span class="tab_span money_right"
												  id="spanRepayPrcp_${repayPlan.termNum}">${repayPlan.repayPrcpFormat}</span>
											<input type="text" name="repayPrcp${repayPlan.termNum}"
												   id="repayPrcp_${repayPlan.termNum}"
												   class="repayInput datelogo BOTTOM_LINE"
												   value="${repayPlan.repayPrcp}"
												   onblur="repayPlan_List.changeRepayPrcpValue(this);"
												   onkeyup="value=value.replace(/[^\d.]/g,'')">
										</td>
										<td id="tdRepayIntst_${repayPlan.termNum}"
											ondblclick="repayPlan_List.showRepayIntsInputValue(this)" hidden>
											<span class="tab_span money_right"
												  id="spanRepayIntst_${repayPlan.termNum}">${repayPlan.repayIntstFormat}</span>
											<input type="text" name="repayIntst${repayPlan.termNum}"
												   id="repayIntst_${repayPlan.termNum}"
												   class="repayInput datelogo BOTTOM_LINE"
												   value="${repayPlan.repayIntst}"
												   onblur="repayPlan_List.changeRepayIntstValue(this);"
												   onkeyup="value=value.replace(/[^\d.]/g,'')">
										</td>
										<td hidden>
											<span class="tab_span money_right"
												  id="spanFeeSum_${repayPlan.termNum}">${repayPlan.feeSumFormat}</span>
											<input type="text" name="feeSum${repayPlan.termNum}"
												   id="feeSum_${repayPlan.termNum}" class="repayInput"
												   value="${repayPlan.feeSum}">
										</td>
										<td hidden>
											<span class="tab_span money_right"
												  id="spanRepayPrcpIntstSum_${repayPlan.termNum}">${repayPlan.repayPrcpIntstSumFormat}</span>
											<input type="text" name="repayPrcpIntstSum${repayPlan.termNum}"
												   id="repayPrcpIntstSum_${repayPlan.termNum}" class="repayInput"
												   value="${repayPlan.repayPrcpIntstSum}">
										</td>
										<td>
											<span class="tab_span money_right"
												  id="spanRepayPrcpBalAfter_${repayPlan.termNum}">${repayPlan.repayPrcpBalAfterFormat}</span>
											<input type="text" name="repayPrcpBalAfter${repayPlan.termNum}"
												   id="repayPrcpBalAfter_${repayPlan.termNum}" class="repayInput"
												   value="${repayPlan.repayPrcpBalAfter}">
										</td>
										<!--隐藏域   本期提前收取的利息-->
										<td align="center" style="display:none">
											<input type="text" name="repayIntstOrig${repayPlan.termNum}"
												   id="repayIntstOrig_${repayPlan.termNum}"
												   value="${repayPlan.repayIntstOrig}">
										</td>

										<td align="center" style="display:none">
											<input type="text" name="outFlag${repayPlan.termNum}"
												   id="outFlag_${repayPlan.termNum}" value="${repayPlan.outFlag}">
										</td>

										<td align="center" style="display:none">
											<input type="text" name="currentNum${repayPlan.termNum}"
												   id="currentNum_${repayPlan.termNum}" value="${repayPlan.currentNum}">
										</td>
										<td align="center" style="display:none">
								   <span class="tab_span"
										 id="spanRepayDate_${repayPlan.termNum}">${repayPlan.planEndDateFormat}</span>
											<input type="text" name="repayDate${repayPlan.termNum}"
												   id="repayDate_${repayPlan.termNum}"
												   class="repayInput datelogo BOTTOM_LINE"
												   value="${repayPlan.planEndDate}" datatype="6" mustinput="1"
												   maxlength="10">
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>
				<div class="col-md-12">
					<div class="row repayfee" style="border:none;">
						<%@ include file="/component/include/biz_node_plugins.jsp" %>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div style="display:none;">
	<input type="hidden" id="fincId" value="${mfBusFincAppChild.fincId}"><!--子表借据号-->
	<input type="hidden" id="repayType" value="${mfBusFincAppChild.repayType}"><!--还款方式 -->
	<input type="hidden" id="beginDateHidden" value="${mfBusFincAppChild.intstBeginDate}"><!--默认的发放日期 -->
	<input type="hidden" id="endDateHidden" value="${mfBusFincAppChild.intstEndDate}"><!--默认到期日期  -->
	<input type="hidden" id="planListSize" value=""><!--还款计划列表期数-->
	<input type="hidden" id="putoutAmtHidden" value="${mfBusFincAppChild.putoutAmt}"><!--借据金额 -->
	<input type="hidden" id="putoutAmtRealHidden" value="${mfBusFincAppChild.putoutAmtReal}"/><!-- 实际发放金额 -->
	<input type="hidden" id="fincRate" value="${mfBusFincAppChild.fincRate}"><!--年利率 -->
	<input type="hidden" id="input_hide_intst_modify" value="0"/><!--判断是否先修改本金    状态 0：未修改   1：已修改 -->
	<input type="hidden" id="repay_fee_sum" value="${mfBusFincAppChild.chargeFeeSum}"/><!--  放款前收取费用的总和   上收息-->
	<input type="hidden" id="repay_intst_sum" value="${mfBusFincAppChild.chargeInterestSum}"/><!-- 放款前收取利息的总和 上收费 -->
	<input type="hidden" id="put_out_feesum" value="${mfBusFincAppChild.putOutChargeFee}"/><!-- 放款时收取的 一次性费用  -->
	<input type="hidden" id="putout_charge_Intst_hidden" value="${mfBusFincAppChild.putOutChargeIntst}"/>
	<!-- 放款时收取利息 （存在固定还款日 ）-->

	<!--系统参数  -->
	<input type="hidden" id="rate_type" value="${mfBusAppKind.rateType}"/><!-- 利率类型 -->
	<input type="hidden" id="multipleLoanPlanMerge" value="${mfBusAppKind.multipleLoanPlanMerge}"/>
	<!-- 多次放款还款计划是否合并  1-启用、0-禁用 -->
	<input type="hidden" id="interestCollectType" value="${mfBusAppKind.interestCollectType}"/>
	<!-- 利息收息方式：1-上收息 2-下收息 -->
	<input type="hidden" id="feeCollectWay" value="${mfBusAppKind.feeCollectWay}"/><!-- 费用收息方式：1-上收费 2-下收费 -->
	<input type="hidden" id="repayDateSet" value="${mfBusAppKindShow.repayDateSet}"/><!--还款日设置：1-贷款发放日 2-月末 3-固定还款日  -->
	<input type="hidden" id="yearDays" value="${mfBusAppKind.yearDays}"/><!--计息天数  -->
	<input type="hidden" id="normCalcType" value="${mfBusAppKind.normCalcType}"/>
	<!-- 利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息 -->
	<input type="hidden" id="secondNormCalcType" value="${mfBusAppKind.secondNormCalcType}"/>
	<!--不足期计息类型    	1-按月计息|2-按实际天数计息  -->
	<input type="hidden" id="returnPlanPoint" value="${mfBusAppKind.returnPlanPoint}"/><!--保留小数位数 	2-两位|1-一位|0-不保留  -->
	<input type="hidden" id="returnPlanRound" value="${mfBusAppKind.returnPlanRound}"/><!--还款计划舍入方式 2-四舍五入  -->
	<input type="hidden" id="instCalcBase" value="${mfBusAppKind.instCalcBase}"/><!--还款利息计算基数：1-贷款金额、2-贷款余额  -->
	<input type="hidden" id="preInstCollectType" value="${mfBusAppKindShow.preInstCollectType}"/>
	<!--预先支付利息收取方式：1-合并第一期，2-独立一期  3-放款时收取，-->
	<input type="hidden" id="repayDateDef" value="${mfBusAppKindShow.repayDateDef}"/>
	<!--默认还款日 当repay_date_set为固定还款日时,该字段才有值  -->
	<input type="hidden" id="rulesNo" value="${mfBusAppKindShow.rulesNo}"/><!--默认还款日 当repay_date_set为固定还款日时,该字段才有值  -->
	<input type="hidden" id="putOutChargeIntstFlag" value="${mfBusFincAppChild.putOutChargeIntstFlag}"/>
	<!--0 表示不是放款时收取  1 表示 是固定还款日 且是放款时收取  -->
	<input type="hidden" id="mfBusEditorRepayplanFlag" value="${mfBusEditorRepayplanFlag}"/>
	<!--还款计划是否能够进行编辑  0 不能编辑 1能够进行编辑 -->
</div>
<div class="formRowCenter" style="text-align: left;position: fixed">
	<div class="col-xs-2" id="newParm-div" style="margin-left:25%;">
		<dhcc:pmsTag pmsId="upload-repay-plan">
			<div id="uploader" class="wu-example">
				<div id="thelist" class="cb-upload-div input-group input-group-lg">
					<input name="uploadFile" readonly="readonly" type="text" class="form-control" style="display:none;">
					<span style="width:0px;height:0px;padding:9px 17px;font-size:15px;" id="picker" readonly="readonly"
						  class="input-group-addon pointer">上传还款计划表</span>
				</div>
			</div>
			<div style="color: blue; margin-bottom: 30px;" id="dataMsg"></div>
		</dhcc:pmsTag>
	</div>
	<dhcc:thirdButton value="未放款解除" action="未放款解除"
					 onclick="repayPlan_List.noPutoutAjax(this);"></dhcc:thirdButton>
	<dhcc:thirdButton value="保存" action="保存"
					  onclick="repayPlan_List.repayPlanChackAjax(this);"></dhcc:thirdButton>
	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
					  onclick="myclose_click();"></dhcc:thirdButton>
	<dhcc:pmsTag pmsId="thirdPay-Repay-Button">
		<dhcc:thirdButton value="三方放款" action="三方放款" typeclass="cancel"
						  onclick="repayPlan_List.thirdPayRepayPlanAjax(this);"></dhcc:thirdButton>
	</dhcc:pmsTag>
</div>
<script>
</script>

</body>
</html>