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
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/initScroll.css" >
		<script type="text/javascript" src="${webPath}/component/include/initScroll.js"></script>
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
		function submitBtnOne(formId,dataParam){
			var flag = false;
			var submitUrl = $("#"+formId).attr('action');
			//dataParam.demoId = "100002";
			var dataParam = JSON.stringify(dataParam); 
			jQuery.ajax({
				url:webPath+submitUrl,
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
		</script>
	</head>

<body style="overflow: hidden;overflow: auto\9;">
    <div class="layout">
	<div id="freewall" style="margin: 8px;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:0px; width:1280px; height: 556px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>审批意见</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info scroll_y">
					<div class="wk-info"> <ul id="wfTree" class="ztree">
					 </ul> 
					
				</div>
			</div>
	</div>
    </div>
    <script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
	<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
	<script type="text/javascript">
	//审批
	function Wkf_addDiyDom(treeId, treeNode) {
			var liObj = $("#" + treeNode.tId).empty();
			var icon = "<span id='" +treeNode.tId+"_icon' class='" +treeId+"_icon'><i></i></span>";
			var line = "<span id='" +treeNode.tId+"_line' class='" +treeId+"_line'></span>";
			var endDate = "<span id='" +treeNode.tId+"_date' class='" +treeId+"_date'>"+treeNode.end.split(" ")[0]+"</span>";
			var description = "<span id='" +treeNode.tId+"_description' class='" +treeId+"_description'>"+treeNode.description+"</span>";
			var endTime = "<span id='" +treeNode.tId+"_time' class='" +treeId+"_time'>"+treeNode.end.split(" ")[1]+"</span>";
			var optName = "<span id='" +treeNode.tId+"_optName' class='" +treeId+"_optName'>"+treeNode.optName+"</span>";
	/* 		var result = "<span id='" +treeNode.tId+"_result' class='" +treeId+"_result'>"+treeNode.result+"</span>"; */
			var approveIdea = "<span id='" +treeNode.tId+"_approveIdea' class='" +treeId+"_approveIdea' >"+treeNode.approveIdea+"</span>";
			liObj.append(icon+line+endDate+description+endTime+optName+approveIdea);
			$("#" +treeNode.tId+"_line").css("height",liObj.outerHeight()-10+"px");
			
		};
	var Wkf_zTreeObj,
		Wkf_setting = {
			view: {
				selectedMulti: false,
				showIcon: false,
				addDiyDom: Wkf_addDiyDom
			}
		},
		Wkf_zTreeNodes = [];

		$(document).ready(function(){
			$.ajax({
				type: "post",
				data:{appNo:"${appNo}"},
				dataType: 'json',
				url: webPath+"/wkfApprovalOpinion/getApplyApprovalOpinionList",
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					Wkf_zTreeNodes=data.zTreeNodes;
					Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
				}
			});
		});
	</script>
</body>
</html>