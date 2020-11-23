<%-- [{"width":"406px","height":"251px","left":"0px","top":"0px","name":"","cellid":"cell_1","formId":"demo0008","formType":"propertyTag","formUrl":""},{"width":"406px","height":"302px","left":"0px","top":"258.6000061035156px","name":"登记信息","cellid":"cell_2"},{"width":"457px","height":"147px","left":"413.76666259765625px","top":"258.6000061035156px","name":"还款信息","cellid":"cell_4"},{"width":"406px","height":"147px","left":"879.2333374023438px","top":"258.6000061035156px","name":"其他信息","cellid":"cell_5"},{"width":"871px","height":"147px","left":"413.76666259765625px","top":"413.76666259765625px","name":"备注信息","cellid":"cell_6"},{"width":"871px","height":"251px","left":"413.76666259765625px","top":"0px","name":"融资信息","cellid":"cell_6"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"demo0008","formType":"propertyTag","formUrl":"","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"登记信息","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"还款信息","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_5":{"cellid":"cell_5","cellname":"其他信息","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_6":{"cellid":"cell_6","cellname":"融资信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
String cellDatas = (String)request.getAttribute("cellDatas");
String blockDatas = (String)request.getAttribute("blockDatas");
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="${webPath}/tech/layoutDesginer/css/style.css" />
		<script type="text/javascript" src="${webPath}/component/demo/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/freewall.js"></script>
		<link rel="stylesheet" href="${webPath}/component/demo/css/font-awesome.min.css" type="text/css" />
		<!--<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/customfw.js"></script>-->
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
		<script type='text/javascript' src="${webPath}/component/demo/js/dbClick.js"></script>
		<link rel="stylesheet" type="text/css"  href="${webPath}/component/demo/css/property.css"/>
		<style type="text/css">
		.cover {
		    cursor: default;
		}
		</style>
		<script type="text/javascript">
		window.onload = function(){
			var wall = new Freewall("#freewall");
			wall.reset({
				draggable: false,
				selector: '.cell',
				animate: true,
				fixSize:0,
				gutterX: 8,
				gutterY: 8,
				onResize: function() {
					wall.fillHoles();
					wall.refresh();
				}
			});
			if('<%=blockDatas%>'!=null){
				var blockData = eval("("+'<%=blockDatas%>'+")");
				for(var key in blockData){
					var plugintype = blockData[key].plugintype;
					var $cell = $(".cell[cellid='"+key+"']");
					var info = $cell.find('.info')[0];
					if($cell.find(".cover").length>0){
						info.style.height=($cell.height()-35)+"px";
						info.style.width=($cell.width()-10)+"px";
					}else{
						info.style.height="100%";
						info.style.width="100%";
						info.style.top="0px";
						info.style.margin="0";
					}
					if(plugintype=="charts"&&typeof(blockData[key].chart)!="undefined"){
						var myChart = echarts.init(info);
						myChart.setOption(blockData[key].chart.option);
					}
				}
			}
			wall.fillHoles();
			wall.refresh();
		}
		</script>
	</head>
<body onselectstart="return false">
    <div class="layout">
	<div id="freewall" style="margin: 8px;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:0px; width:406px; height: 251px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<form  method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
							<dhcc:propertySeeTag property="formdemo0008" mode="query"/>
					</form>
				</div>
			</div>
			<div class='cell' cellid='cell_2' style='top:258.6000061035156px; left:0px; width:406px; height: 302px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>登记信息</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_4' style='top:258.6000061035156px; left:413.76666259765625px; width:457px; height: 147px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>还款信息</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_5' style='top:258.6000061035156px; left:879.2333374023438px; width:406px; height: 147px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>其他信息</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_6' style='top:413.76666259765625px; left:413.76666259765625px; width:871px; height: 147px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>备注信息</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_6' style='top:0px; left:413.76666259765625px; width:871px; height: 251px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>融资信息</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
	</div>
    </div>
</body>
</html>