
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
<script type="text/javascript" src="${webPath}/component/pact/repay/js/mfPrepaymentJsp.js"></script>
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/repay/js/initRepaymentDoc.js"></script>
<title>提前还款</title>
</head>
<script type="text/javascript">
	var preRepayApplyFlag = '${dataMap.preRepayApplyFlag}';//提前还款申请标志1-表示要经过提前还款申请才能提前还款，0-反之
	var currDate = '${mfPrepaymentBean.systemDateLong}';//当前日期
	var ifYunXuTuiKuan="0";//是否允许结余退款 0 不允许退款 借据金额 直接冲抵  1 允许退款 结余金额 进行直接退回 
 	var preRepayType='${mfPrepaymentBean.preRepayType}';//提前还款：1-提前结清 2-提前归还部分本金
 	var shengyubenjin='${mfPrepaymentBean.shengYuBenJin}';//获取剩余本金
 	var appIdVal="${mfBusFincApp.appId}";//申请号
 	if(preRepayApplyFlag=="1"){
 		shengyubenjin='${mfPreRepayApply.appAmt}';//获取剩余本金
 	}
 	var tiQianHuanKuanWeiYueJin='${mfPrepaymentBean.tiQianHuanKuanWeiYueJin}';// 提前结清时  该违约金 是定值 
 	var termInstMustBack='${mfPrepaymentBean.termInstMustBack}';//当期本息是否必须归还：1-是、0-否
 	var returnPlanPoint='${mfPrepaymentBean.returnPlanPoint}';//保留小数位
 	var fincId="${mfBusFincApp.fincId}";
	var docParm = "";//查询文档信息的url的参数
	var projectName = '${projectName}';
    var mfBusEditorRepayplanFlag = '${mfBusEditorRepayplanFlag}';
    var appId = '${mfBusFincApp.appId}';
</script>
<body class="body_bg">
	<div class="bigform_content">
		<div class="content_table">
			<div class="baseinf">
			    <!--客户信息  -->
				<div class="pay-cus">
				    <div class="col-xs-3 col-md-3 pay-cus-img">
					    <img id="headImgShow" name="headImgShow" class="img-circle"
					    src="${webPath}/uploadFile/viewUploadImageDetail?srcPath=themes%252Ffactor%252Fimages%252Fuser_0.jpg&amp;fileName=user2.jpg">
				    </div>    
					<div class="pay-cus-name">
					${mfCusCustomer.cusName }
					</div>
				<%--<div class="pay-cus-phone" >
					 	${mfCusCustomer.contactsTel }
