<%-- [{"width":"596px","height":"213px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"656px","height":"495px","left":"603.75px","top":"0px","name":"点此拖拽","cellid":"cell_2"},{"width":"596px","height":"274px","left":"0px","top":"221.38333129882812px","name":"点此拖拽","cellid":"cell_3"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"charts","subplugintype":"line","chart":{"option":{"title":{"text":"评级历史","subtext":"副标题文本"},"tooltip":{"show":true,"formatter":"{a} <br>{b} : {c}","trigger":"axis"},"legend":{"show":true,"selectedMode":false,"data":["最高温度","最低温度"]},"toolbox":{"show":true,"feature":{"dataView":{"show":true,"readOnly":true},"restore":{"show":true},"saveAsImage":{"show":true}}},"xAxis":[{"type":"time","name":"时间"}],"yAxis":[{"type":"category","name":"评级级别","data":["周一","周二","周三","周四","周五"]}],"series":[{"name":"最高温度","type":"line","data":["11","-2","4","14","5"],"markLine":{"data":[]}},{"name":"最低温度","type":"line","data":["7","-9","0","2","1"],"markLine":{"data":[]}}]},"type":"line","name":"折线图","chartObj":null}},"cell_4":{"cellid":"cell_4","cellname":"点此拖拽","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
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
		<style type="text/css">
			.cover {
			    cursor: default;
			}
		</style>
		<script type="text/javascript">
		    var dataMap = <%=dataMap%>;
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
		function submitBtnOne(formId,dataParam){
			var flag = false;
			var submitUrl = $("#"+formId).attr('action');
			//dataParam.demoId = "100002";
			var dataParam = JSON.stringify(dataParam); 
			jQuery.ajax({
				url:submitUrl,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				async:false,//关键
				success:function(data){
					if(data.flag=="error"){
						alert(data.msg);
					}else{
						alert(data.msg);
						flag = true;//必须写
					}
				},error:function(data){
					alert(  top.getMessage("FAILED_SAVE"));
				}
			});
			return flag;//必须写
		}
		function openBigForm(obj,url){
			window.top.window.openBigForm(url,'评级',false);
		}
		</script>
		<script type="text/javascript" src="${webPath}/component/eval/js/detailResult.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/initScroll.js"></script>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/initScroll.css" type="text/css"/>
		<link type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/detailResult.css?v=${cssJsVersion}"/>
	</head>
<body style="overflow: hidden;overflow: auto\9;">
    <div class="layout">
	<div id="freewall" style="margin: 8px;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:0px; width:596px; height: 243px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>最新评级</span>
					</div>
				</div>
				<div class="info scroll_xy">
					<table class="evel_table">
						<tbody></tbody>
					</table>
				</div>
			</div>
			<div class='cell' cellid='cell_2' style='top:0px; left:603.75px; width:656px; height: 495px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>评级信息列表</span>
					</div>
				</div>
				<div class="info scroll_y tableBgNew ">
					<form  method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
						<dhcc:tableTag paginate="appEvalList" property="tableeval1005" head="true" />
					</form>
				</div>
			</div>
			<div class='cell' cellid='cell_3' style='top:251.38333129882812px; left:0px; width:596px; height: 244px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>历史比较</span>
					</div>
				</div>
				<div class="info">
					<div id="main" style="width: 580px;height:234px;"></div>
				    <script type="text/javascript">
						var myChart = echarts.init(document.getElementById('main'));
						var avgScore = dataMap.avgScore;
						var maxScore = dataMap.maxScore;
						var currentScore = dataMap.currentScore;
						option = {
						    tooltip : {
						        trigger: 'axis',
						        axisPointer : {           
						            type : 'shadow'   
						        }
						    },
						    legend: {
						        data:['当前评级得分','历史平均值','历史最高值']
						    },
						    grid: {
						        left: '3%',
						        right: '4%',
						        bottom: '3%',
						        containLabel: true
						    },
						    xAxis : [
						        {
						            type : 'category',
						            data : ['定量得分','定性得分','调整得分','客户经理调得分']
						        }
						    ],
						    yAxis : [
						        {
						            type : 'value'
						        }
						    ],
						    series : [
						        {
						            name:'当前评级得分',
						            type:'bar',
						            barWidth : 15,
						            stack: '当前评级得分',
						            data:[320, 332, 301, 334, 390]
						           /* markLine : {
						                lineStyle: {
						                    normal: {
						                        type: 'dashed'
						                    }
						                },
						                data : [
						                    [{type : 'min'}, {type : 'max'}]
						                ]
						            }*/
						        },
						        {
						            name:'历史平均值',
						            type:'bar',
						            barWidth : 15,
						            stack: '历史平均值',
						            data:[862, 1018, 964, 1026, 1679]
						        },
						        {
						            name:'历史最高值',
						            type:'bar',
						            barWidth : 15,
						            stack: '历史最高值',
						            data:[620, 732, 701, 734, 1090]
						           
						        }
						    ]
						};
						option.series[0].data = currentScore;
						option.series[1].data = avgScore;
						option.series[2].data = maxScore;
						 myChart.setOption(option);
						</script>
				</div>
			</div>
	</div>
    </div>
</body>
</html>