	<%-- [{"width":"452px","height":"152px","left":"440.640625px","top":"520.75px","name":"点此拖拽","cellid":"cell_1"},{"width":"453px","height":"312px","left":"901.3125px","top":"0px","name":"股东信息","cellid":"cell_2","formId":"cussha00002","formType":"tableTag","formUrl":"${webPath}/mfCusShareholder/getListPage"},{"width":"452px","height":"513px","left":"440.640625px","top":"0px","name":"点此拖拽","cellid":"cell_3"},{"width":"433px","height":"693px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_4"},{"width":"453px","height":"373px","left":"901.3125px","top":"320.46875px","name":"点此拖拽","cellid":"cell_5"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"股东信息","celltype":"","cellsts":"","plugintype":"form","formId":"cussha00002","formType":"tableTag","formUrl":"${webPath}/mfCusShareholder/getListPage","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"点此拖拽","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"点此拖拽","chart":{}},"cell_5":{"cellid":"cell_5","cellname":"点此拖拽","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
String cellDatas = (String)request.getAttribute("cellDatas");
String blockDatas = (String)request.getAttribute("blockDatas");

String cusNo = (String)request.getAttribute("cusId");
String cusType = (String)request.getAttribute("cusType");
String cusForms = (String)request.getAttribute("cusForms");
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
		var cusType = '<%=cusType%>';
		var cusForm = '<%=cusForms%>';
		var cusFormArr = cusForm.split("@");
		var len = cusFormArr.length;
		$(function(){
				for(var i = 0;i<len;i++){
					$("div[cellid="+cusFormArr[i]+"]").show();
				}
			});
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
	</head>
<body style="overflow-y: auto">
    <div class="layout" id = "corp" >
	<div id="freewall" style="margin: 8px;" class="free-wall">
			
			<div class='cell' cellid='mf_cus_capital_info' style='display:none; top:0px; left:0px; width:446px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>资产负债表（企业客户）</span>
					</div>
				</div>
				<div class="info">
						<dhcc:tableTag paginate="mfCusCapitalInfoList" property="tablecuscapi00001" head="true" />
						<a href="${webPath}/mfCusCapitalInfo/getListPageTmp?cusNo=<%=cusNo%>" class="more font-small bold">更多>></a>
				</div>
			</div>
			<div class='cell' cellid='mf_cus_profit_info' style='display:none; top:0px; left:450px; width:446px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>利润分配</span>
					</div>
				</div>
				<div class="info">
						<dhcc:tableTag paginate="mfCusProfitInfoList" property="tablecusprof00001" head="true" />
						<a href="${webPath}/mfCusProfitInfo/getListPageTmp?cusNo=<%=cusNo%>" class="more font-small bold">更多>></a>
				</div>
			</div>
			<div class='cell' cellid='mf_cus_cash_enum' style='display:none; top:0px; left:900px; width:440px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>现金流量</span>
					</div>
				</div>
				<div class="info">
						<dhcc:tableTag paginate="mfCusCashEnumList" property="tablecuscash00001" head="true" />
						<a href="${webPath}/mfCusCashEnum/getListPageTmp?cusNo=<%=cusNo%>" class="more font-small bold">更多>></a>
				</div>
			</div>
			<div class='cell' cellid='mf_cus_house_info' style='display:none; top:0px; left:0px; width:446px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>房屋土地信息</span>
					</div>
				</div>
				<div class="info">
					<dhcc:tableTag paginate="mfCusHouseInfoList" property="tablecushou00001" head="true" />
					<a href="${webPath}/mfCusHouseInfo/getListPageTmp?cusNo=<%=cusNo%>" class="more font-small bold">更多>></a>
				</div>
			</div>
			<div class='cell' cellid='mf_cus_vehicle_info' style='display: none;top:0px; left:450px; width:446px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>车辆资产</span>
					</div>
				</div>
				<div class="info">
						<dhcc:tableTag paginate="mfCusVehicleInfoList" property="tablecusveh00001" head="true" />
						<a href="${webPath}/mfCusVehicleInfo/getListPageTmp?cusNo=<%=cusNo%>" class="more font-small bold">更多>></a>
				</div>
			</div>
			<div class='cell' cellid='mf_cus_society_insur' style='display: none;top:0px;left:900px; width:440px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>社会保险</span>
					</div>
				</div>
				<div class="info"> 
					<dhcc:tableTag paginate="mfCusSocietyInsurList" property="tablecussosur00001" head="true" />
					<a href="${webPath}/mfCusSocietyInsur/getListPageTmp?cusNo=<%=cusNo%>" class="more font-small bold">更多>></a>
				</div>
			</div>
			<div class='cell' cellid='mf_cus_buss_insur' style='display: none;top:405px; left:0px; width:446px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>商业保险</span>
					</div>
				</div>
				<div class="info">
				商业保险
				</div>
			</div>
			<div class='cell' cellid='mf_cus_ratepay_info' style='display:none; top:405px; left:450px; width:446px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>个人纳税信息</span>
					</div>
				</div>
				<div class="info">
										
				</div>
			</div>
			<div class='cell' cellid='mf_cus_intangible_assets' style='display:none; top:405px; left:900px; width:440px; height: 400px; background-color:#FFFFFF' data-handle=".handle">
				<div class='cover'>
					<div class='handle'>
						<span>无形资产信息</span>
					</div>
				</div>
				<div class="info">
				
				</div>
			</div>
			
	</div>
    </div>
    <div class="layout" id = "per" style="display: none;">
		<div id="freewall" style="margin: 8px;" class="free-wall">
		</div>
    </div>
</body>
</html>