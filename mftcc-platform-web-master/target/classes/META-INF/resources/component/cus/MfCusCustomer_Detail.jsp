<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">

<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" /> 
		<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" /> 
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<%--评级相关 strat--%>
		<script type="text/javascript" src="${webPath}/component/eval/js/cardDetailResult.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/creditquery/js/MfCreditQueryRecordInfo.js"></script>
		<script type="text/javascript" src="${webPath}/component/thirdservice/cloudmftcc/js/MfThirdMftccHighcourt.js"></script>
		<%--评级相关 end--%>
		<!-- 同盾认证报告 -->
		<script type="text/javascript" charset="utf-8" src="//cdnjs.tongdun.cn/preloan/tdreport.1.4.min.js?r=" + (newDate()).getTime()></script>
		<script type="text/javascript">
			var cusNo="",appId="",pleId="",fincId="",pactId="",creditId="",bidissueId="";
			cusNo = '${cusNo}';
			appId = '${appId}';
			fincId = '${fincId}';
			pactId = '${pactId}';
			creditId='${creditId}';
			bidissueId='${bidissueId}';
			//var isApprove = false;
			var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
			var wkfAppId = "${wkfAppId}";
			var completeFlag ='${dataMap.completeFlag}';
			//授信评级申请id 供展示使用
			//var creditApproveId = "${creditApproveId}";
			var evalAppNo = "${evalAppNo}";
			var creditType = "";
			var creditAppId = "";
			var cusType = '${mfCusCustomer.cusType}';
			var cusBaseType = '${mfCusCustomer.cusBaseType}';
			var cusBaseFlag = '${cusBaseFlag}';//表示客户基本信息是否已经录入
			var cusFullFlag = '${cusFullFlag}';//客户信息所有表单信息是否已经全部录入
			var scNo = '${scNo}';//客户要件场景
			//var authFlag = '${authFlag}';//是否存在正在进行的客户授信 0否1是
			//var creditSts = '${creditSts}';//授信状态
			var headImg = '${mfCusCustomer.headImg}';
			var ifUploadHead = '${mfCusCustomer.ifUploadHead}';
			//dataMap是业务参数，包括客户表单信息和参与业务信息等。
			var dataMap = <%=request.getAttribute("dataMap")%>;
			var cusTableList = dataMap.cusTableList;
			var cusClassify= '${mfCusClassify.classifyType}';//客户类别，黑名单或者优质客户
			var rankTypeName = '${mfCusClassify.rankTypeName}';
			var cusInfIntegrity = '${mfCusCustomer.infIntegrity}';
			var webPath = '${webPath}';
			var basePath = '${webPath}';
			var query = '${query}';
			var operable = '${operable}';
			var queryFile = 'queryFile';
			var docParm = "query="+queryFile+"&cusNo="+cusNo+"&scNo="+scNo+"&cusType="+cusType+"&appId="+appId+"&pactId="+pactId+"&fincId="+fincId;//查询文档信息的url的参数
			var firstKindNo = '${firstKindNo}';
			var cusName = '${mfCusCustomer.cusName}';
			var idNum = '${mfCusCustomer.idNum}';
			var cusTel = '${mfCusCustomer.contactsTel}';
			var headImgShowSrc;
			var busSubmitCnt = '${dataMap.busSubmitCnt}';
			var relation = dataMap.relation;//是否有关联关系
			var busEntrance ="${busEntrance}";//业务入口
			var pageView = "cusView";//客户视角
			var isCusDoc = "cusDoc";//客户视角要件实时刷新
			var evalCredit = '${evalCredit}';
			var formEditFlag = '${query}'; //表单单子段可编辑的标志
			var effectFlag = '${effectFlag}';//客户信息生效标志
			var cusAccountStatus = '${cusAccountStatus}';
			var cusAccountStatusName = '${cusAccountStatusName}';
			var examResultFlag=false;//是否能查看贷后检查历史标识
			var cusLevelName = "${mfCusCustomer.cusLevelName}";
			var cusCreditAddPro = "${cusCreditAddPro}";
			var comReportFlag = "${comReportFlag}";
			MfThirdMftccHighcourt.init();//法执情况按钮初始化
			var expansionFlag = 0;
			var sysProjectName = '${sysProjectName}';
            var examEntrance = 'cus';//贷后检查入口标识（客户）
		</script>
	</head>
	
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div" >
					 <c:if test='${evalCredit!="evalApp"}'> 
					<!-- 客户授信申请业务流程提示信息 -->
					<div class="row clearfix hide" id="showWkf">
						<div class="app-process">
							<div id="modeler1" class="modeler">
								<ul id="wj-modeler1" class="wj-modeler" isApp = "true">
								</ul>
							</div>
						</div>
					</div>
					<div id = "showCusNext" class="row clearfix btn bg-danger next-div hide">
						<div class="col-xs-12 column text-center">
							<div class="block-next"></div>
						</div>
					</div>
					</c:if>
					<!-- 项目授信申请业务流程提示信息 -->
					<c:if test='${evalCredit=="evalCredit"}'> 
					<div class="row clearfix hide" id="showProjectWkf">
						<div class="app-process">
							<div id="modeler1" class="modeler">
								<ul id="prolect-wj-modeler" class="wj-modeler" isApp = "true">
								</ul>
							</div>
						</div>
					</div>
					<div id = "showProjectNext" class="row clearfix btn bg-danger next-div hide">
						<div class="col-xs-12 column text-center">
							<div class="block-next"></div>
						</div>
					</div>
					</c:if>
					<!-- 评级审批和授信审批流程 展示使用 -->
					 <c:if test='${evalCredit=="evalApp"}'> 
						<div class="row clearfix " id="openWkf">
							<div class="app-process">
								<div id="modeler2" class="modeler">
									<ul id="eval-wj-modeler2" class="wj-modeler" isApp = "true">
									</ul>
								</div>
							</div>
						</div>
						<div id = "showProjectNext" class="row clearfix btn bg-danger next-div hide">
						<div class="col-xs-12 column text-center">
							<div class="block-next"></div>
						</div>
					</div>
					</c:if> 
					<!--头部主要信息 -->
				
					<div class="row clearfix head-info">
						<!--头像 -->
						
							<div class="col-xs-3 column text-center head-img">
								<div class="btn btn-link">
									<img id="headImgShow" name="headImgShow" class="img-circle"  onclick = "uploadHeadImg();"/>
									<dhcc:pmsTag pmsId="cus-header-change">
										<a class="btn btn-link head-word" onclick = "uploadHeadImg();">更换头像</a>
									</dhcc:pmsTag>
								</div>
							</div>
						<!--概要信息 -->
						<div class="col-xs-9 column head-content">
							<div class="clearfix">
								<div class="multi-bus pull-right">
									客户共有<span class="moreCnt_apply">申请中业务<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList('apply');">${dataMap.moreApplyCount}</a> 笔,</span>
									<span class="moreCnt_pact">在履行合同<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList('pact');">${dataMap.morePactCount}</a> 笔</span>
									<span class="moreCnt_finc">, 在履行借据<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList('finc');">${dataMap.moreFincCount}</a> 笔 </span>
									<span class="moreCnt_assure">, 为他人担保<a class="moreCnt more-assure-count pointer" onclick="getMultiBusList('assure');">${dataMap.moreAssureCount}</a> 笔 </span>
                                    <span class="moreCnt_finc_finish">, 已结清担保<a
                                            class="moreCnt more-finc-finish-count pointer"
                                            onclick="getMultiBusList('fincFinish');">${dataMap.moreFincFinishCount}</a> 笔 </span>
                                    <span class="moreCnt_repay_finish">, 已还款<a class="moreCnt more-repay-count pointer"
                                                                               onclick="getMultiBusList('repay');">${dataMap.moreRepayCount}</a> 次 </span>
								</div>
							</div>
							<div class="row clearfix">
								<div class="col-xs-10 column">
									<div class="margin_bottom_5">
									  	<button  class="btn btn-link cus head-title" onclick="updateCustomerInfo();">
											${mfCusCustomer.cusName}
										</button>
									</div>
									<!--信息查看入口 -->
									<div class="margin_bottom_10">
										<!-- 客户分类 -->
										<dhcc:pmsTag pmsId="cus-classify-custype">
											<button class="btn btn-view cus-tag" type="button" onclick="cusTagHis();">
												<i class="i i-ren2"></i><span  id="cusNameRate-span"></span>
											</button>
										</dhcc:pmsTag>
										<!-- 查看资料完整度 -->
										<dhcc:pmsTag pmsId="cus-integrity-datalevel">
											<button class="btn btn-view cus-integrity" type="button" onclick="getInfList();">
												<i class="i i-xing2"></i><span  id="integrity-span">完整度0%</span>
											</button>
										</dhcc:pmsTag>
										<!-- 查看关联关系 -->
										<dhcc:pmsTag pmsId="cus-relations-ship">
											<button  id = btn-initRelation  class="btn btn-relation btn-view" type="button" onclick="cusRelation();">
												<i class="i i-guanXi"></i><span>关联关系</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="cus-eval-rating">
											<%-- <c:if test='${mfCusCustomer.cusLevelName!=null}'>
												<c:if test='${mfCusCustomer.cusLevelName.contains("A")}' > 
												<button  class="btn btn-forestgreen btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</c:if>
												<c:if test='${mfCusCustomer.cusLevelName.contains("B")}' > 
												<button  class="btn cus-middle btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</c:if>
												<c:if test='${mfCusCustomer.cusLevelName.contains("C")}' > 
												<button  class="btn btn-danger btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</c:if>
											</c:if>
											<c:if test='${mfCusCustomer.cusLevelName==null}'>
												<button class="btn btn-lightgray btn-view " type="button" onclick="getEvalDetailResult('0');">
													<i class="i i-eval1"></i>未评估
												</button>
											</c:if> --%>
											
											<button id="cusEvalRating-button" class="btn btn-lightgray btn-view " type="button" onclick="getEvalDetailResult('0');">
												<i class="i i-eval1"></i><span id="cusEvalRating-span">未评估</span>
											</button>
										</dhcc:pmsTag>
										
										<dhcc:pmsTag pmsId="cus-auth-credit">
											<%-- <c:if test="${mfCusCreditApply.creditSts == 1|| mfCusCreditApply.creditSts == 2 ||mfCusCreditApply.creditSts == 3}">
												<button  class="btn cus-middle btn-view" title="授信总额" type="button" onclick="MfCusCredit.getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">授信中</span>
												</button>
											</c:if>
											<c:if test="${mfCusCreditApply.creditSts == 5}">
												<button  class="btn btn-dodgerblue btn-view" title="授信总额" type="button" onclick="MfCusCredit.getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">${mfCusCreditUseHis.applySum} 万</span>
												</button>
											</c:if>
											<c:if test="${mfCusCreditApply.creditSts != 1&&mfCusCreditApply.creditSts != 2&&mfCusCreditApply.creditSts != 3&&mfCusCreditApply.creditSts != 5}">
												<button  class="btn btn-lightgray btn-view" title="授信总额" type="button" onclick="">
													<i class="i i-credit"></i><span class="creditBus">未授信</span>
												</button>
											</c:if> --%>
											<button id="cusCredit-button" class="btn btn-lightgray btn-view" title="授信总额" type="button" onclick="">
												<i class="i i-credit"></i><span class="creditBus">未授信</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="cus-projectCredit-btn">
												<button id="projectCredit-button" class="btn btn-lightgray btn-view" title="立项授信额度" type="button" onclick="">
													<i class="i i-credit"></i><span class="projectCredit-span">未立项</span>
												</button>
											<%-- <c:if test="${mfCusCreditApply.creditSts == 1|| mfCusCreditApply.creditSts == 2 ||mfCusCreditApply.creditSts == 3}">
												<button  class="btn cus-middle btn-view" title="立项授信总额" type="button" onclick="MfCusCredit.getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">立项中</span>
												</button>
											</c:if>
											<c:if test="${mfCusCreditApply.creditSts == 5}">
												<button  class="btn btn-dodgerblue btn-view" title="立项授信总额" type="button" onclick="MfCusCredit.getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">${mfCusCreditUseHis.applySum} 万</span>
												</button>
											</c:if>
											<c:if test="${mfCusCreditApply.creditSts != 1&&mfCusCreditApply.creditSts != 2&&mfCusCreditApply.creditSts != 3&&mfCusCreditApply.creditSts != 5}">
												<button  class="btn btn-lightgray btn-view" title="立项授信总额" type="button" onclick="">
													<i class="i i-credit"></i><span class="creditBus">未立项</span>
												</button>
											</c:if> --%>
										</dhcc:pmsTag>
										<!-- 贷后检查 -->
										<dhcc:pmsTag pmsId="cus-get-after-check">
											<button id="examineResult" class="btn btn-lightgray btn-view" type="button" onclick="BusExamine.examineDetailResult();">
												<i class="i i-fangdajing"></i><span>保后检查</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="cus-risk-level">
											<c:if test="${dataMap.riskLevel > -1}">
												<button class="btn risklevel${dataMap.riskLevel} btn-view" type="button" id="cusRiskLevel" onclick="cusRisk();">
													<i class="i i-risk"></i><span>${dataMap.riskName}</span>
												</button>
											</c:if>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="cus-approve-report">
											<button class="btn btn-view btn-forestgreen" type="button" onclick="openCustomerCerReport();">
												<i class="i i-ren2"></i><span>认证报告</span>
											</button>
										</dhcc:pmsTag>
										<!-- 征信查询-->
										<dhcc:pmsTag pmsId="cus_credit_query">
											<button id ="creditQuery" class="btn btn-lightgray btn-view" type="button" onclick="MfCreditQueryRecordInfo.openCreditQuery();">
												<i class="i i-ren2"></i><span>征信查询</span>
											</button>
										</dhcc:pmsTag>
										<!-- 法执情况-->
										<dhcc:pmsTag pmsId="cus-law-enforcement">
											<button id ="lawEnforcementQuery" class="btn btn-lightgray btn-view" type="button" onclick="MfThirdMftccHighcourt.openLawEorcement();">
												<i class="i i-ren2"></i><span>法执情况</span>
											</button>
										</dhcc:pmsTag>
										<!-- 风控查询 （第三方数据）-->
								 		<dhcc:pmsTag pmsId="cus-third-service">
											<button id ="riskQuery" class="btn btn-dodgerblue btn-view" type="button" onclick="getRiskReport();">
												<i class="i i-fangdajing"></i><span>风控查询</span>
											</button>
										</dhcc:pmsTag> 
										<!-- 账户状态 -->
										<dhcc:pmsTag pmsId="cus-account-status">
											<button id="cusAccountStatus-button" class="btn btn-lightgray btn-view" type="button" onclick="">
												<i class="i i-ren2"></i><span id="cusAccountStatus-span">未开户</span>
											</button>
										</dhcc:pmsTag>
										<!-- 客户信息变更记录-->
										<dhcc:pmsTag pmsId="cus-info-change-record">
											<button id ="cusInfoChangeRecordQuery" class="btn btn-view" type="button" onclick="openCusInfoChangeRecord();">
											<i class="i i-ren2"></i><span>客户信息变更记录</span>
											</button>
										</dhcc:pmsTag>
										
									</div>
									<div>
										<p>
											<span><i class="i i-ren1 "></i>
												<span id = "contactsName">
													<c:if test="${mfCusCustomer.contactsName!=null&&mfCusCustomer.contactsName!=''}">
														${mfCusCustomer.contactsName}
													</c:if>
													<c:if test="${mfCusCustomer.contactsName==null||mfCusCustomer.contactsName==''}">
														<span class="unregistered">未登记</span>
													</c:if>
												</span>
											</span>
											<span class="vertical-line"></span> 
											<span><i class="i i-dianhua "></i>
											<span id = "contactsTel">
											
											<c:if test="${mfCusCustomer.contactsTel!=null&&mfCusCustomer.contactsTel!=''}">
													${mfCusCustomer.contactsTel}
												</c:if>
												<c:if test="${mfCusCustomer.contactsTel==null||mfCusCustomer.contactsTel==''}">
													<span class="unregistered">未登记</span>
												</c:if></span></span>
											<span class="vertical-line"></span> 
											<span><i class="i i-idcard2 " ></i><span id="idNum"><c:if test="${mfCusCustomer.idNum!=null&&mfCusCustomer.idNum!=''}">
													${mfCusCustomer.idNum}
												</c:if>
												<c:if test="${mfCusCustomer.idNum==null||mfCusCustomer.idNum==''}">
													<span class="unregistered">未登记</span>
												</c:if></span></span>
										</p>
									</div>
								</div>
									<div class="col-xs-2 column">
										<div class="i i-warehouse cus-type-font">
											<div class="type-name-div">${cusTypeName}</div>
										</div>
									</div>
							</div>
							<div class="row clearfix">
								<div>
									<p>
										<i class="i i-location "></i>
											<span id = "commAddress">
												<c:if test="${mfCusCustomer.commAddress!=null&&mfCusCustomer.commAddress!=''}">
													${mfCusCustomer.commAddress}
												</c:if>
												<c:if test="${mfCusCustomer.commAddress==null||mfCusCustomer.commAddress==''}">
													<span class="unregistered">未登记</span>
												</c:if>
											</span>
											<span class="vertical-line"></span>
											<i class="i i-youjian1 "></i>
											<span id = "postalCode">
												<c:if test="${!empty mfCusCustomer.postalCode}">
													${mfCusCustomer.postalCode}
												</c:if>
												<c:if test="${empty mfCusCustomer.postalCode}">
													<span class="unregistered">未登记</span>
												</c:if>
											</span>
										</p>
								</div>
							</div>
						</div>
					</div>
				
					<c:if test='${operable != "query"}'> 
						<!--信息登记操作入口 -->
						<div class="row clearfix btn-opt-group">
							<div class="col-xs-12 column">
								<div class="btn-group pull-right">
									<dhcc:pmsTag pmsId="cus-info-addBtn">
										<button class="btn btn-opt cus-add" onclick="MfCusDyForm.updateCusFormStas();" stype="display:none;" type="button">
											<i class="i i-bi"></i><span>完善资料</span>	
										</button>
									</dhcc:pmsTag>
									
									<dhcc:pmsTag pmsId="apply-new-btn">
										<button class="btn btn-opt"  onclick="applyInsert();">
											<i class="i i-jia2"></i><span>新增业务</span>
										</button>
									</dhcc:pmsTag>
	<!-- 								<button class="btn btn-opt"  onclick="addService();"> -->
	<!-- 									<i class="i i-lianwang"></i><span>联网核查</span> -->
	<!-- 								</button> -->
									<dhcc:pmsTag pmsId="cw-report-btn">
										<button class="btn btn-opt" onclick="getPfsDialog();" type="button">
											<i class="i i-qian1" ></i><span>财务报表</span>	
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-eval-btn">
										<button class="btn btn-opt" onclick="cusEval.getInitatEcalApp('${cusNo}','1');" type="button">
											<i class="i i-eval1"></i><span>发起评级</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-evalman-btn">
										<button class="btn btn-opt" onclick="cusEval.getManEvalApp();" type="button">
											<i class="i i-eval1"></i><span>外部评级</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-auth-btn">
										<button id="creditApply" class="btn btn-opt" onclick="MfCusCredit.getAppAuth('1');" type="button">
											<i class="i i-credit"></i><span>发起授信</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-projectCredit-btn">
										<button id="projectApply" class="btn btn-opt" onclick="MfCusCredit.getAppAuth('2');" type="button">
											<i class="i i-credit"></i><span>发起立项</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-report-auth-btn">
										<button id="reportApply" class="btn btn-opt" onclick="reportApply();" type="button">
											<i class="i i-credit"></i><span>贷前报告</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-do-after-check-btn">
										<button class="btn btn-opt" onclick="BusExamine.loanAfterExamine();" type="button">
											<i class="i i-qian1" ></i><span>贷后检查</span>	
										</button>
									</dhcc:pmsTag>
	<!-- 								<button class="btn btn-opt visible-lg-block" onclick="cusRelationIn();" type="button"> -->
	<!-- 									<i class="i i-guanXi"></i><span>关联关系</span>	 -->
	<!-- 								</button> -->
	<!-- 								<button class="btn btn-opt visible-lg-block" onclick="cusTag();" type="button"> -->
	<!-- 									<i class="i i-ren"></i><span id="cusTag">客户分类</span>	 -->
	<!-- 								</button> -->
									<div class="btn-group">
										<button type="button" class="btn btn-opt  dropdown-toggle"  data-toggle="dropdown">
											更多<span class="caret"></span>
										</button>
										<ul class="dropdown-menu btn-opt pull-right" role="menu">
											<dhcc:pmsTag pmsId="cus-relations-btn">
				
												<li class="btn-opt" role="presentation" onclick="cusRelationIn();">
													<button class="btn btn-opt more-btn-opt" type="button">
														<i class="i i-guanXi"></i><span>关联关系</span>	
													</button>
												</li>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="cus-tracing-btn">
												<li class="btn-opt" role="presentation" onclick="cusTrack('0');">
													<button class="btn btn-opt more-btn-opt" type="button">
													<i class="i i-dianhua"></i><span>客户跟进</span>
													</button>
												</li>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="cus-classify-btn">
												<li class="btn-opt" role="presentation" onclick="cusTag();">
													<button class="btn btn-opt more-btn-opt" type="button">
														<i class="i i-ren"></i><span id="cusTag">客户分类</span>	
													</button>
												</li>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="cus-openaccount-btn">
												<li class="btn-opt" role="presentation" onclick="mfBusOpenAccountApp();">
													<button class="btn btn-opt more-btn-opt" type="button">
														<i class="i i-ren"></i><span id="cusTag">客户开户</span>	
													</button>
												</li>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="cus-closeaccount-btn">
												<li class="btn-opt" role="presentation" onclick="mfBusCloseAccountApp();">
													<button class="btn btn-opt more-btn-opt" type="button">
														<i class="i i-ren"></i><span id="cusTag">客户销户</span>	
													</button>
												</li>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="cus-releasecashdeposit-btn">
												<li class="btn-opt" role="presentation" onclick="releaseCashDepositApp();">
													<button class="btn btn-opt more-btn-opt" type="button">
														<i class="i i-ren"></i><span id="cusTag">保证金释放</span>	
													</button>
												</li>
											</dhcc:pmsTag>
											<dhcc:pmsTag pmsId="bus-redeemcertificate-btn">
												<li class="btn-opt" role="presentation" onclick="redeemCertificateApp();">
													<button class="btn btn-opt more-btn-opt" type="button">
														<i class="i i-ren"></i><span id="cusTag">赎证申请</span>	
													</button>
												</li>
											</dhcc:pmsTag>
											<c:if test="${mfCusCustomer.cusType == '204'}">
												<dhcc:pmsTag pmsId="cus-family-assets-liabilities-btn">
													<li class="btn-opt" role="presentation"
														onclick="showFamilyTable();">
														<button class="btn btn-opt more-btn-opt" type="button">
															<i class="i i-ren"></i><span id="cusTag">家庭资产表</span>
														</button>
													</li>
											</dhcc:pmsTag>
											</c:if>
											<c:if test="${mfCusCustomer.cusType == '204'}">
												<dhcc:pmsTag pmsId="cus-family-profit-btn">
													<li class="btn-opt" role="presentation"
														onclick="showFamilyProfitTable();">
														<button class="btn btn-opt more-btn-opt" type="button">
															<i class="i i-excel"></i><span id="cusTag">家庭利润表</span>
														</button>
													</li>
												</dhcc:pmsTag>
											</c:if>
										</ul>
									</div>
									<dhcc:pmsTag pmsId="cus-expansion">
										<button class="btn btn-opt" onclick="expansion();" type="button">
											<i class="i i-qian1"></i><span id="expansion">一键收起</span>
										</button>
									</dhcc:pmsTag>
								</div>
							</div>
						</div>
					</c:if>
					<!--客户其他信息-->
						<div class="row clearfix">
							<div class="col-xs-12 column info-block">
								<!-- 评分卡 -->
								<%@ include file="/component/eval/AppEval_evalCardDetail.jsp" %>
								<div class="block-add" style="display: none;"></div>
								<c:if test="${cusFinMainList.size() != 0}">
									<%@ include file="/component/cus/MfCusFinData_Detail.jsp" %>
								</c:if>
							</div>
						</div>
					
					<dhcc:pmsTag pmsId="cus-info-uploadTree">
						<div class="row clearfix">
							<div class="col-xs-12 column" >
	 							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	 						</div>
						</div>
					</dhcc:pmsTag>
					
					<c:if test='${operable != "query"}'> 
						<div class="row clearfix">
							<div class="col-xs-12 column" >
								<div id="rotate-body"></div>
							</div>
						</div> 
					</c:if>
					<!--评级 授信审批历史 -->
					<c:if test='${evalCredit=="evalCredit"}'> 
				 	<div class="row clearfix approval-hist" id="spInfo-block">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
						   </div>
						   <div class="content margin_left_15 collapse in " id="spInfo-div">
								<div class="approval-process">
									<div id="modeler3" class="modeler">
										<ul id="wj-modeler3" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
						   </div>
						</div>
					</div>
					</c:if>
						<!--评级 授信审批历史 -->
					<c:if test='${evalCredit=="evalApp"}'> 
				 	<div class="row clearfix approval-hist" id="spInfo-block">
						<div class="list-table">
						   <div class="title">
						 <span><i class="i i-xing blockDian"></i>评级审批历史</span>
						 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#evalApprovalSpInfo-div">
							<i class='i i-close-up'></i>
							<i class='i i-open-down'></i>
						</button>
				   </div>
				   <div class="content margin_left_15 collapse in " id="evalApprovalSpInfo-div">
						<div class="approval-process">
							<div id="evalApprovalHisModeler-div" class="modeler">
								<ul id="evalApprovalHisModeler" class="wj-modeler" isApp = "false">
								</ul>
							</div>
						</div>
				   </div>
						</div>
					</div>
					</c:if>
				</div>
			</div>
			<!--客户附属信息-->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<jsp:include page="/component/app/MfBusApply_AbstractInfo.jsp?cusNo=${cusNo}&appId=${appId}&pleId=${pleId}&type=${type}&fincId=${fincId}&creditId=${creditId}&bidissueId=${bidissueId}"/>
					<jsp:include page="/component/collateral/MfBusCollateralRel_AbstractInfo.jsp?relId=${appId}&fincId=${fincId}&busModel=${busModel}&operable=${operable}"/>
					
					<c:if test='${appId==null||appId==""}' > 
					<!--客户跟踪-->
					<dhcc:pmsTag pmsId="mf-cus-follow">
						<jsp:include page="/component/cus/custracing/MfCusTrack_pub.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag>
					
					<!--历史业务统计-->
					<dhcc:pmsTag pmsId="mf-history-service">
						<jsp:include page="/component/pact/MfBusPact_Statistics.jsp?cusNo=${cusNo}"/>					
					</dhcc:pmsTag>
					<!--信息变更记录-->
					<dhcc:pmsTag pmsId="mf-message-change-record">
						<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag>
					</c:if>
					
					<c:if test='${appId!=null&&appId!=""}' > 
					<!--信息变更记录-->
					<dhcc:pmsTag pmsId="mf-message-change-record">
						<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag>
					<!--历史业务统计-->
					<dhcc:pmsTag pmsId="mf-history-service">
						<jsp:include page="/component/pact/MfBusPact_Statistics.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag>
					<!--客户跟踪-->
					<dhcc:pmsTag pmsId="mf-cus-follow">
						<jsp:include page="/component/cus/custracing/MfCusTrack_pub.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusGetNotCusInfo.js"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_Detail.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/themes/factor/js/selectInfo.js?v=${cssJsVersion}"></script>

<script type="text/javascript" src="${webPath}/component/cus/js/MfCusBankAccManage.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/collateral.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit_cus.js"></script>
<script type="text/javascript" src="${webPath}/component/auth/js/MfCusCredit.js"></script>
<%-- <script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditApply.js'></script> --%>
<script type="text/javascript" src="${webPath}/component/cus/trenchsubsidiary/js/MfTrenchUser_Insert.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/examine/js/BusExamine.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/cusEval.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/pub_init_eval_info.js?v=${cssJsVersion}"></script>
</html>