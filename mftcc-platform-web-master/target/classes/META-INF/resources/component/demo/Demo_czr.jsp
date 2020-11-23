<%-- [{"width":"344px","height":"294px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"344px","height":"243px","left":"0px","top":"301.7833251953125px","name":"","cellid":"cell_2","formId":"test1002","formType":"propertySeeTag","formUrl":""},{"width":"596px","height":"243px","left":"352.066650390625px","top":"0px","name":"","cellid":"cell_3","formId":"test1002","formType":"propertySeeTag","formUrl":""},{"width":"344px","height":"394px","left":"955.63330078125px","top":"0px","name":"点此拖拽","cellid":"cell_4","formId":"tabletest1001","formType":"tableTag","formUrl":""},{"width":"596px","height":"143px","left":"352.066650390625px","top":"251.48333740234375px","name":"点此拖拽","cellid":"cell_5","formId":"test1012","formType":"recordTag","formUrl":""},{"width":"596px","height":"143px","left":"352.066650390625px","top":"402.3666687011719px","name":"","cellid":"cell_6","formId":"test1012","formType":"recordTag","formUrl":""},{"width":"344px","height":"143px","left":"955.63330078125px","top":"402.3666687011719px","name":"","cellid":"cell_7","formId":"test1012","formType":"recordTag","formUrl":""}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"test1002","formType":"propertySeeTag","formUrl":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"test1002","formType":"propertySeeTag","formUrl":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"form","formId":"tabletest1001","formType":"tableTag","formUrl":"","chart":{}},"cell_5":{"cellid":"cell_5","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"form","formId":"test1012","formType":"recordTag","formUrl":"","chart":{}},"cell_6":{"cellid":"cell_6","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"test1012","formType":"recordTag","formUrl":"","chart":{}},"cell_7":{"cellid":"cell_7","cellname":"","celltype":"","cellsts":"","plugintype":"form","formId":"test1012","formType":"recordTag","formUrl":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
String cellDatas = (String)request.getAttribute("cellDatas");
String blockDatas = (String)request.getAttribute("blockDatas");
System.out.println("@@@@@"+cellDatas);
System.out.println("@@@@@"+blockDatas);
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
						myChart.setOption(blockData[key].chart.option).children;
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
				<div class="info">
					<dhcc:propertySeeTag property="formdemo0008" mode="query"/>
				</div>
			</div>
			<div class='cell' cellid='cell_2' style='top:301.7833251953125px; left:0px; width:344px; height: 243px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<form  method="post" theme="simple" name="operform" action="${webPath}/demo/updateAjaxByOne">
							<dhcc:propertySeeTag property="formdemo0003" mode="query"/>
					</form>
				</div>
			</div>
			<div class='cell' cellid='cell_3' style='top:0px; left:352.066650390625px; width:596px; height: 243px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<form  method="post" theme="simple" name="operform" action="${webPath}/demo/updateAjaxByOne">
							<dhcc:propertySeeTag property="formdemo0001" mode="query"/>
					</form>
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
					<form  method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
							<%--<dhcc:tableTag paginate="testGnList" property="tabletest1001" head="true" />
					--%></form>
				</div>
			</div>
			<div class='cell' cellid='cell_5' style='top:251.48333740234375px; left:352.066650390625px; width:596px; height: 143px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<form  method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
							<dhcc:recordCountTag property="formdemo0002" mode="query"/>
					</form>
				</div>
			</div>
			<div class='cell' cellid='cell_6' style='top:402.3666687011719px; left:352.066650390625px; width:596px; height: 143px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<form  method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
							<dhcc:recordCountTag property="formdemo0004" mode="query"/>
					</form>
				</div>
			</div>
			<div class='cell' cellid='cell_7' style='top:402.3666687011719px; left:955.63330078125px; width:344px; height: 143px; background-color:#EBEBEB' data-handle=".handle">
				<div class="info">
					<form  method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
							<!--<dhcc:propertySeeTag property="formdemo0008" mode="query"/>-->
					</form>
				</div>
			</div>
	</div>
    </div>
</body>
</html>