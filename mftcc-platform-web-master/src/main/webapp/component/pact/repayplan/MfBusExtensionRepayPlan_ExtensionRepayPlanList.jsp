<%@ page language="java" import="java.util.*,com.google.gson.Gson" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%
    String appId =(String) request.getParameter("appId");
    String extensionApplyId = (String) request.getParameter("extensionApplyId");
	String repayJsonString = new Gson().toJson(request.getAttribute("repayPlanList"));
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<link rel="stylesheet" href="${webPath}/component/pact/css/MfRepayPlan_RepayPlanList.css" />
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusExtensionRepayPlan_ExtensionRepayPlanList.js"></script>
	<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
<style type="text/css">
	
</style>
<script type="text/javascript">	
	var extensionApplyId='<%=extensionApplyId%>';
	var appId='<%=appId%>';
	var nodeNo='${nodeNo}';
    var fincId ='${fincId}';
	var feePower = "3";
	var projectName = '${projectName}';
    var lastReturnDate = '${lastReturnDate}';
	$(function() {
		var repayPlanList = <%=repayJsonString%>;
		var delayDateMap = ${delayDateMap};
		$("#planListSize").val(repayPlanList.length);
		var planListSize=repayPlanList.length;
		var lastPlanEndRate="#tdPlanEndDate_"+planListSize;//最后一期结束日期
		var lastRepayPrcp="#tdRepayPrcp_"+planListSize;//最后一期应还本金
		var lastRepayIntst="#tdRepayIntst_"+planListSize;//最后一期应还利息
		$(lastPlanEndRate).removeAttr('ondblclick');
		$(lastRepayPrcp).removeAttr('ondblclick');
		$(lastRepayIntst).removeAttr('ondblclick');
		extensionRepayPlan_List.init();
		changeMfFincAppValue(delayDateMap);//固定还款日顺延日期
	});
	
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="scroll-content">
		<div class="container">
		   <div class="row repay">
		      <div class="col-md-8 repayleft" id="pact_info">
		      	<dhcc:propertySeeTag property="formrepayplanlist0001" mode="query" />
		      </div>
		      <div class="col-md-4 repayright" >
		      	<table id="" class="from_w" title="" align="center"
						border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td class="tdlable" align="right">发放金额</td>
								<td class="tdvalue" align="left"><span id="putoutAmt"
									class="realvalue"> ${mfBusFincAppChild.putoutAmtFormat }
								</span><span>&nbsp;元</span></td>
							</tr>
							<tr>
								<td class="tdlable" align="right">实际发放金额</td>
								<td class="tdvalue" align="left">
									<span class="realvalue" id="span_putout_amt_real_format">
										${mfBusFincAppChild.putoutAmtRealFormat }
									</span>
									<span>&nbsp;元</span>
									<span style="color:red" id="span_charge_interest_sum">&nbsp;(扣除费用利息${mfBusFincAppChild.chargeFeeAndInterSumFormat }元)
									</span>
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right">发放日期</td>
								<td class="tdvalue" align="left">								
								  <span  class="realvalue" name="beginDate" id="beginDate">${mfBusFincAppChild.intstBeginDate }</span>	
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right">到期日期</td>
								<td class="tdvalue" align="left">
									<div class="realvalue">
										<select name="endDate" id="endDate" onchange="getChangeExtensionPlanListByEndDate()">
											<option value="${mfBusFincAppChild.intstEndDate }" selected = "selected">${mfBusFincAppChild.intstEndDate }</option>
										</select>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
		      </div>      
		   </div>
		   <div class="row" >
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
								<th scope="col" align="center" name="planBeginDate" sorttype="0">起息日</th>
								<th scope="col" align="center" name="expiryIntstDate" sorttype="0">结息日</th>
								<th scope="col" align="center" name="planEndDate" sorttype="0">还款日期</th>
								<%--<th scope="col" align="center" name="" sorttype="0">还款日期</th>--%>
								<th scope="col" align="center" name="repayPrcp" sorttype="0">应还本金(元)</th>
								<th scope="col" align="center" name="repayIntst" sorttype="0">应还利息(元)</th>
								<%--<th scope="col" align="center" name="feeSum" sorttype="0">费用总额(元)</th>--%>
								<th scope="col" align="center" name="repayPrcpIntstSum" sorttype="0">应还总额(元)</th>
								<th scope="col" align="center" name="repayPrcpBal" sorttype="0">期末本金余额(元)</th>
							</tr>
						</thead>
						<tbody id="repayPlan_tab">
						<c:forEach items="${repayPlanList}" varStatus="i" var="repayPlan" >  
					       <tr id="tr_${repayPlan.termNum}">
									<td align="center">
										<span class="tab_span" id="spanTermNum_${repayPlan.termNum}">${repayPlan.termNum}</span>
										<input type="text" name="termNum${repayPlan.termNum}" id="termNum_${repayPlan.termNum}" class="repayInput" value="${repayPlan.termNum}" >
									</td>
									<td align="center">
										<span class="tab_span" id="spanPlanBeginDate_${repayPlan.termNum}">${repayPlan.planBeginDateFormat}</span>
										<input type="text" name="planBeginDate${repayPlan.termNum}" id="planBeginDate_${repayPlan.termNum}" class="repayInput" value="${repayPlan.planBeginDateFormat}" datatype="6" >
									</td>


							   <td align="center">
								   <span class="tab_span"
										 id="spanExpiryIntstDate_${repayPlan.termNum}">${repayPlan.expiryIntstDateFormat}</span>
								   <input type="text" name="expiryIntstDate${repayPlan.termNum}"
										  id="expiryIntstDate_${repayPlan.termNum}"
										  class="repayInput datelogo BOTTOM_LINE"
										  value="${repayPlan.expiryIntstDateFormat}" datatype="6" mustinput="1"
										  maxlength="10">
							   </td>
							   <td align="center" id="tdPlanEndDate_${repayPlan.termNum}"
								   ondblclick="extensionRepayPlan_List.showExtensionEndDateInputValue(this)">
										<span class="tab_span" id="spanPlanEndDate_${repayPlan.termNum}">${repayPlan.planEndDateFormat}</span>
										<input type="text" name="planEndDate${repayPlan.termNum}" id="planEndDate_${repayPlan.termNum}" class="repayInput datelogo BOTTOM_LINE" value="${repayPlan.planEndDateFormat}" onfocus="fPopUpCalendarDlg({choose:extensionRepayPlan_List.changeExtensionEndDateValue});"  onmousedown="enterKey()" onkeydown="enterKey();" datatype="6"  mustinput="1" maxlength="10">
									</td>

									<td id="tdRepayPrcp_${repayPlan.termNum}"   ondblclick="extensionRepayPlan_List.showExtensionRepayPrcpInputValue(this)"  >
										<span class="tab_span money_right" id="spanRepayPrcp_${repayPlan.termNum}">${repayPlan.repayPrcpFormat}</span>
										<input type="text" name="repayPrcp${repayPlan.termNum}" id="repayPrcp_${repayPlan.termNum}" class="repayInput datelogo BOTTOM_LINE" value="${repayPlan.repayPrcp}" onblur="extensionRepayPlan_List.changeExtensionRepayPrcpValue(this);" onkeyup="value=value.replace(/[^\d.]/g,'')">
									</td>
									<td id="tdRepayIntst_${repayPlan.termNum}"  ondblclick="extensionRepayPlan_List.showExtensionRepayIntsInputValue(this)"  >
										<span class="tab_span money_right" id="spanRepayIntst_${repayPlan.termNum}">${repayPlan.repayIntstFormat}</span>
										<input type="text" name="repayIntst${repayPlan.termNum}" id="repayIntst_${repayPlan.termNum}" class="repayInput datelogo BOTTOM_LINE" value="${repayPlan.repayIntst}"  onblur="extensionRepayPlan_List.changeExtensionRepayIntstValue(this);" onkeyup="value=value.replace(/[^\d.]/g,'')">
									</td>
							   <td style="display:none">
										<span class="tab_span money_right" id="spanFeeSum_${repayPlan.termNum}">${repayPlan.feeSumFormat}</span>
										<input type="text" name="feeSum${repayPlan.termNum}" id="feeSum_${repayPlan.termNum}" class="repayInput" value="${repayPlan.feeSum}" >
									</td>
									<td >
										<span class="tab_span money_right" id="spanRepayPrcpIntstSum_${repayPlan.termNum}">${repayPlan.repayPrcpIntstSumFormat}</span>
										<input type="text" name="repayPrcpIntstSum${repayPlan.termNum}" id="repayPrcpIntstSum_${repayPlan.termNum}" class="repayInput" value="${repayPlan.repayPrcpIntstSum}" >
									</td>
									<td >
										<span class="tab_span money_right" id="spanRepayPrcpBalAfter_${repayPlan.termNum}">${repayPlan.repayPrcpBalAfterFormat}</span>
										<input type="text" name="repayPrcpBalAfter${repayPlan.termNum}" id="repayPrcpBalAfter_${repayPlan.termNum}" class="repayInput" value="${repayPlan.repayPrcpBalAfter}" >
									</td>
									<!--隐藏域   本期提前收取的利息-->
									<td align="center" style="display:none">
										<input type="text" name="repayIntstOrig${repayPlan.termNum}" id="repayIntstOrig_${repayPlan.termNum}"  value="${repayPlan.repayIntstOrig}">
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
										  id="repayDate_${repayPlan.termNum}" class="repayInput datelogo BOTTOM_LINE"
										  value="${repayPlan.planEndDate}" datatype="6" mustinput="1" maxlength="10">
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
					<%@ include file="/component/include/biz_node_plugins.jsp"%>
			   </div>
		   	  </div>
		   	</div>
		   </div>
		</div>
	</div>
	<div style="display:none;">
		<input type="hidden" id="fincId" value="${mfBusFincAppChild.fincId}"><!--子表借据号-->
        <input type="hidden" id="appId" value="${mfBusFincAppChild.appId}"><!--业务申请ID-->
		<input type="hidden" id="repayType" value="${mfBusFincAppChild.repayType}"><!--还款方式 -->
		<input type="hidden" id="beginDateHidden" value="${mfBusFincAppChild.intstBeginDate}"><!--默认的发放日期 -->
		<input type="hidden" id="endDateHidden" value="${mfBusFincAppChild.intstEndDate}"><!--默认到期日期  -->
		<input type="hidden" id="planListSize" value=""><!--还款计划列表期数-->
		<input type="hidden" id="putoutAmtHidden" value="${mfBusFincAppChild.putoutAmt}"><!--借据金额 -->
		<input type="hidden" id="putoutAmtRealHidden" value="${mfBusFincAppChild.putoutAmtReal}"/><!-- 实际发放金额 -->
		<input type="hidden" id="fincRate" value="${mfBusFincAppChild.fincRate}"><!--年利率 -->
		<input type="hidden" id="input_hide_intst_modify" value="0"/><!--判断是否先修改本金    状态 0：未修改   1：已修改 -->
		<input type="hidden" id="repay_fee_sum" value="${mfBusFincAppChild.chargeFeeSum}"/><!-- 放款前收取费用的总和 -->
		<input type="hidden" id="repay_intst_sum" value="${mfBusFincAppChild.chargeInterestSum}"/><!-- 放款前收取利息的总和 -->
		<!--系统参数  -->
		<input type="hidden" id="rate_type" value="${mfBusAppKind.rateType}"/><!-- 利率类型 -->
	    <input type="hidden" id="multipleLoanPlanMerge" value="${mfBusAppKind.multipleLoanPlanMerge}"/><!-- 多次放款还款计划是否合并  1-启用、0-禁用 -->
        <input type="hidden" id="interestCollectType" value="${mfBusAppKind.interestCollectType}"/><!-- 利息收息方式：1-上收息 2-下收息 --> 
		<input type="hidden" id="repayDateSet" value="${mfBusAppKindShow.repayDateSet}"/><!--还款日设置：1-贷款发放日 2-月末 3-固定还款日  -->
		<input type="hidden" id="yearDays" value="${mfBusAppKind.yearDays}"/><!--计息天数  -->
		<input type="hidden" id="normCalcType" value="${mfBusAppKind.normCalcType}"/><!-- 利息计算方式（按月计息：每月30天/按日计息：实际天数）2按月结息3按日结息 -->
		<input type="hidden" id="secondNormCalcType" value="${mfBusAppKind.secondNormCalcType}"/><!--不足期计息类型    	1-按月计息|2-按实际天数计息  -->
		<input type="hidden" id="returnPlanPoint" value="${mfBusAppKind.returnPlanPoint}"/><!--保留小数位数 	2-两位|1-一位|0-不保留  -->
		<input type="hidden" id="returnPlanRound" value="${mfBusAppKind.returnPlanRound}"/><!--还款计划舍入方式 2-四舍五入  -->
		<input type="hidden" id="instCalcBase" value="${mfBusAppKind.instCalcBase}"/><!--还款利息计算基数：1-贷款金额、2-贷款余额  -->
		<input type="hidden" id="preInstCollectType" value="${mfBusAppKindShow.preInstCollectType}"/><!--预先支付利息收取方式：0-放款时收取，1-合并第一期，2-独立一期  -->
		<input type="hidden" id="repayDateDef" value="${mfBusAppKindShow.repayDateDef}"/><!--默认还款日 当repay_date_set为固定还款日时,该字段才有值  -->
		<input type="hidden" id="rulesNo" value="${mfBusAppKindShow.rulesNo}"/><!--默认还款日 当repay_date_set为固定还款日时,该字段才有值  -->
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存"
			onclick="extensionRepayPlan_List.extensionRepayPlanAjax();"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
				onclick="myclose_click();"></dhcc:thirdButton>
	</div>
	<script>
	</script>
	
</body>
</html>
