<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	var webPath='${webPath}';
	var basePath="${webPath}";
</script>
<script type="text/javascript" src="${webPath}/component/examine/js/MfLoanAfterConfig.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/prdct/js/MfKindConfig.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/set.css" />
<link rel="stylesheet" href="${webPath}/themes/factor/css/filter.css?v=${cssJsVersion}" />
<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<link id="MfKindConfig" rel="stylesheet" type="text/css" href="${webPath}/component/prdct/css/MfKindConfig${skinSuffix}.css?v=${cssJsVersion}">
<script type="text/javascript" src="${webPath}/component/examine/js/ExtensionBussConfig.js?v=${cssJsVersion}"></script>
<title>列表</title>
<script type="text/javascript">
		var ajaxData = '${ajaxData}';
		ajaxData = JSON.parse(ajaxData);
		var docBizSceConfigListData=ajaxData.docBizSceConfigListData;
		var mfExamineTemplateConfigListData=ajaxData.mfExamineTemplateConfigListData;
        var mfExamineTemplateConfigRiskListData=ajaxData.mfExamineTemplateConfigRiskListData;
		var docBizSceConfigData=ajaxData.docBizSceConfigData;
		var examineDocTemplateData=ajaxData.examineDocTemplateData;
		var extenApproveFlow=ajaxData.extenApproveFlow;
		var extensionNodeList=ajaxData.extensionNodeList;
		var jsonNodeConfig=ajaxData.jsonNodeConfig;
		var mfSysFeeItemList=ajaxData.mfSysFeeItemList;
		var countDef = 5;
		$(function(){
			MfLoanAfterConfig.init();
			ExtensionBussConfig.init();
	    	 //调用时间轴
			ne.createNavLine();
		});
	</script>
