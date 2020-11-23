<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<style type="text/css">
			#bus_approval_list input, select{
				height: 35px;
			}
			
			#bus_approval_list .operating{
				height: 35px;
				padding: 10px 5px;
			}
		
			#bus_approval_list .operating .i{
				font-size: 18px;
			}
		
		</style>
		<script type="text/javascript">
			var cusNo = '${mfBusApply.cusNo}';
			var appId = '${mfBusApply.appId}';
			var appStep = '${appStep}';
// 			var termType = '${mfBusApply.termType}';
			$(function(){
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
				//只展示无需带产品
				bindVouTypeByKindNo($("input[name=vouType]"), '');
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
				if(appStep=='2'){
					$("#bus_approval_list tbody tr:first").find('td:last').html('<div class="operating"><i class="i i-add ui-icon-plus"></i><i class="i i-x1 ui-icon-trash"></i></div>');
				}
				addListEvent();
			});
			 
			 //审批提交
			function doSubmit(obj){
			    var opinionType = $("select[name=opinionType]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess(webPath+"/mfAssureApply/submitUpdateAjax?opinionType"+opinionType+"&appId="+ appId+"&appStep="+ appStep, obj,'busSP');
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
			 
			 function addListEvent(){
				 if('2'==appStep){
					 $("#bus_approval_list").find("input[name=reviewName]").off('click').on('click', function(){
						var input = $(this);
						var noInput = $(this).parents('tr').find("input[name=reviewNo]");
						selectUserDialog(function(data){
							input.val(data.opName);
							noInput.val(data.opNo);
						});
					 });
					//添加一行
					$('.ui-icon-plus').unbind().on('click', function(){
						var trObj = $(this).parents('tr');
						var nextTr = $(trObj.prop('outerHTML'));
						nextTr.find('td').children('input,select').val('');
						trObj.after(nextTr);
						addListEvent();
					});
					//删除一行
					$('.ui-icon-trash').unbind().on('click', function(){
						var trObj = $(this).parents('tr');
						if(trObj.siblings('tr').length > 0){
							trObj.remove();
						}else{
							trObj.find('td').children('input,select').val('');
						}
					});
				 }
			 }
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<iframe src="${webPath}/mfBusApply/getSummary?appId=<s:property value="appId"/>&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formapplyzh00001" mode="query" />
						<c:if  test='appStep=="1"'>
							<div class="feeInfo showOrHide">
								<div class="list-table margin_0">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>业务审查表</span>
								</div>
								<div class="content_table collapse in" id="bus_approval_list">
									<dhcc:tableTag property="tableapprovalcensor0001" paginate="mfBusCensorBaseList" head="true"></dhcc:tableTag>
								</div>
								</div>
							</div>
						</c:if>
						<c:if  test='appStep=="2"'>
							<div class="feeInfo showOrHide">
								<div class="list-table margin_0">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>审保会决议</span>
								</div>
								<div class="content_table collapse in" id="bus_approval_list">
									<dhcc:tableTag property="tableapprovalaudit0001" paginate="mfBusApplyAuditHisList" head="true"></dhcc:tableTag>
								</div>
								</div>
							</div>
						</c:if>
						<c:if  test='appStep=="3"'>
							<div class="feeInfo showOrHide">
								<div class="list-table margin_0">
								<div class="title">
									<span><i class="i i-xing blockDian"></i>业务审查表</span>
								</div>
								<div class="content_table collapse in" id="bus_show_approval_list">
									<dhcc:tableTag property="tableapprovalcensorshow" paginate="mfBusCensorDataList" head="true"></dhcc:tableTag>
								</div>
								</div>
							</div>
						</c:if>
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