<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
	    <link rel="stylesheet" href="${webPath}/themes/factor/css/list.css?v=${cssJsVersion}" />
		<script type="text/javascript">
			var fincMainId = '${fincMainId}';
			var appId = '${appId}';
			var pactId = '${pactId}';
			var cusNo = '${cusNo}';
			var nodeNo = '${nodeNo}';
            var ajaxData = '${ajaxData}';
            ajaxData = JSON.parse(ajaxData);
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced:{
						updateOnContentResize:true
					}
				});
                //还款方式处理
                var $repayObj = $("input[name=repayType]");
                $("input[name=repayType]").popupSelection({
                    searchOn:false,//启用搜索
                    inline:false,//下拉模式
                    multiple:false,//多选选
                    title:"还款方式",
                    valueClass:"show-text",
                    items:ajaxData.repayTypeMap,
                    changeCallback : function (obj, elem) {
                    }
                });
                if(typeof($repayObj.attr('readonly'))!='undefined'){
                    $repayObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
                }

				//当前审批节点是发回补充资料回来的
				if(nodeNo=="finc_supplement_data"){
					$("[name=opinionType]").parents("tr").hide();
					$("textarea[name=approvalOpinion]").attr("title","补充资料说明");
					$("textarea[name=approvalOpinion]").parents("tr").find(".control-label").html("<font color='#FF0000'>*</font>补充资料说明");
				}
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
				var opinionType = $("[name=opinionType]").val();
				//没有选择审批意见默认同意
				if(opinionType == undefined || opinionType == ''){
					opinionType = "1";
				}
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
					commitProcess(webPath+"/mfBusFincAppMain/submitUpdateForBatchAjax?appId=${appId}&fincMainId="+fincMainId+"&appNo="+fincMainId+"&opinionType="+opinionType,obj,'applySP');
				}
				
			}
		</script>
</head>
 
<body  class="overflowHidden bg-white multi-select-list">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
				<iframe src="${webPath}/mfBusFincAppMain/getSummary?appId=${appId}&pactId=${pactId}&fincMainId=${fincMainId}&busEntrance=batch_finc_approve&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formbatchfincapprovalbase" mode="query" />
					</form>	
				</div>
				<div class="list-table">
					<div class="title">
						<span><i class="i i-xing blockDian"></i>出账明细</span>
						<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#accountList">
							<i class="i i-close-up"></i>
							<i class="i i-open-down"></i>
						</button>
					</div>
					<div id="accountList" class="content collapse in" aria-expanded="true">
						<dhcc:tableTag property="tablefincApproveBatchListBase" paginate="mfBusFincAppList" head="true"></dhcc:tableTag>
					</div>
				</div>
				<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="approvalBack();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId} />
	<input name="activityType" id="activityType" type="hidden" value=${activityType} />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>