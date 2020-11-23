<%-- [{"width":"664px","height":"302px","left":"0px","top":"0px","name":"","cellid":"cell_1"},{"width":"613px","height":"302px","left":"672.36669921875px","top":"0px","name":"","cellid":"cell_2"},{"width":"664px","height":"251px","left":"0px","top":"310.316650390625px","name":"","cellid":"cell_3"},{"width":"613px","height":"251px","left":"672.36669921875px","top":"310.316650390625px","name":"","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"","celltype":"","cellsts":"","plugintype":"charts","subplugintype":"line","chart":{"option":{"title":{/*"text":"业务增长情况","subtext":""*/},"tooltip":{"show":true,"formatter":"{a} <br>{b} : {c}","trigger":"axis"},"legend":{"show":true,"selectedMode":false,"data":["最高温度","最低温度"],y:20},"toolbox":{"show":true,y:20,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"xAxis":[{"type":"category","name":"","data":["周一","周二","周三","周四","周五"]}],"yAxis":[{"type":"value","name":""}],"series":[{"name":"最高温度","type":"line","data":["11","-2","4","14","5"],"markLine":{"data":[]}},{"name":"最低温度","type":"line","data":["7","-9","0","2","1"],"markLine":{"data":[]}}]},"type":"line","name":"折线图","chartObj":null}},"cell_2":{"cellid":"cell_2","cellname":"","celltype":"","cellsts":"","plugintype":"charts","subplugintype":"line","chart":{"option":{"title":{/*"text":"放款情况","subtext":""*/},"tooltip":{"show":true,y:20,"formatter":"{a} <br>{b} : {c}","trigger":"axis"},"legend":{"show":true,"selectedMode":false,"data":["最高温度","最低温度"],y:20},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"xAxis":[{"type":"category","name":"","data":["周一","周二","周三","周四","周五"]}],"yAxis":[{"type":"value","name":""}],"series":[{"name":"最高温度","type":"line","data":["11","-2","4","14","5"],"markLine":{"data":[]}},{"name":"最低温度","type":"line","data":["7","-9","0","2","1"],"markLine":{"data":[]}}]},"type":"line","name":"折线图","chartObj":null}},"cell_3":{"cellid":"cell_3","cellname":"","celltype":"","cellsts":"","plugintype":"charts","subplugintype":"line","chart":{"option":{"title":{/*"text":"回款情况","subtext":""*/},"tooltip":{"show":true,y:30,"formatter":"{a} <br>{b} : {c}","trigger":"axis"},"legend":{"show":true,"selectedMode":false,"data":["最高温度","最低温度"],y:20},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"xAxis":[{"type":"category","name":"","data":["周一","周二","周三","周四","周五"]}],"yAxis":[{"type":"value","name":""}],"series":[{"name":"最高温度","type":"line","data":["11","-2","4","14","5"],"markLine":{"data":[]}},{"name":"最低温度","type":"line","data":["7","-9","0","2","1"],"markLine":{"data":[]}}]},"type":"line","name":"折线图","chartObj":null}},"cell_4":{"cellid":"cell_4","cellname":"","celltype":"","cellsts":"","plugintype":"charts","subplugintype":"pie","chart":{"option":{"title":{"x":"center"/*,"text":"逾期情况","subtext":""*/},"tooltip":{"show":true,"trigger":"item","formatter":"{a} <br/>{b} : {c} ({d}%)",y:20},"legend":{"show":true,"orient":"vertical","x":"left","data":["直接访问","邮件营销","联盟广告","视频广告","搜索引擎"],"selectedMode":false,y:20},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"series":[{"name":"访问来源","type":"pie","radius":"55%","center":["50%","60%"],"data":[{"name":"直接访问","value":"335"},{"name":"邮件营销","value":"310"},{"name":"联盟广告","value":"234"},{"name":"视频广告","value":"135"},{"name":"搜索引擎","value":"1548"}]}]},"type":"pie","name":"饼状图","chartObj":null}}} --%>
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
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/freewall.js"></script>
		<link rel="stylesheet" href="${webPath}/component/demo/css/font-awesome.min.css" type="text/css" />
		<!--<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/customfw.js"></script>-->
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
		<script type='text/javascript' src="${webPath}/component/demo/js/dbClick.js"></script>
		<link rel="stylesheet" type="text/css"  href="${webPath}/component/demo/css/property.css"/>
		<link rel="stylesheet" type="text/css"  href="${webPath}/layout/view/themes/css/view-main.css"/>
		<style type="text/css">
		.cover {
		    cursor: default;
		}
		.handle span{top:0;font-size:12px;}
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
			<div class='cell' cellid='cell_1' style='top:0px; left:0px; width:664px; height: 302px; background-color:#EBEBEB' data-handle=".handle">
				<div class="cover">
					<div class="handle ">
						<span>业务增长情况</span>
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_2' style='top:0px; left:672.36669921875px; width:613px; height: 302px; background-color:#EBEBEB' data-handle=".handle">
				<div class="cover">
					<div class="handle ">
						<span>放款情况</span>
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_3' style='top:310.316650390625px; left:0px; width:664px; height: 251px; background-color:#EBEBEB' data-handle=".handle">
				<div class="cover">
					<div class="handle ">
						<span>回款情况</span>
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_4' style='top:310.316650390625px; left:672.36669921875px; width:613px; height: 251px; background-color:#EBEBEB' data-handle=".handle">
				<div class="cover">
					<div class="handle ">
						<span>逾期情况</span>
					</div>
				</div>
				<div class="info">
				</div>
			</div>
	</div>
    </div>
</body>
</html>