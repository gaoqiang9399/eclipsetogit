
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<link rel="stylesheet" href="${webPath}/component/pact/css/MfRepayment_MfRepaymentJsp.css" />
<script type="text/javascript" src="${webPath}/component/pact/repay/js/mfLsbqRaymentJsp.js"></script>
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
</script>
<script type="text/javascript" src="${webPath}/component/pact/repay/js/initRepaymentDoc.js"></script>
<title>利随本清还款</title>
</head>
<script type="text/javascript">
	var currDate = '${mfLsbqRapaymentBean.systemDateLong}';//当前日期
 	var shengyubenjin='${mfLsbqRapaymentBean.shengYuBenJin}';//获取剩余本金
 	var returnPlanPoint='${mfLsbqRapaymentBean.returnPlanPoint}';//保留小数位
 	var fincId="${mfBusFincApp.fincId}";
	var docParm = "";//查询文档信息的url的参数 
</script>
<body class="body_bg">
	<div class="bigform_content">
		<div class="content_table">
			<div class="baseinf">
			    <!--客户信息  -->
				<div class="pay-cus">
				    <div class="col-xs-3 col-md-3 pay-cus-img">
					    <img id="headImgShow" name="headImgShow" class="img-circle" ;" 
					    src="${webPath}/uploadFile/viewUploadImageDetail?srcPath=themes%252Ffactor%252Fimages%252Fuser_0.jpg&amp;fileName=user2.jpg">
				    </div>    
					<div class="pay-cus-name">
					  ${mfCusCustomer.cusName }
					</div>
				<div class="pay-cus-phone" >
					 	${mfCusCustomer.contactsTel }
