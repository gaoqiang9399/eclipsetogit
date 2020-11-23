<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var cusNo = '${mfBusApply.cusNo}';
			var appId = '${mfBusApply.appId}';
			var termType = '${mfBusApply.termType}';
			var ajaxData = JSON.parse('${ajaxData}');
			var mfSysKind = '${mfSysKind}';
			var appTerm = '${mfBusApply.term}';
			var maxTerm = '${mfSysKind.maxTerm}';
			var minTerm = '${mfSysKind.minTerm}';
			var unit = termType=="1"?"个月":"天";
			var minTime = minTerm+unit;
			var maxTime = maxTerm+unit;
			$(function(){
				if($("input[name=term]").length>0){
					$("input[name=term]").bind("change",function(){
						checkTerm();
					});
				}
// 				if($("input[name=nextOpNo]").length > 0){
// 					$("input[name=nextOpNo]").popupSelection({
// 						searchOn:true,//启用搜索
// 						inline:false,//弹窗模式
// 						multiple:false,//单选
// 						labelShow : false,//已选项显示
// 						items:ajaxData.userJsonArray				
// 					});	
// 				}
				if($("input[name=vouType]").is(':visible')){
					$("input[name=vouType]").popupSelection({
						searchOn:true,//启用搜索
						inline:false,//下拉模式
						multiple:true,//多选
						items:ajaxData.map,
						title:"担保方式",
						handle:false
					}); 
			 		$("input[name=vouType]").parent().children(".pops-value").unbind("click");
			 		$("input[name=vouType]").parent().children(".pops-value").children().children(".pops-close").remove(); 
				}
			 	//bindVouTypeByKindNo($("input[name=vouType]"), '');
				var idIndex = $("#busfee-div").find("thead th[name=id]").index();
				$("#busfee-div").find("thead th[name=id]").hide();
				//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback:WkfApprove.opinionTypeChange
				});
				
				var rateScaleIndex = $("#busfee-div").find("thead th[name=rateScale]").index();
				$("#busfee-div").find("tbody tr").each(function(index){
					$thisTr = $(this);
					$thisTr.find("td").eq(idIndex).hide();
					var $rateScaleTd = $thisTr.find("td").eq(rateScaleIndex); 
					var rateScale = $rateScaleTd.html().trim();
					$rateScaleTd.html('<input value="'+rateScale+'">');
				});
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced:{
						updateOnContentResize:true
					}
				});
// 				if(termType=="1"){
// 					$("input[name=term]").parent().parent().prev().append("&nbsp;(个月)");
// 				}else{
// 					$("input[name=term]").parent().parent().prev().append("&nbsp;(天)");
// 				}
			});
			function checkTerm(){
				var term = $("input[name=term]").val();						
				if(parseFloat(term)<parseFloat(minTerm)||parseFloat(term)>parseFloat(maxTerm)){
					$("input[name=term]").val(appTerm);
					alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE",{"info":"产品设置","field":"融资期限","value1":minTime,"value2":maxTime}),0);
				}
			}
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
// 				var opinionType = $("select[name=opinionType]").val();
				var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
					commitProcess(webPath+"/mfBusApplyWkf/submitUpdateAjax?appId="+appId+"&opinionType="+opinionType,obj,'applySP');
				}
				
			}
			 
			 function updatePactEndDate(){
				var beginDate =  $("input[name=beginDate]").val();
				var term = $("input[name=term]").val();
				var termType = $("select[name=termType]").val();
				var intTerm = parseInt(term);
				if(1==termType){ //融资期限类型为月 
					var d = new Date(beginDate);
					d.setMonth(d.getMonth()+intTerm);
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
					$("input[name=endDate]").val(str);
				}else{ //融资期限类型为日 
					var d = new Date(beginDate);
				 	d.setDate(d.getDate()+intTerm);
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
					$("input[name=endDate]").val(str);
				}
			};
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfBusApply/getSummary?appId=${mfBusApply.appId}&busEntrance=8&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formapply0003" mode="query" />
					</form>	
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
	<input name="designateType" type="hidden" value=${designateType} />
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>