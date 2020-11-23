<%-- [{"width":"344px","height":"294px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"344px","height":"243px","left":"0px","top":"301.7833251953125px","name":"","cellid":"cell_2","formId":"test1002","formType":"propertyTag","formUrl":""},{"width":"596px","height":"243px","left":"352.066650390625px","top":"0px","name":"","cellid":"cell_3","formId":"test1002","formType":"propertyTag","formUrl":""},{"width":"344px","height":"394px","left":"955.63330078125px","top":"0px","name":"点此拖拽","cellid":"cell_4","formId":"tabletest1001","formType":"tableTag","formUrl":""},{"width":"596px","height":"143px","left":"352.066650390625px","top":"251.48333740234375px","name":"点此拖拽","cellid":"cell_5","formId":"test1012","formType":"recordTag","formUrl":""},{"width":"596px","height":"143px","left":"352.066650390625px","top":"402.3666687011719px","name":"","cellid":"cell_6","formId":"test1012","formType":"recordTag","formUrl":""},{"width":"344px","height":"143px","left":"955.63330078125px","top":"402.3666687011719px","name":"","cellid":"cell_7","formId":"test1012","formType":"recordTag","formUrl":""}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"test1002","formType":"propertyTag","formUrl":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"test1002","formType":"propertyTag","formUrl":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"form","formId":"tabletest1001","formType":"tableTag","formUrl":"","chart":{}},"cell_5":{"cellid":"cell_5","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"form","formId":"test1012","formType":"recordTag","formUrl":"","chart":{}},"cell_6":{"cellid":"cell_6","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"test1012","formType":"recordTag","formUrl":"","chart":{}},"cell_7":{"cellid":"cell_7","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"test1012","formType":"recordTag","formUrl":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String contextPath = request.getContextPath();
String cellDatas = (String)request.getAttribute("cellDatas");
String blockDatas = (String)request.getAttribute("blockDatas");
System.out.println("@@@@@"+cellDatas);
System.out.println("@@@@@"+blockDatas);
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib prefix="dhcc1" uri="http://www.lssrc.com/tags.tld"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<link rel="stylesheet" type="text/css" href="${webPath}/drag/layout/desgin/css/style.css" />
		<script type="text/javascript" src="${webPath}/drag/layout/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/drag/layout/desgin/js/freewall.js"></script>
		<link rel="stylesheet" href="${webPath}/drag/layout/css/font-awesome.min.css" />
		<!--<script type="text/javascript" src="${webPath}/drag/layout/desgin/js/customfw.js"></script>-->
		<script type="text/javascript" src="${webPath}/drag/layout/desgin/js/echarts-all.js"></script>
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
			<div class='cell' cellid='cell_1' style='top:0px; left:0px; width:344px; height: 294px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>点此拖拽</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_2' style='top:301.7833251953125px; left:0px; width:344px; height: 243px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<s:form method="post" theme="simple" name="operform" action="TestGnAction_update.action">
							<dhcc1:propertyTag property="formtest1002" mode="query"/>
					</s:form>
				</div>
			</div>
			<div class='cell' cellid='cell_3' style='top:0px; left:352.066650390625px; width:596px; height: 243px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<s:form method="post" theme="simple" name="operform" action="TestGnAction_update.action">
							<dhcc1:propertyTag property="formtest1002" mode="query"/>
					</s:form>
				</div>
			</div>
			<div class='cell' cellid='cell_4' style='top:0px; left:955.63330078125px; width:344px; height: 394px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>点此拖拽</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
					<s:form method="post" theme="simple" name="operform" action="TestGnAction_update.action">
							<dhcc:tableTag paginate="testGnList" property="tabletabletest1001" head="true" />
					</s:form>
				</div>
			</div>
			<div class='cell' cellid='cell_5' style='top:251.48333740234375px; left:352.066650390625px; width:596px; height: 143px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>点此拖拽</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
					<s:form method="post" theme="simple" name="operform" action="TestGnAction_update.action">
							<dhcc1:recordTag property="formtest1012" mode="query"/>
					</s:form>
				</div>
			</div>
			<div class='cell' cellid='cell_6' style='top:402.3666687011719px; left:352.066650390625px; width:596px; height: 143px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<s:form method="post" theme="simple" name="operform" action="TestGnAction_update.action">
							<dhcc1:recordTag property="formtest1012" mode="query"/>
					</s:form>
				</div>
			</div>
			<div class='cell' cellid='cell_7' style='top:402.3666687011719px; left:955.63330078125px; width:344px; height: 143px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<s:form method="post" theme="simple" name="operform" action="TestGnAction_update.action">
							<dhcc1:recordTag property="formtest1012" mode="query"/>
					</s:form>
				</div>
			</div>
	</div>
    </div>
</body>
</html>