<!--  					 	<i class="i i-dianhua"></i>  -->
<!-- 					 	<i class="i i-youjian"></i>  -->
				</div>
				</div>
				<!-- 合同信息 -->
				<div class="col-md-6 repayright payinf">
					<div class="paydetail" style="width: 100%;margin: 0px;">
						<table style="width: 100%;">
							<tr>
								<td class="tdlable" align="right">合同编号</td>
								<td class="tdvalue"><span class="fieldShow ">${mfBusFincApp.pactNo}</span></td>
								<td class=" tdlable" align="right">产品种类 </td>
								<td class=" tdvalue"><span class="fieldShow ">${mfBusFincApp.kindName}</span></td>
							</tr>
							<tr>
								<td class=" tdlable" align="right">合同金额 </td>
								<td class=" tdvalue"><span class="fieldShow " id="putoutAmt" >${mfBusFincApp.putoutAmtFormat}元</span></td>
								<td class=" tdlable" align="right">合同利率 </td>
								<td class=" tdvalue"><span class="fieldShow ">${mfBusFincApp.fincRateShow}${rateUnit}</span></td>
							</tr>
							<tr>
								<td class=" tdlable" align="right">开始日期 </td>
								<td class=" tdvalue"><span class="fieldShow ">${mfBusFincApp.intstBeginDate}</span></td>
								<td class=" tdlable" align="right">结束日期 </td>
								<td class=" tdvalue"><span class="fieldShow ">${mfBusFincApp.intstEndDate}</span></td>
							</tr>
						</table>
					</div>
			</div>
			<!-- 合同信息  end-->
			
			<!--利随本清还款信息  -->
			<div class="payinf clearfix">
				<div class="paydetail">
					<table title="paydetail" width="100%" height="auto">
						<tbody>
							<tr>
								<td class="tdlable" align="right"><span class="theader">剩余本金</span>
								</td>
								<td class="tdvalue" align="left" width="0%"><span
									id="shiShouBenJin" class="fieldShow"
									style="display: inline-block;">
											${mfLsbqRapaymentBean.shengYuBenJinFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right"><span class="theader">实收利息</span>
								</td>
								<td class="tdvalue" align="left" width="0%"><span
									id="shiShouLiXi" class="fieldShow"
									style="display: inline-block;">
											${mfLsbqRapaymentBean.shiShouLiXiFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right"><span class="theader">本次结余</span>
								</td>
								<td class="tdvalue" align="left" width="0%"><span
									id="benCiJieYu" class="fieldShow"
									style="display: inline-block;">
											${mfLsbqRapaymentBean.benCiJieYuFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right"><span  id="benCiChongDiOrTuiKuan" class="theader">本次冲抵</span>
								</td>
								<td class="tdvalue" align="left" width="0%"><span
									id="benCiChongDi" class="fieldShow"
									style="display: inline-block;">
											${mfLsbqRapaymentBean.benCiChongDiFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="prealpay">
					<form method="post" theme="simple" name="operform"
						action="${webPath}/mfRepayment/lsbqRapaymentAjax">
						<table id="formrepay0003" class="from_w" title="formrepay0003"
							align="center" border="0" cellpadding="0" cellspacing="0"
							width="100%">
							<tbody>
							    <tr>
									<td class="tdlable" align="right">还本金额</td>
									<td class="tdvalue" align="left">
									  <div class="realvalue">
									  <input title="" name="huanKuanBenJin" id="huanKuanBenJin" onchange="funcCheckPRepay(this); resetTimes();shiShouBenJinByLsbqHuanKuanInputOnblur(this);"
												datatype="12" mustinput="0" type="text" value="0.00" > <i
												class="punit" style="top: 0px;">元</i> 
									  </div>
									</td>
								</tr>
								<tr>
									<td class="tdlable" align="right">还款日期</td>
									<td class="tdvalue" align="left">
										<div class="realvalue">
											<input title="还款日期" name="systemDateLong" id="systemDateLong"
												value="${mfLsbqRapaymentBean.systemDateLong}"
												datatype="6" mustinput="1" maxlength="10" readonly="readonly"
												onfocus="fPopUpCalendarDlg({min: '${mfLsbqRapaymentBean.shangCiHuanKuanRiQi}',max: new Date().toLocaleDateString(),choose:lsbqHuanKuanRiQiChange});"
												class="datelogo BOTTOM_LINE" type="text"><i class="i i-rili pointer"  style="color: #32b5cb;margin-left: -20px;" onclick="$('#systemDateLong').focus();"></i>
										</div>
									</td>
								</tr>
								<tr>
									<td class="tdlable" align="right">实收总计</td>
									<td class="tdvalue" align="left">
										<div class="realvalue">
												<spanstyle="color: #32b5cb;" id="shiShouZongJi">
												${mfLsbqRapaymentBean.shiShouZongJi}
												</span>元	
										</div>
									</td>
								</tr>
					            <tr>
									<td class="tdlable" align="right">
										还款说明
									</td>
									<td class="tdvalue" align="left">
										<div class="realvalue">
										<input title="还款说明" name="remark" id="remark" placeholder="200字以内" datatype="0" mustinput="0" maxlength="200" type="text">
										</div>
									</td>
								</tr>							
								<tr>
									<td class="tdlable" align="right"></td>
									<td class="tdvalue" align="left">
										<div class="repay-btn">
											<dhcc:thirdButton value="利随本清还款" action="利随本清还款"
												typeclass="nor-prepay"
												onclick="ajaxLsbqRapayment(this.form,myclose)"></dhcc:thirdButton>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--利随本清还款信息   end-->
			<!-- 还款计划 -->
			<div class="row">
				<div class="table_show">
					<div class="title">还款计划</div>
					<div class="col-md-12">
						<div class="list-table">
									<div class="content margin_left_15 collapse in">
										<table id="yingShou_List_table_001" width="100%" border="0"
											cellspacing="1" class="ls_list" title="">
										  <thead>
											<tr>
												<th scope="col" align="center" name="termNum" sorttype="0">期号</th>
												<th scope="col" align="center" name="planBeginDate" sorttype="0">开始日期</th>
												<th scope="col" align="center" name="planEndDate" sorttype="0">结束日期</th>
												<th scope="col" align="center" name="repayDate" sorttype="0">还款日期</th>
												<th scope="col" align="center" name="repayPrcp" sorttype="0">应还本金(元)</th>
												<th scope="col" align="center" name="repayIntst" sorttype="0">应还利息(元)</th>
												<th scope="col" align="center" name="repayPrcpIntstSum" sorttype="0">应还总额(元)</th>
												<th scope="col" align="center" name="repayPrcpBal" sorttype="0">期末本金余额(元)</th>
												<th scope="col" align="center" name="outFlag" sorttype="0">状态</th>
											</tr>
										  </thead>
										  <tbody>
											<c:forEach var="repayPlan"
												items="${mfRepayPlanList}" varStatus="stat">
												 <tr>
												    <td align="center"><span class="tab_span" >${repayPlan.termNum}</span> </td>
										            <td align="center"><span class="tab_span">${repayPlan.planBeginDateFormat}</span> </td> 
										            <td align="center"><span class="tab_span">${repayPlan.planEndDateFormat}</span> </td>
										            <td align="center"><span class="tab_span">${repayPlan.repayDate}</span> </td>
										            <td align="center"><span class="tab_span money_right">${repayPlan.repayPrcpFormat}</span> </td>
										            <td align="center"><span class="tab_span money_right">${repayPlan.repayIntstFormat}</span> </td>
										            <td align="center"><span class="tab_span money_right">${repayPlan.repayPrcpIntstSumFormat}</span> </td>
										            <td align="center"><span class="tab_span money_right">${repayPlan.repayPrcpBalFormat}</span> </td> 
										            <td align="center"><span class="tab_span">${repayPlan.outFlag}</span> </td>    
												 </tr>
											</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
			</div>
			<!-- 还款计划 end -->
          </div>
        </div>
        </div>
        <div style="height:50px;"></div>
           <!-- 页面隐藏域 -->
			<div id="hidden_parm" style="display: none;">
				<input id="pactId" value="${mfLsbqRapaymentBean.pactId}" type="hidden" />
				<input id="fincId" value="${mfLsbqRapaymentBean.fincId}" type="hidden" />
				<input id="shengYuBenJin_input_text" value="${mfLsbqRapaymentBean.shengYuBenJin}" type="hidden" />
				<input id="shiShouLiXi_input_text"  value="${mfLsbqRapaymentBean.shiShouLiXi}" type="hidden" />
				<input id="benCiJieYu_input_text" value="${mfLsbqRapaymentBean.benCiJieYu}" type="hidden" />					
				<input id="benCiChongDi_input_text" value="${mfLsbqRapaymentBean.benCiChongDi}" type="hidden" />
				<input id="shiShouZongJi_input_text" value="${mfLsbqRapaymentBean.shiShouZongJi}" type="hidden" />	
			</div>
      </body>
</html>