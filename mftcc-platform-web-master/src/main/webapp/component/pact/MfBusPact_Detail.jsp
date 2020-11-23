<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/common.jsp"%>
<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pact/css/MfBusPact_Detail.css?v=${cssJsVersion}" />
		<script type="text/javascript">
		var menuBtn=[],menuUrl=[];
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData")%>'+")");
		var vpNo = '3';
		var pactId,appId,wkfAppId,cusNo,pactSts,fincSts,fincId,pleId;
		var wkfFlag;//表示工作流是否还有节点；0结束，1还有节点
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var busModel = '${busModel}';
		var coreCusNo = '${coreCusNo}';
		var fundCusNo = '${fundCusNo}';
		var wareHouseCusNo = '${wareHouseCusNo}';
		var pactModelNo = '${mfBusPact.kindNo}';
		var pactId = '${mfBusPact.pactId}';
		var pactNo = '${mfBusPact.pactNo}';
		var cusNo = '${mfBusPact.cusNo}';
		var appId = '${mfBusPact.appId}';
		var wkfAppId = '${mfBusFincApp.wkfRepayId}';
		var pactSts = '${mfBusPact.pactSts}';//合同状态值参考BizPubParm中的字段PACT_STS**
		var fincSts = '${mfBusFincApp.fincSts}';
		var fincId = '${mfBusFincApp.fincId}';
		var fincShowId = '${mfBusFincApp.fincShowId}';
		var wkfRepayId = '${mfBusFincApp.wkfRepayId}';
		var pleId = '${mfBusPledge.pleId}';
		var term = '${mfBusPact.term}';
		var termType = '${mfBusPact.termType}';
		var hisTaskList = eval("(" + '${json}' + ")").hisTaskList;
		var cusNoTmp = "";
		var webPath = '${webPath}';
		var query = '${query}';
		var operable = '${operable}';
		var docParm = "query=query"+"&cusNo="+cusNo+"&relNo="+cusNo+"&appId="+appId+"&pactId="+pactId;//查询文档信息的url的参数
		var hasLawsuit = '${mfBusPact.hasLawsuit}';
		var fiveclassId = "";
		var recParam = <%=request.getAttribute("recParam")%>;
		var hasRecallFlag = recParam.hasRecallFlag;
		var recallingFlag = recParam.recallingFlag;
		var entrance = "business";
		var operateflag='${operateflag}';
		var ifShowRepayHistory='${ifShowRepayHistory}';
		var examResultFlag=false;//是否能查看贷后检查历史标识
		var busEntrance ="${busEntrance}";//业务入口
        var examEntrance = 'pact';//贷后检查入口标识（合同）
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<!--合同主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<c:if test='${operable=="operable"}'> 
						<div class="row clearfix">
							<div class="app-process hide">
								<div id="modeler1" class="modeler">
									<ul id="wj-modeler1" class="wj-modeler" isApp = "true">
									</ul>
								</div>
							</div>
						</div>
						<div  class="row clearfix btn bg-danger next-div hide">
							<div class="col-xs-12 column text-center">
								<div class="block-next"></div>
							</div>
						</div>
					</c:if>
					<!--业务主要信息 -->
					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img">
							<div>
								<button type="button" class="btn btn-font-pact font-pact-div padding_left_15">
									<i class="i i-pact font-icon"></i>
									<div class="font-text-left">合同信息</div>
								</button>
							</div>
							<c:if test="${fn:length(mfBusPact.kindName)>8 }">
								<div class="btn btn-link" onclick="getKindInfo();"
									title="${mfBusPact.kindName}">
									${fn:substring(mfBusPact.kindName,0,8)} ...
								</div>
							</c:if>
							<c:if test="${fn:length(mfBusPact.kindName)<=8 }">
								<div class="btn btn-link" onclick="getKindInfo();">${mfBusPact.kindName}</div>
							</c:if>
						</div>
						<!--概要信息 -->
						<div class="col-xs-9 column head-content">
							<div></div>
							<div class="margin_bottom_5 clearfix">
								<div>
									<c:if test="${dataMap.moreCount>1}">
									<div class="bus-more head-title pull-left" title="${mfBusPact.appName}">${mfBusPact.appName}</div>
										<div class="multi-bus pull-left">
											客户共有 <a class="moreCnt more-apply-count pointer"  onclick="getMultiBusList();">${dataMap.moreCount}</a> 笔在履行业务
										</div>
									</c:if>
									<c:if test="${dataMap.moreCount<=1}">
										<div class="bus head-title pull-left" title="${mfBusPact.appName}">${mfBusPact.appName}</div>
									</c:if>
								</div>
							</div>
							<div class="margin_bottom_10">
								<!--法律诉讼0-->
								<dhcc:pmsTag pmsId="loan-law-suit">
									<button id="has-lawsuit"  class="btn btn-lightgray btn-view" type="button" onclick="getLawsuitList();">
										<i class="i i-falv"></i><span>法律诉讼</span>
									</button>
								</dhcc:pmsTag>
								<!-- 五级分类 -->
								<dhcc:pmsTag pmsId="loan-five-class">
									<button id="fiveclass" class="btn pact-fiveclass btn-view" type="button" onclick="fiveclassView();">
										<i class="i i-fenlei" ></i><span id="fiveclass-i">五级分类</span>
									</button>
								</dhcc:pmsTag>
									<button id="examineResult" class="btn pact-fiveclass btn-view" type="button" onclick="BusExamine.examineDetailResult();">
										<i class="i i-fangdajing" ></i><span id="examineResultTitle">保后检查</span>
									</button>
								<button id="recallbase"  class="btn btn-danger btn-view hide" type="button" onclick="getRecallInfo();">
									<i class="i i-time"></i><span id="recallspan">待催收</span>
								</button>
							</div>
							<!--信息查看入口 -->
							<div>
								<table>
									<tr>
										<td>
											<p class="cont-title">合同金额</p>
											<p><span class="content-span">${mfBusPact.fincAmt}</span><span class="unit-span margin_right_25">万</span> </p>
										</td>
										<td>
											<p class="cont-title">合同余额</p>
											<p><span id="term" class="content-span"></i>${dataMap.pactBal}</span><span class="unit-span margin_right_25">万</span> </p>
										</td>
										<td>
											<p class="cont-title">逾期天数</p>
											<p><span class="content-span">${dataMap.overDays}</span><span class="unit-span">天</span></p>
										</td>
									</tr>
								</table>
							</div>
							<div class="btn-special">
								<c:if test='${wareHouseCusNo!=null && wareHouseCusNo!="" && wareHouseCusNo!="0"}'>
									<span  class="relate-corp" data-view='cuswarehouse'>
										<i class="i i-cangKu"></i><span>由仓储机构<a href="javascript:void(0);" onclick="getInfoForView('103','${wareHouseCusNo}','仓储机构');">${mfBusApply.cusNameWarehouse}</a>保管货物 </span>
									</span>
								</c:if>
								<c:if test='${coreCusNo!=null && coreCusNo!="" && coreCusNo!="0"}'>
									<span class="relate-corp" data-view='cuscore'>
										<i class="i i-qiYe"></i><span>由核心企业 <a href="javascript:void(0);"  onclick="getInfoForView('108','${coreCusNo}','核心企业');">${mfBusApply.cusNameCore}</a> 推荐</span>
									</span>
								</c:if>
								<c:if test='${fundCusNo!=null && fundCusNo!="" && fundCusNo !="0"}'>
									<span class="relate-corp" data-view='fundorg' >
										<i class="i i-fundorg"></i><span>由资金机构 <a href="javascript:void(0);" onclick="getInfoForView('109','${fundCusNo}','资金机构 ');">${mfBusApply.cusNameFund}</a> 放款</span>
									</span>
								</c:if>
							</div>
						</div>
					</div>
					<c:if test='${operable=="operable"}'> 
						<!--信息登记操作入口 -->
						<div class="row clearfix btn-opt-group">
							<div class="col-xs-12 column">
								<div class="btn-group pull-right">
									<!-- <button class="btn btn-opt" onclick="Collateral.editCollateral('pactDeatil');" type="button">
										<i class="i i-huankuan"></i><span>应收账款管理</span>
									</button> -->
									
									<dhcc:pmsTag pmsId="loan-opt-btn">
									
										<button class="btn btn-opt-dont" id="repay" onclick="repayment();" type="button"  disabled >
											<i class="i i-huankuan"></i><span>还款操作</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="loan-check-btn">
										<button class="btn btn-opt" id="loanAfterExamine" onclick="BusExamine.loanAfterExamine();" type="button">
											<i class="i i-fangdajing"></i><span>贷后检查</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="loan-collection-btn">
										<button class="btn btn-opt" id="recallRegist" onclick="recallRegist();" type="button">
											<i class="i i-time"></i><span>催收登记</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="loan-five-class-btn">
										<button class="btn btn-opt" id="fiveclassUpdate" style="display:none" onclick="fiveclassUpdate();" type="button">
											<i class="i i-fenlei"></i><span>五级分类</span>
										</button>
										<button class="btn btn-opt" id="fiveclassInsert"  onclick="fiveclassInsert();" type="button">
											<i class="i i-fenlei"></i><span>五级分类</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="loan-file-print-btn">
										<button class="btn btn-opt" onclick="filePrint();" type="button">
											<i class="i i-x"></i><span>文件打印</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="loan-file-filing-btn">
										<button class="btn btn-opt" onclick="fileArchive();" type="button">
											<i class="i i-guidang"></i><span>文件归档</span>
										</button>
									</dhcc:pmsTag>
									 <dhcc:pmsTag pmsId="risk-registration-btn">
										<button class="btn btn-opt" type="button"  onclick="riskRegistration();" id="riskRegistrationButton">
											<i class="i i-bi1"></i><span>风险登记</span>
										</button>
									</dhcc:pmsTag>
									<c:if test='%{mfBusPact.pactSts=="7"}'> 
										<dhcc:pmsTag pmsId="loan-lawsuit-btn">
										
											<button class="btn btn-opt" onclick="lawsuit();" type="button" id="lawsuitAdd">
												<i class="i i-falv"></i><span>法律诉讼</span>
											</button>
										</dhcc:pmsTag>
	 								</c:if> 
									<c:if test='${mfBusPact.pactSts!="7"}'> 
										<div class="btn-group">
											<button type="button" class="btn btn-opt  dropdown-toggle"  data-toggle="dropdown">
												更多<span class="caret"></span>
											</button>
											<ul class="dropdown-menu btn-opt pull-right" role="menu">
												<dhcc:pmsTag pmsId="loan-lawsuit-btn">
												
													<li class="btn-opt" role="presentation" onclick="lawsuit();">
														<button class="btn btn-opt more-btn-opt" type="button">
															<i class="i i-falv"></i><span>法律诉讼</span>
														</button>
													</li>
												</dhcc:pmsTag>
											</ul>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</c:if>
					<!--合同表单信息 -->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="form-table base-info">
								<c:if test='${cusBaseFlag!="0"}'>
									<div class="content" id="pactDetailInfo">
										<form method="post" theme="simple" name="operform" action="${webPath}/mfBusPact/updateAjaxByOne">
											<dhcc:propertySeeTag property="formpact0004" mode="query" />
										</form>
									</div>
								</c:if>
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
					   		<div class="form-table">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>放款申请</span>
									<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#fincappchild">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
								</div>
								<div class="content collapse in" style="margin-top:15px;" id="fincappchild" name="fincappchild">
									<form id="fincAppDetail" method="post" theme="simple" name="operform" >
										<dhcc:propertySeeTag property="formfincapp0002" mode="query" />
									</form>
								 </div>	
							 </div>								
					     </div>
				    </div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="list-table-replan">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>收款计划</span>
									<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfRepayPlanList">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
								</div>
								<div class="content margin_left_15 collapse in" id="mfRepayPlanList">
									<dhcc:thirdTableTag property="tablerepayplan0003" paginate="mfRepayPlanList" head="true"></dhcc:thirdTableTag>
								</div>
							</div>
				 		</div>	
					</div>
				
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="list-table-replan base-info" style="display:none" id="repayHistoryList">
								   <div class="title">
									 <span>
									 	<i class="i i-xing blockDian"></i>
									 	还款历史
									 </span>
									 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfRepayHistoryList">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
								   </div>
								   <div class="content margin_left_15 collapse in" id="mfRepayHistoryList">
									 <dhcc:thirdTableTag property="tablerepayhis0001" paginate="mfRepayHistoryList" head="true"></dhcc:thirdTableTag>
								   </div>
							</div>
						</div>
					</div>
						<div class="row clearfix">
						<div class="col-xs-12 column">
								<div class="list-table">
									<div class="title">
										<span>
										<i class="i i-xing blockDian"></i>
										检查历史</span>
										<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#examineHis">
											<i class='i i-close-up'></i>
											<i class='i i-open-down'></i>
										</button>
									</div>
									<div class="content margin_left_15 collapse in" id="examineHis">
										<dhcc:thirdTableTag paginate="mfExamineHisList" property="tableexamhis0001" head="true" />
									</div>
								</div>
				 		</div>	
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column" >
 							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
 						</div>
					</div>
					
				</div>
			</div>
			<!--合同 附属信息 -->
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<jsp:include page="/component/cus/MfCusCustomer_AbstractInfo.jsp?cusNo=${mfBusPact.cusNo}&type=2&fincId=${mfBusFincApp.fincId}"/>
					<jsp:include  page="/component/collateral/MfBusCollateralRel_AbstractInfo.jsp?relId=${mfBusPact.appId}&busModel=${busModel}&operable=${operable}&cusNo=${mfBusPact.cusNo}&type=2&fincId=${mfBusFincApp.fincId}"/>
					<!--信息变更记录-->
					<dhcc:pmsTag pmsId="mf-message-change-record">
						<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${mfBusPact.cusNo}"/>
					</dhcc:pmsTag>					
					<!--历史业务统计-->
					<dhcc:pmsTag pmsId="mf-history-service">
						<jsp:include page="/component/pact/MfBusPact_Statistics.jsp?cusNo=${mfBusPact.cusNo}"/>
					</dhcc:pmsTag>
					<!--客户跟踪-->
					<dhcc:pmsTag pmsId="mf-cus-follow">
						<jsp:include page="/component/cus/custracing/MfCusTrack_pub.jsp?cusNo=${mfBusPact.cusNo}"/>
					</dhcc:pmsTag>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusPact_Detail.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/collateral.js"></script>
<script type="text/javascript" src="${webPath}/component/examine/js/BusExamine.js"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfPrint.js"></script>
</html>