</head>
<body class="bg-white">
	<div class="container">
		<div class="row clearfix config-div">
			<!-- 贷后流程配置 begin -->
			<dhcc:pmsTag pmsId="set-loan-wkf-flow-conf">
				<div class="list-item col-md-9">
					<div class="title-div">
						<ol class="breadcrumb pull-left padding_left_0" id="flowConfig">
							<li class="active"><span name="title">保后流程设置</span></li>
						</ol>
					</div>
					<div id="flow-content-div" class="content-div">
						<div class="sub-content-div padding_left_15 margin_top_15">
							<c:forEach items="${loanAfterflowList}" var="kindFlow" varStatus="status">
								<div class="item-div">
									<div class="item-title  margin_bottom_10">
										<span class="item-checkbox">
											<c:choose> 
											  <c:when test="${kindFlow.useFlag==1}">   
												 <span class="checkbox-span curChecked" data-id="${kindFlow.kindFlowId}" data-flowid="${kindFlow.flowId}" onclick="MfKindConfig.updateFlowUseFlag(this,'','1');">
													<i class="i i-gouxuan1"></i>
												</span>
											  </c:when> 
											  <c:otherwise>   
												<span class="checkbox-span" data-id="${kindFlow.kindFlowId}" data-flowid="${kindFlow.flowId}" onclick="MfKindConfig.updateFlowUseFlag(this,'','0');">
													<i class="i i-gouxuan1"></i>
												</span>
											  </c:otherwise> 
											</c:choose>
											<span>启用${kindFlow.flowApprovalName}</span>
										</span>
									</div>
									<div class="item-content margin_bottom_15 padding_left_15">
										<div id="processItem${kindFlow.kindFlowId}" class="padding_left_15">当前流程：<span id="${kindFlow.flowApprovalNo}">${kindFlow.flowRemark}</span>
											<c:choose> 
											  <c:when test="${kindFlow.useFlag==1}">   
												<a href="javascript:void(0);" onclick="MfKindConfig.openProcessDesigner('${kindFlow.flowId}')" class="padding_left_15 pointer">配置</a>
											  </c:when> 
											  <c:otherwise>   
												<a href="javascript:void(0);" class="link-disabled padding_left_15 pointer">配置</a>
											  </c:otherwise> 
											</c:choose>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<!-- 贷后流程配置 end -->
			
			<!-- 贷后参数配置 begin -->
			<dhcc:pmsTag pmsId="set-loan-parm-conf">
				<div class="list-item col-md-9">
					<div class="title-div">
						<ol class="breadcrumb pull-left padding_left_0" id="flowConfig">
							<li class="active"><span name="title">保后参数设置</span></li>
						</ol>
					</div>
					<div id="param-content-div" class="content-div">
						<div class="sub-content-div padding_left_15 margin_top_15">
							<div class="item-div">
							
							<div class="p-title margintop10 marginbottom10"><span class="content_title">提前还款设置</span></div>
								
								<div class="item-title  margin_bottom_10">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="item-checkbox">
										<c:choose> 
										  <c:when test="${preRepayApplyFlag==1}">   
											  <span class="checkbox-span curChecked" data-id="PRE_REPAY_APPLY_FLAG" data-useflag="1" onclick="MfLoanAfterConfig.updateParamUseFlag(this,'PRE_REPAY_APPLY_FLAG');">
												<i class="i i-gouxuan1"></i>
											</span>
										  </c:when> 
										  <c:otherwise>   
											<span class="checkbox-span" data-id="PRE_REPAY_APPLY_FLAG"  data-useflag="0" onclick="MfLoanAfterConfig.updateParamUseFlag(this,'PRE_REPAY_APPLY_FLAG');">
												<i class="i i-gouxuan1"></i>
											</span>
										  </c:otherwise> 
										</c:choose>
										<span>启用提前还款申请</span>
									</span>
								</div>
								<div class="item-title  margin_bottom_10">
								
								
								
								<div class="p-title margintop10 marginbottom10"><span class="content_title">核销参数设置</span></div>
								
									<span class="item-checkbox">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据源来源设置： 
										<c:choose> 
										  <c:when test="${checkoffDatasource==1}">   
												<input class="margin_right_5" type="radio" id='checkoffDatasource_1' name=checkoffDatasource value="1" onclick="MfLoanAfterConfig.updateCheckSetting('#checkoffDatasource_1');"  checked="checked">五级分类
												<input class="margin_right_5" type="radio" id='checkoffDatasource_2'  name="checkoffDatasource" value="2"  onclick="MfLoanAfterConfig.updateCheckSetting('#checkoffDatasource_2');" >逾期天数  
													<span style="display:none;" id="overdaysSettingSpan" >
														<input type="text" name="checkoffDatasourceOverDays" onchange="MfLoanAfterConfig.updateCheckSetting('#checkoffDatasource_2');" id="checkoffDatasourceOverDays"  value="${checkoffDatasourceOverDays}">天
													</span>
										  </c:when> 
										  <c:otherwise>   
												<input class="margin_right_5" type="radio"  id='checkoffDatasource_1' name="checkoffDatasource"  onclick="MfLoanAfterConfig.updateCheckSetting('#checkoffDatasource_1');" value="1" >五级分类
												<input class="margin_right_5" type="radio"  id='checkoffDatasource_2' name="checkoffDatasource"  onclick="MfLoanAfterConfig.updateCheckSetting('#checkoffDatasource_2');" value="2" checked="checked">逾期天数     
												<span  id="overdaysSettingSpan" >
													<input type="text" name="checkoffDatasourceOverDays" id="checkoffDatasourceOverDays"  onchange="MfLoanAfterConfig.updateCheckSetting('#checkoffDatasource_2');"   value="${checkoffDatasourceOverDays}">天
												</span>
										  </c:otherwise> 
										</c:choose>
										</br>
										</br>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="item-checkbox">
										<c:choose> 
										  <c:when test="${finishAfterPrcpCheck==1}">   
											<span class="checkbox-span curChecked" data-id="FINISH_AFTER_PRCP_CHECK"  data-useflag="1" onclick="MfLoanAfterConfig.updateParamUseFlag(this,'FINISH_AFTER_PRCP_CHECK');">
												<i class="i i-gouxuan1"></i>
											</span>
										  </c:when> 
										  <c:otherwise>   
											 <span class="checkbox-span" data-id="FINISH_AFTER_PRCP_CHECK" data-useflag="0" onclick="MfLoanAfterConfig.updateParamUseFlag(this,'FINISH_AFTER_PRCP_CHECK');">
												<i class="i i-gouxuan1"></i>
											</span>
										  </c:otherwise> 
										</c:choose>
										<span>本金核销后是否结清贷款</span>
									</span>
										
										
									
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<!-- 贷后参数配置 end -->
			
			<!-- 贷后检查模型配置 begin -->
			<dhcc:pmsTag pmsId="set-loan-check-conf">
				<div class="list-item col-md-9">
					<div class="title-div">
						<ol class="breadcrumb pull-left padding_left_0" id="cusFormConfig">
							<li class="active"><span name="title">保后跟踪模型配置</span></li>
							<span class="btn btn-link config-font" id="cusTypeAdd"
								onclick="MfLoanAfterConfig.addExamineTemplateConfig('0');"><i class="i i-jia2"></i></span>
						</ol>
					</div>
					<div id="examine-content-div" class="content-div">
						<c:forEach items="${mfExamineTemplateConfigList}" var="mfExamineTemplateConfig" varStatus="status">
							<div id="base${mfExamineTemplateConfig.templateId}" class="sub-content-div padding_left_15">
								<div class="sub-title">
									<span id="templateName${mfExamineTemplateConfig.templateId}">
									<a id="edit_${mfExamineTemplateConfig.templateId}" class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.editExamineTemplateConfig(${mfExamineTemplateConfig.templateId})">
									${mfExamineTemplateConfig.templateName}
									</a>
									</span>
									<span id="remark${mfExamineTemplateConfig.templateId}" class="font-weight-normal">${mfExamineTemplateConfig.remark}</span>
									<a class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.setExamConfig(${mfExamineTemplateConfig.templateId})">检查项</a>
										<a class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.examFormDesign(${mfExamineTemplateConfig.templateId})">表单配置</a>
								</div>
							</div>
							<div id="uploadInfo${mfExamineTemplateConfig.templateId}" class="sub-content-div padding_left_15 bus-config max-width">
							</div>
							<div id="docTemplateInfo${mfExamineTemplateConfig.templateId}" class=" sub-content-div padding_left_15 template-config bus-config max-width" style="margin-left:15px">
								<div class="sub-title">
								<span class="font-weight-normal">模板配置</span>
								</div>
								<div id="docTemplateContent${mfExamineTemplateConfig.templateId}" class="sub-content margin_bottom_10 padding_left_15">
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</dhcc:pmsTag>
			<!-- 贷后检查模型配置 end -->

			<!-- 风控稽核模型配置 begin -->
			<dhcc:pmsTag pmsId="risk-audit-conf">
				<div class="list-item col-md-9">
					<div class="title-div">
						<ol class="breadcrumb pull-left padding_left_0" id="riskAauditConfig">
							<li class="active"><span name="title">风控稽核模型配置</span></li>
							<span class="btn btn-link config-font" id="cusTypeAdd1"
								  onclick="MfLoanAfterConfig.addExamineTemplateConfig('1');"><i class="i i-jia2"></i></span>
						</ol>
					</div>
					<div id="audita-content-div" class="content-div">
						<c:forEach items="${mfExamineTemplateConfigRiskList}" var="mfExamineTemplateConfig" varStatus="status">
							<div id="base${mfExamineTemplateConfig.templateId}" class="sub-content-div padding_left_15">
								<div class="sub-title">
									<span id="templateName${mfExamineTemplateConfig.templateId}">
									<a id="edit_${mfExamineTemplateConfig.templateId}" class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.editExamineTemplateConfig(${mfExamineTemplateConfig.templateId})">
											${mfExamineTemplateConfig.templateName}
									</a>
									</span>
									<span id="remark${mfExamineTemplateConfig.templateId}" class="font-weight-normal">${mfExamineTemplateConfig.remark}</span>
									<a class="config-font" href="javascript:void(0);" onclick="MfLoanAfterConfig.examFormDesign(${mfExamineTemplateConfig.templateId})">表单配置</a>
								</div>
							</div>
							<div id="uploadInfo${mfExamineTemplateConfig.templateId}" class="sub-content-div padding_left_15 bus-config max-width">
							</div>
							<div id="docTemplateInfo${mfExamineTemplateConfig.templateId}" class=" sub-content-div padding_left_15 template-config bus-config max-width" style="margin-left:15px">
								<div class="sub-title">
									<span class="font-weight-normal">模板配置</span>
								</div>
								<div id="docTemplateContent${mfExamineTemplateConfig.templateId}" class="sub-content margin_bottom_10 padding_left_15">
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</dhcc:pmsTag>
			<!-- 风控稽核模型配置 end -->

			<!--贷后风险级别设置 begin-->
			<dhcc:pmsTag pmsId="set-grade-class-conf">
			<div class="list-item col-md-9">
				<div class="title-div">
					<ol class="breadcrumb pull-left padding_left_0" id="evalScoreGradeConfig">
						<li class="active"><span name="title">保后风险级别设置</span></li>
					</ol>
				</div>
				<div id="ScoreGradeContent_table_div" class="content-div">
					<div class="sub-content-div padding_left_15 margin_top_15">
						<div class="item-div">
							<c:forEach items="${mfExamRiskLevelConfigList}" var="mfExamRiskLevelConfig" varStatus="status">
								<div class="item-title margin_bottom_10">
									<p class="">
										<span class="level-a">${mfExamRiskLevelConfig.examRiskLevelName}</span>
										<%-- <span id="opScore${configId" />">检查分值 <s:property value="opScore1" />--<s:property value="opScore2}</span> --%>
										<span>
											<a href="javascript:void(0);" onclick="MfLoanAfterConfig.editExamRiskLevel(${mfExamRiskLevelConfig.configId});return false;" class="config-font">配置</a>
										</span>
									</p>
									<p id= "remark${mfExamRiskLevelConfig.configId}">
										${mfExamRiskLevelConfig.remark}
									</p>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			</dhcc:pmsTag>
			<!-- 展期配置 start -->
			<dhcc:pmsTag pmsId="set-loan-extension-conf">
				<div id="extensionConfig_div" class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0" id="extensionConfig">
								<li class="active"><span name="title">展期设置</span></li>
							</ol>
						</div>
				</div>
			</dhcc:pmsTag>
			<!-- 展期配置 end -->
			<!-- 还款配置 begin -->
			<dhcc:pmsTag pmsId="set-loan-repayment-conf">
				<div class="list-item col-md-9">
					<div class="title-div">
						<ol class="breadcrumb pull-left padding_left_0" id="repaymentConfig">
							<li class="active"><span name="title">还款配置</span></li>
						</ol>
					</div>
					<div id="examine-content-div" class="content-div">
							<div id="normalRepayment" class="sub-content-div padding_left_15">
								<div class="sub-title">
									<span id="normalRepayment-title" class="font-weight-normal">正常还款</span>
								</div>
							</div>
							<div id="uploadInfo-normalDoc" class="sub-content-div padding_left_15 bus-config max-width">
							</div>
							
							<div id="advanceRepayment" class="sub-content-div padding_left_15">
								<div class="sub-title">
									<span id="advanceRepayment-title" class="font-weight-normal">提前还款</span>
								</div>
							</div>
							<div id="uploadInfo-advanceDoc" class="sub-content-div padding_left_15 bus-config max-width">
							</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<!-- 还款配置 end -->
		</div>
		<!--贷后风险级别设置 end-->
		<!-- 导航轴 -->
		<div class="row clearfix">
			<div class="work-zone-timeLine" style="position: fixed; margin-top: -78px; margin-right: 79px;">
				<div class="time_contents">
					<div class="time-line-bg">
						<div class="time-line-line"></div>
						<div class="time-line-body">
							<dl class="time-line-dl"></dl>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 导航轴 end -->
	</div>
</body>
</html>
