<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<%@page import="app.component.common.BizPubParm"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript"
	src="${webPath}/component/cus/js/MfCusConfig.js"></script>
<script type="text/javascript"
	src="${webPath}/component/prdct/js/MfKindConfig.js?v=${cssJsVersion}"></script>
<link rel="stylesheet" href="${webPath}/themes/factor/css/set.css" />
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/filter.css?v=${cssJsVersion}" />
<link id="B1" rel="stylesheet" type="text/css"
	href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<link id="MfKindConfig"  rel="stylesheet" type="text/css"
	href="${webPath}/component/prdct/css/MfKindConfig${skinSuffix}.css?v=${cssJsVersion}">
<title>列表</title>
</head>
<body class="bg-white">
	<div class="container">
		<!-- 客户配置信息 begin -->
		<div class="row clearfix config-div">
			<!-- 客户表单配置 -->
			<dhcc:pmsTag pmsId="set-cus-form-conf">
				<div class="list-item col-md-9">
					<div class="title-div">
						<ol class="breadcrumb pull-left padding_left_0" id="cusFormConfig">
							<li class="active"><span name="title">客户表单配置</span></li>
							<span class="btn btn-link config-font" id="cusTypeAdd"
								onclick="MfCusConfig.addCusType();"><i class="i i-jia2"></i></span>
						</ol>
					</div>
					<div class="content-div">
						<div class="sub-content-div padding_left_15 margin_top_15">
							<div class="item-div" id="content_table_nmd">
								<c:forEach items="${cusTypeList }" var="cusType">
									<div class="item-title margin_bottom_10" id="typeNo${cusType.typeNo }">
										<span style="min-width: 63px; display: inline-block;">[${cusType.baseType }]
										</span>
										<c:if test='${cusType.isBase!="1"}'>
											<span class="color_theme">${cusType.typeName }</span>
										</c:if>
										<c:if test='${cusType.isBase=="1"}'>
											<span>${cusType.typeName }</span>
										</c:if>
										信息采集与信息展示
                                        <c:choose>
                                            <c:when test="${cusType.useFlag == '1'}">
                                                <a class="config-font" href="#"
                                                   onclick="MfCusConfig.editCusType('${cusType.typeNo }');">编辑</a>
                                                <a class="config-font"
                                                   href="${webPath}/mfCusFormConfig/getAllFormConList?fromPage=${fromPage}&optCode=${cusType.typeNo }">表单配置</a>
                                                <a class="config-font"
                                                   href="JavaScript:newDocModel('/mfCusFormConfig/getEditDocPage?optCode=${cusType.typeNo }');">要件配置</a>
                                                <a class="config-font"
                                                   href="JavaScript:newTemplateModel('/mfCusFormConfig/getEditTemplatePage?optCode=${cusType.typeNo }');">模板配置</a>
												<c:if test='${cusType.baseType=="渠道商"}'>
													<dhcc:pmsTag pmsId="set-trench-user-doc">
													<a class="config-font"
													   href="JavaScript:newDocModel('/mfCusFormConfig/getUserEditDocPage?optCode=2');">渠道操作员要件配置</a>
													</dhcc:pmsTag>
												</c:if>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="config-font"
                                                   >编辑</span>
                                                <span class="config-font"
                                                   >表单配置</span>
                                                <span class="config-font"
                                                   >要件配置</span>
                                                <span class="config-font"
                                                   >模板配置</span>
                                            </c:otherwise>
                                        </c:choose>
									</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</dhcc:pmsTag>
			<!-- 客户表单配置 end -->
			<!--开发者平台不显示下面的配置 -->
			<c:if test='${fromPage!="devView"}'>
				<!-- 客户分类配置  begin-->
				<dhcc:pmsTag pmsId="set-cus-class-conf">
					<div class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0"
								id="cusClassifyConfig">
								<li class="active"><span name="title">客户分类配置</span></li>
								<span class="btn btn-link config-font" id="classifyAdd"
									onclick="MfCusConfig.addCusClassIfy();"><i
									class="i i-jia2"></i></span>
							</ol>
						</div>
						<div class="content-div">
							<div class="sub-content-div padding_left_15 margin_top_15">
								<div class="item-div" id="cusClassifyContent_table_div">
									<c:forEach items="${cusClassifyParmDiclist }"
										var="cusClassifyParmDic">
										<div class="item-title margin_bottom_10">
											<a id="a_classIfy${cusClassifyParmDic.optCode }"
												href="javascript:void(0);"
												onclick='MfCusConfig.editCusClassify("${cusClassifyParmDic.optCode }");return false;'>
												${cusClassifyParmDic.optName }
											</a> <span style="margin-left: 0px;"
												id="classIfy${cusClassifyParmDic.optCode }">${cusClassifyParmDic.remark }</span>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</dhcc:pmsTag>
				<!-- 客户分类配置  end-->

				<!-- 客户流程配置 begin -->
				<dhcc:pmsTag pmsId="set-cus-flow-conf">
				<div class="list-item col-md-9">
					<div class="title-div">
						<ol class="breadcrumb pull-left padding_left_0" id="flowConfig">
							<li class="active"><span name="title">客户流程设置</span></li>
						</ol>
					</div>
					<div id="flow-content-div" class="content-div">
						<div class="sub-content-div padding_left_15 margin_top_15">
							<c:forEach items="${cusflowList }" var="kindFlow">
								<div class="item-div">
									<div class="item-title  margin_bottom_10">
										<span class="item-checkbox"> 
											<c:if test='${kindFlow.useFlag==1 }'>
												<span class="checkbox-span curChecked"
													data-id="${kindFlow.kindFlowId }"
													data-flowid="${kindFlow.flowId }"
													onclick="MfKindConfig.updateFlowUseFlag(this,'','1');">
													<i class="i i-gouxuan1"></i>
												</span>
											</c:if> 
											<c:if test='${kindFlow.useFlag!=1 }'>
												<span class="checkbox-span"
													data-id="${kindFlow.kindFlowId }"
													data-flowid="${kindFlow.flowId }"
													onclick="MfKindConfig.updateFlowUseFlag(this,'','0');">
													<i class="i i-gouxuan1"></i>
												</span>
											</c:if> <span>启用${kindFlow.flowApprovalName }</span>
										</span>
									</div>
									<div class="item-content margin_bottom_15 padding_left_15">
										<div id="processItem${kindFlow.kindFlowId }"
											class="padding_left_15">
											当前流程：<span id="${kindFlow.flowApprovalNo }">${kindFlow.flowRemark }</span>
											<c:if test='${kindFlow.useFlag==1 }'>
												<a href="javascript:void(0);"
													onclick="MfKindConfig.openProcessDesigner('${kindFlow.flowId }')"
													class="padding_left_15 pointer">配置</a>
											</c:if>
											<c:if test='${kindFlow.useFlag!=1 }'>
												<a href="javascript:void(0);"
													class="link-disabled padding_left_15 pointer">配置</a>
											</c:if>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				</dhcc:pmsTag>
				<!-- 客户流程配置 end -->

                <!-- 授信流程配置 begin -->
                <dhcc:pmsTag pmsId="set-credit-conf">
                    <div class="list-item col-md-9">
                        <div class="title-div">
                            <ol class="breadcrumb pull-left padding_left_0" id="creditConfig">
                                <li class="active"><span name="title">授信流程配置</span></li>
                                <span class="btn btn-link config-font"
                                      onclick="MfCusConfig.creditConfigAdd();"><i
                                        class="i i-jia2"></i></span>
                            </ol>
                        </div>
                        <div id="credit-flow-content-div" class="content-div">
                            <div class="sub-content-div padding_left_15 margin_top_15">
                                <c:forEach items="${mfCusCreditConfigs }" var="mfCusCreditConfig">
                                    <div class="item-div">
                                        <div class="item-title  margin_bottom_10">
                                            <span>${mfCusCreditConfig.creditName }</span>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <span>${mfCusCreditConfig.remark }</span>
                                            &nbsp;&nbsp;
                                            <a href="javascript:void(0);"
                                               onclick="MfCusConfig.openCreditConfig('${mfCusCreditConfig.creditId }','credit_${mfCusCreditConfig.creditModel }','base')"
                                               class="padding_left_15 pointer">配置</a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </dhcc:pmsTag>
                <!-- 授信流程配置 end -->

				<!-- 评级模型配置 begin-->
				<%--<dhcc:pmsTag pmsId="set-level-model-conf">--%>
					<%--<div class="list-item col-md-9">--%>
						<%--<div class="title-div">--%>
							<%--<ol class="breadcrumb pull-left padding_left_0" id="evalConfig">--%>
								<%--<li class="active"><span name="title">评级模型配置</span></li>--%>
								<%--<span class="btn btn-link config-font" id="scenceAdd"--%>
									<%--onclick="MfCusConfig.addEvalScenceConfig(gradeType='<%=BizPubParm.GRADE_TYPE_1%>');"><i--%>
									<%--class="i i-jia2"></i></span>--%>
							<%--</ol>--%>
						<%--</div>--%>
						<%--<div class="content-div">--%>
							<%--<div class="sub-content-div padding_left_15 margin_top_15">--%>
								<%--<div class="item-div" id="ScenceContent_table_div">--%>
									<%--<c:forEach items="${evalScenceConfigKH }" var="evalScenceConfig">--%>
										<%--<div class="item-title margin_bottom_10">--%>
											<%--<span id="${evalScenceConfig.evalScenceNo }"> <a--%>
												<%--href="javascript:void(0);"--%>
												<%--onclick='MfCusConfig.getById("${evalScenceConfig.evalScenceNo }");return false;'>--%>
													<%--${evalScenceConfig.evalScenceName } </a>--%>
											<%--</span>评级指标与评级模型 <a href="javascript:void(0);"--%>
												<%--onclick="MfCusConfig.getEvalScenceConfig('${evalScenceConfig.evalScenceNo }','${evalScenceConfig.evalIndexTypeRel }');return false;"--%>
												<%--class="config-font">配置</a>--%>
										<%--</div>--%>
									<%--</c:forEach>--%>
								<%--</div>--%>
							<%--</div>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</dhcc:pmsTag>--%>
				<!-- 评级模型配置 end-->

				<!-- 业务评级模型配置 begin-->
				<dhcc:pmsTag pmsId="set-level-model-bus-conf">
					<div class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0" id="evalConfig">
								<li class="active"><span name="title">业务评级模型配置</span></li>
								<span class="btn btn-link config-font" id="scenceAdd"
									  onclick="MfCusConfig.addEvalScenceConfigXY(gradeType='<%=BizPubParm.GRADE_TYPE_4%>');"><i
										class="i i-jia2"></i></span>
							</ol>
						</div>
						<div class="content-div">
							<c:if test="${evalScenceConfigXY.size()>0}">
								<div class="sub-content-div padding_left_15 margin_top_15">
									<div class="item-div" id="ScenceContent_table_div_XY">
										<div style="margin-bottom: 10px;">信用评级</div>
										<c:forEach items="${evalScenceConfigXY }" var="evalScenceConfig">
											<div class="item-title margin_bottom_10">
											<span id="${evalScenceConfig.evalScenceNo }"> <a
													href="javascript:void(0);"
													onclick='MfCusConfig.getById("${evalScenceConfig.evalScenceNo }");return false;'>
													${evalScenceConfig.evalScenceName } </a>
											</span>评级指标与评级模型 <a href="javascript:void(0);"
																onclick="MfCusConfig.getEvalScenceConfig('${evalScenceConfig.evalScenceNo }','${evalScenceConfig.evalIndexTypeRel }');return false;"
																class="config-font">配置</a>
											</div>
										</c:forEach>
									</div>
								</div>
							</c:if>
							<c:if test="${evalScenceConfigZX.size()>0}">
								<div class="sub-content-div padding_left_15 margin_top_15">
									<div class="item-div" id="ScenceContent_table_div_ZY">
										<div style="margin-bottom: 10px;">债项评级</div>
										<c:forEach items="${evalScenceConfigZX }" var="evalScenceConfig">
											<div class="item-title margin_bottom_10">
											<span id="${evalScenceConfig.evalScenceNo }"> <a
													href="javascript:void(0);"
													onclick='MfCusConfig.getById("${evalScenceConfig.evalScenceNo }");return false;'>
													${evalScenceConfig.evalScenceName } </a>
											</span>评级指标与评级模型 <a href="javascript:void(0);"
																onclick="MfCusConfig.getEvalScenceConfig('${evalScenceConfig.evalScenceNo }','${evalScenceConfig.evalIndexTypeRel }');return false;"
																class="config-font">配置</a>
											</div>
										</c:forEach>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</dhcc:pmsTag>
				<!-- 业务评级模型配置 end-->

				<!-- 风险评级模型配置 begin-->
				<dhcc:pmsTag pmsId="set-risk-check-model-conf">
					<div class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0">
								<li class="active"><span name="title">风险检查模型配置</span></li>
								<span class="btn btn-link config-font"
									  onclick="MfCusConfig.addEvalScenceConfigFX(gradeType='<%=BizPubParm.GRADE_TYPE_2%>');"><i
										class="i i-jia2"></i></span>
							</ol>
						</div>
						<div class="content-div">
							<div class="sub-content-div padding_left_15 margin_top_15">
								<div class="item-div"  id="ScenceContent_table_divFX">
									<c:forEach items="${evalScenceConfigFX }" var="evalScenceConfig">
										<div class="item-title margin_bottom_10">
											<span id="${evalScenceConfig.evalScenceNo }"> <a
													href="javascript:void(0);"
													onclick='MfCusConfig.getById("${evalScenceConfig.evalScenceNo }");return false;'>
													${evalScenceConfig.evalScenceName } </a>
											</span>客户授信与业务风险检查模型 <a href="javascript:void(0);"
																onclick="MfCusConfig.getEvalScenceConfig('${evalScenceConfig.evalScenceNo }','${evalScenceConfig.evalIndexTypeRel }');return false;"
																class="config-font">配置</a>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</dhcc:pmsTag>
				<!-- 风险评级模型配置 end-->
				<!-- 额度测算模型配置 begin-->
				<dhcc:pmsTag pmsId="set-mea-calc-model-conf">
					<div class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0">
								<li class="active"><span name="title">额度测算模型配置</span></li>
								<span class="btn btn-link config-font"
									  onclick="MfCusConfig.addEvalScenceConfigEC(gradeType='<%=BizPubParm.GRADE_TYPE_3%>');"><i
										class="i i-jia2"></i></span>
							</ol>
						</div>
						<div class="content-div">
							<div class="sub-content-div padding_left_15 margin_top_15">
								<div class="item-div" id="ScenceContent_table_divEC">
									<c:forEach items="${evalScenceConfigEC }" var="evalScenceConfig">
										<div class="item-title margin_bottom_10">
											<span id="${evalScenceConfig.evalScenceNo }"> <a
													href="javascript:void(0);"
													onclick='MfCusConfig.getById("${evalScenceConfig.evalScenceNo }");return false;'>
													${evalScenceConfig.evalScenceName } </a>
											</span>客户额度测算模型 <a href="javascript:void(0);"
																onclick="MfCusConfig.getEvalScenceConfig('${evalScenceConfig.evalScenceNo }','${evalScenceConfig.evalIndexTypeRel }');return false;"
																class="config-font">配置</a>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</dhcc:pmsTag>
				<!-- 风险评级模型配置 end-->

				<!-- 分数等级配置  begin-->
				<dhcc:pmsTag pmsId="set-grade-class-conf">
					<div class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0"
								id="evalScoreGradeConfig">
								<li class="active"><span name="title">分数等级配置</span></li>
							</ol>
						</div>
						<div class="content-div">
							<div class="sub-content-div padding_left_15 margin_top_15">
								<div class="item-div" id="ScoreGradeContent_table_div">

									        <div class="item-title margin_bottom_10">
											<span id="company"> <a
													href="javascript:void(0);"
													onclick='MfCusConfig.getCompany();return false;'>
													企业客户分数等级 </a>
											</span>评级客户分数等级 <a href="javascript:void(0);"
																onclick="MfCusConfig.getCompany();return false;"
																class="config-font">查看</a>
											</div>


									       <div class="item-title margin_bottom_10">
											<span id="person"> <a
													href="javascript:void(0);"
													onclick='MfCusConfig.getPerson();return false;'>
													个人客户分数等级 </a>
											</span>评级客户分数等级 <a href="javascript:void(0);"
																onclick="MfCusConfig.getPerson();return false;"
																class="config-font">查看</a>
										   </div>
								</div>
							</div>
						</div>
					</div>
				</dhcc:pmsTag>
				<!-- 分数等级配置  end-->

				<%--<c:forEach items="${EvalScoreGradeConfigList }"
						   var="EvalScoreGradeConfig">
					 <div class="item-title margin_bottom_10">
					<p class="margin_bottom_10">
					<a href="javascript:void(0);"
					onclick="MfCusConfig.editAssess('evalAssess','action?configNo=${EvalScoreGradeConfig.configNo }','action?cusType=${EvalScoreGradeConfig.cusType }');return false;"
					class="level-a">${EvalScoreGradeConfig.evalLevelEn }</a> 等级
					<span id="opScore${EvalScoreGradeConfig.configNo }">评级分值
					${EvalScoreGradeConfig.opScore1 }--${EvalScoreGradeConfig.opScore2 }
					</span> <span id="creditAmt${EvalScoreGradeConfig.configNo }">授信额度${EvalScoreGradeConfig.creditAmt }万
					</span> <span> <a href="javascript:void(0);"
					onclick="MfCusConfig.editAssess('evalInfo','action?configNo=${EvalScoreGradeConfig.configNo }','action?cusType=${EvalScoreGradeConfig.cusType }');return false;"
					class="config-font">配置</a>
					</span>
					</p>
					<p id="evalAssess${EvalScoreGradeConfig.configNo }">
					${EvalScoreGradeConfig.evalAssess }</p>
					</div>
				</c:forEach>
				--%>
				<%--<!-- 客户授信模型配置  begin-->
				<dhcc:pmsTag pmsId="set-credit-model-conf">
					<div class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0"
								id="cusCreditModelConfig">
								<li class="active"><span name="title">授信模型配置</span></li>
								<span class="btn btn-link config-font" id="creditAdd"
									onclick="MfCusConfig.addCusCreditModel();"><i
									class="i i-jia2"></i></span>
							</ol>
						</div>
						<div class="content-div">
							<div class="sub-content-div padding_left_15 margin_top_15">
								<div class="item-div" id="cusCreditContent_table_div">
									<c:forEach items="${mfCusCreditModelList }" var="mfCusCreditModel">
										<div class="item-title margin_bottom_10">
											<a id="creditModelName${mfCusCreditModel.modelId }"
												href="javascript:void(0);"
												onclick='MfCusConfig.editCusCreditModel("${mfCusCreditModel.modelId }");return false;'>
												${mfCusCreditModel.modelName } </a> <span
												style="margin-left: 0px;"
												id="creditRemark${mfCusCreditModel.modelId }">${mfCusCreditModel.remark }</span>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</dhcc:pmsTag>
				<!-- 客户授信模型配置  end-->--%>

				<!-- 财务报表配置 begin-->
				<dhcc:pmsTag pmsId="set-cw-report-conf">
					<div class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0"
								id="cusFinParmConfig">
								<li class="active"><span name="title">财务报表配置</span></li>
							</ol>
						</div>
						<div class="content-div">
							<div class="sub-content-div padding_left_15 margin_top_15">
								<div class="item-div" id="cusFinParmContent_table_div">
									<c:forEach items="${cusFinParmDiclist }" var="cusFinParmDic">
										<div class="item-title margin_bottom_10">
											${cusFinParmDic.optName } 信息采集与信息展示
                                            <%--<a class="config-font"--%>
												<%--href="javascript:void(0);"--%>
												<%--onclick='MfCusConfig.getReportPreview("${cusFinParmDic.optCode }")'>预览</a>--%>
											<%--<a class="config-font" href="javascript:void(0);"--%>
												<%--onclick='MfCusConfig.getFinParm("${cusFinParmDic.optCode }")'>配置</a>--%>
                                            <a class="config-font"
												href="javascript:void(0);"
												onclick='MfCusConfig.getReportView("${cusFinParmDic.remark }")'>预览</a>
											<a class="config-font" href="javascript:void(0);"
												onclick='MfCusConfig.getReportSet("${cusFinParmDic.remark }")'>配置</a>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</dhcc:pmsTag>
				<!-- 财务报表配置  end-->

				<!-- 财务指标管理 begin-->
				<dhcc:pmsTag pmsId="set-cw-aim-conf">
					<div class="list-item col-md-9">
						<div class="title-div">
							<ol class="breadcrumb pull-left padding_left_0"
								id="cusFinFormConfig">
								<li class="active"><span name="title">财务指标配置</span></li>
								<span class="btn btn-link config-font" id="classifyAdd"
									onclick="MfCusConfig.addCusFinForm();"><i
									class="i i-jia2"></i></span>
							</ol>
						</div>
						<div class="content-div">
							<div class="sub-content-div padding_left_15 margin_top_15">
								<div class="item-div" id="cusFinFormContent_table_div">
									<c:forEach items="${cusFinFormList }" varStatus="st"
										var="cusFinForm">
										<c:if test="${st.count<=10}">
											<div class="item-title margin_bottom_10"
												id="cusFinformName${ cusFinForm.formNo}">
												${cusFinForm.formName } &nbsp;&nbsp; ${cusFinForm.formDesc }
												<a class="config-font" href="javascript:void(0);"
													onclick='MfCusConfig.editCusFinForm("${cusFinForm.formNo }","${cusFinForm.accRule }")'>配置</a>
											</div>
										</c:if>
									</c:forEach>
									<div class="item-title margin_bottom_10" id="moreCusFinForm">
										<a href="javascript:void(0);"
											onclick='MfCusConfig.getCusFinFormByPage()'> ......更多 </a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</dhcc:pmsTag>
				<!-- 财务指标管理   end-->
			</c:if>
		</div>
		<!-- 客户配置信息 end -->
		<!-- 导航轴 -->
		<div class="row clearfix">
			<div class="work-zone-timeLine"
				style="position: fixed; margin-top: -78px; margin-right: 79px;">
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
<script type="text/javascript">
		$(function(){
			 $("#content_table_nmd").find("tr").bind("dblclick", function(event){
	    	 	var optCode = $("tr").find('td').eq(0).html();
	    	 	var optName = $("tr").children('td').eq(1).html();
	    	 	var url =webPath+"/mfCusFormConfig/getAllFormConList?optCode="+optCode+"&&optName="+optName;
	    	 	window.location.href = url;
	    	 })
	    	 //调用时间轴
	    	 navLine.createNavLine();
		});
		function newDocModel(url){
			if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
			top.addFlag = false;
			top.openBigForm(url,"要件配置",function(){
				if(top.addFlag){
					window.location.reload();
				}
			});
		};
		function newTemplateModel(url){
            url =webPath + url;
			top.addFlag = false;
			top.openBigForm(url,"模板配置",function(){
				if(top.addFlag){
					window.location.reload();
				}
			});
		};
	</script>
</html>
