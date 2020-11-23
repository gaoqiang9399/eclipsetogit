<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
  <script type="text/javascript" src='${webPath}/component/examine/js/WkfExamViewPointForCus.js?v=${cssJsVersion}'> </script>
  <script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
  <script type="text/javascript" src='${webPath}/component/examine/js/ExamineApplyAfter.js?v=${cssJsVersion}'> </script>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var templateId = '${templateId}';
			var templateType = '${templateType}';
			var examHisId ="${examHisId}";
			var scNo="${scNo}";
			var basePath="${webPath}";
            var cusNo="${cusNo}";
            var query="${query}";
            var nodeNo="${nodeNo}";
            var docParm = "relNo="+examHisId+"&scNo="+scNo+"&query="+query;//查询文档信息的url的参数
            var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				examHisId = $("input[name=examHisId]").val();
				ExamineApply.template_init(templateId);

				if('node4596463489'==nodeNo){
				    $("select[name='adjRiskLevel']").attr("disabled","disabled");
				}
                $.ajax({
                    url : webPath+"/mfExamineHis/queryCusFinDataAjax",
                    data : {
                        cusNo : cusNo,
                        examHisId:examHisId
                    },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        getFinDataHtml(data);
                    },
                    error : function() {
                    }
                });
                $("#pfsAdd").hide();
                $("#finDataDel").hide();
			});
			 //审批页面
			 function getApprovaPage(){
			 	$("#infoDiv").css("display","none"); 
			 	$("#approvalBtn").css("display","none"); 
			 	$("#approvalDiv").css("display","block"); 
			 	$("#submitBtn").css("display","block"); 
			 }
			 //返回详情页面
			 function approvalBack(){
			 	$("#infoDiv").css("display","block"); 
			 	$("#approvalBtn").css("display","block"); 
			 	$("#approvalDiv").css("display","none"); 
			 	$("#submitBtn").css("display","none");
			 }
			
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("select[name=opinionType]").val();
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
                var ajaxData = JSON.stringify($(obj).serializeArray());
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess(webPath+"/mfExamineHis/submitUpdateAjax?opinionType="+opinionType+"&examHisId="+examHisId+"&appNo="+examHisId,obj,'examSP');
				}
			}
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfCusCustomer/getById?cusNo=${cusNo}&creditApproveId=${creditApproveId}&evalCredit=evalCredit&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="examApprovForm">
						<dhcc:bootstarpTag property="formexamapproveForCus" mode="query" />
					</form>
					<div class="list-table" id="mfBusPactExamineListDiv">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>担保方案及在保情况</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusPactExamineList">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="mfBusPactExamineList" name="mfBusPactExamineList">
							<dhcc:tableTag property="tablemfBusPactExamineList" paginate="mfBusPactExamineList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-10 col-md-offset-1 column"  id="reprot">
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
			<div class="col-xs-10 col-md-offset-1 column" >
	 				<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
	 		</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#examApprovForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="approvalBack();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId} />
	<input name="activityType" id="activityType" type="hidden" value=${activityType} />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=${designateType} />
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>