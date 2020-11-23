<%-- ${cellDatas} --%>
<%-- ${blockDatas} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
String cellDatas = (String)request.getAttribute("cellDatas");
String blockDatas = (String)request.getAttribute("blockDatas");
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib prefix="dhcc1" uri="http://www.lssrc.com/tags.tld"%>
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
		<#list datas as data>
			<div class='cell' cellid='${data.cellid}' style='top:${data.top}; left:${data.left}; width:${data.width}; height: ${data.height}; background-color:#EBEBEB' data-handle=".handle">
				<#if data.name !="" >
				<div class='cover'>
					<div class='handle'>
						<span>${data.name}</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				</#if>  
				<div class="info">
					<#if data.formId??>
					<s:form method="post" theme="simple" name="operform" action="TestGnAction_update.action">
						<#if data.formType=="tableTag">
							<dhcc:tableTag paginate="testGnList" property="table${data.formId}" head="true" />
						<#else>
							<dhcc1:${data.formType} property="form${data.formId}" mode="query"/>
						</#if>  
					</s:form>
					</#if>  
				</div>
			</div>
		</#list> 
	</div>
    </div>
</body>
</html>