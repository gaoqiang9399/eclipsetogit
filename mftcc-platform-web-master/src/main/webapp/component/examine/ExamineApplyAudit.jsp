<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
<link type="text/css" rel="stylesheet" href="${webPath}/component/examine/css/ExamineApply.css" />
<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
<script type="text/javascript" src='${webPath}/component/examine/js/ExamineApplyAudit.js'> </script>
<script type="text/javascript" src='${webPath}/component/examine/js/ExamineApplyNew.js'> </script>
<script type="text/javascript" src='${webPath}/component/examine/js/ExamineApplySave.js'> </script>
<script type="text/javascript" src='${webPath}/component/examine/js/ExamCardInitData.js'> </script> 
<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>  
<script type='text/javascript' src='${webPath}/dwr/util.js'></script>  
<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_examineTaskInsert.js"></script>

<script type="text/javascript">
 	var dataMap = ${dataMap};
 	var scNo="${scNo}";
 	var examHisId = "${examHisId}";
    //文档模板需要参数
    var temBizNo = "${examHisId}";
    var nodeNo ="${scNo}";
    var querySaveFlag = "0";
    var approvalNodeNo ="";
    var querySaveFlag_pl="";
    //要件所需参数
	var docParm = "relNo="+examHisId+"&scNo="+scNo;//查询文档信息的url的参数
	var basePath="${webPath}";
	var queryVal = "${query}";
	var getFlag = "${dataMap.getFlag}";
 	var templateId = "${templateId}";
 	var resultFormId="${resultFormId}";
    var firstApprovalUser='';
    var entrance = "${examEntrance}";
    var addFormId = "ecamHisInsertForm";
    var cusNo = "${cusNo}";
    var appId="${appId}";
    var pactId="${pactId}";
    var fincId="${fincId}";
    var templateType="${templateType}";

	$(function(){
        //文档加载
		// ExamineApplyAudit.template_init();
        //查询检查模型
        if (getFlag=="add"||getFlag=="apply"||getFlag=="query"){
            ExamineApplyAudit.initTemplateOptions(templateType);
        }
        ExamineApplyAudit.init();

        //MfBusFincApp_examineTaskInsert.init();
	});
	var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
    PageMessageSend.onPageLoad(userId);
	dwr.engine.setActiveReverseAjax(true);
 	dwr.engine.setNotifyServerOnPageUnload(true);
 	
 	function chooseUserToNext(obj, type, hide) {
        ExamineApplySave.chooseUserToNext(obj, type, hide)
    }
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
                <dhcc:pmsTag pmsId="loan-check-checkcard">
				<li name="dx"><span class="span_btn"> <span
						class="lable">检查卡<i class="i i-jiantou2"></i></span>
				</span></li>
                </dhcc:pmsTag>
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
										name="operform" action="${webPath}/mfExamineHis/insertAuditAjax">
										<div id="choseFinDiv">
											<dhcc:bootstarpTag property="formexamhis001" mode="query" />
										</div>
									</form>
								</div>
							</div>
						</div>

					</div>
				</li>
                <dhcc:pmsTag pmsId="loan-check-checkcard">
				<li>
					<div name="gradeCard" class="li_content_type eval-app container form-container">
						<div class="scroll-content" id="gradeCard">
							<div id="gradeCard-content" class="panel-group overflowHidden" style="width: 99%;">
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
                </dhcc:pmsTag>
				<li>
					<div  name="evalapp" class="li_content_type container form-container">
						<div class="scroll-content" id="evalapp">
							<div class="col-md-10 col-md-offset-1 column margin_top_20">
								<div class="bootstarpTag fourColumn">
									<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
									<form method="post" id="evalMsg" theme="simple"
										name="operform" action="${webPath}/mfExamineHis/updateAuditAjax">
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
								<div class="row clearfix">
									<%@ include file="/component/model/templateIncludePage.jsp"%>
								</div>
	 							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	 						</div>
						</div>
						<c:if test="${query!='query'}">
							<div class="formRowCenter" id ="evalappButton" style="display: none;bottom:40px\9;">
								<dhcc:thirdButton value="提交" action="提交" onclick="ExamineApplySave.examUpdate('#evalMsg')"></dhcc:thirdButton>
							</div>
						</c:if>
					</div>
				</li>
			</ul>
			
		</div>
	</div>
	<c:if test="${query!='query'}">
		<div class="formRowCenter" id ="chosefinButton" style="bottom:40px\9;display: block">
			<dhcc:thirdButton value="保存" action="保存" onclick="ExamineApplySave.examApplySaveAjax('#ecamHisInsertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		</div>
		<div class="formRowCenter" id ="chosefinTaskButton" style="bottom:40px\9;display: none">
			<dhcc:thirdButton value="保存" action="保存" onclick="ExamineApplySave.examApplySaveAjax('#ecamHisInsertForm')"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</c:if>
</body>
</html>