<%-- [{"width":"535px","height":"234px","left":"0px","top":"0px","name":"图表显示","cellid":"cell_1"},{"width":"737px","height":"234px","left":"543.3800048828125px","top":"0px","name":"评级信息列表","cellid":"cell_2","formId":"eval1001","formType":"tableTag","formUrl":""},{"width":"535px","height":"294px","left":"0px","top":"241.5px","name":"评级得分详情查看","cellid":"cell_3","formId":"eval1001","formType":"propertySeeTag","formUrl":""},{"width":"737px","height":"294px","left":"543.3800048828125px","top":"241.5px","name":"评级记录信息","cellid":"cell_4","formId":"eval1001","formType":"recordCountTag","formUrl":""}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"图表显示","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"评级信息列表","celltype":"","cellsts":"","plugintype":"form","formId":"eval1001","formType":"tableTag","formUrl":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"评级得分详情查看","celltype":"","cellsts":"","plugintype":"form","formId":"eval1001","formType":"propertySeeTag","formUrl":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"评级记录信息","celltype":"","cellsts":"","plugintype":"form","formId":"eval1001","formType":"recordCountTag","formUrl":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
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
		</script>
		<script type="text/javascript" src="${webPath}/component/include/initScroll.js"></script>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/initScroll.css" type="text/css"/>
	</head>
<body  style="overflow: hidden;overflow: auto\9;">
   <dhcc:markPoint markPointName="MfCusAppEval_Detail_List"/>
    <div>
		<div style="vertical-align: bottom;display: block;background-color:#eaebec;height:35px;line-height:35px;" class="tabCont">
			<strong>评级申请</strong>
			<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
				<dhcc:thirdButton value="新增"  action="新增"
						onclick="window.top.openBigForm('${webPath}/appEval/input','新增申请')"></dhcc:thirdButton>
					<!--我的筛选按钮-->
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input id="filter_in_input" placeholder="智能搜索" class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search" class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
					 		<button id="fiter_ctrl_btn"  class="filter_btn_myFilter" type="button"  ><i class="i i-jiantou7"></i></button>
						</div>
						<form  id="filter_action" method="post" action="${webPath}/appEval/getListPage">
							<input type="hidden" id="filter_action_val" name="ajaxData"/>
						</form>
					</div>
					
				</div>
			</div>
		</div>
	</div>
    <div class="layout">
	<div id="freewall" style="margin: 8px;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:0px; width:535px; height: 234px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>最新评级</span>
					</div>
				</div>
				<div class="info">
				</div>
			</div>
			<div class='cell' cellid='cell_2' style='top:0px; left:543.3800048828125px; width:737px; height: 234px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>评级信息列表</span>
					</div>
				</div>
				<div class="info">
					<form  method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
							<dhcc:tableTag paginate="appEvalGnList" property="tableeval1003" head="true" />
					</form>
				</div>
				<a href="${webPath}/appEval/getListPage" class="more font-small bold">更多</a>
			</div>
			<div class='cell' cellid='cell_3' style='top:241.5px; left:0px; width:535px; height: 294px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>评级得分详情查看</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info scroll_xy">
					<form  method="post" theme="simple" name="operform" action="${webPath}/appEval/update">
							<dhcc:propertySeeTag property="formeval1001" mode="query"/>
					</form>
				</div>
			</div>
			<div class='cell' cellid='cell_4' style='top:241.5px; left:543.3800048828125px; width:737px; height: 294px; background-color:#EBEBEB' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>评级记录信息</span>
						<!--<img class="jt-down" src="${webPath}/imgs/jiantou_botton.png"></img>-->
					</div>
				</div>
				<div class="info scroll_xy">
					<form  method="post" theme="simple" name="operform" action="${webPath}/appEval/update">
							<dhcc:recordCountTag property="formeval1002" mode="query"/>
					</form>
				</div>
			</div>
	</div>
    </div>
    <%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
</html>