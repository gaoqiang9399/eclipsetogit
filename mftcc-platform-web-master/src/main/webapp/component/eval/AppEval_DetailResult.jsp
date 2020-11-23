<%-- [{"width":"596px","height":"213px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"656px","height":"495px","left":"603.75px","top":"0px","name":"点此拖拽","cellid":"cell_2"},{"width":"596px","height":"274px","left":"0px","top":"221.38333129882812px","name":"点此拖拽","cellid":"cell_3"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"charts","subplugintype":"line","chart":{"option":{"title":{"text":"评级历史","subtext":"副标题文本"},"tooltip":{"show":true,"formatter":"{a} <br>{b} : {c}","trigger":"axis"},"legend":{"show":true,"selectedMode":false,"data":["最高温度","最低温度"]},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"xAxis":[{"type":"time","name":"时间"}],"yAxis":[{"type":"category","name":"评级级别","data":["周一","周二","周三","周四","周五"]}],"series":[{"name":"最高温度","type":"line","data":["11","-2","4","14","5"],"markLine":{"data":[]}},{"name":"最低温度","type":"line","data":["7","-9","0","2","1"],"markLine":{"data":[]}}]},"type":"line","name":"折线图","chartObj":null}},"cell_4":{"cellid":"cell_4","cellname":"点此拖拽","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String cellDatas = (String)request.getAttribute("cellDatas");
	String blockDatas = (String)request.getAttribute("blockDatas");
	Object dataMap = request.getAttribute("dataMap");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<link rel="stylesheet" href="${webPath}/component/eval/css/detailResult.css" />
		<link type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/appEvalInfo.css" />
		<style type="text/css">
			.cover {
			    cursor: default;
			}
		</style>
		<script type="text/javascript">
		    var dataMap = ${dataMap};
		    var evalAppNo='${appEval.evalAppNo}';
		    var gradeType='${appEval.gradeType}';
		    var evalClass='${appEval.evalClass}';
		    var appWorkFlowNo='${appEval.appWorkFlowNo}';
		    var detailFlag='${detailFlag}';
		    var cusBaseType='${cusBaseType}';
		    var useType='${useType}';
		    var evalType='${appEval.evalType}';
		    var ifExisRisk='${ifExisRisk}';
		    var evalRulesConfirmFlag='${evalRulesConfirmFlag}';
		    function openBigForm(obj,url){
				window.top.window.openBigForm(webPath + url,'评级',false);
			}
		</script>
		<script type="text/javascript" src="${webPath}/component/eval/js/detailResult.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
	</head>
<body class="overflowHidden bg-white">
    <div class="layout container form-container">
		<div id="freewall" class="scroll-content">
			<div id = "detailInfo-div" class="info" style="padding:10px;">
				<div class='cover'>
					<div class='handle'>
						<button class="btn btn-opt"  onclick="getFinancialRatio();"type="button" >
							<i class="i i-tongji"></i><span>财务比率工具</span>
						</button>
					</div>
				</div>
				<div id="evalInfo">
					<table class="evel_table">
						<tbody></tbody>
					</table>
				</div>
				<dhcc:pmsTag pmsId="cus-eval-grade-card">
					<c:forEach items="${evalGradeCardList }" var="evalGradeCard">
						<!-- 定性 -->
						<div id="${evalGradeCard.gradeCardId}" name = "${evalGradeCard.gradeCardId}" class="li_content_type">
							<div class='cover'>
								<div class='handle'>
									<span>${evalGradeCard.gradeCardName}</span>
								</div>
							</div>
							<div class="li_content">
									<table class="ls_list_a" style="width:100%">
										<thead></thead>
										<tbody></tbody>
									</table>
							</div>
						</div>
					</c:forEach>
				</dhcc:pmsTag>
				<div id ="evalApproveHis-div" class="table_content approval-hist">
					<div class="list-table">
						<div class="title" style="background:#f8f9fc;height: 37px;">
							 <span><i class="i i-xing blockDian"></i>审批历史</span>
							 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
								<i class='i i-close-up'></i>
								<i class='i i-open-down'></i>
							</button>
					  	</div>
					  	<div class="content margin_left_15 collapse in " id="spInfo-div">
                                    <div class="approval-process">
                                         <div id="modeler1" class="modeler">
                                              <ul id="wj-modeler2" class="wj-modeler" isApp = "false">
                                              </ul>
                                         </div>
                                    </div>
                              </div>
					</div>
				</div>
			</div>
			<div style="padding-left: 10px;">
				<div id ="evalHis-div" class="bigform_content col_content">
					<div class='cover'>
						<div class='handle'>
							<span>${showName}历史列表</span>
						</div>
					</div>
					<div id="corpEvalHis" class="table_content"  style="width:100%;margin-left:-15px;">
						
					</div>
					<div id="persEvalHis" class="table_content" style="width:100%;margin-left:-15px;">
					
					</div>
				</div>
			</div>
		</div>
    </div>
</body>
<script>
	$(function(){
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
	
	});
</script>
	<script>
	 $("#evalHis-div .table_content:visible table tbody tr").each(function(i, tr){
	     if($(tr).find("td:first-child a").text() == evalAppNo){
	     	$(this).hide();
	     }
	    });
	</script>
</html>