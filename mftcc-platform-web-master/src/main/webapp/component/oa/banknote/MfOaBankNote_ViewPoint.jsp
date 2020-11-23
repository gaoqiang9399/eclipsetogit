<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var billId = '${billId}';
			//var nodeId = '${dataMap.nodeId}';
// 			var termType = '${mfBusApply.termType}';
			$(function(){
			/* 	if('manager'!=nodeId){
					$('#edit-form table tbody').find('tr:eq(3)').addClass('hidden');
				} */
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					inline: true, //下拉模式
					multiple: false //单选
				});							
			});
			 
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
// 				var opinionType = $("select[name=opinionType]").val();
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess(webPath+"/mfOaBankNote/commitProcessAjax?appNo="+ billId+"&opinionType="+opinionType, obj,'sp');
				}
			}
		</script>
</head>
 
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="approvalDiv" class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formbanknote0003" mode="query" />
					</form>	
					</div>
				</div>
			</div>
		</div>
		<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
</html>