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
			var cmpdRateType='${mfBusApply.cmpFltRateShow}';
			var lastApproveType='${lastApproveType}';//上次审批审批意见类型
			$(function(){
				if($("input[name=term]").length>0){
					$("input[name=term]").bind("change",function(){
						checkTerm();
					});
				}
// 				if($("input[name=nextOpNo]").length > 0){
// 					$('input[name=nextOpNo]').popupList({
// 						searchOn: true, //启用搜索
// 						multiple: false, //false单选，true多选，默认多选
// 						ajaxUrl:webPath+"/sysUser/findSameDownBrAndRoleByPageAjax",//请求数据URL
// 						valueElem:"input[name=nextOpNo]",//真实值选择器
// 						title: "选择操作员",//标题
// 						changeCallback:function(elem){//回调方法
// 							BASE.removePlaceholder($("input[name=nextOpNo]"));
// 						},
// 						tablehead:{//列表显示列配置
// 							"opName":"操作员编号",
// 							"opNo":"操作员名称"
// 						},
// 						returnData:{//返回值配置
// 							disName:"opName",//显示值
// 							value:"opNo"//真实值
// 						}
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
			 	bindVouTypeByKindNo($("input[name=vouType]"), '');
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
				// 是否隐藏 复利利率上浮字段
				if(cmpdRateType =="0"){//隐藏						
					$('input[name=cmpdFloat]').parents("td").hide();// 字段td
					$('input[name=cmpdFloat]').parents("td").prev("td").hide();// 标签td
				}
				//当前审批节点是发回补充资料回来的
				if(lastApproveType=="6"){
					$("input[name=opinionType]").parents("tr").hide();
					$("textarea[name=approvalOpinion]").attr("title","补充资料说明");
					$("textarea[name=approvalOpinion]").parents("tr").find(".control-label").html("<font color='#FF0000'>*</font>补充资料说明");
				}
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
			 	//当前审批节点是发回补充资料回来的,刷新补充资料表单
				if(lastApproveType=="6"){
				 	$.ajax({
						url : webPath+"/mfBusApplyWkf/getApproveFormDataAjax",
						data : {
							appId : appId
						},
						type : 'post',
						dataType : 'json',
						async : false,
						success : function(data) {
							$("#edit-form").html(data.htmlStr);
							$("[name=opinionType]").parents("tr").hide();
							$("textarea[name=approvalOpinion]").attr("title","补充资料说明");
							$("textarea[name=approvalOpinion]").parents("tr").find(".control-label").html("<font color='#FF0000'>*</font>补充资料说明");
							if($("input[name=vouType]").is(':visible')){
								$("input[name=vouType]").popupSelection({
									searchOn:true,//启用搜索
									inline:false,//下拉模式
									multiple:true,//多选
									items:data.map,
									title:"担保方式",
									handle:false
								}); 
						 		$("input[name=vouType]").parent().children(".pops-value").unbind("click");
						 		$("input[name=vouType]").parent().children(".pops-value").children().children(".pops-close").remove(); 
							}
						},
						error : function() {
							LoadingAnimate.stop();
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
				}
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
					commitProcess(webPath+"/mfBusApplyWkf/submitUpdateAjax?appId="+appId+"&appNo="+appId+"&opinionType="+opinionType,obj,'applySP');
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
			/* 校验批复金额不能大于申请金额 */
			function checkAppAmt(obj){
				var appAmt1 =  $("input[name=appAmt1]").val();//申请金额
				var appAmt = $("input[name=appAmt]").val();//批复金额
				appAmt1=appAmt1.replace(/,/g,'');
				appAmt=appAmt.replace(/,/g,'');
				if(parseFloat(appAmt)>parseFloat(appAmt1)){
					$("input[name=appAmt]").val(appAmt1);
					window.top.alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"批复金额" , "timeTwo":"申请金额"}), 3);
				}
			}
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfBusApply/getSummary?appId=${mfBusApply.appId}&busEntrance=4&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
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