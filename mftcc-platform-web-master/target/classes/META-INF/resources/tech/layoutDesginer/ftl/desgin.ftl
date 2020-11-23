<%-- ${cellDatas} --%>
<%-- ${blockDatas} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
String cellDatas = (String)request.getAttribute("cellDatas");
String blockDatas = (String)request.getAttribute("blockDatas");
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
			//控制模块显示，启用这段代码，将上边的代码块注释
			/* wall.reset({
				draggable: false,
				selector: '.cell',
				animate: true,
				onResize: function() {
					wall.fitWidth();
				}
			});
			$('body').css("overflow","auto");
			resDispaly("#freewall",{});
			wall.filter(".cell-dis");
			wall.fitWidth(); */
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
					alert("保存失败！");
				}
			});
			return flag;//必须写
		}
		</script>
	</head>
<body style="overflow: hidden">
    <div class="layout">
	<div id="freewall" style="margin: 8px;" class="free-wall">
		<#list datas as data>
			<div class='cell' data-dis='${(data.dispalySts)!}' cellid='${data.cellid}' style='top:${data.top}; left:${data.left}; width:${data.width}; height: ${data.height}; background-color:#EBEBEB' data-handle=".handle">
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
							<dhcc:${data.formType} property="form${data.formId}" mode="query"/>
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