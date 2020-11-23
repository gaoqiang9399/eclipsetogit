<%-- [{"width":"294px","height":"515px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"535px","height":"535px","left":"301.75px","top":"0px","name":"点此拖拽","cellid":"cell_2"},{"width":"374px","height":"515px","left":"844.9000244140625px","top":"0px","name":"点此拖拽","cellid":"cell_3"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"点此拖拽","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"点此拖拽","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
String cellDatas = (String)request.getAttribute("cellDatas");
String blockDatas = (String)request.getAttribute("blockDatas");
String jsonBean = (String)request.getAttribute("jsonBean");
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
		var jsonBean = eval("("+ '<%=jsonBean%>'+")");
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
		$(document).ready(function() {
		var clientWidth  = $(document.body).width();
		var clientHeight = $(document).height();
		var busSecWidth = clientWidth*24.2/100;		
		var interval = 7.75;
		var div1Left = 0;
		var div1Width = busSecWidth;
		var div2Left = div1Width+interval;
		var div2Width = busSecWidth;
		var div3Left = div2Left+div2Width+interval;
		var div3Width = busSecWidth;
		var div4Left = div3Left+div3Width+interval;
		var div4Width = busSecWidth; 
		var cell1 = $("div[cellid='cell_1']");
		var cell2 = $("div[cellid='cell_2']");
		var cell3 = $("div[cellid='cell_3']");
		var cell4 = $("div[cellid='cell_4']");
		
		cell1.css({ width: div1Width+"px", left: "0px" ,height:clientHeight});
		cell2.css({ width: div2Width+"px", left: div2Left+"px" });
		cell3.css({ width: div3Width+"px", left: div3Left+"px" });
		cell4.css({ width: div4Width+"px", left: div4Left+"px" });
		
		
		 
		 
		 //alert(cell1.css("width","100"));
		});
		</script>
		<link rel="stylesheet" href="${webPath}/component/doc/css/DocBizSceConfig_main.css" />
	</head>
<body  style="overflow: hidden;overflow: auto\9;">
    <div class="layout">
	<div id="freewall" style="margin: 8px;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:0px; width:294px; height: 535px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>文档业务场景</span>
						<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"  id="docScences" src="${webPath}/docBizSceConfig/findScenByType" style="width:222px "></iframe>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_2' style='top:0px; left:301.75px; width:535px; height: 535px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>文档类别</span>
						<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" id="sceDocType" src="" style="width:222px "></iframe>
						
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_3' style='top:0px; left:844.9000244140625px; width:374px; height: 515px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>维度划分</span>
						
						<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" id="docDimms" src="" style="width:222px "></iframe>
						
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			
				<div class='cell' cellid='cell_4' style='top:0px; left:844.9000244140625px; width:374px; height: 515px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>文档细分类型</span>
						
						<iframe frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" id="splitDocs" src="" style="width:222px "></iframe>
						
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