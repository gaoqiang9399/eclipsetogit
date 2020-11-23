<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
	Object dataMap = request.getAttribute("dataMap");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<title>无标题文档</title>
	<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
	<link href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${webPath}/themes/view/css/view-main.css" rel="stylesheet" type="text/css">
	<link href="${webPath}/themes/view/css/filter.css" rel="stylesheet" type="text/css">
	<link href="${webPath}/themes/view/css/formline.css" rel="stylesheet" type="text/css">
	<link id="appEvalInfo"  href="css/appEvalInfo${skinSuffix}.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="${webPath}/layout/view/themes/css/initScroll.css" type="text/css" />
	<link type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/detailResult.css?v=${cssJsVersion}" />
	<script>
	   var dataMap = <%=dataMap%>;
	   var types = "";
		$(function(){
		    var flag = dataMap["flag"];
			if(flag=="error"||flag===undefined){
				$("body").html("暂无评级信息");
			}else if(falg = "success"){
				var grade = dataMap["grade"];
				var gradeScore = dataMap["gradeScore"];
				var listData = dataMap["listData"];
				if(listData!==undefined){
					if(grade===undefined||grade==""||grade==null){
						grade = 7;
					}
					$("#content").find(".evel_table .levels").addClass("level_"+grade);
					$("#content").find(".evaluation").text(dataMap["evalAssess"]);
					$("#content").find(".evel_table .count_score").html(gradeScore);
					if(gradeScore>=100){
						gradeScore=99;
					}
					$("#content").find(".evel_table .bar_div .bar_inner").css("width",gradeScore+"%");
					$.each(listData, function(key,dates) {
						types+=key;
						if(key=="DX"){
							var $table = $("table.ls_list_a");
							var thLength = $table.find("thead th").length;
							$table.find("tbody").html("");
							$.each(dates["LIST"], function(index,entityObj) {
								var $tr = $("<tr></tr>");
								var level = entityObj.level;
								var upIndexNo = entityObj.upIndexNo;
								$tr.data("entityObj",entityObj);
								if(level==1){
									$tr.append('<td class="font_weight border_left" rowspan="1">'+entityObj.indexName+'</td><td class="border_right"></td>');
									$table.find("tbody").append($tr);
								}else{
									$table.find("tbody tr").each(function(index){
										if($(this).data("entityObj")!==undefined){
											var $ValTd = $(this).find("td").eq(1);
											var thisIndexNo = $(this).data("entityObj").indexNo;
											if(upIndexNo==thisIndexNo){
												var $radioInput = $('<input name="'+thisIndexNo+'" type="radio" value="'+entityObj.stdCore+'">');
												if($ValTd.find("table").length>0){
													$ValTd.find("table tbody").append('<tr><td>'+$radioInput.prop('outerHTML')+entityObj.indexName+'</td></tr>');
												}else{
													var $thisTable = $('<table class="ls_list_b" style="width: 100%; margin: 5px 0px"><tbody><tr><td></td></tr></tbody></table>');
													$radioInput.attr("checked","checked");
													$thisTable.find("tr td").append($radioInput.prop('outerHTML')+entityObj.indexName);
													$ValTd.append($thisTable);
												}
											}
										}
									});
								}
							});
							if(dates["entity"]!=null){
								$(".dx_score").html(dates["entity"].score);
								intiVal(dates["entity"]);  
							}
						}else if(key=="DL"){
							$.each(dates["LIST"], function(index,data) {
								var level = data["level"];
								if(level==2){
									var indexName = data["indexName"];
									var indexVal =  data["javaItem"];
									var score = data["opVal1"];
									var $tr = $("<tr></tr>");
									$tr.append('<td name="indexName" style="padding-left: 15px; width: 40%;">'+indexName+'</td>');
									$tr.append('<td name="javaItem" style="width: 40%;">'+indexVal+'</td>');
									$tr.append('<td name="score">'+score+'</td>');
									$("#dl_show").find("table.ls_list tbody").append($tr);
								}
							});
							$(".dl_score").html(dates["SCORE"]);
						}
					});
					if(types.indexOf("DX")==-1){
						$("#dx_show").parents(".statisticsCenter").remove();
						$(".dx_score").parents(".div_line").remove();
					}
					if(types.indexOf("DL")==-1){
						$("#dl_show").parents(".statisticsCenter").remove();
						$(".dl_score").parents(".div_line").remove();
					}
					$(".statisticsRight").mCustomScrollbar();
				}
			}
			function intiVal(dxData){
				//判断定性存不存在 初始化数据
				if($(".li_content .ls_list_a").length>0){
					var dxList = dxData.scoreList;
					for(var i=0;i<dxList.split("@").length-1;i++){
						var dxobj = dxList.split("@")[i];
						var name = dxobj.split(":")[0];
						var vuale = dxobj.split(":")[1];
						$(".li_content .ls_list_a").find("input[type=radio][name="+name+"]").each(function(index){
							if(vuale==$(this).attr("value")){
								$(this).prop("checked",true);
								return false;
							}
						});
					}
				}
			};
			var size = $(".statisticsCenter").length;
			$(".statisticsCenter").css("width",100/size+"%");
			$(".statisticsRight").height($("body").height()-80);
			//加载滚动条
			$(".statisticsRight").mCustomScrollbar({
				scrollButtons:{
			        enable:true,
			        autoHideScrollbar: true,
			        scrollAmount:200
			    },advanced:{
			        autoExpandHorizontalScroll:true,
			        updateOnBrowserResize:true,
				    updateOnContentResize:true,
				    autoScrollOnFocus:true,
			        scrollSpeed:50
			    }
			});
			$(window).resize(function() {
				$(".statisticsRight").height($("body").height()-80);
				$(".statisticsRight").mCustomScrollbar("update");
			});
		});
	</script>
	<style>
		html {
			height: 100%;
		}
		.statistics {
			padding: 0 15px;
			height: 100%
		}
		.statisticsCenter {
			float: left;
			width: 33%;
			position: relative;
		}
		body.contentA table.evel_table {
			width: calc(100% -   100px);
		}
		.statisticsRightTitle {
			top: 38px;
			left: 20px;
			font-weight: 700;
			padding-top: 7px;
			border-top: 1px solid #afc5d0;
			width: 72px;
			float: left;
			
		}
		body.contentA .bigform_content .content_table .ls_list>tbody>tr>td{
			text-align:center;
		}
		body.contentA .bigform_content .content_table .ls_list>thead>tr>th {
		    background-color: #f7f7f7;
		    border-bottom: 1px solid #ccc;
		    color: #000;
		    padding: 0 5px;
		}
		.score_text{
			margin-left: 13px;
			color: #7e9abb;
		}
		.score_title{
			position: relative; 
			float: right; 
			right: 25px;
			color: #00b5f2;
			font-size: 16px;
		}
		.ls_list_a table>tbody>tr>td{
			border-bottom:none;
		}
		.ls_list_a tbody tr td {
		    background: #f7f7f7 none repeat scroll 0 0;
		    border-bottom: 1px solid #ddd;
		    color: #333;
		    line-height: 30px;
		    padding: 0 5px;
		}
		.radio_lable {
		    top: -1px;
		}
	</style>
