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
<title>外部评级</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
<link id="appEvalInfo"  type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/appEvalInfo${skinSuffix}.css" />
<style type="text/css">
#uploadTree_1{width: 100%}
</style>
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
 	var evalType = "${evalType}";
 	var aloneFlag = true;
 	var timeLimit = "${timeLimit}";
 	var dataDocParm={
			relNo:evalAppNo,
			docType:"evalPic",
			docTypeName:"外部评级资料",
			docSplitName:"外部评级相关文件",
			query:query,
	};
	$(function(){
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });

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

	//获取审批完成机构列表
	function getInstitutionName() {
        $.ajax({
            url:webPath+"/appEval/getInstitutionName",
            type:'get',
            dataType:'json',
            success:function(data){
                if(data.flag == "success"){
                    //$("select[name=institutionName]").empty();
					$.each(data.finishedOrganList, function(key,obj) {
                                $("select[name=institutionName]").append("<option value='"+ obj.organName +"'>"+ obj.organName +"</option>");
					});
                }
            }
        });
    }
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="eval-content" style="display: none;">
		<div class="content_show" style="height: 100%" >
			<ul class="content_ul">
				<li>
					<div  name="evalapp" class="li_content_type container form-container">
						<div class="scroll-content" id="evalapp">
							<div class="col-md-10 col-md-offset-1 column margin_top_20">
								<div class="bootstarpTag">
									<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
									<form  method="post" id="choseFinForm" theme="simple"
										name="operform" action="${webPath}/appEval/evalManSubmitAjax">
										<div id="choseFinDiv">
											<c:if test="${query!='query'}">
												<dhcc:bootstarpTag property="formevalpersman" mode="query" />
										    </c:if>
										</div>
									</form>
																
								</div>
								<c:if test="${evalType == '2'}">
								<!--外部评级资料 -->
									<div class="row clearfix">
										<div class="col-xs-12 column" >
											<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
										</div>
									</div>
								</c:if>
							</div>
						</div>
						<c:if test="${query!='query'}">
							<div class="formRowCenter" id ="chosefinButton" style="bottom:40px\9;">
								<dhcc:thirdButton value="保存" action="保存" onclick="evalManSubmitAjax('#choseFinForm','${webPath}/appEval/evalManSubmitAjax')"></dhcc:thirdButton>
								<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
							</div>
						</c:if>
					</div>
				</li>
			</ul>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/eval/js/appEvalInitData_new.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/appEval_new.js"></script>
<script type="text/javascript" src="${webPath}/component/eval/js/appEvalSave_new.js"></script>
<script type="text/javascript">
window.evalManSubmitAjax = function(obj,url){
	var $obj = $(obj);
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var ajaxUrl = $(obj).attr("action");
		var updateFlag = false;
		if(ajaxUrl===undefined||ajaxUrl==null||ajaxUrl==""){
			ajaxUrl = url;
			updateFlag = true;
		}
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		LoadingAnimate.start();
		jQuery.ajax({
			url:ajaxUrl,
			data:{ajaxData:dataParam,evalAppNo:evalAppNo,timeLimit:timeLimit},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					//审批提醒弹窗，不自动关闭
					window.top.alert(data.msg,3);
					$obj.find(".from_btn").remove();
					//window.top.cloesBigForm(window,"","iframepage");
					top.cusLevelName = data.cusLevelName;
					top.creditFlag=true;
					myclose_click();
				}else if(data.flag=="error"){
					window.top.alert(data.msg,0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
};
</script>
</html>