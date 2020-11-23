<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%
	String query = (String) request.getAttribute("query");
	String appSts = (String) request.getAttribute("appSts");
%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
<link type="text/css" rel="stylesheet" href="${webPath}/component/examine/css/ExamineApply.css" />
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
<script type="text/javascript" src='${webPath}/component/examine/js/ExamineApplyForCus.js'> </script> 
<script type="text/javascript" src='${webPath}/component/examine/js/ExamineApplySave.js'> </script> 
<script type="text/javascript" src='${webPath}/component/examine/js/ExamCardInitData.js'> </script> 
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>  
<script type='text/javascript' src='${webPath}/dwr/util.js'></script>  
<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	var basePath="${webPath}";
	var query = "<%=query%>";
	var getFlag = "${getFlag}";
 	
 	var appSts = "<%=appSts%>";
 	var examHisId = "${examHisId}";
 	var templateId = "${templateId}";
 	var cusType = "${cusType}";
 	var cusBaseType = "${cusBaseType}";
 	var cusNo = "${cusNo}";
 	var resultFormId="${resultFormId}";
 	var scNo="${scNo}";
	var docParm = "relNo="+examHisId+"&scNo="+scNo;//查询文档信息的url的参数
	var dataMap = ${dataMap};
	var firstApprovalUser ='';
	$(function(){
		var cusTelNewClass = $("input[name=cusTelNew]").parents("td").attr("class");
		ExamineApply.init();
		ExamineApply.template_init();
	});
	var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
    PageMessageSend.onPageLoad(userId); 
	dwr.engine.setActiveReverseAjax(true);
 	dwr.engine.setNotifyServerOnPageUnload(true);
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="eval-content" style="display: none;">
		<div class="showprogress">
			<!--进度-->
			<ul>
				<li class="selected" name="chosefin"><span class="span_btn">
						<span class="lable">模型匹配<i class="i i-jiantou2"></i></span>
				</span></li>
				<li name="dx"><span class="span_btn"> <span
						class="lable">检查卡<i class="i i-jiantou2"></i></span>
				</span></li>
				<li name="evalapp"><span class="span_btn"> <span
						class="lable">检查结论<i class="i i-jiantou2"></i></span>
				</span></li>
			</ul>
		</div>
		<div class="content_show">
			<ul class="content_ul">
				<li>
					<div name="chosefin" class="li_content_type container form-container">
						<div class="scroll-content" id="chosefin">
							<div class="col-md-10 col-md-offset-1 column margin_top_20">
								<div class="bootstarpTag fourColumn">
									<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
									<form method="post" id="ecamHisInsertForm" theme="simple"
										name="operform" action="${webPath}/mfExamineHis/insertForCusAjax">
										<div id="choseFinDiv">
											<c:if test="${query!='query'}">
												<dhcc:bootstarpTag property="formexamhisForCus" mode="query" />
											</c:if>
										</div>
									</form>
								</div>
							</div>
						</div>
							<div class="formRowCenter" id ="chosefinButton" style="bottom:40px\9;">
								<dhcc:thirdButton value="保存" action="保存" onclick="ExamineApplySave.examApplySaveAjax('#ecamHisInsertForm')"></dhcc:thirdButton>
								<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
							</div>
					</div>
				</li>
				<li>
					<div name="gradeCard" class="li_content_type eval-app container form-container">
						<div class="scroll-content mCustomScrollbar" id="gradeCard">
							<div id="gradeCard-content" class="panel-group overflowHidden" style="width: 99%;">
							<%-- <s:iterator value="examCardListDataList" var="examCard">
								<!-- 定性 -->
								<div class="row clearfix">
									<div class="col-xs-12 column">
										<div name="dx${examineCardId}" class="panel panel-default li_content_type">
											<div class="panel-heading">
												<h4 class="panel-title">
													<span class="span-title">${examineCardName}</span>
													<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#dxDetailInfo${examineCardId}" data-parent="#accordion" style="height: auto;">
														<i class="i i-close-up"></i><i class="i i-open-down"></i>
													</button>
												</h4>
											</div>
											<div id="dxDetailInfo${examineCardId}" class="li_content panel-collapse collapse in">
													<form id="evalDx${examineCardId}">
														<table class="ls_list_a" style="width: 100%">
															<thead></thead>
															<tbody class="level1"></tbody>
														</table>
													</form>
											</div>
										</div>
									</div>
								</div>
							</s:iterator> --%>
							<c:forEach items="${examCardListDataList}" var="examCard" varStatus="status">
								<div class="row clearfix">
									<div class="col-xs-12 column">
										<div name="dx${examCard.examineCardId}" class="panel panel-default li_content_type">
											<div class="panel-heading">
												<h4 class="panel-title">
													<span class="span-title">${examCard.examineCardName}</span>
													<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#dxDetailInfo${examCard.examineCardId}" data-parent="#accordion" style="height: auto;">
														<i class="i i-close-up"></i><i class="i i-open-down"></i>
													</button>
												</h4>
											</div>
											<div id="dxDetailInfo${examCard.examineCardId}" class="li_content panel-collapse collapse in">
													<form id="evalDx${examCard.examineCardId}">
														<table class="ls_list_a" style="width: 100%">
															<thead></thead>
															<tbody class="level1"></tbody>
														</table>
													</form>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
							</div>
						</div>
						<div id ="gradeCardButton" style="display: none;">
							<c:if test="${query!='query'}">
								<div class="formRowCenter" id="addEval" style="bottom:40px\9;">
									<dhcc:thirdButton value="保存" action="保存"
										onclick="ExamineApplySave.examCardSave()"></dhcc:thirdButton>
								</div>
							</c:if>
							 <c:if test="${query!='query'}">
								<div class="formRowCenter" id="editEval" style="display: none;bottom:40px\9;">
									<dhcc:thirdButton value="编辑" action="编辑"
										onclick="ExamineApply.editEval()"></dhcc:thirdButton>
								</div>
							</c:if>
						</div>
					</div>
				</li>
				<li>
					<div  name="evalapp" class="li_content_type container form-container">
						<div class="scroll-content" id="evalapp">
							<div class="col-md-10 col-md-offset-1 column margin_top_20">
								<div class="bootstarpTag fourColumn">
									<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
									<form method="post" id="evalMsg" theme="simple"
										name="operform" action="${webPath}/mfExamineHis/updateAjax">
										<div id="bootstarpTag-div">
											<dhcc:bootstarpTag property="formeval1001" mode="query" />
										</div>
									</form>
								</div>
							</div>
							<!-- 检查模板 -->
							<div class="col-md-10 col-md-offset-1 column" >
								<div id="template_div">
									<div class="list-table margin_0">
										<div class="title">
											<span><i class="i i-xing blockDian"></i>文档模板</span>
										</div>
									</div>
									<div id="bizConfigs" class="template-config item-div padding_left_15"></div>
								</div>
							</div>
							<!-- 检查资料 -->
							<div class="col-md-10 col-md-offset-1 column" >
	 							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	 						</div>
						<c:if test="${query!='query'}">
							<div class="formRowCenter" id ="evalappButton" style="display: none;bottom:40px\9;">
								<dhcc:thirdButton value="提交" action="提交" onclick="ExamineApplySave.examApplySubmit('#evalMsg')"></dhcc:thirdButton>
							</div>
						</c:if>
						</div>
					</div>
					
					
				</li>
			</ul>
			
		</div>
	</div>
</body>
</html>