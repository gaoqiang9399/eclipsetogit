<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
				<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" /> 
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<style type="text/css">
			.cover {
				cursor: default;
			}
			.list-table .list-add{
				color: #32b5cb
			}
		</style>
	
		<script type="text/javascript">
		var appId,wkfAppId,cusNo,scNo;
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var rebateAppId='${rebateAppId}';//折让申请id
		var disputedAppId = '${disputedAppId}';//押品争议申请id
		var receTranAppId = '${receTranAppId}';//转让申请审批id
		var modifyId = '${modifyId}';//调价审批id
		var patrolId = '${patrolId}';//巡库审批id
		var cusNo = "${cusNo}";
		var relId = "${appId}";
		var scNo = '${scNo}';	
		var repoAppId = '${repoAppId}';
		var webPath = '${webPath}';	
		$(function(){
			$("body").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true
				},
				callbacks: {//解决单字段编辑输入框位置随滚动条变化问题
					whileScrolling: function(){
						if ($(".changeval").length>0) {
							$(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
						}
					}
				}
			});
			//adjustheight();
			top.LoadingAnimate.stop();
			showCollateralBaseInfo();
			//折让审批流程
			if(rebateAppId != null && rebateAppId != ''){
				//审批历史
				//showWkfFlow($("#wj-modeler2"),rebateAppId);
				showWkfFlowVertical($("#wj-modeler2"),rebateAppId,"8","rebate_approval");
			}
			//押品争议审批流程
			if(disputedAppId != null && disputedAppId != ''){
				//审批历史
				//showWkfFlow($("#wj-modeler2"),disputedAppId);
				showWkfFlowVertical($("#wj-modeler2"),disputedAppId,"9","disputed_approval");
			}
			//转让申请审批流程
			if(receTranAppId != null && receTranAppId != ''){
				//审批历史
				showWkfFlowVertical($("#wj-modeler2"),receTranAppId,"10","recetran_approval");
			}
			//调价审批流程
			if(modifyId != null && modifyId != ''){
				//审批历史
				//showWkfFlow($("#wj-modeler2"),modifyId);
				showWkfFlowVertical($("#wj-modeler2"),modifyId,"11","adjust_price_approval");
			}
			//反转让审批流程
			if(repoAppId != null && repoAppId != ''){
				//审批历史
				//showWkfFlow($("#wj-modeler2"),modifyId);
				showWkfFlowVertical($("#wj-modeler2"),repoAppId,"12","repo_approval");
			}
			//巡库审批流程
			if(patrolId != null && patrolId != ''){
				//审批历史
				showWkfFlowVertical($("#wj-modeler2"),patrolId,"13","patrol_approval");
			}
		});
		//展示押品详情
		function showCollateralBaseInfo(){
			LoadingAnimate.start();
			jQuery.ajax({
				url:webPath+"/mfBusCollateralRel/getCollateralListHtmlAjax",
				data:{relId:relId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag="success"){
						$.each(data.htmlMap,function(index,obj){
								var collateralId = index;
								var bean = data.beanMap[index];
								var collateralName = bean.collateralName;
								var htmlStr ='<div class="dynamic-block" title="押品基本信息" name="#collateralBase'+collateralId+'">'+
								'<div class="form-table"><div class="title">'+
								'<span class="formName"><i class="i i-xing blockDian"></i>押品基本信息</span>'+
								'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#collateralBase'+collateralId+'">'+
								'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></div>'+
								'<div class="content collapse in" id="collateralBase'+collateralId+'">'+
								'<form id="'+collateralId+'">'+obj+'</form></div></div></div></div>';
								$("#btn-group-div").after(htmlStr);
						});
					}else{
						
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
		</script>
	</head>
<body class="overflowHidden">
    <div class="container">
		<div class="row clearfix">	
			<!--申请主信息 -->
			<div class="col-md-8 column block-left" style="width:100%">
				<div class="bg-white block-left-div">
					<%-- <c:if test='${showable=="showable"}'> 
					<!-- 折让审批流程 -->
					<div class="row clearfix" id="openWkf">
						<div class="app-process">
							<div id="modeler1" class="modeler">
								<ul id="wj-modeler1" class="wj-modeler" isApp = "true">
								</ul>
							</div>
						</div>
					</div>
					</c:if> --%>
					<!--头部主要信息 -->
					<div class="row clearfix head-info">
						<div class="col-xs-3 column text-center head-img">
							<div>
								<button type="button" class="btn btn-font-pledge padding_left_15">
									<i class="i i-pledge font-icon"></i>
									<div class="font-text-left">反担保信息</div>
								</button>
							</div>
						</div>
						<div class="col-xs-9 column head-content">
							<div>
								<table>
									<tr>
										<td>
											<p class="cont-title">反担保方式</p>
											<p><span id='vouType' class="content-span">
											<c:if test='${mfBusCollateralRel.vouType=="1"}'>${mfBusCollateralRel.vouTypeName}</c:if>
											<c:if test='${mfBusCollateralRel.vouType=="2"}'>${mfBusCollateralRel.vouTypeName}</c:if>
											<c:if test='${mfBusCollateralRel.vouType=="3"}'>${mfBusCollateralRel.vouTypeName}</c:if>
											<c:if test='${mfBusCollateralRel.vouType=="4"}'>${mfBusCollateralRel.vouTypeName}</c:if>
											<c:if test='${mfBusCollateralRel.vouType=="5"}'>${mfBusCollateralRel.vouTypeName}</c:if>
											</span></p>
										</td>
										<td>
											<p class="cont-title">反担保总额</p>
											<p><span id='envalueAmt' class="content-span">
												<c:if test='${mfBusCollateralRel.collateralAmt == null}'>0.00</c:if>
												<c:if test='${mfBusCollateralRel.collateralAmt != null}'>
													<fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusCollateralRel.collateralAmt}"/>
												</c:if>
											</span><span class="unit-span margin_right_25">万</span></p>
										</td>
										<td>
											 <p class="cont-title">反担保比例</p>
											<p><span id='receiptsAmount' class="content-span">
											<c:if test='${mfBusCollateralRel.collateralRate == null}'>0.00</c:if>
											<c:if test='${mfBusCollateralRel.collateralRate != null}'>
												<fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusCollateralRel.collateralRate}" />
											</c:if>
											</span><span class="unit-span margin_right_25">%</span></p>
										</td>
										<td>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="row clearfix btn-opt-group" id="btn-group-div">
					</div>
					<c:if test='${showable=="showable"}'> 
					<!--折让审批历史-->
					<div class="row clearfix approval-hist" id="spInfo-block">
						<div class="list-table">
						   <div class="title">
								 <span><i class="i i-xing blockDian"></i>审批历史</span>
								 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
						   </div>
						   <div class="content margin_left_15 collapse in " id="spInfo-div">
								<div class="approval-process">
									<div id="modeler2" class="modeler">
										<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
						   </div>
						</div>
					</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>