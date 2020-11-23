<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信申请表单详情</title>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
		<script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<script type="text/javascript" src="${webPath}/component/report/js/highcharts_3.0.10.js"></script>
  		<script type="text/javascript" src="${webPath}/component/report/js/highcharts-more.js"></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditOpenHisData.js'></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditContract_Detail.js'></script>
		<script type="text/javascript">
			var menuBtn = [];
			var menuUrl = [];
			var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
			var vpNo = '8';
			var basePath = "${webPath}";
			var creditType = "${creditType}";
			var hisTaskList = eval("(" + '${json}' + ")").hisTaskList;
			var cusNo = "${cusNo}";
			var creditAppId = "${creditAppId}";
			var appId = "${creditAppId}";
			var creditApproveId = "${creditApproveId}";
			var creditSts = "${creditSts}";
			var creditModel = "${creditModel}";
			var query = "${query}";//该值标志位是否可以上传
			var index = 0; 
			var showParmsJson = eval("(" + '${showParmsJson}' + ")");
			var creditHisJsonObject =eval("(" + '${creditHisJsonObject}' + ")");
			var dateValuesJsonObject =eval("(" + '${dateValuesJsonObject}' + ")");
			var dateValues ='${dateValues}';
			var beginDate = creditHandleUtil.convertToYYYYMMDD("${mfCusCreditUseHis.beginDate}");
			var endDate = creditHandleUtil.convertToYYYYMMDD("${mfCusCreditUseHis.endDate}");
			var series=new Array();
			$(function(){
				mfCusCreditOpenHisData.init();
			});
		</script>
		<style type="text/css">
			.list-table .title span, .form-table .title span {
			    font-size: 16px;
			    font-weight: bold;
			    height: 37px;
			    line-height: 37px;
			    margin-left: 24px;
		    }
		    .content .fieldShow {
			    max-width: 14em;
			}
		</style>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="bg-white">
				<div class="row clearfix">
					<div class="col-xs-12 column">
						<div name="auth" title="授信申请信息" class="dynamic-block" style="">
							<div class="" style="margin-top: 71px;margin-left: 76px;line-height:30px;height: 250px;">
								<p>
									<span id="cusName" style="font-size: 25px;">
									${mfCusCreditUseHis.cusName}
									&nbsp;&nbsp;</span><br>
								</p>
								<p>	
									<span class="creditSumTitle">授信总额&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<span  id="creditSum" class="creditSum" style="color: 32B5CB;font-size:30px;">
									${mfCusCreditUseHis.creditSum}</span>
									<span class="creditSumTitle" style="margin-left:10px;">万</span><br>
								</p>
								<p>	
									<span class="creditSumTitle">占用额度&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<span  id=creditOccupySum class="creditSum" style="color: 32B5CB;font-size:30px;">
									${mfCusCreditUseHis.creditOccupySum}</span>
									<span class="creditSumTitle" style="margin-left:10px;">万</span><br>
								</p>
								<p>	
									<span class="creditSumTitle">可用额度&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<span  id=creditUsableSum class="creditSum" style="color: 32B5CB;font-size:30px;">
										${mfCusCreditUseHis.creditUsableSum}</span>
									<span class="creditSumTitle" style="margin-left:10px;">万</span><br>
								</p>
								<p>	
									<span class="creditDate">授信期限：&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<span  id="creditTerm" class="creditTerm" style="color: 32B5CB;font-size:30px;">
									${mfCusCreditUseHis.creditTerm}</span>
									<span class="creditSumTitle" style="margin-left:10px;">月</span><br>
								</p>
								<p>	
									<span class="creditDate">授信起止期限：&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<span id="creditbeginDate" class="creditbeginDate" style="color:32B5CB;">&nbsp;&nbsp;</span>
									&nbsp;&nbsp;至&nbsp;&nbsp;<span id="creditendDate" class="creditendDate" style="color:32B5CB;">&nbsp;&nbsp;</span>
								</p>
								<%-- <p>
									<span id="creditisCeilingLoop" class="creditisCeilingLoop">
									额度是否循环：&nbsp;&nbsp;</span>
									<span id="isCeilingLoop" class="isCeilingLoop" style="color:32B5CB;">
										<c:if  test='${mfCusCreditUseHis.isCeilingLoop == 1}'>
											是
										</c:if>
										<c:if  test='${mfCusCreditUseHis.isCeilingLoop == 0}'>
											否
										</c:if>
									</span>
								</p>
								<c:forEach items="${mfCusPorductCredits}" var="mfCusPorductCredit" varStatus="status">
									<p>${mfCusPorductCredit.kindName}&nbsp;&nbsp;&nbsp;&nbsp;
									<span  id="creditKindAmt" class="creditKindAmt" style="color: 32B5CB;font-size:14px;">
									${mfCusPorductCredit.creditKindAmt}</span>
									<span class="creditSumTitle" style="margin-left:10px;">万</span>
									</p>
								</c:forEach> --%>
							<%-- 	<s:iterator value="mfCusPorductCredits">
									<p>${kindName}&nbsp;&nbsp;&nbsp;&nbsp;
									<span  id="creditKindAmt" class="creditKindAmt" style="color: 32B5CB;font-size:14px;">
									${creditKindAmt}</span>
									<span class="creditSumTitle" style="margin-left:10px;">万</span>
									</p>
								</s:iterator> --%>
								<p id="creditRemak" class="creditRemak" style="width:46%;word-wrap:break-word;">
									授信说明：${mfCusCreditUseHis.remark}
								</p>
							</div>
							<div class="" style="position:absolute;margin-top:-232px;margin-left:32em;">
                                <div class="i i-warehouse cus-type-font">
                                    <div class="type-name-div" id="creditStsName">${creditStsName}</div>
                                </div>
							</div>
							<!-- <div style="margin-left: 76px;">
								<a href="#authHisInfo">历史授信</a>
							</div> -->
							<div id="productInfo" style="width: 600px;height:380px; margin-top:-304px;margin-left:52%"></div>
						</div>
						<dhcc:pmsTag pmsId="cus_product_credit_info_list">
						<div style="clear:both;"></div>
						<div id="productCreditInfo" name="productCreditInfo" title="产品授信明细信息" class="dynamic-block" style="height: auto;">
							<div class="list-table">
								<div class="title" style="background:#f8f9fc;height: 37px;">
									<span><i class="i i-xing blockDian"></i>产品授信明细信息</span>
									 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfCusProductCreditInfo">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
								</div>
								<div class="content margin_left_15 collapse in" id="mfCusProductCreditInfo">
									<dhcc:tableTag paginate="mfCusCreditProductDetailList" property="tablecreditproduct0004" head="true" />
								</div>
							</div>
						</div>
						</dhcc:pmsTag>
						
						<div style="clear:both;"></div>
						<div id = "creditPledgeInfo" class=" list-table " name="authPlege" title="反担保信息" style="display: none;">
							<div class="title">
								<span> <i class="i i-xing blockDian"></i>反担保信息</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#applyHis">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>	
							<div id="applyHis"class="collapse in">
								<div class="row clearfix " >
								 	<div class="col-xs-12 column">
								 		<div class="form-table base-info">
											<div class="content">
												<form  method="post" theme="simple" name="pedgeInfoform" id = "pedgeInfoform">
													<dhcc:propertySeeTag property="formpledydetail0001_new" mode="query"/>
												</form>
											</div>
										</div>
								 	</div>
								 </div>
							</div>
						</div>
						<div style="clear:both;"></div>
							
						<div name="authProtocolInfo" title="授信签约信息" class="dynamic-block list-table" style="display: none;">
							<div class="title">
								<span> <i class="i i-xing blockDian"></i> 授信签约信息
								</span>
								<button class="btn btn-link pull-right formAdd-btn"
									data-toggle="collapse" data-target="#authProtocolInfoDiv">
									<i class='i i-close-up'></i> <i class='i i-open-down'></i>
								</button>
							</div>
							<div class="row clearfix collapse in" id="authProtocolInfoDiv">
								<div class="col-xs-12 column">
									<div class="form-table base-info">
										<div class="content">
											<form  method="post" theme="simple" id="authProtocolForm">
												<dhcc:propertySeeTag property="formcreditpact0001_new"
													mode="query" />
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div style="clear:both;"></div>
					
						<div name="creditTemplateInfo" title="授信模板信息" class="dynamic-block list-table" >
							<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
						</div>
						<div style="clear:both;"></div>
						<div name="authApproveInfo" title="授信审批信息" class="dynamic-block approval-hist" style="display: none;">
							<div class="list-table">
								<div class="title" style="background:#f8f9fc;height: 37px;">
									 <span><i class="i i-xing blockDian"></i>审批历史</span>
									 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
							  	</div>
							  	<div class="content margin_left_15 collapse in " id="spInfo-div">
                                           <div class="approval-process">
                                                <div id="modeler1" class="modeler">
                                                     <ul id="wj-modeler2" class="wj-modeler" isApp = "false">
                                                     </ul>
                                                </div>
                                           </div>
                                </div>
							</div>
						</div>
						<div style="clear:both;"></div>
						<dhcc:pmsTag pmsId="pact_detail_list_info">
						<div id="pactDetailListInfo" name="pactDetailListInfo" title="合同明细信息" class="dynamic-block hide" style="height: outo;">
							<div class="list-table">
								<div class="title" style="background:#f8f9fc;height: 37px;">
									<span><i class="i i-xing blockDian"></i>合同明细信息</span>
									<button id="addPactDetailButton" class='btn btn-link formAdd-btn' onclick='mfCusCreditContractDetail.addPactDetail();' title='新增'>
											<i class='i i-jia3'></i>
									</button>
									 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#contractDetailList">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
								</div>
								<div class="content margin_left_15 collapse in" id="contractDetailList">
									<dhcc:tableTag paginate="mfCusCreditContractDetailList" property="tablePactDetail0001" head="true" />
								</div>
							</div>
						</div>
						</dhcc:pmsTag>
						<div style="clear:both;"></div>
						
						<div id="childPactListInfo" name="childPactListInfo" title="资金机构合同信息" class="dynamic-block hide" style="height: outo;">
							<div class="list-table">
								<div class="title" style="background:#f8f9fc;height: 37px;">
									<span><i class="i i-xing blockDian"></i>资金机构合同信息</span>
									<button id="addChildPactButton" class='btn list-add subBtn' onclick='mfCusCreditContractDetail.addChildPact();' title='新增'>
											<i class='i i-jia3'></i>
									</button>
									 <button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#creditChildPactList">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
								</div>
								<div class="content margin_left_15 collapse in" id="creditChildPactList">
									<dhcc:tableTag paginate="mfCusCreditChildContractList" property="tablecreditChildPactList" head="true" />
								</div>
							</div>
						</div>
						<div style="clear:both;"></div>

						<div id="authHisInfo" name="authHisInfo" title="用信历史信息" class="dynamic-block" >
							<div class="list-table">
								<div class="title" style="background:#f8f9fc;height: 37px;">
									<span><i class="i i-xing blockDian"></i>授信历史信息</span>
									 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfCusCreditsApplyHists">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
								</div>
								<div class="content margin_left_15 collapse in" id="mfCusCreditsApplyHists">
									<dhcc:tableTag paginate="mfCusCreditUseHisList" property="tablecreditapplyhist001" head="true" />
								</div>
							</div>
						</div>
						<div style="clear:both;"></div>
						
					</div>
				</div>	
			</div>
			<%-- <div class="formRowCenter">
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
					onclick="mfCusCreditOpenHisData.close();"></dhcc:thirdButton>
			</div> --%>
	</div>	
	</body>
</html>