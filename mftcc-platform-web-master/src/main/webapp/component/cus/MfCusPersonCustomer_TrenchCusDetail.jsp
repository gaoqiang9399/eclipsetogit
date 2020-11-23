<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/bussNodePmsBiz.jsp"%>

<%
	String authFlag = (String) request.getAttribute("authFlag");
	String authFormHtml = "";
	if("1".equals(authFlag)){
		authFormHtml = (String) request.getAttribute("authFormHtml");
	}else{
	}
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
		<link rel="stylesheet" href="${webPath}/component/app/css/MfBusApply_Detail.css" />
		<script type="text/javascript" src="${webPath}/component/include/myCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusBorrowerInfo_Insert.js'> </script>
		<%--评级相关 strat--%>
		<script type="text/javascript" src="${webPath}/component/eval/js/cardDetailResult.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusAssetValueAnalyse.js"></script>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusSurveySpot_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
		<script type='text/javascript' src="${webPath}/dwr/util.js"></script>  
		<script type="text/javascript" src="${webPath}/dwr/interface/UtilDwr.js"></script> 
		<script type="text/javascript" src="${webPath}/component/thirdservice/cloudmftcc/js/MfThirdMftccHighcourt.js"></script>
		<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/applyBigPage.js?v=${cssJsVersion}"></script>
		<%--评级相关 end--%>
		<!-- 同盾认证报告 -->
		<!-- <script type="text/javascript" charset="utf-8" src="//cdnjs.tongdun.cn/preloan/tdreport.1.4.min.js?r=" + (newDate()).getTime()></script> -->
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
			var creditApproveId = "${creditApproveId}";
			var evalAppNo = "${evalAppNo}";
			var cusType = '${mfCusCustomer.cusType}';
			var cusBaseType = '${mfCusCustomer.cusBaseType}';
			var cusBaseFlag = '${cusBaseFlag}';//表示客户基本信息是否已经录入
			var cusFullFlag = '${cusFullFlag}';//客户信息所有表单信息是否已经全部录入
			var scNo = '${scNo}';//客户要件场景
			var authFlag = '${authFlag}';
			var headImg = '${mfCusCustomer.headImg}';
			var ifUploadHead = '${mfCusCustomer.ifUploadHead}';
			//dataMap是业务参数，包括客户表单信息和参与业务信息等。
			var dataMap = <%=request.getAttribute("dataMap")%>;
			var cusTableList = dataMap.cusTableList;
			var relNo = "cusNo-"+cusNo;
			var cusClassify= '${mfCusClassify.classifyType}';//客户类别，黑名单或者优质客户
			var rankTypeName = '${mfCusClassify.rankTypeName}';
			var webPath = '${webPath}';
			var basePath = '${webPath}';
			var authFormHtml = '<%=authFormHtml%>';
			var query = '${query}';
			var operable = '${operable}';
			var docParm = "query="+query+"&cusNo="+cusNo+"&scNo="+scNo+"&cusType="+cusType+"&appId="+appId+"&pactId="+pactId+"&fincId="+fincId;//查询文档信息的url的参数
			var firstKindNo = '${firstKindNo}';
			var entrance = "credit";//授信业务流程中登记押品标记入口
			var cusName = '${mfCusCustomer.cusName}';
			var idNum = '${mfCusCustomer.idNum}';
			var cusTel = '${mfCusCustomer.cusTel}';
			var headImgShowSrc;
			var cusInfIntegrity = '${mfCusCustomer.infIntegrity}';
			var busSubmitCnt='${dataMap.busSubmitCnt}';
			var relation = dataMap.relation;//是否有关联关系
			var creditType = "${creditType}";
			var creditAppId = "${creditAppId}";
			var pageView  = "cusView";
			var isCusDoc = "cusDoc";//客户视角要件实时刷新
			var busEntrance ="${busEntrance}";//业务入口
			var evalCredit = '${evalCredit}';
			var formEditFlag = '${query}';//表单单子段可编辑的标志
			//当前登录系统标识 
			var sysFlag ='${dataMap.sysFlag}';
			// 单字段编辑的保存回调方法。
			function oneCallback(data) {
				console.log(data);
			}
			MfThirdMftccHighcourt.init();//法执情况按钮初始化
		</script>
	</head>
	
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div" >
					<%-- <s:if test='%{operable != "query"}'> 
						<div class="row clearfix hide" id="showWkf">
							<div class="app-process">
								<div id="modeler1" class="modeler">
									<ul id="wj-modeler1" class="wj-modeler" isApp = "true">
									</ul>
								</div>
							</div>
						</div>
						<!-- 授信申请业务流程提示信息 -->
						<div class="row clearfix btn bg-danger next-div hide">
							<div class="col-xs-12 column text-center">
								<div class="block-next"></div>
							</div>
						</div>
					</s:if>
					<!-- 评级审批和授信审批流程 展示使用 -->
					<s:if test='%{evalCredit=="evalCredit"}'> 
						<div class="row clearfix " id="openWkf">
							<div class="app-process">
								<div id="modeler2" class="modeler">
									<ul id="wj-modeler2" class="wj-modeler" isApp = "true">
									</ul>
								</div>
							</div>
						</div>
					</s:if> --%>
					<!--头部主要信息 -->
				
					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img">
							<div class="btn btn-link">
								<img id="headImgShow" name="headImgShow" class="img-circle"  onclick = "uploadHeadImg();"/>
								<a class="btn btn-link head-word" onclick = "uploadHeadImg();">更换头像</a>
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
										<%-- <dhcc:pmsTag pmsId="cus-classify-custype">
											<button class="btn btn-view cus-tag" type="button" onclick="cusTagHis();">
												<i class="i i-ren2"></i><span  id="cusNameRate-span"></span>
											</button>
										</dhcc:pmsTag> --%>
										<button class="btn btn-view cus-integrity" type="button" onclick="getInfList();">
											<i class="i i-xing2"></i><span  id="integrity-span">完整度0%</span>
										</button> 
										<button class="btn btn-relation btn-view" type="button"  onclick="cusRelation();">
											<i class="i i-guanXi"></i><span>关联关系</span>
										</button>
										<%-- <dhcc:pmsTag pmsId="cus-eval-rating">
											<s:if test='%{mfCusCustomer.cusLevelName!=null}'>
												<s:if test='%{mfCusCustomer.cusLevelName.contains("A")}' > 
												<button id="cusEvalLevel" class="btn btn-forestgreen btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</s:if>
												<s:if test='%{mfCusCustomer.cusLevelName.contains("B")}' > 
												<button id="cusEvalLevel" class="btn cus-middle btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</s:if>
												<s:if test='%{mfCusCustomer.cusLevelName.contains("C")}' > 
												<button id="cusEvalLevel" class="btn btn-danger btn-view" type="button" onclick="getEvalDetailResult('1');">
													<i class="i i-eval1"></i>${mfCusCustomer.cusLevelName}
												</button>
												</s:if>
											</s:if>
											<s:else>
												<button id="cusEvalLevel" class="btn btn-lightgray btn-view " type="button" onclick="getEvalDetailResult('0');">
													<i class="i i-eval1"></i>未评估
												</button>
											</s:else>
										</dhcc:pmsTag>
										
										<dhcc:pmsTag pmsId="cus-auth-credit">
											<s:if test="%{mfCusCreditApply.creditSts == 1}">
												<button  class="btn cus-middle btn-view" title="授信总额" type="button" onclick="getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">授信中</span>
												</button>
											</s:if>

											<s:elseif test="%{mfCusCreditApply.creditSts == 2}">
												<button  class="btn cus-middle btn-view" title="授信总额" type="button" onclick="getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">授信中</span>
												</button>
											</s:elseif>
											<s:elseif test="%{mfCusCreditApply.creditSts == 3}">
												<button  class="btn cus-middle btn-view" title="授信总额" type="button" onclick="getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">授信中</span>
												</button>
											</s:elseif>
											<s:elseif test="%{mfCusCreditApply.creditSts == 5}">
												<button  class="btn btn-dodgerblue btn-view" title="授信总额" type="button" onclick="getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">${mfCusCreditApply.applySum} 万</span>
												</button>
											</s:elseif>
											<s:else>
												<button  class="btn btn-lightgray btn-view" title="授信总额" type="button" onclick="getCreditHisDataInfo();">
													<i class="i i-credit"></i><span class="creditBus">未授信</span>
												</button>
											</s:else>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="cus-risk-level">
											<s:if test="%{dataMap.riskLevel > -1}">
												<button class="btn risklevel${dataMap.riskLevel} btn-view" type="button" onclick="cusRisk();">
													<i class="i i-risk"></i><span>${dataMap.riskName}</span>
												</button>
											</s:if>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="cus-approve-report">
											<button class="btn btn-view cus-tag" type="button" onclick="openCustomerCerReport();">
												<i class="i i-ren2"></i><span>认证报告</span>
											</button>
										</dhcc:pmsTag>

										<!-- 征信查询-->
										<dhcc:pmsTag pmsId="cus_credit_query">
											<button id ="creditQuery" class="btn btn-lightgray btn-view" type="button" onclick="MfCreditQueryRecordInfo.openCreditQueryForCusDatail();">
												<i class="i i-ren2"></i><span>征信查询</span>
											</button>
										</dhcc:pmsTag>
										
										<!-- 法执情况-->
										<dhcc:pmsTag pmsId="cus-law-enforcement">
											<button id ="lawEnforcementQuery" class="btn btn-lightgray btn-view" type="button" onclick="MfThirdMftccHighcourt.openLawEorcement();">
											<i class="i i-ren2"></i><span>法执情况</span>
											</button>
										</dhcc:pmsTag> --%>
									</div>
									<div>
										<p>
											<span><i class="i i-ren1 "></i>
												<span id = "contactsName">
													<s:if test="mfCusCustomer.contactsName!=null&&mfCusCustomer.contactsName!=''">
														${mfCusCustomer.contactsName}
													</s:if>
													<s:else>
														<span class="unregistered">未登记</span>
													</s:else>
												</span>
											</span>
											<span class="vertical-line"></span> 
											<span><i class="i i-dianhua "></i>
											<span id = "contactsTel">
											
											<s:if test="mfCusCustomer.contactsTel!=null&&mfCusCustomer.contactsTel!=''">
													${mfCusCustomer.contactsTel}
												</s:if>
												<s:else>
													<span class="unregistered">未登记</span>
												</s:else></span></span>
											<span class="vertical-line"></span> 
											<span><i class="i i-idcard2 " ></i><span id="idNum"><s:if test="mfCusCustomer.idNum!=null&&mfCusCustomer.idNum!=''">
													${mfCusCustomer.idNum}
												</s:if>
												<s:else>
													<span class="unregistered">未登记</span>
												</s:else></span></span>
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
												<s:if test="mfCusPersBaseInfo.commAddress!=null&&mfCusPersBaseInfo.commAddress!=''">
													${mfCusPersBaseInfo.commAddress}
												</s:if>
												<s:else>
													<span class="unregistered">未登记</span>
												</s:else>
											</span>
											<span class="vertical-line"></span>
											<i class="i i-youjian1 "></i>
											<span id = "postalCode">
												<s:if test="mfCusCustomer.postalCode!=null&&mfCusCustomer.postalCode!=''">
													${mfCusCustomer.postalCode}
												</s:if>
												<s:else>
													<span class="unregistered">未登记</span>
												</s:else>
											</span>
										</p>
								</div>
							</div>
						</div>
					</div>
			
					<s:if test='%{operable != "query"}'>
						<!--信息登记操作入口 -->
						<div class="row clearfix btn-opt-group">
							<div class="col-xs-12 column">
								<div class="btn-group pull-right">
									<button class="btn btn-opt cus-add" onclick="MfCusDyForm.updateCusFormStas();" stype="display:none;" type="button">
										<i class="i i-bi"></i><span>完善资料</span>	
									</button>
									<!-- <button class="btn btn-opt"  onclick="applyInsert();">
										<i class="i i-jia2"></i><span>新增业务</span>
									</button> -->
									<button class="btn btn-opt" onclick="applyBigPage.applyInsertByBigPage('${cusNo}');">
										<i class="i i-jia2"></i><span>新增业务</span>
									</button>


	<!-- 								<button class="btn btn-opt"  onclick="addService();"> -->
	<!-- 									<i class="i i-xinxihecha"></i><span>联网核查</span> -->
	<!-- 								</button> -->
									<%-- <dhcc:pmsTag pmsId="person-cw-report-btn">
										<button class="btn btn-opt" onclick="getPersonPfsDialog();" type="button">
											<i class="i i-qian1" ></i><span>名下企业财务报表</span>	
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-eval-btn">
										<button class="btn btn-opt" onclick="getInitatEcalApp();" type="button">
											<i class="i i-eval1"></i><span>发起评级</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-auth-btn">
										<button class="btn btn-opt" onclick="getAppAuth();" type="button">
											<i class="i i-credit"></i><span>发起授信</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-report-auth-btn">
										<button id="reportApply" class="btn btn-opt" onclick="reportApply();" type="button">
											<i class="i i-credit"></i><span>贷前报告</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="cus-relations-btn">
										<button class="btn btn-opt" onclick="cusRelationIn();" type="button">
											<i class="i i-guanXi"></i><span>关联关系</span>	
										</button>
									</dhcc:pmsTag> --%>
									<!-- <div class="btn-group">
										<button type="button" class="btn btn-opt hidden-lg dropdown-toggle"  data-toggle="dropdown">
											更多<span class="caret"></span>
										</button>
										<ul class="dropdown-menu btn-opt pull-right" role="menu">
											<li class="btn-opt hidden-lg" role="presentation" onclick="cusTag();">
												<button class="btn btn-opt more-btn-opt" onclick="cusTag();" type="button">
													<i class="i i-ren"></i><span id="cusTag">客户分类</span>	
												</button>
											</li>								
										</ul>
									</div> -->
									<%-- <div class="btn-group">
										<button type="button" class="btn btn-opt  dropdown-toggle"  data-toggle="dropdown">
											更多<span class="caret"></span>
										</button>
										<ul class="dropdown-menu btn-opt pull-right" role="menu">
											<dhcc:pmsTag pmsId="cus-tracing-btn">
												<li class="btn-opt" role="presentation" onclick="cusTrack('0');">
												<button class="btn btn-opt more-btn-opt"  type="button">
													<i class="i i-dianhua"></i><span>客户跟进</span>
												</button>
											</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="cus-classify-btn">
											<li class="btn-opt" role="presentation" onclick="cusTag();">
												<button class="btn btn-opt more-btn-opt" type="button">
													<i class="i i-ren"></i><span id="cusTag">客户分类</span>	
												</button>
											</li>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="cus-channel-self-putout-set-btn">
											<li class="btn-opt" role="presentation" onclick="channelSelfPutoutSet();">
												<button class="btn btn-opt more-btn-opt" type="button">
													<i class="i i-chilun"></i><span id="cusTag">放款控制</span>	
												</button>
											</li>
										</dhcc:pmsTag>
										</ul>
									</div> --%>
								</div>
							</div>
						</div>
					</s:if>
					<!--客户其他信息-->
					<!--个人名下企业财务报表-->
						<div class="row clearfix">
							<div class="col-xs-12 column info-block">
								<!-- 评分卡 -->
								<%-- <%@ include file="/component/eval/AppEval_evalCardDetail.jsp" %> --%>
								<div class="block-add" style="display: none;"></div>
								<%-- <s:if test="cusFinMainList.size() != 0">
									<%@ include file="/component/cus/MfCusPersonFinData_Detail.jsp" %>
								</s:if> --%>
							</div>
						</div>
					<div class="row clearfix">
						<div class="col-xs-12 column" >
 							<%@ include file="/component/doc/webUpload/pub_uploadForMainPage.jsp"%>
 						</div>
					</div>
					<s:if test='%{operable != "query"}'> 
						<div class="row clearfix">
							<div class="col-xs-12 column" >
								<div id="rotate-body"></div>
							</div>
						</div> 
					</s:if>
					<!--评级 授信审批历史 -->
					<%-- <s:if test='%{evalCredit=="evalCredit"}'> 
				 	<div class="row clearfix" id="spInfo-block">
						<div class="form-table">
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
					</s:if> --%>
				</div>
			</div>
			<!--客户附属信息-->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<jsp:include page="/component/app/MfBusApply_TrenchAbstractInfo.jsp?cusNo=${cusNo}&appId=${appId}&pleId=${pleId}&type=${type}&fincId=${fincId}&creditId=${creditId}&bidissueId=${bidissueId}"/>
					<%-- 
					<jsp:include page="/component/collateral/MfBusCollateralRel_AbstractInfo.jsp?relId=${appId}&busModel=${busModel}&operable=${operable}"/>
					 --%>
					<s:if test='%{appId==null||appId==""}' > 
					<!--客户跟踪-->
					<%-- <dhcc:pmsTag pmsId="mf-cus-follow">
						<jsp:include page="/component/cus/custracing/MfCusTrack_pub.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag> --%>
					<!--历史业务统计-->
					<jsp:include page="/component/pact/MfBusPact_Statistics.jsp?cusNo=${cusNo}"/>	
						<!--信息变更记录-->
					<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${cusNo}"/>
					</s:if>
					<s:else>
					<!--信息变更记录-->
					<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${cusNo}"/>
					<!--历史业务统计-->
					<jsp:include page="/component/pact/MfBusPact_Statistics.jsp?cusNo=${cusNo}"/>
					<!--客户跟踪-->
					<%-- <dhcc:pmsTag pmsId="mf-cus-follow">
						<jsp:include page="/component/cus/custracing/MfCusTrack_pub.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag> --%>
					</s:else>	
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusDyForm.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_TrenchCusDetail.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/cus/creditquery/js/MfCreditQueryRecordInfo.js"></script>
</html>