<!--  					 	<i class="i i-dianhua"></i>  -->
<!-- 					 	<i class="i i-youjian"></i>  -->
				</div>--%>
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
								<td class=" tdvalue"><span class="fieldShow ">${mfBusFincApp.intstEndDateShow}</span>
								</td>
							</tr>
						</table>
					</div>
			</div>
			<!-- 合同信息  end-->
			
			<!--提前还款信息  -->
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
											${mfPrepaymentBean.shengYuBenJinFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right"><span class="theader">当期本金</span>
								</td>
								<td class="tdvalue" align="left" width="0%"><span
									id="dangQiBenJin" class="fieldShow"
									style="display: inline-block;">
											${mfPrepaymentBean.dangQiBenJinFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right"><span class="theader">实收利息</span>
								</td>
								<td class="tdvalue" align="left" width="0%"><span
									id="shiShouLiXi" class="fieldShow"
									style="display: inline-block;">
											${mfPrepaymentBean.shiShouLiXiFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right"><span class="theader">本次结余</span>
								</td>
								<td class="tdvalue" align="left" width="0%"><span
									id="benCiJieYu" class="fieldShow"
									style="display: inline-block;">
											${mfPrepaymentBean.benCiJieYuFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
							<tr>
								<td class="tdlable" align="right"><span  id="benCiChongDiOrTuiKuan" class="theader">本次冲抵</span>
								</td>
								<td class="tdvalue" align="left" width="0%"><span
									id="benCiChongDi" class="fieldShow"
									style="display: inline-block;">
											${mfPrepaymentBean.benCiChongDiFormat}</span><span>&nbsp;元</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="prealpay">
					<form method="post" theme="simple" name="operform" action="${webPath}/mfRepayment/prepaymentAjax">
						<table id="formrepay0003" class="from_w" title="formrepay0003"
							align="center" border="0" cellpadding="0" cellspacing="0"
							width="100%">
							<tbody>
							    <tr>
									<td class="tdlable" align="right">提前还本</td>
									<td class="tdvalue" align="left">
									  <div class="realvalue">
									  <input title="" name="tiQianHuanBen" id="tiQianHuanBen" onchange="funcCheckPRepay(this); resetTimes();shiShouBenJinByTiQianHuanKuanInputOnblur(this);"
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
												   value="${mfPrepaymentBean.systemDateLong }"
												   datatype="6" mustinput="1" maxlength="10" readonly="readonly"
												   onfocus="fPopUpCalendarDlg({min: '${mfPrepaymentBean.shangCiHuanKuanRiQi}',max: new Date().toLocaleDateString(),choose:tiQianHuanKuanRiQiChange});"
												   class="datelogo BOTTOM_LINE" type="text"><i class="i i-rili pointer"  style="color: #32b5cb;margin-left: -20px;" onclick="$('#systemDateLong').focus();"></i>
										</div>
									</td>
								</tr>
								<tr>
								<td class="tdlable" align="right">优惠总额</td>
									<td class="tdvalue" align="left">
									  <div class="realvalue">
									  <input title="" name="YouHuiZongEr" id="YouHuiZongEr"
									    onchange="funcCheckPRepay(this); resetTimes(); calcTiQianYouHuiInputTextOnblur()"
									    datatype="12" mustinput="0" type="text" value="0.00"> <i
												class="punit" style="top: 0px;">元</i> 
									  </div>
								</td>
								</tr>
								<tr>
									<td class="tdlable" align="right">违约金</td>
									<td class="tdvalue" align="left">
										<div class="realvalue">
											<span title="" name="tiQianHuanKuanWeiYueJin" id="tiQianHuanKuanWeiYueJin"											   
											 style="display: inline-block;">${mfPrepaymentBean.tiQianHuanKuanWeiYueJin}</span><span>&nbsp;元</span>
												<span style="height: 34px; line-height: 34px; margin-left: 5px;">实收总计<span
												style="color: #32b5cb;" id="shiShouZongJi">
													${mfPrepaymentBean.shiShouZongJi}</span>元 </span>
										</div>
									</td>
								</tr>
								<tr id="huanKuanJiHuaTiaoZheng">
									<td class="tdlable" align="right" >还款计划调整</td>
									<td class="tdvalue" align="left">
									<div class="realvalue">
										<select id="jiHuaTiaoZhengCanShu" name="jiHuaTiaoZhengCanShu">
											<option value="0" selected>还款计划总期数不变</option>
											<option value="1">还款计划每期金额不变</option>
										</select>
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
											<dhcc:pmsTag pmsId="loan-return-money">
												<dhcc:thirdButton value="提前还款" action="提前还款" typeclass="nor-prepay" onclick="ajaxPrepayment(this.form,myclose)"></dhcc:thirdButton>
											</dhcc:pmsTag>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
			</div>
			<!--提前还款信息   end-->
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
												<th scope="col" align="center" name="planBeginDate" sorttype="0">开始日
												</th>
												<th scope="col" align="center" name="expiryIntstDate" sorttype="0">结息日
												</th>
												<th scope="col" align="center" name="planEndDate" sorttype="0">到期日期</th>
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
													 <td align="center"><span
															 class="tab_span">${repayPlan.planBeginDateFormat}</span>
													 </td>
													 <td align="center"><span
															 class="tab_span">${repayPlan.expiryIntstDateFormat}</span>
													 </td>
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
		<!-- 还款资料 -->
		<div class="col-md-12 col-md-offset-0 column" >
				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
			</div>
          </div>
        </div>
        <c:if test='${mfPreRepayApply.flowFlag=="1"}'>
	        <!-- 提前还款申请审批历史 -->
			<div id="spInfo-block" class="approval-hist">
				<div class="list-table">
				   <div class="title">
						 <span><i class="i i-xing blockDian"></i>提前还款申请审批历史</span>
						 <button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
							<i class="i i-close-up"></i>
							<i class="i i-open-down"></i>
						</button>
				   </div>
				   <div class="content margin_left_15 collapse in " id="spInfo-div">
						<div class="approval-process">
							<div id="modeler1" class="modeler">
								<ul id="wj-modeler2" class="wj-modeler" isapp="false">
								</ul>
							</div>
						</div>
				   </div>
				</div>
			</div>
        </c:if>
        </div>
        
        <div style="height:50px;"></div>
           <!-- 页面隐藏域 -->
			<div id="hidden_parm" style="display: none;">
			    <input id="preRepayType" value="${mfPrepaymentBean.preRepayType}" type="hidden" />
			    <input id="preRepayInstAccoutBase" value="${mfPrepaymentBean.preRepayInstAccoutBase}" type="hidden" />
				<input id="pactId" value="${mfPrepaymentBean.pactId}" type="hidden" />
				<input id="fincId" value="${mfPrepaymentBean.fincId}" type="hidden" />
				<input id="input_hidden_returnMethod" value="${mfPrepaymentBean.returnMethod}" type="hidden" />
				<input id="shengYuBenJin_input_text" value="${mfPrepaymentBean.shengYuBenJin}" type="hidden" />
				<input id="shiShouLiXi_input_text"  value="${mfPrepaymentBean.shiShouLiXi}" type="hidden" />
		        <input id="dangQiBenJin_input_text" value="${mfPrepaymentBean.dangQiBenJin}" type="hidden" />					
				<input id="tiQianHuanKuanWeiYueJin_input_text" value="${mfPrepaymentBean.tiQianHuanKuanWeiYueJin}" type="hidden" />					
				<input id="benCiJieYu_input_text" value="${mfPrepaymentBean.benCiJieYu}" type="hidden" />					
				<input id="benCiChongDi_input_text" value="${mfPrepaymentBean.benCiChongDi}" type="hidden" />
				<input id="shangCiJieYu_input_text" value="${mfPrepaymentBean.shangCiJieYu}" type="hidden" />					
				<input id="YouHuiZongEr_input_text" value="0.00" type="hidden" />	
				<input id="shiShouZongJi_input_text" value="${mfPrepaymentBean.shiShouZongJi}" type="hidden" />	
				<input id="shiShouYouHuiZongJi_input_text" value="0.00" type="hidden" />										 
				<input id="returnMethod_input_text" value="${mfPrepaymentBean.returnMethod}" type="hidden" />					
				<input id="shiShouLiXiTip_input_text" value="${mfPrepaymentBean.shiShouLiXi}" type="hidden" />
				<input id="tiQianHuanKuanWeiYueJinTip_input_text" value="${mfPrepaymentBean.tiQianHuanKuanWeiYueJin}" type="hidden" />
			</div>
      </body>
  <script type="text/javascript">
	  var flowFlag = '${mfPreRepayApply.flowFlag}';
	  if(flowFlag=="1"){
	  	var preRepayAppId = '${preRepayAppId}';
	 	showWkfFlowVertical($("#wj-modeler2"),preRepayAppId,"14","pre_repay_approval");
	  }
</script>
</html>