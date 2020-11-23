<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
<link id="appEvalInfo" type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/appEvalInfo${skinSuffix}.css" />
<script type="text/javascript">
	var query = "${query}";
	var getFlag = "${getFlag}";
 	var evalIndexTypeRel = "${evalIndexTypeRel}";
 	var dataMap = "";
 	var appSts = "${appSts}";
 	var evalAppNo = "${evalAppNo}";
 	var evalScenceNo = "${evalScenceNo}";
 	var cusType = "${cusType}";
 	var cusBaseType = "${cusBaseType}";
 	var cusNo = "${cusNo}";
 	var appId = "${appId}";
 	var creditAppId = "${creditAppId}";
 	var useType = "${useType}";
 	var evalStage = "${appEval.evalStage}";
 	var timeLimit = "${timeLimit}";
 	var pinFenName = "${pinFenName}";
 	var gradeType = "${gradeType}";
 	var evalClass = "${evalClass}";
	$(function(){
		AppEval_InitiateApp.getEvalListData();
		// if(gradeType == '4' && evalClass == '2'){
		//     $("input[name='rptDate']").parent().parent().parent().remove();
		// }
	});
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="eval-content" style="display: none;">
		<div class="showprogress">
			<!--进度-->
			<ul>
				<li class="selected" name="chosefin"><span class="span_btn">
						<span class="lable color_theme" style="width: 100px;">发起${showName}<i class="i i-jiantou2"></i></span>
				</span></li>
				<dhcc:pmsTag pmsId="cus-eval-grade-card">
					<li name="dx"><span class="span_btn"> <span
							class="lable color_theme" style="width: 100px;margin-left: 30px;">${pinFenName}<i class="i i-jiantou2"></i></span>
					</span></li>
				</dhcc:pmsTag>
				<li name="evalapp"><span class="span_btn"> <span
						class="lable color_theme" style="width: 100px;">${showName}结果<i class="i i-jiantou2"></i></span>
				</span></li>
			</ul>
		</div>
		<div class="content_show">
			<ul class="content_ul">
				<li>
					<div name="chosefin" class="li_content_type container form-container">
						<div class="scroll-content" id="chosefin">
							<div class="col-md-8 col-md-offset-2 column margin_top_20">
								<div class="bootstarpTag">
									<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
									<form  method="post" id="choseFinForm" theme="simple"
										name="operform" action="${webPath}/appEval/insertEvalApplyAjax">
										<div id="choseFinDiv">
											<c:if test="${query!='query'}">
												<dhcc:bootstarpTag property="formappeval0001" mode="query" />
											</c:if>
										</div>
										<input type="hidden" name="accountId">
									</form>
								</div>
							</div>
						</div>
						<c:if test="${query!='query'&&getFlag!=true}">
							<div class="formRowCenter" id ="chosefinButton" style="bottom:40px\9;">
								<dhcc:thirdButton value="保存" action="保存" onclick="evalAjaxSave('#choseFinForm','${webPath}/appEval/insertEvalApplyAjax')"></dhcc:thirdButton>
								<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
							</div>
						</c:if>
					</div>
				</li>
				<dhcc:pmsTag pmsId="cus-eval-grade-card">
				<li>
					<div name="gradeCard" class="li_content_type eval-app container form-container">
						<div class="scroll-content" id="gradeCard">
							<div id="gradeCard-content" class="panel-group overflowHidden" style="width: 99%;">
							<c:forEach items="${evalGradeCardList }" var="evalGradeCard">
								<!-- 定性 -->
									<div class="row clearfix">
										<div class="col-xs-12 column">
											<div name="${evalGradeCard.gradeCardId}" class="panel panel-default li_content_type">
												<div class="panel-heading">
													<h4 class="panel-title">
														<span class="span-title">${evalGradeCard.gradeCardName}</span>
														<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#indexDetailInfo${evalGradeCard.gradeCardId}" data-parent="#accordion" style="height: auto;">
															<i class="i i-close-up"></i><i class="i i-open-down"></i>
														</button>
													</h4>
												</div>
												<div id="indexDetailInfo${evalGradeCard.gradeCardId}" class="li_content panel-collapse collapse in">
														<form id="evalIndex${evalGradeCard.gradeCardId}" name="${evalGradeCard.gradeCardId}">
															<table class="ls_list_a" style="width: 100%">
																<thead></thead>
																<tbody></tbody>
															</table>
														</form>
												</div>
											</div>
										</div>
									</div>
								<c:if test='${evalGradeCard.gradeCardType==3}'>
								</c:if>
							</c:forEach>
							</div>
						</div>
						<div id ="gradeCardButton" style="display: none;">
							<c:if test="${query!='query'}">
								<div class="formRowCenter" id="addEval" style="bottom:40px\9;">
									<dhcc:thirdButton value="保存" action="保存"
										onclick="evalUpdate()"></dhcc:thirdButton>
								</div>
							</c:if> <c:if test="${query!='query'}">
								<div class="formRowCenter" id="editEval" style="display: none;bottom:40px\9;">
									<dhcc:thirdButton value="编辑" action="编辑"
										onclick="AppEval_InitiateApp.editEval()"></dhcc:thirdButton>
								</div>
							</c:if>
						</div>
					</div>
				</li>
				</dhcc:pmsTag>
				<li>
					<div  name="evalapp" class="li_content_type container form-container">
						<div class="scroll-content" id="evalapp">
							<div class="col-md-10 col-md-offset-1 column margin_top_20">
								<div class="bootstarpTag">
									<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
									<form  method="post" id="evalMsg" theme="simple"
										name="operform" action="${webPath}/appEval/evalSubmitAjax">
										<div id="bootstarpTag-div">
											<dhcc:bootstarpTag property="formeval1001" mode="query" />
										</div>
									</form>
								</div>
							</div>
						<c:if test="${query!='query'}">
							<div class="formRowCenter" id ="evalappButton" style="display: none;bottom:40px\9;">
								<dhcc:thirdButton value="提交" action="提交" onclick="evalAppSubmit('#evalMsg','${webPath}/appEval/evalSubmitAjax')"></dhcc:thirdButton>
							</div>
						</c:if>
						</div>
					</div>
					
					
				</li>
			</ul>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/eval/js/appEvalInitData.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/AppEval_InitiateApp.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/appEvalSave.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/pub_init_eval_info.js"></script>
</html>