</head>

<body style="padding: 10px; height: 100%;" class="contentA cellBoxBg">
	<div class="cellBg statistics">
		<h3>评级信息</h3>
		<div class="bigform_content">
			<div class="content_table">
				<div class="input_btn"></div>
				<!--页面显示区域-->
				<div id="content" class="table_content" style="height: auto;">
						<div class="statisticsCenter" id="freewall">
							<div class='cell' cellid='cell_1'>
								<div class="info">
									<table class="evel_table">
										<tbody>
											<tr>
												<td style="width: 50%"><span class="levels"></span>
													<span class="evel_title">综合评价</span>
													<span class="evaluation">偿还债务能力较强,较易受不利环境影响,等级风险低。</span>
												</td>
											</tr>
											<tr>
												<td style="width: 50%">
													<div style="text-align: right;">
														<span class="score_lable">得分</span>
														<span class="count_score">0</span>
													</div>
													<div class="bar_div">
														<span class="bar_outter">
														<span class="bar_inner" style="width: 94%;"></span></span>
													</div>
													<div class="div_line show_0">
														<span class="radio_span">
															<span class="radio_span_inner"></span>
														</span>
														<span class="radio_lable">定量得分</span>
														<span class="radio_filed dl_score">0</span>
													</div>
													<div class="div_line show_1">
														<span class="radio_span">
															<span class="radio_span_inner"></span>
														</span>
														<span class="radio_lable">定性得分</span>
														<span class="radio_filed dx_score">0</span>
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- statistics-right -->
						<div class="statisticsCenter" data="dl_show">
							<div class="title_score">
								<span class="score_text">定量指标</span>
							</div>
							<div class="statisticsRight">
								<div id="dl_show" class="li_content_type">
									<div class="li_content">
										<table class="ls_list" style="width: 100%">
											<thead>
												<tr>
													<th name="indexName" class="text_align_s"
														style="width: 40%;">指标名称</th>
													<th name="javaItem" style="width: 32%;">业务参数</th>
													<th name="score">得分</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						
						<div class="statisticsCenter" data="dx_show">
							<div class="title_score">
								<span class="score_text">定性指标</span>
							</div>
							<div class="statisticsRight">
								<div id="dx_show" class="li_content_type">
									<div class="li_content">
										<table class="ls_list_a" style="width:100%;margin-top: 9px;">
										<thead>
											<tr>
											 	<th style="width: 20%;" name="indexName">指标名称</th>
											 	<th name="ctrl_btn">打分选项</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>