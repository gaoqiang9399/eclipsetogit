<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String query = (String) request.getAttribute("query");
	String appSts = (String) request.getAttribute("appSts");
	boolean getFlag = (Boolean) request.getAttribute("getFlag");
	Object dataMap = request.getAttribute("dataMap");
%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
<link id="appEvalInfo" type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/appEvalInfo${skinSuffix}.css" />
<script type="text/javascript">
	var query = "<%=query%>";
	var getFlag = <%=getFlag%>;
 	var evalIndexTypeRel = "${evalIndexTypeRel}";
 	var dataMap = <%=dataMap%>;
 	var appSts = "<%=appSts%>";
 	var evalAppNo = "${evalAppNo}";
 	var evalScenceNo = "${evalScenceNo}";
 	var cusType = "${cusType}";
 	var cusBaseType = "${cusBaseType}";
 	var cusNo = "${cusNo}";
 	var evalStage = "${appEval.evalStage}";
	$(function(){
		
//  		myCustomScrollbarForForm({
// 			obj:"#gradeCard",
// 			advanced : {
// 				updateOnContentResize : true
// 			}
// 		});
//  		myCustomScrollbarForForm({
// 			obj:"#evalapp",
// 			advanced : {
// 				updateOnContentResize : true
// 			}
// 		});

		cusNo = $("#choseFinForm").find("[name='cusNo']").val();

		setGradeModelOption();
	});
	//设置评分模型下拉框数据
	function setGradeModelOption(){
		var select = $("select[name=evalScenceNo]");
		$.ajax({
			url:webPath+"/evalScenceConfig/getEvalScenceConfigData?cusNo="+cusNo+"&cusBaseType="+cusBaseType,
			data:{cusType:cusType},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					//如果是编辑，只展示选中的模型
					if(getFlag==true){
						$.each(data.dataMap.EvalScenceConfigList, function(key,obj) {
							if(evalScenceNo==obj.evalScenceNo){
								select.append("<option value='"+obj.evalScenceNo+"'>"+obj.evalScenceName+"</option>");
							}
						});
					}else{
						$.each(data.dataMap.EvalScenceConfigList, function(key,obj) {
							select.append("<option value='"+obj.evalScenceNo+"'>"+obj.evalScenceName+"</option>");
						});
					}
				}else{
					window.top.alert("获得评分模型失败",0);
				}
			},error:function(){
				// window.top.alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		}); 
	};
 	function myclose(){
			$(top.window.document).find(".dhccModalDialog_bg").hide();
			$(top.window.document).find(".dhccModalDialog").remove();
	};
	function doSubmit(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var opinionType = $("select[name=opinionType]").val();
			var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
			var evalAppNo  = $("input[name=evalAppNo]").val();
			commitProcess("${webPath}/appEvalWkf/submitUpdate?opinionType="+opinionType+"&evalAppNo="+evalAppNo+"&taskId="+taskId,obj,'evalSP');
		};
	};
	function selectFinMain(){
		var cusNo = $("input[name=cusNo]").val();
		dialog({
			id:"selectFinMainDialog",
			url:webPath+"/cusFinMain/getListPage?cusNo="+cusNo,
			title:"财务报表",
			width:550,
    		height:300,
    		backdropOpacity:0,
    		onclose:function(){
    			if(typeof(this.returnValue) == "undefined" || typeof(this.returnValue) == null || this.returnValue == ''){
    			}else{
    				$("form[id=choseFinForm]").find("input[name=rptDate]").val(this.returnValue.rptDate);
    				$("form[id=choseFinForm]").find("input[name=rptType]").val(this.returnValue.rptType);
    				$("form[id=choseFinForm]").find("input[name=rptDate]").blur();
    			}
    		}
			
		}).showModal();
	};
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="eval-content" style="display: none;">
		<div class="showprogress">
			<!--进度-->
			<ul>
				<li class="selected" name="chosefin"><span class="span_btn">
						<span class="lable">发起评级<i class="i i-jiantou2"></i></span>
				</span></li>
				<li name="dx"><span class="span_btn"> <span
						class="lable">评分卡<i class="i i-jiantou2"></i></span>
				</span></li>
				<li name="evalapp"><span class="span_btn"> <span
						class="lable">评级结果<i class="i i-jiantou2"></i></span>
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
				<li>
					<div name="gradeCard" class="li_content_type eval-app container form-container">
						<div class="scroll-content" id="gradeCard">
							<div id="gradeCard-content" class="panel-group overflowHidden" style="width: 99%;">
							<c:forEach items="${evalGradeCardList }" var="evalGradeCard">
								<!-- 财务 -->
								<c:if test='${evalGradeCard.gradeCardType==1}'>
									<div class="row clearfix">
										<div class="col-xs-12 column">
											<div name="fin${evalGradeCard.gradeCardId}" class="panel panel-default li_content_type">
												<div class="panel-heading">
													<h4 class="panel-title">
														<span class="span-title">${evalGradeCard.gradeCardName}</span>
														<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#finDetailInfo${evalGradeCard.gradeCardId}" data-parent="#accordion" style="height: auto;">
															<i class="i i-close-up"></i><i class="i i-open-down"></i>
														</button>
													</h4>
												</div>
												<div id="finDetailInfo${evalGradeCard.gradeCardId}" class="li_content panel-collapse collapse in">
													<table class="ls_list_a" style="width: 100%;">
														<thead></thead>
														<tbody></tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</c:if>
								<!-- 定量 -->
								<c:if test='${evalGradeCard.gradeCardType==2}'>
									<div class="row clearfix">
										<div class="col-xs-12 column">
											<div name="dl${evalGradeCard.gradeCardId}" class="panel panel-default li_content_type">
												<div class="panel-heading">
													<h4 class="panel-title">
														<span class="span-title">${evalGradeCard.gradeCardName}</span>
														<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#dlDetailInfo${evalGradeCard.gradeCardId}" data-parent="#accordion" style="height: auto;">
															<i class="i i-close-up"></i><i class="i i-open-down"></i>
														</button>
													</h4>
												</div>
												<div id="dlDetailInfo${evalGradeCard.gradeCardId}" class="li_content panel-collapse collapse in">
													<table class="ls_list_a" style="width: 100%">
														<thead></thead>
														<tbody></tbody>
													</table>
												</div>
											</div>
											</div>
										</div>
								</c:if>
								<!-- 定性 -->
									<div class="row clearfix">
										<div class="col-xs-12 column">
											<div name="dx${evalGradeCard.gradeCardId}" class="panel panel-default li_content_type">
												<div class="panel-heading">
													<h4 class="panel-title">
														<span class="span-title">${evalGradeCard.gradeCardName}</span>
														<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#dxDetailInfo${evalGradeCard.gradeCardId}" data-parent="#accordion" style="height: auto;">
															<i class="i i-close-up"></i><i class="i i-open-down"></i>
														</button>
													</h4>
												</div>
												<div id="dxDetailInfo${evalGradeCard.gradeCardId}" class="li_content panel-collapse collapse in">
														<form id="evalDx${evalGradeCard.gradeCardId}">
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
								<!-- 调整 -->
								<c:if test='${evalGradeCard.gradeCardType==4}'>
									<div class="row clearfix">
									<div class="col-xs-12 column">
										<div name="adj${evalGradeCard.gradeCardId}" class="panel panel-default li_content_type">
											<div class="panel-heading">
												<h4 class="panel-title">
													<span class="span-title">${evalGradeCard.gradeCardName}</span>
													<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#adjDetailInfo${evalGradeCard.gradeCardId}" data-parent="#accordion" style="height: auto;">
														<i class="i i-close-up"></i><i class="i i-open-down"></i>
													</button>
												</h4>
											</div>
											<div id="adjDetailInfo${evalGradeCard.gradeCardId}" class="li_content panel-collapse collapse in">
												<div class="" name="initadj${evalGradeCard.gradeCardId}">
													<form id="initadj${evalGradeCard.gradeCardId}">
														<table class="ls_list_a" style="width: 100%">
															<thead></thead>
															<tbody></tbody>
														</table>
													</form>
												</div>
											</div>
										</div>
									</div>
								</div>
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
										onclick="editEval()"></dhcc:thirdButton>
								</div>
							</c:if>
						</div>
					</div>
				</li>
				<li>
					<div  name="evalapp" class="li_content_type container form-container">
						<div class="scroll-content" id="evalapp">
							<div class="col-md-10 col-md-offset-1 column margin_top_20">
								<div class="bootstarpTag">
									<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
									<form  method="post" id="evalMsg" theme="simple"
										name="operform" action="${webPath}/appEval/updateAjax">
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
<script type="text/javascript" src="${webPath}/component/eval/js/appEvalInitData_new.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/appEval_new.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/appEvalSave_new.js"></script>
</html>